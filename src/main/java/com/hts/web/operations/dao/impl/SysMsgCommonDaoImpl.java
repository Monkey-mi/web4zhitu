package com.hts.web.operations.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.operations.dao.SysMsgCommonDao;

@Repository("HTSSysMsgCommonDao")
public class SysMsgCommonDaoImpl extends BaseDaoImpl implements SysMsgCommonDao {
	
	private static String table = HTS.OPERATIONS_SYS_MSG_COMMON;
	
	private static final String SELECT_ID = "select id from " + table
			+ " where id=?";

	@Override
	public Integer queryMsgId(Integer id) {
		try {
			return getJdbcTemplate().queryForInt(SELECT_ID, id);
		} catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}

}
