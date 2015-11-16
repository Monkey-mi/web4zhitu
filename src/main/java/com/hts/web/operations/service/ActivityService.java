package com.hts.web.operations.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface ActivityService extends BaseService{
	
	public void getactivityPageInfoByAid(Integer aid,Map<String, Object> jsonMap) throws Exception;
	
}
