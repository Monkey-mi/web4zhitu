package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldReport;
import com.hts.web.ztworld.dao.HTWorldReportDao;

/**
 * <p>
 * 举报织图数据访问对象
 * </p>
 * 
 * 创建时间：2013-7-31
 * @author ztj
 *
 */
@Repository("HTSHTWorldReportDao")
public class HTWorldReportDaoImpl extends BaseDaoImpl implements HTWorldReportDao{
	
	/**
	 * 织图举报表
	 */
	public static final String TABLE_HTWORLD_REPORT = "htworld_report";
	
	/**
	 * 保存举报
	 */
	private static final String SAVE_REPORT = "insert into " + TABLE_HTWORLD_REPORT + "(user_id,world_id,report_content,report_date,valid) values (?,?,?,?,?)";
	
	/**
	 * 查询织图举报
	 */
	private static final String QUERY_REPORT = "select * from " + TABLE_HTWORLD_REPORT + " where user_id=? and world_id=?";
	
	/**
	 * 梗系织图举报
	 */
	private static final String UPDATE_REPORT = "update " + TABLE_HTWORLD_REPORT + " set report_content=?,valid=?,report_date=? where user_id=? and world_id=?";
	
	/**
	 * 更新举报有效标志
	 */
	private static final String UPDATE_REPORT_VALID = "update " + TABLE_HTWORLD_REPORT + " set valid=?,report_date=? where user_id=? and world_id=?";
	
	
	@Override
	public HTWorldReport queryReport(Integer userId, Integer worldId) {
		return queryForObjectWithNULL(QUERY_REPORT, new RowMapper<HTWorldReport>(){

			@Override
			public HTWorldReport mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldReport(
						rs.getInt("id"), 
						rs.getInt("user_id"), 
						rs.getInt("world_id"),
						rs.getString("report_content"), 
						(Date)rs.getObject("report_date"),
						rs.getInt("valid"));
			}
			
		}, new Object[] { userId, worldId });
	}
	
	@Override
	public Integer saveReport(HTWorldReport report) {
		return save(SAVE_REPORT, new Object[]{
			report.getUserId(),
			report.getWorldId(),
			report.getReportContent(),
			report.getReportDate(),
			Tag.TRUE
		});
	}
	

	@Override
	public void updateReport(Integer userId, Integer worldId, String reportContent, Integer valid, Date date) {
		getJdbcTemplate().update(UPDATE_REPORT, new Object[]{reportContent, valid, date, userId, worldId});
	}
	
	@Override
	public void updateReportValid(Integer userId, Integer worldId, Integer valid, Date date) {
		getJdbcTemplate().update(UPDATE_REPORT_VALID, new Object[]{valid, date, userId, worldId});
	}

}
