package com.hts.web.userinfo.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.base.constant.Tag;

public class UserActivityServiceTest extends BaseTest {

	@Autowired
	private UserActivityService service;
	
	@Test
	public void addActivityScoreTest()throws Exception{
		service.addActivityScore(Tag.ACT_TYPE_LIKE, 1111);
	}
	
}
