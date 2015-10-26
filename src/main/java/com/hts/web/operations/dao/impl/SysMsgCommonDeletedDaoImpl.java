package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.operations.dao.SysMsgCommonDeletedDao;

@Repository("HTSSysMsgCommonDeletedDao")
public class SysMsgCommonDeletedDaoImpl extends BaseDaoImpl implements SysMsgCommonDeletedDao {

	private static String table = HTS.OPERATIONS_SYS_MSG_COMMON_DELETE;
	
	private static final String SAVE_DELETED = "insert into " + table 
			+ " (recipient_id, msg_id) values (?,?)";
	
	private static final String QUERY_MSG_ID = "select msg_id from " + table
			+ " where recipient_id=? and msg_id<=? order by msg_id desc";
	
	@Override
	public void saveDeleted(Integer recipientId, Integer msgId) {
		getMasterJdbcTemplate().update(SAVE_DELETED, recipientId, msgId);
	}

	@Override
	public void queryMsgId(Integer maxId, Integer recipientId,
			final RowCallback<Integer> callback) {
		getJdbcTemplate().query(QUERY_MSG_ID, new Object[]{recipientId, maxId}, 
				new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("msg_id"));
			}
		});
	}

}
