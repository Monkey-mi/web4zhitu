package com.hts.web.operations.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelTopOneTitle;
import com.hts.web.operations.dao.ChannelTopOneTitleCacheDao;

@Repository("HTSChannelTopOneTitleCacheDao")
public class ChannelTopOneTitleCacheDaoImpl extends BaseCacheDaoImpl<OpChannelTopOneTitle>
		implements ChannelTopOneTitleCacheDao {

	@Override
	public OpChannelTopOneTitle queryTitle() {
		return getRedisTemplate().opsForValue().get(CacheKeies.OP_CHANNEL_TOP_ONE_TITLE);
	}
}
