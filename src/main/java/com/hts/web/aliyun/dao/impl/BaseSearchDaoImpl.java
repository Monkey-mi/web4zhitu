package com.hts.web.aliyun.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.aliyun.dao.BaseSearchDao;
import com.hts.web.aliyun.dao.OpenSearchDataSource;
import com.opensearch.javasdk.CloudsearchClient;

public class BaseSearchDaoImpl implements BaseSearchDao {
	
	@Autowired
	private OpenSearchDataSource dataSource;
	
	public CloudsearchClient getSearchClient() {
		return dataSource.getSearchClient();
	}

}
