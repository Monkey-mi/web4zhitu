package com.hts.web.ztworld.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.util.Log;

public class HTWorldLabelCacheDaoTest extends BaseTest {

	@Autowired
	private HTWorldLabelCacheDao dao;
	
	@Test
	public void testQueyLabel() {
		List<HTWorldLabel> list = dao.queryHotLabel(new RowSelection(1, 10));
		JSONArray jsArray = JSONArray.fromObject(list);
		Log.debug(jsArray);
	}
	
}
