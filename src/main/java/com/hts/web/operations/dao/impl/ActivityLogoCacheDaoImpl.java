package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpActivityLogo;
import com.hts.web.operations.dao.ActivityLogoCacheDao;

@Repository("HTSActivityLogoCacheDao")
public class ActivityLogoCacheDaoImpl extends BaseCacheDaoImpl<OpActivityLogo>
	implements ActivityLogoCacheDao {

	@Override
	public List<OpActivityLogo> queryCacheLogo(int limit) {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_ACTIVITY_LOGO, 0, limit);
	}
}
