package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpActivityAward;
import com.hts.web.operations.dao.ActivityAwardDao;

@Repository("HTSActivityAwardDao")
public class ActivityAwardDaoImpl extends BaseDaoImpl implements
		ActivityAwardDao {

	private static final String table = HTS.OPERATIONS_ACTIVITY_AWARD;
	
	/**
	 * 查询活动所有奖品
	 */
	private static final String QUERY_ALL_AWARD = "select * from " + table 
			+ " where activity_id=?" + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据活动id查询奖品剩余总数
	 */
	private static final String QUERY_SUM_REMAIN = "select SUM(remain) from " + table
			+ " where activity_id=?";
	
	
	@Override
	public Integer querySumRemain(Integer activityId) {
		return getJdbcTemplate().queryForInt(QUERY_SUM_REMAIN, activityId);
	}
	
	@Override
	public List<OpActivityAward> queryAward(Integer activityId) {
		return getJdbcTemplate().query(QUERY_ALL_AWARD, new Object[]{activityId},
				new RowMapper<OpActivityAward>() {

					@Override
					public OpActivityAward mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return null;
					}
		});
	}
	
	@Override
	public void queryAward(Integer activityId,
			final RowCallback<OpActivityAward> callback) {
		getJdbcTemplate().query(QUERY_ALL_AWARD, new Object[]{activityId}, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildAward(rs));
			}
			
		});
	}
	
	@Override
	public OpActivityAward buildAward(ResultSet rs) throws SQLException{
		return new OpActivityAward(
				rs.getInt("id"), 
				rs.getInt("activity_id"),
				rs.getString("icon_thumb_path"),
				rs.getString("icon_path"),
				rs.getString("award_name"),
				rs.getString("award_desc"),
				rs.getDouble("price"),
				rs.getString("award_link"),
				rs.getInt("total"),
				rs.getInt("remain"),
				rs.getInt("serial"));
	}


}
