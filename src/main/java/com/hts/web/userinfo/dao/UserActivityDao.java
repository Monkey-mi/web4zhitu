package com.hts.web.userinfo.dao;

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
	public Integer queryTotalScore(Integer userId);
	
}
