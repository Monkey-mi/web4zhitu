package com.hts.web.ztworld.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldInteract;

/**
 * <p>
 * 织图互动数据访问接口
 * </p>
 * 
 * 创建时间：2013-12-3
 * @author ztj
 *
 */
public interface HTWorldInteractDao extends BaseDao {

	/**
	 * 查询互动列表
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteract> queryInteract(Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询互动列表
	 * 
	 * @param maxDate
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteract> queryInteract(Date maxDate, Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询互动总数
	 * 
	 * @param maxDate
	 * @param userId
	 * @return
	 */
	public long queryInteractCount(Date maxDate, Integer userId);
	
	/**
	 * 更新未读互动状态
	 * 
	 * @param maxDate
	 * @param userId
	 * @return
	 */
	public void updateUnReadInteract(Date maxDate, Integer userId);
}
