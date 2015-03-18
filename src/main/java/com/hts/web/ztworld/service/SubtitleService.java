package com.hts.web.ztworld.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface SubtitleService extends BaseService {

	/**
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSubtitle(Integer maxId, Integer start,
			Integer limit, Map<String, Object> jsonMap) throws Exception;
		
}
