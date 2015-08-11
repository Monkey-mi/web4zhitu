package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.UserInfoDto;

/**
 * 达人推荐缓存数据访问接口
 * 
 * @author lynch
 *
 */
public interface UserVerifyRecCacheDao extends BaseCacheDao {

	/**
	 * 根据认证id查询推荐用户列表
	 * 
	 * @param verifyId
	 * @param limit
	 * @return
	 */
	public List<UserInfoDto> queryUserByVerifyId(Integer verifyId, Integer limit);
	
	public void update(Integer verifyId);

}