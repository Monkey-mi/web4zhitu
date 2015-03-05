package com.hts.web.operations.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.util.Log;

public class UserZombieDaoTest extends BaseTest {

	@Autowired
	private UserZombieDao dao;
	
	@Test
	public void testQueryRandomZombieId() {
		List<Integer> dtoList = dao.queryRandomZombieId(10);
		JSONArray jsArray = JSONArray.fromObject(dtoList);
		Log.debug(jsArray);
	}
	
	@Test
	public void testQueryIdByUID() {
		Integer id = dao.queryIdByUID(-1);
		Log.debug(id);
	}
}
