package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpNearCityGroupDto;

/**
 * 附近推荐城市缓存持久化接口
 * @author zxx 2015-12-3 18:24:51
 *
 */
public interface NearRecommendCityCacheDao extends BaseCacheDao{
	/**
	 * 更新推荐城市缓存
	 * @param cityGroupList
	 * @author zxx 2015-12-3 18:25:27
	 */
	public void updateNearRecommendCityCache(List<OpNearCityGroupDto> cityGroupList);
	
	/**
	 * 查询推荐城市缓存
	 * @return
	 * @author zxx 2015-12-3 18:26:13
	 */
	public List<OpNearCityGroupDto> queryNearRecommendCityCache();
}
