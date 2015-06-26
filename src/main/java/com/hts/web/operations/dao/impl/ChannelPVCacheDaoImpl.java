package com.hts.web.operations.dao.impl;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.operations.dao.ChannelPVCacheDao;

@Repository("HTSChannelPVCacheDao")
public class ChannelPVCacheDaoImpl extends BaseCacheDaoImpl<Integer> implements
		ChannelPVCacheDao {

	@Override
	public void inrc(Integer channelId) {
		BoundHashOperations<String, Integer, Integer> bound = 
				getRedisTemplate().boundHashOps(CacheKeies.OP_CHANNEL_PV);
		Integer count = bound.get(channelId);
		if(count == null) {
			count = 0;
		}
		bound.put(channelId, ++count);
	}

}
