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
//			aps.put("alert", "织图新版火热上线！据说颜值高的人已经在玩了(⊙_⊙)！进入频道有惊喜，首创【弹幕】功能，边看图边勾搭，简直噜？！更能发布【文字】，超夯的图片心情，随时感慨随时发，赶紧接招吧！ ");
//			apnJSON.put("aps", aps);
//			apnJSON.put("a", Tag.PUSH_ACTION_SYS);
////			client.publishToCommonTopic("t6", "织图新版火热上线！据说颜值高的人已经在玩了(⊙_⊙)！进入频道有惊喜，首创【弹幕】功能，边看图边勾搭，简直噜？！更能发布【文字】，超夯的图片心情，随时感慨随时发，赶紧接招吧！ ", apnJSON);
//			client.publishToAlias("299398", "织图新版火热上线！据说颜值高的人已经在玩了(⊙_⊙)！进入频道有惊喜，首创【弹幕】功能，边看图边勾搭，简直噜？！更能发布【文字】，超夯的图片心情，随时感慨随时发，赶紧接招吧！ ", apnJSON);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (YunbaException e) {
//			e.printStackTrace();
//		}
	}
}
