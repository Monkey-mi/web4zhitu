package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpNearCityGroupDto;
import com.hts.web.operations.dao.NearRecommendCityCacheDao;

/**
 * 附近推荐城市缓存持久化
 * @author zxx 2015-12-3 19:47:18
 *
 */
@Repository("NearRecommendCityCacheDao")
public class NearRecommendCityCacheDaoImpl extends BaseCacheDaoImpl<OpNearCityGroupDto> implements NearRecommendCityCacheDao{

	@Override
	public void updateNearRecommendCityCache(
			List<OpNearCityGroupDto> cityGroupList) {
		if(getRedisTemplate().hasKey(CacheKeies.OP_NEAR_RECOMMEND_CITY)){
			getRedisTemplate().delete(CacheKeies.OP_NEAR_RECOMMEND_CITY);
		}
		if(cityGroupList != null && cityGroupList.size()>0){
			OpNearCityGroupDto[] array = new OpNearCityGroupDto[cityGroupList.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_NEAR_RECOMMEND_CITY, cityGroupList.toArray(array));
		}
	}

	@Override
	public List<OpNearCityGroupDto> queryNearRecommendCityCache() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_NEAR_RECOMMEND_CITY,0,-1);
	}

}
