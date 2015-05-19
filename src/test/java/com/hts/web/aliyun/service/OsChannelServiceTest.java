package com.hts.web.aliyun.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;

public class OsChannelServiceTest extends BaseTest{

	@Autowired
	private OsChannelService service;
	
	@Test
	public void updateChannelAtOnceTest() throws Exception {
		service.updateChannelAtOnce(8, null, null, 
				null, "副标题", null, null, 
				null, null, "美女,御姐", "2,3,4",
				null, null, null);
	}

}
