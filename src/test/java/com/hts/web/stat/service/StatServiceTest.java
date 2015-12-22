package com.hts.web.stat.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

/**
 * 统计业务逻辑访问接口
 * 
 * @author lynch 2015-12-19
 *
 */
public class StatServiceTest extends BaseTest {

	@Autowired
	private StatService service;
	
	@Test
	public void incPVTest() throws Exception {
		service.incPV(1);
	}

	
}
