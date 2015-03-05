package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpActivitySponsor;
import com.hts.web.operations.dao.ActivitySponsorDao;

@Repository("HTSActivitySponsorDao")
public class ActivitySponsorDaoImpl extends BaseDaoImpl implements ActivitySponsorDao {
	
	private static String table = HTS.OPERATIONS_ACTIVITY_SPONSOR;
	
	/**
	 * 发起人信息
	 */
	private static final String SPONSOR_INFO = "u.id,u.user_name,u.user_avatar,u.user_avatar_l,u.star,u.platform_verify";
	
	/**
	 * 根据活动id查询发起人
	 */
	private static final String QUERY_SPONSOR_BY_ACTIVITY_ID = "select s.activity_id," + SPONSOR_INFO
			+ " from " + table + " as s," + HTS.USER_INFO + " as u"
			+ " where s.user_id=u.id and u.shield=0 and s.activity_id=?";
	
	/**
	 * 根据活动ids查询发起人
	 */
	private static final String QUERY_SPONSOR_BY_ACTIVITY_IDS = "select s.activity_id," + SPONSOR_INFO
			+ " from " + table + " as s," + HTS.USER_INFO + " as u"
			+ " where s.user_id=u.id and u.shield=0 and s.activity_id in ";
	
	
	@Override
	public void querySponsor(Integer[] activityIds,
			final RowCallback<OpActivitySponsor> callback) {
		String inSelection = SQLUtil.buildInSelection(activityIds);
		String sql = QUERY_SPONSOR_BY_ACTIVITY_IDS + inSelection;
		getJdbcTemplate().query(sql, (Object[])activityIds, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildSponsor(rs));
			}
		});
	}
	
	@Override
	public void querySponsor(Integer activityId, final RowCallback<OpActivitySponsor> callback) {
		getJdbcTemplate().query(QUERY_SPONSOR_BY_ACTIVITY_ID,
				new Object[]{activityId}, new RowCallbackHandler() {
			
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						callback.callback(buildSponsor(rs));
					}
			});
	}
	
	/**
	 * 构建OpActivitySponsor
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpActivitySponsor buildSponsor(ResultSet rs) throws SQLException {
		return new OpActivitySponsor(
				rs.getInt("activity_id"),
				rs.getInt("id"), 
				rs.getString("user_name"), 
				rs.getString("user_avatar"), 
				rs.getString("user_avatar_l"), 
				rs.getInt("star"),
				rs.getInt("platform_verify"));
	}

}
