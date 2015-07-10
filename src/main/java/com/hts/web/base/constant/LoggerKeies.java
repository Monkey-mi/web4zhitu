package com.hts.web.base.constant;

/**
 * <p>
 * 日志键值,用于细化每个模块的日志存储路径
 * </p>
 * 
 * 创建时间: 2015-06-23
 * @author lynch
 *
 */
public class LoggerKeies {

	private static final String PREFIX = "com.hts.web.";
	private static final String MODULE_USER = PREFIX + "user.";
	private static final String MODULE_PUSH = PREFIX + "push.";
	private static final String MODULE_WORLD = PREFIX + "world.";
	
	/**
	 * 用户登录日志
	 */
	public static final String USER_LOGIN = MODULE_USER + "login";
	
	/**
	 * 第三方登录日志
	 */
	public static final String USER_LOGIN_BY_SOCIAL = MODULE_USER + "loginBySocial";

	/**
	 * 用户注册日志
	 */
	public static final String USER_REGISTER = MODULE_USER + "register";
	
	/**
	 * 第三方注册
	 */
	public static final String USER_REGISTER_BY_SOCIAL = MODULE_USER + "registerBySocial";

	/**
	 * 用户im
	 */
	public static final String PUSH_IM = MODULE_PUSH + "im";

	/**
	 * 保存织图
	 */
	public static final String WORLD_WORLD = MODULE_WORLD + "world";
	
	/**
	 * 保存文本
	 */
	public static final String WORLD_TEXT = MODULE_WORLD + "text";
	
	/**
	 * 屏蔽评论
	 */
	public static final String WORLD_SHIELD_COMMENT = MODULE_WORLD + "shieldComment";
	
}
