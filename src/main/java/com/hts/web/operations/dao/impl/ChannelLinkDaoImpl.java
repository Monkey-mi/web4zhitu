package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpChannelLink;
import com.hts.web.operations.dao.ChannelLinkDao;

@Repository("HTSChannelLinkDao")
public class ChannelLinkDaoImpl extends BaseDaoImpl implements ChannelLinkDao {

	private static String table = HTS.OPERATIONS_CHANNEL_LINK;
	
	private static final String QUERY_LINK = "select c0.id,c0.channel_name from "
			+ table + " cl0," + HTS.OPERATIONS_CHANNEL + " c0"
			+ " where cl0.link_id=c0.id and cl0.channel_id=?"
			+ " order by cl0.serial desc";
	
	private static final String SAVE_LINK = "insert into " + table
			+ " (channel_id, link_id, serial) values (?,?,?)";
	
	@Override
	public List<OpChannelLink> queryLink(Integer channelId) {
		return getJdbcTemplate().query(QUERY_LINK, new Object[]{channelId},
				new RowMapper<OpChannelLink>() {

					@Override
					public OpChannelLink mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new OpChannelLink(
								rs.getInt("id"),
								rs.getString("channel_name"));
					}

			
		});
	}

	@Override
	public void saveLink(Integer channelId, Integer linkId, Integer serial) {
		getMasterJdbcTemplate().update(SAVE_LINK, new Object[]{channelId, linkId, serial});
	}
	
}
