package com.hts.web.operations.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.util.Log;

public class ChannelWorldDaoTest extends BaseTest {

	@Autowired
	private ChannelWorldDao dao;

	@Test
	public void queryChildCountTest() throws Exception {
		int a = dao.queryChildCount(-1999);
		Log.debug(a);
	}
}
