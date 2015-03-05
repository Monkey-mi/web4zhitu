package com.hts.web.security.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.UserGrantedAuthority;
import com.hts.web.common.pojo.UserLoginPersistent;

/**
 * <p>
 * 织图用户信息业务访问逻辑对象
 * </p>
 * 
 * 创建时间：2013-8-24
 * @author ztj
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new UserGrantedAuthority());
		return new UserLoginPersistent(userId, authorities);
	}
	
}
