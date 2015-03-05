package com.hts.web.operations.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class ActivityDaoTest extends BaseTest {

	@Autowired
	private ActivityDao dao;
	
	@Test
	public void testQueryActivityById() {
		dao.queryActivityById(2);
	}
	
	@Test
	public void testQueryAuthorLikeRank() {
		dao.queryAuthorLikeRank(11, 10);
	}
	
}
