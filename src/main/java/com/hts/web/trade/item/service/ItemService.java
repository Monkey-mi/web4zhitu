package com.hts.web.trade.item.service;

import java.util.Map;

/**
 * 商品业务接口类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
public interface ItemService {

	/**
	 * 查询商品相关信息，
	 * 
	 * @param itemSetId	商品集合id
	 * @param uid		用户id
	 * @param jsonMap	返回值json
	 * @author zhangbo	2015年12月10日
	 */
	void queryItemInfo(Integer itemSetId, Integer uid, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 为商品点赞
	 * 
	 * @param itemId	商品id
	 * @param uid		用户id
	 * @author zhangbo	2015年12月10日
	 */
	void likeItem(Integer itemId, Integer uid)  throws Exception;

}
