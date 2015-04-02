package com.hts.web.userinfo.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hts.web.common.pojo.HTWorldLiked;
import com.hts.web.common.pojo.OpSysMsg;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.hts.web.common.pojo.UserInfoAvatar;
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
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.operations.dao.SysMsgDao;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserMsgDao;
import com.hts.web.userinfo.dao.UserMsgInteractDao;
import com.hts.web.userinfo.dao.UserMsgRecipientBoxDao;
import com.hts.web.userinfo.dao.UserMsgSendBoxDao;
import com.hts.web.userinfo.dao.UserMsgShieldDao;
import com.hts.web.userinfo.dao.UserRemarkDao;
import com.hts.web.userinfo.dao.UserShieldDao;
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
	
	@Value("${msg.squareRuleMsg}")
	private String squareRuleMsg ;
	
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
//				return worldInteractDao.queryInteractCount((Date)maxSerializable, userId);
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
		long commentCount = worldCommentDao.queryUnCheckUserCommentCount(userId);
		long msgCount = sysMsgDao.queryUnCheckSysMsgCount(userId);
		long userMsgCount = userMsgRecipientBoxDao.queryUnReadMsgCount(userId);
		jsonMap.put(OptResult.JSON_KEY_FOLLOW_COUNT, followCount);
		jsonMap.put(OptResult.JSON_KEY_LIKED_COUNT, likedCount);
		jsonMap.put(OptResult.JSON_KEY_COMMENT_COUNT, commentCount);
		jsonMap.put(OptResult.JSON_KEY_MSG_COUNT, msgCount);
		jsonMap.put(OptResult.JSON_KEY_USER_MSG_COUNT, userMsgCount);
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
		boolean shield = (userMsgShieldDao.queryShieldId(recipientId) != null ? true : false);
		final Integer id = keyGenService.generateId(KeyGenServiceImpl.USER_MSG_ID);
		userMsgDao.saveMsg(new UserMsg(id, new Date(), content, msgType));
		UserMsgBox msgBox = new UserMsgBox(id,senderId,recipientId,id);
		userMsgSendBoxDao.saveSendMsg(msgBox);
		if(!shield) { // 保存到接受者的收件箱并推送
			msgBox.setCk(Tag.FALSE);
			if(recipientId.equals(customerServiceId)) { // 表明这是通过反馈页面发送的
				userMsgRecipientBoxDao.saveRecipientBox(msgBox);
				
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
				List<OpSysMsgDto> list = null;
				if(!trimUserRecMsg)
					list = sysMsgDao.querySysMsgDto(userId, rowSelection);
				else
					list = sysMsgDao.querySysMsgDto2(userId, rowSelection);
				
				sysMsgDao.updateUnreadSysMsg(userId);
				// 更新最新标记
				if(clearUnCheck && list.size() > 0) {
					sysMsgDao.updateIsNew(userId, list.get(list.size()-1).getId(), 
							list.get(0).getId());
				}
				return list;
			}

			@Override
			public List<OpSysMsgDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpSysMsgDto> list = null;
				if(!trimUserRecMsg)
					list = sysMsgDao.querySysMsgDtoByMaxId(userId, maxId, rowSelection);
				else
					list = sysMsgDao.querySysMsgDtoByMaxId2(userId, maxId, rowSelection);
				// 更新最新标记
				if(clearUnCheck && list.size() > 0) {
					sysMsgDao.updateIsNew(userId, list.get(list.size()-1).getId(), 
							list.get(0).getId());
				}
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
//				return sysMsgDao.querySysMsgCountByMaxId(userId,maxId);
				return 0l;
			}

		}, OptResult.JSON_KEY_MSG, OptResult.JSON_KEY_TOTAL_COUNT); 
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

}
