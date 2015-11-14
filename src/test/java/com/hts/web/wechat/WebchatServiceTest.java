package com.hts.web.wechat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.wechat.service.WechatService;

import net.sf.json.JSONObject;

public class WebchatServiceTest extends BaseTest {

	@Autowired
	private WechatService service;
	
	@Test
	public void getSignTest() {
		JSONObject sign = service.getSignature("http://www.imzhitu.com");
		System.out.println(sign.toString());
	}
}
