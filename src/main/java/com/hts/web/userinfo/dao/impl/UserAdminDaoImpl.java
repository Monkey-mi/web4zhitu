package com.hts.web.userinfo.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.userinfo.dao.UserAdminDao;

@Repository("HTSUserAdminDao")
public class UserAdminDaoImpl extends BaseDaoImpl implements UserAdminDao {

	private static String table = HTS.USER_ADMIN;
	
	private static final String QUERY_UID = "select user_id from " + table
			+ " where user_id=?";
	
	@Override
	public Boolean queryShieldCommentPermission(Integer userId) {
		try {
			Integer uid = getJdbcTemplate().queryForInt(QUERY_UID, userId);
			if(uid != null && uid != 0) {
				return true;
			}
		} catch(EmptyResultDataAccessException e) {
		}
		return false;
	}

}
