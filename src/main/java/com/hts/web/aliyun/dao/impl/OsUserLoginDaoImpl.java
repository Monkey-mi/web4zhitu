package com.hts.web.aliyun.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.OsUserLoginDao;
import com.hts.web.base.database.OpenSearch;

@Repository("HTSOsUserLoginDao")
public class OsUserLoginDaoImpl extends BaseOsDaoImpl implements OsUserLoginDao {

	@Value("${aliyun.search.userLogin}")
	private String table;

	@Override
	public void pushUpdate(String json) throws Exception {
		pushUpdate(json, OpenSearch.USER_INFO, table);
	}
}
