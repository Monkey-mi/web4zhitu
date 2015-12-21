package com.hts.web.trade.item.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.trade.item.dto.ItemDTO;

/**
 * 商品redis缓存操作接口类
 * 
 * @author zhangbo	2015年12月11日
 *
 */
public interface ItemCacheDao extends BaseCacheDao {
	
	/**
	 * 根据商品集合id，查询其下的商品列表
	 * 
	 * @param itemSetId		商品集合id
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	public List<ItemDTO> queryItemListBySetId(Integer itemSetId, RowSelection rowSelection);

	/**
	 * 刷新商品点赞数，为点赞数+1
	 * 
	 * @param itemId	商品id
	 * @author zhangbo	2015年12月15日
	 */
	public void refreshItemLikeNumPlusOne(Integer itemId);
	
}
