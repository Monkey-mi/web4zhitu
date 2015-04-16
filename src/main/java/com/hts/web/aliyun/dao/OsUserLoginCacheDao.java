package com.hts.web.aliyun.dao;

import com.hts.web.common.dao.BaseCacheDao;

public interface OsUserLoginCacheDao extends BaseCacheDao {

	public void saveLogin(Integer id, Long lastLogin);
	
	public void updateLogin(Integer id, Long lastLogin);
	
	public void popOpts(int limit);
}
