package com.hts.web.ztworld.dao;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldChildWorldTypeDto2;

/**
 * <p>
 * 子世界类型数据访问接口
 * </p>
 * 
 * 创建时间：2014-6-18
 * @author tianjie
 *
 */
public interface HTWorldChildWorldTypeDao extends BaseDao {

	/**
	 * 查询使用总数
	 * 
	 * @param ids
	 * @param callback
	 */
	public void queryUseCount(Integer[] ids, RowCallback<HTWorldChildWorldTypeDto2> callback);
	
	/**
	 * 添加使用次数
	 * @param id
	 */
	public void addUseCount(Integer id);
	
}
