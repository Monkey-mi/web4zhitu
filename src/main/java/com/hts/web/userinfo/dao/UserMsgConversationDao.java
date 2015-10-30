package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsgConver;

/**
 * 用户私信对话数据访问接口
 * 
 * @author lynch 2015-10-29
 *
 */
public interface UserMsgConversationDao extends BaseDao {

	/**
	 * 发送消息
	 */
	public static final int MSG_TYPE_SEND = 1;
	
	/**
	 * 接收消息
	 */
	public static final int MSG_TYPE_RECEIVE = 0;

	/**
	 * 保存对话
	 * 
	 * @param conver
	 * @author lynch 2015-10-30
	 */
	public void saveConver(UserMsgConver conver);

	/**
	 * 更新对话
	 * 
	 * @param userId
	 * @param otherId
	 * @param contentId
	 * @return
	 */
	public Integer sendMsg(Integer userId, 
			Integer otherId, Integer contentId);

	/**
	 * 更新对话id和未读数
	 * 
	 * @param userId
	 * @param otherId
	 * @param contentId
	 * @return
	 */
	public Integer receiveMsg(Integer userId, 
			Integer otherId, Integer contentId);
	/**
	 * 清空未读数量
	 * 
	 * @param userId
	 * @param otherId
	 * @return
	 */
	public Integer clearUnreadCount(Integer userId,
			Integer otherId);
	

}
