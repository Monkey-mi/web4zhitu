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
	 * 构建频道精选列表
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
	public void buildSuperbChannelWorld(Integer channelId, Integer userId, Integer maxId,
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
	
	
	/**
	 * 构建加入的频道列表
	 * 
	 * @param nameOnly
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSubscribedChannel(Boolean nameOnly, Integer userId, Integer maxId,
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建单个频道基本信息
	 * 
	 * @param channelId
	 * @param userId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildChannelAbstract(Integer channelId, Integer userId, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建频道详情
	 * 
	 * @param channelId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildChannelDetail(Integer channelId, Integer userId, Integer memberLimit,
			Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 保存成员
	 * 
	 * @param channelId
	 * @param userId
	 * @throws Exception
	 */
	public void saveMember(Integer channelId, Integer userId) throws Exception;
	
	/**
	 * 删除成员
	 * 
	 * @param channelId
	 * @param userId
	 * @throws Exception
	 */
	public void deleteMember(Integer channelId, Integer userId) throws Exception;
	
	/**
	 * 构建热门频道
	 * 
	 * @throws Exception
	 */
	public void buildHot(Integer start, Integer limit, Integer userId,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存频道织图
	 * 
	 * @param channelId
	 * @param worldId
	 * @param userId
	 */
	public void saveChannelWorld(Integer channelId, Integer worldId, 
			Integer authorId, Integer addChildCount);
	
	/**
	 * 保存频道
	 * 
	 * @param ownerId
	 * @param channelName
	 * @param channelTitle
	 * @param subtitle
	 * @param channelDesc
	 * @param channelIcon
	 * @param subIcon
	 * @param channelType
	 * @param channelLabel
	 * @param danmu
	 * @param mood
	 * @param world
	 */
	public void saveChannel(Integer ownerId, String channelName, String channelTitle,
			String subtitle, String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, Integer danmu, Integer mood, Integer world);
	
	/**
	 *　构建系统弹幕列表
	 *
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @throws Exception
	 */
	public void buildSysDanmu(Integer channelId, Integer userId, Integer maxId, 
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 更新织图和图片总数
	 * 
	 * @param channelId
	 * @param addChildCount　指定添加的图片数
	 */
	public void addWorldCountAndChildCount(Integer channelId, Integer addChildCount);

	/**
	 * 更新图片总数
	 * 
	 * @param channelId
	 */
	public void updateWorldCount(Integer channelId);
	
	/**
	 * 更新成员数
	 * 
	 * @param channelId
	 */
	public void updateMemberCount(Integer channelId);

	/**
	 * 
	 * @param channelId
	 */
	public void updateSuperbCount(Integer channelId);
	
	/**
	 * 构建关联频道列表
	 * 
	 * @param channelId
	 * @param jsonMap
	 */
	public void buildLinkChannel(Integer channelId, Map<String, Object> jsonMap);
	
}
