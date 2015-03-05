package com.hts.web.security.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import com.hts.web.base.BaseTest;

/**
 * <p>
 * 用户持久化登录数据访问接口单元测试
 * </p>
 * 
 * 创建时间：2013-8-24
 * @author ztj
 *
 */
public class UserLoginPersistentDaoTest extends BaseTest {
	
	@Autowired
	private UserLoginPersistentDao dao;
	
	@Test
	public void testCreateNewToken() {
		dao.createNewToken(new PersistentRememberMeToken("109", "1111", "111", new Date()));
	}
	
	@Test
	public void testGetTokenForSeries() {
		dao.getTokenForSeries("111");
	}
	
	@Test
	public void testRemoveUserTokens() {
		dao.removeUserTokens("109");
	}
	
	@Test
	public void testUpdateUserToken() {
		dao.updateToken("tONVWG+UuLSDXz59Q8CCag==", "LRKg/AwmU3gUcogHe/QAnw==", new Date());
	}

}
