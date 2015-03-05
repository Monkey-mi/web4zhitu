package com.hts.web.ztworld.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldFilterLogo;
import com.hts.web.ztworld.dao.HTWorldFilterLogoCacheDao;

@Repository("HTSHTWorldFilterLogoCacheDao")
public class HTWorldFilterLogoCacheDaoImpl extends BaseCacheDaoImpl<HTWorldFilterLogo>
		implements HTWorldFilterLogoCacheDao {

	@Override
	public HTWorldFilterLogo queryLogo() {
		return getRedisTemplate().opsForValue().get(CacheKeies.ZTWORLD_FILTER_LOGO);
	}

}
