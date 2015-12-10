package com.hts.web.trade.item.dao.impl;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.trade.item.dao.ItemLikeDao;

/**
 * 商品点赞数据实现类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
public class ItemLikeDaoImpl extends BaseDaoImpl implements ItemLikeDao {
	
	/**
	 * 商品点赞表
	 * @author zhangbo	2015年12月10日
	 */
	private static final String table = HTS.ITEM_LIKE;
	
	/**
	 * 保存
	 * @author zhangbo	2015年12月10日
	 */
	private static final String SAVE_ITEM_LIKE = "insert into " + table + "(uid, item_id) values (?,?)";
	
	private static final String QUERY_ITEM_LIKE = "select count(*) from " + table + " where uid=? and item_id=?";

	@Override
	public void saveLikeItem(Integer itemId, Integer uid) {
		getMasterJdbcTemplate().update(SAVE_ITEM_LIKE , new Object[]{uid, itemId});
	}

	@Override
	public boolean isExist(Integer itemId, Integer uid) {
		int count = getJdbcTemplate().queryForInt(QUERY_ITEM_LIKE, new Object[]{uid, itemId});
		if ( count > 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
}
