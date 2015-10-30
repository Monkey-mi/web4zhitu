package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsgBox;
import com.hts.web.common.pojo.UserMsgDto;

/**
 * <p>
 * 私信发件箱数据访问接口
 * </p>
 * 
 * @author ztj 2013-11-28 2015-10-28
 *
 */
public interface UserMsgSendBoxDao extends BaseDao {

	/**
	 * 保存消息到收件箱
	 * 
	 * @param contentId
	 * @param userId
	 * @author lynch 2015-10-28
	 */
	public void saveSendMsg(UserMsgBox msgBox);
	
	/**
	 * 删除收件箱消息
	 * 
	 * @param userId
	 * @param contentId
	 * @author lynch 2015-10-28
	 */
	public void deleteSendMsg(Integer senderId, Integer recipientId, Integer contentId);

	/**
	 * 查询收件箱消息
	 * 
	 * @param recipientId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-10-28
	 * 
	 */
	public void querySendMsg(Integer senderId, Integer recipientId,
			RowSelection rowSelection, RowCallback<UserMsgDto> callback);
	
	/**
	 * 查询收件箱消息
	 * 
	 * @param maxId
	 * @param recipientId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-10-28
	 */
	public void querySendMsg(Integer maxId, Integer senderId, Integer recipientId,
			RowSelection rowSelection, RowCallback<UserMsgDto> callback);
	
	
	/**
	 * 查询消息id
	 * 
	 * @param senderId
	 * @param recipientId
	 * @param contentId
	 */
	public Integer queryContentId(Integer senderId, 
			Integer recipientId, Integer contentId);
	
	/**
	 * 查询发件数
	 * 
	 * @param senderId
	 * @param recipientId
	 * @param maxId
	 * @return
	 * 
	 * @author lynch 2015-10-30
	 */
	public long querySendCount(Integer senderId, Integer recipientId, Integer maxId);
	
}
