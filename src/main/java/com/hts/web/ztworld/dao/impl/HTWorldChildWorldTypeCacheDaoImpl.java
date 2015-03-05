package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldChildWorldType;
import com.hts.web.ztworld.dao.HTWorldChildWorldTypeCacheDao;

@Repository("HTSHTWorldChildWorldTypeCacheDao")
public class HTWorldChildWorldTypeCacheDaoImpl extends BaseCacheDaoImpl<HTWorldChildWorldType>
		implements HTWorldChildWorldTypeCacheDao {

	@Override
	public List<HTWorldChildWorldType> queryLatestType() {
		long size = getRedisTemplate().opsForList().size(CacheKeies.ZTWORLD_CHILD_LATEST_TYPE);
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_CHILD_LATEST_TYPE, 
				0, size - 1);
	}

}
