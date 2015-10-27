package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * <p>
 * 公用系统消息最大id缓存数据访问接口
 * </p>

 * @author lynch 2015-10-24
 *
 */
public interface SysMsgCommonMaxIdCacheDao extends BaseCacheDao {

	public Integer queryMaxId();
	
	public void updateMaxId(Integer id);
	
	
}
