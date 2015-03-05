package com.hts.web.base.database;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hts.web.common.util.XmlHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * 数据库管理操作基础类，弃用，使用Spring框架进行统一配置管理
 * 
 * @author ztj
 * 
 */
@Deprecated
public abstract class DBManager {

	protected static Logger log = LoggerFactory.getLogger(DBManager.class);
	protected static ComboPooledDataSource dbConnPool;
	protected static DataSource dataSource;
	
	/**
	 * 初始化
	 * 
	 * @param confXMLPath 数据库配置文件存路径
	 */
	public void initWithXMLPath(String confXMLPath) {
		Document doc = XmlHelper.buildDocument(confXMLPath);
		initDBConfig(doc.getDocumentElement());
	}
	
	
	/**
	 * 销毁连接池
	 */
	public void destroy() {
		try {
			DataSources.destroy(dbConnPool);
			dbConnPool.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException 
	 */
//	public synchronized static Connection getConnection() throws SQLException {
////		return dbConnPool.getConnection();
//		return dataSource.getConnection();
//	}

	/**
	 * 初始化数据库
	 * 
	 * @param dbConfig
	 * @param dbName
	 */
	private void initDBConfig(Element dbConfig) {
		DBConfig conf = DBConfig.parse(dbConfig);
		initialize(conf.dirver, conf.url + "/" + conf.name + "?characterEncoding=UTF-8", conf.user, conf.pwd);
	}

	/**
	 * 初始化连接池
	 * 
	 * @param driver
	 * @param url
	 * @param user
	 * @param pwd
	 */
	private void initialize(String driver, String url, String user, String pwd) {
		try {
			dbConnPool = new ComboPooledDataSource();
			dbConnPool.setDriverClass(driver);
			dbConnPool.setJdbcUrl(url);
			dbConnPool.setUser(user);
			dbConnPool.setPassword(pwd);
			doConfigurePool();
		} catch (Exception e) {
			log.warn("", e);
		}
	}
	
	/**
	 * 连接池属性配置
	 */
	protected void doConfigurePool() {
//		 dbConnPool.setCheckoutTimeout(1000);
//		 dbConnPool.setIdleConnectionTestPeriod(30);
//		 dbConnPool.setInitialPoolSize(5);
//		 dbConnPool.setMaxIdleTime(100);
//		 dbConnPool.setMaxPoolSize(30);
//		 dbConnPool.setMinPoolSize(5);
//		 dbConnPool.setAcquireIncrement(5);
//		 dbConnPool.setMaxStatements(100);
//		 dbConnPool.setMaxStatementsPerConnection(5);
	}


}
