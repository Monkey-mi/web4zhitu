package com.hts.web.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.pojo.HTWorldGeo;
import com.hts.web.common.pojo.ObjectWithLiked;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 织图互动子模块业务逻辑访问接口
 * <p>
 * 
 * 创建时间：2013-8-3
 * 
 * @author ztj
 * 
 */
public interface ZTWorldInteractService extends BaseService {

	/**
	 * 构建织图世界评论分页列表
	 * 
	 * @param worldId
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildComments(Integer userId, Integer worldId, int maxId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 构建开放评论
	 * 
	 * @param worldId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildOpenComment(final Integer worldId, int maxId, 
			int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 保存织图评论
	 * 
	 * @param im
	 * @param worldId
	 * @param worldAuthorId
	 * @param authorId
	 * @param content
	 * @param jsonMap
	 * @return
	 * @throws Exception
	 */
	public void saveComment(Boolean im, Integer worldId, Integer worldAuthorId,
			Integer authorId, String content, String atIdsStr, String atNamesStr,
			Map<String, Object> jsonMap) throws Exception;

	/**
	 * 保存评论回复
	 * 
	 * @param im 运营
	 * @param worldId
	 * @param worldAuthorId
	 * @param authorId
	 * @param content
	 * @param reId
	 * @param reAuthorId
	 * @param atIdsStr
	 * @param atNamesStr
	 * @param jsonMap
	 * @throws Exception
	 */
	public void saveReply(Boolean im, Integer worldId, Integer worldAuthorId,
			Integer authorId, String content, Integer reId, Integer reAuthorId,
			String atIdsStr, String atNamesStr, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 系统内部使用回复接口
	 * 
	 * @param authorId 评论作者id
	 * @param content 回复内容,格式为[评论作者回复@被回复人: 回复内容],如Lulu回复@Tom: 哈哈
	 * @param worldId 织图id
	 * @param reId 回复的评论id
	 * @throws Exception
	 */
	public void saveReply4Admin(Integer authorId, String content,
			Integer worldId, Integer reId) throws Exception;
	

	/**
	 * 织图作者评论自己的织图的时候，参与该织图讨论的用户都会收到这条评论消息
	 * 
	 * @param comment
	 * @author lynch 2015-11-02
	 */
	public void broadcastComment(HTWorldComment comment) throws Exception;
	
	/**
	 * 删除评论
	 * 
	 * @param id
	 * @param userId
	 * @throws Exception
	 */
	public void deleteComment(Integer id, Integer userId) throws Exception;

	/**
	 * 保存织图喜欢
	 * 
	 * @param im
	 * @param userId
	 * @param worldId
	 * @param worldAuthorId
	 * @throws Exception
	 */
	public PushStatus saveLiked(Boolean im, Integer userId, Integer worldId,
			Integer worldAuthorId) throws Exception;

	/**
	 * 取消喜欢
	 * 
	 * @param userId
	 * @param worldId
	 * @throws Exception
	 */
	public void cancelLiked(Integer userId, Integer worldId) throws Exception;

	/**
	 * 保存织图收藏
	 * 
	 * @param userId
	 * @param worldId
	 * @throws Exception
	 */
	public void saveKeep(Integer userId, Integer worldId) throws Exception;

	/**
	 * 取消收藏
	 * 
	 * @param userId
	 * @param worldId
	 * @throws Exception
	 */
	public void cancelKeep(Integer userId, Integer worldId) throws Exception;

	/**
	 * 保存织图举报
	 * 
	 * @param userId
	 * @param worldId
	 * @throws Exception
	 */
	public void saveReport(Integer userId, Integer worldId, String reportContent)
			throws Exception;

	/**
	 * 取消举报
	 * 
	 * @param userId
	 * @param worldId
	 * @throws Exception
	 */
	public void cancelReport(Integer userId, Integer worldId) throws Exception;

	/**
	 * 获取织图和用户互动信息
	 * 
	 * @param worldId
	 * @param userId
	 * @param isAddClick
	 * @param trimExtras
	 * @param commentLimit
	 * @param likedLimit
	 * @return
	 * @throws Exception
	 */
	public void getWorldInteract(Integer worldId, Integer userId,
			boolean trimExtras, Integer commentLimit,
			Integer likedLimit, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 获取指定用户的织图位置信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<HTWorldGeo> getWorldGeo(Integer userId) throws Exception;

	/**
	 * 未登陆情况下喜欢织图
	 * 
	 * @param worldId
	 * @throws Exception
	 */
	public void saveLikedWithoutLogin(Integer worldId) throws Exception;

	/**
	 * 构建喜欢指定织图用户列表
	 * 
	 * @param maxId
	 * @param worldId
	 * @param userId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLikedUser(Integer maxId, Integer worldId, Integer userId,
			int start, int limit, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 构建某用户喜欢指定用户的织图列表
	 * 
	 * @param limit
	 * @param likerId
	 * @param authorId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLikeOthersWorldThumbnail(Integer limit, Integer userId, Integer authorId,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存评论举报
	 * 
	 * @param userId
	 * @param commentId
	 * @param content
	 * @throws Exception
	 */
	public void saveCommentReport(Integer userId, Integer commentId, 
			String content) throws Exception;
	
	/**
	 * 提取喜欢标记
	 * 
	 * @param userId
	 * @param list
	 */
	public void extractLiked(Integer userId, List<? extends ObjectWithLiked> list);
	
	/**
	 * 构建被赞总数
	 * 
	 * @param maxId
	 * @param userId
	 * @param jsonMap
	 */
	public void buildLikeMeCount(Integer maxId, Integer userId,
			Map<String, Object> jsonMap);
	
	/**
	 * 构建评论reid列表
	 * 
	 * @param idsStr
	 * @param jsonMap
	 */
	public void buildCommentReId(String idsStr, Map<String, Object> jsonMap);
	
	
	/**
	 * 检测织图是否有效
	 * 
	 * @param worldId
	 */
	public boolean checkWorldValid(Integer worldId);
	
	/**
	 * 检测评论是否有效
	 * 
	 * @param commentId
	 * @return
	 */
	public boolean checkCommentValid(Integer commentId);
	
	/**
	 * 过滤评论开头的' : '
	 * @param content
	 * @return
	 */
	public String trimColon2Comment(String content);
	
	/**
	 * 将@替换为回复
	 * @param content
	 */
	public String replaceAt2Reply(String content);
	
	/**
	 * 根据短链查询织图和用户信息
	 * 
	 * @param shortLink
	 * @param likedLimit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void getWorldInteractByLink(String shortLink, Integer likedLimit, 
			Map<String, Object> jsonMap) throws Exception;
	
}
