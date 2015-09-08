package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpAdAppLink;
import com.hts.web.common.pojo.OpAdAppLinkDto;
import com.hts.web.operations.dao.AppLinkDao;

@Repository("HTSAppLinkDao")
public class AppLinkDaoImpl extends BaseDaoImpl implements AppLinkDao {

	private static String table = HTS.OPERATIONS_AD_APPLINK;

	private static final String ORDER_BY_SERIAL_DESC = " order by serial desc";
	
	private static final String QUERY_APP_LINK_DTO_HEAD = "select app_name, app_icon,app_icon_l,app_desc,short_link,serial from " + table 
			+ " where open=? and phone_code=?";
	
	private static final String QUERY_APP_LINK_DTO = QUERY_APP_LINK_DTO_HEAD + ORDER_BY_SERIAL_DESC;
	
	private static final String QUERY_APP_LINK_DTO_BY_MAX_SERIAL = QUERY_APP_LINK_DTO_HEAD + " and serial<=?" + ORDER_BY_SERIAL_DESC;
	
	private static final String QUERY_APP_LINK_BY_SHORT_LINK = "select * from " + table + " where short_link=?";
	
	private static final String ADD_CLICK_COUNT = "update " + table + " set click_count=click_count+1 where id=?";
	
	
	@Value("${appUrlPrefix}")
	private String appUrlPrefix = "http://api.imzhitu.com/APP";
	
	public String getAppUrlPrefix() {
		return appUrlPrefix;
	}

	public void setAppUrlPrefix(String appUrlPrefix) {
		this.appUrlPrefix = appUrlPrefix;
	}

	@Override
	public List<OpAdAppLinkDto> queryAppLinkDto(Integer open, Integer phoneCode, RowSelection rowSelection) {
		return queryForPage(QUERY_APP_LINK_DTO, new Object[]{open, phoneCode}, new RowMapper<OpAdAppLinkDto>(){

			@Override
			public OpAdAppLinkDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildAppLinkDtoByRs(rs);
			}
			
		},rowSelection);
	}

	@Override
	public List<OpAdAppLinkDto> queryAppLinkDto(Integer maxSerial, Integer open, Integer phoneCode,
			RowSelection rowSelection) {
		return queryForPage(QUERY_APP_LINK_DTO_BY_MAX_SERIAL, new Object[]{open, phoneCode, maxSerial}, new RowMapper<OpAdAppLinkDto>(){

			@Override
			public OpAdAppLinkDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildAppLinkDtoByRs(rs);
			}
			
		},rowSelection);
	}
	
	@Override
	public OpAdAppLink queryIdByShortLink(String shortLink) {
		return queryForObjectWithNULL(QUERY_APP_LINK_BY_SHORT_LINK, new Object[]{shortLink}, 
				new RowMapper<OpAdAppLink>() {

			@Override
			public OpAdAppLink mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildAppLinkByRs(rs);
			}
		});
	}
	

	@Override
	public void addClickCount(Integer id) {
		getMasterJdbcTemplate().update(ADD_CLICK_COUNT, new Object[]{id});
	}
	
	/**
	 * 根据结果集构建AppLinkDto
	 * 
	 * @param rs
	 * @throws SQLException 
	 */
	public OpAdAppLinkDto buildAppLinkDtoByRs(ResultSet rs) throws SQLException {
		return new OpAdAppLinkDto(
			rs.getInt("serial"),
			rs.getString("app_name"),
			rs.getString("app_icon"),
			rs.getString("app_icon_l"),
			rs.getString("app_desc"),
			appUrlPrefix + rs.getString("short_link")
		);
	}
	
	/**
	 * 根据结果集构建AppLink
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpAdAppLink buildAppLinkByRs(ResultSet rs) throws SQLException {
		OpAdAppLink link = new OpAdAppLink(
				rs.getInt("id"),
				rs.getString("app_name"),
				rs.getString("app_icon"),
				rs.getString("app_icon_l"),
				rs.getString("app_desc"),
				rs.getString("app_link"),
				rs.getString("short_link"),
				rs.getInt("phone_code"),
				rs.getInt("click_count"),
				rs.getInt("serial"),
				rs.getInt("open"));
		link.setUrl(appUrlPrefix + rs.getString("short_link"));
		return link;
	}


}
