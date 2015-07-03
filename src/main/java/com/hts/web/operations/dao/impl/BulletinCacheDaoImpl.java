package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.operations.dao.BulletinCacheDao;

@Repository("HTSBulletinCacheDao")
public class BulletinCacheDaoImpl extends BaseCacheDaoImpl<OpMsgBulletin> implements
		BulletinCacheDao {

	@Override
	public List<OpMsgBulletin> queryBulletin() {
		return getRedisTemplate().boundListOps(CacheKeies.OP_MSG_BULLETIN).range(0, -1);
	}

	@Override
	public void updateBulletin(List<OpMsgBulletin> cacheList) {
		if(getRedisTemplate().hasKey(CacheKeies.OP_MSG_BULLETIN)) {
			getRedisTemplate().delete(CacheKeies.OP_MSG_BULLETIN);
		}
		if(cacheList.size() > 0) {
			OpMsgBulletin[] list = new OpMsgBulletin[cacheList.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_MSG_BULLETIN,
					cacheList.toArray(list));
		}
	}

}