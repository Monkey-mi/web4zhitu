package com.hts.web.addr.service;

import com.hts.web.common.pojo.AddrCity;

/**
 * 城市信息业务逻辑访问接口
 * 
 * @author lynch 2015-12-02
 *
 */
public interface CityService {

	/**
	 * 根据名字获取最近的城市信息
	 * 
	 * @param name
	 * @return 城市信息
	 * @author lynch 2015-12-02
	 */
	public AddrCity getCityByName(String name);
	
	/**
	 * 根据经纬度查询最近的城市信息
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public AddrCity getNearCityByLoc(double longitude, double latitude);
	
}
