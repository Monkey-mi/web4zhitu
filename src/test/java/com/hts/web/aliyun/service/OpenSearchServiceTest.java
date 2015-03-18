package com.hts.web.aliyun.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class OpenSearchServiceTest extends BaseTest{
	@Autowired
	private OpenSearchService service;
	
	@Test
	public void searchAnswerTest()throws Exception{
		String str = service.searchAnswer("怎么");
		System.out.println(str);
	}
}
