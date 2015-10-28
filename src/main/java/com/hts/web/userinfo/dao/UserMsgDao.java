package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsg;

/**
 * <p>
 * 私信数据访问接口
 * </p>
 * 
 * @author ztj 2013-10-29 2015-10-28
 *
 */
public interface UserMsgDao extends BaseDao {

	/**
	 * 保存私信
	 * 
	 * @param msg
	 */
	public void saveMsg(UserMsg msg);
	
}
