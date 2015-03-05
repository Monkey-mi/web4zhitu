package com.hts.web.base.database;

/**
 * 汇图说数据管理对象，弃用，使用Spring框架进行统一配置管理
 * 
 * @author ztj
 *
 */
@Deprecated
public class HTSDBManager extends DBManager {

	private HTSDBManager() {
		super();
	}
	private static HTSDBManager htsdbManager = new HTSDBManager();
	public static HTSDBManager getInstance() {
		return htsdbManager;
	}
	
	@Override
	protected void doConfigurePool() {
		super.doConfigurePool();
		dbConnPool.setCheckoutTimeout(5000);
		dbConnPool.setIdleConnectionTestPeriod(30);
		dbConnPool.setInitialPoolSize(5);
		dbConnPool.setMaxIdleTime(100);
		dbConnPool.setMaxPoolSize(30);
		dbConnPool.setMinPoolSize(5);
		dbConnPool.setAcquireIncrement(5);
		dbConnPool.setMaxStatements(100);
		dbConnPool.setMaxStatementsPerConnection(5);
	}

	
}
