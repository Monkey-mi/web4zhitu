package com.hts.web.userinfo.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserConcernType;
import com.hts.web.userinfo.dao.UserConcernTypeDao;

@Repository("HTSUserConcernTypeDao")
public class UserConcernTypeDaoImpl extends BaseDaoImpl implements
		UserConcernTypeDao {
	
	private static String table = HTS.USER_CONCERN_TYPE;
	
	/**
	 * 保存分类关注
	 */
	private static final String SAVE_CONCERN_TYPE = "insert into " + table 
			+ " (user_id, type_id, valid) values (?,?,?)";
	
	/**
	 * 查询分类关注
	 */
	private static final String QUERY_CONCERN_TYPE_ID = "select type_id from " 
			+ table + " where user_id=? and valid=?";
	
	/**
	 * 查询关注类型
	 */
	private static final String QUERY_CONCERN_TYPE = "select * from " + table 
			+ " where user_id=? and type_id=?";
	
	
	/**
	 * 更新分类关注有效性
	 */
	private static final String UPDATE_CONCERN_VALID = "update " + table 
			+ " set valid=? where user_id=? and type_id=?";

	@Override
	public void queryConcernType(Integer userId, final RowCallback<Integer> callback) {
		
		getJdbcTemplate().query(QUERY_CONCERN_TYPE_ID, new Object[]{userId, Tag.TRUE}, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("type_id"));
			}
		});
		
	}

	@Override
	public void saveConcernType(UserConcernType type) {
		getJdbcTemplate().update(SAVE_CONCERN_TYPE, new Object[]{
			type.getUserId(),
			type.getTypeId(),
			type.getValid()
		});
	}

	@Override
	public UserConcernType queryConcernType(Integer userId, Integer typeId) {
		return queryForObjectWithNULL(QUERY_CONCERN_TYPE, new Object[]{userId, typeId}, new RowMapper<UserConcernType>() {

			@Override
			public UserConcernType mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildUserConcernType(rs);
			}
		});
	}

	@Override
	public void updateConcernTypeValid(Integer userId, Integer typeId,
			Integer valid) {
		getJdbcTemplate().update(UPDATE_CONCERN_VALID, new Object[]{valid, userId, typeId});
	}
	
	/**
	 * 构建分类关注
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private UserConcernType buildUserConcernType(ResultSet rs) throws SQLException {
		return new UserConcernType(
				rs.getInt("user_id"),
				rs.getInt("type_id"),
				rs.getInt("valid"));
	}
	
}
