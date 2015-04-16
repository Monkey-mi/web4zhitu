package com.hts.web.aliyun.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class OsUserInfoServiceTest extends BaseTest {

	@Autowired
	private OsUserInfoService service;
	
	@Test
	public void updateUserWithoutNULLTest() throws Exception {
//		service.updateUserWithoutNULL(25, null, null, null, null, null, 100);
	}
	
	@Test
	public void updateLastLoginTest() throws Exception {
//		service.updateLastLogin(25, 1000);
	}
	
	@Test
	public void commitOptTest() throws Exception {
//		service.commitOpt();
	}
}
