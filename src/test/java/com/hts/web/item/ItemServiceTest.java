package com.hts.web.item;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.BaseTest;
import com.hts.web.trade.item.service.ItemService;

/**
 * <p>
 * 商品模块业务逻辑访问接口
 * </p>
 * 
 * @author lynch 2015-12-13
 *
 */
public class ItemServiceTest extends BaseTest {
	
	@Autowired
	private ItemService service;

	@Test
	public void queryItemInfoTest() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		service.queryItemInfo(446, -1, jsonMap);
		logObj(jsonMap);
	}
	
}
