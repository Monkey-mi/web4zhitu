package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsgBox;

/**
 * <p>
 * 私信发件箱数据访问接口
 * </p>
 * 
 * 创建时间：2013-11-28
 * @author ztj
 *
 */
public interface UserMsgSendBoxDao extends BaseDao {

	/**
	 * 保存发送信息
	 * 
	 * @param box
	 */
	public void saveSendMsg(UserMsgBox box);
	
	/**
	 * 更新有效性
	 * @param maxContentId
	 * @param senderId
	 * @param recipientId
	 */
	public void updateChatUnValid(Integer maxContentId, Integer senderId, Integer recipientId);
	
	/**
	 * 更新发件有效性
	 * 
	 * @param contentId
	 */
	public void updateUnValid(Integer contentId);
}
