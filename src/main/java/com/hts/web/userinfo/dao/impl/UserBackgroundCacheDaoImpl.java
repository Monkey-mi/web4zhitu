package com.hts.web.userinfo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.userinfo.dao.UserBackgroundCacheDao;

/**
 * 用户背景redis缓存数据实现类
 * 
 * @author zhangbo	2015年12月25日
 *
 */
@Repository("com.hts.web.userinfo.dao.impl.UserBackgroundCacheDaoImpl")
public class UserBackgroundCacheDaoImpl extends BaseCacheDaoImpl<String> implements UserBackgroundCacheDao {

	@Override
	public List<String> queryDefaultBackgroundPath() throws Exception {
		return getRedisTemplate().opsForList().range(CacheKeies.USER_DEFAULT_BACKGROUNG, 0, -1);
	}

}
