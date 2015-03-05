package com.hts.web.operations.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpActivityAward;

/**
 * <p>
 * 活动奖品数据访问接口
 * </p>
 * 
 * 创建时间：2014-5-18
 * @author tianjie
 *
 */
public interface ActivityAwardDao extends BaseDao {

	/**
	 * 查询奖品列表
	 * 
	 * @param activityId
	 * @return
	 */
	public List<OpActivityAward> queryAward(Integer activityId);

	/**
	 * 查询奖品列表
	 * 
	 * @param activityId
	 * @param callback
	 */
	public void queryAward(Integer activityId, RowCallback<OpActivityAward> callback);
	
	/**
	 * 构建奖品POJO
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpActivityAward buildAward(ResultSet rs) throws SQLException;
	
	/**
	 * 查询奖品剩余总数
	 * 
	 * @param activityId
	 * @return
	 */
	public Integer querySumRemain(Integer activityId);
	
}
