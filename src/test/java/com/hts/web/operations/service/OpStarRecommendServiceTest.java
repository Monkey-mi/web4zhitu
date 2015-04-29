package com.hts.web.operations.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.OpStarRecommendDto;

public class OpStarRecommendServiceTest extends BaseTest{
	@Autowired
	private OpStarRecommendService service;
	
	private Logger log = Logger.getLogger(OpStarRecommendServiceTest.class);
	
	@Test
	public void queryStarRecommendFromCacheTest()throws Exception{
		List<OpStarRecommendDto> list = service.queryStarRecommend();
		log.info("list ==========>>>>>"+list.toString());
	}
	
	@Test
	public void queryStarRecommendTest()throws Exception{
		Map<String,Object>jsonMap = new HashMap<String,Object>();
		service.queryStarRecommend(0,1, jsonMap);
		log.info("jsonMap is ===============>>>>"+jsonMap);
	}
	
	@Test
	public void updateStarRecommendCacheTest()throws Exception{
		service.updateStarRecommendCache();
	}

}
