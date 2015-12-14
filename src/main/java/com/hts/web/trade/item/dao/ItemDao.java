package com.hts.web.trade.item.dao;

/**
 * 商品数据接口类
 * 
 * @author zhangbo	2015年12月14日
 *
 */
public interface ItemDao {
	
	/**
	 * 增加商品点赞数
	 * 
	 * @author zhangbo	2015年12月14日
	 */
	void likeNumPlusOne(Integer id);
}
