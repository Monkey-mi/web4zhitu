package com.hts.web.operations.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.util.Log;

public class ActivityWinnerDaoTest extends BaseTest {

	@Autowired
	private ActivityWinnerDao dao;
	
	@Test
	public void testQueryMaxWinnerId() throws Exception{
		Integer id = dao.queryMaxWinnerId(100999);
		Log.debug(id);
	}
}
