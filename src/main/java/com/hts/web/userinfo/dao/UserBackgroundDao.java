package com.hts.web.userinfo.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * @author zhangbo	2015年12月21日
 *
 */
public interface UserBackgroundDao extends BaseDao {
	
	/**
	 * 根据id查询用户背景图片路径
	 * 
	 * @param userId	用户id
	 * @return
	 */
	String getUserBackgroundById(Integer userId) throws Exception;
	
	/**
	 * 新增用户背景图片路径
	 * 
	 * @param userId		用户id
	 * @param background	背景图片路径
	 * @author zhangbo	2015年12月21日
	 */
	void saveUserBackground(Integer userId, String background) throws Exception;
	
	/**
	 * 更新用户背景图片路径
	 * 
	 * @param userId		用户id
	 * @param background	背景图片路径
	 * @author zhangbo	2015年12月21日
	 */
	void updateUserBackground(Integer userId, String background) throws Exception;
}
