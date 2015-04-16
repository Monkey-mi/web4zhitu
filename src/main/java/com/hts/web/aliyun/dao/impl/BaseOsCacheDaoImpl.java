package com.hts.web.aliyun.dao.impl;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.hts.web.common.dao.impl.BaseCacheDaoImpl;

public class BaseOsCacheDaoImpl extends BaseCacheDaoImpl<String> {

	@Autowired
	private RedisTemplate<String, String> redisKeyTemplate;
	
	public RedisTemplate<String, String> getRedisKeyTemplate() {
		return redisKeyTemplate;
	}
	
	/**
	 * 缓存操作
	 * 
	 * @param cmd
	 * @param fields
	 * @param key
	 */
	public void saveOpt(String cmd, Map<String, Object> fields, String key) {
		JSONObject jsobj = new JSONObject();
		jsobj.put("fields", fields);
		jsobj.put("cmd", cmd);
		jsobj.put("timestamp", new Date().getTime());
		BoundListOperations<String, String> op = 
				getRedisKeyTemplate().boundListOps(key);
		op.rightPush(jsobj.toString());
	}
}
