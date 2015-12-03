package com.hts.web.addr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.addr.dao.mongo.CityMongoDao;
import com.hts.web.addr.dao.redis.CityCacheDao;
import com.hts.web.addr.service.CityService;
import com.hts.web.common.pojo.AddrCity;

@Service("HTSAddrCityService")
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityCacheDao cityCacheDao;

	@Autowired
	private CityMongoDao cityMongoDao;

	@Override
	public AddrCity getCityByName(String name) {
		return cityCacheDao.queryCityByName(name);
	}
	
	public AddrCity getNearCityByLoc(double longitude, double latitude) {
		return cityMongoDao.queryNearCity(longitude, latitude);
	}
	
}
