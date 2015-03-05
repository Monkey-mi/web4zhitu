package com.hts.web.operations.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

public interface ChannelService extends BaseService{
	
	/**
	 * 构建channel列表
	 * 
	 * @param jsonMap
	 * @return
	 * @throws Exception
	 */
	public void buildChannel(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建频道红人信息
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildMaxChannelTopOne(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建channel world列表
	 * 
	 * @param channelId
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param trimExtras
	 * @param commentLimit
	 * @param likedLimit
	 * @param completeLimit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildChannelWorld(Integer channelId, Integer userId, Integer maxId,
			Integer start, Integer limit, Boolean trimExtras, Integer commentLimit,
			Integer likedLimit, Integer completeLimit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建频道Top One列表
	 * 
	 * @param userId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildChannelTopOne(Integer userId, Map<String, Object> jsonMap) throws Exception;

}
