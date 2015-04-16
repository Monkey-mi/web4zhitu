package com.hts.web.aliyun.dao.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.aliyun.dao.BaseOsDao;
import com.hts.web.aliyun.dao.OpenSearchDataSource;
import com.opensearch.javasdk.CloudsearchClient;
import com.opensearch.javasdk.CloudsearchDoc;

/**
 * opensearch数据访问接口基础类
 * 
 * 创建时间: 2015-04-15
 * @author lynch
 *
 */
public class BaseOsDaoImpl implements BaseOsDao {
	
	@Autowired
	private OpenSearchDataSource dataSource;
	
	public CloudsearchClient getSearchClient() {
		return dataSource.getSearchClient();
	}
	
	/**
	 * 提交更新数据
	 * 
	 * @param json
	 * @param indexName
	 * @param tableName
	 * @throws Exception
	 */
	protected void pushUpdate(String json, String indexName, String tableName) throws Exception {
		CloudsearchDoc doc = new CloudsearchDoc(indexName, getSearchClient());
		String res = doc.push(json, tableName);
		if(res != null) {
			JSONObject js = JSONObject.fromObject(res);
			if(!js.get("status").toString().toLowerCase().equals("ok")) {
				throw new Exception(res);
			}
		}
	}

}
