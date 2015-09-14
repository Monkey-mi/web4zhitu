package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 自动通过的频道id缓存数据访问接口
 * 
 * @version 3.0.0
 * @author lynch
 *
 */
public interface ChannelAutoRejectIdCacheDao extends BaseCacheDao {

	/**
	 * 判断是否为自动通过的id
	 * 
	 * @param id
	 * @return
	 * 
	 * @author lynch 2015-09-14
	 */
	public boolean isAutoReject(Integer id);
}
