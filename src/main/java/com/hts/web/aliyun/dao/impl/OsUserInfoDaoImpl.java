package com.hts.web.aliyun.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.OsUserInfoDao;
import com.hts.web.base.database.OpenSearch;

@Repository("HTSOsUserInfoDao")
public class OsUserInfoDaoImpl extends BaseOsDaoImpl implements OsUserInfoDao {

	@Value("${aliyun.search.userInfo}")
	private String table;

	@Override
	public void pushUpdate(String json) throws Exception {
		pushUpdate(json, OpenSearch.USER_INFO, table);
	}

}
