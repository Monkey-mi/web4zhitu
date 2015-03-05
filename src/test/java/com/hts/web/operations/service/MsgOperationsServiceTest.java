package com.hts.web.operations.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.Tag;

public class MsgOperationsServiceTest extends BaseTest {

	@Autowired
	private MsgOperationsService service;
	
	@Test
	public void testBuildNotice() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildNotice(1595, Tag.ANDROID, jsonMap);
		logObj(jsonMap);
	}
}
