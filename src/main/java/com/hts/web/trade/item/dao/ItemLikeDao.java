package com.hts.web.trade.item.dao;

import com.hts.web.base.database.RowCallback;

/**
 * 商品点赞数据接口类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
public interface ItemLikeDao {
	
	/**
	 * 为商品点赞
	 * 
	 * @param itemId	商品id
	 * @param uid		用户id
	 * 
	 * @author zhangbo	2015年12月10日
	 */
	void saveLikeItem(Integer itemId, Integer uid);
	
	/**
	 * 查询喜欢标记
	 * 
	 * @param itemIds
	 * @param uid
	 */
	public void queryLike(Integer[] itemIds, Integer uid, RowCallback<Integer> callback);
	
}
