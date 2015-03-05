package com.hts.web.userinfo.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.security.service.UserLoginPersistentService;

public class UserLoginPersistentServiceTest extends BaseTest {

	private Logger log = Logger.getLogger(UserLoginPersistentServiceTest.class);
	
	@Autowired
	private UserLoginPersistentService service;
	
	@Test
	public void testGenerateSeriesData() {
		for(int i = 0; i < 100; i++) {
			String series = service.generateSeriesData();
			String token = service.generateTokenData();
			log.debug(series.length() + " : " + series + " : " + token.length() + " : " + token);
		}
	}
}
