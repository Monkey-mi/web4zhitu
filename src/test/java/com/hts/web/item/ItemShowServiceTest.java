package com.hts.web.item;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.trade.item.service.ItemShowService;

public class ItemShowServiceTest extends BaseTest {
	
	@Autowired
	private ItemShowService itemShowService;
	
	private HashMap<String, Object> jsonMap = new HashMap<String, Object>();
	
	@Test
	public void queryItemShowInfoTest() throws Exception{
		itemShowService.queryItemShowInfo(13,jsonMap);
	}
}
