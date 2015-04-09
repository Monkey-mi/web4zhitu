package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpSquare;
import com.hts.web.common.pojo.OpUserZombie;
import com.hts.web.operations.dao.UserZombieDao;

@Repository("HTSUserZombieDao")
public class UserZombieDaoImpl extends BaseDaoImpl implements UserZombieDao {
	
	private static String table = HTS.OPERATIONS_USER_ZOMBIE;
	
	private static final String ORDER_BY_UR_ID_DESC = " order by ur.id desc";
	
	/**
	 * 查询推荐用户
	 */
	private static final String QUERY_ZOMBIE_USER = "select ur.*,u.* from " + HTS.USER_INFO + " as u," + table + " as ur"
			+ " where u.id=ur.user_id"
			+ ORDER_BY_UR_ID_DESC;
	
	private static final String QUERY_ZOMBIE_USER_COUNT = "select count(*) from " + table;
	
	private static final String DELETE_BY_USER_ID = "delete from " + table + " where user_id=?";
	
	private static final String DELETE_BY_ID = "delete from " + table + " where id=?";
	
	private static final String SAVE_ZOMBIE_USER = "insert into " + table + " (user_id, recommender, recommend_date) values(?,?,?)";
	
	private static final String QUERY_RANDOM_ZOMBIE = "select uz1.user_id from " + table 
			+ " as uz1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM " +table+ ")-(SELECT MIN(id) FROM " 
			+ table + " ))+(SELECT MIN(id) FROM " 
			+ table + ") - ?) AS rand_id) AS uz2 WHERE uz1.id >= uz2.rand_id and uz1.shield=? ORDER BY uz1.id LIMIT ?";
	
	private static final String UPDATE_SHIELD = "update " + table + " set shield=? where user_id=?";
	
	private static final String QUERY_UN_SHIELD_ZOMBIE_UID = "select user_id from " + table + " where shield=?";
	
	/**
	 * 根据用户id查询主键
	 */
	private static final String QUERY_ID_BY_UID = "select id from " + table + " where user_id=?";

	@Override
	public List<OpUserZombie> queryZombieUser(
			RowSelection rowSelection) {
		return queryForPage(QUERY_ZOMBIE_USER, new Object[]{}, new RowMapper<OpUserZombie>() {

			@Override
			public OpUserZombie mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOperationsZombie(rs);
			}
		}, rowSelection);
	}

	@Override
	public long queryZombieUserCount() {
		return getJdbcTemplate().queryForLong(QUERY_ZOMBIE_USER_COUNT);
	}

	@Override
	public void saveZombieUser(OpSquare recommend) {
		getMasterJdbcTemplate().update(SAVE_ZOMBIE_USER, new Object[]{
			recommend.getTargetId(),
			recommend.getRecommender(),
			recommend.getRecommendDate()
		});
	}

	@Override
	public void deleteZombieUserByUserId(Integer userId) {
		getMasterJdbcTemplate().update(DELETE_BY_USER_ID, userId);
	}

	@Override
	public void deleteZombieUserById(Integer id) {
		getMasterJdbcTemplate().update(DELETE_BY_ID, id);
	}

	@Override
	public List<Integer> queryRandomZombieId(int limit) {
		return getJdbcTemplate().queryForList(QUERY_RANDOM_ZOMBIE, Integer.class, new Object[]{limit, Tag.FALSE, limit});
	}

	@Override
	public void updateShield(Integer userId, Integer shield) {
		getMasterJdbcTemplate().update(UPDATE_SHIELD, new Object[]{shield, userId});
	}

	@Override
	public List<Integer> queryUnShieldZombieUserId() {
		return getJdbcTemplate().queryForList(QUERY_UN_SHIELD_ZOMBIE_UID, Integer.class, new Object[]{Tag.FALSE});
	}
	
	@Override
	public Integer queryIdByUID(Integer uid) {
		return queryForObjectWithNULL(QUERY_ID_BY_UID, new Object[]{uid}, Integer.class);
	}
	
	/**
	 * 从结果及构建马甲信息
	 * @param rs
	 * @param hasRecommendDesc
	 * @return
	 * @throws SQLException
	 */
	public static OpUserZombie buildOperationsZombie(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new OpUserZombie(
				rs.getInt("id"), 
				rs.getInt("user_id"), 
				rs.getInt("platform_code"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("sex"),
				rs.getString("email"),
				rs.getString("address"),
				birthday,
				rs.getString("signature"),
				(Date)rs.getObject("register_date"),
				rs.getInt("phone_code"),
				rs.getInt("online"),
				rs.getInt("concern_count"),
				rs.getInt("follow_count"),
				rs.getInt("world_count"),
				rs.getInt("liked_count"),
				rs.getInt("keep_count"),
				rs.getInt("star"),
				rs.getInt("trust"),
				rs.getInt("shield"),
				rs.getString("recommender"),
				(Date)rs.getObject("recommend_date"),
				rs.getInt("platform_verify"));
	}


}
