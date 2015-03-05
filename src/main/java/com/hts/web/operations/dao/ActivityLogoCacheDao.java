package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpActivityLogo;

/**
 * <p>
 * 活动LOGO数据数据访问接口
 * </p>
 * 
 * 创建时间：2014-3-22
 * @author tianjie
 *
 */
public interface ActivityLogoCacheDao extends BaseCacheDao {
	
	/**
	 * 查询缓存LOGO列表
	 * 
	 * @return
	 */
	public List<OpActivityLogo> queryCacheLogo(int limit);
}
