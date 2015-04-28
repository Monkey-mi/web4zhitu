package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpStarRecommendDto;

public interface OpStarRecommendDao extends BaseDao{
	/**
	 * 查询推荐用户
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<OpStarRecommendDto> queryStarRecommend(Integer maxId,Integer start,Integer limit);
	
	//public long queryStarRecommendTotalCount(Integer maxId);
	
	/**
	 * 更新activity值
	 * @param userId
	 * @param activty
	 */
	public void updateStarRecommendActivity(Integer userId,Integer activity);
}
