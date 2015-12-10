package com.hts.web.trade.item.dao;

import java.util.List;

import com.hts.web.trade.item.dto.ItemDTO;

/**
 * 商品集合数据接口类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
public interface ItemSetDao {
	
	/**
	 * 通过商品集合id，查询集合中的商品
	 * 
	 * @return List<ItemDTO>	商品列表
	 * @throws Exception
	 * @author zhangbo	2015年12月10日
	 */
	List<ItemDTO> queryItemListBySetId(Integer itemSetId) throws Exception;

}
