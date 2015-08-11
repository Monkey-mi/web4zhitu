package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldType;

/**
 * <p>
 * 织图分类缓存数据访问接口
 * </p>
 * 
 * @author lynch
 *
 */
public interface TypeCacheDao extends BaseCacheDao {

	/**
	 * 查询分类
	 * 
	 * @return
	 */
	public List<HTWorldType> queryType();
	
}
