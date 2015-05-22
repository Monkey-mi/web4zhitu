package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpSysMsg;
import com.hts.web.common.pojo.OpSysMsgDto;

/**
 * <p>
 * 系统私信数据访问对象
 * </p>
 * 
 * 创建时间：2013-12-2
 * @author ztj
 *
 */
public interface SysMsgDao extends BaseDao {
	
	/**
	 * 保存私信
	 * @param msg
	 */
	public void saveMsg(OpSysMsg msg);	
	
	/**
	 * 查询接收到的私信列表
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpSysMsgDto> querySysMsgDto(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询接收到的私信列表
	 * 
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpSysMsgDto> querySysMsgDtoByMaxId(Integer userId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * （排除用户推荐消息）查询系统消息列表
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpSysMsgDto> querySysMsgDto2(Integer userId,
			RowSelection rowSelection);
			
	/**
	 * （排除用户推荐消息）根据最大id查询系统消息列表
	 * 
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<OpSysMsgDto> querySysMsgDtoByMaxId2(Integer userId,
			Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询接收到的私信总数
	 * 
	 * @param maxId
	 * @return
	 */
	public long querySysMsgCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 更新未读消息为已读（但不更新权重weight>0的消息）
	 * 
	 * @param userId
	 */
	public void updateUnreadSysMsg(Integer userId);
	
	/**
	 * 查询未读消息总数
	 * 
	 * @param userId
	 * @return
	 */
	public long queryUnCheckSysMsgCount(Integer userId);
	
	/**
	 * 查询未读消息总数2,排除用户推荐消息
	 * 
	 * @param userId
	 * @return
	 */
	public long queryUnCheckSysMsgCount2(Integer userId);
	
	/**
	 * 根据id删除私信
	 * 
	 * @param id
	 */
	public void deleteMsgById(Integer id);
	
	/**
	 * 查询有效消息
	 * 
	 * @param senderId
	 * @param recipientId
	 * @param objType
	 * @param objId
	 * @return
	 */
	public Integer queryValidMessage(Integer senderId,
			Integer recipientId, Integer objType, Integer objId);
	
	/**
	 * 查询有效消息
	 * 
	 * @param senderId
	 * @param recipientId
	 * @param objType
	 * @param objId
	 * @param objMeta
	 * @return
	 */
	public Integer queryValidMessage(Integer senderId, Integer recipientId, Integer objType,
			Integer objId, String objMeta);
	
	/**
	 * 更新接受有效状态
	 * 
	 * @param recipientId
	 * @param objType
	 * @param valid
	 */
	public void updateRecipientValid(Integer recipientId, Integer objType, Integer valid);
	
	/**
	 * 跟进objType查询单条消息
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public OpSysMsgDto querySysMsgByObjType(Integer userId, Integer objType);
	
	/**
	 * 查询接受者id
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryRecipientId(Integer id);
	
	/**
	 * 更新最新标记
	 * 
	 * @param recipientId
	 * @param minId
	 */
	public void updateIsNew(Integer recipientId, Integer minId);
	
}
