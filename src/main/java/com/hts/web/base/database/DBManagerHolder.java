package com.hts.web.base.database;

import java.io.File;

/**
 * DBManager容器,用来装载系统的DBManager，弃用，使用Spring框架进行统一配置管理
 * 
 * 创建时间：2012-10-19
 * @author ztj
 *
 */
@Deprecated
public class DBManagerHolder {

	public static final String DB_HTS_CONF_XML_PATH = "hts-db-config.xml";
	public DBManager htsDBManager;
	
	/**
	 * 初始化DBManager容器
	 */
	public void init(String webInfoPath) {
		htsDBManager = HTSDBManager.getInstance();
		htsDBManager.initWithXMLPath(webInfoPath + File.separator + DB_HTS_CONF_XML_PATH);
	}
	
	/**
	 * 销毁DBManager容器，并销毁其中的所有DBManager
	 */
	public void destroy() {
		htsDBManager.destroy();
	}
	
	
	
}
