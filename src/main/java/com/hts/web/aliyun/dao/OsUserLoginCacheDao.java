package com.hts.web.aliyun.dao;

import com.hts.web.common.dao.BaseCacheDao;

public interface OsUserLoginCacheDao extends BaseCacheDao {

	public void saveLogin(Integer id, Integer lastLogin);
	
	public void updateLogin(Integer id, Integer lastLogin);
	
	public void popOpts(int limit);
}
