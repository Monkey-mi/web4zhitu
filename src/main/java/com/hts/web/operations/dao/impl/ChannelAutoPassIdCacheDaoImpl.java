package com.hts.web.operations.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.operations.dao.ChannelAutoPassIdCacheDao;

@Repository("HTSChannelAutoPassIdCacheDao")
public class ChannelAutoPassIdCacheDaoImpl extends BaseCacheDaoImpl<Integer>
	implements ChannelAutoPassIdCacheDao {

	@Override
	public boolean isAutoPass(Integer id) {
		return getRedisTemplate().boundSetOps(CacheKeies.OP_CHANNEL_AUTO_PASS_ID).isMember(id);
	}

}
