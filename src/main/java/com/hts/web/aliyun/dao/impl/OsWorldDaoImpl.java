package com.hts.web.aliyun.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.OsWorldDao;
import com.hts.web.base.database.OpenSearch;

@Repository("HTSOsWorldDao")
public class OsWorldDaoImpl extends BaseOsDaoImpl implements OsWorldDao {

	private static String table = "main";

	@Override
	public void pushUpdate(String json) throws Exception {
		pushUpdate(json, OpenSearch.WORLD, table);
	}
}
