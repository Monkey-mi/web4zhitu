package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.operations.dao.ChannelCacheDao;

@Repository("HTSChannelCacheDao")
public class ChannelCacheDaoImpl extends BaseCacheDaoImpl<OpChannel> implements
		ChannelCacheDao {

	@Override
	public List<OpChannel> queryChannel() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL, 0, -1);
	}
	
}
