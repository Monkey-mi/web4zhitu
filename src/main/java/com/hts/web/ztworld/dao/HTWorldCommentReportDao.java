package com.hts.web.ztworld.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldCommentReport;

public interface HTWorldCommentReportDao extends BaseDao {

	/**
	 * 保存举报
	 * 
	 * @param report
	 */
	public void saveReport(HTWorldCommentReport report);
	
	/**
	 * 根据评论id和用户id查询举报
	 * 
	 * @param userId
	 * @param commentId
	 * @return
	 */
	public Integer queryReportId(Integer userId, Integer commentId);
}
