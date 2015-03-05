package com.hts.web.operations.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.util.Log;
import com.hts.web.userinfo.dao.UserConcernTypeDao;

public class UserConcernTypeDaoTest extends BaseTest {

	@Autowired
	private UserConcernTypeDao dao;
	
	@Test
	public void testQueryConcernType() {
		dao.queryConcernType(527, new RowCallback<Integer>() {

			@Override
			public void callback(Integer t) {
				Log.debug(t);
			}
		});
	}
}
