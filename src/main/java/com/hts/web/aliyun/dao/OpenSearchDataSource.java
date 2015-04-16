package com.hts.web.aliyun.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.opensearch.javasdk.CloudsearchClient;
import com.opensearch.javasdk.object.KeyTypeEnum;

@Repository("HTSOpenSearchDataSource")
public class OpenSearchDataSource {

	
	@Value("${aliyun.accessKeyId}")
	private String aliyunAccessKeyId;
	
	@Value("${aliyun.accessKeySecret}")
	private String aliyunAccessKeySecret;
	
	@Value("${aliyun.search.host}")
	private String host;
	
	@Value("${aliyun.search.maxConnections}")
	private Integer maxConnections;
	
	@Value("${aliyun.search.timeout}")
	private Integer timeout;
	
	private CloudsearchClient searchClient;
	
	public CloudsearchClient getSearchClient() {
		if(searchClient == null) {
			Map<String,Object> opts = new HashMap<String,Object>();
			opts.put("host",host);
			searchClient = new CloudsearchClient(aliyunAccessKeyId,
					aliyunAccessKeySecret,opts,KeyTypeEnum.ALIYUN);
			searchClient.setMaxConnections(maxConnections);
		}
		return searchClient;
	}

	
}
