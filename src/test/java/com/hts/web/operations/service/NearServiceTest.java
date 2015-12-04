package com.hts.web.operations.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.HTWorldInteractDto;

/**
 * 附近织图业务接口单元测试
 * 
 * @author lynch 2015-12-02
 *
 */
public class NearServiceTest extends BaseTest {

	@Autowired
	private NearService service;
	
	private static Logger logger = Logger.getLogger(NearServiceTest.class);
	
	@Test
	public void queryNearWorldTest() throws Exception {
		logNumberList(logger, new NumberListAdapter() {
			
			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildNearWorld("深圳", 113.937538, 22.539017, 0, 10, jsonMap, 2, 0);
			}
		});
		
	}
	
	@Test
	public void queryNearLabel()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.buildNearLabel("湛江", null, null, 1, 10, jsonMap);
		logger.info(jsonMap);
	}
	
	@Test
	public void queryNearBanner()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.buildNearBanner("湛江", null, null, 1, 10, jsonMap);
		logger.info(jsonMap);
	}
	
	@Test
	public void queryRecommendCityTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.buildRecommendCity(jsonMap);
	}
}
