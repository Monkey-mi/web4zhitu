package com.hts.web.operations.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface MsgOperationsService extends BaseService {

	public void buildNotice(Integer userId, Integer phoneCode,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询启动页列表
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildStartPage(Map<String, Object> jsonMap)
			throws Exception;
	
	/**
	 * 查询频道公告
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildBulletin(Map<String, Object> jsonMap)
			throws Exception;
	
	/**
	 * 查询专题列表
	 * 
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildTheme(int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询用户专题
	 * 
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildUserTheme(int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询频道专题
	 * 
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * @author zxx 2015-12-10 20:08:53
	 */
	public void buildChannelTheme(int start, int limit,
			Map<String, Object> jsonMap) throws Exception;

	/**
	 * 查询商品公告列表
	 * 
	 * @param maxId		最大id
	 * @param limit		每页查询数量
	 * @param jsonMap	返回值
	 * @author zhangbo	2015年12月7日
	 */
	void buildItemSet(Integer maxId, Integer limit, Map<String, Object> jsonMap) throws Exception;
	
	
}
