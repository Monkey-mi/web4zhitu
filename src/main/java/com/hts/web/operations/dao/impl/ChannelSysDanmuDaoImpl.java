package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpChannelSysDanmuDto;
import com.hts.web.operations.dao.ChannelSysDanmuDao;

@Repository("HTSChannelSysDanmuDao")
public class ChannelSysDanmuDaoImpl extends BaseDaoImpl implements
		ChannelSysDanmuDao {
	
	private static String table = HTS.OPERATIONS_CHANNEL_SYS_DANNMU;
	
	private static final String DANMU_INFO = "d0.serial,d0.author_id,d0.content,u0.user_avatar";
	
	private static final String QUERY_DANMU = "select " + DANMU_INFO
			+ " from " + table + " d0," + HTS.USER_INFO + " u0"
			+ " where d0.author_id=u0.id and d0.channel_id=?"
			+ " order by d0.id desc limit ?,?";
	
	private static final String QUEYR_DANMU_BY_MAXID = "select " + DANMU_INFO
			+ " from " + table + " d0," + HTS.USER_INFO + " u0"
			+ " where d0.author_id=u0.id and d0.channel_id=? and d0.serial<=?"
			+ " order by d0.id desc limit ?,?";
	
	@Override
	public List<OpChannelSysDanmuDto> querySysDanmu(Integer channelId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_DANMU, 
				new Object[]{channelId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelSysDanmuDto>() {

					@Override
					public OpChannelSysDanmuDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildDanmuDto(rs);
					}
		});
	}

	@Override
	public List<OpChannelSysDanmuDto> querySysDanmu(Integer maxId,
			Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUEYR_DANMU_BY_MAXID, 
				new Object[]{channelId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelSysDanmuDto>() {

					@Override
					public OpChannelSysDanmuDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildDanmuDto(rs);
					}
			
		});
	}
	
	public OpChannelSysDanmuDto buildDanmuDto(ResultSet rs) throws SQLException {
		return new OpChannelSysDanmuDto(
			rs.getInt("serial"),
			rs.getString("content"),
			rs.getInt("author_id"),
			rs.getString("user_avatar")
		);
	}

}
