package com.hts.web.ztworld.dao.mongo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.operations.dao.mongo.NearWorldMongoDao;

public class WorldMongoDaoTest extends BaseTest {

	@Autowired
	private NearWorldMongoDao dao;
	
	@Test
	public void deleteWorldTest() {
		dao.deleteWorld(19837);
	}
}
