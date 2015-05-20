package com.hts.web.operations.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.operations.dao.ChannelDanmuReadDao;

@Repository("HTSChannelDanmuReadDao")
public class ChannelDanmuReadDaoImpl extends BaseDaoImpl implements
		ChannelDanmuReadDao {
	
	private static String table = HTS.OPERATIONS_CHANNEL_DANMU_READ;
	
	private static final String SAVE_DANMU_SERIAL = "insert into " + table
			+ " (channel_id,user_id,danmu_serial) values (?,?,?)";
	
	private static final String QUERY_DANMU_SERIAL = "select danmu_serial from " 
			+ table + " where channel_id=? and user_id=?";
	
	private static final String UPDATE_DANMU_SERIAL = "update " 
			+ table + " set danmu_serial=? where channel_id=? and user_id=?";

	@Override
	public void updateDanmuSerial(Integer channelId, Integer userId, 
			Integer danmuId) {
		getMasterJdbcTemplate().update(UPDATE_DANMU_SERIAL, 
				new Object[]{danmuId, channelId, userId});
	}

	@Override
	public Integer queryDanmuSerial(Integer channelId, Integer userId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_DANMU_SERIAL, 
					new Object[]{channelId, userId});
		} catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}

	@Override
	public void saveDanmuSerial(Integer channelId, Integer userId, Integer maxId) {
		getMasterJdbcTemplate().update(SAVE_DANMU_SERIAL, 
				new Object[]{channelId, userId, maxId});
	}

}
