package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserDynamicRec;

public interface UserPlatConcernDao extends BaseDao {

	public void savePlatConcern(Integer userId, Integer platConcernId);
	
	public Integer queryCid(Integer userId, Integer platConcernId);
	
}
