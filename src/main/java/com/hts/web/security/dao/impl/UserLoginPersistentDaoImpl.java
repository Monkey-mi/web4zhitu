package com.hts.web.security.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.util.TimeUtil;
import com.hts.web.security.dao.UserLoginPersistentDao;

/**
 * <p>
 * AccessToken数据访问对象
 * </p>
 * 
 * 创建时间：2013-8-23
 * @author ztj
 *
 */
@Repository("HTSUserLoginPersistentDao")
public class UserLoginPersistentDaoImpl extends BaseDaoImpl implements
		UserLoginPersistentDao {

	private static Logger logger = Logger.getLogger(UserLoginPersistentDaoImpl.class);
	
	private static String table = HTS.USER_LOGIN_PERSISTENT;
	
	/**
	 * 保存Token
	 */
	private static final String SAVE_TOKEN = "insert into " + table 
			+ " (user_id, series, token, last_login) values (?,?,?,?)";
	
	/**
	 * 更新Token
	 */
	private static final String UPDATE_TOKEN = "update " + table 
			+ " set token=?,last_login=? where series=?";
	
	/**
	 * 根据series查询Token
	 */
	private static final String QUERY_TOKEN_BY_SERIES = "select * from " + table 
			+ " where series=?";
	
	/**
	 * 根据用户id删除Token
	 */
	private static final String DELETE_TOKEN_BY_USER_ID = "delete from " + table 
			+ " where user_id=?";
	
	/**
	 * 根据用户id查询Token
	 */
	private static final String QUERY_TOKEN_BY_USER_ID = "select * from " + table 
			+ " where user_id=?";
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		try {
			getMasterJdbcTemplate().update(SAVE_TOKEN, new Object[]{token.getUsername(),
					token.getSeries(), token.getTokenValue(), TimeUtil.getTimeLONG(token.getDate())});
		} catch (DuplicateKeyException e) {
			logger.warn("remember me error (uid=" + token.getUsername() + "): " + e.getMessage());
		}
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		getMasterJdbcTemplate().update(UPDATE_TOKEN, 
				new Object[]{tokenValue, TimeUtil.getTimeLONG(lastUsed), series});
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		return queryForObjectWithNULL(QUERY_TOKEN_BY_SERIES, new RowMapper<PersistentRememberMeToken>(){

			@Override
			public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Date date = new Date();
				date.setTime(rs.getLong("last_login"));
				return new PersistentRememberMeToken(
						String.valueOf(rs.getInt("user_id")),
						rs.getString("series"), 
						rs.getString("token"),
						date);
			}
			
		}, seriesId);
	}

	@Override
	public void removeUserTokens(String username) {
		getMasterJdbcTemplate().update(DELETE_TOKEN_BY_USER_ID, username);
	}

	@Override
	public PersistentRememberMeToken queryTokenByUserId(Integer userId) {
		return queryForObjectWithNULL(QUERY_TOKEN_BY_USER_ID, new RowMapper<PersistentRememberMeToken>(){
			@Override
			public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Date date = new Date();
				date.setTime(rs.getLong("last_login"));
				return new PersistentRememberMeToken(
						String.valueOf(rs.getInt("user_id")),
						rs.getString("series"), 
						rs.getString("token"),
						date);
			}
		}, userId);
	}

}
