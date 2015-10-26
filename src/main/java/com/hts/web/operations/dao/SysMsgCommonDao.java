package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 公用系统消息数据访问接口
 * </p>
 * 
 * @author lynch 2015-10-24
 *
 */
public interface SysMsgCommonDao extends BaseDao {

	/**
	 * 查询系统消息id
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryMsgId(Integer id);
	
}
