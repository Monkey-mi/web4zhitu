package com.hts.web.addr.service.impl;

import org.springframework.stereotype.Service;

import com.hts.web.addr.service.CityService;

@Service("HTSAddrCityService")
public class CityServiceImpl implements CityService {

	@Override
	public double[] getLocByCityName(String city) {
		return new double[]{113.567841, 22.167654};
	}

}
