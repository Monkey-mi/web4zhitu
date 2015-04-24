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
//		JSONObject apnJSON = new JSONObject();
//		JSONObject aps = new JSONObject();
//		try {
//			aps.put("sound", "default");
//			aps.put("badge", 1);
//			aps.put("alert", "伟大的安妮加入织图啦！赶快来参观吧!" + new Date());
//			apnJSON.put("aps", aps);
//			apnJSON.put("a", "14");
//			apnJSON.put("oid", "4");
//			System.out.println(apnJSON);
//			client.publishToTopic("t2", "伟大的安妮加入织图啦！赶快来参观吧", apnJSON);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (YunbaException e) {
//			e.printStackTrace();
//		}
	}
}
