package com.hts.web.ztworld.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.HTWorldLabelWorldAuthor;
import com.hts.web.common.util.Log;

public class HTWorldLabelWorldDaoTest extends BaseTest {

	@Autowired
	private HTWorldLabelWorldDao dao;
	
	@Test
	public void testQueryLabelWorldV2() {
		dao.queryLabelWorldV2(527, 11, new RowSelection(1, 10));
		dao.queryLabelWorldV2(10000, 527, 11, new RowSelection(1, 10));
	}
	
	@Test
	public void testQueryLabelWorldAuthor() {
		dao.queryLabelWorldAuthor(1000, 11, 527, new RowSelection(1, 10));
		dao.queryLabelWorldAuthor(11, 527, new RowSelection(1, 10));
	}
	
	@Test
	public void testQueryParticByLimit() {
		dao.queryParticipatorByLimit(527, new Integer[]{11}, 6, new RowCallback<HTWorldLabelWorldAuthor>() {
			
			@Override
			public void callback(HTWorldLabelWorldAuthor t) {
				Log.debug(t.getId() + " : " + t.getUserId());
			}
		});
	}
	
}
