package com.hts.web.userinfo.dao;

import java.util.List;

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
	public void deleteSendMsg(Integer recipientId, Integer contentId);

	/**
	 * 查询收件箱消息
	 * 
	 * @param recipientId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-10-28
	 * 
	 */
	public List<UserMsgDto> querySendMsg(Integer recipientId,
			RowSelection rowSelection);
	
	/**
	 * 查询收件箱消息
	 * 
	 * @param maxId
	 * @param recipientId
	 * @param rowSelection
	 * @return
	 * @author lynch 2015-10-28
	 */
	public List<UserMsgDto> querySendMsg(Integer maxId, Integer recipientId, 
			RowSelection rowSelection);
	
}
