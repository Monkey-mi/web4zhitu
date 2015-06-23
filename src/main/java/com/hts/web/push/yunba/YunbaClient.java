package com.hts.web.push.yunba;

import net.sf.json.JSONArray;

import org.json.JSONObject;

public interface YunbaClient {

	public void publishToAlias(String toAlias, String msg, JSONObject apnsJSON) throws YunbaException;
	
	public void publishToAlias(String toAlias, String msg) throws YunbaException;
	
	public void publishToAliasBatch(JSONArray toAliasBatch, String msg, JSONObject apnsJSON) throws YunbaException;
	
	public void publishToCommonTopic(String toTopic, String msg, JSONObject apnsJSON) throws YunbaException;
}
