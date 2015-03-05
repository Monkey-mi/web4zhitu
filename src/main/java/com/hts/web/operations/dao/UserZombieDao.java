package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpSquare;
import com.hts.web.common.pojo.OpUserZombie;

/**
 * <p>
 * 僵尸用户数据访问接口
 * </p>
 * 
 * 创建时间：2013-9-10
 * @author ztj
 *
 */
public interface UserZombieDao extends BaseDao {

	/**
	 * 查询僵尸用户
	 * 
	 * @param rowSelection
	 * @return
	 */
	public List<OpUserZombie> queryZombieUser(RowSelection rowSelection);
	
	/**
	 * 查询僵尸用户总数
	 * 
	 * @return
	 */
	public long queryZombieUserCount();
	
	/**
	 * 保存推荐用户
	 * 
	 * @param recommend
	 */
	public void saveZombieUser(OpSquare recommend);
	

	/**
	 * 根据用户id删除僵尸
	 * 
	 * @param userId
	 */
	public void deleteZombieUserByUserId(Integer userId);
	
	/**
	 * 根据id删除僵尸
	 * 
	 * @param id
	 */
	public void deleteZombieUserById(Integer id);
	
	/**
	 * 随机查询僵尸id
	 * 
	 * @param limit
	 * @return
	 */
	public List<Integer> queryRandomZombieId(int limit);
	
	/**
	 * 更新屏蔽标志
	 * 
	 * @param userId
	 * @param shield
	 */
	public void updateShield(Integer userId, Integer shield);
	
	/**
	 * 查询所有马甲用户id
	 * 
	 * @return
	 */
	public List<Integer> queryUnShieldZombieUserId();
	
	/**
	 * 根据用户id查询马甲id
	 * @param uid
	 * @return
	 */
	public Integer queryIdByUID(Integer uid);
	
}
