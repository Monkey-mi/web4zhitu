package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserMsgBox;
import com.hts.web.userinfo.dao.UserMsgRecipientBoxDao;

@Repository("HTSUserMsgRecipientBoxDao")
public class UserMsgRecipientBoxDaoImpl extends BaseDaoImpl implements
		UserMsgRecipientBoxDao {

	private static String table = HTS.USER_MSG_RECIPIENT_BOX;
	
	private static final String SAVE_RECIPIENT_MSG = "insert into " + table
			+ " (id,sender_id,recipient_id,content_id,ck) values (?,?,?,?,?)";
	
	/** 
	 * 更新收件箱未读标记 
	 */
	private static final String UPDATE_RECIPIENT_CK = "update " + HTS.USER_MSG_RECIPIENT_BOX 
			+ " set ck=? where sender_id=? and recipient_id=? and valid=? and chat_valid=? and ck=? and id<=?";
	
	/** 
	 * 更新对话有效性
	 */
	private static final String UPDATE_CHAT_UNVALID_BY_MAX_CONTENT_ID = "update " + table
			+ " set chat_valid=? where sender_id=? and recipient_id=? and chat_valid=? and id<=?";

	/** 
	 * 更新收件有效性
	 */
	private static final String UPDATE_UNVALID_BY_CONTENT_ID = "update " + table
			+ " set valid=?, chat_valid=? where id=? and valid=?";
	
	/**
	 * 根据私信id查询发送者
	 */
	private static final String QUERY_SENDER_ID_BY_CONTENT_ID = "select sender_id,recipient_id from " + table
			+ " where id=?";
	
	/** 
	 * 查询未读消息总数
	 */
	private static final String QUERY_UN_READ_MSG_COUNT = "select count(*) from " + table + " where recipient_id=? and ck=?";
	
	/**
	 * 设置和指定用户的收件无效
	 */
	private static final String UPDATE_RECIPIENT_UN_VALID_BY_SIDS = "update " + table + " set valid=0"
			+ " where valid=1 and recipient_id=? and sender_id in ";
	
	@Override
	public void saveRecipientBox(UserMsgBox box) {
		getJdbcTemplate().update(SAVE_RECIPIENT_MSG, new Object[]{
			box.getId(),
			box.getSenderId(),
			box.getRecipientId(),
			box.getContentId(),
			box.getCk()
		});
	}

	@Override
	public void updateRecipientCK(Integer maxId, Integer userId, Integer otherId) {
		getJdbcTemplate().update(UPDATE_RECIPIENT_CK, 
				new Object[]{Tag.TRUE, userId, otherId, Tag.TRUE, Tag.TRUE, Tag.FALSE, maxId});
	}

	@Override
	public void updateChatUnValid(Integer maxContentId, Integer senderId,
			Integer recipientId) {
		getJdbcTemplate().update(UPDATE_CHAT_UNVALID_BY_MAX_CONTENT_ID, 
				new Object[]{Tag.FALSE, senderId, recipientId, Tag.TRUE, maxContentId});
	}

	@Override
	public void updateUnValid(Integer contentId) {
		getJdbcTemplate().update(UPDATE_UNVALID_BY_CONTENT_ID,
				new Object[]{Tag.FALSE, Tag.FALSE, contentId, Tag.TRUE});
	}

	@Override
	public Integer[] querySenderIdByContentId(Integer contentId) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_SENDER_ID_BY_CONTENT_ID, new Object[]{contentId}, new RowMapper<Integer[]>(){

				@Override
				public Integer[] mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Integer[] i = new Integer[2];
					i[0] = rs.getInt("sender_id");
					i[1] = rs.getInt("recipient_id");
					return i;
				}
				
			});
		} catch(EmptyResultDataAccessException e) {
			
		}
		return null;
		
	}

	@Override
	public long queryUnReadMsgCount(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_UN_READ_MSG_COUNT, new Object[]{userId, Tag.FALSE});
	}

	@Override
	public void updateRecipientUnValid(Integer[] senderIds, Integer recipientId) {
		String selection = SQLUtil.buildInSelection(senderIds);
		String sql = UPDATE_RECIPIENT_UN_VALID_BY_SIDS + selection;
		Object[] args = SQLUtil.getArgsByInCondition(senderIds, new Object[]{recipientId}, true);
		getJdbcTemplate().update(sql, args);
	}
	
	
}
