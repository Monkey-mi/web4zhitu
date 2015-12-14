package com.hts.web.trade.item.dao.impl;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.trade.item.dao.ItemDao;

/**
 * 商品数据实现类
 * 
 * @author zhangbo	2015年12月14日
 *
 */
public class ItemDaoImpl extends BaseDaoImpl implements ItemDao {
	
	/**
	 * 商品表
	 * @author zhangbo	2015年12月10日
	 */
	private static final String table = HTS.ITEM;
	
	/**
	 * 点赞数加一
	 * @author zhangbo	2015年12月10日
	 */
	private static final String UPDATE_LIKENUM_PLUS_ONE = "update " + table + " set like_num = like_num + 1 where id = ?";

	@Override
	public void likeNumPlusOne(Integer id) {
		getMasterJdbcTemplate().update(UPDATE_LIKENUM_PLUS_ONE, new Object[]{id});
	}
	
}
