package com.hts.web.base;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统启动、结束监听器，配置系统启动和销毁时的操作
 * 
 * @author ztj
 *
 */
public class HTSListener implements ServletContextListener {
	public static final String ROOT_PATH = "webRootPath"; //Web根路径
	public static final String QINIU_ACCESS_KEY = "alJXpWJFt2HW_r4UjYdyge9ut_FzXrWo4TILwnOD";
	public static final String QINIU_SECRET_KEY = "ees7veb5AFEZtj8Q3orisFFfw0VZ8vjyrDmcxaD6";
	public static final String QINIU_BUCKET = "imzhitu";
	public static final String WEB_VER = "webVer"; // Web版本
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String rootPath = context.getContextPath();
		context.setAttribute(ROOT_PATH, rootPath);
		context.setAttribute(WEB_VER, context.getInitParameter(WEB_VER));
		context.setAttribute("qiniuAccessKey", QINIU_ACCESS_KEY);
		context.setAttribute("qiniuSecretKey", QINIU_SECRET_KEY);
		context.setAttribute("qiniuBucket", QINIU_BUCKET);
	}
	
}
