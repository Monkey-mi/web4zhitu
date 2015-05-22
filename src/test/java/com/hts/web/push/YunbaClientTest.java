package com.hts.web.push;


import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.Tag;
import com.hts.web.push.yunba.YunbaClient;
import com.hts.web.push.yunba.YunbaException;

public class YunbaClientTest extends BaseTest {
	
	private static Logger log = Logger.getLogger(YunbaClientTest.class);

	@Autowired
	private YunbaClient client;

	@Test
	public void testPublishToAlias() throws YunbaException, JSONException, InterruptedException {
//		JSONObject apnJSON = new JSONObject();
//		JSONObject aps = new JSONObject();
//		try {
//			aps.put("sound", "default");
//			aps.put("badge", 1);
//			aps.put("alert", "谈了那么久的恋爱，还是走到了尽头，你将永远是我的备胎——至我终将逝去的春天，立夏你好。");
//			apnJSON.put("aps", aps);
//			apnJSON.put("a", Tag.PUSH_ACTION_SYS);
//			client.publishToCommonTopic("t9", "谈了那么久的恋爱，还是走到了尽头，你将永远是我的备胎——至我终将逝去的春天，立夏你好。", apnJSON);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (YunbaException e) {
//			e.printStackTrace();
//		}
	}
}
