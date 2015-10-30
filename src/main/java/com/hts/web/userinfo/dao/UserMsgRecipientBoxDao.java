package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserAvatar;
import com.hts.web.common.pojo.UserMsgBox;
import com.hts.web.common.pojo.UserMsgDto;

/**
 * <p>
 * 私信收件箱数据访问接口
 * </p>
 * 
 * @author ztj 2013-11-28 2015-10-28
 *
 */
public interface UserMsgRecipientBoxDao extends BaseDao {

	/**
	 * 保存消息到收件箱
	 * 
	 * @param contentId
	 * @param userId
	 * @author lynch 2015-10-28
	 */
	public void saveRecipientMsg(UserMsgBox msgBox);
	
	/**
	 * 删除收件箱消息
	 * 
	 * @param userId
	 * @param contentId
	 * @author lynch 2015-10-28
	 */
	public void deleteRecipientMsg(Integer recipientId, Integer senderId, Integer contentId);

	/**
	 * 查询收件箱消息
	 * 
	 * @param recipientId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-10-28
	 * 
	 */
	public void queryRecipientMsg(Integer recipientId, Integer senderId,
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
	public void queryRecipientMsg(Integer maxId, Integer recipientId,
			Integer senderId, RowSelection rowSelection, RowCallback<UserMsgDto> callback);
	
	
	/**
	 * 查询消息id
	 * 
	 * @param recipientId
	 * @param senderId
	 * @param contentId
	 * @return
	 */
	public Integer queryContentId(Integer recipientId, Integer senderId,
			Integer contentId);

	/**
	 * 查询接受者id
	 * 
	 * @param recipientId
	 * @param senderId
	 * @param content
	 * @return
	 */
	public Integer queryRecipientId(Integer recipientId, Integer senderId,
			Integer contentId);
	
	/**
	 * 查询收件总数
	 * 
	 * @param recipientId
	 * @param senderId
	 * @param maxId
	 * @return
	 */
	public long queryRecipientCount(Integer recipientId, 
			Integer senderId, Integer maxId);
}
