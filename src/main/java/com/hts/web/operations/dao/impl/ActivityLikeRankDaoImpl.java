package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpActivityLikeRank;
import com.hts.web.operations.dao.ActivityLikeRankDao;

@Repository("HTSActivityLikeRankDao")
public class ActivityLikeRankDaoImpl extends BaseDaoImpl implements
		ActivityLikeRankDao {
	
	private static String table = HTS.OPERATIONS_ACTIVITY_LIKE_RANK;
	
	/**
	 * 根据uids查询点赞排行
	 */
	private static final String QUERY_LIKE_RANK = "select * from " + table
			+ " where activity_id=? and user_id in ";

	@Override
	public void queryLikeRank(Integer activityId, Integer[] userIds,
			final RowCallback<OpActivityLikeRank> callback) {
		String inSelection = SQLUtil.buildInSelection(userIds);
		String sql = QUERY_LIKE_RANK + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(userIds, new Object[]{activityId}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildLikeRank(rs));
			}
		});
	}
	
	public OpActivityLikeRank buildLikeRank(ResultSet rs) throws SQLException {
		return new OpActivityLikeRank(
				rs.getInt("user_id"),
				rs.getInt("activity_id"),
				rs.getInt("like_count"),
				rs.getInt("last_pos"));
	}

}
