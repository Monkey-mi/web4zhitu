package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
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
	
}
