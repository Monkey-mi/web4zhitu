package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserMsgUnreadCount;
import com.hts.web.userinfo.dao.MsgUnreadDao;

@Repository("HTSMsgUnReadDao")
public class MsgUnreadDaoImpl extends BaseDaoImpl implements MsgUnreadDao {
	
	private static String table = HTS.USER_MSG_UNREAD;
	
	private static final String UNREAD_COUNT_INFO = "concern_count,like_count,"
			+ "comment_count,at_count,sysmsg_count,umsg_count";
	
	private static final String SAVE_UNREAD = "insert into " + table
			+ "(user_id) values (?)";

	private static final String QUERY_COUNT = "select %s_count from " + table
			+ " where user_id=?";
	
	private static final String UPDATE_COUNT = "update " + table
			+ " set %s_count=? where user_id=?";
	
	private static final String CLEAR_COUNT = "update " + table
			+ " set %s_count=0 where user_id=?";
	
	private static final String QUERY_COUNT_INFO = "select " + UNREAD_COUNT_INFO
			+ " from " + table + " where user_id=?";
	
	private static final String QUERY_READ_ID = "select %s_id from " + table
			+ " where user_id=?";
	
	private static final String UPDATE_READ_ID = "update " + table 
			+ " set %s_id=? where user_id=?";
	
	
	@Override
	public void saveUnRead(Integer userId) {
		getMasterJdbcTemplate().update(SAVE_UNREAD, userId);
	}

	@Override
	public Integer queryCount(Integer userId, UnreadType unreadType) {
		try {
			return getJdbcTemplate().queryForInt(
					String.format(QUERY_COUNT, unreadType), userId);
		} catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}

	@Override
	public void updateCount(Integer userId, Integer count, UnreadType unreadType) {
		getMasterJdbcTemplate().update(
				String.format(UPDATE_COUNT, unreadType), count, userId);
	}

	@Override
	public void clearCount(Integer userId, UnreadType unreadType) {
		getMasterJdbcTemplate().update(
				String.format(CLEAR_COUNT, unreadType), userId);
	}

	@Override
	public UserMsgUnreadCount queryCount(final Integer userId) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_COUNT_INFO,
					new RowMapper<UserMsgUnreadCount>() {
	
						@Override
						public UserMsgUnreadCount mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new UserMsgUnreadCount(
									userId,
									rs.getInt("concern_count"),
									rs.getInt("like_count"),
									rs.getInt("comment_count"),
									rs.getInt("at_count"),
									rs.getInt("sysmsg_count"),
									rs.getInt("umsg_count"));
						}
			}, userId);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateReadId(Integer userId, Integer id, UnreadType unreadType) {
		getMasterJdbcTemplate().update(
				String.format(UPDATE_READ_ID, unreadType), id, userId);
	}

	@Override
	public Integer queryReadId(Integer userId, UnreadType unreadType) {
		try {
			return getJdbcTemplate().queryForInt(
					String.format(QUERY_READ_ID, unreadType), userId);
		} catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}

}
