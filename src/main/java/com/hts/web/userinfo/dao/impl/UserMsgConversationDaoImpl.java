package com.hts.web.userinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserMsgConver;
import com.hts.web.userinfo.dao.UserMsgConversationDao;

@Repository("HTSUserMsgConversationDao")
public class UserMsgConversationDaoImpl extends BaseDaoImpl implements UserMsgConversationDao {

	private static String table = HTS.USER_MSG_CONVERSATION;

	private static final String SAVE_CONVER = "insert into " + table
			+ "(user_id,other_id,content_id,unread_count,msg_type) values (?,?,?,?,?)";
	
	private static final String SEND_MSG = "update " + table
			+ " set content_id=?, msg_type=1 where user_id=? and other_id=?";
	
	private static final String RECEIVE_MSG = "update " + table
			+ " set content_id=?, unread_count=unread_count+1, msg_type=0"
			+ " where user_id=? and other_id=?";

	private static final String CLEAR_UNREAD_COUNT = "update " + table
			+ " set unread_count=0 where user_id=? and other_id=?";
	
	@Override
	public void saveConver(UserMsgConver conver) {
		getMasterJdbcTemplate().update(SAVE_CONVER, 
				conver.getUserId(),
				conver.getOtherId(),
				conver.getContentId(),
				conver.getUnreadCount(),
				conver.getMsgType());
	}

	@Override
	public Integer sendMsg(Integer userId, 
			Integer otherId, Integer contentId) {
		return getMasterJdbcTemplate().update(SEND_MSG, 
				contentId, userId, otherId);
	}
	
	@Override
	public Integer receiveMsg(Integer userId, 
			Integer otherId, Integer contentId) {
		return getMasterJdbcTemplate().update(RECEIVE_MSG, 
				contentId, userId, otherId);
	}

	@Override
	public Integer clearUnreadCount(Integer userId, Integer otherId) {
		return getMasterJdbcTemplate().update(CLEAR_UNREAD_COUNT, 
				userId, otherId);
	}

}
