package com.hts.web.ztworld.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class ZTWorldStickerTest extends BaseTest {

	private static Logger log = Logger.getLogger(ZTWorldStickerTest.class);

	@Autowired
	private ZTWorldStickerService service;

	@Test
	public void buildTopStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildTopSticker(485, jsonMap);
			}

		});
	}

	@Test
	public void buildRecommendStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildRecommendSticker(1595, jsonMap);
			}

		});
	}

	@Test
	public void buildStickerTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildSticker(485, 1, 100, 1, 10, jsonMap);
				service.buildSticker(485, 1, 0, 1, 10, jsonMap);
			}

		});
	}

	@Test
	public void buildMaxStickerIdTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildMaxStickerId(jsonMap);
			}

		});
	}

	@Test
	public void unlockTest() throws Exception {
		service.unlock(2, 22);
	}

	@Test
	public void usedTest() throws Exception {
		service.saveStickerUsed(485, 11);
	}

	@Test
	public void buildLibTest() throws Exception {
		logNumberList(log, new NumberListAdapter() {

			@Override
			public void buildNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildLib(9, 1, 10, jsonMap);
			}

		});
	}

}
