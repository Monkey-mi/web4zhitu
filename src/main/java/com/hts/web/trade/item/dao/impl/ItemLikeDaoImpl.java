package com.hts.web.trade.item.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.trade.item.dao.ItemLikeDao;

/**
 * 商品点赞数据实现类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
@Service
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
	
	
	private static final String QUERY_LIKE = "select item_id from " + table
			+ " where uid=? and item_id in";

	@Override
	public void saveLikeItem(Integer itemId, Integer uid) {
		getMasterJdbcTemplate().update(SAVE_ITEM_LIKE , new Object[]{uid, itemId});
	}

//	@Override
//	public boolean isExist(Integer itemId, Integer uid) {
//		int count = getJdbcTemplate().queryForInt(QUERY_ITEM_LIKE, new Object[]{uid, itemId});
//		if ( count > 0 ) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	@Override
	public void queryLike(Integer[] itemIds, Integer uid,
			final RowCallback<Integer> callback) {
		String sql = QUERY_LIKE + SQLUtil.buildInSelection(itemIds);
		Object[] args = SQLUtil.getArgsByInCondition(itemIds, new Object[]{uid}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("item_id"));
			}
			
		});
	}
	
}
