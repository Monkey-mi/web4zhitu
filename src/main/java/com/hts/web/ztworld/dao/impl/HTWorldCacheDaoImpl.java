package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.ztworld.dao.HTWorldCacheDao;

@Repository("HTSHTWorldCacheDao")
public class HTWorldCacheDaoImpl extends BaseCacheDaoImpl<HTWorld> implements
		HTWorldCacheDao {

	@Override
	public void saveLatestCache(HTWorld world) {
		getRedisTemplate().opsForList().leftPush(CacheKeies.ZTWORLD_LATEST_WORLD, world);
	}

	@Override
	public List<HTWorld> queryCacheLatestList(int limit) {
		ListOperations<String, HTWorld> listOpt = getRedisTemplate().opsForList();
		List<HTWorld> list = listOpt.range(CacheKeies.ZTWORLD_LATEST_WORLD, 0, limit-1);
		return list;
	}
	

}
