package com.hts.web.trade.item.dao;

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
	 * 是否已经为商品点过赞
	 * 
	 * @param itemId	商品id
	 * @param uid		用户id
	 * 
	 * @return true：已经点过赞，false：未点过赞
	 * @author zhangbo	2015年12月10日
	 */
	boolean isExist(Integer itemId, Integer uid);

}
