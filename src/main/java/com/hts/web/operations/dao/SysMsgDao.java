package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpSysMsgDto;

/**
 * <p>
 * 系统私信数据访问对象
 * </p>
 * 
 * @author ztj 2013-12-2 2015-10-24
 *
 */
public interface SysMsgDao extends BaseDao {
	
	/**
	 * 查询消息列表
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpSysMsgDto> queryMsg(Integer userId, 
			RowSelection rowSelection);

	/**
	 * 查询消息列表
	 * 
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpSysMsgDto> queryMsg(Integer maxId, Integer userId, 
			RowSelection rowSelection);
	
	/**
	 * 根据obj_type查询消息
	 * 
	 * @param userId
	 * @param objType
	 * @return
	 */
	public OpSysMsgDto queryMsgByObjType(Integer userId, Integer objType);
	
	/**
	 * 根据obj_type删除系统消息
	 * 
	 * @param userId
	 * @param objType
	 */
	public void deleteByObjType(Integer userId, Integer objType);
	
	/**
	 * 根据id删除消息
	 * 
	 * @param userId
	 * @param id
	 */
	public void deleteById(Integer userId, Integer id);
	
	
}
