package com.hts.web.userinfo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.common.pojo.UserMsgStatus;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户消息业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-28
 * @author ztj
 *
 */
public interface UserMsgService extends BaseService {
	
	/**
	 * 保存私信消息
	 * 
	 * @param senderId
	 * @param recipientId
	 * @param content
	 * @throws Exception
	 */
	public Integer saveUserMsg(Integer senderId, Integer recipientId,
			String content) throws Exception;
	
	/**
	 * 构建和指定用户的私信列表
	 * 
	 * @param userId
	 * @param otherId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildUserMsg(Integer userId, Integer otherId, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 查询私信列表
	 * @param userId
	 * @param otherId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgDto> queryUserMsg(Integer userId, Integer otherId, 
			Integer maxId, RowSelection rowSelection);
	
	/**
	 * 查询私信总数
	 * 
	 * @param userId
	 * @param otherId
	 * @param maxId
	 * @return
	 */
	public long queryUserMsgCount(Integer userId, Integer otherId, Integer maxId);
	
	/**
	 * 构建接收到的消息列表
	 * 
	 * @param userId
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSysMsg(Integer userId, int maxId, int start, int limit,
			Boolean trimTotal, Boolean trimUserRecMsg, Boolean clearUnCheck, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 更新消息有效性
	 * 
	 * @param contentId
	 */
	public void delUserMsg(Integer contentId, Integer userId, Integer otherId) throws Exception;
	
	/**
	 * 保存欢迎私信
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void saveUserWelcomeMsg(Integer userId) throws Exception;
	
	/**
	 * 推送欢迎私信
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void pushWelcomeMsg(Integer userId) throws Exception;
	
	/**
	 * 构建未读消息列表
	 * 
	 * @param userId
	 * @param jsonMap
	 */
	public void buildUnreadSysMsgCount(Integer userId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 删除系统消息
	 * 
	 * @param recipientId
	 * @param msgId
	 * @throws Exception
	 */
	public void deleteSysMsg(Integer recipientId, Integer msgId) throws Exception;
	
	/**
	 * 保存私信屏蔽
	 * 
	 * @param userId
	 * @param shieldId
	 * @throws Exception
	 */
	public void saveShield(Integer userId, Integer shieldId) throws Exception;
	
	/**
	 * 删除私信屏蔽
	 * @param userId
	 * @param shieldId
	 * @throws Exception
	 */
	public void deleteShield(Integer userId, Integer shieldId) throws Exception;
	
	/**
	 * 获取消息发送状态
	 * 
	 * @param userId
	 * @param joinId
	 * @return
	 * @throws Exception
	 */
	public UserMsgStatus getMsgStatus(Integer userId, Integer joinId) throws Exception;
	
//	/**
//	 * 获取喜欢消息列表
//	 * 
//	 * @param maxId
//	 * @param userId
//	 * @param authorId
//	 * @param start
//	 * @param limit
//	 * @param jsonMap
//	 * @throws Exception
//	 */
//	public void buildLikedMsg2(Integer maxId, Integer userId, Integer authorId, 
//			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception;
//	
//	/**
//	 * 获取我喜欢对方的消息列表
//	 * 
//	 * @param maxId
//	 * @param userId
//	 * @param authorId
//	 * @param start
//	 * @param limit
//	 * @param jsonMap
//	 * @throws Exception
//	 */
//	public void buildILikeOtherMsg(Integer maxId, Integer userId, Integer authorId, 
//			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception;
	
//	/**
//	 * 获取对方喜欢我的消息列表
//	 * 
//	 * @param maxId
//	 * @param userId
//	 * @param authorId
//	 * @param start
//	 * @param limit
//	 * @param jsonMap
//	 * @throws Exception
//	 */
//	public void buildOtherLikeMeMsg(Integer maxId, Integer userId, Integer authorId, 
//			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 构建所有喜欢我的信息
	 * 
	 * @param maxId
	 * @param userId
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLikeMeMsg(Integer maxId, Integer userId,
			Integer limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建所有喜欢我的信息（不分组）
	 * 
	 * @param maxId
	 * @param userId
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLikeMeMsgWithoutGroup(Integer maxId, Integer userId, 
			Integer limit, final Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建用户和织图缩略图
	 * 
	 * @param userIdStr
	 * @param worldIdStr
	 * @param jsonMap
	 * 
	 * @throws Exception
	 */
	public void buildThumbnail(String userIdStr, String worldIdStr, Integer userId,
			Integer likeOtherUID, Integer likeMeUID, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 保存at消息
	 * 
	 * @param atIdsStr atids字符串
	 * @param atNamesStr atnames字符串
	 * @param push 系统是否发送推送
	 * @param userId
	 * @param objType
	 * @param objId
	 * @param worldId
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-22
	 */
	public List<PushStatus> saveAtMsgs(String atIdsStr, String atNamesStr, 
			Boolean push, Integer userId, Integer objType, Integer objId,
			Integer worldId, String content) throws Exception;
	
	/**
	 * 保存at消息
	 * 
	 * @param atIds
	 * @param atNames
	 * @param rejectIds
	 * @param push
	 * @param userId
	 * @param objType
	 * @param objId
	 * @param worldId
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public List<PushStatus> saveAtMsgs(String atIdsStr, String atNamesStr, Set<Integer> rejectIds, 
			Boolean push, Integer userId, Integer objType, Integer objId,
			Integer worldId, String content) throws Exception;
	
	/**
	 * 查询atid
	 * @param objType
	 * @param objId
	 * @param atName
	 * @param index
	 * @return 0表示异常
	 * @throws Exception
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-22
	 */
	public Integer queryAtId(Integer objType, Integer objId, Integer index,
			String atName) throws Exception;

	/**
	 * 构建AT消息
	 * 
	 * @param atId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildAtMsg(Integer atId, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建评论消息列表
	 * 
	 * @param worldAuthorId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-28
	 */
	public void buildCommentMsg(final Integer worldAuthorId, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception;
	
	
}
