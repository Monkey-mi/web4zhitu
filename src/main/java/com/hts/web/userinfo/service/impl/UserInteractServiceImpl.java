package com.hts.web.userinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.SerializableSinceIdListAdapter;
import com.hts.web.common.pojo.ObjectWithUserRemark;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.pojo.UserConcern;
import com.hts.web.common.pojo.UserConcernDto;
import com.hts.web.common.pojo.UserConcernType;
import com.hts.web.common.pojo.UserFollowDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.pojo.UserRemark;
import com.hts.web.common.pojo.UserReport;
import com.hts.web.common.pojo.UserSearchInfo;
import com.hts.web.common.pojo.UserShieldInfo;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.operations.dao.UserZombieDao;
import com.hts.web.push.service.PushService;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserConcernTypeDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserRemarkDao;
import com.hts.web.userinfo.dao.UserReportDao;
import com.hts.web.userinfo.dao.UserShieldDao;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;

/**
 * <p>
 * 用户互动子模块业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-7-2
 * @author ztj
 *
 */
@Service("HTSUserInteractService")
public class UserInteractServiceImpl extends BaseServiceImpl implements UserInteractService{
	
	/**
	 * 已经关注错误
	 */
	public static final int HAS_OPTIONED_ERROR = 1;
	
	/**
	 * 已经取消关注错误
	 */
	public static final int HAS_CANCEL_OPTION_ERROR = 2;
	
	/**
	 * 已经关注提示
	 */
	public static final String TIP_HAS_CONCERN_ERROR = "已经关注过,或关注对象不存在";
	
	public static final String TIP_HAS_CANCEL_CONCERN_ERROR = "已经取消关注，或取消关注对象不存在";

	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private UserConcernTypeDao userConcernTypeDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserShieldDao userShieldDao;
	
	@Autowired
	private UserReportDao userReportDao;
	
	@Autowired
	private UserZombieDao userZombieDao;
	
	@Autowired
	private UserRemarkDao userRemarkDao;
	
	@Override
	public List<PushStatus> batchSaveConcern(Boolean im, Integer userId, String concernIdsStr) throws Exception {
		return batchSaveConcern(im, userId, concernIdsStr, 0);
	}
	
	@Override
	public List<PushStatus> batchSaveConcern(Boolean im, Integer userId, String concernIdsStr, 
			Integer concernCount) throws Exception {
		Integer count = 0;
		Integer[] concernIds = StringUtil.convertStringToIds(concernIdsStr);
		List<PushStatus> resultList = new ArrayList<PushStatus>();
		for(Integer concernId : concernIds) {
			if(userId.equals(concernId)) {
				throw new HTSException(TIP_HAS_CONCERN_ERROR, HAS_OPTIONED_ERROR);
			}
			PushStatus result = null;
			UserConcern concern = userConcernDao.queryConcern(userId, concernId);
			if(concern == null) {
				result = saveConcernOpt(im, userId, concernId, Tag.FALSE, new Date());
			} else if(concern.getValid() == Tag.FALSE) {
				result = reSaveConcernOpt(im, userId, concernId, new Date());
			} else {
				continue; // 跳过已经关注过的用户
			}
			resultList.add(result);
			Long followCount = userConcernDao.queryFollowCount(concernId);
			userInfoDao.updateFollowCount(concernId, followCount.intValue());
			count++;
		}
		if(concernCount == null || concernCount.equals(0)) {
			Long concernNum = userConcernDao.queryConcernCount(userId);
			concernCount = concernNum.intValue();
		}
		userInfoDao.updateConcernCount(userId, concernCount.intValue());
		
		return resultList;
	}
	
	@Override
	public PushStatus saveConcern(Boolean im, Integer userId, 
			Integer concernId) throws Exception {
		return saveConcern(im, userId, concernId, 0);
	}
	
	@Override
	public PushStatus saveConcern(Boolean im, Integer userId, Integer concernId, 
			Integer concernCount) throws Exception {
		if(userId.equals(concernId)) {
			throw new HTSException(TIP_HAS_CONCERN_ERROR, HAS_OPTIONED_ERROR);
		}
		PushStatus result = null;
		UserConcern concern = userConcernDao.queryConcern(userId, concernId);
		if(concern == null) {
			result = saveConcernOpt(im, userId, concernId, Tag.FALSE, new Date());
		} else if(concern.getValid() == Tag.FALSE) {
			result = reSaveConcernOpt(im, userId, concernId, new Date());
		} else {
			throw new HTSException(TIP_HAS_CONCERN_ERROR, HAS_OPTIONED_ERROR);
		}
		Long followCount = userConcernDao.queryFollowCount(concernId);
		userInfoDao.updateFollowCount(concernId, followCount.intValue());
		
		if(concernCount == null || concernCount.equals(0)) {
			Long concernNum = userConcernDao.queryConcernCount(userId);
			concernCount = concernNum.intValue();
		}
		userInfoDao.updateConcernCount(userId, concernCount.intValue());
		return result;
	}
	
	/**
	 * 保存用户关注
	 * 
	 * @param userId
	 * @param concernId
	 * @param ck
	 * @param concernDate
	 * @throws Exception
	 */
	private PushStatus saveConcernOpt(Boolean im, Integer userId, Integer concernId, Integer ck,
			Date concernDate) throws Exception {
		UserPushInfo userPushInfo = userInfoDao.queryUserPushInfoById(concernId);
		Integer isMututal = updateMututal(concernId, userId, concernDate, true);
		Integer id = keyGenService.generateId(KeyGenServiceImpl.USER_CONCERN_ID);
		userConcernDao.saveConcern(id, userId, concernId, isMututal, ck, concernDate);
		
		Integer shield = userShieldDao.queryShieldId(concernId, userId) == null ? Tag.FALSE : Tag.TRUE;
		String remark = userRemarkDao.queryRemark(concernId, userId);
		PushStatus result = new PushStatus(id, concernId, userPushInfo.getPhoneCode(), isMututal,
				userPushInfo.getAcceptConcernPush(), shield, remark);
		
		
		if(im) {
			boolean otherIm = UserInfoUtil.checkIsImVersion(userPushInfo.getVer());
			// 对方未开通im，系统代发消息
			if(!otherIm) {
				result.setAccept(Tag.FALSE);
				result.setShield(Tag.TRUE);
				pushService.pushConcern(id, userId, concernId, userPushInfo, shield);
			}
		// 我未开通IM，系统代发消息
		} else {
			pushService.pushConcern(id, userId, concernId, userPushInfo, shield);
		}
		return result;
	}
	
	/**
	 * 重新关注
	 * @param userId
	 * @param concernId
	 * @param concernDate
	 * @throws Exception
	 */
	private PushStatus reSaveConcernOpt(Boolean im, Integer userId, Integer concernId, 
			Date concernDate) throws Exception {
		Integer isMututal = updateMututal(concernId, userId, concernDate, true);
		userConcernDao.updateConcern(userId, concernId, isMututal, concernDate, Tag.TRUE);
		PushStatus result = new PushStatus(concernId, Tag.IOS, isMututal,
				Tag.FALSE, Tag.TRUE, null);
		return result;
	}
	
	@Override
	public Integer updateMututal(Integer userId, Integer concernId, Date concernDate, boolean mututal) throws Exception {
		Integer isMututal = Tag.FALSE;
		/*
		 * 判断对方是否已经关注自己 
		 */
		UserConcern concern = userConcernDao.queryConcern(userId, concernId);
		if(concern != null && concern.getValid() == Tag.TRUE) {
			if(mututal && concern.getIsMututal() == Tag.FALSE) {
				// 设置对方互相关注
				userConcernDao.updateIsMututal(userId, concernId, Tag.TRUE, concernDate);
			} else if(!mututal && concern.getIsMututal() == Tag.TRUE){
				// 取消对方的互相关注
				userConcernDao.updateIsMututal(userId, concernId, Tag.FALSE, concernDate);
			}
			isMututal = Tag.TRUE;
		} 
		return isMututal;
	}
	
	@Override
	public void cancelConcern(Integer userId, Integer concernId) throws Exception {
		updateMututal(concernId, userId, new Date(), false);
		Integer status = userConcernDao.updateConcern(userId, concernId, Tag.FALSE, new Date(), Tag.FALSE);
		if(status > 0) {
			Long followCount = userConcernDao.queryFollowCount(concernId);
			userInfoDao.updateFollowCount(concernId, followCount.intValue());
			Long concernCount = userConcernDao.queryConcernCount(userId);
			userInfoDao.updateConcernCount(userId, concernCount.intValue());
		} else {
			throw new HTSException(TIP_HAS_CANCEL_CONCERN_ERROR, HAS_CANCEL_OPTION_ERROR);
		}
	}
	
	@Override
	public int batchCancelConcern(Integer userId, String concernIdsStr) throws Exception {
		int count = 0;
		Integer[] concernIds = StringUtil.convertStringToIds(concernIdsStr);
		for(Integer concernId : concernIds) {
			updateMututal(concernId, userId, new Date(), false);
			Integer status = userConcernDao.updateConcern(userId, concernId, Tag.FALSE, new Date(), Tag.FALSE);
			if(status > 0) {
				Long followCount = userConcernDao.queryFollowCount(concernId);
				userInfoDao.updateFollowCount(concernId, followCount.intValue());
				count++;
			} else {
				continue;
			}
		}
		Long concernCount = userConcernDao.queryConcernCount(userId);
		userInfoDao.updateConcernCount(userId, concernCount.intValue());
		return count;
	}
	
	@Override
	public void buildConcerns(final Integer userId, final Integer joinId, int sinceId, int maxId, 
			int start, final int limit, final int total, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(sinceId, maxId, start, limit, jsonMap, new SerializableSinceIdListAdapter<UserConcernDto>() {

			@Override
			public List<UserConcernDto> getSerializables(
					RowSelection rowSelection) {
				List<UserConcernDto> list = null;
				if(joinId == 0 || userId.equals(joinId)) {
					list = userConcernDao.queryConcerns(userId, rowSelection);
					// userConcernDao.updateConcernCK(userId); // 更新未读关注消息
				} else {
					list = userConcernDao.queryConcernsWithJoin(userId, joinId, rowSelection);
				}
				userInfoService.extractVerify(list);
				extractRemark(joinId, list);
				return list;
			}

			@Override
			public List<UserConcernDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<UserConcernDto> list = null;
				if(joinId == 0 || userId.equals(joinId)) {
					list = userConcernDao.queryConcernsByMaxId(userId, maxId, rowSelection);
				} else {
					// 如果是马甲，只查询和user_info中设置的concern_count的关注列表
					Integer zombieId = userZombieDao.queryIdByUID(userId);
					if(zombieId != null && !zombieId.equals(0)) {
						Integer concernCount = userInfoDao.queryConcernCount(userId);
						if(concernCount <= total) {
							list = new ArrayList<UserConcernDto>();
						} else {
							//最后一页不足一页时，取余数
							if((concernCount-total) / limit == 0) {
								rowSelection.setLimit((concernCount-total) % limit);
							}
							list = userConcernDao.queryConcernsWithJoinByMaxId(userId, joinId, maxId, rowSelection);
						}
					} else {
						list = userConcernDao.queryConcernsWithJoinByMaxId(userId, joinId, maxId, rowSelection);
					}
				}
				userInfoService.extractVerify(list);
				extractRemark(joinId, list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
//				return userConcernDao.queryConcernCountByMaxId(userId, maxId);
				return 0l;
			}

			@Override
			public List<UserConcernDto> getSerializableBySinceId(int sinceId,
					RowSelection rowSelection) {
				List<UserConcernDto> list = null;
				if(joinId == 0 || userId.equals(joinId)) {
					list = userConcernDao.queryConcernsByMinId(userId, sinceId, rowSelection);
				} else {
					list = userConcernDao.queryConcernsWithJoinByMinId(userId, joinId, sinceId, rowSelection);
				}
				userInfoService.extractVerify(list);
				extractRemark(joinId, list);
				return list;
			}

			@Override
			public long getTotalBySinceId(int sinceId) {
				return userConcernDao.queryConcernCountByMinId(userId, sinceId);
			}
			
		},OptResult.JSON_KEY_CONCERNS,OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	@Override
	public void buildConcerns(final String userName, final Integer userId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserConcernDto>() {

			@Override
			public List<UserConcernDto> getSerializables(RowSelection rowSelection) {
				List<UserConcernDto> list = userConcernDao.queryConcernByName(userName, userId, rowSelection);
				userInfoService.extractVerify(list);
				extractRemark(userId, list);
				return list;
			}

			@Override
			public List<UserConcernDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<UserConcernDto> list = userConcernDao.queryConcernByName(maxId, userName, userId, rowSelection);
				userInfoService.extractVerify(list);
				extractRemark(userId, list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
		}, OptResult.JSON_KEY_CONCERNS, OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	@Override
	public void buildFollows(final Boolean clearUnCheck, final Integer userId, final Integer joinId, int sinceId, int maxId, 
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(sinceId, maxId, start, limit, jsonMap, new SerializableSinceIdListAdapter<UserFollowDto>() {

			@Override
			public List<UserFollowDto> getSerializables(
					RowSelection rowSelection) {
				List<UserFollowDto> dtoList = null;
				if(joinId == 0 || userId.equals(joinId)) {
					dtoList = userConcernDao.queryFollows(userId, rowSelection);
					if(clearUnCheck && dtoList.size() > 0)
						userConcernDao.updateConcernCK(userId, 
								dtoList.get(dtoList.size() - 1).getId(), dtoList.get(0).getId()); // 更新未读关注消息
				} else {
					dtoList = userConcernDao.queryFollowsWithJoin(userId, joinId, rowSelection);
				}
				userInfoService.extractVerify(dtoList);
				extractRemark(joinId, dtoList);
				return dtoList;
			}

			@Override
			public List<UserFollowDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<UserFollowDto> list = null;
				if(joinId == 0 || userId.equals(joinId)) {
					list = userConcernDao.queryFollowsByMaxId(userId, maxId, rowSelection);
					if(clearUnCheck && list.size() > 0) {
						userConcernDao.updateConcernCK(userId, 
								list.get(list.size() - 1).getId(), list.get(0).getId()); // 更新未读关注消息
					}
				} else {
					list = userConcernDao.queryFollowsWithJoinByMaxId(userId, joinId, maxId, rowSelection);
				}
				userInfoService.extractVerify(list);
				extractRemark(joinId, list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
//				return userConcernDao.queryFollowCountByMaxId(userId, maxId);
				return 0l;
			}

			@Override
			public List<UserFollowDto> getSerializableBySinceId(int sinceId,
					RowSelection rowSelection) {
				List<UserFollowDto> list = null;
				if(joinId == 0 || userId.equals(joinId)) {
					list = userConcernDao.queryFollowsByMinId(userId, sinceId, rowSelection);
				} else {
					list = userConcernDao.queryFollowsWithJoinByMinId(userId, joinId, sinceId, rowSelection);
				}
				userInfoService.extractVerify(list);
				extractRemark(joinId, list);
				return list;
			}

			@Override
			public long getTotalBySinceId(int sinceId) {
				return userConcernDao.queryFollowCountByMinId(userId, sinceId);
			}
			
		},OptResult.JSON_KEY_FOLLOWS,OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	
	@Override
	public void buildUserSearchInfo(String userName, Integer joinId, int minId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		List<UserSearchInfo> searList = userInfoDao.queryUserSearchInfoByNameAndMinId(userName, joinId, minId, 
				new RowSelection(start, limit));
		userInfoService.extractVerify(searList);
		extractRemark(joinId, searList);
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, searList);
	}

	@Override
	public void saveConcernType(Integer userId, Integer typeId)
			throws Exception {
		UserConcernType type = userConcernTypeDao.queryConcernType(userId, typeId);
		if(type == null) {
			userConcernTypeDao.saveConcernType(new UserConcernType(userId, typeId, Tag.TRUE));
		} else if(type.getValid().equals(Tag.FALSE)) {
			userConcernTypeDao.updateConcernTypeValid(userId, typeId, Tag.TRUE);
		} else {
			throw new HTSException(TIP_HAS_CONCERN_ERROR, HAS_OPTIONED_ERROR);
		}
	}

	@Override
	public void cancelConcernType(Integer userId, Integer typeId)
			throws Exception {
		userConcernTypeDao.updateConcernTypeValid(userId, typeId, Tag.FALSE);
	}

	@Override
	public void buildNotConcern(Integer userId, String loginCodesStr,
			Map<String, Object> jsonMap)
			throws Exception {
		String[] loginCodes = StringUtil.convertStringToStrs(loginCodesStr);
		if(loginCodes.length <= 0) {
			throw new HTSException("loginCode列表为空");
		}
		List<UserInfoDto> list = userConcernDao.queryNotConcernUserInfo(userId, loginCodes);
		userInfoService.extractVerify(list);
		extractRemark(userId, list);
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, list);
	}

	@Override
	public void buildRegister(Integer userId, String loginCodesStr, Map<String, Object> jsonMap)
			throws Exception {
		String[] loginCodes = StringUtil.convertStringToStrs(loginCodesStr);
		if(loginCodes.length <= 0) {
			throw new HTSException("loginCode列表为空");
		}
		List<UserInfoDto> list = userInfoDao.queryRegister(loginCodes);
		userInfoService.extractVerify(list);
		extractRemark(userId, list);
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, list);
	}

	@Override
	public void saveShield(Integer userId, Integer shieldId) throws Exception {
		Integer suid = userShieldDao.queryShieldId(userId, shieldId);
		if(suid == null) {
			userShieldDao.saveShield(userId, shieldId, new Date());
		} else {
			throw new HTSException("该用户已经被屏蔽", HAS_OPTIONED_ERROR);
		}
	}

	@Override
	public void deleteShield(Integer userId, Integer shieldId) throws Exception {
		Integer suid = userShieldDao.queryShieldId(userId, shieldId);
		if(suid != null) {
			userShieldDao.deleteShield(userId, shieldId);
		} else {
			throw new HTSException("该用户未被屏蔽", HAS_CANCEL_OPTION_ERROR);
		}
		
	}

	@Override
	public void buildShieldUser(final Integer userId, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserShieldInfo>() {

			@Override
			public List<UserShieldInfo> getSerializables(RowSelection rowSelection) {
				return userShieldDao.queryShieldUser(userId, rowSelection);
			}

			@Override
			public List<UserShieldInfo> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return userShieldDao.queryShieldUser(maxId, userId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
		}, OptResult.JSON_KEY_USER_INFO, null);
	}

	@Override
	public void saveReport(Integer userId, Integer reportId) throws Exception {
		UserReport ur = userReportDao.queryReportId(userId, reportId);
		if(ur == null) {
			Integer id = keyGenService.generateId(KeyGenServiceImpl.USER_REPORT_ID);
			userReportDao.saveReport(new UserReport(id, userId, reportId,
					new Date(), null));
		} else if(ur.getValid().equals(Tag.FALSE)) {
			Integer id = keyGenService.generateId(KeyGenServiceImpl.USER_REPORT_ID);
			ur.setId(id);
			ur.setValid(Tag.TRUE);
			ur.setReportDate(new Date());
			userReportDao.updateReport(ur);
		}
	}
	
	@Override
	public void extractRemark(Integer userId, final List<? extends ObjectWithUserRemark> objList) {
		Set<Integer> keySet = new HashSet<Integer>();
		for(int i = 0; i < objList.size(); i++) {
			Integer rid = objList.get(i).getRemarkId();
			if(!keySet.contains(rid)) {
				keySet.add(rid);
			}
		}
		if(keySet.size() > 0) {
			Integer[] keies = new Integer[keySet.size()];
			keySet.toArray(keies);
			Map<Integer, String> rmap = userRemarkDao.queryRemark(userId, keies);
			for(ObjectWithUserRemark obj : objList) {
				if(rmap.containsKey(obj.getRemarkId())) {
					obj.setRemark(rmap.get(obj.getRemarkId()));
				}
			}
		}
	}
	

	@Override
	public void extractRemark(Integer userId, ObjectWithUserRemark obj) {
		if(userId != -1 && !userId.equals(obj.getRemarkId())) {
			String remark = userRemarkDao.queryRemark(userId, obj.getRemarkId());
			obj.setRemark(remark);
		}
	}

	@Override
	public void updateRemark(Integer userId, Integer remarkId, String remark)
			throws Exception {
		// 为空时删除备注
		if(StringUtil.checkIsNULL(remark)) {
			userRemarkDao.deleteRemark(userId, remarkId);
		} else {
			String currRemark = userRemarkDao.queryRemark(userId, remarkId);
			if(currRemark != null) { 
				// 更新备注
				if(!currRemark.equals(remark))
					userRemarkDao.updateRemark(new UserRemark(userId, remarkId, remark));
			// 保存备注
			} else {
				userRemarkDao.saveRemark(new UserRemark(userId, remarkId, remark));
			}
		}
	}

	@Override
	public void deleteRemark(Integer userId, Integer remarkId) throws Exception {
		userRemarkDao.deleteRemark(userId, remarkId);
	}

	@Override
	public void buildNewFollow(final Boolean clearUnCheck, final Integer userId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserFollowDto>() {

			@Override
			public List<UserFollowDto> getSerializables(
					RowSelection rowSelection) {
				List<UserFollowDto> dtoList = null;
				dtoList = userConcernDao.queryNewFollow(userId, rowSelection);
				if(clearUnCheck && dtoList.size() > 0) {
					userConcernDao.updateConcernCK(userId, 
							dtoList.get(dtoList.size() - 1).getId(), dtoList.get(0).getId()); // 更新未读关注消息
				}
				userInfoService.extractVerify(dtoList);
//				extractRemark(userId, dtoList);
				return dtoList;
			}

			@Override
			public List<UserFollowDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<UserFollowDto> dtoList = null;
				dtoList = userConcernDao.queryNewFollow(maxId, userId, rowSelection);
				if(clearUnCheck && dtoList.size() > 0) {
					userConcernDao.updateConcernCK(userId, 
							dtoList.get(dtoList.size() - 1).getId(), dtoList.get(0).getId()); // 更新未读关注消息
				}
				userInfoService.extractVerify(dtoList);
//				extractRemark(userId, list);
				return dtoList;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0l;
			}

		},OptResult.JSON_KEY_FOLLOWS, null);
	}

	@Override
	public void updateNewFollow(Integer concernId) throws Exception {
		userConcernDao.updateISNew(concernId);
	}

	@Override
	public void deleteFollow(Integer userId, Integer followId) throws Exception {
		cancelConcern(followId, userId);
	}
}
