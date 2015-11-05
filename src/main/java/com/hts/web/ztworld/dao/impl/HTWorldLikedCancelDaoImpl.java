package com.hts.web.ztworld.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.ztworld.dao.HTWorldLikedCancelDao;

@Repository("HTSWorlddLikedCancelDao")
public class HTWorldLikedCancelDaoImpl extends BaseDaoImpl implements HTWorldLikedCancelDao {

	private static String table = HTS.HTWORLD_LIKED_CANCEL;
	
	private static final String SAVE_CANCEL = "insert into " + table
			+ "(user_id,world_id) values (?,?)";
	
	private static final String IS_CANCEL = "select 1 from " + table
			+ " where user_id=? and world_id=?";
	
	private static final String DEL_CANCEL = "delete from " + table
			+ " where user_id=? and world_id=?";
	
	@Override
	public void saveCancel(Integer userId, Integer worldId) {
		getMasterJdbcTemplate().update(SAVE_CANCEL, userId, worldId);
	}

	@Override
	public boolean isCancel(Integer userId, Integer worldId) {
		try {
			getJdbcTemplate().queryForInt(IS_CANCEL, userId, worldId);
			return true;
		} catch(EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public void delCancel(Integer userId, Integer worldId) {
		getMasterJdbcTemplate().update(DEL_CANCEL, userId, worldId);
	}

	
	
}
