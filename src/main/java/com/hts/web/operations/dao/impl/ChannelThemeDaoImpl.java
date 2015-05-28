package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpChannelTheme;
import com.hts.web.operations.dao.ChannelThemeDao;

@Repository("HTSChannelThemeDao")
public class ChannelThemeDaoImpl extends BaseDaoImpl implements ChannelThemeDao {

	private static String table = HTS.QUERY_CHANNEL_THEME;
	
	private static final String THEME_INFO = "t0.id,t0.theme_name";
	
	private static final String QUERY_ALL_THEME = "select " + THEME_INFO
			+ " from " + table + " t0 order by t0.serial desc";

	@Override
	public List<OpChannelTheme> queryAllTheme() {
		return getJdbcTemplate().query(QUERY_ALL_THEME, 
				new RowMapper<OpChannelTheme>() {

					@Override
					public OpChannelTheme mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildTheme(rs);
					}
		});
	}
	
	public OpChannelTheme buildTheme(ResultSet rs) throws SQLException {
		return new OpChannelTheme(
				rs.getInt("id"),
				rs.getString("theme_name"),
				Tag.TRUE);
	}
}
