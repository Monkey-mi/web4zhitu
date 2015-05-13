package com.hts.web.aliyun.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.OsChannelDao;
import com.hts.web.base.database.OpenSearch;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.util.StringUtil;

@Repository("HTSOsChannelDao")
public class OsChannelDaoImpl extends BaseOsDaoImpl implements OsChannelDao {

	private String table = "main";	

	@Override
	public void saveChannel(OpChannel channel) throws Exception {
		JSONObject opt = getAddChannelOpt(channel);
		pushUpdate(opt.toString(), OpenSearch.CHANNEL, table);
	}
	
	@Override
	public void saveChannel(List<OpChannel> channels) throws Exception {
		JSONArray optArray = new JSONArray();
		for(OpChannel channel : channels) {
			JSONObject opt = getAddChannelOpt(channel);
			optArray.add(opt);
		}
		pushUpdate(optArray.toString(), OpenSearch.CHANNEL, table);
	}
	
	/**
	 * 获取频道添加操作JSON
	 * 
	 * @param channel
	 * @return
	 */
	private JSONObject getAddChannelOpt(OpChannel channel) {
		if(channel.getId() == null || channel.getId() == 0) {
			throw new NullPointerException("id can not be null");
		}
		
		if(channel.getLastModified() == null) {
			channel.setLastModified(new Date());
		}
		
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", channel.getId());
		fields.put("channel_name", channel.getChannelName());
		fields.put("subtitle", channel.getSubtitle());
		fields.put("channel_desc", channel.getChannelDesc());
		fields.put("channel_icon", channel.getChannelIcon());
		fields.put("channel_type", channel.getChannelType());
		fields.put("world_count", channel.getWorldCount());
		fields.put("child_count", channel.getChildCount());
		fields.put("member_count", channel.getMemberCount());
		fields.put("superb_count", channel.getSuperbCount());
		fields.put("last_modified", channel.getLastModified().getTime());
		fields.put("labelIds", channel.getLabelIds().split(","));
		fields.put("channel_label", channel.getChannelLabel().split(","));
		JSONObject opt = getAddOpt(fields);
		return opt;
	}

	@Override
	public void updateChannel(OpChannel channel) throws Exception {
		JSONObject opt = getUpdateChannelOpt(channel);
		pushUpdate(opt.toString(), OpenSearch.CHANNEL, table);
	}
	
	@Override
	public void updateChannel(List<OpChannel> channels) throws Exception {
		JSONArray optArray = new JSONArray();
		for(OpChannel channel : channels) {
			JSONObject opt = getUpdateChannelOpt(channel);
			optArray.add(opt);
		}
		pushUpdate(optArray.toString(), OpenSearch.CHANNEL, table);
	}
	
	/**
	 * 获取更新频道操作JSON
	 * @param channel
	 * @return
	 */
	private JSONObject getUpdateChannelOpt(OpChannel channel) {
		if(channel.getId() == null || channel.getId() == 0) {
			throw new NullPointerException("id can not be null");
		}
		
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", channel.getId());
		if(!StringUtil.checkIsNULL(channel.getChannelName()))
			fields.put("channel_name", channel.getChannelName());
		
		if(!StringUtil.checkIsNULL(channel.getSubtitle()))
			fields.put("subtitle", channel.getSubtitle());
		
		if(!StringUtil.checkIsNULL(channel.getChannelDesc()))
			fields.put("channel_desc", channel.getChannelDesc());
		
		if(!StringUtil.checkIsNULL(channel.getChannelIcon()))
			fields.put("channel_icon", channel.getChannelIcon());
		
		if(channel.getChannelType() != null) 
			fields.put("channel_type", channel.getChannelType());
		
		if(channel.getWorldCount() != null)
			fields.put("world_count", channel.getWorldCount());
		
		if(channel.getChildCount() != null)
			fields.put("child_count", channel.getChildCount());
		
		if(channel.getMemberCount() != null) 
			fields.put("member_count", channel.getMemberCount());
		
		if(channel.getSuperbCount() != null) 
			fields.put("superb_count", channel.getSuperbCount());
		
		if(channel.getLastModified() != null)
			fields.put("last_modified", channel.getLastModified().getTime());
			
		if(channel.getSuperb() != null) 
			fields.put("superb", channel.getSuperb());
		
		if(channel.getLabelIds() != null)
			fields.put("labelIds", channel.getLabelIds().split(","));
		
		if(channel.getChannelLabel() != null)
			fields.put("channel_label", channel.getChannelLabel().split(","));
		
		JSONObject opt = getUpdateOpt(fields);
		return opt;
	}

	@Override
	public void deleteChannelById(Integer id) throws Exception {
		if(id == null || id == 0) {
			throw new NullPointerException("id can not be null");
		}
		
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", id);
		
		JSONObject opt = getDeleteOpt(fields);
		pushUpdate(opt.toString(), OpenSearch.CHANNEL, table);
	}
	
	@Override
	public void deletChannelByIds(Integer[] ids) throws Exception {
		JSONArray optArray = new JSONArray();
		for(Integer id : ids) {
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("id", id);
			JSONObject opt = getDeleteOpt(fields);
			optArray.add(opt);
		}
		pushUpdate(optArray.toString(), OpenSearch.CHANNEL, table);
	}

}
