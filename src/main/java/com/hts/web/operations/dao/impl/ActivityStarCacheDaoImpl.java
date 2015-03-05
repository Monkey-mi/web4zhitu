package com.hts.web.operations.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpActivityStar;
import com.hts.web.operations.dao.ActivityStarCacheDao;

@Repository("HTSActivityStarCacheDao")
public class ActivityStarCacheDaoImpl extends BaseCacheDaoImpl<OpActivityStar> implements
		ActivityStarCacheDao {

	@Override
	public List<OpActivityStar> queryStar(Integer activityId) {
		HashOperations<String, Integer, List<OpActivityStar>> ops = 
				getRedisTemplate().opsForHash();
		List<OpActivityStar> list = ops.get(CacheKeies.OP_ACTIVITY_STAR, activityId);
		if(list == null) {
			return new ArrayList<OpActivityStar>();
		}
		return list;
	}

}
