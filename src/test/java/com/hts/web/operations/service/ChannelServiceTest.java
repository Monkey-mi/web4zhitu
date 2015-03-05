package com.hts.web.operations.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.OpChannelStar;
import com.hts.web.common.util.Log;

public class ChannelServiceTest extends BaseTest {

	private Logger logger = Logger.getLogger(ChannelServiceTest.class);
	
	@Autowired
	private ChannelService service;
	
	@Test
	public void testBuildChannel() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildChannel(jsonMap);
			}
			
		});
	}
	
	@Test
	public void testBuildChannelWorld() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildChannelWorld(1, 485, 10000, 1, 10, false, 0, 10, 4, jsonMap);
				service.buildChannelWorld(1, 527, 0, 1, 10, false, 0, 10, 4, jsonMap);
			}
			
		});
	}
	
	@Test
	public void testBuildChannelTopOne() throws Exception {
		logNumberList(logger, new NumberListAdapter(){

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildChannelTopOne(485, jsonMap);
			}
		});
	}
}
