package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserReport;
import com.hts.web.userinfo.dao.UserReportDao;

@Repository("HTSUserReportDao")
public class UserReportDaoImpl extends BaseDaoImpl implements UserReportDao {
	
	private static String table = HTS.USER_REPORT;
	
	/**
	 * 保存举报
	 */
	private static final String SAVE_REPORT = "insert into " + table 
			+ " (id, user_id,report_id,report_date) values (?,?,?,?)";
	
	/**
	 * 查询举报id
	 */
	private static final String QUERY_REPORT_ID = "select user_id,report_id,valid from " + table
			+ " where report_id=? and user_id=?";
	
	/**
	 * 更新举报
	 */
	private static final String UPDATE_REPORT = "update " + table
			+ " set id=?, report_date=?, valid=? where user_id=? and report_id=?";

	@Override
	public void saveReport(UserReport report) {
		getJdbcTemplate().update(SAVE_REPORT, new Object[]{
				report.getId(),
				report.getUserId(),
				report.getReportId(), 
				report.getReportDate()});
	}

	@Override
	public UserReport queryReportId(Integer userId, Integer reportId) {
		return queryForObjectWithNULL(QUERY_REPORT_ID, new Object[]{reportId, userId}, new RowMapper<UserReport>() {

			@Override
			public UserReport mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserReport ur = new UserReport();
				ur.setUserId(rs.getInt("user_id"));
				ur.setReportId(rs.getInt("report_id"));
				ur.setValid(rs.getInt("valid"));
				return ur;
			}
			
		});
	}

	@Override
	public void updateReport(UserReport report) {
		getJdbcTemplate().update(UPDATE_REPORT, new Object[]{
			report.getId(),
			report.getReportDate(),
			report.getValid(),
			report.getUserId(),
			report.getReportId()
		});
	}

}
