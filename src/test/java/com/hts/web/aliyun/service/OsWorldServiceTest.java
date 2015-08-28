package com.hts.web.aliyun.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class OsWorldServiceTest extends BaseTest {

	@Autowired
	private OsWorldService service;
	
	@Test
	public void saveWorldTest() throws Exception {
		service.saveWorld(-100, 0.1d, 0.2d, "木叶村", "火影", "忍者");
	}
	
	@Test
	public void commitOptsTest() throws Exception {
		service.commitOpts(10);
	}
	
}
