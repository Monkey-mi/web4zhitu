package com.hts.web.userinfo.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserShieldInfo;

public interface UserShieldDao extends BaseDao {

	/**
	 * 查询屏蔽用户id
	 * 
	 * @param userId
	 * @param shieldId
	 * @return
	 */
	public Integer queryShieldId(Integer userId, Integer shieldId);

	
	/**
	 * 保存屏蔽
	 * 
	 * @param userId
	 * @param shieldId
	 * @param date
	 */
	public void saveShield(Integer userId, Integer shieldId, Date date);
	
	/**
	 * 删除屏蔽
	 * 
	 * @param userId
	 * @param shieldId
	 */
	public void deleteShield(Integer userId, Integer shieldId);
	
	/**
	 * 查询屏蔽用户
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserShieldInfo> queryShieldUser(Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询屏蔽用户
	 * 
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserShieldInfo> queryShieldUser(Integer maxId, Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询所有屏蔽id列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> queryShieldIds(Integer userId);
	
	/**
	 * 查询谁屏蔽了我
	 * 
	 * @param otherIds
	 * @param myId
	 * @return
	 */
	public Set<Integer> queryWhoShieldMe(Integer[] otherIds, Integer myId);
	
}
