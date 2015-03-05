package com.hts.web.common.pojo;

import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * 普通用户权限POJO
 * </p>
 * 
 * 创建时间：2013-8-24
 * @author ztj
 *
 */
public class UserGrantedAuthority implements GrantedAuthority {

	private static final String ROLE = "ROLE_USER"; // 普通用户权限
	/**
	 * 
	 */
	private static final long serialVersionUID = 7946128374524359409L;

	@Override
	public String getAuthority() {
		return ROLE;
	}

}
