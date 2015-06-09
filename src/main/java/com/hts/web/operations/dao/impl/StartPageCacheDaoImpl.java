package com.hts.web.operations.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpMsgStartPage;
import com.hts.web.operations.dao.StartPageCacheDao;

@Repository("HTSStartPageCacheDao")
public class StartPageCacheDaoImpl extends BaseCacheDaoImpl<OpMsgStartPage> implements
		StartPageCacheDao {

	@Override
	public OpMsgStartPage queryStartPage() {
		return getRedisTemplate().boundValueOps(
				CacheKeies.OP_MSG_START_PAGE).get();
	}
	
	@Override
	public void updateStartPage(OpMsgStartPage page) {
		getRedisTemplate().boundValueOps(
				CacheKeies.OP_MSG_START_PAGE).set(page);
	}
}
