package com.hts.web.operations.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

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
				service.buildNearWorld("深圳", 113.937538, 22.539017, 0, 10, jsonMap, 2, 0, 1723);
			}
		});
		
	}
	
	@Test
	public void queryNearLabel()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.buildNearLabel("深圳", null, null, 0, 10, jsonMap);
		logger.info(jsonMap);
	}
	
	@Test
	public void queryNearBanner()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.buildNearBanner("深圳", null, null, 1, 10, jsonMap);
//		logger.info(jsonMap);
		logObj(jsonMap);
	}
	
	@Test
	public void queryRecommendCityTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.buildRecommendCity(jsonMap);
	}
	
	@Test
	public void buildNearLabelWorldTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.buildNearLabelWorld(43, 0, 0, 0, 10, jsonMap, 485);
		logObj(jsonMap);
	}
	
	@Test
	public void updateRecommendCityCacheTest()throws Exception{
		service.updateRecommendCityCache();
	}
	
//	@Test
	public void insertNearLabelWorldUserTest()throws Exception{
		service.insertNearLabelWorldUser(1123, 485, 1);
	}
}
