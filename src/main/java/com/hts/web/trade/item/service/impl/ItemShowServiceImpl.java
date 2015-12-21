package com.hts.web.trade.item.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.ItemShow;
import com.hts.web.trade.item.dao.ItemShowDao;
import com.hts.web.trade.item.service.ItemShowService;

@Service
public class ItemShowServiceImpl implements ItemShowService {

	@Autowired
	private ItemShowDao itemShowDao; 
	
	@Override
	public void queryItemShowInfo(Integer itemSetId, Map<String, Object> jsonMap) throws Exception {
		List<ItemShow> list = itemShowDao.queryItemShow(itemSetId);
		
		jsonMap.put(OptResult.ROWS, list);
	}

}
