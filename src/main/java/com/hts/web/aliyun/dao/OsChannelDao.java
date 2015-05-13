package com.hts.web.aliyun.dao;

import java.util.List;

import com.hts.web.common.pojo.OpChannel;

public interface OsChannelDao extends BaseOsDao {

	/**
	 * 保存频道
	 * 
	 * @param channel
	 */
	public void saveChannel(OpChannel channel) throws Exception;
	
	/**
	 * 更新频道
	 * 
	 * @param channel
	 */
	public void updateChannel(OpChannel channel) throws Exception;
	
	/**
	 * 根据id删除频道
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteChannelById(Integer id) throws Exception;
	
	/**
	 * 根据ids删除频道
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void deletChannelByIds(Integer[] ids) throws Exception;
	
	/**
	 * 批量更新频道
	 * 
	 * @param channels
	 * @throws Exception
	 */
	public void updateChannel(List<OpChannel> channels) throws Exception;
	
	/**
	 * 批量添加频道
	 * 
	 * @param channels
	 * @throws Exception
	 */
	public void saveChannel(List<OpChannel> channels) throws Exception;
}
