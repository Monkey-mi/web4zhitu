package com.hts.web.ztworld.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * 取消点赞数据访问接口
 * 
 * @author lynch 2015-11-05
 *
 */
public interface HTWorldLikedCancelDao extends BaseDao {

	/**
	 * 保存取消记录
	 * 
	 * @param userId
	 * @param worldId
	 */
	public void saveCancel(Integer userId, Integer worldId);

	/**
	 * 是否取消过
	 * 
	 * @param userId
	 * @param worldId
	 * @return
	 */
	public boolean isCancel(Integer userId, Integer worldId);
	
	/**
	 * 删除点赞取消记录
	 * 
	 * @param userId
	 * @param worldId
	 */
	public void delCancel(Integer userId, Integer worldId);
}
