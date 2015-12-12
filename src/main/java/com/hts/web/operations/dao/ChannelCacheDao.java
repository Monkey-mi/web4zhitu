package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannel;

public interface ChannelCacheDao extends BaseCacheDao {

	public List<OpChannel> queryChannel();
	
	public List<OpChannel> queryChannel(int limit);
	
	public List<OpChannel> queryChannel(RowSelection rowSelection);
	
	public List<OpChannel> queryOldChannel();
	
	/**
	 * 更新频道缓存,注意,superbList中不能含有topList的数据
	 * 
	 * @param topList 置顶的精选频道列表
	 * @param superbList 普通精选频道列表
	 */
	public void updateChannel(List<OpChannel> topList, List<OpChannel> superbList);
	
	
	/**
	 * 查询随机缓存频道
	 * 
	 * @param limit
	 * @return
	 */
	public List<OpChannel> queryRandomChannel(Integer limit);
	
}
