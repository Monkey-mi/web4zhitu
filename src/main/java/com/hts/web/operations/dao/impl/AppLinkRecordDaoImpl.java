package com.hts.web.operations.dao.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpAdAppLinkRecord;
import com.hts.web.operations.dao.AppLinkRecordDao;

@Repository("HTSAppLinkRecordDao")
public class AppLinkRecordDaoImpl extends BaseDaoImpl implements
		AppLinkRecordDao {
	
	private static String table = HTS.OPERATIONS_AD_APPLINK_RECORD;
	
	public static final String SAVE_RECORD = "insert into " + table
			+ " (record_ip,record_date,app_id) values (?,?,?)";
	
	@Override
	public void saveRecord(OpAdAppLinkRecord record) {
		getMasterJdbcTemplate().update(SAVE_RECORD, new Object[]{
			record.getRecordip(),
			record.getRecordDate(),
			record.getAppId()
		});
	}

}
