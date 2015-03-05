package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldLabel;

/**
 * <p>
 * 织图标签缓存数据访问接口
 * </p>
 * 
 * 创建时间：2014-5-5
 * @author tianjie
 *
 */
public interface HTWorldLabelCacheDao extends BaseCacheDao {

	/**
	 * 查询热门标签列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldLabel> queryHotLabel(RowSelection rowSelection);
	
	/**
	 * 查询互动标签列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldLabel> queryActivityLabel(RowSelection rowSelection);
}
