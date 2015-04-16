package com.hts.web.aliyun.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.UserOpenSearch;

/**
 * opensearch用户缓存数据访问接口
 * 
 * 创建时间:2015-04-15
 * @author lynch
 *
 */
public interface OsUserInfoCacheDao extends BaseCacheDao{

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public void updateUser(UserOpenSearch user);
	
	/**
	 * 更新用户信息，NULL值不更新
	 * 
	 * @param user
	 */
	public void updatUserWithoutNULL(UserOpenSearch user);
	
	
	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void addUser(UserOpenSearch user);
	
	/**
	 * 弹出限定操作
	 * 
	 * @param limit
	 */
	public void popOpts(int limit);
}
