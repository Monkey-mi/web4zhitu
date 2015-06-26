package com.hts.web.operations.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class ChannelPVCacheDaoTest extends BaseTest {

	@Autowired
	private ChannelPVCacheDao dao;
	
	@Test
	public void testIncr() throws Exception {
		dao.inrc(11);
	}
}
