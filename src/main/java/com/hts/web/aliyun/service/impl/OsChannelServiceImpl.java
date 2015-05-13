package com.hts.web.aliyun.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.dao.OsChannelDao;
import com.hts.web.aliyun.service.OsChannelService;
import com.hts.web.common.pojo.OpChannel;

@Service("HTSOsChannelService")
public class OsChannelServiceImpl implements OsChannelService {

	@Autowired
	private OsChannelDao channelDao;
	
	@Override
	public void saveChannelAtOnce(Integer id, Integer ownerId,
			String channelName, String channelTitle, String subtitle,
			String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, String labelIds,
			Integer danmu, Integer mood, Integer world) throws Exception {
		OpChannel chan = new OpChannel();
		chan.setId(id);
		chan.setChannelName(channelName);
		chan.setSubtitle(subtitle);
		chan.setChannelDesc(channelDesc);
		chan.setChannelIcon(channelIcon);
		chan.setChannelType(channelType);
		chan.setChannelLabel(channelLabel);
		chan.setLabelIds(labelIds);
		channelDao.saveChannel(chan);
	}

	@Override
	public void updateChannelAtOnce(Integer id, Integer ownerId,
			String channelName, String channelTitle, String subtitle,
			String channelDesc, String channelIcon, String subIcon,
			Integer channelType, String channelLabel, String labelIds,
			Integer danmu, Integer mood, Integer world) throws Exception{
		OpChannel chan = new OpChannel();
		chan.setId(id);
		chan.setChannelName(channelName);
		chan.setSubtitle(subtitle);
		chan.setChannelDesc(channelDesc);
		chan.setChannelIcon(channelIcon);
		chan.setChannelType(channelType);
		chan.setChannelLabel(channelLabel);
		chan.setLabelIds(labelIds);
		channelDao.updateChannel(chan);
	}

	@Override
	public void deleteChannelAtOnce(Integer id) throws Exception {
		channelDao.deleteChannelById(id);
	}
	
	public void deleteChannelAtOnce(Integer[] ids) throws Exception {
		channelDao.deletChannelByIds(ids);
		
	}

	@Override
	public void saveChannelAtOnce(List<OpChannel> channels) throws Exception {
		channelDao.saveChannel(channels);
	}

	@Override
	public void updateChannelAtOnce(List<OpChannel> channels) throws Exception {
		channelDao.updateChannel(channels);
	}


}
