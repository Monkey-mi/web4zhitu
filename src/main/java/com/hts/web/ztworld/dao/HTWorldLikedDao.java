package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldLiked;
import com.hts.web.common.pojo.HTWorldLikedInline;
import com.hts.web.common.pojo.HTWorldLikedUserDto;

/**
 * <p>
 * 织图世界喜欢数据访问对象
 * </p>
 * 
 * @author ztj 2013-8-3 2015-11-05
 * 
 */
public interface HTWorldLikedDao extends BaseDao {

	/**
	 * 保存喜欢
	 * 
	 * @param liked
	 * @author lynch 2015-11-05
	 */
	public Integer saveLiked(HTWorldLiked liked);
	
	/**
	 * 删除喜欢
	 * 
	 * @param userId
	 * @param worldId
	 * @return
	 * @author lynch 2015-11-05
	 */
	public void delLiked(Integer userId, Integer worldId);
	
	/**
	 * 查询喜欢总数
	 * 
	 * @param worldId
	 * @return
	 */
	public long queryLikedCount(Integer worldId);
	
	/**
	 * 查询喜欢织图的用户信息
	 * 
	 * @param worldIds
	 * @param limit
	 * @param callback
	 * @return
	 * @author lynch 2015-11-05
	 */
	public void queryInlineLikedByLimit(Integer[] worldIds, Integer limit, 
			RowCallback<HTWorldLikedInline> callback);
	
	/**
	 * 查询喜欢织图的用户信息
	 * 
	 * @param worldId
	 * @param limit
	 * @param callback
	 * @author lynch 2015-11-05
	 */
	public void queryInlineLikedByLimit(Integer worldId,
			Integer limit, RowCallback<HTWorldLikedInline> callback);
	
	/**
	 * 查询喜欢指定织图用户信息
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-11-05
	 */
	public List<HTWorldLikedUserDto> queryLikedUser(Integer worldId, 
			RowSelection rowSelection);
	
	/**
	 * 查询喜欢指定织图用户信息
	 * 
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-11-05
	 */
	public List<HTWorldLikedUserDto> queryLikedUser(Integer maxId, Integer worldId, 
			RowSelection rowSelection);
	
	/**
	 * 查询喜欢标记
	 * 
	 * @param userId
	 * @param worldIds
	 * @param callback
	 * @author lynch 2015-11-05
	 */
	public void queryLiked(Integer userId, Integer[] worldIds, RowCallback<Integer> callback);
	
	
	/**
	 * 判断该用户是否喜欢过织图
	 * 
	 * @param userId
	 * @param worldId
	 * @return
	 * @author lynch 2015-11-05
	 */
	public boolean isLiked(Integer userId, Integer worldId);
	
	
}
