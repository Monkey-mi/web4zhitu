package com.hts.web.push;


import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

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
//			aps.put("alert", "消息推送修复,不影响用户");
//			apnJSON.put("aps", aps);
//			apnJSON.put("a", Tag.PUSH_ACTION_SYS);
//			List<Integer> uids = new ArrayList<Integer>();
//			uids.add(4);
////			uids.add(2035);
//			JSONArray toAliasBatch = JSONArray.fromObject(uids);
//			client.publishToAliasBatch(toAliasBatch, 
//					"端午小长假咻~的一声就过了，像【夏の风】一样【Fly away】，连伤心都来不及╮(╯▽╰)╭。赶紧让【喵星人】贴纸来治愈你，我戳→ → → ！！！", apnJSON);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (YunbaException e) {
//			e.printStackTrace();
//		}
	}
}
