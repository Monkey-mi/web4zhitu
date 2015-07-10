package com.hts.web.userinfo.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class UserConcernServiceTest extends BaseTest {

	@Autowired
	private UserConcernService service;
	
	@Test
	public void buildConcernStatusTest()	 {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildConcernStatus(485, "12,13", jsonMap);
	}
	
}
