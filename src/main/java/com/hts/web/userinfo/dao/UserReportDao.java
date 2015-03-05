package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserReport;

public interface UserReportDao extends BaseDao {

	/**
	 * 保存举报
	 * 
	 * @param userId
	 * @param reportId
	 * @param reportDate
	 */
	public void saveReport(UserReport report);
	
	/**
	 * 更新举报
	 * 
	 * @param report
	 */
	public void updateReport(UserReport report);
	
	/**
	 * 查询举报id
	 * 
	 * @param userId
	 * @param reportId
	 * @return
	 */
	public UserReport queryReportId(Integer userId, Integer reportId);
}
