package com.hts.web.trade.item.dao;

import java.util.List;

import com.hts.web.trade.item.dto.ItemSetDTO;

/**
 * 商品集合数据接口类
 * 
 * @author zhangbo	2015年12月12日
 *
 */
public interface ItemSetDao {
	
	/**
	 * 查询商品集合列表
	 * 
	 * @param maxId	最大maxId，作为查询标记位
	 * @param limit	每页查询数量
	 * @return
	 * @throws Exception
	 * @author zhangbo	2015年12月12日
	 */
	List<ItemSetDTO> queryItemSetList(Integer maxId, Integer limit) throws Exception;
}
