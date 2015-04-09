package com.hts.web.userinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserMsgBox;
import com.hts.web.userinfo.dao.UserMsgSendBoxDao;

@Repository("HTSUserMsgSendBoxDao")
public class UserMsgSendBoxDaoImpl extends BaseDaoImpl implements
		UserMsgSendBoxDao {

	public static String table = HTS.USER_MSG_SEND_BOX;
	
	/** 保存发送的信息*/
	public static final String SAVE_SEND_MSG = "insert into " + table 
			+ " (id,sender_id,recipient_id,content_id) values (?,?,?,?)";
	
	/** 更新对话有效性 */
	private static final String UPDATE_CHAT_UNVALID_BY_MAX_CONTENT_ID = "update " + table
			+ " set chat_valid=? where sender_id=? and recipient_id=? and chat_valid=? and content_id<=?";
	
	/** 更新发件有效性 */
	private static final String UPDATE_UNVALID_BY_CONTENT_ID = "update " + table
			+ " set valid=?, chat_valid=? where content_id=? and valid=?";
	
	@Override
	public void saveSendMsg(UserMsgBox box) {
		getMasterJdbcTemplate().update(SAVE_SEND_MSG, new Object[]{
			box.getId(),
			box.getSenderId(),
			box.getRecipientId(),
			box.getContentId()
		});
	}

	@Override
	public void updateChatUnValid(Integer maxContentId, Integer senderId, Integer recipientId) {
		getMasterJdbcTemplate().update(UPDATE_CHAT_UNVALID_BY_MAX_CONTENT_ID, 
				new Object[]{Tag.FALSE, senderId, recipientId, Tag.TRUE, maxContentId });
	}

	@Override
	public void updateUnValid(Integer contentId) {
		getMasterJdbcTemplate().update(UPDATE_UNVALID_BY_CONTENT_ID,
				new Object[]{Tag.FALSE, Tag.FALSE, contentId, Tag.TRUE});
	}
	
	
}
