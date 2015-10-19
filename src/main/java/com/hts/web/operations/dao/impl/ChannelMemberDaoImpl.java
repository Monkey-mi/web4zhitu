package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpChannelMember;
import com.hts.web.common.pojo.OpChannelMemberThumb;
import com.hts.web.common.pojo.OpChannelSub;
import com.hts.web.operations.dao.ChannelMemberDao;

@Repository("HTSChannelMemberDao")
public class ChannelMemberDaoImpl extends BaseDaoImpl implements
		ChannelMemberDao {

	private static final String MEMBER_THUMB = "m0.id,m0.channel_id,m0.user_id,"
			+ "u0.user_name,u0.user_avatar,u0.user_avatar_l,u0.platform_verify,u0.star";
	
	private static String table = HTS.OPERATIONS_CHANNEL_MEMBER;
	
	private static final String SAVE_MEMBER = "insert into " + table
			+ " (channel_id,user_id,sub_time,degree) values (?,?,?,?)";

	private static final String DELETE_MEMBER = "delete from " + table
			+ " where channel_id=? and user_id=?";

	private static final String QUERY_MEMBER = "select " + MEMBER_THUMB 
			+ " from " + table + " m0," + HTS.USER_INFO + " u0"
			+ " where m0.user_id=u0.id and m0.channel_id=?"
			+ " order by m0.id desc limit ?,?";
	
	private static final String QUERY_MEMBER_BY_MAX_ID = "select " + MEMBER_THUMB 
			+ " from " + table + " m0," + HTS.USER_INFO + " u0"
			+ " where m0.user_id=u0.id and m0.channel_id=? and m0.id<=?"
			+ " order by m0.id desc limit ?,?";
	
	private static final String QUERY_ID = "select id from " + table
			+ " where channel_id=? and user_id=?";
	
	private static final String QUERY_CHANNEL_SUB = "select channel_id,user_id from " 
			+ table + " where user_id=? and channel_id in";

	private static final String QUERY_MEMBER_COUNT = "select count(*) from " 
			+ table + " where channel_id=?";
	
	private static final String QUERY_MEMBER_DEGREE = "select degree from " 
			+ table + " where channel_id=? and user_id=?";
	
	private static final String QUERY_IS_MEMBER = "select user_id from " 
			+ table + " where channel_id=? and user_id=?";
	
	public OpChannelMemberThumb buildThumb(ResultSet rs) throws SQLException {
		return new OpChannelMemberThumb(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("channel_id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("platform_verify"),
				rs.getInt("star"));
	}

	
	@Override
	public void saveMember(OpChannelMember member) {
		try {
			getMasterJdbcTemplate().update(SAVE_MEMBER, new Object[]{
					member.getChannelId(),
					member.getUserId(),
					member.getJoinTime().getTime(),
					member.getDegree()
			});
		} catch(DuplicateKeyException e) {
			// 避免重复订阅出错
		}
	}

	@Override
	public void deleteMember(Integer channelId, Integer userId) {
		getMasterJdbcTemplate().update(DELETE_MEMBER, new Object[]{channelId, userId});
	}

	@Override
	public List<OpChannelMemberThumb> queryMemberThumb(Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_MEMBER, 
				new Object[]{channelId, rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<OpChannelMemberThumb>() {

					@Override
					public OpChannelMemberThumb mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildThumb(rs);
					}
		});
	}

	@Override
	public List<OpChannelMemberThumb> queryMemberThumb(Integer maxId,
			Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_MEMBER_BY_MAX_ID, 
				new Object[]{channelId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<OpChannelMemberThumb>() {

					@Override
					public OpChannelMemberThumb mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildThumb(rs);
					}
		});
	}

	@Override
	public Integer queryId(Integer channelId, Integer userId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_ID, 
					new Object[]{channelId, userId});
		} catch(DataAccessException e) {
			return 0;
		}
	}


	@Override
	public void queryChannelSub(Integer userId, Integer[] channelIds,
			RowCallback<OpChannelSub> callback) {
		String inSelection = SQLUtil.buildInSelection(channelIds);
		String sql = QUERY_CHANNEL_SUB + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(channelIds,
				new Object[]{userId}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				
			}
		});
	}

	@Override
	public Long queryMemberCount(Integer channelId) {
		return getMasterJdbcTemplate().queryForLong(QUERY_MEMBER_COUNT, channelId);
	}

	@Override
	public Integer queryDegree(Integer channelId, Integer userId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_MEMBER_DEGREE, 
					new Object[]{channelId, userId});
		} catch(DataAccessException e) {
			return -1;
		}
	}


	@Override
	public boolean ismember(Integer channelId, Integer userId) {
		Integer uid;
		try {
			uid = getJdbcTemplate().queryForInt(QUERY_IS_MEMBER, 
					new Object[]{channelId, userId});
			if(uid != null && uid > 0) {
				return true;
			}
		} catch(DataAccessException e) {
			return false;
		}
		return false;
	}
	
}
