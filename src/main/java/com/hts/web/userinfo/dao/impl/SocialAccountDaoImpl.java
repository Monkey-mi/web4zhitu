package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserSocialAccount;
import com.hts.web.common.pojo.UserSocialAccountDto;
import com.hts.web.userinfo.dao.SocialAccountDao;

/**
 * <p>
 * 用户社交账号绑定表
 * </p>
 * 
 * 创建时间：2013-6-21
 * @author ztj
 *
 */
@Repository("HTSSocialAccountDao")
public class SocialAccountDaoImpl extends BaseDaoImpl implements SocialAccountDao{

	/**
	 * 社交账号表
	 */
	private static final String table = HTS.USER_SOCIAL_ACCOUNT;
	
	/**
	 * 根据用户id查询其绑定的有效社交账号
	 */
	private static final String QUERY_SOCIAL_ACCOUNT_BY_USER_ID = "select * from " + table 
			+ " where user_id=?";
	
	/**
	 * 根据用户id查询其绑定的社交账号
	 */
	private static final String QUERY_SOCIAL_ACCOUNT_DTO = "select "
			+ "id, platform_code,platform_id,platform_name,platform_avatar,platform_avatar_l,"
			+ "platform_sign,platform_verify,platform_reason,user_id from " 
			+ table + " where user_id=? and valid=1";
	
	/**
	 * 查询用户指定关联社区的账号
	 */
	private static final String QUERY_SOCIAL_ACCOUNT_ID_BY_PLATFORM_CODE = "select * from " 
			+ table + " where platform_code=? and user_id=?";
	
	
	private static final String DELETE_BY_PLATFORM_CODE = "delete from " + table
			+ " where user_id=? and platform_code=?";
	
	/**
	 * 保存社交绑定账号
	 */
	private static final String SAVE_SOCIAL_ACCOUNT = "insert into " + table 
			+ "(platform_code,platform_id,platform_name,platform_avatar,platform_avatar_l,"
			+ "platform_token,platform_token_expires,platform_sign,platform_verify,platform_reason,user_id,valid) "
			+ "values (?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 更新社交绑定账号
	 */
	private static final String UPDATE_SOCIAL_ACCOUNT = "update " + table 
			+ " set user_id=?,platform_name=?," +
			"platform_avatar=?,platform_avatar_l=?,platform_token=?, platform_token_expires=?, "
			+ "platform_sign=?,platform_verify=?,platform_reason=? where id=?";
	
	/**
	 * 查询用户绑定的社交平台代号
	 */
	private static final String QUERY_SOCIAL_ACCOUNT_CODE = "select platform_code from " + table 
			+ " where user_id=?"; 
	
	/**
	 * 根据平台代号查询其绑定的有效社交账号
	 */
	private static final String QUERY_SOCIAL_ACCOUNT_BY_PLATFORM_CODE = "select * from " + table 
			+ " where user_id=? and platform_code in ";
	
	private static final String QUERY_USER_ID = "select * from " + table
			+ " where platform_id=? and platform_code=? limit 1";


	@Override
	public List<UserSocialAccount> querySocialAccountByUserId(Integer userId) {
		return getJdbcTemplate().query(QUERY_SOCIAL_ACCOUNT_BY_USER_ID, new Object[]{userId},
				new RowMapper<UserSocialAccount>() {

					@Override
					public UserSocialAccount mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildSocialAccountByResult(rs);
					}
			
		});
	}
	
	@Override
	public List<UserSocialAccountDto> querySocialAccountDtoByUserId(
			Integer userId) {
		return getJdbcTemplate().query(QUERY_SOCIAL_ACCOUNT_DTO, new Object[]{userId},
				new RowMapper<UserSocialAccountDto>() {

					@Override
					public UserSocialAccountDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildSocialAccountDto(rs);
					}
			
		});
	}

	@Override
	public UserSocialAccount querySocialAccountIdByPlatformCode(Integer userId, Integer platformCode) {
		return queryForObjectWithNULL(QUERY_SOCIAL_ACCOUNT_ID_BY_PLATFORM_CODE, 
				new RowMapper<UserSocialAccount>(){

			@Override
			public UserSocialAccount mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildSocialAccountByResult(rs);
			}
			
		}, new Object[]{platformCode,userId});
	}
	
	@Override
	public Integer saveSocialAccount(UserSocialAccount socialAccount) {
		return save(SAVE_SOCIAL_ACCOUNT, new Object[]{
			socialAccount.getPlatformCode(),
			socialAccount.getPlatformId(),
			socialAccount.getPlatformName(),
			socialAccount.getPlatformAvatar(),
			socialAccount.getPlatformAvatarL(),
			socialAccount.getPlatformToken(),
			socialAccount.getPlatformTokenExpires(),
			socialAccount.getPlatformSign(),
			socialAccount.getPlatformVerify(),
			socialAccount.getPlatformReason(),
			socialAccount.getUserId(),
			socialAccount.getValid()
		});
	}
	
	@Override
	public void udpateSocialAccount(UserSocialAccount socialAccount) {
		getJdbcTemplate().update(UPDATE_SOCIAL_ACCOUNT, new Object[]{
				socialAccount.getUserId(),
				socialAccount.getPlatformName(),
				socialAccount.getPlatformAvatar(),
				socialAccount.getPlatformAvatarL(),
				socialAccount.getPlatformToken(),
				socialAccount.getPlatformTokenExpires(),
				socialAccount.getPlatformSign(),
				socialAccount.getPlatformVerify(),
				socialAccount.getPlatformReason(),
				socialAccount.getId()
		});
	}
	
//	@Override
//	public void updateSocialAccountValid(Integer userId, Integer platformCode,
//			Integer valid) {
//		getJdbcTemplate().update(UPDATE_ACCOUNT_VALID, new Object[]{valid,userId,platformCode});
//		
//	}
	
	@Override
	public List<Integer> querySocialAccountCodeByUserId(Integer userId) {
		return getJdbcTemplate().queryForList(QUERY_SOCIAL_ACCOUNT_CODE, Integer.class, new Object[]{userId});
	}
	
	@Override
	public List<UserSocialAccount> querySocialAccountByPlatformCodes(
			Integer[] platformCodes, Integer userId) {
		String inSelection = SQLUtil.buildInSelection(platformCodes);
		String sql = new StringBuilder(QUERY_SOCIAL_ACCOUNT_BY_PLATFORM_CODE)
			.append(inSelection)
			.toString();
		Object[] args = SQLUtil.getArgsByInCondition(platformCodes, new Object[]{userId}, true);
		return getJdbcTemplate().query(sql, args, new RowMapper<UserSocialAccount>(){

			@Override
			public UserSocialAccount mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildSocialAccountByResult(rs);
			}
		});
	}
	
	@Override
	public UserSocialAccount queryUserId(String platformId, Integer platformCode) {
		return queryForObjectWithNULL(QUERY_USER_ID, new Object[]{platformId, platformCode},
				new RowMapper<UserSocialAccount>() {

					@Override
					public UserSocialAccount mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildSocialAccountByResult(rs);
					}
			
		});
	}
	
	@Override
	public void deleteByPlatformCode(Integer platformCode, Integer userId) {
		getJdbcTemplate().update(DELETE_BY_PLATFORM_CODE, new Object[]{userId, platformCode});
	}
	
	/**
	 * 根据元数据构建社交账号POJO
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public UserSocialAccount buildSocialAccountByResult(ResultSet rs) throws SQLException {
		return new UserSocialAccount(
				rs.getInt("id"),
				rs.getInt("platform_code"),
				rs.getString("platform_id"),
				rs.getString("platform_name"),
				rs.getString("platform_avatar"),
				rs.getString("platform_avatar_l"), 
				rs.getString("platform_token"), 
				rs.getLong("platform_token_expires"),
				rs.getString("platform_sign"),
				rs.getInt("platform_verify"),
				rs.getString("platform_reason"),
				rs.getInt("user_id"),
				rs.getInt("valid"));
	}
	
	/**
	 * 构建UserSocialAccountDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UserSocialAccountDto buildSocialAccountDto(ResultSet rs) throws SQLException {
		return new UserSocialAccountDto(
				rs.getInt("id"),
				rs.getInt("platform_code"),
				rs.getString("platform_id"),
				rs.getString("platform_name"),
				rs.getString("platform_avatar"),
				rs.getString("platform_avatar_l"), 
				rs.getString("platform_sign"),
				rs.getInt("platform_verify"),
				rs.getString("platform_reason"),
				rs.getInt("user_id"));
	}

}
