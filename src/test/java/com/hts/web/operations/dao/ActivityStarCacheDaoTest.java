package com.hts.web.operations.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.OpActivityStar;

public class ActivityStarCacheDaoTest extends BaseTest {

	private Logger log = Logger.getLogger(ActivityStarCacheDaoTest.class);
	
	@Autowired
	private ActivityStarCacheDao dao;
	
	@Test
	public void queryStarTest() throws Exception {
		List<OpActivityStar> list = dao.queryStar(93);
		log.debug(list.size());
	}
}
