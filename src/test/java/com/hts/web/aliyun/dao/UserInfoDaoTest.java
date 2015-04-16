package com.hts.web.aliyun.dao;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class UserInfoDaoTest extends BaseTest {

	@Autowired
	private OsUserInfoDao dao;
	
	@Test
	public void updateUserNameTest() throws ClientProtocolException, JSONException, IOException {
//		dao.updateUserName(25, "琳琳我嗷嗷嗷");
	}
}
