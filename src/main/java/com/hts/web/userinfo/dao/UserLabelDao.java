package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 用户标签数据访问接口
 * </p>
 * 
 * 创建时间：2014-1-11
 * @author ztj
 *
 */
public interface UserLabelDao extends BaseDao {
//
//	/**
//	 * 保存标签
//	 * 
//	 * @param userLabel
//	 */
//	public void saveLabel(UserLabel userLabel);
	
	/**
	 * 保存用户-标签关联
	 * 
	 * @param userId
	 * @param labelId
	 */
	public void saveUserLabel(Integer userId, Integer labelId);
	
	/**
	 * 删除用户标签
	 * 
	 * @param userId
	 * @param labelIds
	 */
	public void deleteUserLabelByLabelIds(Integer userId, Integer[] labelIds);
	
	/**
	 * 删除用户标签
	 * 
	 * @param userId
	 */
	public void deleteUserLabel(Integer userId);
	
//	
//	/**
//	 * 根据用户id查询其标签
//	 * @param userId
//	 * @return
//	 */
//	public Set<Integer> queryLabelByUID(Integer userId);
	

}
