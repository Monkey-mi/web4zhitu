package com.hts.web.stat.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * pv统计数据访问接口
 * 
 * @author lynch 2015-12-19
 *
 */
public interface StatPVCacheDao extends BaseCacheDao {

	/**
	 * 增加PV
	 * 
	 * @param key
	 * @author lynch 2015-12-18
	 */
	public void inc(String key);
	
}
