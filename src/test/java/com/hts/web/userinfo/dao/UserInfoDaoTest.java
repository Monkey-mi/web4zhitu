package com.hts.web.userinfo.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.UserInfo;

/**
 * <p>
 * 用户信息数据访问接口单元测试
 * </p>
 * 
 * 创建时间：2013-8-4
 * @author ztj
 *
 */
public class UserInfoDaoTest extends BaseTest {

	@Autowired
	private UserInfoDao dao;
	
	@Test
	public void testQueryUserInfoById() {
		UserInfo userInfo = dao.queryUserInfoById(116);
		Assert.assertNotNull(userInfo);
	}
	
	@Test
	public void testQueryUserInfoByLoginCode() {
		UserInfo userInfo = dao.queryUserInfoByLoginCode("2279052031", 2);
		Assert.assertNotNull(userInfo);
	}
}
