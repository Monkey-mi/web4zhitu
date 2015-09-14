package com.hts.web.operations.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.operations.dao.ChannelAutoRejectIdCacheDao;

@Repository("HTSChannelAutoRejectIdCacheDao")
public class ChannelAutoRejectIdCacheDaoImpl extends BaseCacheDaoImpl<Integer>
	implements ChannelAutoRejectIdCacheDao {

	@Override
	public boolean isAutoReject(Integer id) {
		return getRedisTemplate().boundSetOps(CacheKeies.OP_CHANNEL_AUTO_REJECT_ID).isMember(id);
	}

}
