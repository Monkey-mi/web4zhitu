package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldSubtitleDto;
import com.hts.web.ztworld.dao.SubtitleDao;

@Repository("HTSSubtitleDao")
public class SubtitleDaoImpl extends BaseDaoImpl implements
		SubtitleDao {

	private static String table = HTS.HTWORLD_SUBTITLE;
	
	private static final String SUBTITLE_INFO = "subtitle, subtitle_en, trans_to";
	
	/**
	 *	查询字幕
	 */
	private static final String QUERY_SUBTITLE = "select " + SUBTITLE_INFO + " from "
			+ table + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大id查询字幕
	 */
	private static final String QUERY_SUBTITLE_BY_MDX_ID = "select " +  SUBTITLE_INFO + " from "
			+ table + ORDER_BY_SERIAL_DESC;
	
	@Override
	public List<HTWorldSubtitleDto> querySubtitleDto(RowSelection rowSelection) {
		return queryForPage(QUERY_SUBTITLE, new RowMapper<HTWorldSubtitleDto>(){

			@Override
			public HTWorldSubtitleDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildSubtitle(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<HTWorldSubtitleDto> querySubtitleDto(Integer maxId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_SUBTITLE_BY_MDX_ID, new Object[]{maxId},
				new RowMapper<HTWorldSubtitleDto>(){

			@Override
			public HTWorldSubtitleDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildSubtitle(rs);
			}
			
		}, rowSelection);
	}

	public HTWorldSubtitleDto buildSubtitle(ResultSet rs) throws SQLException {
		return new HTWorldSubtitleDto(
				rs.getInt("serial"),
				rs.getString("subtitle"),
				rs.getString("subtitle_en"),
				rs.getString("trans_to"));
	}
}
