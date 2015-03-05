package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpActivity;

/**
 * <p>
 * 活动缓存数据访问接口器
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public interface ActivityCacheDao extends BaseCacheDao {

	/**
	 * 查询活动
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpActivity> queryActivity();
	
	/**
	 * 查询活动
	 * 
	 * @param limit
	 * @return
	 */
	public List<OpActivity> queryActivity(int limit);
	
	/**
	 * 查询活动
	 * 
	 * @param name
	 * @return
	 */
	public OpActivity queryActivity(String name);
	
	/**
	 * 添加活动哈希
	 * 
	 * @param activity
	 */
	public void addHashActivity(OpActivity activity);
	
	/**
	 * 查询最新活动
	 * 
	 * @return
	 */
	public OpActivity queryMaxActivity();
}
