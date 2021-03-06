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
	private static final String QUERY_SHIELD_ID = "select DISTINCT shield_id from " 
	+ table + " where user_id=? and shield_id=?";
	
	/** 删除屏蔽 */
	private static final String DELETE_SHIELD = "delete from " + table 
			+ " where user_id=? and shield_id=?";
	
	@Override
	public void saveShield(Integer userId, Integer shieldId) {
		getMasterJdbcTemplate().update(SAVE_SHIELD, new Object[]{userId, shieldId});
	}

	@Override
	public Integer queryShieldId(Integer userId, Integer shieldId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_SHIELD_ID,
					new Object[]{userId, shieldId});
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
		
	}

	@Override
	public void deleteShield(Integer userId, Integer shieldId) {
		getMasterJdbcTemplate().update(DELETE_SHIELD, new Object[]{userId, shieldId});
	}
	
	
}
