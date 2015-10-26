package com.hts.web.userinfo.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.service.OpenSearchService;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.BaseOnBuildSerializableListener;
import com.hts.web.common.BaseSerializableListAdapter;
import com.hts.web.common.OnBuildSerializableListener;
import com.hts.web.common.OnBuildSinceSerializableListener;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.SerializableSinceIdListAdapter;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldInteract;
import com.hts.web.common.pojo.HTWorldLikeMe;
import com.hts.web.common.pojo.HTWorldLikeMeRelate;
import com.hts.web.common.pojo.HTWorldLikeMeThumb;
import com.hts.web.common.pojo.HTWorldLiked;
import com.hts.web.common.pojo.MsgAt;
import com.hts.web.common.pojo.MsgAtDto;
import com.hts.web.common.pojo.MsgAtId;
import com.hts.web.common.pojo.MsgCommentDto;
import com.hts.web.common.pojo.OpSysMsg;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.pojo.UserInfoAvatar;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserIsMututal;
import com.hts.web.common.pojo.UserMsg;
import com.hts.web.common.pojo.UserMsgBox;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.common.pojo.UserMsgIndex;
import com.hts.web.common.pojo.UserMsgLiked;
import com.hts.web.common.pojo.UserMsgRecipientDto;
import com.hts.web.common.pojo.UserMsgStatus;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.PushUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.TimeUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.operations.dao.SysMsgCommonCacheDao;
import com.hts.web.operations.dao.SysMsgCommonDeletedDao;
import com.hts.web.operations.dao.SysMsgDao;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.hts.web.userinfo.dao.MsgAtCommentDao;
import com.hts.web.userinfo.dao.MsgAtDao;
import com.hts.web.userinfo.dao.MsgAtWorldDao;
import com.hts.web.userinfo.dao.MsgCommentDao;
import com.hts.web.userinfo.dao.MsgUnreadDao;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserMsgDao;
import com.hts.web.userinfo.dao.UserMsgInteractDao;
import com.hts.web.userinfo.dao.UserMsgRecipientBoxDao;
import com.hts.web.userinfo.dao.UserMsgSendBoxDao;
import com.hts.web.userinfo.dao.UserMsgShieldDao;
import com.hts.web.userinfo.dao.UserRemarkDao;
import com.hts.web.userinfo.dao.UserShieldDao;
import com.hts.web.userinfo.dao.MsgUnreadDao.UnreadType;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.userinfo.service.UserMsgService;
import com.hts.web.ztworld.dao.HTWorldCommentDao;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldInteractDao;
import com.hts.web.ztworld.dao.HTWorldLikedDao;

/**
 * 用户消息业务逻辑访问对象
 * 
 * @author ztj
 * 
 */
@Service("HTSUserMsgService")
public class UserMsgServiceImpl extends BaseServiceImpl implements
		UserMsgService {
	
	private static Logger logger = Logger.getLogger(UserMsgServiceImpl.class);
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private HTWorldCommentDao worldCommentDao;
	
	@Autowired
	private HTWorldLikedDao worldLikedDao;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private UserMsgDao userMsgDao;
	
	@Autowired
	private UserMsgSendBoxDao userMsgSendBoxDao;
	
	@Autowired
	private UserMsgRecipientBoxDao userMsgRecipientBoxDao;
	
	@Autowired
	private UserMsgShieldDao userMsgShieldDao;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private SysMsgDao sysMsgDao;
	
	@Autowired
	private HTWorldInteractDao worldInteractDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserShieldDao userShieldDao;
	
	@Autowired
	private UserMsgInteractDao userMsgInteractDao;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private UserRemarkDao userRemarkDao;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Autowired
	private OpenSearchService openSearchService;
	
	@Autowired
	private MsgAtDao atDao;
	
	@Autowired
	private MsgAtWorldDao atWorldDao;
	
	@Autowired
	private MsgAtCommentDao atCommentDao;
	
	@Autowired
	private MsgCommentDao msgCommentDao;
	
	@Autowired
	private SysMsgCommonDeletedDao sysMsgCommonDeletedDao;
	
	@Autowired
	private SysMsgCommonCacheDao sysMsgCommonCacheDao;
	
	@Autowired
	private MsgUnreadDao msgUnreadDao;
	
	@Value("${msg.squareRuleMsg}")
	private String squareRuleMsg ;
	
	@Value("${push.customerServiceId}")
	private Integer customerServiceId = 13;
	
	@Value("${push.feedbackListeners}")
	private String feedbackListeners;

	private Integer likeMeGroupInterval = 5*60*1000;
	
	public Integer getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(Integer customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public String getFeedbackListeners() {
		return feedbackListeners;
	}

	public void setFeedbackListeners(String feedbackListeners) {
		this.feedbackListeners = feedbackListeners;
	}
	
	public String getSquareRuleMsg() {
		return squareRuleMsg;
	}

	public void setSquareRuleMsg(String squareRuleMsg) {
		this.squareRuleMsg = squareRuleMsg;
	}

	@Override
	public void buildCommentMsg(final Integer userId, int sinceId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(sinceId, maxId, start, limit, jsonMap, new SerializableSinceIdListAdapter<HTWorldCommentDto>() {

			@Override
			public List<HTWorldCommentDto> getSerializables(
					RowSelection rowSelection) {
				return worldCommentDao.queryUserComment(userId, rowSelection);
			}

			@Override
			public List<HTWorldCommentDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return worldCommentDao.queryUserCommentByMaxId(userId, maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldCommentDao.queryUserCommentCountByMaxId(userId,maxId);
			}

			@Override
			public List<HTWorldCommentDto> getSerializableBySinceId(
					int sinceId, RowSelection rowSelection) {
				return worldCommentDao.queryUserCommentByMinId(userId, sinceId, rowSelection);
			}

			@Override
			public long getTotalBySinceId(int sinceId) {
				return worldCommentDao.queryUserCommentCountByMinId(userId,sinceId);
			}
			
		}, OptResult.JSON_KEY_COMMENTS, OptResult.JSON_KEY_TOTAL_COUNT, new OnBuildSinceSerializableListener() {

			@Override
			public void onBuild(List<? extends Serializable> list, long total) {
				worldCommentDao.updateUnreadComment(userId);
			}

			@Override
			public void onBuildByMaxId(List<? extends Serializable> list, long total) {}

			@Override
			public void onBuildBySinceId(List<? extends Serializable> list, long total) {}
			
		});
	}

	@Override
	public void buildLikedMsg(final Integer userId, int sinceId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(sinceId, maxId, start, limit, jsonMap, new SerializableSinceIdListAdapter<HTWorldLiked>() {

			@Override
			public List<HTWorldLiked> getSerializables(RowSelection rowSelection) {
				return worldLikedDao.queryUserLiked(userId, rowSelection);
			}

			@Override
			public List<HTWorldLiked> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return worldLikedDao.queryUserLikedByMaxId(userId, maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldLikedDao.queryUserLikedCountByMaxId(userId, maxId);
			}

			@Override
			public List<HTWorldLiked> getSerializableBySinceId(int sinceId,
					RowSelection rowSelection) {
				return worldLikedDao.queryUserLikedByMinId(userId, sinceId, rowSelection);
			}

			@Override
			public long getTotalBySinceId(int sinceId) {
				return worldLikedDao.queryUserLikedCountByMinId(userId,sinceId);
			}
			
		}, OptResult.JSON_KEY_LIKEDS, OptResult.JSON_KEY_TOTAL_COUNT, new OnBuildSinceSerializableListener() {

			@Override
			public void onBuild(List<? extends Serializable> list, long total) {
				worldLikedDao.updateUnreadUserLiked(userId);
			}

			@Override
			public void onBuildByMaxId(List<? extends Serializable> list, long total) {}

			@Override
			public void onBuildBySinceId(List<? extends Serializable> list, long total) {}
			
		});
	}
	
	@Override
	public void buildInteractMsg(final Integer userId, String maxDateStr, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		Date maxDate = null;
		if(!StringUtil.checkIsNULL(maxDateStr)) {
			maxDate = format.parse(maxDateStr);
		}
		buildSerializables("getInteractDate", maxDate, start, limit, jsonMap, new BaseSerializableListAdapter<HTWorldInteract>() {

			@Override
			public List<HTWorldInteract> getSerializables(RowSelection rowSelection) {
				List<HTWorldInteract> list = worldInteractDao.queryInteract(userId, rowSelection);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public List<HTWorldInteract> getSerializableByMaxId(
					Serializable maxSerializable, RowSelection rowSelection) {
				List<HTWorldInteract> list = worldInteractDao.queryInteract((Date)maxSerializable, userId, rowSelection);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public long getTotalByMaxId(Serializable maxSerializable) {
				return 0l;
			}
		}, OptResult.JSON_KEY_INTERACT, OptResult.JSON_KEY_TOTAL_COUNT, new BaseOnBuildSerializableListener() {
			
			@Override
			public void onBuildByMaxId(List<? extends Serializable> list,
					Serializable maxSerializable, long total) {
				
			}
			
			@Override
			public void onBuild(List<? extends Serializable> list,
					Serializable maxSerializable, long total) {
				worldInteractDao.updateUnReadInteract((Date)maxSerializable, userId);
			}
		});
	}

	@Override
	public void buildUnreadSysMsgCount(Integer userId, Map<String, Object> jsonMap) {
		long followCount = userConcernDao.queryUnCheckFollowCount(userId);
		long likedCount = worldLikedDao.queryUnCheckUserLikedCount(userId);
		long commentCount = msgCommentDao.queryUnCkCount(userId);
		long msgCount = sysMsgDao.queryUnCheckSysMsgCount(userId);
		long userMsgCount = userMsgRecipientBoxDao.queryUnReadMsgCount(userId);
		long atMsgCount = atDao.queryUnCheckCount(userId);
		jsonMap.put(OptResult.JSON_KEY_FOLLOW_COUNT, followCount);
		jsonMap.put(OptResult.JSON_KEY_LIKED_COUNT, likedCount);
		jsonMap.put(OptResult.JSON_KEY_COMMENT_COUNT, commentCount);
		jsonMap.put(OptResult.JSON_KEY_MSG_COUNT, msgCount);
		jsonMap.put(OptResult.JSON_KEY_USER_MSG_COUNT, userMsgCount);
		jsonMap.put(OptResult.JSON_KEY_AT_MSG_COUNT, atMsgCount);
	}
	
	public void buildUnreadSysMsgCountV2(Integer userId, Map<String, Object> jsonMap) {
		long followCount = userConcernDao.queryUnCheckFollowCount(userId);
		long msgCount = sysMsgDao.queryUnCheckSysMsgCount2(userId);
		long userMsgCount = userMsgRecipientBoxDao.queryUnReadMsgCount(userId);
		jsonMap.put(OptResult.JSON_KEY_FOLLOW_COUNT, followCount);
		jsonMap.put(OptResult.JSON_KEY_MSG_COUNT, msgCount);
		jsonMap.put(OptResult.JSON_KEY_USER_MSG_COUNT, userMsgCount);
	}
	
	
	@Override
	public Integer saveUserMsg(Integer senderId, Integer recipientId,
			String content, Integer msgType) throws Exception {
		if(senderId.equals(recipientId)) {
			throw new HTSException("不能向自己发送私信");
		}
		boolean shield = (userMsgShieldDao.queryShieldId(recipientId, senderId) != null ? true : false);
		final Integer id = keyGenService.generateId(KeyGenServiceImpl.USER_MSG_ID);
		userMsgDao.saveMsg(new UserMsg(id, new Date(), content, msgType));
		UserMsgBox msgBox = new UserMsgBox(id,senderId,recipientId,id);
		userMsgSendBoxDao.saveSendMsg(msgBox);
		if(!shield) { // 保存到接受者的收件箱并推送
			msgBox.setCk(Tag.FALSE);
			if(recipientId.equals(customerServiceId)) { // 表明这是通过app反馈页面发送的
				userMsgRecipientBoxDao.saveRecipientBox(msgBox);
				autoResponse(senderId, recipientId, content, msgType); // 自动回复
			} else {
				UserPushInfo pusnInfo = userInfoDao.queryUserPushInfoById(recipientId);
				// 系统发出或者2.9.5版本（用户间的私信不通过我们的服务器）之前的用户才会收到私信
				if(senderId.equals(customerServiceId) || pusnInfo.getVer() < Tag.VERSION_2_9_5) {
					if(senderId.equals(customerServiceId)) {
						userMsgRecipientBoxDao.updateRecipientCK(id, recipientId, senderId); // 更新指定用户发给系统私信未读标记
					}
					userMsgRecipientBoxDao.saveRecipientBox(msgBox);
					pushService.pushMiShuMessage(senderId, PushUtil.getShortTip(content),
							pusnInfo, new PushFailedCallback() {
	
						@Override
						public void onPushFailed(Exception e) {}
					});
					
				}
			}
		}
		return id;
	}
	
	/**
	 * 小秘书自动回复 
	 * 
	 * @param senderId
	 * @param recipientId
	 * @param content
	 * @param msgType
	 */
	private void autoResponse(final Integer senderId, final Integer recipientId,
			final String content, final Integer msgType) {
		pushService.getPushExecutor().execute(new Runnable() {

			@Override
			public void run() {
				try {
					String answer = openSearchService.searchAnswer(content);
					if(answer != null) {
						saveUserMsg(recipientId, senderId, answer, msgType);
					}
				} catch (Exception e) {
					logger.warn("xiao mi shu auto response error:" + e.getMessage());
				}
			}
		});
	}
	
	@Override
	public void buildUserConcernMsgIndex(final Integer userId, int maxId, int start,
			int limit, final Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgIndex>() {

			@Override
			public List<UserMsgIndex> getSerializables(RowSelection rowSelection) {
				Integer currentMaxId = userMsgDao.queryMaxMsgId();
				jsonMap.put(OptResult.JSON_KEY_MAX_ID, currentMaxId);
				return userMsgDao.queryConcernMsgIndex(currentMaxId, userId, rowSelection);
			}

			@Override
			public List<UserMsgIndex> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return userMsgDao.queryConcernMsgIndex(maxId, userId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				Long[] l = userMsgDao.queryConcernMsgIndexCount(maxId, userId);
				jsonMap.put("unreadTotal", l[1]);
				return l[0];
			}
			
		}, OptResult.JSON_KEY_MSG, OptResult.JSON_KEY_TOTAL_COUNT, new OnBuildSerializableListener() {

			@Override
			public void onBuild(List<? extends Serializable> list, int maxId, long total) {
				// 查询陌生私信数量
				long uneadTotal = (Long) jsonMap.get("unreadTotal");
				jsonMap.remove("unreadTotal");
				int maxMsgId = (Integer) jsonMap.get(OptResult.JSON_KEY_MAX_ID);
				long allUneadCount = userMsgDao.queryUnReadCount(maxMsgId, userId);
				jsonMap.put(OptResult.JSON_KEY_STRANGER_MSG_TOTAL, allUneadCount - uneadTotal);
				
			}

			@Override
			public void onBuildByMaxId(List<? extends Serializable> list, int maxId,
					long total) {
				jsonMap.put(OptResult.JSON_KEY_STRANGER_MSG_TOTAL, 0);
			}
			
		});
	}

	@Override
	public void buildUserUnConcernMsgIndex(final Integer userId, int maxId, int start,
			int limit, final Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgIndex>() {

			@Override
			public List<UserMsgIndex> getSerializables(RowSelection rowSelection) {
				return userMsgDao.queryUnConcernMsgIndex(userId, rowSelection);
			}

			@Override
			public List<UserMsgIndex> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return userMsgDao.queryUnConcernMsgIndex(maxId, userId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return userMsgDao.queryUnConcernMsgIndexCount(maxId, userId);
			}
			
		}, OptResult.JSON_KEY_MSG, OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	@Override
	public void buildUserMsg(final Integer userId, final Integer otherId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgDto>() {

			@Override
			public List<UserMsgDto> getSerializables(RowSelection rowSelection) {
				List<UserMsgDto> list = userMsgDao.queryUserMsg(userId, otherId, rowSelection);
				if(list.size() > 0) {
					userMsgRecipientBoxDao.updateRecipientCK(list.get(0).getId(), otherId, userId);
				}
				return list;
			}

			@Override
			public List<UserMsgDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return userMsgDao.queryUserMsg(maxId, userId, otherId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return userMsgDao.queryUserMsgCount(maxId, userId, otherId);
			}
			
		}, OptResult.JSON_KEY_MSG, OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	@Override
	public void updateChatValid(Integer maxId, Integer userId, Integer otherId) throws Exception {
		userMsgSendBoxDao.updateChatUnValid(maxId, userId, otherId);
		userMsgRecipientBoxDao.updateChatUnValid(maxId, otherId, userId);
	}

	@Override
	public void updateUserMsgValid(Integer contentId, Integer userId) throws Exception {
		Boolean isSender = false;
		Integer[] ids = userMsgRecipientBoxDao.querySenderIdByContentId(contentId);
		if(ids == null) {
			throw new HTSException("私信不存在");
		} else if(ids[0].equals(userId)) {
			isSender = true;
		} else if(ids[1].equals(userId)) {
			isSender = false;
		} else {
			throw new HTSException("权限不足",1);
		}
		
		if(isSender) {
			userMsgSendBoxDao.updateUnValid(contentId);
		} else {
			userMsgRecipientBoxDao.updateUnValid(contentId);
		}
	}
	
	@Override
	public void saveShield(Integer userId, Integer shieldId) throws Exception {
		userMsgShieldDao.saveShield(userId, shieldId);
	}

	@Override
	public void deleteShield(Integer userId, Integer shieldId) throws Exception {
		userMsgShieldDao.deleteShield(userId, shieldId);
	}
	
	
	@Override
	public void buildReceiveMsg(Integer minId, Integer userId, Integer otherId, Map<String, Object> jsonMap)
			throws Exception {
		List<UserMsgRecipientDto> list = userMsgDao.queryRecipientMsg(minId, otherId, userId);
		if(list.size() > 0) {
			Integer maxId = list.get(0).getId();
			userMsgRecipientBoxDao.updateRecipientCK(maxId, otherId, userId);
		}
		jsonMap.put(OptResult.JSON_KEY_MSG, list);
	}
	
	@Override
	public void saveSysMsg(Integer senderId, Integer recipientId,
			String content, Integer objType, Integer objId,
			String objMeta, String objMeta2, String thumbPath, Integer weight) throws Exception {
//		Integer id = keyGenService.generateId(KeyGenServiceImpl.OP_SYS_MSG_ID);
		sysMsgDao.saveMsg(new OpSysMsg(senderId, recipientId,
				new Date(), content, objType, objId, objMeta, objMeta2, thumbPath, weight));
//		return id;
	}
	
	@Override
	public void buildSysMsg(final Integer userId, int maxId,
			int start, int limit, Boolean trimTotal, final Boolean trimUserRecMsg,
			final Boolean clearUnCheck, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<OpSysMsgDto>() {

			@Override
			public List<OpSysMsgDto> getSerializables(
					RowSelection rowSelection) {
				List<OpSysMsgDto> list = sysMsgDao.queryMsg(userId, rowSelection);
				List<OpSysMsgDto> commonList = sysMsgCommonCacheDao.queryMsg(
						rowSelection.getLimit());
				unionSysMsg(list, commonList, userId, rowSelection.getLimit());
				updateSysMsgReadId(userId, list);
				return list;
			}

			@Override
			public List<OpSysMsgDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpSysMsgDto> list = sysMsgDao.queryMsg(maxId, userId, rowSelection);
				List<OpSysMsgDto> commonList = sysMsgCommonCacheDao.queryMsg(maxId,
						rowSelection.getLimit());
				unionSysMsg(list, commonList, userId, rowSelection.getLimit());
				updateSysMsgReadId(userId, list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0l;
			}

		}, OptResult.JSON_KEY_MSG, OptResult.JSON_KEY_TOTAL_COUNT); 
	}

	/**
	 * 更新系统消息已读id
	 * 
	 * @param userId
	 * @param list
	 */
	private void updateSysMsgReadId(Integer userId, List<OpSysMsgDto> list) {
		if(list == null || list.isEmpty()) {
			return;
		}
		
		Integer readId = msgUnreadDao.queryReadId(userId, UnreadType.SYSMSG);
		for(OpSysMsgDto dto : list) {
			if(dto.getId() > readId)
				dto.setIsNew(Tag.TRUE);
		}
		msgUnreadDao.updateReadId(userId, 
				list.get(0).getId(), UnreadType.SYSMSG);
	}
	
	private void unionSysMsg(List<OpSysMsgDto> userList, 
			List<OpSysMsgDto> commonList, Integer userId, Integer limit) {
		Integer maxId;

		if(commonList.isEmpty()) {
			return;
		}
		
		if(userList.isEmpty()) {
			if(commonList.isEmpty()) 
				return;
			else
				maxId = commonList.get(0).getId();
		} else {
			maxId = userList.get(0).getId() > commonList.get(0).getId() 
					? userList.get(0).getId() :  commonList.get(0).getId();
		}
		
		final Map<Integer, OpSysMsgDto> map = new HashMap<Integer, OpSysMsgDto>();
		for(OpSysMsgDto dto : commonList) {
			map.put(dto.getId(), dto);
		}
		sysMsgCommonDeletedDao.queryMsgId(maxId, userId, new RowCallback<Integer>() {

			@Override
			public void callback(Integer t) {
				if(map.containsKey(t)) {
					map.remove(t);
				}
			}
		});
		userList.addAll(map.values());
		Collections.sort(userList, new Comparator<OpSysMsgDto>() {

			@Override
			public int compare(OpSysMsgDto o1, OpSysMsgDto o2) {
				if(o1.getId() > o2.getId())
					return 1;
				else if(o1.getId() < o2.getId()) 
					return -1;
				return 0;
			}
			
		});
	}
	
	public static void main(String[] args) {
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(3);
		Set<Integer> set = new HashSet<Integer>(l);
		System.out.println(set);
	}

	
	@Override
	public void deleteById(Integer msgId) throws Exception {
		sysMsgDao.deleteMsgById(msgId);
	}

	@Override
	public Integer getValidMessageId(Integer senderId, Integer recipientId, Integer objType, 
			Integer objId) {
		return sysMsgDao.queryValidMessage(senderId, recipientId, objType, objId);
	}
	

	@Override
	public Integer getValidMessageId(Integer senderId, Integer recipientId,
			Integer objType, Integer objId, String objMeta2) {
		return sysMsgDao.queryValidMessage(senderId, recipientId, objType, objId, objMeta2);
	}

	@Override
	public void saveSquareRuleMsg(Integer userId) throws Exception {
		Integer sid = userMsgDao.querySenderId(customerServiceId, userId, Tag.USER_MSG_SQUARE_RULE);
		if(sid == null) {
			saveUserMsg(customerServiceId, userId, squareRuleMsg, Tag.USER_MSG_SQUARE_RULE);
		}
	}

	@Override
	public UserMsgStatus getMsgStatus(Integer userId, Integer joinId)
			throws Exception {
		UserMsgStatus status = userInfoDao.queryUserMsgStatus(userId);
		if(status == null) {
			throw new HTSException("用户不存在", 1);
		} else {
			Integer shield = userShieldDao.queryShieldId(userId, joinId) == null ? Tag.FALSE : Tag.TRUE;
			if(shield.equals(Tag.FALSE)) { // 我没屏蔽它的时候查询这个用户是否被屏蔽
				shield = userInfoDao.queryShield(joinId);
			}
			Integer ishield = userShieldDao.queryShieldId(joinId, userId) == null ? Tag.FALSE : Tag.TRUE;
			Integer isMutual = userConcernDao.queryIsMututal(userId, joinId);
			Integer iisMututal = userConcernDao.queryIsMututal(joinId, userId);
			String remarkMe = userRemarkDao.queryRemark(userId, joinId);
			if(isMutual == null) {
				isMutual = Tag.UN_CONCERN;
			}
			if(iisMututal == null) {
				iisMututal = Tag.UN_CONCERN;
			}
			Integer im = UserInfoUtil.checkIsImVersion(status.getVer()) ? Tag.TRUE : Tag.FALSE;
			status.setShield(shield);
			status.setIshield(ishield);
			status.setIm(im);
			status.setIsMututal(isMutual);
			status.setIisMututal(iisMututal);
			status.setRemarkMe(remarkMe);
		}
		return status;
	}
	
	@Override
	public void buildILikeOtherMsg(Integer maxId, final Integer userId, final Integer authorId, 
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception {
		final List<UserMsgLiked> list = new ArrayList<UserMsgLiked>();
		final UserInfoAvatar avatar = userInfoDao.queryUserAvatar(authorId);
		userInfoService.extractVerify(avatar);
		userInteractService.extractRemark(userId, avatar);
		if(avatar != null) {
			final RowCallback<UserMsgLiked> callback = new RowCallback<UserMsgLiked>() {
				
				@Override
				public void callback(UserMsgLiked t) {
					t.setUserId(t.getWorldAuthorId());
					t.setUserName(avatar.getUserName());
					t.setUserAvatar(avatar.getUserAvatar());
					t.setUserAvatarL(avatar.getUserAvatarL());
					t.setStar(avatar.getVerifyId());
					t.setVerifyName(avatar.getVerifyName());
					t.setVerifyIcon(avatar.getVerifyIcon());
					t.setRemark(avatar.getRemark());
					t.setPlatformVerify(avatar.getPlatformVerify());
					list.add(t);
				}
			};
			buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgLiked>() {

				@Override
				public List<UserMsgLiked> getSerializables(
						RowSelection rowSelection) {
					userMsgInteractDao.queryLikedMsg(userId, authorId, rowSelection, callback);
					return list;
				}

				@Override
				public List<UserMsgLiked> getSerializableByMaxId(int maxId,
						RowSelection rowSelection) {
						userMsgInteractDao.queryLikedMsg(maxId, userId, authorId, rowSelection, callback);
					return list;
				}

				@Override
				public long getTotalByMaxId(int maxId) {
					return 0;
				}
			}, OptResult.JSON_KEY_MSG, null);
		}
	}
	
	@Override
	public void buildOtherLikeMeMsg(Integer maxId, final Integer userId, final Integer authorId, 
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception {
		final List<UserMsgLiked> list = new ArrayList<UserMsgLiked>();
		final UserInfoAvatar avatar = userInfoDao.queryUserAvatar(userId);
		userInfoService.extractVerify(avatar);
		userInteractService.extractRemark(authorId, avatar);
		if(avatar != null) {
			final RowCallback<UserMsgLiked> callback = new RowCallback<UserMsgLiked>() {
				
				@Override
				public void callback(UserMsgLiked t) {
					t.setUserName(avatar.getUserName());
					t.setUserAvatar(avatar.getUserAvatar());
					t.setUserAvatarL(avatar.getUserAvatarL());
					t.setStar(avatar.getVerifyId());
					t.setVerifyName(avatar.getVerifyName());
					t.setVerifyIcon(avatar.getVerifyIcon());
					t.setRemark(avatar.getRemark());
					t.setPlatformVerify(avatar.getPlatformVerify());
					list.add(t);
				}
			};
			buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgLiked>() {

				@Override
				public List<UserMsgLiked> getSerializables(
						RowSelection rowSelection) {
					userMsgInteractDao.queryLikedMsg(userId, authorId, rowSelection, callback);
					return list;
				}

				@Override
				public List<UserMsgLiked> getSerializableByMaxId(int maxId,
						RowSelection rowSelection) {
						userMsgInteractDao.queryLikedMsg(maxId, userId, authorId, rowSelection, callback);
					return list;
				}

				@Override
				public long getTotalByMaxId(int maxId) {
					return 0;
				}
			}, OptResult.JSON_KEY_MSG, null);
		}
	}

	@Override
	public void buildLikedMsg2(Integer maxId, final Integer userId, final Integer authorId, 
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception {
		
	}
	
	@Override
	public void buildThumbnail(String userIdStr, String worldIdStr, 
			Integer userId, Integer likeOtherUID, Integer likeMeUID,
			Map<String, Object> jsonMap) throws Exception {
		if(!StringUtil.checkIsNULL(userIdStr)) {
			Integer[] uids = StringUtil.convertStringToIds(userIdStr);
			if(uids.length > 0) {
				List<UserInfoAvatar> ulist = userInfoDao.queryUserThumbnail(uids);
				userInfoService.extractVerify(ulist);
				userInteractService.extractRemark(userId, ulist);
				extractIsMututal(userId, ulist);
				jsonMap.put(OptResult.JSON_KEY_USER_INFO, ulist);
			}
		}
		if(!StringUtil.checkIsNULL(worldIdStr)) {
			Integer[] wids = StringUtil.convertStringToIds(worldIdStr);
			if(wids.length > 0) {
				jsonMap.put(OptResult.JSON_KEY_HTWORLD, 
						worldDao.queryWorldThumbnail(wids));
			}
		}
		
		if(likeOtherUID != null && likeOtherUID != 0) {
			long total = worldLikedDao.queryLikeOthersWorldCount(userId, likeOtherUID);
			jsonMap.put(OptResult.JSON_KEY_LIKE_OTHER_COUNT, total);
		}
		
		if(likeMeUID != null && likeMeUID != 0) {
			long total = worldLikedDao.queryLikeOthersWorldCount(likeMeUID, userId);
			jsonMap.put(OptResult.JSON_KEY_LIKE_ME_COUNT, total);
		}
	}
	
	private void extractIsMututal(Integer userId, final List<UserInfoAvatar> users) {
		if(users != null && users.size() > 0) {
			final Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
			Integer[] uids = new Integer[users.size()];
			for(int i = 0; i < users.size(); i++) {
				Integer uid = users.get(i).getId();
				idxMap.put(uid, i);
				uids[i] = uid;
			}
			userConcernDao.queryIsMututal(uids, userId, new RowCallback<UserIsMututal>() {

				@Override
				public void callback(UserIsMututal t) {
					Integer uid = t.getUserId();
					Integer idx = idxMap.get(uid);
					users.get(idx).setIsMututal(t.getIsMututal());
				}
			});
			userConcernDao.queryIsMututal(userId, uids, new RowCallback<UserIsMututal>() {

				@Override
				public void callback(UserIsMututal t) {
					Integer uid = t.getConcernId();
					Integer idx = idxMap.get(uid);
					users.get(idx).setIisMututal(t.getIsMututal());
				}
			});
		}
		
	}

	@Override
	public void deleteSysMsg(Integer recipientId, Integer msgId)
			throws Exception {
		Integer mrid = sysMsgDao.queryRecipientId(msgId);
		if(mrid != null && mrid.equals(recipientId)) {
			sysMsgDao.deleteMsgById(msgId);
		}
	}

	@Override
	public void buildLikeMeMsg(Integer maxId, 
			final Integer userId,final Integer limit, 
			final Map<String, Object> jsonMap) throws Exception {
		List<HTWorldLikeMe> likeMeList = null;
		if(maxId == 0) {
			Date minDate = TimeUtil.getSubDateFromNow(likeMeGroupInterval);
			Integer minId= worldLikedDao.queryMinIdByMinDate(userId, minDate);
			if(minId > 0) {
				likeMeList = worldLikedDao.queryLikeMeByGroup(minId, userId); // 根据最小id查询喜欢过我的人的分组列表
				
				extractLikeMeWorldThumb(minId, userId, likeMeList); // 根据最小id获取被点过赞所有缩略图
				
				if(likeMeList.size() < limit) { // 不足一页，加载更多数据
					List<HTWorldLikeMe> noThumbsList = worldLikedDao.queryLikeMe(minId-1, 
							userId, limit-likeMeList.size());
					likeMeList.addAll(noThumbsList);
				} else { // 大于或等于一页, 下一页以最小id作为起始id
					jsonMap.put(OptResult.JSON_KEY_MAX_ID, minId);
				}
				
				jsonMap.put(OptResult.JSON_KEY_INTERVAL, likeMeGroupInterval);
				
			} else {
				likeMeList = worldLikedDao.queryLikeMe(userId, limit);
			}
			
		} else {
			likeMeList = worldLikedDao.queryLikeMe(maxId, userId, limit);
		}
		
		userInfoService.extractVerify(likeMeList);
		jsonMap.put(OptResult.JSON_KEY_MSG, likeMeList);
		
	}
	
	@Override
	public void buildLikeMeMsgWithoutGroup(Integer maxId, Integer userId, 
			Integer limit, final Map<String, Object> jsonMap) throws Exception {
		userInfoDao.queryUserInfoDtoById(userId);
		List<HTWorldLikeMe> likeMeList = null;
		if(maxId == 0) {
			likeMeList = worldLikedDao.queryLikeMe(userId, limit);
		} else {
			likeMeList = worldLikedDao.queryLikeMe(maxId, userId, limit);
		}
		extractRelate(userId, likeMeList);
		userInfoService.extractVerify(likeMeList);
		jsonMap.put(OptResult.JSON_KEY_MSG, likeMeList);
	}

	public void extractRelate(Integer userId, List<HTWorldLikeMe> list) {
		if(list == null || list.size() == 0) {
			return;
		}
		
		UserInfoDto user = userInfoDao.queryUserInfoDtoById(userId);
		String city = StringUtil.checkIsNULL(user.getCity()) ? null 
				: StringUtil.subSHIFromCity(user.getCity());
		String province = StringUtil.checkIsNULL(user.getProvince()) ? null
				: StringUtil.subShengFromProvince(user.getProvince());

		Map<Integer, HTWorldLikeMeRelate> relMap = new HashMap<Integer, HTWorldLikeMeRelate>();
		
		for(HTWorldLikeMe lm : list) {
			Integer relId = lm.getId();
			if(relMap.containsKey(relId)) {
				lm.setRelate(relMap.get(relId));
				
			} else {
				if(city!= null && !StringUtil.checkIsNULL(lm.getCity()) && lm.getCity().contains(city)) {
					HTWorldLikeMeRelate rel = new HTWorldLikeMeRelate(Tag.LIKE_ME_RELATE_CITY, 
							lm.getProvince() + " " + lm.getCity(), 0);
					lm.setRelate(rel);
					relMap.put(relId, rel);
					continue;
				}
				
				if(province!=null && !StringUtil.checkIsNULL(lm.getProvince()) && lm.getProvince().contains(province)) {
					HTWorldLikeMeRelate rel = new HTWorldLikeMeRelate(Tag.LIKE_ME_RELATE_PROVINCE, 
							lm.getProvince(), 0);
					lm.setRelate(rel);
					relMap.put(relId, rel);
					continue;
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @param minId
	 * @param likeMeList
	 * @param limit
	 */
	private void extractLikeMeWorldThumb(Integer minId, Integer authorId, 
			final List<HTWorldLikeMe> likeMeList) {
		final Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
		for(int i = 0; i < likeMeList.size(); i++) {
			Integer uid = likeMeList.get(i).getUserId();
			idxMap.put(uid, i);
		}
		worldLikedDao.queryLikeMeWorld(minId, authorId, 
				new RowCallback<HTWorldLikeMeThumb>() {

					@Override
					public void callback(HTWorldLikeMeThumb t) {
						Integer uid = t.getUserId();
						Integer idx = idxMap.get(uid);
						if(idx != null) {
							HTWorldLikeMe likeMe = likeMeList.get(idx);
							if(likeMe.getTitleThumbs() == null) {
								likeMe.setTitleThumbs(new ArrayList<HTWorldLikeMeThumb>());
							}
							likeMe.getTitleThumbs().add(t);
						}
					}
			
		});
	}

	@Override
	public List<PushStatus> saveAtMsgs(String atIdsStr, String atNamesStr , Set<Integer> rejectIds, Boolean push,
			Integer userId, Integer objType, Integer objId, Integer worldId, String content) throws Exception {
		List<PushStatus> statusList;
		MsgAt[] msgIdxs;
		List<MsgAt> msgs;
		Set<Integer> shieldSet;
		Set<Integer> noAcceptAtSet;
		Integer[] atIds;
		String[] atNames;

		if(StringUtil.checkIsNULL(atIdsStr) || StringUtil.checkIsNULL(atNamesStr)) {
			return null;
		}
		
		atIds = StringUtil.convertStringToIds(atIdsStr);
		atNames = StringUtil.convertStringToStrs(atNamesStr);

		if(atIds.length == 0 || atNames.length == 0) {
			return null;
		}
		
		shieldSet = userShieldDao.queryWhoShieldMe(atIds, userId);
		noAcceptAtSet = userInfoDao.queryNotAcceptAtUIds(atIds);
		
		if(atIds.length != atNames.length) {
			throw new HTSException("at ids length must be equals at names length");
		}
		
		statusList = new ArrayList<PushStatus>();
		msgIdxs = new MsgAt[atIds.length];
		msgs = new ArrayList<MsgAt>();
		
		for(int i = 0; i < atIds.length;i++) {
			
			Integer id;
			MsgAt msg;
			PushStatus status;
			int accept;
			int shield;
			
			
			id = keyGenService.generateId(KeyGenServiceImpl.MSG_AT_ID);
			msg = new MsgAt();
			
			msg.setId(id);
			msg.setUserId(userId);
			msg.setAtId(atIds[i]);
			msg.setObjId(objId);
			msg.setObjType(objType);
			msg.setWorldId(worldId);
			msg.setAtName(atNames[i]);
			msg.setContent(content);
			msgIdxs[i] = msg;
			
			if(!userId.equals(atIds[i]) || rejectIds.contains(atIds[i])) { // 不能自己at自己
				status = new PushStatus();
				accept = noAcceptAtSet.contains(atIds[i]) ? Tag.FALSE : Tag.TRUE;
				shield = shieldSet.contains(atIds[i]) ? Tag.TRUE : Tag.FALSE;
				
				status.setId(objId);
				status.setAccept(accept);
				status.setUserId(atIds[i]);
				status.setShield(shield);
				statusList.add(status);
				msgs.add(msg);
			}
		}
		
		switch (objType) {
		case Tag.AT_TYPE_WORLD:
			atWorldDao.saveAtMsgs(msgIdxs);
			break;
			
		case Tag.AT_TYPE_COMMENT:
			atCommentDao.saveAtMsgs(msgIdxs);
			break;
			
		default:
			break;
		}
		
		atDao.saveAtMsgs(msgs);
		
		if(push && statusList != null) {
			for(PushStatus status : statusList) {
				pushService.pushAtMsg(userId, status.getUserId(),
						content,status.getAccept(), status.getShield());
			}
		}
		
		return statusList;
	}
	
	@Override
	public List<PushStatus> saveAtMsgs(String atIdsStr, String atNamesStr, 
			Boolean push, Integer userId, Integer objType, Integer objId, Integer worldId,
			String content) throws Exception {
		Set<Integer> rejectIds = new HashSet<Integer>();
		return saveAtMsgs(atIdsStr, atNamesStr, rejectIds, push, userId, objType, objId, worldId, content);
	}

	@Override
	public Integer queryAtId(Integer objType, Integer objId, Integer index, 
			String atName) throws Exception {
		Integer atId;
		MsgAtId msg;
		
		atId = 0;
		msg = null;
		
		switch (objType) {
		case Tag.AT_TYPE_WORLD:
			msg = atWorldDao.queryAtId(objId, index);
			break;
		case Tag.AT_TYPE_COMMENT:
			msg = atCommentDao.queryAtId(objId, index);
			break;
		default:
			break;
		}
		
		if(msg != null && atName != null && atName.equals(msg.getAtName()))
			atId = msg.getAtId();
		
		return atId;
	}

	@Override
	public Integer queryUnReadAtCount(Integer atId) throws Exception {
		return atDao.queryUnCheckCount(atId).intValue();
	}

	@Override
	public void updateAtCK(Integer atId) throws Exception {
		atDao.updateCK(atId);
	}
	
	@Override
	public void buildAtMsg(final Integer atId, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<MsgAtDto>() {

			@Override
			public List<MsgAtDto> getSerializables(RowSelection rowSelection) throws Exception {
				atDao.updateCK(atId);
				return atDao.queryMsg(atId, rowSelection);
			}

			@Override
			public List<MsgAtDto> getSerializableByMaxId(int maxId, RowSelection rowSelection) {
				return atDao.queryMsg(maxId, atId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) throws Exception {
				return 0;
			}
		}, OptResult.JSON_KEY_MSG, null);
	}
	
	@Override
	public void buildCommentMsg(final Integer worldAuthorId, Integer maxId, 
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<MsgCommentDto>() {

			@Override
			public List<MsgCommentDto> getSerializables(RowSelection rowSelection) throws Exception {
				msgCommentDao.updateCK(worldAuthorId);
				return msgCommentDao.queryMsg(worldAuthorId, rowSelection);
			}

			@Override
			public List<MsgCommentDto> getSerializableByMaxId(int maxId, RowSelection rowSelection) {
				return msgCommentDao.queryMsg(maxId, worldAuthorId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) throws Exception {
				return 0;
			}
			
		}, OptResult.JSON_KEY_MSG, null);
	}


}

