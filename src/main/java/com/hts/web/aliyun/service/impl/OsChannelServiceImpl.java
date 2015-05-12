package com.hts.web.aliyun.service.impl;

import org.springframework.stereotype.Service;

import com.hts.web.aliyun.service.OsChannelService;

@Service("HTSOsChannelService")
public class OsChannelServiceImpl implements OsChannelService {

	@Override
	public void saveChannelAtOnce(Integer id, Integer ownerId,
			String channelName, String channelTitle, String subtitle,
			String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, Integer danmu,
			Integer mood, Integer world) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateChannelAtOnce(Integer id, Integer ownerId,
			String channelName, String channelTitle, String subtitle,
			String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, Integer danmu,
			Integer mood, Integer world) {
		// TODO Auto-generated method stub

	}

}
