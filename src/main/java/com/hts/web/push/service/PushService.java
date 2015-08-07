package com.hts.web.push.service;

import java.util.List;

import org.springframework.core.task.TaskExecutor;

import com.hts.web.base.HTSException;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;

/**
 * <p>
 * 消息推送服务接口
 * </p>
 * 
 * 创建时间：2013-6-23
 * 
 * @author ztj
 * 
 */
public interface PushService {

	/**
	 * 评论推送
	 * 
	 * @param id
	 * @param authorId
	 * @param content
	 * @param userPushInfo
	 * @param shield
	 * @throws Exception
	 */
	public void pushComment(final Integer id, Integer authorId, Integer worldId, Integer wauthorId, 
			String content, UserPushInfo userPushInfo, Integer shield) throws Exception;

	/**
	 * 回复推送
	 * 
	 * @param id
	 * @param authorId
	 * @param content
	 * @param userPushInfo
	 * @param shield
	 * @throws Exception
	 */
	public void pushReply(Integer id, Integer authorId, Integer worldId, 
			Integer watuhorId, Integer reId, String content, UserPushInfo userPushInfo,
			Integer shield) throws Exception;

	/**
	 * 喜欢推送
	 * 
	 * @param id
	 * @param userId
	 * @param worldId
	 * @param userPushInfo
	 * @param shield
	 * @throws Exception
	 */
	public void pushLiked(final Integer id, Integer userId, Integer worldId, Integer wauthorId,
			UserPushInfo userPushInfo, Integer shield) throws Exception;

	/**
	 * 关注推送
	 * 
	 * @param id
	 * @param userId
	 * @param concernId
	 * @param userPushInfo
	 * @param shield
	 */
	public void pushConcern(Integer id, Integer userId, Integer concernId,
			UserPushInfo userPushInfo, Integer shield);

	/**
	 * 用户私信推送
	 * 
	 * @param senderId
	 * @param content
	 * @param userPushInfo
	 * @throws Exception
	 */
	public void pushMiShuMessage(Integer senderId, String content,
			UserPushInfo userPushInfo, PushFailedCallback callback)
			throws Exception;

	/**
	 * 系统私信推送
	 * 
	 * @param title
	 * @param senderId
	 * @param content
	 * @param userPushInfo
	 * @throws Exception
	 */
	public void pushSysMessage(String title, Integer senderId, String content,
			UserPushInfo userPushInfo, Integer sysType, PushFailedCallback callback)
			throws Exception;
	
	/**
	 * 推送公告
	 * 
	 * @param bulletin
	 * @throws Exception
	 */
	public void pushBulletin(Integer pushAction, String bulletin, String sid, List<Integer> recipientIds) throws Exception;
	
	/**
	 * 屏蔽消息推送
	 * 
	 * @param userId
	 * @param shieldId
	 * @throws Exception
	 */
	public void pushShield(Integer userId, Integer shieldId, Integer shield) throws Exception;
	
	/**
	 * 推送APP消息
	 * 
	 * @param content
	 * @param pushAction
	 * @param pushId
	 * @param phoneTypeList
	 * @throws Exception
	 */
	public void pushAppMessage(String content) throws Exception;

	/**
	 * 向多个用户推送消息
	 * 
	 * @param content
	 * @param worldId
	 * @param pushInfoList
	 * @throws Exception
	 */
	public void pushListMessage(String content, Integer pushAction,
			String pushId, List<UserPushInfo> pushInfoList,
			PushFailedCallback callback) throws Exception;
	
	
	/**
	 * 推送用户推荐全局系统消息
	 * 
	 * @param recUid　推荐用户id
	 * @param content
	 * @throws Exception
	 */
	public void pushUserRecSysMsg(Integer recUid, String content) throws Exception;
	
	public void apnsPushTest(String pushToken) throws HTSException;
	
	public TaskExecutor getPushExecutor();

}
