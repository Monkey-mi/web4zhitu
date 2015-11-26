package com.hts.web.ztworld.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSErrorCode;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.LoggerKeies;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.SerializableSinceIdListAdapter;
import com.hts.web.common.pojo.HTWorldChannelName;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldCommentReId;
import com.hts.web.common.pojo.HTWorldCommentReport;
import com.hts.web.common.pojo.HTWorldGeo;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldKeep;
import com.hts.web.common.pojo.HTWorldLiked;
import com.hts.web.common.pojo.HTWorldLikedInline;
import com.hts.web.common.pojo.HTWorldLikedUserDto;
import com.hts.web.common.pojo.HTWorldReport;
import com.hts.web.common.pojo.MsgComment;
import com.hts.web.common.pojo.ObjectWithLiked;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.operations.dao.ChannelDao;
import com.hts.web.push.service.PushService;
import com.hts.web.userinfo.dao.MsgCommentDao;
import com.hts.web.userinfo.dao.MsgLikedDao;
import com.hts.web.userinfo.dao.MsgUnreadDao;
import com.hts.web.userinfo.dao.MsgUnreadDao.UnreadType;
import com.hts.web.userinfo.dao.UserAdminDao;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserShieldDao;
import com.hts.web.userinfo.service.UserActivityService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.userinfo.service.UserMsgService;
import com.hts.web.ztworld.dao.CommentBroadcastCacheDao;
import com.hts.web.ztworld.dao.CommentDeleteDao;
import com.hts.web.ztworld.dao.CommentShieldDao;
import com.hts.web.ztworld.dao.CommentWeekDao;
import com.hts.web.ztworld.dao.HTWorldCommentDao;
import com.hts.web.ztworld.dao.HTWorldCommentReportDao;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldKeepDao;
import com.hts.web.ztworld.dao.HTWorldLikedCancelDao;
import com.hts.web.ztworld.dao.HTWorldLikedDao;
import com.hts.web.ztworld.dao.HTWorldReportDao;
import com.hts.web.ztworld.service.ZTWorldInteractService;
import com.hts.web.ztworld.service.ZTWorldService;
import com.imzhitu.filter.comment.service.CommentFilterService;

/**
 * <p>
 * 织图世界互动子模块业务逻辑访问对象
 * <p>
 * 
 * @author ztj 2013-7-4 2015-11-04
 *
 */
@Service("HTSZTWorldInteractService")
public class ZTWorldInteractServiceImpl extends BaseServiceImpl implements ZTWorldInteractService{
	
	private static Logger shieldCommentLogger = 
			Logger.getLogger(LoggerKeies.WORLD_SHIELD_COMMENT);
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private UserInteractService userInteractService;

	@Autowired
	private PushService pushService;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private HTWorldCommentDao worldCommentDao;
	
	@Autowired
	private MsgCommentDao msgCommentDao;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private HTWorldLikedDao worldLikedDao;
	
	@Autowired
	private HTWorldKeepDao worldKeepDao;
	
	@Autowired
	private HTWorldReportDao worldReportDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserShieldDao userShieldDao;
	
	@Autowired
	private HTWorldCommentReportDao commentReportDao;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserAdminDao userAdminDao;
	
	@Autowired
	private CommentFilterService commentFilterService;

	@Autowired
	private ChannelDao channelDao;

	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private CommentBroadcastCacheDao commentBroadcastCacheDao;
	
	@Autowired
	private MsgUnreadDao msgUnreadDao;

	@Autowired
	private HTWorldLikedCancelDao likedCancelDao;
	
	@Autowired
	private MsgLikedDao msgLikedDao;
	
	@Autowired
	private CommentDeleteDao commentDeleteDao;
	
	@Autowired
	private CommentShieldDao commentShieldDao;
	
	@Autowired
	private CommentWeekDao commentWeekDao;
	
	@Override
	public void buildComments(final Integer userId, final Integer worldId, int maxId, int sinceId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(sinceId, maxId, start, limit, jsonMap, new SerializableSinceIdListAdapter<HTWorldCommentDto>(){

			@Override
			public List<HTWorldCommentDto> getSerializables(
					RowSelection rowSelection) {
				List<HTWorldCommentDto> list = worldCommentDao.queryComment(worldId, rowSelection);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public List<HTWorldCommentDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<HTWorldCommentDto> list = worldCommentDao.queryCommentByMaxId(worldId, maxId, rowSelection);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0l;
			}

			@Override
			public List<HTWorldCommentDto> getSerializableBySinceId(int sinceId, RowSelection rowSelection) {
				List<HTWorldCommentDto> list = worldCommentDao.queryCommentByMinId(worldId, sinceId, rowSelection);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public long getTotalBySinceId(int sinceId) {
				return 0;
			}

		}, OptResult.JSON_KEY_COMMENTS, OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	@Override
	public void buildOpenComment(final Integer worldId, int maxId, 
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<HTWorldCommentDto>(){

			@Override
			public List<HTWorldCommentDto> getSerializables(
					RowSelection rowSelection) {
				List<HTWorldCommentDto> list = worldCommentDao.queryComment(worldId, rowSelection);
				userInfoService.extractVerify(list);
				userInfoService.checksum(list);
				return list;
			}

			@Override
			public List<HTWorldCommentDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<HTWorldCommentDto> list = worldCommentDao.queryCommentByMaxId(worldId, maxId, rowSelection);
				userInfoService.extractVerify(list);
				userInfoService.checksum(list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0l;
			}

		}, OptResult.JSON_KEY_COMMENTS, OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	
	@Override
	public void saveComment(Boolean im, Integer worldId, Integer worldAuthorId,
			Integer authorId, String content, String atIdsStr, String atNamesStr,
			Map<String, Object> jsonMap) throws Exception {
		
		Integer commentId;
		UserPushInfo userPushInfo;

		content = StringUtil.filterXSS(
				trimColon2Comment(content)); // 过滤评论
		
		// 旧版没有传worldAuthorId
		if(worldAuthorId != null) {
			userPushInfo = userInfoDao.queryUserPushInfoById(worldAuthorId);
		} else { 
			userPushInfo = userInfoDao.queryUserPushInfoByWorldId(worldId);
			worldAuthorId = userPushInfo.getId();
		}
		
		commentId = saveCommentContent(worldId, worldAuthorId, 
				authorId, content, jsonMap);
		
		if(commentId > 0) {
			saveCommentMsg(im, authorId, worldAuthorId, worldId, 
					content, commentId, jsonMap, userPushInfo);
			
			saveAtByComment(im, authorId, worldId, 
					content, commentId, jsonMap, atIdsStr, atNamesStr, worldAuthorId);
		}
	}
	
	/**
	 * 保存评论内容
	 * 
	 * @param worldId
	 * @param worldAuthorId
	 * @param authorId
	 * @param content
	 * @param atIdsStr
	 * @param atNamesStr
	 * @param jsonMap
	 * @return
	 * @throws Exception
	 */
	private Integer saveCommentContent(Integer worldId, Integer worldAuthorId,
			Integer authorId, String content, Map<String, Object> jsonMap) throws Exception {
		
		Integer id = 0;
		HTWorldComment comment;
		HTWorldCommentDto dto;
		UserInfoDto udto;
		
		if(!checkWorldValid(worldId)) {
			throw new HTSException(HTSErrorCode.INVALID_WORLD);
		}
		
		id = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_COMMENT_ID);
		comment = new HTWorldComment(id, authorId, content, new Date(), worldId, worldAuthorId, 0);
		dto = new HTWorldCommentDto(id, authorId, 0, content, comment.getCommentDate(),
				worldId);
		
		udto = userInfoDao.queryUserInfoDtoById(authorId);
		dto.setUserInfo(udto);
		userInfoService.extractVerify(dto);
		
		jsonMap.put(OptResult.JSON_KEY_COMMENT, dto);
		jsonMap.put(OptResult.JSON_KEY_PHONE, Tag.IOS);
		jsonMap.put(OptResult.JSON_KEY_ACCEPT, Tag.FALSE);
		jsonMap.put(OptResult.JSON_KEY_SHIELD, Tag.TRUE);
		jsonMap.put(OptResult.JSON_KEY_IS_MUTUTAL, -1);
		jsonMap.put(OptResult.JSON_KEY_USER_ID, worldAuthorId);
		
		if(isCommentValid(comment)) {
			commentWeekDao.saveComment(comment);
			worldCommentDao.saveWorldComment(comment);
			Long count = worldCommentDao.queryCommentCount(worldId);// 更新评论总数
			worldDao.updateCommentCount(worldId, count.intValue());
			userActivityService.addActivityScore(Tag.ACT_TYPE_COMMENT, authorId);
			
			// 保存评论到广播队列
			if(authorId.equals(worldAuthorId)) {
				commentBroadcastCacheDao.saveComment(comment);
			}
			
			return id;
		}
		
		return 0;
	}
	
	/**
	 * 保存评论消息
	 * 
	 * @param atIds
	 * @param atNames
	 * @param im
	 * @param authorId
	 * @param worldAuthorId
	 * @param worldId
	 * @param content
	 * @param commentId
	 * @param jsonMap
	 * @throws Exception
	 */
	private void saveCommentMsg(Boolean im, 
			Integer authorId, Integer worldAuthorId, Integer worldId, String content, Integer commentId,
			Map<String, Object> jsonMap, UserPushInfo userPushInfo) throws Exception {
		
		if(!authorId.equals(worldAuthorId)) {
			
			saveUnReadMsgComment(commentId, authorId, worldAuthorId, worldId);

			boolean otherIm = UserInfoUtil.checkIsImVersion(userPushInfo.getVer());
			Integer shieldUser = userShieldDao.queryShieldId(worldAuthorId, authorId) == null ? Tag.FALSE : Tag.TRUE;
			if(im) { // 我已经开通IM
				if(otherIm) { // 对方已开通IM
					jsonMap.put(OptResult.JSON_KEY_ACCEPT, userPushInfo.getAcceptReplyPush());
					jsonMap.put(OptResult.JSON_KEY_SHIELD, shieldUser);
					if(!UserInfoUtil.checkIsAtVersion(userPushInfo.getVer())) { // 旧版需要关注关系
						Integer mut = userConcernDao.queryIsMututal(worldAuthorId, authorId);
						Integer isMutual = (mut != null ? mut : Tag.UN_CONCERN);
						jsonMap.put(OptResult.JSON_KEY_IS_MUTUTAL, isMutual);
					}
				} else {
					pushService.pushComment(commentId, authorId, worldId, worldAuthorId,
							content, userPushInfo, shieldUser, true); // 推送评论
				}
			} else {
				pushService.pushComment(commentId, authorId, worldId, worldAuthorId,
						content, userPushInfo, shieldUser, true); // 推送评论
			}
		}
	}

	@Override
	public void saveReply(Boolean im, Integer worldId, Integer worldAuthorId,
			Integer authorId,  String content, Integer reId, Integer reAuthorId, 
			String atIdsStr, String atNamesStr, Map<String, Object> jsonMap) throws Exception {
		
		if(worldAuthorId == null || worldAuthorId == 0) {
			worldAuthorId = worldDao.queryAuthorId(worldId);
		}
		if(reAuthorId == null || reAuthorId == 0) {
			reAuthorId = worldCommentDao.queryAuthorId(reId, worldId);
		}
		
		Integer commentId;

		content = StringUtil.filterXSS(
				replaceAt2Reply(content)); // 过滤评论
		
		
		commentId = saveReplyContent(worldId, worldAuthorId, 
				authorId, content, reId, reAuthorId, jsonMap);
		
		if(commentId > 0) {
			saveReplyMsg(im, commentId, reId, worldId, content, authorId, reAuthorId, 
					worldAuthorId, jsonMap);
			
			saveReplyMsg2WorldAuthor(authorId, reAuthorId, worldAuthorId, 
					commentId, worldId, content);
			
			saveAtByComment(im, authorId, worldId, 
					content, commentId, jsonMap, atIdsStr, atNamesStr, reAuthorId, worldAuthorId);
		}
	}
	
	@Override
	public void saveReply4Admin(Integer authorId, String content, 
			Integer worldId, Integer reId) throws Exception {
		saveReply(false, worldId, null, authorId, content, reId,
				null, null, null, new HashMap<String, Object>());
	}
	
	/**
	 * 检查评论是否有效
	 * 
	 * @param content
	 * @param worldId
	 * @param authorId
	 * @return
	 * @throws Exception
	 */
	private boolean isCommentValid(HTWorldComment comment) throws Exception {
		
		boolean valid = true;
		if(commentFilterService.isad(comment.getContent())) {
			valid = false;
			commentFilterService.insertADCommentToRedis(comment.getWorldId(), comment.getAuthorId(),
					comment.getContent(), new Date());
			commentShieldDao.saveComment(comment);
		} else {
			Map<String, Object> tags = userInfoDao.queryTagById(comment.getAuthorId());
			valid = (tags.get("shield")).equals(Tag.TRUE) ? false : true;
		}
		
		return valid;
	}
	
	/**
	 * 检测用户是否有效
	 * 
	 * @param userId
	 * @return
	 */
	private boolean isUserValid(Integer userId) {
		boolean valid = true;
		Map<String, Object> tags = userInfoDao.queryTagById(userId);
		valid = (tags.get("shield")).equals(Tag.TRUE) ? false : true;
		return valid;
	}
	
	/**
	 * 回复暗号检查，如果回复暗号并且拥有权限可以直接删除评论
	 * 
	 * @param authorId
	 * @param reId
	 * @param content
	 * @param worldId
	 * @return
	 * @throws Exception
	 */
	private PushStatus replyCypherCheck(Integer authorId, Integer reId, Integer reAuthorId, String content, Integer worldId) throws Exception {
		if(!StringUtil.checkIsNULL(content) && content.contains("zt666")) {
			if(reId != null && reId != 0) {
				if(userAdminDao.queryShieldCommentPermission(authorId)) {
					worldCommentDao.validRecord(HTS.HTWORLD_COMMENT, Tag.FALSE, reId); // 直接删除评论
					Long count = worldCommentDao.queryCommentCount(worldId);
					worldDao.updateCommentCount(worldId, count.intValue());
					
					HTWorldCommentDto dto = new HTWorldCommentDto(0, authorId, reAuthorId, "删除成功", 
							new Date(), worldId);
					UserInfoDto udto = userInfoDao.queryUserInfoDtoById(authorId);
					dto.setUserInfo(udto);
					PushStatus pushStatus = new PushStatus(authorId, Tag.IOS, Tag.UN_CONCERN,
							Tag.FALSE, Tag.TRUE, "");
					pushStatus.setInteractRes(dto);
					shieldCommentLogger.info("authorId=" + authorId + ",worldId=" + worldId + ",cid="+reId);
					return pushStatus;
				}
			}
		}
		return null;
	}
	
	/**
	 * 通过评论发送AT消息
	 * 
	 * @param atIds
	 * @param atNames
	 * @param im
	 * @param authorId
	 * @param worldId
	 * @param content
	 * @param commentId
	 * @param jsonMap
	 * @throws Exception
	 */
	private void saveAtByComment(Boolean im, 
			Integer authorId,  Integer worldId, String content, Integer commentId,
			Map<String, Object> jsonMap, String atIdsStr, String atNamesStr, Integer...rejectIds) throws Exception {
		
		if(atIdsStr != null && atNamesStr != null) {

			Set<Integer> rejectSet = new HashSet<Integer>();
			for(Integer rid : rejectIds) {
				rejectSet.add(rid);
			}
			List<PushStatus> atPushStatus = userMsgService.saveAtMsgs(atIdsStr, atNamesStr, rejectSet, 
					!im, authorId, Tag.AT_TYPE_COMMENT, commentId, worldId, content);
			jsonMap.put(OptResult.JSON_KEY_AT_PUSH_STATUS, atPushStatus);
		}
	}
	
	
	/**
	 * 保存评论内容
	 * 
	 * @param im
	 * @param worldId
	 * @param worldAuthorId
	 * @param authorId
	 * @param content
	 * @param reId
	 * @param reAuthorId
	 * @param jsonMap
	 * @return
	 * @throws Exception
	 */
	private Integer saveReplyContent(Integer worldId, Integer worldAuthorId,
			Integer authorId,  String content, Integer reId, Integer reAuthorId, 
			Map<String, Object> jsonMap) throws Exception {
		
		Integer id = 0;
		PushStatus cypherStatus;
		HTWorldComment comment;
		HTWorldCommentDto dto;
		UserInfoDto udto;
		
		if(!checkWorldValid(worldId)) {
			throw new HTSException(HTSErrorCode.INVALID_WORLD);
		} else if(!worldCommentDao.isCommentExist(reId, worldId)){
			throw new HTSException(HTSErrorCode.INVALID_COMMENT);
		}
		
		// 暗号检查
		cypherStatus = replyCypherCheck(authorId, reId, reAuthorId, content, worldId);
		if(cypherStatus != null) {
			jsonMap.put(OptResult.JSON_KEY_COMMENT, cypherStatus.getInteractRes());
			jsonMap.put(OptResult.JSON_KEY_PHONE, cypherStatus.getPhone());
			jsonMap.put(OptResult.JSON_KEY_ACCEPT, cypherStatus.getAccept());
			jsonMap.put(OptResult.JSON_KEY_SHIELD, cypherStatus.getShield());
			jsonMap.put(OptResult.JSON_KEY_IS_MUTUTAL, cypherStatus.getIsMututal());
			jsonMap.put(OptResult.JSON_KEY_USER_ID, cypherStatus.getUserId());
			jsonMap.put(OptResult.JSON_KEY_REMARK_ME, cypherStatus.getRemarkMe());
			return 0;
		}
		
		id = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_COMMENT_ID);
		
		comment = new HTWorldComment(id, authorId, content, new Date(), worldId,
				worldAuthorId, reAuthorId);
		dto = new HTWorldCommentDto(id, authorId, reAuthorId, content, 
				comment.getCommentDate(), worldId);
		
		udto = userInfoDao.queryUserInfoDtoById(authorId);
		dto.setUserInfo(udto);
		userInfoService.extractVerify(dto);
		
		jsonMap.put(OptResult.JSON_KEY_COMMENT, dto);
		jsonMap.put(OptResult.JSON_KEY_PHONE, Tag.IOS);
		jsonMap.put(OptResult.JSON_KEY_ACCEPT, Tag.FALSE);
		jsonMap.put(OptResult.JSON_KEY_SHIELD, Tag.TRUE);
		jsonMap.put(OptResult.JSON_KEY_IS_MUTUTAL, -1);
		jsonMap.put(OptResult.JSON_KEY_USER_ID, reAuthorId);
		
		if(isCommentValid(comment)) {
			commentWeekDao.saveComment(comment);
			worldCommentDao.saveWorldComment(comment);
			Long count = worldCommentDao.queryCommentCount(worldId);
			worldDao.updateCommentCount(worldId, count.intValue());
			userActivityService.addActivityScore(Tag.ACT_TYPE_COMMENT, authorId);
			return id;
		}
		
		return 0;
	}
	
	/**
	 * 保存评论回复消息
	 * 
	 * @param im
	 * @param id
	 * @param reId
	 * @param worldId
	 * @param content
	 * @param authorId
	 * @param reAuthorId
	 * @param worldAuthorId
	 * @param pushStatus
	 * @param userPushInfo
	 * @throws Exception
	 */
	private void saveReplyMsg(boolean im, Integer id, Integer reId, Integer worldId, String content,
			Integer authorId, Integer reAuthorId, Integer worldAuthorId,
			Map<String, Object> jsonMap) throws Exception {
		
		if(!reAuthorId.equals(authorId)) { // 不是自己回复自己
			
			UserPushInfo userPushInfo = userInfoDao.queryUserPushInfoById(reAuthorId);
			
			// 保存评论消息
			saveUnReadMsgComment(id, authorId, reAuthorId, worldId);

			boolean otherIm = UserInfoUtil.checkIsImVersion(userPushInfo.getVer());
			Integer shieldUser = userShieldDao.queryShieldId(reAuthorId, authorId) == null ? Tag.FALSE : Tag.TRUE;
			
			if(im) { // 我已经开通IM
				if(otherIm) { // 对方已开通IM
					jsonMap.put(OptResult.JSON_KEY_ACCEPT, userPushInfo.getAcceptReplyPush());
					jsonMap.put(OptResult.JSON_KEY_SHIELD, shieldUser);
					if(!UserInfoUtil.checkIsAtVersion(userPushInfo.getVer())) { // 旧版需要关注关系
						Integer mut = userConcernDao.queryIsMututal(reAuthorId, authorId);
						Integer isMutual = (mut != null ? mut : Tag.UN_CONCERN);
						jsonMap.put(OptResult.JSON_KEY_IS_MUTUTAL, isMutual);
					}
				} else {
					pushService.pushReply(reAuthorId, reAuthorId, worldId,
							worldAuthorId, reId, content, userPushInfo, shieldUser);
				}
			} else {
				pushService.pushReply(reAuthorId, worldAuthorId, worldId, 
						worldAuthorId, reId, content, userPushInfo, shieldUser);
			}
		}
		
	}

	/**
	 * 回复同时回复给原织图作者
	 * 
	 * @param authorId
	 * @param reAuthorId
	 * @param worldAuthorId
	 * @param commentId
	 * @param worldId
	 * @param content
	 * @throws Exception
	 */
	private void saveReplyMsg2WorldAuthor(Integer authorId, Integer reAuthorId,
			Integer worldAuthorId, Integer commentId, Integer worldId, 
			String content) throws Exception {
		if(!worldAuthorId.equals(reAuthorId) && !authorId.equals(worldAuthorId)) {
			
			// 保存评论消息
			saveUnReadMsgComment(commentId, authorId, worldAuthorId, worldId);
			
			UserPushInfo userPushInfo = userInfoDao.queryUserPushInfoById(worldAuthorId);
			Integer shield = userShieldDao.queryShieldId(worldAuthorId, authorId) == null ? Tag.FALSE : Tag.TRUE;
			
			pushService.pushComment(commentId, authorId, worldId, worldAuthorId,
					content, userPushInfo, shield, false);
		}
		
	}
	
	@Override
	public String replaceAt2Reply(String content) {
		if(content != null && content.length() > 1 && content.charAt(1) == '@') {
			return content.replaceFirst(" ", "回复").replaceFirst(" :", ":");
		}
		return content;
	}
	
	@Override
	public String trimColon2Comment(String content) {
		if(content != null && content.length() > 1 && content.charAt(1) == ':') {
			return content.replaceFirst(" : ", "");
		}
		return content;
	}
	
	/**
	 * 保存评论未读消息
	 * 
	 * @param commentId
	 * @param authorId
	 * @param receiveId
	 * @param worldId
	 */
	private void saveUnReadMsgComment(Integer commentId, Integer authorId, 
			Integer receiveId, Integer worldId) {
		if(!authorId.equals(receiveId)) {
			msgCommentDao.saveMsgComment(new MsgComment(commentId, 
					authorId, receiveId, worldId));
			msgUnreadDao.addCount(receiveId, UnreadType.COMMENT);
		}
	}
	
	@Override
	public void broadcastComment(HTWorldComment comment) throws Exception {
		
		Integer commentId = comment.getId();
		Integer worldId = comment.getWorldId();
		Integer authorId = comment.getAuthorId();
		String content = comment.getContent();
		
		if(!worldCommentDao.isCommentExist(commentId, worldId)) 
			return;
		
		// 推送给最近2000个评论参与人
		List<Integer> rids = worldCommentDao.queryAllAuthorId(worldId, 2000);
		for(Integer rid : rids) {
			if(rid.equals(authorId))
				continue;
			
			saveUnReadMsgComment(commentId, authorId, rid, worldId);
			Integer shield = userShieldDao.queryShieldId(rid, authorId) == null ? Tag.FALSE : Tag.TRUE;
			UserPushInfo pushInfo = userInfoDao.queryUserPushInfoById(rid);
			pushService.pushComment(commentId, authorId, worldId, authorId,
					content, pushInfo, shield, true);
		}
	}
	
	@Override
	public void deleteComment(Integer id, Integer worldId, 
			Integer userId) throws Exception {
		if(worldId == null || worldId == 0) {
			// TODO 未传织图id不允许删除评论
			throw new HTSException(HTSErrorCode.PARAMATER_ERR);
		}
		HTWorldComment comment = worldCommentDao.queryCommentById(id, worldId);
		if(comment == null)
			return;
		
		// 评论作者或织图作者有权删除评论
		if(comment.getAuthorId().equals(userId) 
				|| userId.equals(comment.getReAuthorId())
				|| worldDao.queryAuthorId(comment.getWorldId()).equals(userId)) {
			
			commentWeekDao.delComment(id, worldId);
			worldCommentDao.delComment(id, worldId);
			commentDeleteDao.saveComment(comment);
			Long count = worldCommentDao.queryCommentCount(comment.getWorldId());
			worldDao.updateCommentCount(comment.getWorldId(), count.intValue());
			
			msgCommentDao.deleteByCommentId(id, comment.getAuthorId());
			if(comment.getReAuthorId() != null) {
				msgCommentDao.deleteByCommentId(id, comment.getReAuthorId());
			}
		}
	}
	
	
	/*
	 *********************************
	 * 喜欢子模块
	 ********************************* 
	 */
	
	@Override
	public PushStatus saveLiked(Boolean im, Integer userId, Integer worldId,
			Integer worldAuthorId) throws Exception, HTSException {
		
		if(!isUserValid(userId)) {
			throw new HTSException(HTSErrorCode.PERMISSION_DENY);
		}
		
		if(!checkWorldValid(worldId)) {
			throw new HTSException(HTSErrorCode.INVALID_WORLD);
		}

		if(worldAuthorId == null || worldAuthorId == 0) {
			worldAuthorId = worldDao.queryAuthorId(worldId);
		}
		
		if(!likedCancelDao.isCancel(userId, worldId))
			return saveLikedOpt(im, userId, worldId, worldAuthorId);
		else
			return reSaveLikedOpt(userId, worldId, worldAuthorId);
	}

	/**
	 * 保存喜欢操作
	 * 
	 * @param userId
	 * @param worldId
	 * @throws Exception
	 */
	private PushStatus saveLikedOpt(Boolean im, Integer userId, Integer worldId, 
			Integer worldAuthorId) throws Exception {
		
		Integer id;
		PushStatus pushStatus;
		
		if((id = saveLikedContent(userId, worldId, worldAuthorId)) > 0)
			pushStatus = saveLikedMsg(im, id, userId, worldId, worldAuthorId);
		else
			pushStatus =  new PushStatus(worldAuthorId, Tag.IOS, Tag.UN_CONCERN, 
					Tag.FALSE, Tag.TRUE, null);
		
		return pushStatus;

	}

	/**
	 * 重新喜欢织图
	 * 
	 * @param worldId
	 * @param userId
	 */
	private PushStatus reSaveLikedOpt(Integer userId, Integer worldId, 
			Integer worldAuthorId) throws Exception {
		
		if(saveLikedContent(userId, worldId, worldAuthorId) > 0) {
			likedCancelDao.delCancel(userId, worldId);
		}
		return new PushStatus(worldAuthorId, Tag.IOS, Tag.UN_CONCERN, 
				Tag.FALSE, Tag.TRUE, null);
	}
	
	/**
	 * 保存喜欢内容
	 * 
	 * @param userId
	 * @param worldId
	 * @param worldAuthorId
	 * @return
	 */
	private Integer saveLikedContent(Integer userId, Integer worldId,
			Integer worldAuthorId) {
		Integer id;
		id = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LIKED_ID);
		try {
			worldLikedDao.saveLiked(new HTWorldLiked(id, userId, worldId));
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		//喜欢数+1
		Long count = worldLikedDao.queryLikedCount(worldId);
		worldDao.updateLikeCount(worldId, count.intValue());
		
		// 更新织图点赞数
		userInfoDao.updateLikeMeCount(worldAuthorId);
		return id;
	}
	
	/**
	 * 保存喜欢消息
	 * 
	 * @param im
	 * @param id
	 * @param userId
	 * @param worldId
	 * @param worldAuthorId
	 * @return
	 * @throws Exception
	 */
	private PushStatus saveLikedMsg(Boolean im, Integer id, Integer userId,
			Integer worldId, Integer worldAuthorId) throws Exception {
		
		UserPushInfo userPushInfo;
		PushStatus pushStatus;
		
		pushStatus = new PushStatus(worldAuthorId, Tag.IOS, Tag.UN_CONCERN, 
				Tag.FALSE, Tag.TRUE, null);
		userPushInfo = userInfoDao.queryUserPushInfoById(worldAuthorId);
		
		saveUnreadMsgLiked(id, userId, worldAuthorId, worldId);

		boolean otherIm = UserInfoUtil.checkIsImVersion(userPushInfo.getVer());
		Integer shieldUser = userShieldDao.queryShieldId(worldAuthorId, userId) == null ? Tag.FALSE : Tag.TRUE;
		if(im) { // 我已经开通IM
			if(otherIm) { // 对方已开通IM
				pushStatus.setAccept(userPushInfo.getAcceptLikedPush());
				pushStatus.setShield(shieldUser);
				if(!UserInfoUtil.checkIsAtVersion(userPushInfo.getVer())) { // 旧版需要关注关系
					Integer mut = userConcernDao.queryIsMututal(worldAuthorId, userId);
					Integer isMutual = (mut != null ? mut : Tag.UN_CONCERN);
					pushStatus.setIsMututal(isMutual);
				}
			} else {
				pushService.pushLiked(id, userId, worldId, worldAuthorId, userPushInfo, shieldUser); //推送消息
			}
		} else {
			pushService.pushLiked(id, userId, worldId, worldAuthorId, userPushInfo, shieldUser); //推送消息
		}
		
		return pushStatus;
	}
	
	/**
	 * 保存喜欢未读消息
	 * 
	 * @param likeId
	 * @param userId
	 * @param receiveId
	 * @param worldId
	 */
	private void saveUnreadMsgLiked(Integer likeId, Integer userId, 
			Integer receiveId, Integer worldId) {
		if(!userId.equals(receiveId)) {
			msgLikedDao.saveMsg(likeId, userId, worldId, receiveId);
			msgUnreadDao.addCount(receiveId, UnreadType.LIKE);
		}
	}
	
	@Override
	public void cancelLiked(Integer userId, Integer worldId) throws Exception {
		
		worldLikedDao.delLiked(userId, worldId);
		likedCancelDao.saveCancel(userId, worldId);
		
		// 更新织图点赞数
		Long count = worldLikedDao.queryLikedCount(worldId);
		worldDao.updateLikeCount(worldId, count.intValue());
	}
	
	/*
	 *********************************
	 * 收藏子模块
	 ********************************* 
	 */
	
	/**
	 * 保存织图收藏
	 * 
	 * @param userId
	 * @param worldId
	 * @throws HTSException 
	 */
	public void saveKeep(Integer userId, Integer worldId) throws Exception {
		
		if(!checkWorldValid(worldId)) {
			throw new HTSException(HTSErrorCode.INVALID_WORLD);
		}
		
		boolean flag = saveOrReKeep(userId, worldId);
		if(!flag) {
			throw new HTSException(HTSErrorCode.REPEAT_KEEP);
		}
	}
	
	/**
	 * 取消收藏
	 * 
	 * @param userId
	 * @param worldId
	 * @throws HTSException 
	 */
	public void cancelKeep(Integer userId, Integer worldId) throws Exception {
		worldKeepDao.updateKeep(userId, worldId, Tag.FALSE, new Date());
		//收藏数-1
		Long count = worldKeepDao.queryKeepCount(worldId);
		worldDao.updateKeepCount(worldId, count.intValue());
		
		Long keepCount = worldKeepDao.queryUserKeepCount(userId);
		userInfoDao.updateKeepCount(userId, keepCount.intValue());
	}
	
	/**
	 * 保存或重新收藏
	 * 
	 * @param worldId
	 * @param userId
	 */
	private boolean saveOrReKeep(Integer userId, Integer worldId) throws Exception {
		
		
		HTWorldKeep keep = worldKeepDao.queryKeep(userId, worldId);
		if(keep == null) {
			saveKeepOpt(userId, worldId);
		} else if(keep.getValid() == Tag.FALSE) {
			reSaveKeep(userId, worldId);
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * 收藏织图操作
	 * 
	 * @param userId
	 * @param worldId
	 * @throws Exception
	 */
	private void saveKeepOpt(Integer userId, Integer worldId) throws Exception {
		
		UserPushInfo userPushInfo = userInfoDao.queryUserPushInfoByWorldId(worldId);
		if(userPushInfo == null)
			throw new HTSException(HTSErrorCode.USER_NOT_EXISTS);
		
		int ck = userPushInfo.getId().equals(userId) ? Tag.TRUE : Tag.FALSE;
		HTWorldKeep keep = new HTWorldKeep(userId, new Date(), worldId,userPushInfo.getId(), ck, Tag.TRUE);
		worldKeepDao.saveKeep(keep);
		
		//收藏数+1
		Long count = worldKeepDao.queryKeepCount(worldId);
		worldDao.updateKeepCount(worldId, count.intValue());
		
		Long keepCount = worldKeepDao.queryUserKeepCount(userId);
		userInfoDao.updateKeepCount(userId, keepCount.intValue());
	}
	
	/**
	 * 重新收藏织图
	 * 
	 * @param worldId
	 * @param userId
	 * @throws Exception 
	 */
	private void reSaveKeep(Integer userId, Integer worldId) throws Exception {
		worldKeepDao.updateKeep(userId, worldId, Tag.TRUE, new Date());
		
		//收藏数+1
		Long count = worldKeepDao.queryKeepCount(worldId);
		worldDao.updateKeepCount(worldId, count.intValue());
		Long keepCount = worldKeepDao.queryUserKeepCount(userId);
		userInfoDao.updateKeepCount(userId, keepCount.intValue());
	}
	
	/*
	 * 举报子模块 
	 */
	
	@Override
	public void saveReport(Integer userId, Integer worldId, String reportContent) throws Exception {
		boolean flag = saveOrReReport(userId, worldId, reportContent);
		if(!flag) {
			throw new HTSException(HTSErrorCode.REPEAT_REPORT);
		}
	}

	@Override
	public void cancelReport(Integer userId, Integer worldId) throws Exception {
		worldReportDao.updateReportValid(userId, worldId, Tag.FALSE, new Date());
	}
	
	/**
	 * 保存或重新举报
	 * 
	 * @param worldId
	 * @param userId
	 * @throws Exception 
	 */
	private boolean saveOrReReport(Integer userId, Integer worldId, String reportContent) throws Exception {
		HTWorldReport report = worldReportDao.queryReport(userId, worldId);
		if(report == null) {
			saveReportOpt(userId, worldId,reportContent);
		} else if(report.getValid() == Tag.FALSE) {
			reSaveReport(userId, worldId,reportContent);
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * 举报织图
	 * @param userId
	 * @param worldId
	 */
	public void saveReportOpt(Integer userId, Integer worldId, String reportContent) throws Exception {
		HTWorldReport keep = null;
		keep = new HTWorldReport(userId, worldId,reportContent, new Date(), Tag.TRUE);
		worldReportDao.saveReport(keep);
	}
	
	/**
	 * 重新举报织图
	 * 
	 * @param worldId
	 * @param userId
	 * @throws Exception 
	 */
	public void reSaveReport(Integer userId, Integer worldId, String reportContent) throws Exception {
		worldReportDao.updateReport(userId, worldId, reportContent, Tag.TRUE, new Date());
	}
	
	@Override
	public void getWorldInteract(Integer worldId, Integer userId, 
			boolean trimExtras, Integer commentLimit, Integer likedLimit, Map<String, Object> jsonMap)
			throws Exception {
		HTWorldInteractDto dto = worldDao.queryHTWorldInteract(worldId);
		if(dto == null) {
			throw new HTSException(HTSErrorCode.INVALID_WORLD);
		}
		if(!dto.getAuthorId().equals(userId)) {
			Integer isMututal = userConcernDao.queryIsMututal(userId, dto.getAuthorId());
			if(isMututal != null) {
				dto.getUserInfo().setIsMututal(isMututal);
			}
		}
		worldService.extractExtraInfo(true, true, userId, trimExtras, commentLimit, likedLimit, dto);
		userInfoService.extractVerify(dto);
		
		// 加载第一个频道的信息
		if(dto.getChannelNames() != null && dto.getChannelNames().size() > 0) {
			HTWorldChannelName cname = dto.getChannelNames().get(0);
			Integer cid = cname.getId();
			if(cid != null) {
				OpChannel channel = channelDao.queryChannel(cid);
				if(channel != null) {
					List<OpChannel> channelList = new ArrayList<OpChannel>();
					channelList.add(channel);
					jsonMap.put(OptResult.JSON_KEY_CHANNELS, channelList);
				}
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, dto);
	}

	public void getWorldInteractByLink(String shortLink, Integer likedLimit, 
			Map<String, Object> jsonMap) throws Exception{
		HTWorldInteractDto dto = worldDao.queryHTWorldInteractByLink(shortLink);
		if(dto == null) {
			throw new HTSException(HTSErrorCode.INVALID_WORLD);
		}
		
		userInfoService.checksum(dto.getUserInfo());
		worldService.extractExtraInfo(false, false, null, false, 0, likedLimit, dto);
		userInfoService.extractVerify(dto);
		
		List<HTWorldLikedInline> likes = dto.getLikes();
		if(likes != null && !likes.isEmpty()) {
			userInfoService.checksum(likes);
		}
		
		// 加载第一个频道的信息
		if(dto.getChannelNames() != null && dto.getChannelNames().size() > 0) {
			HTWorldChannelName cname = dto.getChannelNames().get(0);
			Integer cid = cname.getId();
			if(cid != null) {
				OpChannel channel = channelDao.queryChannel(cid);
				if(channel != null) {
					List<OpChannel> channelList = new ArrayList<OpChannel>();
					channelList.add(channel);
					jsonMap.put(OptResult.JSON_KEY_CHANNELS, channelList);
				}
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, dto);
	}

	@Override
	public List<HTWorldGeo> getWorldGeo(Integer userId) throws Exception {
		return worldDao.queryWorldGeo(userId);
	}

	@Override
	public void saveLikedWithoutLogin(Integer worldId) throws Exception {
	}
	
	@Override
	public void buildLikedUser(Integer maxId, final Integer worldId, final Integer userId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<HTWorldLikedUserDto>() {

			@Override
			public List<HTWorldLikedUserDto> getSerializables(
					RowSelection rowSelection) {
				List<HTWorldLikedUserDto> list = worldLikedDao.queryLikedUser(worldId,
						rowSelection);
				userInfoService.extractVerify(list);
				return list;
			}

			@Override
			public List<HTWorldLikedUserDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<HTWorldLikedUserDto> list = worldLikedDao.queryLikedUser(maxId, worldId, rowSelection);
				userInfoService.extractVerify(list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
		}, OptResult.JSON_KEY_USER_INFO, null);
	}

	@Override
	public void buildLikeOthersWorldThumbnail(Integer limit, Integer userId,
			Integer authorId, Map<String, Object> jsonMap) throws Exception {
//		List<HTWorldThumbnail> list = worldLikedDao.queryLikeOthersWorldThumbnail(limit, userId, authorId);
//		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
//		long total = worldLikedDao.queryLikeOthersWorldCount(userId, authorId);
//		jsonMap.put(OptResult.JSON_KEY_TOTAL_COUNT, total);
		
	}

	@Override
	public void saveCommentReport(Integer userId, Integer commentId,
			String content) throws Exception {
		Integer id = commentReportDao.queryReportId(userId, commentId);
		if(id == null || id.equals(0)) {
			commentReportDao.saveReport(new HTWorldCommentReport(userId, commentId, content, 
					new Date(), Tag.TRUE));
		}
	}

	@Override
	public void extractLiked(Integer userId, final List<? extends ObjectWithLiked> list) {
		if(list.size() > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] worldIds = new Integer[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Integer worldId = list.get(i).getWorldId();
				worldIds[i] = worldId;
				indexMap.put(worldId, i);
			}
			
			if(indexMap.size() > 0) {
				worldLikedDao.queryLiked(userId, worldIds, new RowCallback<Integer>() {
		
					@Override
					public void callback(Integer t) {
						Integer index = indexMap.get(t);
						if(index != null) {
							list.get(index).setLiked(Tag.TRUE);
						}
					}
				});
			}
		}
		
	}

	@Override
	public void buildLikeMeCount(Integer minId, Integer userId,
			Map<String, Object> jsonMap) {
//		long count = 0l;
//		if(minId == null || minId == 0)
//			count = worldLikedDao.queryLikeMeCount(userId);
//		else 
//			count = worldLikedDao.queryLikeMeCount(minId, userId);
//		Integer maxId = worldLikedDao.queryMaxLikeMeId(userId);
//		jsonMap.put(OptResult.JSON_KEY_TOTAL_COUNT, count);
//		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
	}

	@Override
	public void buildCommentReId(String idsStr, Map<String, Object> jsonMap) {
//		if(!StringUtil.checkIsNULL(idsStr)) {
//			Integer[] ids = StringUtil.convertStringToIds(idsStr);
//			if(ids != null && ids.length > 0) {
//				List<HTWorldCommentReId> list = worldCommentDao.queryReId(ids);
//				jsonMap.put(OptResult.JSON_KEY_COMMENTS, list);
//				return;
//			}
//		}
		jsonMap.put(OptResult.JSON_KEY_COMMENTS, new ArrayList<HTWorldCommentReId>());
	}
	
	@Override
	public boolean checkWorldValid(Integer worldId) {
		return worldDao.queryValid(worldId).equals(Tag.TRUE) ? true : false;
	}
	
}
