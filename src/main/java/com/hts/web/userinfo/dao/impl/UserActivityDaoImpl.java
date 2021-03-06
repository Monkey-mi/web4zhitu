package com.hts.web.userinfo.dao.impl;

import java.util.Date;

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
	private static final String SAVE_ACTIVITY = "insert into " + table
			+ " (user_id,type_id,date_added,score) values (?,?,?,?)";
	
	/**
	 * 查询总数
	 */
	private static final String QUERY_USER_ACTIVITY_TOTAL_COUNT = "select count(*) from hts.user_activity where user_id=? and type_id=? and date_added between ? and ?";
	
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
	public long queryUserActivityTotalCount(Integer userId,Integer typeId,Date begin,Date end){
		return getMasterJdbcTemplate().queryForLong(QUERY_USER_ACTIVITY_TOTAL_COUNT, userId,typeId,begin,end);
	}

}
