package com.hts.web.operations.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpWorldTypeDto;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 织图运营子模块业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-8
 * @author ztj
 *
 */
public interface ZTWorldOperationsService extends BaseService {
	
	/**
	 * 构建广场推送列表
	 * 
	 * @param isRandom
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildSquarePush(boolean isRandom, int sinceId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建广场推送列表
	 * 
	 * @param isRandom
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSquarePush(boolean isRandom, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 获取最大推送id
	 * 
	 * @throws Exception
	 */
	public int getMaxSquarePushId() throws Exception;

	/**
	 * 构建最新广场话题列表
	 * @param limit
	 */
	public void buildLatestSquarePushTopic(int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 获取广场推送列表
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<OpWorldTypeDto> getSquarePushs(int sinceId, int maxId, int start, int limit) throws Exception;
	
	/**
	 * 获取广场推送列表
	 * 
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param label
	 * @return
	 */
	public List<OpWorldTypeDto> getSquarePushs(int sinceId, int maxId, int start, int limit, String label[]) throws Exception;
	
	/**
	 * 获取广场分类索引列表
	 * 
	 * @param superbLimit
	 * @param typeLimit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSquarePushLabelIndex(Integer superbLimit, Integer typeLimit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 根据标签获取广场推送列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param squareLabel
	 * @param joinId
	 * @param jsonMap
	 * @return
	 * @throws Exception
	 */
	public void buildSquarePush(int maxId, int start, int limit, int squareLabel, Integer joinId, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建分类列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param commentLimit
	 * @param likedLimit
	 * @param completeLimit 包含评论和喜欢列表的织图数量
	 * @param typeId
	 * @param joinId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildTypeSquare(int maxId, int start,
			int limit, final int commentLimit, int likedLimit, 
			int typeId, Integer joinId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询精品分类
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param commentLimit
	 * @param likedLimit
	 * @param completeLimit
	 * @param trimTutorial
	 * @param trimConcernId
	 * @param trimVer0
	 * @param joinId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSuperbTypeSquare(int maxId, int start,
			int limit, int commentLimit, int likedLimit, 
			int completeLimit, boolean trimTutorial, boolean trimConcernId,
			boolean trimVer0, Integer joinId, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 查询精品列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param pageCount
	 * @param isRefresh
	 * @param commentLimit
	 * @param likedLimit
	 * @param completeLimit
	 * @param trimTutorial
	 * @param trimConcernId
	 * @param joinId
	 * @param jsonMap
	 */
	public void buildSuperbTypeSquareList(int maxId, int start, int limit, int pageCount, boolean isRefresh, 
			int commentLimit, int likedLimit, int completeLimit, boolean trimConcernId,
			Integer joinId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建精品列表V2
	 * 
	 * @param typeId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param commentLimit
	 * @param likedLimit
	 * @param completeLimit
	 * @param trimConcernId
	 * @param joinId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSuperbTypeSquareListV2(Integer typeId, int maxId, int start, int limit,
			int commentLimit, int likedLimit, int completeLimit, boolean trimConcernId,int channelCount,
			Integer joinId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建精品列表V3
	 * 
	 * @param joinId
	 * @param maxId
	 * @param cursor
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSuperbTypeSquareListV3(Integer joinId, int maxId, int cursor, int start, int limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建广场随机分类列表
	 * 
	 * @param limit
	 * @param joinId
	 * @param jsonMap
	 */
	public void buildRandomLabelPush(int limit, Integer joinId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建分类索引
	 * 
	 * @param userId
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildTypeIndex(Integer userId, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建最新活动列表
	 * 
	 * @param activityLimit
	 * @param joinId
	 * @param refreshCount
	 * @param authorLimit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildActivity(Integer activityLimit, Integer joinId, Boolean refreshCount, 
			Integer authorLimit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建商业活动logo列表
	 * 
	 * @param jsonMap
	 */
	public void buildCommercialActivityLogo(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询最新普通活动
	 * 
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public OpActivity getMaxActivity(Integer maxId) throws Exception;
	
	/**
	 * 构建活动获奖名单
	 * 
	 * @param isOrderBySerial
	 * @param activityId
	 * @param joinId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param commentLimit
	 * @param likedLimit
	 */
	public void buildActivityWinner(boolean isOrderBySerial, Integer activityId, Integer joinId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,boolean trimTotal, 
			int commentLimit, int likedLimit) throws Exception;
	
	/**
	 * 查询活动点赞排行
	 * 
	 * @param activityId
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildActivityLikeRank(Integer joinId, Integer activityId,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建教程列表
	 * 
	 * @param commentLimit
	 * @param likedLimit
	 * @param completeLimit
	 * @param trimConcernId
	 * @param joinId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildTutorial(int commentLimit, int likedLimit, 
			int completeLimit, boolean trimConcernId,
			Integer joinId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建活动明星列表
	 * 
	 * @param joinId
	 * @param jsonMap
	 */
	public void buildActivityStar(Integer joinId, Integer activityId, Map<String, Object> jsonMap);
	
}
