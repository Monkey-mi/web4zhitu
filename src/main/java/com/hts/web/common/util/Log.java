package com.hts.web.common.util;

import org.apache.log4j.Logger;

/**
 * <p>
 * Log工具类
 * </p>
 * 
 * 创建时间：2012-10-19
 * @author ztj
 *
 */
public class Log {
	
	private static Logger logger = Logger.getRootLogger();
	
	public static void warn(Integer uid, Object warn) {
		logger.warn(warn);
	}
	
	public static void warn(Object warn){
		logger.warn(warn);
	}
	
	public static void warn(Object warn, Throwable t){
		logger.warn(warn, t);
	}
	
	public static void info(Object info){
		logger.info(info);
	}
	
	public static void info(Object info, Throwable t){
		logger.info(info, t);
	}
	
	public static void debug(Object msg){
		logger.debug(msg);
	}
	
	public static void debug(Object msg, Throwable t){
		logger.debug(msg, t);
	}
	
	public static void error(Object msg) {
		logger.error(msg);
	}
	
	public static void error(Object msg, Throwable t) {
		logger.error(msg, t);
	}
	
}
