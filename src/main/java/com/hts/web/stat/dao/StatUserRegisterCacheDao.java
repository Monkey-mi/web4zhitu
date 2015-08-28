package com.hts.web.stat.dao;

import java.util.Date;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 用户注册统计数据访问接口 
 * 
 * @author lynch 2015-08-28
 *
 */
public interface StatUserRegisterCacheDao extends BaseCacheDao {

	/**
	 * 保存注册统计数据
	 * 
	 * @param uid
	 * @param phoneCode
	 * @param registerDate
	 */
	public void saveRegisterStat(Integer uid, Integer phoneCode, Date registerDate);
}
