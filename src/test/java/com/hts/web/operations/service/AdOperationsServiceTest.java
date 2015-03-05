package com.hts.web.operations.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class AdOperationsServiceTest extends BaseTest {

	@Autowired
	private AdOperationsService service;
	
	@Test
	public void testBuildAppLink() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildAppLink(0, 10, 1, 10, jsonMap);
		logObj(jsonMap);
	}
}
