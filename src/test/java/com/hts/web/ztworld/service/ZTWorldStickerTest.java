package com.hts.web.ztworld.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.util.Log;

public class ZTWorldStickerTest extends BaseTest {
	
	private static Logger log = Logger.getLogger(ZTWorldStickerTest.class);

	@Autowired
	private ZTWorldStickerService service;
	
	
	
	@Test
	public void buildTopStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildTopSticker(jsonMap);
			}
			
		});
	}
	
	@Test
	public void buildRecommendStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildRecommendSticker(1595, jsonMap);
			}
			
		});
	}
	
	@Test
	public void buildStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildSticker(485, 1, 100, 1, 10, jsonMap);
				service.buildSticker(485, 1, 0, 1, 10, jsonMap);
			}
			
		});
	}
	
	@Test
	public void buildMaxStickerIdTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap)
					throws Exception {
				service.buildMaxStickerId(jsonMap);
			}
			
		});
	}
	
	@Test
	public void unlockTest() throws Exception {
		service.unlock((int)(Math.random() * 1000000), (int)(Math.random() * 1000000));
	}
	
}
