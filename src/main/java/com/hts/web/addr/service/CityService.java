package com.hts.web.addr.service;

/**
 * 城市信息业务逻辑访问接口
 * 
 * @author lynch 2015-12-02
 *
 */
public interface CityService {

	/**
	 * 根据城市名获取经纬度
	 * 
	 * @param city
	 * @return longitude:0, latitude:1
	 * @author lynch 2015-12-02
	 */
	public double[] getLocByCityName(String city);
}
