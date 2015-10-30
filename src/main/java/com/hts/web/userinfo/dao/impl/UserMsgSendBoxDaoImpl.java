package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserMsgBox;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.userinfo.dao.UserMsgSendBoxDao;

@Repository("HTSUserMsgSendBoxDao")
public class UserMsgSendBoxDaoImpl extends BaseDaoImpl implements
		UserMsgSendBoxDao {

	public static String table = HTS.USER_MSG_SEND_BOX;
	
	private static final String MSG_INFO = 
			"m0.id,m0.content,b0.msg_date";
	
	public static final String SAVE_SEND_MSG = "insert into " + table 
			+ " (sender_id,recipient_id,content_id) values (?,?,?)";
	
	private static final String DELETE_SEND_MSG = "delete from " + table
			+ " where sender_id=? and recipient_id=? and content_id=?";
	
	private static final String QUERY_MSG = "select " + MSG_INFO + " from " 
			+ table + " b0," + HTS.USER_MSG + " m0"
			+ " where b0.content_id=m0.id"
			+ " and b0.sender_id=? and recipient_id=?"
			+ " order by b0.content_id desc limit ?,?";
	
	private static final String QUERY_MSG_MAX_ID = "select " + MSG_INFO + " from " 
			+ table + " b0," + HTS.USER_MSG + " m0"
			+ " where b0.content_id=m0.id"
			+ " and b0.sender_id=? and recipient_id=?"
			+ " and b0.content_id<=?"
			+ " order by b0.content_id desc limit ?,?";

	private static final String QUERY_CONTENT_ID = "select content_id from "
			+ table + " where sender_id=? and recipient_id=? and content_id=?";
	
	private static final String QUERY_SEND_COUNT = "select count(*) from " 
			+ table + " where sender_id=? and recipient_id=? and content_id<=?";
	
	/**
	 * 构建消息POJO
	 * 
	 * @param rs
	 * @param senderId
	 * @return
	 * @throws SQLException
	 */
	public UserMsgDto buildMstDto(ResultSet rs, 
			Integer senderId, Integer recipientId) throws SQLException {
		UserMsgDto dto = new UserMsgDto();
		dto.setId(rs.getInt("id"));
		dto.setSenderId(senderId);
		dto.setRecipientId(recipientId);
		dto.setMsgDate((Date)rs.getObject("msg_date"));
		dto.setContent(rs.getString("content"));
		return dto;
	}
	
	
	@Override
	public void saveSendMsg(UserMsgBox msgBox) {
		getMasterJdbcTemplate().update(SAVE_SEND_MSG, new Object[]{
				msgBox.getSenderId(),
				msgBox.getRecipientId(),
				msgBox.getContentId()
		});
	}

	@Override
	public void deleteSendMsg(Integer senderId, Integer recipientId, 
			Integer contentId) {
		getMasterJdbcTemplate().update(DELETE_SEND_MSG, 
				new Object[]{senderId, recipientId, contentId});
	}

	@Override
	public void querySendMsg(final Integer senderId,
			final Integer recipientId, RowSelection rowSelection, 
			final RowCallback<UserMsgDto> callback) {
		getJdbcTemplate().query(QUERY_MSG, 
				new Object[]{senderId, recipientId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowCallbackHandler() {
					
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						callback.callback(buildMstDto(rs, senderId, recipientId));
					}
		});
	}

	@Override
	public void querySendMsg(Integer maxId, final Integer senderId,
			final Integer recipientId, RowSelection rowSelection,
			final RowCallback<UserMsgDto> callback) {
		getJdbcTemplate().query(QUERY_MSG_MAX_ID, 
				new Object[]{senderId, recipientId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowCallbackHandler() {
					
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						callback.callback(buildMstDto(rs, senderId, recipientId));
					}
		});
	
	}


	@Override
	public Integer queryContentId(Integer senderId, Integer recipientId, Integer contentId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_CONTENT_ID, 
					senderId, recipientId, contentId);
		} catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}


	@Override
	public long querySendCount(Integer senderId, Integer recipientId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_SEND_COUNT, 
				senderId, recipientId, maxId);
	}
	
}
