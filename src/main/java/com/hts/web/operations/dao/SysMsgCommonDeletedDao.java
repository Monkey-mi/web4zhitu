package com.hts.web.operations.dao;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 用户删除系统消息记录数据访问接口
 * </p>
 * 
 * @author lynch 2015-10-24
 *
 */
public interface SysMsgCommonDeletedDao extends BaseDao {

	/**
	 * 保存已删除记录
	 * 
	 * @param recipientId
	 * @param msgId
	 */
	public void saveDeleted(Integer recipientId, Integer msgId);

	/**
	 * 查询已删除记录
	 * 
	 * @param maxId
	 * @param recipientId
	 * @param callback
	 * @return
	 */
	public void queryMsgId(Integer maxId, Integer minId, Integer recipientId,
			RowCallback<Integer> callback);
	
}
