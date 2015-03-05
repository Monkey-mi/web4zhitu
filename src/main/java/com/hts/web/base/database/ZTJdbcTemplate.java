package com.hts.web.base.database;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p>
 * 织图JDBC操作模板类
 * </p>
 * 
 * 创建时间：2013-8-5
 * @author ztj
 *
 */
public class ZTJdbcTemplate extends JdbcTemplate {

	public ZTJdbcTemplate() {
		super();
	}

	public ZTJdbcTemplate(DataSource dataSource, boolean lazyInit) {
		super(dataSource, lazyInit);
	}

	public ZTJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		try {
			return super.queryForMap(sql);
		} catch (EmptyResultDataAccessException e) {
			return new HashMap<String, Object>();
		}
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object[] args,
			int[] argTypes) throws DataAccessException {
		try {
			return super.queryForMap(sql, args, argTypes);
		} catch (EmptyResultDataAccessException e) {
			return new HashMap<String, Object>();
		}
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object... args)
			throws DataAccessException {
		try {
			return super.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return new HashMap<String, Object>();
		}
	}

	
}
