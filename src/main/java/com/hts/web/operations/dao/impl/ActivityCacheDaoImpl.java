package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.operations.dao.ActivityCacheDao;

@Repository("HTSActivityCacheDao")
public class ActivityCacheDaoImpl extends BaseCacheDaoImpl<OpActivity> implements
		ActivityCacheDao {
	
	@Override
	public List<OpActivity> queryActivity() {
//		long size = getRedisTemplate().opsForList().size(CacheKeies.OP_ACTIVITY);
		return getRedisTemplate().opsForList().range(CacheKeies.OP_ACTIVITY, 0, -1);
	}
	
	@Override
	public List<OpActivity> queryActivity(int limit) {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_ACTIVITY, 
				0, limit-1);
	}

	@Override
	public OpActivity queryActivity(String name) {
		HashOperations<String, String, OpActivity> hashOp = getRedisTemplate().opsForHash();
		return hashOp.get(CacheKeies.OP_ACTIVITY_HASH, name);
	}
	
	@Override
	public void addHashActivity(OpActivity activity) {
		HashOperations<String, String, OpActivity> hashOp = getRedisTemplate().opsForHash();
		hashOp.put(CacheKeies.OP_ACTIVITY_HASH, activity.getActivityName(), activity);
	}
	
	@Override
	public OpActivity queryMaxActivity() {
		return getRedisTemplate().opsForList().index(CacheKeies.OP_ACTIVITY, 0);
	}

	
}
