package com.hts.web.addr.dao.redis;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.AddrCity;

/**
 * 城市缓存数据访问接口
 * 
 * @author lynch 2015-12-03
 *
 */
public interface CityCacheDao extends BaseCacheDao {

	/**
	 * 根据名字查询城市信息
	 * 
	 * @param name
	 * @return
	 * @author lynch 2015-12-03
	 */
	public AddrCity queryCityByName(String name);
}
