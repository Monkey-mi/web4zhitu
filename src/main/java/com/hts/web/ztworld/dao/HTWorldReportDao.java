package com.hts.web.ztworld.dao;

import java.util.Date;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldReport;

/**
 * <p>
 * 举报织图数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface HTWorldReportDao extends BaseDao {

	/**
	 * 查询织图举报
	 * 
	 * @param userId
	 * @param worldId
	 */
	public HTWorldReport queryReport(Integer userId, Integer worldId);
	
	
	/**
	 * 保存举报
	 * @param report
	 * @return
	 */
	public Integer saveReport(HTWorldReport report);
	
	/**
	 * 更新举报
	 * 
	 * @param userId
	 * @param worldId
	 */
	public void updateReport(Integer userId, Integer worldId, String reportContent, Integer valid, Date date);
	
	/**
	 * 更新举报有效标志
	 * @param userId
	 * @param worldId
	 * @param reportContent
	 * @param valid
	 * @param date
	 */
	public void updateReportValid(Integer userId, Integer worldId, Integer valid, Date date);
}
