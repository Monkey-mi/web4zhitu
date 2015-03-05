package com.hts.web.ztworld.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldCommentReport;
import com.hts.web.ztworld.dao.HTWorldCommentReportDao;

@Repository("HTSHTWorldCommentReportDao")
public class HTWorldCommentReportDaoImpl extends BaseDaoImpl implements
		HTWorldCommentReportDao {
	
	private static String table = HTS.HTWORLD_COMMENT_REPORT;
	
	private static final String QUERY_ID_BY_USER_COMMENT_ID = 
			"select id from " + table + " where user_id=? and comment_id=?";
	
	public static final String SAVE_REPORT = "insert into " + table
			+ " (user_id,comment_id,report_content,report_date,valid) values (?,?,?,?,?)";

	@Override
	public void saveReport(HTWorldCommentReport report) {
		getJdbcTemplate().update(SAVE_REPORT, new Object[]{
			report.getUserId(),
			report.getCommentId(),
			report.getReportContent(),
			report.getReportDate(),
			report.getValid()
		});
	}

	@Override
	public Integer queryReportId(Integer userId, Integer commentId) {
		return queryForObjectWithNULL(QUERY_ID_BY_USER_COMMENT_ID, 
				new Object[]{userId, commentId}, Integer.class);
	}

}
