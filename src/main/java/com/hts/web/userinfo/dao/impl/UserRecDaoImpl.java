package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserDynamicRec;
import com.hts.web.userinfo.dao.UserRecDao;

@Repository("HTSUserRecDao")
public class UserRecDaoImpl extends BaseDaoImpl implements UserRecDao {

	private static final String REC_INFO = "u0.id,u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar,u0.user_avatar_l,u0.star";
	
	/**
	 *　根据城市查询未关注的推荐用户总数
	 */
	private static final String QUERY_CITY_REC_COUNT = "select count(*) from " + HTS.USER_INFO + " u0"
			+ " where u0.id!=? and u0.city=? and NOT EXISTS "
			+ " (select concern_id from " + HTS.USER_CONCERN 
			+ " where u0.id=concern_id and valid=1 and user_id=?)";
	
	/**
	 * 根据城市查询未关注的推荐用户
	 */
	private static final String QUERY_CITY_REC = "select " + REC_INFO + " from " 
			+ HTS.USER_INFO + " u0" + " where u0.id!=? and u0.city=? and NOT EXISTS "
			+ " (select concern_id from " + HTS.USER_CONCERN 
			+ " where u0.id=concern_id and valid=1 and user_id=?)"
			+ " limit ?,1";
	
	/**
	 *　查询未关注推荐用户总数
	 */
	private static final String QUERY_PLAT_REC_COUNT = "select count(*) from " 
			+ HTS.USER_PLAT_CONCERN  + " pc" + " where pc.user_id=? and NOT EXISTS "
			+ " (select concern_id from " + HTS.USER_CONCERN 
			+ " where pc.plat_concern_id=concern_id and valid=1 and user_id=?)";
	
	/**
	 * 查询社交平台推荐
	 */
	private static final String QUERY_PLAT_REC = "select " + REC_INFO + " from " 
			+ HTS.USER_PLAT_CONCERN + " pc" + "," + HTS.USER_INFO + " as u0"
			+ " where pc.plat_concern_id=u0.id and pc.user_id=? and NOT EXISTS "
			+ " (select concern_id from " + HTS.USER_CONCERN 
			+ " where pc.plat_concern_id=concern_id and valid=1 and user_id=?)"
			+ " limit ?,1";
	
	
	/**
	 * 查询运营推荐的用户
	 */
	private static final String QUERY_OP_REC = "select " + REC_INFO + ",u0.signature" + " from " 
			+ HTS.OPERATIONS_USER_RECOMMEND + " as ur0, " + HTS.USER_INFO + " as u0"
			+ " where ur0.user_id=u0.id and ur0.user_accept=1 and ur0.sys_accept=1 and ur0.user_id!=? and NOT EXISTS"
			+ " (select concern_id from " + HTS.USER_CONCERN + " where u0.id=concern_id and valid=1 and user_id=?) "
					+ "ORDER BY u0.activity desc limit ?,?";
	
	
	
	/**
	 *　根据城市查询未关注的推荐用户总数
	 */
	private static final String QUERY_CONCERN_REC_COUNT = "select count(*) from " 
			+ HTS.USER_CONCERN + " ur0"
			+ " where ur0.user_id=? and ur0.concern_id!=? and ur0.valid=1 and NOT EXISTS "
			+ " (select concern_id from " + HTS.USER_CONCERN 
			+ " where ur0.concern_id=concern_id and valid=1 and user_id=?)";
	
	/**
	 * 根据城市查询未关注的推荐用户
	 */
	private static final String QUERY_CONCERN_REC = "select " + REC_INFO + " from " 
			+ HTS.USER_CONCERN + " ur0," + HTS.USER_INFO + " u0"
			+ " where u0.id=ur0.concern_id and ur0.user_id=? and ur0.concern_id!=?"
			+ " and ur0.valid=1 and NOT EXISTS "
			+ " (select concern_id from " + HTS.USER_CONCERN 
			+ " where ur0.concern_id=concern_id and valid=1 and user_id=?)"
			+ " limit ?,1";
	
	private static final String QUERY_LABEL_REC_COUNT = "select count(*) from " 
			+ HTS.USER_INFO_LABEL + " ul0"
			+ " where ul0.label_id=? and NOT EXISTS "
			+ " (select concern_id from " + HTS.USER_CONCERN 
			+ " where ul0.user_id=concern_id and valid=1 and user_id=?)";
	
	/**
	 * 根据城市查询未关注的推荐用户
	 */
	private static final String QUERY_LABEL_REC = "select " + REC_INFO + " from " 
			+ HTS.USER_INFO_LABEL + " ul0, " + HTS.USER_INFO + " u0"
			+ " where u0.id=ul0.user_id and ul0.label_id=? and NOT EXISTS "
			+ " (select concern_id from " + HTS.USER_CONCERN 
			+ " where ul0.user_id=concern_id and valid=1 and user_id=?)"
			+ " limit ?,1";
	
	@Override
	public long queryCityUserCount(Integer userId, String city) {
		return getJdbcTemplate().queryForLong(QUERY_CITY_REC_COUNT,
				new Object[]{userId, city, userId});
	}

	@Override
	public UserDynamicRec queryCityRecUser(Integer userId, String city, Integer start) {
		try {
		return getJdbcTemplate().queryForObject(QUERY_CITY_REC, 
				new Object[]{userId, city, userId, start},
				new RowMapper<UserDynamicRec>() {

					@Override
					public UserDynamicRec mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildUserDynamicRec(rs);
					}
		});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public long queryPlatRecCount(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_PLAT_REC_COUNT, 
				new Object[]{userId, userId});
	}

	@Override
	public UserDynamicRec queryPlatRec(Integer userId, Integer start) {
		return getJdbcTemplate().queryForObject(QUERY_PLAT_REC, 
				new Object[]{userId, userId, start},
				new RowMapper<UserDynamicRec>(){

					@Override
					public UserDynamicRec mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildUserDynamicRec(rs);
					}
		});
	}

	@Override
	public long queryConcernRecCount(Integer userId, Integer concernId) {
		return getJdbcTemplate().queryForLong(QUERY_CONCERN_REC_COUNT, 
				new Object[]{concernId, userId, userId});
	}

	@Override
	public UserDynamicRec queryConcernRec(Integer userId, Integer concernId,
			Integer start) {
		return getJdbcTemplate().queryForObject(QUERY_CONCERN_REC, 
				new Object[]{concernId, userId, userId, start},
				new RowMapper<UserDynamicRec>(){

					@Override
					public UserDynamicRec mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildUserDynamicRec(rs);
					}
		});
	}
	
	@Override
	public List<UserDynamicRec> queryOpRecList(Integer userId, int start, int limit) {
		return getJdbcTemplate().query(QUERY_OP_REC, new RowMapper<UserDynamicRec>() {

			@Override
			public UserDynamicRec mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserDynamicRec rec = buildUserDynamicRec(rs);
				rec.setSignature(rs.getString("signature"));
				return rec;
			}
		}, new Object[]{userId, userId, start, limit});
	}
	
	@Override
	public UserDynamicRec queryOpRec(Integer userId, int start) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_OP_REC, new RowMapper<UserDynamicRec>() {
	
				@Override
				public UserDynamicRec mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserDynamicRec rec = buildUserDynamicRec(rs);
					rec.setSignature(rs.getString("signature"));
					return rec;
				}
			}, new Object[]{userId, userId, start, 1});
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * 构建信息流推荐用户
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@Override
	public UserDynamicRec buildUserDynamicRec(ResultSet rs) throws SQLException {
		return new UserDynamicRec(
				rs.getInt("id"), 
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("star"));
	}

	@Override
	public long queryLabelRecCount(Integer userId, Integer labelId) {
		return 0;
	}

	@Override
	public UserDynamicRec queryLabelRec(Integer userId, Integer labelId) {
		return null;
	}

	
}
