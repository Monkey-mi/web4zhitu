package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.MsgComment;
import com.hts.web.common.pojo.MsgCommentDto;
import com.hts.web.common.pojo.MsgCommentUserDto;
import com.hts.web.common.pojo.MsgCommentWorldDto;
import com.hts.web.userinfo.dao.MsgCommentDao;

@Repository("HTSMsgCommentDao")
public class MsgCommentDaoImpl extends BaseDaoImpl implements MsgCommentDao {
	
	private static String table = HTS.USER_MSG_COMMENT;
	
	private static final String MSG_INFO = "m0.comment_id, m0.author_id, m0.world_id";
	
	private static final String MSG_COMMENT_INFO = "c0.comment_date,c0.content";
	
	private static final String MSG_USER_INFO = "u0.user_name,u0.user_avatar,u0.user_avatar_l";
	
	private static final String MSG_WORLD_INFO = "h0.title_thumb_path,h0.title_path,h0.valid";

	private static final String QUERY_COMMENT_MSG = "select " 
			+ MSG_INFO + "," + MSG_COMMENT_INFO + "," + MSG_USER_INFO + "," + MSG_WORLD_INFO
			+ " from " + table + " m0," + HTS.HTWORLD_COMMENT + " c0," 
			+ HTS.USER_INFO + " u0," + HTS.HTWORLD_HTWORLD + " h0"
			+ " where m0.comment_id=c0.id and m0.author_id=u0.id and m0.world_id=h0.id and m0.world_author_id=?"
			+ " order by m0.comment_id desc limit ?,?";
	
	private static final String QUERY_COMMENT_MSG_BY_MAX_ID = "select " 
			+ MSG_INFO + "," + MSG_COMMENT_INFO + "," + MSG_USER_INFO + "," + MSG_WORLD_INFO
			+ " from " + table + " m0," + HTS.HTWORLD_COMMENT + " c0," 
			+ HTS.USER_INFO + " u0," + HTS.HTWORLD_HTWORLD + " h0"
			+ " where m0.comment_id=c0.id and m0.author_id=u0.id and m0.world_id=h0.id and m0.world_author_id=? and m0.comment_id<=?"
			+ " order by m0.comment_id desc limit ?,?";
	
	private static final String SAVE_MSG = "insert into " + table 
			+ " (comment_id,author_id,world_author_id,world_id) values (?,?,?,?)";
	
	private static final String DELETE_MSG = "delete from " + table
			+ " where id=?";
	
	private static final String UPDATE_CK = "update " + table 
			+ " set ck=1 where world_author_id=? and ck=0";
	
	private static final String QUERY_UNCK_COUNT = "select count(1) from " + table
			+ " where world_author_id=? and ck=0";

	/**
	 * 构建评论消息DTO
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-28
	 */
	public MsgCommentDto buildMsgDto(ResultSet rs) throws SQLException {
		MsgCommentDto msg = new MsgCommentDto();
		msg.setId(rs.getInt("comment_id"));
		msg.setWorldId(rs.getInt("world_id"));
		msg.setContent(rs.getString("content"));
		msg.setCommentDate((Date)rs.getObject("comment_date"));
		
		MsgCommentUserDto user = new MsgCommentUserDto();
		user.setId(rs.getInt("author_id"));
		user.setUserName(rs.getString("user_name"));
		user.setUserAvatar(rs.getString("user_avatar"));
		user.setUserAvatarL(rs.getString("user_avatar_l"));
		
		MsgCommentWorldDto world = new MsgCommentWorldDto();
		world.setId(rs.getInt("world_id"));
		world.setTitleThumbPath(rs.getString("title_thumb_path"));
		world.setTitlePath(rs.getString("title_path"));
		world.setValid(rs.getInt("valid"));
		
		msg.setUserInfo(user);
		msg.setHtworld(world);
		
		return msg;
	}

	@Override
	public List<MsgCommentDto> queryMsg(Integer worldAuthorId, 
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_COMMENT_MSG,
				new Object[]{worldAuthorId, rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<MsgCommentDto>() {

			@Override
			public MsgCommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				return buildMsgDto(rs);
			}
			
		});
	}

	@Override
	public List<MsgCommentDto> queryMsg(Integer maxId, Integer worldAuthorId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_COMMENT_MSG_BY_MAX_ID,
				new Object[]{worldAuthorId,maxId,rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<MsgCommentDto>() {

			@Override
			public MsgCommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				return buildMsgDto(rs);
			}
		});
	}

	@Override
	public void saveMsgComment(MsgComment msg) {
		getMasterJdbcTemplate().update(SAVE_MSG, new Object[]{
			msg.getCommentId(),
			msg.getAuthorId(),
			msg.getWorldAuthorId(),
			msg.getWorldId()
		});
	}

	@Override
	public void deleteByCommentId(Integer commentId) {
		getMasterJdbcTemplate().update(DELETE_MSG, commentId);
	}

	@Override
	public void updateCK(Integer worldAuthorId) {
		getMasterJdbcTemplate().update(UPDATE_CK, worldAuthorId);
	}

	@Override
	public long queryUnCkCount(Integer worldAuthorId) {
		return getJdbcTemplate().queryForLong(QUERY_UNCK_COUNT, worldAuthorId);
	}
	

}
