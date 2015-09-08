package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpAdAppLinkRecord;

/**
 * <p>
 * App链接点击记录数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-30
 * @author ztj
 *
 */
public interface AppLinkRecordDao extends BaseDao {

	/**
	 * 保存记录
	 * 
	 * @param record
	 */
	public void saveRecord(OpAdAppLinkRecord record);
	
}
