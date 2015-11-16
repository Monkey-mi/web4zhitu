package com.hts.web.operations.service;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class ActivityServiceTest extends BaseTest {

	private Logger logger = Logger.getLogger(ActivityServiceTest.class);
	
	@Autowired
	private ActivityService service;
	
	
	@Test
	public void testBuildSuperbChannelWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.getactivityPageInfoByAid(10635,  jsonMap);
		logger.debug("---------------------------" + jsonMap + "----------------------------"); 
	}
	
	
}
