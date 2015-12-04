package com.hts.web.addr.dao.mongo;

import com.hts.web.common.dao.BaseMongoDao;
import com.hts.web.common.pojo.AddrCity;

/**
 * 城市MongoDB数据访问接口
 * 
 * @author lynch 2015-12-03
 *
 */
public interface CityMongoDao extends BaseMongoDao {

	/**
	 * 查询最近的城市信息
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author lynch 2015-12-03
	 */
	public AddrCity queryNearCity(double longitude, double latitude);
	
}
