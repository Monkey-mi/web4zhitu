package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorld;

/**
 * <p>
 * 织图缓存数据访问接口
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public interface HTWorldCacheDao extends BaseCacheDao {

	/**
	 * 保存最新织图缓存
	 * 
	 * @param world
	 */
	public void saveLatestCache(HTWorld world);
	
	/**
	 * 查询最新织图缓存列表
	 * 
	 * @param limit
	 * @return
	 */
	public List<HTWorld> queryCacheLatestList(int limit);
}
