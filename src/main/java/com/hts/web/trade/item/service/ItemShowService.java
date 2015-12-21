package com.hts.web.trade.item.service;

import java.util.Map;

public interface ItemShowService {
	
	/**
	 * 通过商品集合查询买家秀
	 * @param ItemSetId
	 * @param jsonMap
	 * @throws Exception 
		*	2015年12月21日
		*	mishengliang
	 */
	public void queryItemShowInfo(Integer ItemSetId,Map<String, Object> jsonMap) throws Exception;
}
