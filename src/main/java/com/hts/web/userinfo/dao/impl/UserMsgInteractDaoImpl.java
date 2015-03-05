package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserMsgLiked;
import com.hts.web.userinfo.dao.UserMsgInteractDao;

@Repository
public class UserMsgInteractDaoImpl extends BaseDaoImpl implements
		UserMsgInteractDao {

	/**
	 * 查询喜欢消息列表
	 */
	private static final String QUERY_LIKED_MSG = "select hl.*,h.title_thumb_path from ("
			+ "select id,user_id,world_id,world_author_id,liked_date from "
			+ HTS.HTWORLD_LIKED + " where user_id=? and world_author_id=? and valid=1 order by id desc limit ?,?) as hl, "
			+ HTS.HTWORLD_HTWORLD + " as h where hl.world_id=h.id";
	
	/**
	 * 根据最大id查询喜欢消息列表
	 */
	private static final String QUERY_LIKED_MSG_BY_MAX_ID = "select hl.*,h.title_thumb_path from ("
			+ "select id,user_id,world_id,world_author_id,liked_date from "
			+ HTS.HTWORLD_LIKED + " where user_id=? and world_author_id=? and valid=1 and id<=? order by id desc limit ?,?) as hl, "
			+ HTS.HTWORLD_HTWORLD + " as h where hl.world_id=h.id";
	
	@Override
	public void queryLikedMsg(Integer userId, Integer authorId, RowSelection rowSelection,
			final RowCallback<UserMsgLiked> callback) {
		getJdbcTemplate().query(QUERY_LIKED_MSG,
				new Object[]{userId, authorId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildMsgLiked(rs));
			}
		}); 
	}

	@Override
	public void queryLikedMsg(Integer maxId, Integer userId, Integer authorId,
			RowSelection rowSelection, final RowCallback<UserMsgLiked> callback) {
		getJdbcTemplate().query(QUERY_LIKED_MSG_BY_MAX_ID, 
				new Object[]{userId, authorId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildMsgLiked(rs));
			}
		}); 
	}
	
	public UserMsgLiked buildMsgLiked(ResultSet rs) throws SQLException {
		return new UserMsgLiked(
				rs.getInt("id"), 
				rs.getInt("user_id"),
				rs.getInt("world_id"), 
				rs.getInt("world_author_id"),
				rs.getString("title_thumb_path"),
				(Date)rs.getObject("liked_date"));
	}

}
