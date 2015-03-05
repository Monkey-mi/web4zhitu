package com.hts.web.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * Security 工具类
 * </p>
 * 
 * 创建时间：2013-8-24
 * @author ztj
 *
 */
public class SecurityUtil {

	/**
	 * 获取登录用户信息
	 * 
	 * @return
	 */
	public static UserDetails getUser() {
		//取得登录用户      
		UserDetails user = null;
        SecurityContext ctx = SecurityContextHolder.getContext();              
        Authentication auth = ctx.getAuthentication(); 
        if(auth != null && auth.getPrincipal() instanceof UserDetails) {
    		user = (UserDetails)auth.getPrincipal();          
        }
        
        return user;
	}
}
