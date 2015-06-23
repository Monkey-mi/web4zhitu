package com.hts.web.aliyun.dao.impl;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.aliyun.dao.BaseOsDao;
import com.hts.web.aliyun.dao.OpenSearchDataSource;
import com.hts.web.common.util.Log;
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
	 * 获取添加操作命令字符串
	 * 
	 * @param fields
	 * @return
	 */
	protected JSONObject getAddOpt(Map<String, Object> fields) {
		return getOpt("add", fields);
	}
	
	/**
	 * 获取更新操作命令字符串
	 * 
	 * @param fields
	 * @return
	 */
	protected JSONObject getUpdateOpt(Map<String, Object> fields) {
		return getOpt("update", fields);
	}
	
	/**
	 * 获取删除操作命令字符串
	 * @param fields
	 * @return
	 */
	protected JSONObject getDeleteOpt(Map<String, Object> fields) {
		return getOpt("delete", fields);
	}
	
	/**
	 * 获取操作字符串
	 * 
	 * @param cmd
	 * @param fields
	 * @return
	 */
	private JSONObject getOpt(String cmd, Map<String, Object> fields) {
		JSONObject jsobj = new JSONObject();
		jsobj.put("fields", fields);
		jsobj.put("cmd", cmd);
		jsobj.put("timestamp", new Date().getTime());
		return jsobj;
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
