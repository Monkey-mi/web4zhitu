package com.hts.web.operations.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class ChannelAutoPassIdCacheDaoTest extends BaseTest {
	
	@Autowired
	private ChannelAutoPassIdCacheDao dao;

	@Test
	public void isAutoPassTest() throws Exception {
		Boolean flag = dao.isAutoPass(12);
	}
	

}
