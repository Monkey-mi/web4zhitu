package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsgLiked;

/**
 * 喜欢消息POJO
 * 
 * @author lynch 2015-11-05
 *
 */
public interface MsgLikedDao extends BaseDao {

	/**
	 * 保存喜欢消息
	 * 
	 * @param id
	 * @param userId
	 * @param worldId
	 * @param receiveId
	 */
	public void saveMsg(Integer id, Integer userId, Integer worldId, Integer receiveId);
	
	/**
	 * 查询喜欢消息
	 * 
	 * @param receiveId
	 * @param limit
	 * @return
	 * @author lynch 2015-11-05
	 */
	public List<UserMsgLiked> queryMsg(Integer receiveId,
			Integer limit);

	/**
	 * 查询喜欢消息
	 * 
	 * @param maxId
	 * @param receiveId
	 * @param limit
	 * @return
	 * @author lynch 2015-11-05
	 */
	public List<UserMsgLiked> queryMsg(Integer maxId, Integer receiveId,
			Integer limit);
}
