package com.hts.web.ztworld.dao;

import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldKeep;
import com.hts.web.common.pojo.OpUser;

/**
 * <p>
 * 织图世界收藏数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface HTWorldKeepDao extends BaseDao {

	/**
	 * 查询织图收藏
	 * 
	 * @param userId
	 * @param worldId
	 */
	public HTWorldKeep queryKeep(Integer userId, Integer worldId);
	
	
	/**
	 * 保存织图收藏
	 * 
	 * @param keep
	 */
	public Integer saveKeep(HTWorldKeep keep);
	
	/**
	 * 更新收藏
	 * 
	 * @param userId
	 * @param worldId
	 */
	public void updateKeep(Integer userId, Integer worldId, Integer valid, Date date);
	
	/**
	 * 查询收藏总数
	 * @param worldId
	 * @return
	 */
	public long queryKeepCount(Integer worldId);
	
	/**
	 * 更新推送标记
	 * 
	 * @param id
	 * @param valid
	 */
	public void updatePushed(Integer id, Integer valid);


	/**
	 * 查询收藏指定织图的运营用户数据
	 * 
	 * @param worldId
	 * @param rowSelection
	 * @return
	 */
//	public List<OpUser> queryOperationsKeepUserByWorldId(
//			Integer worldId, RowSelection rowSelection);

	/**
	 * 查询收藏指定织图的运营用户总数
	 * 
	 * @param worldId
	 */
	public long queryOperationsKeepUserCountByWorldId(Integer worldId);


	/**
	 * 根据最大id查询收藏指定织图的运营用户数据
	 * 
	 * @param worldId
	 */
//	public List<OpUser> queryOperationsKeepUserByWorldIdAndMaxId(
//			Integer maxId, Integer worldId, RowSelection rowSelection);


	/**
	 * 根据最大id查询收藏指定织图的运营用户总数
	 * 
	 * @param worldId
	 */
	public long queryOperationsKeepUserCountByWorldIdAndMaxId(Integer maxId, Integer worldId);

	/**
	 * 查询用户收藏织图总数
	 * 
	 * @param userId
	 * @return
	 */
	public long queryUserKeepCount(Integer userId);


	/**
	 * 查询收藏标记
	 * 
	 * @param userId
	 * @param worldIds
	 * @param rowCallback
	 */
	public void queryKeep(Integer userId, Integer[] worldIds, RowCallback<Integer> rowCallback);

}
