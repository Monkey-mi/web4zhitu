package com.hts.web.push;

import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.push.yunba.YunbaClient;
import com.hts.web.push.yunba.YunbaException;

public class YunbaClientTest extends BaseTest {
	
	private static Logger log = Logger.getLogger(YunbaClientTest.class);

	@Autowired
	private YunbaClient client;

	@Test
	public void testPublishToAlias() throws YunbaException, JSONException, InterruptedException {
//		for(int i = 0; i < 100; i++) {
//			final int k = i % 2;
//			log.debug("current push : " + k);
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					
//				}
//				
//			}).start();
//		}
//		Thread.sleep(20000);
		JSONObject apnJSON = new JSONObject();
		JSONObject aps = new JSONObject();
		try {
			aps.put("sound", "default");
			aps.put("badge", 1);
			aps.put("alert", "apns alert title," + new Date());
			apnJSON.put("aps", aps);
			client.publishToAlias("1", "publish_alias from resful api:" + 1, apnJSON);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (YunbaException e) {
			e.printStackTrace();
		}
	}
}
