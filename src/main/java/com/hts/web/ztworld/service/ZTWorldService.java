package com.hts.web.ztworld.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldThumbDto;
import com.hts.web.common.pojo.HTWorldWithExtra;
import com.hts.web.common.service.BaseService;

import net.sf.json.JSONObject;

/**
 * <p>
 * 织图业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-6
 * 
 * @author ztj
 * 
 */
public interface ZTWorldService extends BaseService {

	/**
	 * 保存世界
	 * 
	 * @param childsJSON
	 * @param titleId
	 * @param phoneCode
	 * @param id
	 * @param authorId
	 * @param worldName
	 * @param worldDesc
	 * @param worldLabel
	 * @param labelIds
	 * @param worldType
	 * @param typeId
	 * @param coverPath
	 * @param titlePath
	 * @param titleThumbPath
	 * @param longitude
	 * @param latitude
	 * @param locationDesc
	 * @param locationAddr
	 * @param province
	 * @param city
	 * @param size
	 * @param activityIds
	 * @return
	 * @throws Exception
	 */
	public void saveWorld(String childsJSON, Integer titleId,Integer phoneCode, 
			Integer id, Integer authorId, String worldName, String worldDesc, String worldLabel,
			String labelIds, String worldType, Integer typeId, String coverPath, String titlePath,
			String bgPath, String titleThumbPath, Double longitude, Double latitude, 
			String locationDesc, String locationAddr,String province, String city, Integer size, 
			String activityIds, Integer ver, String channelIds, Integer tp, String color, 
			Integer mask, String atIdsStr, String atNamesStr,
			Map<String, Object> jsonMap) throws Exception;

	/**
	 * 删除织图
	 * 
	 * @param worldId
	 * @param userId
	 */
	public void deleteWorld(Integer worldId, Integer userId) throws Exception;

	/**
	 * 根据id查询世界信息
	 * 
	 * @param worldId
	 * @param isAddClick
	 * @return
	 * @throws Exception
	 */
	public HTWorld getWorldById(Integer worldId, boolean isAddClick)
			throws Exception;

	/**
	 * 查询织图和首页子世界信息
	 * 
	 * @param worldId
	 * @param limit
	 */
	public void buildWorldWithTitleChild(Integer worldId, Integer limit,
			boolean isAdmin, Map<String, Object> jsonMap) throws Exception;

	/**
	 * 获取首页子世界信息
	 * 
	 * @param worldId
	 * @param limit
	 * @param isNotAddClick
	 * @param isAdmin
	 * @return
	 * @throws Exception
	 */
	public JSONObject getTitleChildPageInfo(Integer worldId, int limit,
			boolean isNotAddClick, boolean isAdmin) throws Exception;

	/**
	 * 根据子世界id查询其所在分页的子世界信息
	 * 
	 * @param worldId
	 * @param childId
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public JSONObject getChildWorldPageInfoById(Integer worldId,
			Integer childId, int limit) throws Exception;

	/**
	 * 获取织图评论点赞
	 * 
	 * @param commentLimit
	 * @param likedLimit
	 * @param worldList
	 */
	public void extractLikeComment(int commentLimit,
			int likedLimit,List<? extends HTWorldWithExtra> worldList);
	
	/**
	 * 获取织图评论点赞,以及是否喜欢过织图
	 * 
	 * @param userId
	 * @param commentLimit
	 * @param likedLimit
	 * @param worldList
	 */
	public void extractLikeComment(int userId, int commentLimit,
			int likedLimit, final List<? extends HTWorldWithExtra> worldList);
	
	/**
	 * 获取织图评论点赞
	 * 
	 * @param userId
	 * @param commentLimit
	 * @param likedLimit
	 * @param world
	 */
	public void extractLikeComment(int userId, int commentLimit,
			int likedLimit, HTWorldInteractDto world);

	/**
	 * 构建关注的好友分享的织图列表
	 * 
	 * @param recType 上次推荐的类型
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 */
	public void buildConcernWorld(Integer recType, Integer recPage, Integer userId, int maxId,
			int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			int commentLimit, int likedLimit) throws Exception;

	/**
	 * 构建收藏的织图列表
	 * 
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 */
	public void buildKeepWorld(final Integer userId, int maxId, 
			int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final int commentLimit, final int likedLimit) throws Exception;

	/**
	 * 构建喜欢的织图列表
	 * 
	 * @param userId
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param trimExtras
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 */
	public void buildLikedWorld(Integer userId, int maxId,
			int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final boolean trimExtras, final int commentLimit, final int likedLimit) throws Exception;

	/**
	 * 构建指定用户分享的织图列表
	 * 
	 * @param userId
	 * @param joinId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 */
	public void buildUserWorld(Integer userId, Integer joinId,
			int maxId, int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			int commentLimit, int likedLimit)
			throws Exception;

	/**
	 * 构建指定用户分享的随机织图列表
	 * 
	 * @param urlPrefix
	 * @param userId
	 * @param joinId
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildRandomUserWorld(Integer userId, Integer joinId, int limit,
			Map<String, Object> jsonMap) throws Exception;

	/**
	 * 从访问路径获取织图数据
	 * 
	 * @param requestURL
	 * @return
	 */
	public HTWorldDto getHTWorldDtoFromURL(String requestURL, boolean isAdmin)
			throws Exception;

	/**
	 * 根据id获取织图信息
	 * 
	 * @param worldId
	 * @param isAddClick
	 * @return
	 * @throws Exception
	 */
	public HTWorldDto getHTWorldDtoById(Integer worldId, boolean isAddClick)
			throws Exception;

	/**
	 * 更新播放次数
	 * 
	 * @param worldId
	 * @param clickCount
	 * @throws Exception
	 */
	public void addClickCount(Integer worldId, Integer clickCount)
			throws Exception;
	
	/**
	 * 构建最新织图列表
	 * 
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLatestWorld(int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建最新织图列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLatestWorld(Integer joinId, int maxId, int start, int limit, 
			Map<String, Object> jsonMap, boolean trimTotal, 
			int commentLimit, int likedLimit) throws Exception;
	
	/**
	 * 构建最新和用户织图列表
	 * 
	 * @param joinId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 */
	public void buildLatestAndUserWorld(final Integer joinId, final int maxId, int start, 
			final int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final int commentLimit, final int likedLimit) throws Exception;
	
	/**
	 * 构建织图互动列表
	 * 
	 * @param query
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildHTWorld(String query, Integer userId, 
			int maxId, int start, int limit, Map<String, Object> jsonMap, 
			boolean trimTotal, final int commentLimit, final int likedLimit) throws Exception;
	
	/**
	 * 构建最新子世界类型列表
	 * 
	 * @throws Exception
	 */
	public void buildLatestChildType(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 添加使用次数
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void addChildTypeUseCount(Integer id) throws Exception;
	
	/**
	 * 构建滤镜logo信息
	 * 
	 * @param ver
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildFilterLogo(Float ver, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建最新织图列表索引
	 * 
	 * @param userId
	 * @param startTime
	 * @param intervalType
	 * @param nextCursor
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLatestIndex(Integer userId, Long startTime, Integer intervalType,
			Integer nextCursor, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建最新织图列表
	 * 
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param maxId
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLatest(Integer userId, Long startTime, Long endTime, Integer maxId, Integer limit,
			Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 构建最新织图列表索引2
	 * 
	 * @param userId
	 * @param startTime
	 * @param intervalType
	 * @param nextCursor
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLatestIndex2(Integer userId, Long startTime, Integer intervalType,
			Integer nextCursor, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建最新织图列表2
	 * 
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param maxId
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLatest2(Integer userId, Long startTime, Long endTime, Integer maxId, Integer limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建所有子世界列表
	 * 
	 * @param worldId
	 * @param jsonMap
	 */
	public void buildAllChild(Integer worldId, Map<String, Object> jsonMap);
	
	/**
	 * 构建最新id
	 * 
	 * @param currentId
	 * @param previousLimit
	 * @param nextLimit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLatestId(Integer maxId, Integer minId,
			Integer limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询用户最新n个织图的title_path
	 * 
	 * @param userId
	 * @param n
	 * @return
	 * @throws Exception
	 * @author lynch 2015-11-16
	 */
	public void queryLastNHtworldInfoByUserId(Integer userId, Integer limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询附近的织图
	 * @param address 位置，若该参数为空，则longitude和latitude不能为空。用以确定所在的经纬度
	 * @param longitude	纬度，若该参数为空，则address不能为空
	 * @param latitude 经度，若该参数为空，则address不能为空
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 * @author zxx 2015-12-1 10:01:55
	 */
	public void queryAroundWorld(String address, Double longitude, Double latitude,int maxId, int start, int limit, Map<String, Object> jsonMap, 
			final int commentLimit, final int likedLimit)throws Exception;
	
}
