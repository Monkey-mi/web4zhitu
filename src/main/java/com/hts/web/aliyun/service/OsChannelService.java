package com.hts.web.aliyun.service;

import java.util.List;

import com.hts.web.common.pojo.OpChannel;

/**
 * OpenSearch channel业务逻辑访问接口
 * 
 * @author lynch
 *
 */
public interface OsChannelService {

	/**
	 * 立即保存频道索引
	 * 
	 * @param id
	 * @param ownerId
	 * @param channelName
	 * @param channelTitle
	 * @param subtitle
	 * @param channelDesc
	 * @param channelIcon
	 * @param subIcon
	 * @param channelType
	 * @param channelLabel
	 * @param labelIds
	 * @param danmu
	 * @param mood
	 * @param world
	 */
	public void saveChannelAtOnce(Integer id, Integer ownerId,
			String channelName, String channelTitle, String subtitle,
			String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, String labelIds,
			Integer danmu, Integer mood, Integer world) throws Exception;

	/**
	 * 批量保存频道
	 * 
	 * @param channels
	 * @throws Exception
	 */
	public void saveChannelAtOnce(List<OpChannel> channels) throws Exception;
	
	/**
	 *　更新频道
	 * 
	 * @param id
	 * @param ownerId
	 * @param channelName
	 * @param channelTitle
	 * @param subtitle
	 * @param channelDesc
	 * @param channelIcon
	 * @param subIcon
	 * @param channelType
	 * @param channelLabel
	 * @param labelIds
	 * @param danmu
	 * @param mood
	 * @param world
	 */
	public void updateChannelAtOnce(Integer id, Integer ownerId,
			String channelName, String channelTitle, String subtitle,
			String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, String labelIds,
			Integer danmu, Integer mood, Integer world) throws Exception;
	
	/**
	 * 批量更新频道
	 * 
	 * @param channels
	 * @throws Exception
	 */
	public void updateChannelAtOnce(List<OpChannel> channels) throws Exception;
	
	
	/**
	 * 立即删除频道
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteChannelAtOnce(Integer id) throws Exception;
	
	/**
	 * 根据ids立即删除频道
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void deleteChannelAtOnce(Integer[] ids) throws Exception;

}
