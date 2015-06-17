package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpMsgStartPage;
import com.hts.web.operations.dao.StartPageCacheDao;

@Repository("HTSStartPageCacheDao")
public class StartPageCacheDaoImpl extends BaseCacheDaoImpl<OpMsgStartPage> implements
		StartPageCacheDao {

	@Override
	public List<OpMsgStartPage> queryStartPage() {
		return getRedisTemplate().boundListOps(
				CacheKeies.OP_MSG_START_PAGE).range(0, -1);
		
	}
	
	@Override
	public void updateStartPage(List<OpMsgStartPage> pages) {
		if(getRedisTemplate().hasKey(CacheKeies.OP_MSG_START_PAGE)) {
			getRedisTemplate().delete(CacheKeies.OP_MSG_START_PAGE);
		}
		if(pages.size() > 0) {
			OpMsgStartPage[] list = new OpMsgStartPage[pages.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_MSG_START_PAGE, pages.toArray(list));
		}
	}
}
