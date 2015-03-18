package com.hts.web.userinfo.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.userinfo.dao.LabelDao;

@Repository("HTSLabelDao")
public class LabelDaoImpl extends BaseDaoImpl implements LabelDao {

	private static String table = HTS.USER_LABEL;
	
	private static final String QUERY_ID_BY_NAME = "select id from " + table
			+ " where label_name=?";
	
	@Override
	public Integer queryIdByName(String labelName) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_ID_BY_NAME, labelName);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

}
