package com.hts.web.userinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserActivity;
import com.hts.web.userinfo.dao.UserActivityDao;

@Repository("HTSUserActivityDao")
public class UserActivityDaoImpl extends BaseDaoImpl implements UserActivityDao {
	
	private static final String table = HTS.USER_ACTIVITY;

	/**
	 * 保存活跃度
	 */
	public static final String SAVE_ACTIVITY = "insert into " + table
			+ " (user_id,type_id,date_added,score) values (?,?,?,?)";
	
	/**
	 * 查询总分
	 */
	public static final String QUERY_TOTAL_SCORE = "select sum(score) from " + table
			+ " where user_id=?";
	
	@Override
	public void saveActivity(UserActivity act) {
		getMasterJdbcTemplate().update(SAVE_ACTIVITY, new Object[]{
			act.getUserId(),
			act.getTypeId(),
			act.getDateAdded(),
			act.getScore()
		});
	}

	@Override
	public Integer queryTotalScore(Integer userId) {
		return getJdbcTemplate().queryForInt(QUERY_TOTAL_SCORE, userId);
	}

}
