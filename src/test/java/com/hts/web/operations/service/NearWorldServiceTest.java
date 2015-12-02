package com.hts.web.operations.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.common.pojo.HTWorldInteractDto;

/**
 * 附近织图业务接口单元测试
 * 
 * @author lynch 2015-12-02
 *
 */
public class NearWorldServiceTest extends BaseTest {

	@Autowired
	private NearWorldService service;
	
	private static Logger logger = Logger.getLogger(NearWorldServiceTest.class);
	
	@Test
	public void queryNearWorldTest() throws Exception {
		List<HTWorldInteractDto> list = service.queryNearWorld(113.567841, 22.167654, 1, 10);
	}
}
