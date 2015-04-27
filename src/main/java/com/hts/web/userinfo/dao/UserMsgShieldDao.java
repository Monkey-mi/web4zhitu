package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 私信屏蔽列表数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-28
 * @author ztj
 *
 */
public interface UserMsgShieldDao extends BaseDao {

	/**
	 * 保存屏蔽
	 * @param userId
	 * @param shieldId
	 */
	public void saveShield(Integer userId, Integer shieldId);

	/**
	 * 查询屏蔽用户信息
	 * 
	 * @param userId
	 */
	public Integer queryShieldId(Integer userId, Integer shieldId);
	
	/**
	 * 删除屏蔽
	 * 
	 * @param userId
	 * @param shieldId
	 */
	public void deleteShield(Integer userId, Integer shieldId);
}
