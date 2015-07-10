package com.hts.web.plat.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.PlatConcern;
import com.hts.web.plat.dao.PlatConcernCacheDao;
import com.hts.web.plat.dao.PlatConcernDao;

@Repository("HTSPlatConcernCacheDao")
public class PlatConcernCacheDaoImpl extends BaseCacheDaoImpl<PlatConcern> implements
		PlatConcernCacheDao {

	private static Logger log = Logger.getLogger(PlatConcernCacheDaoImpl.class);
	
	@Autowired
	private PlatConcernDao platConcernDao;
	
	@Override
	public void saveConcern(PlatConcern concern) {
		BoundListOperations<String, PlatConcern> op = 
				getRedisTemplate().boundListOps(CacheKeies.PLAT_CONCERN);
		op.rightPush(concern);
	}
	
	@Override
	public void popConcern(int limit) throws Exception {
		BoundListOperations<String, PlatConcern> op = 
				getRedisTemplate().boundListOps(CacheKeies.PLAT_CONCERN);
		List<PlatConcern> list = op.range(0, limit-1);
		if(list == null || list.isEmpty()) {
			return;
		}
		
		for(int i = 0; i < list.size(); i++) {
			try {
				platConcernDao.pushConcern(list.get(i));
			} catch(Exception e) {
				log.warn("push plat concern err", e);
			}
			op.leftPop();
		}
	}

	@Override
	public void saveBeConcern(PlatConcern concern) {
		BoundListOperations<String, PlatConcern> op = 
				getRedisTemplate().boundListOps(CacheKeies.PLAT_BE_CONCERN);
		op.rightPush(concern);
	}

	@Override
	public void deleteBeConcern(int index) {
		BoundListOperations<String, PlatConcern> op = 
				getRedisTemplate().boundListOps(CacheKeies.PLAT_BE_CONCERN);
		PlatConcern concern = op.index(index);
		op.remove(0, concern);
	}

	@Override
	public List<PlatConcern> queryAllBeConcern() {
		BoundListOperations<String, PlatConcern> op = 
				getRedisTemplate().boundListOps(CacheKeies.PLAT_BE_CONCERN);
		return op.range(0, -1);
	}

}
