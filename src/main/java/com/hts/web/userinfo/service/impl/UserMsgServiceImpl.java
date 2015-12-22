package com.hts.web.userinfo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.hts.web.base.HTSErrorCode;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.MsgAt;
import com.hts.web.common.pojo.MsgAtDto;
import com.hts.web.common.pojo.MsgAtId;
import com.hts.web.common.pojo.MsgCommentDto;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.pojo.UserAvatar;
import com.hts.web.common.pojo.UserInfoAvatar;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserIsMututal;
import com.hts.web.common.pojo.UserMsg;
import com.hts.web.common.pojo.UserMsgBox;
import com.hts.web.common.pojo.UserMsgConver;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.common.pojo.UserMsgLiked;
import com.hts.web.common.pojo.UserMsgLikedRelate;
import com.hts.web.common.pojo.UserMsgStatus;
import com.hts.web.common.pojo.UserMsgUnreadCount;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.operations.dao.SysMsgCommonCacheDao;
import com.hts.web.operations.dao.SysMsgCommonDao;
import com.hts.web.operations.dao.SysMsgCommonDeletedDao;
import com.hts.web.operations.dao.SysMsgDao;
import com.hts.web.push.service.PushService;
import com.hts.web.stat.StatKey;
import com.hts.web.stat.service.StatService;
import com.hts.web.userinfo.dao.MsgAtCommentDao;
import com.hts.web.userinfo.dao.MsgAtDao;
import com.hts.web.userinfo.dao.MsgAtWorldDao;
import com.hts.web.userinfo.dao.MsgCommentDao;
import com.hts.web.userinfo.dao.MsgLikedDao;
import com.hts.web.userinfo.dao.MsgUnreadDao;
import com.hts.web.userinfo.dao.MsgUnreadDao.UnreadType;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserMsgConversationDao;
import com.hts.web.userinfo.dao.UserMsgDao;
import com.hts.web.userinfo.dao.UserMsgRecipientBoxDao;
import com.hts.web.userinfo.dao.UserMsgSendBoxDao;
import com.hts.web.userinfo.dao.UserMsgShieldDao;
import com.hts.web.userinfo.dao.UserRemarkDao;
import com.hts.web.userinfo.dao.UserShieldDao;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.userinfo.service.UserMsgService;
import com.hts.web.ztworld.dao.HTWorldDao;

/**
 * 用户消息业务逻辑访问对象
 * 
 * @author ztj 2015-11-04 2015-11-05
 * 
 */
@Service("HTSUserMsgService")
public class UserMsgServiceImpl extends BaseServiceImpl implements
		UserMsgService {
	
	private static Logger logger = Logger.getLogger(UserMsgServiceImpl.class);
	
	@Autowired
	private KeyGenService keyGenService;
	
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
	private UserInfoService userInfoService;
	
	@Autowired
	private UserShieldDao userShieldDao;
	
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
	private SysMsgCommonDao sysMsgCommonDao;
	
	@Autowired
	private MsgUnreadDao msgUnreadDao;
	
	@Autowired
	private UserMsgConversationDao msgConversationDao;
	
	@Autowired
	private MsgLikedDao msgLikedDao;
	
	@Autowired
	private StatService statService;
	
	@Value("${msg.squareRuleMsg}")
	private String welcomeMsg ;
	
	private Integer welcomeMsgId = 1;
	
	@Value("${push.customerServiceId}")
	private Integer customerServiceId = 13;
	
	@Value("${push.feedbackListeners}")
	private String feedbackListeners;

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
	
	public String getWelcomeMsg() {
		return welcomeMsg;
	}

	public void setWelcomeMsg(String welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	public Integer getWelcomeMsgId() {
		return welcomeMsgId;
	}

	public void setWelcomeMsgId(Integer welcomeMsgId) {
		this.welcomeMsgId = welcomeMsgId;
	}

	@Override
	public void buildUnreadSysMsgCount(Integer userId, Map<String, Object> jsonMap) throws Exception{
		UserMsgUnreadCount cnt = queryUnreadCountInfo(userId);
		
		long followCount = userConcernDao.queryUnCheckFollowCount(userId);
		long likedCount = cnt.getLikeCount();
		long commentCount = cnt.getCommentCount();
		
		long atMsgCount = cnt.getAtCount();
		long msgCount = cnt.getSysmsgCount() 
				+ sysMsgCommonCacheDao.higherCount(cnt.getSysmsgId());
		long userMsgCount = cnt.getUmsgCount();
		
		jsonMap.put(OptResult.JSON_KEY_FOLLOW_COUNT, followCount);
		jsonMap.put(OptResult.JSON_KEY_LIKED_COUNT, likedCount);
		jsonMap.put(OptResult.JSON_KEY_COMMENT_COUNT, commentCount);
		jsonMap.put(OptResult.JSON_KEY_MSG_COUNT, msgCount);
		jsonMap.put(OptResult.JSON_KEY_USER_MSG_COUNT, userMsgCount);
		jsonMap.put(OptResult.JSON_KEY_AT_MSG_COUNT, atMsgCount);
	}
	
	/**
	 * 获取未读消息数量信息
	 * 
	 * @param userId
	 * @return
	 */
	private UserMsgUnreadCount queryUnreadCountInfo(Integer userId) {
		UserMsgUnreadCount cnt = msgUnreadDao.queryCount(userId);
		if(cnt == null) {
			msgUnreadDao.saveUnRead(userId);
			cnt = new UserMsgUnreadCount(userId, 0, 0, 0, 0, 0, 0, 0);
		}
		return cnt;
	}
	
	@Override
	public Integer saveUserMsg(Integer senderId, Integer recipientId,
			String content) throws Exception {
		if(senderId.equals(recipientId)) {
			throw new HTSException(HTSErrorCode.PARAMATER_ERR);
		}
		
		Integer id = keyGenService.generateId(KeyGenServiceImpl.USER_MSG_ID);
		userMsgDao.saveMsg(new UserMsg(id, content));
		UserMsgBox msgBox = new UserMsgBox(id, senderId, recipientId);
		userMsgSendBoxDao.saveSendMsg(msgBox);
		userMsgRecipientBoxDao.saveRecipientMsg(msgBox);
		msgUnreadDao.addCount(recipientId, UnreadType.UMSG);
		updateUserMsgConversation(senderId, recipientId, id);
		
		if(recipientId.equals(customerServiceId)) {
			if(senderId != 0)
				autoResponse(senderId, recipientId, content);
		} else {
			UserPushInfo pusnInfo = userInfoDao.queryUserPushInfoById(recipientId);
			pushService.pushMiShuMessage(senderId, content, pusnInfo);
		}
		return id;
	}
	
	/**
	 * 更新私信对话
	 * 
	 * @param userId
	 * @param otherId
	 * @param contentId
	 */
	private void updateUserMsgConversation(Integer userId, Integer otherId, Integer contentId) {
		// 更新我和对方的会话状态
		if(msgConversationDao.sendMsg(userId, otherId, contentId) == 0) {
			msgConversationDao.saveConver(
					new UserMsgConver(userId, otherId, contentId,
							0, UserMsgConversationDao.MSG_TYPE_SEND));
		}
		
		// 更新对方和我的对话
		if(msgConversationDao.receiveMsg(otherId, userId, contentId) == 0) {
			msgConversationDao.saveConver(
					new UserMsgConver(otherId, userId, contentId, 1, UserMsgConversationDao.MSG_TYPE_SEND));
		}
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
			final String content) {
		pushService.getPushExecutor().execute(new Runnable() {

			@Override
			public void run() {
				try {
					String answer = openSearchService.searchAnswer(content);
					if(answer != null) {
						Integer id = saveUserMsg(recipientId, senderId, answer);
						msgConversationDao.sendMsg(recipientId, senderId, id);
					}
				} catch (Exception e) {
					logger.warn("xiao mi shu auto response error:" + e.getMessage());
				}
			}
		});
	}
	

	@Override
	public void buildUserMsg(final Integer userId, final Integer otherId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserMsgDto>() {

			@Override
			public List<UserMsgDto> getSerializables(RowSelection rowSelection) {
				List<UserMsgDto> list = queryUserMsg(userId, otherId, 0, rowSelection);
				msgConversationDao.clearUnreadCount(userId, otherId);
				if(!list.isEmpty()) {
					msgUnreadDao.clearCount(userId, list.get(0).getId(), UnreadType.UMSG);
				}
				return list;
			}

			@Override
			public List<UserMsgDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return queryUserMsg(userId, otherId, maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0l;
			}
			
		}, OptResult.JSON_KEY_MSG, null);
	}
	
	@Override
	public List<UserMsgDto> queryUserMsg(Integer userId, Integer otherId, 
			Integer maxId, RowSelection rowSelection) {
		final List<UserMsgDto> senderList = new ArrayList<UserMsgDto>();
		final List<UserMsgDto> recipientList = new ArrayList<UserMsgDto>();
		final UserAvatar userInfo = userInfoDao.queryUserAvatar(userId);
		final UserAvatar otherInfo = userInfoDao.queryUserAvatar(otherId);
		
		RowCallback<UserMsgDto> senderCallback = new RowCallback<UserMsgDto>() {

			@Override
			public void callback(UserMsgDto t) {
				t.setSenderInfo(userInfo);
				t.setRecipientInfo(otherInfo);
				senderList.add(t);
			}
		};
		
		RowCallback<UserMsgDto> recipientCallback = new RowCallback<UserMsgDto>() {

			@Override
			public void callback(UserMsgDto t) {
				t.setSenderInfo(otherInfo);
				t.setRecipientInfo(userInfo);
				recipientList.add(t);
			}
		};
		
		if(maxId == 0) {
			userMsgSendBoxDao.querySendMsg(userId, otherId, rowSelection, senderCallback);
			userMsgRecipientBoxDao.queryRecipientMsg(userId, otherId, rowSelection, recipientCallback);
		} else {
			userMsgSendBoxDao.querySendMsg(maxId, userId, otherId, rowSelection, senderCallback);
			userMsgRecipientBoxDao.queryRecipientMsg(maxId, userId, otherId, rowSelection, recipientCallback);
		}

		unionUserMsg(senderList, recipientList, rowSelection.getLimit());
		
		return senderList;
	}

	/**
	 * 合并发送消息和收取的消息
	 * 
	 * @param senderList
	 * @param recipientList
	 * @param limit
	 */
	private void unionUserMsg(List<UserMsgDto> senderList,
			List<UserMsgDto> recipientList, Integer limit) {

		senderList.addAll(recipientList);
		Collections.sort(senderList, new Comparator<UserMsgDto>() {

			@Override
			public int compare(UserMsgDto o1, UserMsgDto o2) {
				if(o1.getId() < o2.getId())
					return 1;
				else if(o1.getId() > o2.getId()) 
					return -1;
				return 0;
			}
		});
		if(senderList.size() > limit) {
			for(int i = limit; i < senderList.size(); i++) {
				senderList.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public void delUserMsg(Integer contentId, Integer userId,
			Integer otherId) throws Exception {
		if(otherId == null || otherId == 0) {
			otherId = customerServiceId;
		}
		
		if(userMsgRecipientBoxDao.queryRecipientId(userId, 
				otherId, contentId) > 0) {
			userMsgRecipientBoxDao.deleteRecipientMsg(userId, otherId, contentId);
			
			// 查询对方的发件箱里还有没有这条信息,没有的话删除原始信息
			if(userMsgSendBoxDao.queryContentId(otherId, userId, contentId) < 0 && 
					!contentId.equals(welcomeMsgId))
				userMsgDao.deleteMsg(contentId);
			
		} else {
			userMsgSendBoxDao.deleteSendMsg(userId, otherId, contentId);
			
			// 查询对方的收件箱里还有没有这条信息,没有的话删除原始信息
			if(userMsgRecipientBoxDao.queryContentId(otherId, userId, contentId) < 0 && 
					!contentId.equals(welcomeMsgId))
				userMsgDao.deleteMsg(contentId);
		}
	}
	
	
	@Override
	public long queryUserMsgCount(Integer userId, Integer otherId, Integer maxId) {
		return userMsgRecipientBoxDao.queryRecipientCount(userId, otherId, maxId) +
				userMsgSendBoxDao.querySendCount(userId, otherId, maxId);
	}

	
	@Override
	public void saveUserWelcomeMsg(Integer userId) throws Exception {

		// 查询接收过欢迎消息,没有的话接收一条
		if(userMsgRecipientBoxDao.queryContentId(userId, 
				customerServiceId, welcomeMsgId) < 0) {
			
			userMsgRecipientBoxDao.saveRecipientMsg(new UserMsgBox(welcomeMsgId,
					customerServiceId, userId));
		}
	}
	
	@Override
	public void pushWelcomeMsg(Integer userId) throws Exception {
		UserPushInfo pushInfo = userInfoDao.queryUserPushInfoById(userId);
		pushService.pushMiShuMessage(customerServiceId, welcomeMsg, pushInfo);
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
				updateSysMsgIsNew(userId, list);
				if(!list.isEmpty())
					msgUnreadDao.clearCount(userId, list.get(0).getId(), UnreadType.SYSMSG);
				return list;
			}

			@Override
			public List<OpSysMsgDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpSysMsgDto> list = sysMsgDao.queryMsg(maxId, userId, rowSelection);
				List<OpSysMsgDto> commonList = sysMsgCommonCacheDao.queryMsg(maxId,
						rowSelection.getLimit());
				unionSysMsg(list, commonList, userId, rowSelection.getLimit());
				updateSysMsgIsNew(userId, list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0l;
			}

		}, OptResult.JSON_KEY_MSG, null); 
	}

	/**
	 * 更新系统消息已读id
	 * 
	 * @param userId
	 * @param list
	 */
	private void updateSysMsgIsNew(Integer userId, List<OpSysMsgDto> list) {
		if(list == null || list.isEmpty()) {
			return;
		}
		
		Integer readId = msgUnreadDao.queryReadId(userId, UnreadType.SYSMSG);
		for(OpSysMsgDto dto : list) {
			if(dto.getId() > readId)
				dto.setIsNew(Tag.TRUE);
		}
	}
	
	/**
	 * 合并用户收到的系统消息和公用系统消息
	 * 
	 * @param userList
	 * @param commonList
	 * @param userId
	 * @param limit
	 */
	private void unionSysMsg(List<OpSysMsgDto> userList, 
			List<OpSysMsgDto> commonList, Integer userId, Integer limit) {
		
		if(commonList.isEmpty())
			return;
		
		final Map<Integer, OpSysMsgDto> map = new HashMap<Integer, OpSysMsgDto>();
		for(OpSysMsgDto dto : commonList) {
			map.put(dto.getId(), dto);
		}
		sysMsgCommonDeletedDao.queryMsgId(commonList.get(0).getId(),
				commonList.get(commonList.size()-1).getId(), 
				userId, new RowCallback<Integer>() {

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
				if(o1.getId() < o2.getId())
					return 1;
				else if(o1.getId() > o2.getId()) 
					return -1;
				return 0;
			}
		});
		if(userList.size() > limit) {
			for(int i = limit; i < userList.size(); i++) {
				userList.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public UserMsgStatus getMsgStatus(Integer userId, Integer joinId)
			throws Exception {
		UserMsgStatus status = userInfoDao.queryUserMsgStatus(userId);
		if(status == null) {
			throw new HTSException(HTSErrorCode.USER_NOT_EXISTS);
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
		if(sysMsgCommonDao.queryMsgId(msgId) > 0) {
			sysMsgCommonDeletedDao.saveDeleted(recipientId, msgId);
		} else {
			sysMsgDao.deleteById(recipientId, msgId);
		}
	}

	@Override
	public void buildLikeMeMsg(Integer maxId, 
			final Integer userId,final Integer limit, 
			final Map<String, Object> jsonMap) throws Exception {
		List<UserMsgLiked> likeMeList = null;
		jsonMap.put(OptResult.JSON_KEY_MSG, likeMeList);
		
	}
	
	@Override
	public void buildLikeMeMsgWithoutGroup(Integer maxId, Integer userId, 
			Integer limit, final Map<String, Object> jsonMap) throws Exception {
		List<UserMsgLiked> likeMeList = null;
		if(maxId == 0) {
			likeMeList = msgLikedDao.queryMsg(userId, limit);
			if(likeMeList != null && !likeMeList.isEmpty()) {
				msgUnreadDao.clearCount(userId, likeMeList.get(0).getId(), UnreadType.LIKE);
			}
		} else {
			likeMeList = msgLikedDao.queryMsg(maxId, userId, limit);
		}
		extractRelate(userId, likeMeList);
		userInfoService.extractVerify(likeMeList);
		jsonMap.put(OptResult.JSON_KEY_MSG, likeMeList);
	}

	public void extractRelate(Integer userId, List<UserMsgLiked> list) {
		if(list == null || list.size() == 0) {
			return;
		}
		
		UserInfoDto user = userInfoDao.queryUserInfoDtoById(userId);
		String city = StringUtil.checkIsNULL(user.getCity()) ? null 
				: StringUtil.subSHIFromCity(user.getCity());
		String province = StringUtil.checkIsNULL(user.getProvince()) ? null
				: StringUtil.subShengFromProvince(user.getProvince());

		Map<Integer, UserMsgLikedRelate> relMap = new HashMap<Integer, UserMsgLikedRelate>();
		
		for(UserMsgLiked lm : list) {
			Integer relId = lm.getId();
			if(relMap.containsKey(relId)) {
				lm.setRelate(relMap.get(relId));
				
			} else {
				if(city!= null && !StringUtil.checkIsNULL(lm.getCity()) && lm.getCity().contains(city)) {
					UserMsgLikedRelate rel = new UserMsgLikedRelate(Tag.LIKE_ME_RELATE_CITY, 
							lm.getProvince() + " " + lm.getCity(), 0);
					lm.setRelate(rel);
					relMap.put(relId, rel);
					continue;
				}
				
				if(province!=null && !StringUtil.checkIsNULL(lm.getProvince()) && lm.getProvince().contains(province)) {
					UserMsgLikedRelate rel = new UserMsgLikedRelate(Tag.LIKE_ME_RELATE_PROVINCE, 
							lm.getProvince(), 0);
					lm.setRelate(rel);
					relMap.put(relId, rel);
					continue;
				}
			}
		}
	}
	
	@Override
	public List<PushStatus> saveAtMsgs(String atIdsStr, String atNamesStr , Set<Integer> rejectIds, Boolean push,
			Integer userId, Integer objType, Integer objId, Integer worldId, String content) throws Exception {
		List<PushStatus> statusList;
		MsgAt[] msgIdxs;
		List<MsgAt> msgs;
		List<Integer> msgIds;
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
			throw new HTSException(HTSErrorCode.PARAMATER_ERR,
					"at ids length must be equals at names length");
		}
		
		statusList = new ArrayList<PushStatus>();
		msgIdxs = new MsgAt[atIds.length];
		msgs = new ArrayList<MsgAt>();
		msgIds = new ArrayList<Integer>();
		
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
			
			if(!userId.equals(atIds[i]) && !rejectIds.contains(atIds[i])) { // 不能自己和被拒绝的人不能被at
				status = new PushStatus();
				accept = noAcceptAtSet.contains(atIds[i]) ? Tag.FALSE : Tag.TRUE;
				shield = shieldSet.contains(atIds[i]) ? Tag.TRUE : Tag.FALSE;
				
				status.setId(objId);
				status.setAccept(accept);
				status.setUserId(atIds[i]);
				status.setShield(shield);
				statusList.add(status);
				msgs.add(msg);
				msgIds.add(atIds[i]);
			}
		}
		
		switch (objType) {
		case Tag.AT_TYPE_WORLD:
			statService.incPV(StatKey.USER_MSG_AT_WORLD);
			atWorldDao.saveAtMsgs(msgIdxs);
			break;
			
		case Tag.AT_TYPE_COMMENT:
			statService.incPV(StatKey.USER_MSG_AT_COMMENT);
			atCommentDao.saveAtMsgs(msgIdxs);
			break;
			
		default:
			break;
		}
		
		atDao.saveAtMsgs(msgs);
		addAtUnreadCount(msgs);
		
		if(push && statusList != null) {
			for(PushStatus status : statusList) {
				pushService.pushAtMsg(userId, status.getUserId(),
						content,status.getAccept(), status.getShield());
			}
		}
		
		return statusList;
	}
	
	/**
	 * 增加At未读消息数量
	 * 
	 * @param msgs
	 * @author lynch 2015-10-31
	 */
	private void addAtUnreadCount(List<MsgAt> msgs) {
		if(!msgs.isEmpty()) {
			Integer[] atIds = new Integer[msgs.size()];
			for(int i = 0; i < atIds.length; i++) {
				atIds[i] = msgs.get(i).getAtId();
			}
			msgUnreadDao.addCounts(atIds, UnreadType.AT);
		}
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
		
		if(msg != null && atName != null) {
			if(atName.equals(msg.getAtName()))
				atId = msg.getAtId();
			else {
				// 只要第一个字符相等也pass
				int len = msg.getAtName().length();
				if(len > 0 && atName.substring(0, 1).equals(
						msg.getAtName().substring(0, 1)))
						atId = msg.getAtId();
			}
		}
		return atId;
	}
	
	@Override
	public void buildAtMsg(final Integer atId, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<MsgAtDto>() {

			@Override
			public List<MsgAtDto> getSerializables(RowSelection rowSelection) throws Exception {
				List<MsgAtDto> list = atDao.queryMsg(atId, rowSelection);
				if(list != null && !list.isEmpty()) {
					msgUnreadDao.clearCount(atId, list.get(0).getId(), UnreadType.AT);
				}
				return list;
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
				List<MsgCommentDto> list = msgCommentDao.queryMsg(worldAuthorId, rowSelection);
				if(!list.isEmpty()) {
					msgUnreadDao.clearCount(worldAuthorId, list.get(0).getId(), UnreadType.COMMENT);
				}
				return list;
				
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

