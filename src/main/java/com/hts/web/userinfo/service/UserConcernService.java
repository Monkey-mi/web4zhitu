package com.hts.web.userinfo.service;

import java.util.List;

import com.hts.web.common.pojo.ObjectWithConcerned;
import com.hts.web.common.pojo.ObjectWithIsMututal;
import com.hts.web.common.service.BaseService;

public interface UserConcernService extends BaseService {

	/**
	 * 查询关注状态
	 * 
	 * @param joinId
	 * @param objList
	 */
	public void extractConcernStatus(Integer joinId, List<? extends ObjectWithConcerned> objList);
	
	/**
	 * 查询关注状态
	 * 
	 * @param joinId
	 * @param objList
	 */
	public void extractIsMututal(Integer joinId, List<? extends ObjectWithIsMututal> objList);
	
}
