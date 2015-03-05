package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldChildWorldType;

/**
 * <p>
 * 子世界类型缓存数据访问接口
 * </p>
 * 
 * 创建时间：2014-6-13
 * @author tianjie
 *
 */
public interface HTWorldChildWorldTypeCacheDao extends BaseCacheDao {

	/**
	 * 查询最新类型列表
	 * 
	 * @return
	 */
	public List<HTWorldChildWorldType> queryLatestType();
}
