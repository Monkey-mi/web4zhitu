package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpWorldTypeDto;

/**
 * <p>
 * 织图分类数据缓存访问接口
 * </p>
 * 
 * 创建时间：2014-5-4
 * @author tianjie
 *
 */
public interface OpWorldTypeDto2CacheDao extends BaseCacheDao {

	/**
	 * 查询精品分类织图
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpWorldTypeDto> querySuperbWorldType(RowSelection rowSelection);
	
	/**
	 * 查询精品织图
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<OpWorldTypeDto> querySuperbWorldType(int start, int end);
	
	/**
	 * 根据位置查询精品分类织图
	 * 
	 * @param index
	 * @return
	 */
	public OpWorldTypeDto querySuperWorldType(long index);
	
	/**
	 * 获取最后一个精品
	 * 
	 * @return
	 */
	public OpWorldTypeDto queryLastSuperWorldType();
}
