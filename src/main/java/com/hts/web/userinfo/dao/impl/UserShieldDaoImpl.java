package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserShieldInfo;
import com.hts.web.userinfo.dao.UserShieldDao;

/**
 * <p>
 * 用户屏蔽数据访问接口
 * </p>
 * 
 * 创建时间：2014-9-15
 * @author tianjie
 *
 */
@Repository("HTSUserShieldDao")
public class UserShieldDaoImpl extends BaseDaoImpl implements UserShieldDao {

	private static String table = HTS.USER_SHIELD;
	
	private static final String SHIELD_INFO = "u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar_l,u0.address,u0.province,u0.city";
	
	/**
	 * 查询屏蔽用户id
	 */
	private static final String QUERY_SHIELD_ID = "select shield_id from " + table
			+ " where user_id=? and shield_id=?";
	
	/**
	 * 保存屏蔽
	 */
	private static final String SAVE_SHIELD = "insert into " + table
			+ " (user_id,shield_id,shield_date) values (?,?,?)";
	
	/**
	 * 删除屏蔽
	 */
	private static final String DELETE_SHIELD = "delete from " + table
			+ " where user_id=? and shield_id=?";
	
	/**
	 * 查询屏蔽用户信息
	 */
	private static final String QUERY_SHIELD_USER = "select su0.id,su0.shield_id," + SHIELD_INFO + " from ("
			+ " select id,shield_id from " + table + " where user_id=? order by id desc limit ?,?) as su0,"
			+ HTS.USER_INFO + " u0 where u0.id=su0.shield_id";
	
	/**
	 * 根据最大id查询屏蔽用户信息
	 */
	private static final String QUERY_SHIELD_USER_BY_MAX_ID = "select su0.id,su0.shield_id," + SHIELD_INFO + " from ("
			+ " select id,shield_id from " + table + " where user_id=? and id<=? order by id desc limit ?,?) as su0,"
			+ HTS.USER_INFO + " u0 where u0.id=su0.shield_id";

	@Override
	public Integer queryShieldId(Integer userId, Integer shieldId) {
		return queryForObjectWithNULL(QUERY_SHIELD_ID, new Object[]{userId, shieldId},
				Integer.class);
	}

	@Override
	public void saveShield(Integer userId, Integer shieldId, Date date) {
		getJdbcTemplate().update(SAVE_SHIELD, new Object[]{userId, shieldId, date});
	}

	@Override
	public void deleteShield(Integer userId, Integer shieldId) {
		getJdbcTemplate().update(DELETE_SHIELD, new Object[]{userId, shieldId});
	}

	@Override
	public List<UserShieldInfo> queryShieldUser(Integer userId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SHIELD_USER,
				new Object[]{userId,rowSelection.getFirstRow(),rowSelection.getLimit()},
				new RowMapper<UserShieldInfo>() {

			@Override
			public UserShieldInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildShieldInfo(rs);
			}
		});
	}

	@Override
	public List<UserShieldInfo> queryShieldUser(Integer maxId, Integer userId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SHIELD_USER_BY_MAX_ID, 
				new Object[]{userId,maxId,rowSelection.getFirstRow(),rowSelection.getLimit()},
				new RowMapper<UserShieldInfo>() {

			@Override
			public UserShieldInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildShieldInfo(rs);
			}
		});
	}

	public UserShieldInfo buildShieldInfo(ResultSet rs) throws SQLException {
		return new UserShieldInfo(
				rs.getInt("id"), 
				rs.getInt("shield_id"), 
				rs.getString("user_name"),
				rs.getString("user_avatar"), 
				rs.getString("user_avatar_l"), 
				rs.getString("address"),
				rs.getString("province"),
				rs.getString("city"));
	}
}
