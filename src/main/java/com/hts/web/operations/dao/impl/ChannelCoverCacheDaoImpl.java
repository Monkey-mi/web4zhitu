package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.operations.dao.ChannelCoverCacheDao;

@Repository("HTSChannelCoverCacheDao")
public class ChannelCoverCacheDaoImpl extends BaseCacheDaoImpl<OpChannelCover> implements
		ChannelCoverCacheDao {

	@Override
	public List<OpChannelCover> queryCacheCover() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL_COVER, 0, -1);
	}
}
