package com.hts.web.userinfo.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class UserUnreadDaoTest extends BaseTest {

	@Autowired
	private MsgUnreadDao dao;
	
	@Test
	public void test() {
		dao.saveUnRead(1595);
	}
}
