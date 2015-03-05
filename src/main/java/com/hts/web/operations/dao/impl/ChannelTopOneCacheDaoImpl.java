package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelTopOne;
import com.hts.web.operations.dao.ChannelTopOneCacheDao;

@Repository("HTSChannelTopOneCacheDao")
public class ChannelTopOneCacheDaoImpl extends BaseCacheDaoImpl<OpChannelTopOne> implements
		ChannelTopOneCacheDao {

	@Override
	public List<OpChannelTopOne> queryTopOne() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL_TOP_ONE, 0, -1);
	}

	@Override
	public OpChannelTopOne queryMaxTopOne() {
		return getRedisTemplate().opsForList().index(CacheKeies.OP_CHANNEL_TOP_ONE, 0);
	}

}
