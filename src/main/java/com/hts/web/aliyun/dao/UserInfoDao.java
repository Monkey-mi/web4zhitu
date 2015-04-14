package com.hts.web.aliyun.dao;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.hts.web.common.pojo.UserOpenSearch;

public interface UserInfoDao extends BaseSearchDao {

//	/**
//	 * 更新用户名称
//	 * 
//	 * @param userId
//	 * @param userName
//	 */
//	public String updateUserName(Integer userId, String userName)
//			throws JSONException, ClientProtocolException, IOException;

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void updateUser(UserOpenSearch user) throws JSONException,
			ClientProtocolException, IOException;
	
	
	public void updateUserName(Integer id, String userName)
			throws JSONException, ClientProtocolException, IOException;
	
	public void updateSignature(Integer id, String signature)
			throws JSONException, ClientProtocolException, IOException;

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void addUser(UserOpenSearch user) throws JSONException,
			ClientProtocolException, IOException;
}
