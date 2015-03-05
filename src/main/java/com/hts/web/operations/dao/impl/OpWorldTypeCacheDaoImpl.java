package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpWorldType;
import com.hts.web.operations.dao.OpWorldTypeCacheDao;

@Repository("HTSOpWorldTypeCacheDao")
public class OpWorldTypeCacheDaoImpl extends BaseCacheDaoImpl<OpWorldType> implements OpWorldTypeCacheDao {

	@Override
	public List<OpWorldType> queryCacheLabel(int limit) {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_TYPE, 0, limit);
	}
	
	@Override
	public OpWorldType queryCacheLabelByIndex(int index) {
		return getRedisTemplate().opsForList().index(CacheKeies.OP_TYPE, index);
	}

}
