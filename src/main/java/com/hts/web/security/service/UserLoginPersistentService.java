package com.hts.web.security.service;

/**
 * <p>
 * 用户持久化登录业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-24
 * @author ztj
 *
 */
public interface UserLoginPersistentService {
	
	/**
	 * 随机生成Series值
	 * @return
	 */
	public String generateSeriesData();

	/**
	 * 随机生成Token值
	 * @return
	 */
	public String generateTokenData();

}
