package com.hts.web.operations.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.operations.dao.SysMsgCommonMaxIdCacheDao;

@Repository("HTSSysMsgCommonMaxIdCacheDao")
public class SysMsgCommonMaxIdCacheDaoImpl extends BaseCacheDaoImpl<Integer>implements SysMsgCommonMaxIdCacheDao {

	@Override
	public Integer queryMaxId() {
		Integer id;
		id = getRedisTemplate().boundValueOps(
				CacheKeies.OP_MSG_COMMON_SYSMSG_MAXID).get();
		if(id == null) {
			id = 0;
		}
		return id;
	}

	@Override
	public void updateMaxId(Integer id) {
		getRedisTemplate().boundValueOps(
				CacheKeies.OP_MSG_COMMON_SYSMSG_MAXID).set(id);;
	}
	
}
