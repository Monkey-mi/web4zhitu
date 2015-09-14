package com.hts.web.common.dao;



/**
 * <p>
 * 主键生成器数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface KeyGenDao extends BaseCacheDao {
	
	/**
	 * 生成下一个id
	 * 
	 * @param keyId
	 * @return
	 */
	public Integer nextId(String keyId);
	
	/**
	 * 生成下一个id
	 * 
	 * @param keyId
	 * @param step
	 * @return
	 */
	public Integer nextId(String keyId, long step);
	
}
