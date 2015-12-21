package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.userinfo.dao.UserBackgroundDao;

/**
 * @author zhangbo	2015年12月21日
 *
 */
@Repository("com.hts.web.userinfo.dao.impl.UserBackgroundDaoImpl")
public class UserBackgroundDaoImpl extends BaseDaoImpl implements UserBackgroundDao {
	
	/**
	 * 用户背景图片表
	 */
	private static String table = HTS.USER_BACKGROUND;
	
	/**
	 * 根据用户id获取背景图片
	 */
	private static final String QUERY_BY_USERID = "select background from " + table + " where user_id=?";
	
	/**
	 * 记录用户背景图片
	 */
	private static final String SAVE_BACKGROUND = "insert into " + table + " (user_id, background) values (?,?)";
	
	/**
	 * 更新背景图片
	 */
	private static final String UPDATE_BACKGROUND = "update " + table + " set background=? where user_id=?";
	
	/**
	 * 更新背景图片
	 */
	private static final String DELETE_BACKGROUND =  DELETE_SINGLE_TABLE + table + " where user_id=?";

	@Override
	public String getUserBackgroundById(Integer userId) throws Exception {
		try {
			return getJdbcTemplate().queryForObject(QUERY_BY_USERID,
				new Object[]{userId}, new RowMapper<String>(){

				@Override
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("background");
				}
			});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void saveUserBackground(Integer userId, String background) throws Exception {
		getMasterJdbcTemplate().update(SAVE_BACKGROUND, new Object[]{userId, background});
	}

	@Override
	public void updateUserBackground(Integer userId, String background) throws Exception {
		getMasterJdbcTemplate().update(UPDATE_BACKGROUND, new Object[]{background, userId});
	}

	@Override
	public void deleteUserBackground(Integer userId) throws Exception {
		getMasterJdbcTemplate().update(DELETE_BACKGROUND, new Object[]{userId});
	}

}
