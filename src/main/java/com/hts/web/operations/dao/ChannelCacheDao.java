package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannel;

/**
 * 频道redis缓存数据操作类
 * 
 * @author zhutianjie
 *
 */
public interface ChannelCacheDao extends BaseCacheDao {

	List<OpChannel> queryChannel();
	
	List<OpChannel> queryChannel(RowSelection rowSelection);
	
	List<OpChannel> queryOldChannel();
	
	/**
	 * 更新频道缓存,注意,superbList中不能含有topList的数据
	 * 
	 * @param topList 置顶的精选频道列表
	 * @param superbList 普通精选频道列表
	 */
	void updateChannel(List<OpChannel> topList, List<OpChannel> superbList);
	
	
	/**
	 * 查询随机缓存频道
	 * 
	 * @param limit
	 * @return
	 */
	List<OpChannel> queryRandomChannel(Integer limit);
	
	/**
	 * 获取推荐频道
	 * 
	 * @return
	 * @author zhangbo	2015年12月3日
	 */
	List<OpChannel> getRecommendChannel();
}
