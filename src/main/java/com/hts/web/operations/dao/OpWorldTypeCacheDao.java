package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpWorldType;

/**
 * <p>
 * 广场推送标签数据访问接口
 * </p>
 * 
 * @author ztj
 *
 */
public interface OpWorldTypeCacheDao extends BaseCacheDao {
	
	/**
	 * 查询所有缓存标签信息
	 * 
	 * @return
	 */
	public List<OpWorldType> queryCacheLabel(int limit);

	/**
	 * 根据索引查询缓存标签
	 * 
	 * @param index
	 * @return
	 */
	public OpWorldType queryCacheLabelByIndex(int index);
	
}
