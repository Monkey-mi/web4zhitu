package com.hts.web.operations.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.OpWorldType;
import com.hts.web.common.util.Log;

public class OpWorldTypeDaoTest extends BaseTest {

	@Autowired
	private OpWorldTypeCacheDao dao;
	
	@Test
	public void testQueryCacheLabel() {
		List<OpWorldType> list = dao.queryCacheLabel(10);
		JSONArray jsArray = JSONArray.fromObject(list);
		Log.debug(jsArray);
	}
}
