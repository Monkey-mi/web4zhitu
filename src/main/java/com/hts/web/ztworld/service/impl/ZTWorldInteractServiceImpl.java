package com.hts.web.ztworld.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.LoggerKeies;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.SerializableSinceIdListAdapter;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldCommentReId;
import com.hts.web.common.pojo.HTWorldCommentReport;
import com.hts.web.common.pojo.HTWorldGeo;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldKeep;
import com.hts.web.common.pojo.HTWorldLiked;
import com.hts.web.common.pojo.HTWorldReport;
import com.hts.web.common.pojo.HTWorldThumbnail;
import com.hts.web.common.pojo.ObjectWithLiked;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.pojo.UserConcernDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.push.service.PushService;
import com.hts.web.userinfo.dao.UserAdminDao;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserRemarkDao;
import com.hts.web.userinfo.dao.UserShieldDao;
import com.hts.web.userinfo.service.UserActivityService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.ztworld.dao.HTWorldCommentDao;
import com.hts.web.ztworld.dao.HTWorldCommentReportDao;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldKeepDao;
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
 * 创建时间：2013-7-4
 * @author ztj
 *
 */
@Service("HTSZTWorldInteractService")
public class ZTWorldInteractServiceImpl extends BaseServiceImpl implements ZTWorldInteractService{
	
	private static Logger shieldCommentLogger = Logger.getLogger(LoggerKeies.WORLD_SHIELD_COMMENT);
	
	/**
	 * 错误代码：重复操作
	 */
	public static final Integer ERROR_CODE_REPEAT_OPT = 1;
	
	/**
	 * 错误代码：无效操作
	 */
	public static final Integer ERROR_CODE_INVALID = 2;
	
	/**
	 * 错误提示：无效操作
	 */
	public static final String ERROR_MSG_INVALID = "织图已经被删除，无法互动";
	
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
	private UserRemarkDao userRemarkDao;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserAdminDao userAdminDao;
	
	@Autowired
	private CommentFilterService commentFilterService;

	@Override
	public void buildComments(final Integer userId, final Integer worldId,  int sinceId, int maxId, 
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
				return worldCommentDao.queryCommentCountByMaxId(worldId, maxId);
			}

			@Override
			public List<HTWorldCommentDto> getSerializableBySinceId(
					int sinceId, RowSelection rowSelection) {
				List<HTWorldCommentDto> list = worldCommentDao.queryCommentByMinId(worldId, sinceId, rowSelection);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public long getTotalBySinceId(int sinceId) {
				return worldCommentDao.queryCommentCountByMinId(worldId, sinceId);
			}
			
		},OptResult.JSON_KEY_COMMENTS, OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	@Override
	public PushStatus saveComment(Boolean im, Integer worldId, Integer worldAuthorId, Integer authorId, 
			String content) throws Exception {
		Integer ck = Tag.TRUE;
		Integer shield = Tag.FALSE;
		Integer wauthorId = worldAuthorId;
		UserPushInfo userPushInfo = null;
		content = StringUtil.filterXSS(content);
		boolean isad = false;
		
		// 获取推送信息
//		if(worldAuthorId == null || worldAuthorId.equals(0)) {
//			userPushInfo = userInfoDao.queryUserPushInfoByWorldId(worldId);
//			wauthorId = userPushInfo.getId();
//		} else {
//			userPushInfo = userInfoDao.queryUserPushInfoById(wauthorId);
//		}
		userPushInfo = userInfoDao.queryUserPushInfoByWorldId(worldId);
		if(userPushInfo == null)
			throw new HTSException("用户不存在", ERROR_CODE_INVALID);
		
		wauthorId = userPushInfo.getId();
		String remark = userRemarkDao.queryRemark(wauthorId, authorId);
		PushStatus pushStatus = new PushStatus(wauthorId, Tag.IOS, Tag.UN_CONCERN, 
				Tag.FALSE, Tag.TRUE, remark);
		
		// 获取已读状态
		ck = wauthorId.equals(authorId) ? Tag.TRUE : Tag.FALSE;

		// 过滤广告
		isad = commentFilterService.isad(content);
		if(isad) {
			shield = Tag.TRUE;
		} else {
			Map<String, Object> tags = userInfoDao.queryTagById(authorId);
			shield = ((Integer)tags.get("shield")).equals(Tag.TRUE) ? Tag.TRUE : Tag.FALSE;
		}
		
		Integer id = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_COMMENT_ID);
		HTWorldComment comment = new HTWorldComment(id, authorId, content, new Date(), worldId, wauthorId, 0,0, 
				ck, Tag.TRUE, shield);
		worldCommentDao.saveWorldComment(comment);
		
		if(shield.equals(Tag.FALSE)) {
			// 更新评论总数
			Long count = worldCommentDao.queryCommentCount(worldId);
			int num = worldDao.updateCommentCount(worldId, count.intValue());
			if(num == 0)
				throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_INVALID);
			
			if(ck.equals(Tag.FALSE)) { // 不是自己发给自己

				boolean otherIm = UserInfoUtil.checkIsImVersion(userPushInfo.getVer());
				Integer shieldUser = userShieldDao.queryShieldId(wauthorId, authorId) == null ? Tag.FALSE : Tag.TRUE;
				if(im) { // 我已经开通IM
					if(otherIm) { // 对方已开通IM
						Integer mut = userConcernDao.queryIsMututal(wauthorId, authorId);
						Integer isMutual = (mut != null ? mut : Tag.UN_CONCERN);
						pushStatus.setPhone(userPushInfo.getPhoneCode());
						pushStatus.setAccept(userPushInfo.getAcceptCommentPush());
						pushStatus.setShield(shieldUser);
						pushStatus.setUserId(wauthorId);
						pushStatus.setIsMututal(isMutual);
					} else {
						pushService.pushComment(id, authorId, worldId, wauthorId,
								content, userPushInfo, shieldUser); // 推送评论
					}
				} else {
					pushService.pushComment(id, authorId, worldId, wauthorId,
							content, userPushInfo, shieldUser); // 推送评论
				}
			}
		}
		HTWorldCommentDto dto = new HTWorldCommentDto(id, authorId, 0, content, 
				comment.getCommentDate(), worldId, wauthorId);
		UserInfoDto udto = userInfoDao.queryUserInfoDtoById(authorId);
		dto.setUserInfo(udto);
		
		userInfoService.extractVerify(dto);
		pushStatus.setInteractRes(dto);
		
		userActivityService.addActivityScore(Tag.ACT_TYPE_COMMENT, authorId);
		
		return pushStatus;
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
	public PushStatus replyCypherCheck(Integer authorId, Integer reId, String content, Integer worldId) throws Exception {
		if(!StringUtil.checkIsNULL(content) && content.contains("zt666")) {
			if(reId != null && reId != 0) {
				if(userAdminDao.queryShieldCommentPermission(authorId)) {
					worldCommentDao.validRecord(HTS.HTWORLD_COMMENT, Tag.FALSE, reId); // 直接删除评论
					Long count = worldCommentDao.queryCommentCount(worldId);
					worldDao.updateCommentCount(worldId, count.intValue());
					
					HTWorldCommentDto dto = new HTWorldCommentDto(0, authorId, reId, "删除成功", 
							new Date(), worldId, 0);
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
	
	@Override
	public PushStatus saveReply(Boolean im, Integer worldId, Integer worldAuthorId, 
			Integer authorId,  String content, Integer reId, Integer reAuthorId) throws Exception {
		// 暗号检查
		PushStatus cypherStatus = replyCypherCheck(authorId, reId, content, worldId);
		if(cypherStatus != null) {
			return cypherStatus;
		}
		
		Integer ck = Tag.TRUE;
		Integer shield = Tag.FALSE;
		Integer rAuthorId = reAuthorId;
		UserPushInfo userPushInfo = null;
		content = StringUtil.filterXSS(content);
		boolean isad = false;
		
		// 获取推送信息
//		if(reAuthorId == null || reAuthorId.equals(0)) {
//			userPushInfo = userInfoDao.queryUserPushInfoByCommentId(reId);
//			rAuthorId = userPushInfo.getId();
//		} else {
//			userPushInfo = userInfoDao.queryUserPushInfoById(reAuthorId);
//		}
		userPushInfo = userInfoDao.queryUserPushInfoByCommentId(reId);
		if(userPushInfo == null)
			throw new HTSException("用户不存在", ERROR_CODE_INVALID);
		
		Integer wauthor = worldDao.queryAuthorId(worldId);
		rAuthorId = userPushInfo.getId();
		
		String remark = userRemarkDao.queryRemark(rAuthorId, authorId);
		PushStatus pushStatus = new PushStatus(rAuthorId, Tag.IOS, Tag.UN_CONCERN,
				Tag.FALSE, Tag.TRUE, remark);
		
		// 获取已读状态
		ck = rAuthorId.equals(authorId) ? Tag.TRUE : Tag.FALSE;
		
		// 过滤广告
		isad = commentFilterService.isad(content);
		if(isad) {
			shield = Tag.TRUE;
		} else {
			Map<String, Object> tags = userInfoDao.queryTagById(authorId);
			shield = ((Integer)tags.get("shield")).equals(Tag.TRUE) ? Tag.TRUE : Tag.FALSE;
		}
		
		
		Integer id = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_COMMENT_ID);
		HTWorldComment comment = new HTWorldComment(id, authorId, content, new Date(), worldId,
				wauthor, reId, userPushInfo.getId(), ck, Tag.TRUE, shield);
		worldCommentDao.saveWorldComment(comment);
		
		if(shield.equals(Tag.FALSE)) {
			// 更新评论总数
			Long count = worldCommentDao.queryCommentCount(worldId);
			int num = worldDao.updateCommentCount(worldId, count.intValue());
			if(num == 0)
				throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_INVALID);
			
			if(ck.equals(Tag.FALSE)) { // 不是自己发给自己

				boolean otherIm = UserInfoUtil.checkIsImVersion(userPushInfo.getVer());
				Integer shieldUser = userShieldDao.queryShieldId(rAuthorId, authorId) == null ? Tag.FALSE : Tag.TRUE;
				
				if(im) { // 我已经开通IM
					if(otherIm) { // 对方已开通IM
						Integer mut = userConcernDao.queryIsMututal(rAuthorId, authorId);
						Integer isMutual = (mut != null ? mut : Tag.UN_CONCERN);
						pushStatus.setPhone(userPushInfo.getPhoneCode());
						pushStatus.setAccept(userPushInfo.getAcceptReplyPush());
						pushStatus.setShield(shieldUser);
						pushStatus.setUserId(rAuthorId);
						pushStatus.setIsMututal(isMutual);
					} else {
						pushService.pushReply(id, authorId, worldId, comment.getWorldAuthorId(), reId, content, userPushInfo, shieldUser);
					}
				} else {
					pushService.pushReply(id, authorId, worldId,comment.getWorldAuthorId(),reId, content, userPushInfo, shieldUser);
				}
			}
		}
		HTWorldCommentDto dto = new HTWorldCommentDto(id, authorId, reId, content, 
				comment.getCommentDate(), worldId, 0);
		UserInfoDto udto = userInfoDao.queryUserInfoDtoById(authorId);
		dto.setUserInfo(udto);
		userInfoService.extractVerify(dto);
		pushStatus.setInteractRes(dto);
		return pushStatus;
	}
	
	@Override
	public void deleteComment(Integer id, Integer userId) throws Exception {
		HTWorldComment comment = worldCommentDao.queryCommentById(id);
		if(comment != null && comment.getValid() == Tag.TRUE) {
			// 评论作者或织图作者有权删除评论
			if(comment.getAuthorId().equals(userId) 
					|| worldDao.queryAuthorId(comment.getWorldId()).equals(userId)) {
				worldCommentDao.validRecord(HTS.HTWORLD_COMMENT, Tag.FALSE, id);
				Long count = worldCommentDao.queryCommentCount(comment.getWorldId());
				int num = worldDao.updateCommentCount(comment.getWorldId(), count.intValue());
				if(num == 0)
					throw new HTSException("记录无效", ERROR_CODE_INVALID);
			}
		} else {
			throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_REPEAT_OPT);
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
		PushStatus pushStatus = saveOrReLiked(im, userId, worldId, worldAuthorId);
		if(pushStatus == null) {
			HTSException e = new HTSException("已经喜欢过");
			e.setErrorCode(ERROR_CODE_REPEAT_OPT);
			throw e;
		}
		return pushStatus;
	}

	@Override
	public void cancelLiked(Integer userId, Integer worldId) throws Exception {
		Integer worldAuthorId = worldDao.queryAuthorId(worldId);
		worldLikedDao.updateLiked(userId, worldId, Tag.FALSE, new Date());
		//喜欢数-1
		Long count = worldLikedDao.queryLikedCount(worldId);
		int num = worldDao.updateLikeCount(worldId, count.intValue());
		if(num == 0)
			throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_INVALID);
		// 更新被赞次数
		Long likeMeCount = worldLikedDao.queryLikeMeCount(worldAuthorId);
		userInfoDao.updateLikeMeCount(worldAuthorId, likeMeCount.intValue());
	}
	
	/**
	 * 保存或重新喜欢
	 * 
	 * @param userId
	 * @param worldId
	 * @return
	 * @throws Exception
	 */
	private PushStatus saveOrReLiked(Boolean im, Integer userId, Integer worldId, 
			Integer worldAuthorId) throws Exception {
		PushStatus pushStatus = null;
		HTWorldLiked liked = worldLikedDao.queryLiked(userId, worldId);
		if(liked == null) {
			pushStatus = saveLikedOpt(im, userId, worldId, worldAuthorId);
			userActivityService.addActivityScore(Tag.ACT_TYPE_LIKE, userId);
		} else if(liked.getValid() == Tag.FALSE) {
			pushStatus = reSaveLiked(im, userId, worldId, worldAuthorId);
		}
		return pushStatus;
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
		Integer ck = Tag.TRUE;
		Integer wauthorId = worldAuthorId;
		UserPushInfo userPushInfo = null;
		
		// 获取推送信息
//		if(worldAuthorId == null || worldAuthorId.equals(0)) {
//			userPushInfo = userInfoDao.queryUserPushInfoByWorldId(worldId);
//			wauthorId = userPushInfo.getId();
//		} else {
//			userPushInfo = userInfoDao.queryUserPushInfoById(worldAuthorId);
//		}
		userPushInfo = userInfoDao.queryUserPushInfoByWorldId(worldId);
		if(userPushInfo == null)
			throw new HTSException("用户不存在", ERROR_CODE_INVALID);
		
		wauthorId = userPushInfo.getId();
		
		String remark = userRemarkDao.queryRemark(wauthorId, userId);
		PushStatus pushStatus = new PushStatus(wauthorId, Tag.IOS, Tag.UN_CONCERN, 
				Tag.FALSE, Tag.TRUE, remark);
		
		// 获取已读状态
		ck = wauthorId.equals(userId) ? Tag.TRUE : Tag.FALSE;
		
		
		HTWorldLiked liked = new HTWorldLiked(userId, new Date(), worldId,userPushInfo.getId(), ck, Tag.TRUE);
		Integer id = worldLikedDao.saveLiked(liked);
		
		//喜欢数+1
		Long count = worldLikedDao.queryLikedCount(worldId);
		int num = worldDao.updateLikeCount(worldId, count.intValue());
		if(num == 0)
			throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_INVALID);
		
		// 更新被赞次数
		Long likeMeCount = worldLikedDao.queryLikeMeCount(wauthorId);
		userInfoDao.updateLikeMeCount(wauthorId, likeMeCount.intValue());
		
		if(ck == Tag.FALSE) {
			
			boolean otherIm = UserInfoUtil.checkIsImVersion(userPushInfo.getVer());
			Integer shieldUser = userShieldDao.queryShieldId(wauthorId, userId) == null ? Tag.FALSE : Tag.TRUE;
			if(im) { // 我已经开通IM
				if(otherIm) { // 对方已开通IM
					Integer mut = userConcernDao.queryIsMututal(wauthorId, userId);
					Integer isMutual = (mut != null ? mut : Tag.UN_CONCERN);
					pushStatus.setPhone(userPushInfo.getPhoneCode());
					pushStatus.setAccept(userPushInfo.getAcceptLikedPush());
					pushStatus.setShield(shieldUser);
					pushStatus.setUserId(wauthorId);
					pushStatus.setIsMututal(isMutual);
				} else {
					pushService.pushLiked(id, userId, worldId, wauthorId, userPushInfo, shieldUser); //推送消息
				}
			} else {
				pushService.pushLiked(id, userId, worldId, wauthorId, userPushInfo, shieldUser); //推送消息
			}
		}
		return pushStatus;
	}
	
	/**
	 * 重新喜欢织图
	 * 
	 * @param worldId
	 * @param userId
	 */
	private PushStatus reSaveLiked(Boolean im, Integer userId, Integer worldId, 
			Integer worldAuthorId) throws Exception {
		// 获取推送信息
//		if(worldAuthorId == null || worldAuthorId.equals(0)) {
//			worldAuthorId = worldDao.queryAuthorId(worldId);
//		}
		Integer wauthorId = worldDao.queryAuthorId(worldId);
		worldLikedDao.updateLiked(userId, worldId, Tag.TRUE, new Date());
		//喜欢数+1
		Long count = worldLikedDao.queryLikedCount(worldId);
		int num = worldDao.updateLikeCount(worldId, count.intValue());
		if(num == 0) {
			throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_INVALID);
		}
		// 更新被赞次数
		Long likeMeCount = worldLikedDao.queryLikeMeCount(wauthorId);
		userInfoDao.updateLikeMeCount(wauthorId, likeMeCount.intValue());
		return new PushStatus(wauthorId, Tag.IOS, Tag.UN_CONCERN, Tag.FALSE, Tag.TRUE, null);
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
		boolean flag = saveOrReKeep(userId, worldId);
		if(!flag) {
			HTSException e = new HTSException("已经收藏过");
			e.setErrorCode(ERROR_CODE_REPEAT_OPT);
			throw e;
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
		int num = worldDao.updateKeepCount(worldId, count.intValue());
		if(num == 0)
			throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_INVALID);
		Long keepCount = worldKeepDao.queryUserKeepCount(userId);
		userInfoDao.updateKeepCount(userId, keepCount.intValue());
	}
	
	/**
	 * 保存或重新收藏
	 * 
	 * @param worldId
	 * @param userId
	 * @throws SQLException 
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
			throw new HTSException("用户不存在", ERROR_CODE_INVALID);
		
		int ck = userPushInfo.getId().equals(userId) ? Tag.TRUE : Tag.FALSE;
		HTWorldKeep keep = new HTWorldKeep(userId, new Date(), worldId,userPushInfo.getId(), ck, Tag.TRUE);
		worldKeepDao.saveKeep(keep);
		
		//收藏数+1
		Long count = worldKeepDao.queryKeepCount(worldId);
		int num = worldDao.updateKeepCount(worldId, count.intValue());
		if(num == 0)
			throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_INVALID);
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
		int num = worldDao.updateKeepCount(worldId, count.intValue());
		if(num == 0)
			throw new HTSException("记录无效", ERROR_CODE_INVALID);
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
			HTSException e = new HTSException("已经举报过");
			e.setErrorCode(ERROR_CODE_REPEAT_OPT);
			throw e;
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
	public HTWorldInteractDto getWorldInteract(Integer worldId, Integer userId, boolean isAddClick, 
			boolean trimExtras, Integer commentLimit, Integer likedLimit)
			throws Exception {
		HTWorldInteractDto dto = worldDao.queryHTWorldInteract(worldId);
		if(dto == null) {
			throw new HTSException("指定织图不存在");
		}
		if(!dto.getAuthorId().equals(userId)) {
			Integer isMututal = userConcernDao.queryIsMututal(userId, dto.getAuthorId());
			if(isMututal != null) {
				dto.getUserInfo().setIsMututal(isMututal);
			}
		}
		worldService.extractExtraInfo(true, true, userId, trimExtras, commentLimit, likedLimit, dto);
		userInfoService.extractVerify(dto);
		userInteractService.extractRemark(userId, dto);
//		if(isAddClick) {
//			worldService.addClickCount(worldId, 1);
//		}
		return dto;
	}

	@Override
	public List<HTWorldGeo> getWorldGeo(Integer userId) throws Exception {
		return worldDao.queryWorldGeo(userId);
	}

	@Override
	public void saveLikedWithoutLogin(Integer worldId) throws Exception {
		int randomUID = -(int)(Math.random() * 999999999);  // 负数表示未登陆用户id
		HTWorldLiked liked = new HTWorldLiked(randomUID, new Date(), worldId, 0, Tag.TRUE, Tag.TRUE);
		worldLikedDao.saveLiked(liked);
		
		//喜欢数+1
		Long count = worldLikedDao.queryLikedCount(worldId);
		int num = worldDao.updateLikeCount(worldId, count.intValue());
		if(num == 0)
			throw new HTSException(ERROR_MSG_INVALID, ERROR_CODE_INVALID);
	}
	
	@Override
	public void buildLikedUser(Integer maxId, final Integer worldId, final Integer userId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<UserConcernDto>() {

			@Override
			public List<UserConcernDto> getSerializables(
					RowSelection rowSelection) {
				List<UserConcernDto> list = worldLikedDao.queryLikedUser(worldId, userId, rowSelection);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public List<UserConcernDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<UserConcernDto> list = worldLikedDao.queryLikedUser(maxId, worldId, userId, rowSelection);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
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
		List<HTWorldThumbnail> list = worldLikedDao.queryLikeOthersWorldThumbnail(limit, userId, authorId);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
		long total = worldLikedDao.queryLikeOthersWorldCount(userId, authorId);
		jsonMap.put(OptResult.JSON_KEY_TOTAL_COUNT, total);
		
		
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
		long count = 0l;
		if(minId == null || minId == 0)
			count = worldLikedDao.queryLikeMeCount(userId);
		else 
			count = worldLikedDao.queryLikeMeCount(minId, userId);
		Integer maxId = worldLikedDao.queryMaxLikeMeId(userId);
		jsonMap.put(OptResult.JSON_KEY_TOTAL_COUNT, count);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
	}

	@Override
	public void buildCommentReId(String idsStr, Map<String, Object> jsonMap) {
		if(!StringUtil.checkIsNULL(idsStr)) {
			Integer[] ids = StringUtil.convertStringToIds(idsStr);
			if(ids != null && ids.length > 0) {
				List<HTWorldCommentReId> list = worldCommentDao.queryReId(ids);
				jsonMap.put(OptResult.JSON_KEY_COMMENTS, list);
				return;
			}
		}
		jsonMap.put(OptResult.JSON_KEY_COMMENTS, new ArrayList<HTWorldCommentReId>());
	}
}
