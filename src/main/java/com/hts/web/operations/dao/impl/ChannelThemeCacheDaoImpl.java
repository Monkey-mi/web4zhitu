package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelTheme;
import com.hts.web.operations.dao.ChannelThemeCacheDao;

@Repository("HTSChannelThemeCacheDao")
public class ChannelThemeCacheDaoImpl extends BaseCacheDaoImpl<OpChannelTheme> implements
		ChannelThemeCacheDao {

	@Override
	public List<OpChannelTheme> queryTheme() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL_THEME, 0, -1);
	}
	
}
