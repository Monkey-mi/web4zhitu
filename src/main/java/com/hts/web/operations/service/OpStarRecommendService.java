package com.hts.web.operations.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.OpStarRecommendDto;

public interface OpStarRecommendService {
	
	/**
	 * 分页查询达人推荐
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public List<OpStarRecommendDto> queryStarRecommend(int maxId, int start, int limit, int worldLimit)throws Exception;
	
	/**
	 * 从cache中查询达人推荐
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryStarRecommend(Map<String, Object> jsonMap)throws Exception;
	
	/**
	 * 从缓存中查询达人推荐数据
	 * @return
	 * @throws Exception
	 */
	public List<OpStarRecommendDto> queryStarRecommend()throws Exception;
	
	/**
	 * 更新activity
	 * @param userId
	 * @param totalCount
	 * @throws Exception
	 */
	public void updateStarRecommend(Integer userId,Integer activity)throws Exception;
	
	/**
	 * 更新达人推荐缓存
	 * @throws Exception
	 */
	public void updateStarRecommendCache()throws Exception;
	
}
