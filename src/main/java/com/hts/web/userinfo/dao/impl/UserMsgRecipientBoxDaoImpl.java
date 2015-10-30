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
import com.hts.web.userinfo.dao.UserMsgRecipientBoxDao;

@Repository("HTSUserMsgRecipientBoxDao")
public class UserMsgRecipientBoxDaoImpl extends BaseDaoImpl implements
		UserMsgRecipientBoxDao {

	private static String table = HTS.USER_MSG_RECIPIENT_BOX;
	
	private static final String MSG_INFO = 
			"m0.id,m0.content,b0.msg_date";
	
	public static final String SAVE_RECEIVE_MSG = "insert into " + table 
			+ " (sender_id,recipient_id,content_id) values (?,?,?)";
	
	private static final String DELETE_RECEIVE_MSG = "delete from " + table
			+ " where recipient_id=? and sender_id=? and content_id=?";
	
	private static final String QUERY_MSG = "select " + MSG_INFO + " from " 
			+ table + " b0," + HTS.USER_MSG + " m0"
			+ " where b0.content_id=m0.id"
			+ " and recipient_id=? and b0.sender_id=?"
			+ " order by b0.content_id desc limit ?,?";
	
	private static final String QUERY_MSG_MAX_ID = "select " + MSG_INFO + " from " 
			+ table + " b0," + HTS.USER_MSG + " m0"
			+ " where b0.content_id=m0.id"
			+ " and recipient_id=? and b0.sender_id=?"
			+ " and b0.content_id<=?"
			+ " order by b0.content_id desc limit ?,?";

	private static final String QUERY_CONTENT_ID = "select content_id from " + table
			+ " where recipient_id=? and sender_id=? and content_id=?";
	
	
	private static final String QUERY_RECIPIENT_ID = "select recipient_id from " + table
			+ " where recipient_id=? and sender_id=? and content_id=?";
	
	private static final String QUERY_RECIPIENT_COUNT = "select count(*) from " + table
			+ " where recipient_id=? and sender_id=? and content_id<=?";
	
	/**
	 * 构建消息POJO
	 * 
	 * @param rs
	 * @param recipientId
	 * @return
	 * @throws SQLException
	 */
	public UserMsgDto buildMstDto(ResultSet rs, Integer recipientId, Integer senderId) throws SQLException {
		UserMsgDto dto = new UserMsgDto();
		dto.setId(rs.getInt("id"));
		dto.setSenderId(senderId);
		dto.setRecipientId(recipientId);
		dto.setMsgDate((Date)rs.getObject("msg_date"));
		dto.setContent(rs.getString("content"));
		return dto;
	}
	
	@Override
	public void saveRecipientMsg(UserMsgBox msgBox) {
		getMasterJdbcTemplate().update(SAVE_RECEIVE_MSG,
				new Object[]{
					msgBox.getSenderId(),
					msgBox.getRecipientId(),
					msgBox.getContentId()
				});
	}

	@Override
	public void deleteRecipientMsg(Integer recipientId, Integer senderId, 
			Integer contentId) {
		getMasterJdbcTemplate().update(DELETE_RECEIVE_MSG, 
				new Object[]{recipientId, senderId, contentId});
	}

	@Override
	public void queryRecipientMsg(final Integer recipientId, 
			final Integer senderId, RowSelection rowSelection,
			final RowCallback<UserMsgDto> callback) {
		getJdbcTemplate().query(QUERY_MSG, 
				new Object[]{recipientId, senderId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						callback.callback(buildMstDto(rs, recipientId, senderId));
					}
				}
		);
	}

	@Override
	public void queryRecipientMsg(Integer maxId, final Integer recipientId, 
			final Integer senderId, RowSelection rowSelection,
			final RowCallback<UserMsgDto> callback) {
		getJdbcTemplate().query(QUERY_MSG_MAX_ID, 
				new Object[]{recipientId, senderId, maxId, 
						rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						callback.callback(buildMstDto(rs, recipientId, senderId));
					}
				}
		);
	}

	@Override
	public Integer queryContentId(Integer recipientId, Integer senderId, Integer contentId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_CONTENT_ID, 
					recipientId, senderId, contentId);
		} catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}

	@Override
	public Integer queryRecipientId(Integer recipientId, Integer senderId, Integer contentId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_RECIPIENT_ID,
					recipientId, senderId, contentId);
		} catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}

	@Override
	public long queryRecipientCount(Integer recipientId, Integer senderId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_RECIPIENT_COUNT, 
				recipientId, senderId, maxId);
	}
}
