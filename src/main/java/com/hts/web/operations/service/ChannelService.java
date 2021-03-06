package com.hts.web.operations.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.OpStarModuleInfo;
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
	 * @param commentLimit
	 * @param likedLimit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildChannelWorld(Integer channelId, Integer userId, Integer maxId,
			Integer start, Integer limit, Integer commentLimit,
			Integer likedLimit, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 构建频道精选列表
	 * 
	 * @param channelId
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param commentLimit
	 * @param likedLimit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSuperbChannelWorld(Integer channelId, Integer userId, Integer maxId,
			Integer start, Integer limit, Integer commentLimit,
			Integer likedLimit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建未生效的频道织图列表
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
	public void buildUnValidChannelWorld(Integer channelId, Integer userId, Integer maxId,
			Integer start, Integer limit, Integer commentLimit,
			Integer likedLimit, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 构建未生效频道织图总数
	 * 
	 * @param channelId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildUnValidWorldCount(Integer channelId, 
			Map<String, Object> jsonMap) throws Exception;
	
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
	 * @param hasChannelStar
	 * @param channelStarLimit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildChannelAbstract(Integer channelId, Integer userId, 
			Boolean hasChannelStar, Integer channelStarLimit, 
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
	 * 保存频道织图,并自动关注该频道
	 * 
	 * @param channelId
	 * @param worldId
	 * @param userId
	 * @author lynch 2015-09-16
	 */
	public void saveChannelWorld(Integer channelId, Integer worldId, 
			Integer authorId, Integer addChildCount, Integer valid) throws Exception;
	
	/**
	 * 根据id查询频道名称
	 * 
	 * @param channelId
	 * @return
	 */
	public String queryChannelNameById(Integer channelId);
	
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
	 * 更新织图和图片总数
	 * 
	 * @param channelId
	 */
	public void updateWorldAndChildCount(Integer channelId);
	
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
	
	/**
	 * 构建专题频道
	 * 
	 * @param themeId	主题id
	 * @param maxId		主题频道最大id（为频道id）
	 * @param limit		查询限定数量，即返回主题频道数量
	 * @param jsonMap	返回值json
	 * 
	 * @throws Exception
	 * 
	 * @author zhutianjie
	 * @modify zhangbo	2015年12月3日
	 */
	public void buildThemeChannel(Integer themeId, Integer maxId, Integer limit, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 删除织图
	 * 
	 * @param channelId
	 * @param worldId
	 * @param userId
	 * @throws Exception
	 */
	public void deleteWorld(Integer channelId, Integer worldId,
			Integer userId) throws Exception;
	
	/**
	 * 添加织图
	 * 
	 * @param channelId
	 * @param worldId
	 * @param userId
	 * @throws Exception
	 */
	public void updateAcceptWorld(Integer channelId, Integer worldId, 
			Integer userId) throws Exception;
	
	/**
	 * 拒绝织图
	 * 
	 * @param channelId
	 * @param worldId
	 * @param userId
	 * @throws Exception
	 */
	public void updateRejectWorld(Integer channelId, Integer worldId,
			Integer userId) throws Exception;
	
	/**
	 * 添加织图精选
	 * 
	 * @param channelId
	 * @param worldId
	 * @param userId
	 * @throws Exception
	 */
	public void addWorldSuperb(Integer channelId, Integer worldId,
			Integer userId) throws Exception;
	
	/**
	 * 删除织图精选
	 * 
	 * @param channelId
	 * @param worldId
	 * @param userId
	 * @throws Exception
	 */
	public void deleteWorldSuperb(Integer channelId, Integer worldId,
			Integer userId) throws Exception;
	
	/**
	 * 获取达人推荐页面所需要的信息
	 * @param topicId
	 * @throws Exception 
		*	2015年9月25日
		*	mishengliang
	 */
	public void getStarRecommendTopicInfo(Integer topicId,Map<String, Object> jsonMap) throws Exception;
	
	public void getStarWorldRecommendTopicInfo(Integer topicId,Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 利用channelId查询出频道分享页面的所有信息
	 * 包括：
	 * 1.频道banner图
	 * 2.频道描述
	 * 3.频道关注人数
	 * 4.频道参与人数
	 * 5.频道中织图精选数
	 * 6.缩略banner（用于微信分享）
	 * 7.一定量的精选织图封面
	 * 8.织图对应的
	 * @param channerId
	 * @param jsonMap
	 * @throws Exception 
		*	2015年11月7日
		*	mishengliang
	 */
	public void  getChannelSharePageInfo(Integer channerId,Map<String, Object> jsonMap)throws Exception ;
}
