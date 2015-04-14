package com.hts.web.userinfo.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class UserRecServiceTest extends BaseTest {

	private static Logger log = Logger.getLogger(UserRecServiceTest.class);
	
	@Autowired	
	private UserRecService service;
	
	@Test
	public void savePlatformLoginCodeTest() throws Exception {
		service.savePlatConcerns(485, "2412620371, 1231231");
	}
	
	@Test
	public void buildRecUserTest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildRecUser(1551, null, null, jsonMap);
		log.debug(jsonMap);
	}
}
