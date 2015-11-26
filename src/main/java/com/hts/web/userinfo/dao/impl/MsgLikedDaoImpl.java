package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserMsgLiked;
import com.hts.web.userinfo.dao.MsgLikedDao;

@Repository("HTSMsgLikedDao")
public class MsgLikedDaoImpl extends BaseDaoImpl implements MsgLikedDao {

	private static String table = HTS.USER_MSG_LIKED;

	private static final String MSG_INFO = "m0.like_id,m0.liked_date,m0.user_id,m0.world_id,"
			+ "u0.user_name,u0.user_avatar,u0.user_avatar_l,u0.province,u0.city,u0.star,"
			+ "u0.platform_verify,w0.title_thumb_path";
	
	private static final String SAVE_MSG = "insert into " + table
			+ " (like_id,user_id,world_id,receive_id) values (?,?,?,?)";

	private static final String QUERY_MSG = "select " + MSG_INFO + " from " + table
			+ " m0, " + HTS.USER_INFO + " u0," + HTS.HTWORLD_HTWORLD + " w0"
			+ " where m0.user_id=u0.id and m0.world_id=w0.id and m0.receive_id=?"
			+ " order by m0.like_id desc limit ?";
	
	private static final String QUERY_MSG_BY_MAXID = "select " + MSG_INFO + " from " + table
			+ " m0, " + HTS.USER_INFO + " u0," + HTS.HTWORLD_HTWORLD + " w0"
			+ " where m0.user_id=u0.id and m0.world_id=w0.id and m0.receive_id=? and m0.like_id<=?"
			+ " order by m0.like_id desc limit ?";
	
	/**
	 * 构建喜欢消息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private UserMsgLiked buildLikedMsg(ResultSet rs) throws SQLException {
		return new UserMsgLiked(
				rs.getInt("like_id"),
				(Date)rs.getObject("liked_date"), 
				rs.getInt("user_id"), 
				rs.getString("user_name"), 
				rs.getString("user_avatar"), 
				rs.getString("user_avatar_l"),
				rs.getString("province"), 
				rs.getString("city"), 
				rs.getInt("star"),
				rs.getInt("platform_verify"));
		
	}
	
	@Override
	public void saveMsg(Integer like_id, Integer userId,
			Integer worldId, Integer receiveId) {
		getMasterJdbcTemplate().update(SAVE_MSG, like_id, userId, worldId, receiveId);
	}
	
	@Override
	public List<UserMsgLiked> queryMsg(Integer receiveId, Integer limit) {
		return getJdbcTemplate().query(QUERY_MSG, 
				new Object[]{receiveId, limit}, 
				new RowMapper<UserMsgLiked>() {

					@Override
					public UserMsgLiked mapRow(ResultSet rs, int rowNum) throws SQLException {
						return buildLikedMsg(rs);
					}
		});
	}

	@Override
	public List<UserMsgLiked> queryMsg(Integer maxId, Integer receiveId, Integer limit) {
		return getJdbcTemplate().query(QUERY_MSG_BY_MAXID, 
				new Object[]{receiveId, maxId, limit}, 
				new RowMapper<UserMsgLiked>() {

					@Override
					public UserMsgLiked mapRow(ResultSet rs, int rowNum) throws SQLException {
						return buildLikedMsg(rs);
					}
		});
	}

}
