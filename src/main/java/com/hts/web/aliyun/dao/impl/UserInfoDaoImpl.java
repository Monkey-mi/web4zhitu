package com.hts.web.aliyun.dao.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.UserInfoDao;
import com.hts.web.base.database.OpenSearch;
import com.hts.web.common.pojo.UserOpenSearch;
import com.hts.web.common.util.StringUtil;
import com.opensearch.javasdk.CloudsearchDoc;

@Repository("HTSOSUserInfoDao")
public class UserInfoDaoImpl extends BaseSearchDaoImpl implements UserInfoDao {

	private static String indexName = OpenSearch.USER_INFO;

	@Override
	public void updateUser(UserOpenSearch user) throws JSONException,
			ClientProtocolException, IOException {
		CloudsearchDoc doc = new CloudsearchDoc(indexName, getSearchClient());
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", user.getId());
		fields.put("user_name", user.getUserName());
		fields.put("user_name_pinyin", StringUtil.getPinYin(user.getUserName()));
		fields.put("user_avatar", user.getUserAvatar());
		fields.put("signature", user.getSignature());
		fields.put("platform_sign", user.getPlatformSign());
		fields.put("star", user.getStar());
		doc.update(fields);
		String res = doc.push(indexName);
	}

	@Override
	public void addUser(UserOpenSearch user) throws JSONException,
			ClientProtocolException, IOException {
		CloudsearchDoc doc = new CloudsearchDoc(indexName, getSearchClient());
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", user.getId());
		fields.put("user_name", user.getUserName());
		fields.put("user_name_pinyin", StringUtil.getPinYin(user.getUserName()));
		fields.put("user_avatar", user.getUserAvatar());
		fields.put("signature", user.getSignature());
		fields.put("platform_sign", user.getPlatformSign());
		fields.put("star", user.getStar());
		doc.add(fields);
		String res = doc.push(indexName);
	}

	@Override
	public void updateSignature(Integer id, String signature)
			throws JSONException, ClientProtocolException, IOException {
		CloudsearchDoc doc = new CloudsearchDoc(indexName, getSearchClient());
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", id);
		fields.put("signature", signature);
		doc.update(fields);
		String res = doc.push(indexName);
	}
	
	@Override
	public void updateUserName(Integer userId, String userName)
			throws JSONException, ClientProtocolException, IOException {
		CloudsearchDoc doc = new CloudsearchDoc(indexName, getSearchClient());
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", userId);
		fields.put("user_name", userName);
		fields.put("user_name_pinyin", StringUtil.getPinYin(userName));
		doc.update(fields);
		String res = doc.push(indexName);
	}

}
