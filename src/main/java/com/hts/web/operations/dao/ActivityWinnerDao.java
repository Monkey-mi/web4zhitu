package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.OpActivityWinnerDto;

/**
 * <p>
 * 活动获奖织图数据访问对象
 * </p>
 * 
 * 创建时间：2014-5-18
 * @author tianjie
 *
 */
public interface ActivityWinnerDao extends BaseDao {
	
	/**
	 * 查询活动织图列表
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryActivityWorld(Integer joinId, Integer labelId,
			RowSelection rowSelection);
	
	/**
	 * 查询活动织图列表
	 * 
	 * @param joinId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryActivityWorld(int maxId, Integer joinId, Integer labelId,
			RowSelection rowSelection);
	
	/**
	 * 查询活动织图总数
	 * 
	 * @param maxId
	 * @param labelId
	 * @return
	 */
	public long queryActivityWorldCount(int maxId, Integer labelId);
	
	/**
	 * 查询获奖织图，根据获奖序号排序
	 * 
	 * @param joinId
	 * @param activityId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryActivityWorldV2(Integer joinId,
			Integer activityId, RowSelection rowSelection);

	/**
	 * 查询获奖织图，根据获奖序号排序
	 * 
	 * @param maxSerial
	 * @param joinId
	 * @param activityId
	 * @param rowSelection
	 * @return
	 */
	public List<HTWorldInteractDto> queryActivityWorldV2(int maxSerial,
			Integer joinId, Integer activityId, RowSelection rowSelection);

	/**
	 * 查询获奖织图总数
	 * 
	 * @param maxSerial
	 * @param activityId
	 * @return
	 */
	public long queryActivityWorldCountV2(int maxSerial, Integer activityId);
	
	/**
	 * 查询最新获胜者id
	 * 
	 * @param activityIds
	 * @param callback
	 */
	public void queryMaxWinnerId(Integer[] activityIds, RowCallback<OpActivityWinnerDto> callback);
	
	/**
	 * 查询最新获胜者id
	 * 
	 * @param activityId
	 * @return
	 */
	public Integer queryMaxWinnerId(Integer activityId);

}
