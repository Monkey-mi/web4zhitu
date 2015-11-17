package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldLabelWorldAuthor;
import com.hts.web.common.pojo.OpActivity;

/**
 * <p>
 * 广场活动数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-8
 * @author ztj
 *
 */
public interface ActivityDao extends BaseDao {

	public OpActivity queryActivityById(Integer id);
	
	public List<HTWorldLabelWorldAuthor> queryAuthorLikeRank(Integer activityId, int limit);
	
	/**
	 * 获取指定活动ID下的织图，用以活动页面分享
	 * @param activityId
	 * @param limit 
	 * @return 
		*	2015年11月16日
		*	mishengliang
	 */
	public List<HTWorld> getHtWorldByAid(Integer activityId, int limit);
	
	/**
	 *  查询活动参加人数
	 * @param activityId
	 * @return 
		*	2015年11月17日
		*	mishengliang
	 */
	public int getActivityCount(Integer activityId);
	
}
