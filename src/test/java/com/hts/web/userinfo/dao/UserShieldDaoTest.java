package com.hts.web.userinfo.dao;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class UserShieldDaoTest extends BaseTest {

	private Logger logger = Logger.getLogger(UserShieldDaoTest.class);
	
	@Autowired
	private UserShieldDao dao;

	@Test
	public void testQueryShieldId() throws Exception {
		Integer id = dao.queryShieldId(1, 2);
		logger.debug("屏蔽id：" + id);
	}
}
