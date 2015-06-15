package com.hts.web.operations.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface MsgOperationsService extends BaseService {

	public void buildNotice(Integer userId, Integer phoneCode,
			Map<String, Object> jsonMap) throws Exception;
	
	public void buildStartPage(Map<String, Object> jsonMap)
			throws Exception;
	
	public void buildBulletin(Map<String, Object> jsonMap)
			throws Exception;
	
	
}
