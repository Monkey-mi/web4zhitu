package com.hts.web.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.HTWorldGeo;
import com.hts.web.common.pojo.HTWorldInteractDto;
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
	public void buildComments(Integer userId, Integer worldId, int sinceId, int maxId,
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
	public PushStatus saveComment(Boolean im, Integer worldId, Integer worldAuthorId,
			Integer authorId, String content) throws Exception;

	/**
	 * 保存评论回复
	 * 
	 * @param im
	 * @param worldId
	 * @param authorId
	 * @param content
	 * @param reId
	 * @param jsonMap
	 * @return
	 * @throws Exception
	 */
	public PushStatus saveReply(Boolean im, Integer worldId, Integer worldAuthorId,
			Integer authorId, String content, Integer reId, Integer reAuthorId) throws Exception;

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
	public HTWorldInteractDto getWorldInteract(Integer worldId, Integer userId,
			boolean isAddClick, boolean trimExtras, Integer commentLimit,
			Integer likedLimit) throws Exception;

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
}
