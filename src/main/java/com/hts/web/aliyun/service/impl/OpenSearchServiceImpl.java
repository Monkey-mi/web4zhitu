package com.hts.web.aliyun.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.hts.web.aliyun.service.OpenSearchService;
import com.opensearch.javasdk.*;
import com.opensearch.javasdk.object.KeyTypeEnum;

public class OpenSearchServiceImpl implements OpenSearchService{
	@Value("${aliyun.accessKeyId}")
	private String aliyunAccessKeyId;
	
	@Value("${aliyun.accessKeySecret}")
	private String aliyunAccessKeySecret;
	
	@Value("${aliyun.appName}")
	private String appName;
	
	@Override
	public String searchAnswer(String question) throws Exception {
		Map<String,Object>opts = new HashMap<String,Object>();
		opts.put("host","http://opensearch.aliyuncs.com");
		CloudsearchClient client = new CloudsearchClient(aliyunAccessKeyId,aliyunAccessKeySecret,opts,KeyTypeEnum.ALIYUN);
		CloudsearchSearch search = new CloudsearchSearch(client);
		
		//添加制定的搜索应用
		search.addIndex(appName);
		
		//指定搜索的关键词，如果没有输入索引名称，则使用default
		search.setQueryString(question);
		
		search.setQueryString("default:'"+question+"'");
		
		//或者指定某索引字段进行查找
		//索引字段名称是您在您的数据结构中的“索引到”字段
		search.setQueryString("index_name:'"+question+"'");
		
		//指定搜索返回的格式
		search.setFormat("json");
		
		//设定过滤条件，字段必须设定为可过滤
		search.addFilter("price>10");
		
		//设定排序方式，字段必须设定为可过滤
		search.addSort("price", "+");
		
		//解析结果
		System.out.println(search.search());
		return null;
	}

	public String getAliyunAccessKeyId() {
		return aliyunAccessKeyId;
	}

	public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
		this.aliyunAccessKeyId = aliyunAccessKeyId;
	}

	public String getAliyunAccessKeySecret() {
		return aliyunAccessKeySecret;
	}

	public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
		this.aliyunAccessKeySecret = aliyunAccessKeySecret;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
