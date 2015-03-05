package com.hts.web.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 网络访问工具类
 * </p>
 * 
 * 创建时间：2012-11-01
 * @author ztj
 *
 */
public class HttpUtil {

	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getRemoteIP(HttpServletRequest request) {

		if (request.getHeader("x-forwarded-for") == null) {

			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	
	/**
	 * 获取服务器路径信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getServerContextPath(HttpServletRequest request) {
		String path = null;
		path = request.getRequestURI();
		return path;
	}
	/**
	 * 获取真实的客户端IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {      
	       String ip = request.getHeader("x-forwarded-for");      
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	          ip = request.getHeader("Proxy-Client-IP");      
	      }      
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	          ip = request.getHeader("WL-Proxy-Client-IP");      
	       }      
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	           ip = request.getRemoteAddr();      
	      }      
	     return ip;      
	}  
}
