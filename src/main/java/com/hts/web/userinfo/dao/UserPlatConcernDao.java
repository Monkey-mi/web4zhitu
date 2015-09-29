package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;

public interface UserPlatConcernDao extends BaseDao {

	public void savePlatConcern(Integer userId, List<Integer> platConcernIds);
	
	public Integer queryCid(Integer userId, Integer platConcernId);
	
}
