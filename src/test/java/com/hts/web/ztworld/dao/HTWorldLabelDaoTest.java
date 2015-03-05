package com.hts.web.ztworld.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.util.Log;

public class HTWorldLabelDaoTest extends BaseTest {
	
	@Autowired
	private HTWorldLabelDao dao;
	
	@Test
	public void queryLabelByNameTest() throws Exception {
		HTWorldLabel label = dao.queryLabelByName("自拍情结");
		Log.debug(label);
	}

}
