package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.operations.dao.ChannelStarDao;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;

@Repository("HTSChannelStarDao")
public class ChannelStarDaoImpl extends BaseDaoImpl implements ChannelStarDao {
	
	private static String table = HTS.OPERATIONS_CHANNEL_STAR;
	
	private static String QUERY_STAR = "select s0.user_id," + U0_INFO + " from " 
			+ table + " s0," + HTS.USER_INFO + " u0"
			+ " where s0.user_id=u0.id and s0.channel_id=?"
			+ " order by s0.serial desc limit ?,?";
	
	@Override
	public List<UserInfoDto> queryStar(Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_STAR, 
				new Object[]{channelId, rowSelection.getFirstRow(), rowSelection.getMaxRow()}, 
				new RowMapper<UserInfoDto>() {

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				return UserInfoDaoImpl.buildUserInfoDtoByResult(
						rs.getInt("user_id"), rs);
			}
		});
	}

}
