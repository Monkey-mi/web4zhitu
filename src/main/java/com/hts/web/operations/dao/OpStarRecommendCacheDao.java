package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpStarRecommendDto;

public interface OpStarRecommendCacheDao extends BaseCacheDao{
	/**
	 * 查询达人推荐缓存
	 * @return
	 */
	public List<OpStarRecommendDto> queryStarRecommendFromCache();
	
	/**
	 * 更新达人推荐缓存
	 * @param starRecommendDtoList
	 * @throws Exception
	 */
	public void updateStarRecommendCache(List<OpStarRecommendDto> starRecommendDtoList)throws Exception;
}
