package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldType;
import com.hts.web.ztworld.dao.TypeCacheDao;

@Repository("HTSTypeCacheDao")
public class TypeCacheDaoImpl extends BaseCacheDaoImpl<HTWorldType> implements TypeCacheDao {

	@Override
	public List<HTWorldType> queryType() {
		return getRedisTemplate().boundListOps(CacheKeies.ZTWORLD_TYPE).range(0, -1);
	}
	
}
