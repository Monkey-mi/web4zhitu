package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpUser;

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
	public List<OpUser> queryUserByVerifyId(Integer verifyId, Integer limit);
	
	
	/**
	 * 根据认证id查询推荐用户列表, 置顶放在最前面
	 * @param verifyId
	 * @param limit
	 * @return
	 */
	public List<OpUser> queryUserByVerifyIdWithTop(Integer verifyId, Integer limit);
	
	/**
	 * 根据认证id随机查询推荐用户列表
	 * 
	 * @param verifyId
	 * @param limit
	 * @return
	 */
	public List<OpUser> queryRandomUserByVerifyId(Integer verifyId, Integer limit);
	
	/**
	 * 根据认证id查询推荐用户列表， 将置顶推荐放在最前面
	 * @param verifyId
	 * @param limit
	 * @return
	 */
	public List<OpUser> queryRandomUserByVerifyIdWithTop(Integer verifyId, Integer limit);
	
}