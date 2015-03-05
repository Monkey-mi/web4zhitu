package com.hts.web.security.dao;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * <p>
 * 用户持久登录数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-23
 * @author ztj
 *
 */
public interface UserLoginPersistentDao extends PersistentTokenRepository {

	/**
	 * 根据用户id查询Token
	 * @param userId
	 * @return
	 */
	public PersistentRememberMeToken queryTokenByUserId(Integer userId);
}
