package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.operations.dao.ChannelCountBaseDao;

@Repository("HTSChannelCountBaseDao")
public class ChannelCountBaseDaoImpl extends BaseDaoImpl implements
		ChannelCountBaseDao {

	private static String table = HTS.OPERATIONS_CHANNEL_COUNT_BASE;

	private static final String QUERY_WORLD_COUNT = "select world_count from " 
			+ table + " where channel_id=?";
	
	private static final String QUERY_MEMBER_COUNT = "select member_count from " 
			+ table + " where channel_id=?";
	
	private static final String QUERY_SUPERB_COUNT = "select superb_count from " 
			+ table + " where channel_id=?";

	private static final String QUERY_WORLD_AND_CHILD_COUNT = 
			"select world_Count, child_count from " + table
			+ " where channel_id=?";
	
	@Override
	public Integer queryWorldCount(Integer id) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_WORLD_COUNT, id);
		} catch(DataAccessException e) {
			return 0;
		}
	}

	@Override
	public Integer queryMemberCount(Integer id) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_MEMBER_COUNT, id);
		} catch(DataAccessException e) {
			return 0;
		}
	}

	@Override
	public Integer querySuperbCount(Integer id) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_SUPERB_COUNT, id);
		} catch(DataAccessException e) {
			return 0;
		}
	}

	@Override
	public Integer[] queryWorldAndChildCount(Integer id) {
		try {
		return getJdbcTemplate().queryForObject(QUERY_WORLD_AND_CHILD_COUNT, 
				new Object[]{id}, new RowMapper<Integer[]>() {

					@Override
					public Integer[] mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new Integer[]{
								rs.getInt("world_count"), 
								rs.getInt("child_count")};
					}
			
		});
		} catch(DataAccessException e) {
			return new Integer[]{0,0};
		}
	}

}
