package com.hts.web.weibo.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class SinaWeiboServiceTest extends BaseTest{
	
	@Autowired
	public SinaWeiboService service;
	
	@Test
	public void getFollowByIdTest()throws Exception{
		String[] ids = service.getFollowerIdsById("2089415235", "2.00j2y5RCFZjt7D0939d0b686C537lD", 500);
		System.out.println(ids.length);
	}

}
