package com.hts.web.ztworld.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class ZTWorldLabelServiceTest extends BaseTest {

	@Autowired
	private ZTWorldLabelService service;
	
	@Test
	public void testSaveLabel() throws Exception {
		service.saveLabel("呼呼哈希2");
	}
	
	@Test
	public void testBuildFuzzyLabel() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildFuzzyLabel("呼呼", 10, jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildHotLabel() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildHotLabel(jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildActivityLabel() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildActivityLabel(jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void testBuildLabelWorld() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		service.buildLabelWorld(true, "自拍情结", true, 116, 1000, 1, 10, jsonMap, false, false, 3, 12);
		service.buildLabelWorld(true, "自拍情结", true, 116, 0, 1, 10, jsonMap, false, false, 3, 12);
		logObj(jsonMap);
	}
	
	@Test
	public void buildLabel() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLabel("毕业季", jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void buildLabelWorldAuthor() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.buildLabelWorldAuthor(2, 485, 3161, 1, 2, jsonMap);
		service.buildLabelWorldAuthor(2, 485, 0, 1, 2, jsonMap);
		logObj(jsonMap);
	}
	
}
