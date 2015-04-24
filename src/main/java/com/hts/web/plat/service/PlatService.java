package com.hts.web.plat.service;

/**
 * <p>
 * 社交平台业务逻辑访问接口
 * </p>
 * 
 * 创建时间: 2015-04-17
 * @author lynch
 *
 */
public interface PlatService {
	
	/**
	 * 保存社交平台关注信息
	 * 
	 * @param uid
	 * @param cid
	 * @param cname
	 * @param pid
	 */
	public void savePlatConcern(String uid, Integer pid);
	
}
