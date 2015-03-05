package com.hts.web.operations.dao;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpActivitySponsor;

/**
 * <p>
 * 活动发起人数据访问接口
 * </p>
 * 
 * 创建时间：2014-3-20
 * @author tianjie
 *
 */
public interface ActivitySponsorDao extends BaseDao {

	/**
	 * 查询活动发起人
	 * 
	 * @param activityId
	 * @param callback
	 */
	public void querySponsor(Integer activityId, RowCallback<OpActivitySponsor> callback);
	
	
	/**
	 * 查询活动发起人
	 * 
	 * @param activityIds
	 * @param callback
	 */
	public void querySponsor(Integer[] activityIds, RowCallback<OpActivitySponsor> callback);
}
