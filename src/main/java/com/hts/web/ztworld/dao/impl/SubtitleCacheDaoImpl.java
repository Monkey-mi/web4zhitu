package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldSubtitleDto;
import com.hts.web.ztworld.dao.SubtitleCacheDao;

@Repository("HTSSubtitleCacheDao")
public class SubtitleCacheDaoImpl extends BaseCacheDaoImpl<HTWorldSubtitleDto> implements
		SubtitleCacheDao {

	@Override
	public List<HTWorldSubtitleDto> querySubtitle() {
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_SUBTITLE, 0, -1);
	}
	
}
