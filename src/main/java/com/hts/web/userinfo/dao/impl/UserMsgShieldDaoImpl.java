package com.hts.web.userinfo.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.userinfo.dao.UserMsgShieldDao;

@Repository("HTSUserMsgShieldDao")
public class UserMsgShieldDaoImpl extends BaseDaoImpl implements
		UserMsgShieldDao {

	private static String table = HTS.USER_MSG_SHIELD;
	
	/** 保存屏蔽信息*/
	private static final String SAVE_SHIELD = "insert into " + table
			+ " (user_id,shield_id) values (?,?)";
	
	/** 查询屏蔽id */
	private static final String QUERY_SHIELD_ID = "select DISTINCT shield_id from " + table + " where user_id=?";
	
	/** 删除屏蔽 */
	private static final String DELETE_SHIELD = "delete from " + table + " where user_id=? and shield_id=?";
	

	@Override
	public void saveShield(Integer userId, Integer shieldId) {
		getJdbcTemplate().update(SAVE_SHIELD, new Object[]{userId, shieldId});
	}

	@Override
	public Integer queryShieldId(Integer userId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_SHIELD_ID, userId);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
		
	}

	@Override
	public void deleteShield(Integer userId, Integer shieldId) {
		getJdbcTemplate().update(DELETE_SHIELD, new Object[]{userId, shieldId});
	}
	
	
}
