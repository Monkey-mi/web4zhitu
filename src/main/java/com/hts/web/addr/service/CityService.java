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
	 * 根据名字获取城市信息
	 * 
	 * @param name
	 * @return longitude:0, latitude:1
	 * @author lynch 2015-12-02
	 */
	public AddrCity getLocByCityName(String name);
}
