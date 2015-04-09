package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.ztworld.dao.HTWorldStickerUnlockDao;

@Repository("HTSHTWorldStickerUnlockDao")
public class HTWorldStickerUnlockDaoImpl extends BaseDaoImpl implements
		HTWorldStickerUnlockDao {
	
	private static String table = HTS.HTWORLD_STICKER_UNLOCK;

	/**
	 * 保存解锁信息
	 */
	private static final String SAVE_UNLOCK = "insert into " + table
			+ " (sticker_id,user_id,unlock_time) values (?,?,?)";
	
	/**
	 * 查询用户解锁列表
	 */
	private static final String QUERY_UNLOCK = "select sticker_id from " + table
			+ " where user_id=? and sticker_id in";
	
	@Override
	public void saveUnlock(Integer stickerId, Integer userId, Date date) {
		getMasterJdbcTemplate().update(SAVE_UNLOCK, new Object[]{stickerId, userId, date});
	}

	@Override
	public void queryUnlock(Integer userId, Integer[] stickerIds,
			final RowCallback<Integer> callback) {
		String inSelection = SQLUtil.buildInSelection(stickerIds);
		String sql = QUERY_UNLOCK + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(stickerIds, new Object[]{userId}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("sticker_id"));
			}
		});
	}

}
