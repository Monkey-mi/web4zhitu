package com.hts.web.addr.service.impl;

import org.springframework.stereotype.Service;

import com.hts.web.addr.service.CityService;
import com.hts.web.common.pojo.AddrCity;

@Service("HTSAddrCityService")
public class CityServiceImpl implements CityService {

	@Override
	public AddrCity getCityByName(String name) {
		AddrCity city = new AddrCity();
		city.setId(1);
		city.setName("深圳");
		city.setLongitude(113.567841);
		city.setLatitude(22.167654);
		return city;
	}

}
