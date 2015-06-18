package com.hts.web.userinfo.dao;

import java.util.Date;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserActivity;

/**
 * 用户活跃度记录表
 * 
 * @author tianjie
 *
 */
public interface UserActivityDao extends BaseDao {

	/**
	 * 保存活跃度
	 * 
	 * @param act
	 */
	public void saveActivity(UserActivity act);
	
	/**
	 * 查询总分
	 * 
	 * @param userId
	 * @return
	 */
//	public Integer queryTotalScore(Integer userId);
	
	/**
	 * 查询总数， 根据用户id，类型，开始时间，结束时间
	 * @param userId
	 * @param typeId
	 * @param begin
	 * @param end
	 * @return
	 */
	public long queryUserActivityTotalCount(Integer userId,Integer typeId,Date begin,Date end);
	
}
