package com.hts.web.operations.dao;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpActivityLikeRank;

/**
 * <p>
 * 活动点赞排行数据访问接口
 * </p>
 * 
 * 创建时间：2014-6-30
 * @author tianjie
 *
 */
public interface ActivityLikeRankDao extends BaseDao {

	
	/**
	 * 查询点赞排行
	 * 
	 * @param activityId
	 * @param userId
	 * @param callback
	 */
	public void queryLikeRank(Integer activityId, Integer[] userIds, 
			RowCallback<OpActivityLikeRank> callback);
}
