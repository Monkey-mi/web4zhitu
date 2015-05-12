package com.hts.web.aliyun.service;

public interface OsChannelService {

	public void saveChannelAtOnce(Integer id, Integer ownerId, String channelName, 
			String channelTitle, String subtitle, String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, Integer danmu, Integer mood, Integer world);
	
	
	public void updateChannelAtOnce(Integer id, Integer ownerId, String channelName, 
			String channelTitle, String subtitle, String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, Integer danmu, Integer mood, Integer world);
	
}
