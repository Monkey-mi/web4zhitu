package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * <p>
 * 频道PV缓存数据访问对象
 * </p>
 * 
 * 创建时间: 2015
 * @author lynch
 *
 */
public interface ChannelPVCacheDao extends BaseCacheDao {

	/**
	 * 增加频道pv
	 * 
	 * @param channelId
	 */
	public void inrc(Integer channelId);
	
}
