package com.hts.web.aliyun.service;

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
			Integer danmu, Integer mood, Integer world);

	/**
	 * 立即更新频道索引
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
			Integer danmu, Integer mood, Integer world);

}
