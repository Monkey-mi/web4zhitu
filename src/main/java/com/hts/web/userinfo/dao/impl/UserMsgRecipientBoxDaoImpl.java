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
import com.hts.web.common.pojo.UserAvatar;
import com.hts.web.common.pojo.UserMsgBox;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.userinfo.dao.UserMsgRecipientBoxDao;

@Repository("HTSUserMsgRecipientBoxDao")
public class UserMsgRecipientBoxDaoImpl extends BaseDaoImpl implements
		UserMsgRecipientBoxDao {

	private static String table = HTS.USER_MSG_RECIPIENT_BOX;
	
	private static final String MSG_INFO = 
			"u0.user_name,u0.user_avatar,u0.user_avatar_l,"
			+ "m0.id,m0.content,m0.msg_date,"
			+ "b0.sender_id";
	
	private static final String SAVE_RECIPIENT_MSG = "insert into " + table
			+ " (sender_id,recipient_id,content_id) values (?,?,?)";
	
	private static final String DELETE_RECIPIENT_MSG = "delete from " + table
			+ " where recipient_id=? and content_id=?";
	
	private static final String QUERY_MSG = "select " + MSG_INFO + " from " 
			+ table + " b0," + HTS.USER_INFO + " u0," + HTS.USER_MSG + " m0"
			+ " where b0.content_id=m0.id and b0.sender_id=u0.id and b0.recipient_id=?"
			+ " order by b0.id desc limit ?,?";
	
	private static final String QUERY_MSG_MSG_ID = "select " + MSG_INFO + " from " 
			+ table + " b0," + HTS.USER_INFO + " u0," + HTS.USER_MSG + " m0"
			+ " where b0.content_id=m0.id and b0.sender_id=u0.id and b0.recipient_id=?"
			+ " and b0.id<=?"
			+ " order by b0.id desc limit ?,?";

	/**
	 * 构建消息POJO
	 * 
	 * @param rs
	 * @param recipientId
	 * @return
	 * @throws SQLException
	 */
	public UserMsgDto buildMstDto(ResultSet rs, Integer recipientId) throws SQLException {
		UserMsgDto dto = new UserMsgDto();
		UserAvatar sender = new UserAvatar();
		UserAvatar recipient = new UserAvatar();
		
		dto.setId(rs.getInt("id"));
		dto.setSenderId(rs.getInt("senderId"));
		dto.setRecipientId(rs.getInt("recipientId"));
		dto.setMsgDate((Date)rs.getObject("msg_date"));
		dto.setContent(rs.getString("content"));
		
		sender.setId(rs.getInt("sender_id"));
		sender.setUserName(rs.getString("user_name"));
		sender.setUserAvatar(rs.getString("user_avatar"));
		sender.setUserAvatarL(rs.getString("user_avatar_l"));
		
		recipient.setId(recipientId);
		
		dto.setSenderInfo(sender);
		dto.setRecipientInfo(recipient);

		return dto;
	}
	
	@Override
	public void saveRecipientMsg(UserMsgBox msgBox) {
		getMasterJdbcTemplate().update(SAVE_RECIPIENT_MSG,
				new Object[]{
					msgBox.getSenderId(),
					msgBox.getRecipientId(),
					msgBox.getContentId()
				});
	}

	@Override
	public void deleteRecipientMsg(Integer recipientId, Integer contentId) {
		getMasterJdbcTemplate().update(DELETE_RECIPIENT_MSG, 
				new Object[]{recipientId, contentId});
	}

	@Override
	public List<UserMsgDto> queryRecipientMsg(final Integer recipientId, 
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_MSG, 
				new Object[]{recipientId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<UserMsgDto>() {

					@Override
					public UserMsgDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						return buildMstDto(rs, recipientId);
					}
			
		});
	}

	@Override
	public List<UserMsgDto> queryRecipientMsg(Integer maxId, final Integer recipientId, 
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_MSG_MSG_ID, 
				new Object[]{recipientId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<UserMsgDto>() {

					@Override
					public UserMsgDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						return buildMstDto(rs, recipientId);
					}
			
		});
	}
	
	
}
