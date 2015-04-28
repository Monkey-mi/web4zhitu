package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpStarRecommendDto;
import com.hts.web.operations.dao.OpStarRecommendCacheDao;

/**
 * 达人推荐 cache 查询
 * @author zxx
 *
 */
@Repository("HTSOpStarRecommendCacheDao")
public class OpStarRecommendCacheDaoImpl extends BaseCacheDaoImpl<OpStarRecommendDto> implements OpStarRecommendCacheDao{

	@Override
	public List<OpStarRecommendDto> queryStarRecommendFromCache() {
		// TODO Auto-generated method stub
		return getRedisTemplate().opsForList().range(CacheKeies.OP_STAR_RECOMMEND, 0, -1);
	}

	@Override
	public void updateStarRecommendCache(List<OpStarRecommendDto> starRecommendDtoList)throws Exception{
		if(starRecommendDtoList.size() > 0){
			if(getRedisTemplate().hasKey(CacheKeies.OP_STAR_RECOMMEND)){
				getRedisTemplate().delete(CacheKeies.OP_STAR_RECOMMEND);
			}
			OpStarRecommendDto[] starRecommendArray = new OpStarRecommendDto[starRecommendDtoList.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_STAR_RECOMMEND, starRecommendDtoList.toArray(starRecommendArray));
		} else {
			throw new Exception("updateStarRecommendCache Failed! size is zero !");
		}
	}
}
