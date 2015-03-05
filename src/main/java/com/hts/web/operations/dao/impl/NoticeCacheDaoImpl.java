package com.hts.web.operations.dao.impl;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpNotice;
import com.hts.web.operations.dao.NoticeCacheDao;

@Repository("HTSNoticeCacheDao")
public class NoticeCacheDaoImpl extends BaseCacheDaoImpl<OpNotice> implements
		NoticeCacheDao {

	
	@Override
	public OpNotice queryNotice(Integer phoneCode) {
		HashOperations<String, Integer, OpNotice> ops = getRedisTemplate().opsForHash();
		return ops.get(CacheKeies.OP_NOTICE, phoneCode);
	}

}
