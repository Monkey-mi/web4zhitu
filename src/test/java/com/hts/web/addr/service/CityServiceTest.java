package com.hts.web.addr.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.AddrCity;

public class CityServiceTest extends BaseTest {
	
	private static Logger logger = Logger.getLogger(CityServiceTest.class);
	
	@Autowired
	private CityService service;

	@Test
	public void queryNearCityTest() {
		AddrCity city = service.getNearCityByLoc(113.899061, 22.555372);
		logger.debug(city.getShortName());
	}
	
	@Test
	public void queryCityByNameTest() {
		AddrCity city = service.getCityByName("柳州");
		logger.debug(city.getShortName());
	}
	
}
