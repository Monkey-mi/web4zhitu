package com.hts.web.ztworld.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldLikeMe;
import com.hts.web.common.pojo.HTWorldLikeMeThumb;
import com.hts.web.common.pojo.HTWorldLiked;
import com.hts.web.common.pojo.HTWorldLikedUser;
import com.hts.web.common.pojo.HTWorldThumbnail;
import com.hts.web.common.pojo.UserConcernDto;

/**
 * <p>
 * 织图世界喜欢数据访问对象
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface HTWorldLikedDao extends BaseDao {

	/**
	 * 查询织图喜欢
	 * 
	 * @param userId
	 * @param worldId
	 */
	public HTWorldLiked queryLiked(Integer userId, Integer worldId);
	
	
	/**
	 * 保存织图喜欢
	 * 
	 * @param conn
	 * @param liked
	 */
	public Integer saveLiked(HTWorldLiked liked);
	
	/**
	 * 更新喜欢
	 * 
	 * @param userId
	 * @param worldId
	 */
	public void updateLiked(Integer userId, Integer worldId, Integer valid, Date date);
	
	
	/**
	 * 查询喜欢总数
	 * 
	 * @param worldId
	 * @return
	 */
	public long queryLikedCount(Integer worldId);
	
	/**
	 * 更新推送标记
	 * 
	 * @param id
	 * @param valid
	 */
	public void updatePushed(Integer id, Integer valid);
	
	/**
	 * 查询指定用户喜欢消息
	 * 
	 * @param userId
	 * @param rowSelection
	 */
	public List<HTWorldLiked> queryUserLiked(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询指定用户喜欢消息
	 * 
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 */
	public List<HTWorldLiked> queryUserLikedByMaxId(Integer userId, Integer maxId, 
			RowSelection rowSelection);
	
	/**
	 * 根据最小id查询指定用户喜欢消息
	 * @param userId
	 * @param minId
	 * @param rowSelection
	 */
	public List<HTWorldLiked> queryUserLikedByMinId(Integer userId, Integer minId, 
			RowSelection rowSelection);
	
	/**
	 * 查询指定用户喜欢总数
	 * 
	 * @param userId
	 * @return
	 */
	public long queryUnCheckUserLikedCount(Integer userId);
	
	/**
	 * 根据最大id查询指定用户喜欢总数
	 * @param userId
	 * @param maxId
	 * @return
	 */
	public long queryUserLikedCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 根据最小id查询指定用户喜欢总数
	 * @param userId
	 * @param maxId
	 * @return
	 */
	public long queryUserLikedCountByMinId(Integer userId, Integer minId);
	
	/**
	 * 更新未读喜欢消息
	 * @param userId
	 */
	public void updateUnreadUserLiked(Integer userId);

	/**
	 * 查询喜欢指定织图的运营用户总数
	 * 
	 * @param worldId
	 */
	public long queryOperationsLikedUserCountByWorldId(Integer worldId);
	
	/**
	 * 根据最大id查询喜欢指定织图的运营用户总数
	 * 
	 * @param worldId
	 */
	public long queryOperationsLikedUserCountByWorldIdAndMaxId(Integer maxId, Integer worldId);
	
	/**
	 * 查询喜欢织图的用户信息
	 * 
	 * @param worldIds
	 * @param limit
	 * @param callback
	 * @return
	 */
	public void queryLikedUserByLimit(Integer[] worldIds, Integer limit, 
			RowCallback<HTWorldLikedUser> callback);
	
	/**
	 * 查询喜欢织图的用户信息
	 * 
	 * @param worldId
	 * @param limit
	 * @param callback
	 */
	public void queryLikedUserByLimit(Integer worldId,
			Integer limit, RowCallback<HTWorldLikedUser> callback);
	
	/**
	 * 查询喜欢指定织图用户信息
	 * 
	 * @param worldId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryLikedUser(Integer worldId, Integer userId, 
			RowSelection rowSelection);
	
	/**
	 * 查询喜欢指定织图用户信息
	 * 
	 * @param maxId
	 * @param worldId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryLikedUser(Integer maxId, Integer worldId, Integer userId, 
			RowSelection rowSelection);
	
	/**
	 * 查询用户喜欢总数
	 * 
	 * @param userId
	 * @return
	 */
	public long queryUserLikeCount(Integer userId);
	
	/**
	 * 查询某用户喜欢指定用户的织图列表
	 * 
	 * @param limit
	 * @param userId
	 * @param authorId
	 * @return
	 */
	public List<HTWorldThumbnail> queryLikeOthersWorldThumbnail(Integer limit, 
			Integer userId, Integer authorId);
	
	/**
	 * 查询某用户喜欢指定用户的织图总数
	 * 
	 * @param userId
	 * @param authorId
	 * @return
	 */
	public long queryLikeOthersWorldCount(Integer userId, Integer authorId);
	
	/**
	 * 查询喜欢标记
	 * 
	 * @param userId
	 * @param worldIds
	 * @param callback
	 */
	public void queryLiked(Integer userId, Integer[] worldIds, RowCallback<Integer> callback);
	
	/**
	 * 查询被赞次数
	 * 
	 * @param worldAuthorId
	 * @return
	 */
	public long queryLikeMeCount(Integer worldAuthorId);
	
	/**
	 * 查询喜欢我的用户信息
	 *  
	 * @param userId
	 * @param limit
	 * @return
	 */
	public List<HTWorldLikeMe> queryLikeMe(Integer userId, Integer limit);
	
	/**
	 * 查询喜欢我的用户信息
	 * 
	 * @param maxId
	 * @param userId
	 * @param limit
	 * @return
	 */
	public List<HTWorldLikeMe> queryLikeMe(Integer maxId, Integer userId,
			Integer limit);
	
	
	/**
	 * 查询喜欢我的用户分组信息
	 * 
	 * @param minId
	 * @param userId
	 * @return
	 */
	public List<HTWorldLikeMe> queryLikeMeByGroup(Integer minId, Integer userId);
	
	/**
	 * 根据用户ids查询我被喜欢的织图信息,不分页
	 * 
	 * @param minId
	 * @param authorId
	 * @param callback
	 */
	public void queryLikeMeWorld(Integer minId, Integer authorId,
			RowCallback<HTWorldLikeMeThumb> callback);

	/**
	 * 根据最小日期查询最小id
	 * 
	 * @param minDate
	 * @return
	 */
	public Integer queryMinIdByMinDate(Integer authorId, Date minDate);
	
	/**
	 * 根据最大id查询被赞总数
	 * 
	 * @param minId
	 * @param authorId
	 * @return
	 */
	public long queryLikeMeCount(Integer minId, Integer authorId);
	
	/**
	 * 查询被赞最大记录id
	 * 
	 * @param authorId
	 * @return
	 */
	public Integer queryMaxLikeMeId(Integer authorId);
}
