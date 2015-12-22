package com.hts.web.stat.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.stat.dao.StatPVCacheDao;

@Repository("HTSStatPVCacheDao")
public class StatPVCacheDaoImpl extends StatBaseCacheDaoImpl<Long> implements StatPVCacheDao {

	@Override
	public void inc(String key) {
		getStatRedisTemplate().boundValueOps(key).increment(1);
	}
	
}
