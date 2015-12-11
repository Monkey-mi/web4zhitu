package com.hts.web.trade.item.dao;

import java.util.List;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.trade.item.dto.ItemDTO;

/**
 * 商品redis缓存操作类
 * 
 * @author zhangbo	2015年12月11日
 *
 */
public class ItemCache extends BaseCacheDaoImpl<ItemDTO>{
	
	/**
	 * 根据商品集合id，查询其下的商品列表
	 * 
	 * @param ItemSetId		商品集合id
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	public List<ItemDTO> queryItemListBySetId(Integer ItemSetId, RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.ITEM_LIST_BY_SETID + ItemSetId, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}
}
