package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpNearLabelWorldDto;

/**
 * 用户所发的附近标签织图 持久层
 * @author zxx 2015-12-16 16:29:23
 *
 */
public interface NearLabelWorldUserDao extends BaseDao{
	/**
	 * 根据最大序列号查询
	 * @param labelId
	 * @param maxSerial
	 * @param userId
	 * @param limit
	 * @return
	 * @author zxx 2015-12-16 16:35:04
	 */
	public List<OpNearLabelWorldDto> queryNearLabelWorldUser(Integer labelId,Integer maxSerial,Integer userId,int limit);
	
	/**
	 * 插入用户所发的附近标签织图
	 * @param id
	 * @param worldId
	 * @param worldAuthorId
	 * @param nearLabelId
	 * @param serial
	 * @author zxx 2015-12-16 16:35:04
	 */
	public void insertNearLabelWorldUser(Integer id,Integer worldId,Integer worldAuthorId,Integer nearLabelId,Integer serial);
	
	/**
	 * 根据id来删除用户所发的附近标签织图
	 * @param id
	 * @author zxx 2015-12-16 16:35:04
	 */
	public void deleteNearLabelWorldUserById(Integer id);
	
	/**
	 * 根据world id来删除用户所发的附近标签织图
	 * @param worldId
	 * @author zxx 2015-12-16 16:35:04
	 */
	public void deleteNearLabelWorldUserByWorldIdAndLabelId(Integer worldId,Integer nearLabelId);
	
	/**
	 * 根据world id来删除用户所发的附近标签织图
	 * @param worldId
	 * @author zxx 2015-12-16 16:35:04
	 */
	public void deleteNearLabelWorldUserByWorldId(Integer worldId);
}
