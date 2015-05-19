package com.hts.web.ztworld.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldWithExtra;
import com.hts.web.common.service.BaseService;

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
	 * @param thumbs
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
	public HTWorld saveWorld(String childsJSON, Integer titleId,Integer phoneCode, 
			Integer id, Integer authorId, String worldName, String worldDesc, String worldLabel,
			String labelIds, String worldType, Integer typeId, String coverPath, String titlePath,
			String bgPath, String titleThumbPath, String thumbs, Double longitude, Double latitude, 
			String locationDesc, String locationAddr,String province, String city, Integer size, 
			String activityIds, Integer ver, String channelIds, Integer tp, String color, 
			Integer mask) throws Exception;

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
	 * 提取织图额外信息（赞，评论列表）
	 * 
	 * @param trimExtra
	 * @param commentLimit
	 * @param likedLimit
	 * @param worldLimit 提取总数
	 * @param worldList
	 */
	public void extractExtraInfo(boolean trimExtra, int commentLimit,
			int likedLimit, int worldLimit, List<? extends HTWorldWithExtra> worldList);
	
	/**
	 * 获取织图额外信息
	 * 
	 * @param extractLiked 获取喜欢标记
	 * @param extractKeep 获取收藏标记
	 * @param trimExtra
	 * @param commentLimit
	 * @param likedLimit
	 * @param worldLimit
	 * @param worldList
	 */
	public void extractExtraInfo(boolean extractLiked, boolean extractKeep, Integer userId, boolean trimExtra, int commentLimit,
			int likedLimit, int worldLimit, final List<? extends HTWorldWithExtra> worldList);
	
	/**
	 * 提取织图额外信息（赞，评论列表）
	 * @param trimExtra
	 * @param commentLimit
	 * @param likedLimit
	 * @param world
	 */
	public void extractExtraInfo(boolean extractLiked, boolean extractKeep, Integer userId, boolean trimExtra, int commentLimit,
			int likedLimit, HTWorldInteractDto world);

	/**
	 * 构建关注的好友分享的织图列表
	 * 
	 * @param userId
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @param trimTotal 是否过滤总数
	 * @param trimExtras
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 */
	public void buildConcernWorld(Integer userId, int maxId,
			int start, int limit, Map<String, Object> jsonMap, boolean trimTotal,
			boolean trimExtras, int commentLimit, int likedLimit) throws Exception;

	/**
	 * 构建收藏的织图列表
	 * 
	 * @param userId
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
	public void buildKeepWorld(final Integer userId, int maxId, 
			int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final boolean trimExtras, final int commentLimit, final int likedLimit) throws Exception;

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
	 * @param trimExtras
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 */
	public void buildUserWorld(Integer userId, Integer joinId,
			int maxId, int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			boolean trimExtras, int commentLimit, int likedLimit)
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
	
	/**;
					label = new HTWorldLabel(labelId, name, pinyin, 0, new Date(), Tag.FALSE, Tag.TRUE, 0, 0);
					worldLabelDao.saveLabel(label);
				} else {
					labelId = label.getId();
//					if(label.getLabelState().equals(Tag.WORLD_LABEL_ACTIVITY)) {
//						valid = Tag.FALSE;
//					}
				}
				Integer lwid = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
				worldLabelWorldDao.saveLabelWorld(new HTWorldLabelWorld(lwid, worldId, authorId, 
						labelId, valid, lwid, 0));
				int count = 0;
//				if(label.getLabelState().equals(Tag.WORLD_LABEL_NORMAL)) { // 普通标签算真实总数，其他标签等审核
				Long labelWorldCount = worldLabelWorldDao.queryWorldCountByLabelId(labelId);
				count = labelWorldCount.intValue();
				worldLabelDao.updateWorldCount(labelId, count);
//				}
	 * 构建最新织图列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLatestWorld(Integer joinId, int maxId, int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			boolean trimExtras, int commentLimit, int likedLimit) throws Exception;
	
	/**
	 * 构建最新和用户织图列表
	 * 
	 * @param joinId
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
	public void buildLatestAndUserWorld(final Integer joinId, final int maxId, int start, final int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final boolean trimExtras, final int commentLimit, final int likedLimit) throws Exception;
	
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
			boolean trimTotal, final boolean trimExtras,
			final int commentLimit, final int likedLimit) throws Exception;
	
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
	
	
}
