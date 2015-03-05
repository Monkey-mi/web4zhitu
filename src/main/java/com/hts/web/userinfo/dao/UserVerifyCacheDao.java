package com.hts.web.userinfo.dao;

import java.util.List;
import java.util.Map;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.hts.web.common.pojo.ObjectWithUserVerifyDesc;
import com.hts.web.common.pojo.UserVerify;

public interface UserVerifyCacheDao extends BaseCacheDao {

	/**
	 * 构建认证信息
	 * 
	 * @param objs
	 */
	public void setUpVerify(List<? extends ObjectWithUserVerify> objs);
	
	/**
	 * 构建认证信息
	 * 
	 * @param obj
	 */
	public void setUpVerify(ObjectWithUserVerify obj);
	
	/**
	 * 构建认证信息（包含描述信息）
	 * 
	 * @param obj
	 */
	public void setUpVerifyDesc(ObjectWithUserVerifyDesc obj);
	
	
	/**
	 * 查询所有认证信息
	 * 
	 * @return
	 */
	public Map<Integer, UserVerify> queryVerify();
	
}
