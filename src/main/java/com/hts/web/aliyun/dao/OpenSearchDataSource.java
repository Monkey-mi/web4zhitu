package com.hts.web.aliyun.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.hts.web.common.util.Log;
import com.opensearch.javasdk.CloudsearchClient;
import com.opensearch.javasdk.object.KeyTypeEnum;

@Repository("HTSOpenSearchDataSource")
public class OpenSearchDataSource {

	
	@Value("${aliyun.accessKeyId}")
	private String aliyunAccessKeyId;
	
	@Value("${aliyun.accessKeySecret}")
	private String aliyunAccessKeySecret;
	
	@Value("${aliyun.searchHost}")
	private String host = "http://opensearch-cn-hangzhou.aliyuncs.com";
	
	private CloudsearchClient searchClient;
	
	public CloudsearchClient getSearchClient() {
		Log.debug("阿里云------->" + aliyunAccessKeyId);
		Log.debug("阿里云 HOST------->" + host);
		if(searchClient == null) {
			Map<String,Object> opts = new HashMap<String,Object>();
			opts.put("host",host);
			searchClient = new CloudsearchClient(aliyunAccessKeyId,
					aliyunAccessKeySecret,opts,KeyTypeEnum.ALIYUN);
		}
		return searchClient;
	}

	public void setSearchClient(CloudsearchClient searchClient) {
		this.searchClient = searchClient;
	}
	
	
}
