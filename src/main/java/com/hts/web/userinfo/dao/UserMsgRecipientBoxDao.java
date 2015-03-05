package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsgBox;

/**
 * <p>
 * 私信收件箱
 * </p>
 * 
 * 创建时间：2013-11-28
 * @author ztj
 *
 */
public interface UserMsgRecipientBoxDao extends BaseDao {

	/**
	 * 保存收件
	 * @param box
	 */
	public void saveRecipientBox(UserMsgBox box);
	
	/**
	 * 更新未读标记
	 * 
	 * @param maxId
	 * @param senderId
	 * @param recipientId
	 */
	public void updateRecipientCK(Integer maxId, Integer senderId, Integer recipientId);
	
	/**
	 * 更新对话有效性
	 * @param maxContentId
	 * @param senderId
	 * @param recipientId
	 */
	public void updateChatUnValid(Integer maxContentId, Integer senderId, Integer recipientId);
	
	/**
	 * 更新收件有效性
	 * 
	 * @param contentId
	 */
	public void updateUnValid(Integer contentId);
	
	/**
	 * 根据消息id查询发送者id
	 * @param contentId
	 * @return
	 */
	public Integer[] querySenderIdByContentId(Integer contentId);
	
	/**
	 * 查询未读消息总数
	 * 
	 * @param userId
	 * @return
	 */
	public long queryUnReadMsgCount(Integer userId);
	
	/**
	 * 设置和指定用户的收件无效
	 * @param senderId
	 * @param recipientId
	 */
	public void updateRecipientUnValid(Integer[] senderIds, Integer recipientId);
}
