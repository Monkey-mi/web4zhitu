package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelTheme;
import com.hts.web.operations.dao.ChannelThemeCacheDao;
import com.hts.web.operations.dao.ChannelThemeDao;

@Repository("HTSChannelThemeCacheDao")
public class ChannelThemeCacheDaoImpl extends BaseCacheDaoImpl<OpChannelTheme> implements
		ChannelThemeCacheDao {

	@Autowired
	private ChannelThemeDao themeDao;

	@Override
	public void updateTheme() {
		List<OpChannelTheme> clist = themeDao.queryAllTheme();
		clist.add(0, new OpChannelTheme(0, "推荐频道", Tag.FALSE));
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL_THEME)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL_THEME);
		}
		if(clist.size() > 0) {
			OpChannelTheme[] list = new OpChannelTheme[clist.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL_THEME, clist.toArray(list));
		}
	}

	@Override
	public List<OpChannelTheme> queryTheme() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL_THEME, 0, -1);
	}
	
}
