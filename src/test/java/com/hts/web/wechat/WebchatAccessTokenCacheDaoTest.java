package com.hts.web.wechat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.wechat.dao.WechatCacheDao;

public class WebchatAccessTokenCacheDaoTest extends BaseTest {

	@Autowired
	private WechatCacheDao dao;
	
	
	@Test
	public void getTokenTest() {
		String token = dao.getToken();
		System.out.println(token);
	}
	
	@Test
	public void getTicketTest() {
		String ticket = dao.getTicket();
		System.out.println(ticket);
	}
}
