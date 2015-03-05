package com.hts.web.operations.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import weibo4j.Friendships;
import weibo4j.model.WeiboException;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldThumbUser;
import com.hts.web.common.pojo.OpUser;
import com.hts.web.common.pojo.OpUserLabelRecommend;
import com.hts.web.common.pojo.OpUserRecommend;
import com.hts.web.common.pojo.OpUserVerifyDto;
import com.hts.web.common.pojo.UserSocialAccount;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.pojo.UserVerifyDto;
import com.hts.web.common.pojo.UserWithWorld;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.operations.dao.OpUserVerifyDtoCacheDao;
import com.hts.web.operations.dao.SysMsgDao;
import com.hts.web.operations.dao.UserRecommendDao;
import com.hts.web.operations.service.UserOperationsService;
import com.hts.web.userinfo.dao.SocialAccountDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.weibo.UserInfo;
import com.qq.connect.javabeans.weibo.FansIdolsBean;
import com.qq.connect.javabeans.weibo.SingleFanIdolBean;
import com.renren.api.RennClient;
import com.renren.api.RennException;

/**
 * <p>
 * 用户运营业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-8-29
 * @author ztj
 *
 */
@Service("HTSUserOperationsService")
public class UserOperationsServiceImpl extends BaseServiceImpl implements
		UserOperationsService {
	
	/**
	 * 当前推荐的社交平台
	 */
	public static final Integer[] RECOMMEND_PLATFORM_CODE = new Integer[]{PlatFormCode.SINA, PlatFormCode.QQ, PlatFormCode.REN_REN};
	
	/**
	 * 新浪微博织图UID
	 */
	public static final String SINA_ZHITU_UID = "2695150601";
	
	/**
	 * 人人网APIKEY
	 */
	public static final String RENREN_API_KEY = "c0bd6bb3d30342ebaa2bc6b2a7beb945";
	
	/**
	 * 人人网SECRETKEY
	 */
	public static final String RENREN_SECRET_KEY = "cdd6568a9e294908816a1bf79d3b6414";
	
	/**
	 * 推荐用户状态：接受
	 */
	public static final int USER_RECOMMEND_ACCEPT = 1;
	
	/**
	 * 推荐用户状态：拒绝
	 */
	public static final int USER_RECOMMEND_REJECT = 2;
	
	/**
	 * 推荐用户状态：待定
	 */
	public static final int USER_RECOMMEND_PENDING = 0;
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private UserRecommendDao userRecommendDao;
	
	@Autowired
	private SocialAccountDao socialAccountDao;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private SysMsgDao sysMsgDao;
	
	@Autowired
	private OpUserVerifyDtoCacheDao opUserVerifyDtoCacheDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Value("${op.squareRecommendUserLimit}")
	private Integer squareRecommendUserLimit = 6;
	
	public Integer getSquareRecommendUserLimit() {
		return squareRecommendUserLimit;
	}

	public void setSquareRecommendUserLimit(Integer squareRecommendUserLimit) {
		this.squareRecommendUserLimit = squareRecommendUserLimit;
	}

	@Override
	public void buildUserRecommend(int maxId, int start,
			int limit, int joinId, Map<String, Object> jsonMap) throws Exception {
		List<OpUser> dtoList = null;
		if(maxId <= 0) { // 默认查询
			dtoList = userRecommendDao.queryRecommendUser(joinId, new RowSelection(start, limit));
		} else { // 查询比指定时间早的记录
			dtoList = userRecommendDao.queryRecommendUserByMaxId(joinId, maxId, new RowSelection(start, limit));
		}
		userInfoService.extractVerify(dtoList);
		userInteractService.extractRemark(joinId, dtoList);
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, dtoList);
	}

	@Override
	public void buildPlatformUserRecomend(int joinId, Map<String, Object> jsonMap) throws Exception {
		List<OpUser> dtoList = null;
		List<UserSocialAccount> accountList = socialAccountDao.querySocialAccountByPlatformCodes(RECOMMEND_PLATFORM_CODE, joinId);
		List<String> userPlatformIds = new ArrayList<String>();
		for(UserSocialAccount socialAccount : accountList) {
			buildPlatformUserRecomendIds(joinId, socialAccount, userPlatformIds);
		}
		if(userPlatformIds.size() > 0) {
			dtoList = userRecommendDao.queryPlatformRecommendUser(SQLUtil.toString(userPlatformIds), joinId);
			userInfoService.extractVerify(dtoList);
			userInteractService.extractRemark(joinId, dtoList);
		} else {
			dtoList = new ArrayList<OpUser>();
		}
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, dtoList);
		
	}
	
	/**
	 * 构建社交平台推荐用户id列表
	 * 
	 * @param joinId
	 * @param socialAccount
	 * @param userPlatformIds
	 */
	private void buildPlatformUserRecomendIds(int joinId, UserSocialAccount socialAccount, List<String> userPlatformIds) {
		int platformCode = socialAccount.getPlatformCode();
		switch(platformCode) {
		case PlatFormCode.SINA:
			buildSinaUserRecommend(socialAccount.getPlatformToken(), socialAccount.getPlatformId(), userPlatformIds);
			break;
		case PlatFormCode.QQ:
			buildQQUserRecommend(socialAccount.getPlatformToken(), socialAccount.getPlatformId(), userPlatformIds);
			break;
		case PlatFormCode.REN_REN:
			buildRenRenUserRecommend(socialAccount.getPlatformToken(), userPlatformIds);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 构建新浪推荐用户id列表
	 * @param accessToken
	 * @param userPlatformIds
	 */
	private void buildSinaUserRecommend(String accessToken, String uid, List<String> userPlatformIds) {
		Friendships ships = new Friendships();
		ships.client.setToken(accessToken);
		try {
			String[] uids = ships.getFriendsIdsByUid(uid);
			for(String id : uids) {
				userPlatformIds.add(id);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构建QQ推荐用户id列表
	 * @param accessToken
	 * @param userPlatformIds
	 */
	private void buildQQUserRecommend(String accessToken, String openID, List<String> userPlatformIds) {
		UserInfo qqUserInfo = new UserInfo(accessToken, openID);
		try {
			FansIdolsBean fansIdols = qqUserInfo.getIdolsList(30, 0, "mode=0", "install=1");
			List<SingleFanIdolBean> idols = fansIdols.getFanIdols();
			for(SingleFanIdolBean idol : idols) {
				userPlatformIds.add(idol.getOpenID());
			}
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构建renren推荐用户id列表
	 * @param accessToken
	 * @param userPlatformIds
	 */
	private void buildRenRenUserRecommend(String accessToken, List<String> userPlatformIds) {
		try {
			RennClient renrenClient = new RennClient(RENREN_API_KEY, RENREN_SECRET_KEY);
			renrenClient.authorizeWithAccessToken(accessToken);
			com.renren.api.service.User[] users = renrenClient.getUserService().listUserFriendApp();
			for(com.renren.api.service.User user : users) {
				userPlatformIds.add(String.valueOf(user.getId()));
			}
		} catch (RennException e) {
		}
	}
	

	@Override
	public void buildSquareRecommendUser(int maxId, int start, int limit, Integer joinId, int worldLimit, Map<String, Object> jsonMap) throws Exception {
		int page = limit;
		if(limit > 0) {
			page = limit;
		} else {
			page = squareRecommendUserLimit;
		}
		List<OpUser> userList = null;
		if(maxId <= 0 && start == 1) {
			OpUser joinUser = userRecommendDao.queryOpUserByUID(joinId);
			if(joinUser != null) {
				userList = userRecommendDao.queryRecommendUser(joinId, new RowSelection(start,page - 1));
				userList.add(0, joinUser);
			} else {
				userList = userRecommendDao.queryRecommendUser(joinId, new RowSelection(start, page));
			}
		} else {
			userList = userRecommendDao.queryRecommendUserByMaxId(joinId, 
					maxId, new RowSelection(start, page));
		}
		if(worldLimit > 0) {
			extractHTWorldThumbUser(worldLimit,userList);
		}
		userInfoService.extractVerify(userList);
		userInteractService.extractRemark(joinId, userList);
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, userList);
	}
	
	
	@Override
	public void buildSquareRecommendUserV2(int maxId, int start, int limit,
			final Integer joinId, final int worldLimit, final Boolean trimSelf, Map<String, Object> jsonMap)
			throws Exception {
		buildSerializables("getActivity", maxId, start, limit, jsonMap, 
				new SerializableListAdapter<OpUser>() {

					@Override
					public List<OpUser> getSerializables(RowSelection rowSelection) {
						List<OpUser> userList = userRecommendDao.queryRecommendUserOrderByAct(joinId, rowSelection);
						if(!trimSelf) {
							OpUser joinUser = userRecommendDao.queryOpUserByUID(joinId);  // 把自己放去第一位
							if(joinUser != null) {
								joinUser.setCurrPos(1);
								userList.add(0, joinUser);
							}
						}
						if(worldLimit > 0) {
							extractHTWorldThumbUser(worldLimit,userList);
						}
						userInfoService.extractVerify(userList);
						userInteractService.extractRemark(joinId, userList);
						return userList;
					}

					@Override
					public List<OpUser> getSerializableByMaxId(int maxId, RowSelection rowSelection) {
						List<OpUser> userList = userRecommendDao.queryRecommendUserOrderByAct(maxId, joinId, rowSelection);
						if(worldLimit > 0) {
							extractHTWorldThumbUser(worldLimit,userList);
						}
						userInfoService.extractVerify(userList);
						userInteractService.extractRemark(joinId, userList);
						return userList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}
					
		}, OptResult.JSON_KEY_USER_INFO, null);
		
	}
	

	@Override
	public void buildLabelRecommendUser(int maxId, int start, int limit,
			final int worldLimit, final Integer joinId, final Boolean trimMe, Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getRecommendId",maxId, start, limit, jsonMap, new SerializableListAdapter<OpUserLabelRecommend>() {

			@Override
			public List<OpUserLabelRecommend> getSerializables(
					RowSelection rowSelection) {
				List<OpUserLabelRecommend> list = null;
				List<OpUserLabelRecommend> normalList = null;
				
				// 查询置顶用户
				list = userRecommendDao.queryWeightLabelRecommendUser(); 
				
				// 查询普通推荐
				if(!trimMe) {
					OpUserLabelRecommend user = userRecommendDao.queryLabelRecommendUserByUID(joinId);
					if(user != null) {
						rowSelection.setLimit(rowSelection.getLimit() - 1);
						normalList = userRecommendDao.queryLabelRecommendUser(joinId, rowSelection);
						normalList.add(user);
					}
				} else {
					normalList = userRecommendDao.queryLabelRecommendUser(joinId, rowSelection);
				}
				list.addAll(normalList);
				extractHTWorldThumbUser(worldLimit, list);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(joinId, list);
				return list;
			}

			@Override
			public List<OpUserLabelRecommend> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpUserLabelRecommend> userList = null;
				userList = userRecommendDao.queryLabelRecommendUser(maxId, joinId, rowSelection);
				extractHTWorldThumbUser(worldLimit, userList);
				userInfoService.extractVerify(userList);
				userInteractService.extractRemark(joinId, userList);
				return userList;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
		}, OptResult.JSON_KEY_USER_INFO, null);
	}
	
	@Override
	public void extractHTWorldThumbUser(int worldLimit, final List<? extends UserWithWorld> userList) {
		int listSize = userList.size();
		if(listSize > 0 && worldLimit > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] userIds = new Integer[listSize];
			for(int i = 0; i < listSize; i++) {
				Integer userId = userList.get(i).getId();
				userIds[i] = userId;
				indexMap.put(userId, i);
			}
			worldDao.queryHTWorldThumbUserByLimit(userIds, worldLimit, new RowCallback<HTWorldThumbUser>() {

				@Override
				public void callback(HTWorldThumbUser thumb) {
					Integer uid = thumb.getUserId();
					Integer index = indexMap.get(uid);
					userList.get(index).getHtworld().add(thumb);
				}
			});
		}
	}

	@Override
	public void saveOrUpdateRecommend(Integer userId, Integer verifyId) throws Exception {
		Date date = new Date();
		OpUserRecommend recommend = userRecommendDao.queryRecommendUserByUID(userId);
		if(recommend == null) {
			Integer id = keyGenService.generateId(KeyGenServiceImpl.OP_USER_REC_ID);
			userRecommendDao.saveRecommendUser(new OpUserRecommend(
					id, userId, verifyId, null, 0, date, new Date(), 
					USER_RECOMMEND_ACCEPT, USER_RECOMMEND_PENDING));
		} else {
			switch (recommend.getUserAccept()) {
			// u-0,(s=1 or s=0)
			case USER_RECOMMEND_PENDING: 
				//set u=1
				userRecommendDao.updateUserAcceptByUID(userId, USER_RECOMMEND_ACCEPT, date); 
				break;
			// u=2,s=0
			case USER_RECOMMEND_REJECT: 
				// set u=1,s=0
				userRecommendDao.updateAcceptByUID(userId, USER_RECOMMEND_ACCEPT,
						USER_RECOMMEND_PENDING, date);
				break;
			// u=1
			default: 
				// set s=0
				if(recommend.getSysAccept().equals(USER_RECOMMEND_REJECT))
					userRecommendDao.updateSysAcceptByUID(userId, USER_RECOMMEND_PENDING, date);
				else 
					throw new HTSException("已经申请过", ERROR_CODE_REPEAT_OPT);
				break;
			}
		}
	}
	
	@Override
	public void updateRecommendUserAccept(Integer userId, Boolean accepted) throws Exception {
		Date date = new Date();
		if(!accepted) // 拒绝推荐
			userRecommendDao.updateUserAcceptByUID(userId, USER_RECOMMEND_REJECT, date);
		else { // 接受推荐
			OpUserRecommend recommend = userRecommendDao.queryRecommendUserByUID(userId);
			userRecommendDao.updateUserAcceptByUID(userId, USER_RECOMMEND_ACCEPT, date);
			userInfoDao.updateStartById(userId, recommend.getVerifyId());
		}
		sysMsgDao.updateRecipientValid(userId, Tag.USER_MSG_USER_RECOMMEND, Tag.FALSE);
	}

	@Override
	public Integer getRecommendState(Integer userId) throws Exception {
		Integer state = 1;
		OpUserRecommend recommend = userRecommendDao.queryRecommendUserByUID(userId);
		if(recommend != null && recommend.getUserAccept().equals(USER_RECOMMEND_ACCEPT)) {
			if(recommend.getSysAccept().equals(USER_RECOMMEND_PENDING)) 
				state = 2;
			else if(recommend.getSysAccept().equals(USER_RECOMMEND_ACCEPT)) 
				state = 3;
		} else {
			com.hts.web.common.pojo.UserInfo userInfo = userInfoDao.queryUserInfoById(userId);
			if(userInfo.getStar() != 0) {
				state = 3;
			}
		}
		return state;
	}
	
	@Override
	public UserVerify getRecommendStateV2(Integer userId) throws Exception {
		Integer state = 0;
		UserVerify verify = new UserVerify(0, "", "", "", 0);
		OpUserRecommend recommend = userRecommendDao.queryRecommendUserByUID(userId);
		if(recommend != null && recommend.getUserAccept().equals(USER_RECOMMEND_ACCEPT)) {
			if(recommend.getSysAccept().equals(USER_RECOMMEND_PENDING)) { 
				state = -2;
			}
			else if(recommend.getSysAccept().equals(USER_RECOMMEND_ACCEPT)) 
				state = recommend.getVerifyId();
		} else {
			com.hts.web.common.pojo.UserInfo userInfo = userInfoDao.queryUserInfoById(userId);
			if(userInfo.getStar() != 0) {
				state = userInfo.getStar();
			}
		}
		verify.setId(state);
		userInfoService.extractVerify(verify);
		return verify;
	}
	
	@Override
	public void buildVerifyIndex(Integer userId, int limit, Map<String, Object> jsonMap) throws Exception {
		final List<OpUserVerifyDto> verifyList = opUserVerifyDtoCacheDao.queryVerify();
		int size = verifyList.size();
		if(size > 0) {
			Integer[] verifyIds = new Integer[size];
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			for(int i = 0; i < size; i++) {
				Integer verifyId = verifyList.get(i).getId();
				verifyIds[i] = verifyId;
				indexMap.put(verifyId, i);
			}
			userRecommendDao.queryVerifyUser(userId, verifyIds, limit, new RowCallback<UserVerifyDto>() {

				@Override
				public void callback(UserVerifyDto t) {
					Integer verifyId = t.getVerifyId();
					Integer index = indexMap.get(verifyId);
					verifyList.get(index).getUserInfo().add(t);
				}
			});
		}
		jsonMap.put(OptResult.JSON_KEY_INDEX, verifyList);
	}
	
	@Override
	public void buildVerify(Map<String, Object> jsonMap) throws Exception {
		List<OpUserVerifyDto> verifyList = opUserVerifyDtoCacheDao.queryVerify();
		jsonMap.put(OptResult.JSON_KEY_VERIFY, verifyList);
	}


	@Override
	public void buildVerifyRecommendUser(int maxId, int start, int limit,
			final Integer userId, final Integer verifyId, final int worldLimit, Map<String, Object> jsonMap)
			throws Exception {
		buildSerializables("getActivity",maxId, start, limit, jsonMap, new SerializableListAdapter<OpUser>() {

			@Override
			public List<OpUser> getSerializables(
					RowSelection rowSelection) {
				List<OpUser> userList = userRecommendDao.queryVerifyRecommendUserOrderByAct(userId, verifyId, rowSelection);
				if(worldLimit > 0) {
					extractHTWorldThumbUser(worldLimit,userList);
				}
				userInfoService.extractVerify(userList);
				userInteractService.extractRemark(userId, userList);
				return userList;
			}

			@Override
			public List<OpUser> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpUser> userList = userRecommendDao.queryVerifyRecommendUserOrderByAct(maxId, userId, verifyId, rowSelection);
				if(worldLimit > 0) {
					extractHTWorldThumbUser(worldLimit,userList);
				}
				userInfoService.extractVerify(userList);
				userInteractService.extractRemark(userId, userList);
				return userList;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
		}, OptResult.JSON_KEY_USER_INFO, null);
	}


}
