package com.hts.web.operations.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.util.Log;

public class ActivityCacheDaoTest extends BaseTest {

	@Autowired
	private ActivityCacheDao dao;
	
	@Test
	public void testQueryMaxActivity() throws Exception {
		OpActivity act = dao.queryMaxActivity();
		if(act != null) {
			Log.debug(act.getId());
		}
	}
	
}
