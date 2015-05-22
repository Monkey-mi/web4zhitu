package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldKeep;
import com.hts.web.ztworld.dao.HTWorldKeepDao;

/**
 * <p>
 * 织图世界收藏数据访问对象
 * </p>
 * 
 * 创建时间：2013-7-24
 * @author ztj
 *
 */
@Repository("HTSHTWorldKeepDao")
public class HTWorldKeepDaoImpl extends BaseDaoImpl implements HTWorldKeepDao{
	
	/**
	 * 表：织图世界收藏表
	 */
	public static String table = HTS.HTWORLD_KEEP;
	
	/**
	 * 保存织图收藏
	 */
	private static final String SAVE_KEEP = "insert into " + table + " (user_id,keep_date,world_id,world_author_id,ck,valid) values (?,?,?,?,?,?)";
	
	/**
	 * 查询织图收藏
	 */
	private static final String QUERY_KEEP = "select * from " + table + " where user_id=? and world_id=?";
	
	/**
	 * 查询织图收藏总数
	 */
	private static final String QUERY_KEEP_COUNT = "select count(*) from " + table + " where world_id=? and valid=?";
	
	/**
	 * 查询用户收藏织图总数
	 */
	private static final String QUERY_USER_KEEP_COUNT = "select count(*) from " + table + " where user_id=? and valid=1";
	
	/**
	 * 更新喜欢有效标志
	 */
	private static final String UPDATE_KEEP_VALID = "update " + table + " set valid=?, keep_date=? where user_id=? and world_id=?";
	
	/**
	 * 更新推送标记
	 */
	private static final String UPDATE_PUSHED = "update " + table + " set pushed=? where id=?";
	
	
	private static final String QUERY_OPERATIONS_KEEP_USER_COUNT_BY_WORLD_ID = "select count(*) from " 
			+ table + " as hk where hk.valid=? and hk.world_id=?"; 
	
	private static final String QUERY_OPERATIONS_KEEP_USER_COUNT_BY_WORLD_ID_AND_MAX_ID = QUERY_OPERATIONS_KEEP_USER_COUNT_BY_WORLD_ID
			+ " and hk.id<=?"; 
	
	/**
	 * 根据用户id查询其收藏的织图id
	 */
	private static final String QUERY_KEEP_WORLD_ID_BY_UID = "select world_id from " + table 
			+ " where user_id=? and valid=1 and world_id in ";
	
	@Override
	public HTWorldKeep queryKeep(Integer userId, Integer worldId) {
		return queryForObjectWithNULL(QUERY_KEEP, new RowMapper<HTWorldKeep>(){

			@Override
			public HTWorldKeep mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldKeep(
						rs.getInt("id"),
						rs.getInt("user_id"),
						(Date)rs.getObject("keep_date"), 
						rs.getInt("world_id"), 
						rs.getInt("world_author_id"),
						rs.getInt("ck"),
						rs.getInt("valid"));
			}
			
		}, new Object[] { userId, worldId });
	}
	
	
	@Override
	public Integer saveKeep(HTWorldKeep keep) {
		return save(SAVE_KEEP, new Object[]{
				keep.getUserId(),
				keep.getKeepDate(),
				keep.getWorldId(),
				keep.getWorldAuthorId(),
				keep.getCk(),
				keep.getValid()
			});
	}
	
	@Override
	public void updateKeep(Integer userId, Integer worldId, Integer valid, Date date) {
		getMasterJdbcTemplate().update(UPDATE_KEEP_VALID, new Object[]{valid, date, userId, worldId});
	}
	
	@Override
	public long queryKeepCount(Integer worldId) {
		return getMasterJdbcTemplate().queryForLong(QUERY_KEEP_COUNT, new Object[]{worldId, Tag.TRUE});
	}
	
	@Override
	public void updatePushed(Integer id, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_PUSHED, new Object[]{valid,id});
	}


	@Override
	public long queryOperationsKeepUserCountByWorldId(Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_OPERATIONS_KEEP_USER_COUNT_BY_WORLD_ID, new Object[]{
			Tag.TRUE,worldId
		});
	}


	@Override
	public long queryOperationsKeepUserCountByWorldIdAndMaxId(Integer maxId, Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_OPERATIONS_KEEP_USER_COUNT_BY_WORLD_ID_AND_MAX_ID, new Object[]{
			Tag.TRUE,worldId,maxId
		});
	}


	@Override
	public long queryUserKeepCount(Integer userId) {
		return getMasterJdbcTemplate().queryForLong(QUERY_USER_KEEP_COUNT, userId);
	}


	@Override
	public void queryKeep(Integer userId, Integer[] worldIds, final RowCallback<Integer> callback) {
		String inSelection = SQLUtil.buildInSelection(worldIds);
		String sql = QUERY_KEEP_WORLD_ID_BY_UID + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(worldIds, new Object[]{userId}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("world_id"));
			}
		});
	}

}
