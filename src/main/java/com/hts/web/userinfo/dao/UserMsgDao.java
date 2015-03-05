package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserMsg;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.common.pojo.UserMsgIndex;
import com.hts.web.common.pojo.UserMsgRecipientDto;

/**
 * <p>
 * 私信数据访问接口
 * </p>
 * 
 * 创建时间：2013-10-29
 * @author ztj
 *
 */
public interface UserMsgDao extends BaseDao {

	/**
	 * 保存私信
	 * @param msg
	 */
	public void saveMsg(UserMsg msg);
	
	/**
	 * 查询已关注对话索引
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgIndex> queryConcernMsgIndex(Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询已经关注对话索引
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgIndex> queryConcernMsgIndex(Integer maxId, Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询已关注对话索引总数
	 * @param maxId
	 * @param userId
	 * @return
	 */
	public Long[] queryConcernMsgIndexCount(Integer maxId, Integer userId);
	
	/**
	 * 查询未关注对话索引
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgIndex> queryUnConcernMsgIndex(Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询为关注对话索引
	 * 
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgIndex> queryUnConcernMsgIndex(Integer maxId, Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询陌生人对话索引总数
	 * 
	 * @param maxId
	 * @param userId
	 * @return
	 */
	public long queryUnConcernMsgIndexCount(Integer maxId, Integer userId);
	
	/**
	 * 查询未读消息总数
	 * @param userId
	 * @return
	 */
	public long queryUnReadCount(Integer maxId, Integer userId);
	
	/**
	 * 查询最大消息id
	 * @return
	 */
	public Integer queryMaxMsgId();
	
	/**
	 * 查询和指定用户的私信记录列表
	 * 
	 * @param userId
	 * @param otherId
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgDto> queryUserMsg(Integer userId, Integer otherId, RowSelection rowSelection);
	
	/**
	 * 查询和指定用户的私信记录列表
	 * 
	 * @param maxId
	 * @param userId
	 * @param otherId
	 * @param rowSelection
	 * @return
	 */
	public List<UserMsgDto> queryUserMsg(Integer maxId, Integer userId, Integer otherId, RowSelection rowSelection);
	
	/**
	 * 查询和指定用户的私信记录总数
	 * 
	 * @param maxId
	 * @param userId
	 * @param otherId
	 * @return
	 */
	public long queryUserMsgCount(Integer maxId, Integer userId, Integer otherId);
	
	/**
	 * 查询收件箱信息
	 * 
	 * @param minId
	 * @param senderId
	 * @param recipientId
	 * @return
	 */
	public List<UserMsgRecipientDto> queryRecipientMsg(Integer minId, Integer senderId, Integer recipientId);
	
	/**
	 * 查询发送者id
	 * 
	 * @param senderId
	 * @param recipientId
	 * @param msgType
	 * @return
	 */
	public Integer querySenderId(Integer senderId, Integer recipientId, Integer msgType);
	
	
}
