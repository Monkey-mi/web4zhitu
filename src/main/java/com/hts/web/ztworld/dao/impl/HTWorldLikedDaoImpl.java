package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldLiked;
import com.hts.web.common.pojo.HTWorldLikedInline;
import com.hts.web.common.pojo.HTWorldLikedUserDto;
import com.hts.web.ztworld.dao.HTWorldLikedDao;

/**
 * <p>
 * 织图世界喜欢数据访问对象
 * </p>
 * 
 * @author ztj　2013-7-4　2015-11-05
 *
 */
@Repository("HTSHTWorldLikedDao")
public class HTWorldLikedDaoImpl extends BaseDaoImpl implements HTWorldLikedDao{

	
	public static String table = HTS.HTWORLD_LIKED;
	
	private static final String INLINE_USER_INFO = "u0.user_avatar,"
			+ "u0.star,u0.platform_verify";
	
	private static final String INLINE_INFO = "l0.user_id,l0.world_id";
	
	private static final String LIKED_USER_INFO = "l0.id,l0.user_id,u0.user_name,"
			+ "u0.user_avatar,u0.user_avatar_l,u0.star";
	
	private static final String SAVE_LIKED = "insert into " + table 
			+ " (id,user_id,world_id) values (?,?,?)";
	
	private static final String DEL_LIKE = "delete from " + table
			+ " where user_id=? and world_id=?";
	
	private static final String QUERY_WORLD_LIKED_COUNT = "select count(*) from " + table 
			+ " where world_id=?";
	
	private static final String QUERY_INLINE = "(select " 
			+ INLINE_INFO + "," +INLINE_USER_INFO
			+ " from " + table + " l0," + HTS.USER_INFO + " u0"
			+ " where l0.user_id=u0.id and l0.world_id=? order by l0.id desc limit ?)";
	
	private static final String QUERY_INLINE_BY_WID = "select " 
			+ INLINE_INFO + "," +INLINE_USER_INFO
			+ " from " + table + " l0," + HTS.USER_INFO + " u0"
			+ " where l0.user_id=u0.id and l0.world_id=? order by l0.id desc limit ?";
	
	private static final String QUERY_LIKED_USER = "select " + LIKED_USER_INFO
			+ " from " + table + " l0," + HTS.USER_INFO + " u0"
			+ " where l0.user_id=u0.id and l0.world_id=?"
			+ " order by l0.id desc limit ?,?";
	
	private static final String QUERY_LIKED_USER_BY_MAX_ID = "select " + LIKED_USER_INFO
			+ " from " + table + " l0," + HTS.USER_INFO + " u0"
			+ " where l0.user_id=u0.id and l0.world_id=? and l0.id<=?"
			+ " order by l0.id desc limit ?,?";
	
	private static final String QUERY_LIKED_ID_BY_UID = "select world_id from " + table 
			+ " where user_id=? and world_id in ";

	private static final String IS_LIKED = "select 1 from " + table 
			+ " where user_id=? and world_id=?";
	
	/**
	 * 构建瀑布流内联喜欢用户信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private HTWorldLikedInline buildLikedInline(ResultSet rs) throws SQLException {
		return new HTWorldLikedInline(
				rs.getInt("user_id"),
				rs.getString("user_avatar"),
				rs.getInt("world_id"),
				rs.getInt("star"),
				rs.getInt("platform_verify"));
	}
	
	/**
	 * 构建喜欢织图的用户信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private HTWorldLikedUserDto buildLikedUserDto(ResultSet rs) throws SQLException {
		return new HTWorldLikedUserDto(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("star"));
	}
	
	
	@Override
	public Integer saveLiked(HTWorldLiked liked) {
		return save(SAVE_LIKED, new Object[]{
				liked.getId(),
				liked.getUserId(),
				liked.getWorldId(),
		});
	}
	
	@Override
	public void delLiked(Integer userId, Integer worldId) {
		getMasterJdbcTemplate().update(DEL_LIKE, userId, worldId);
	}
	
	@Override
	public long queryLikedCount(Integer worldId) {
		return getMasterJdbcTemplate().queryForLong(QUERY_WORLD_LIKED_COUNT, new Object[]{worldId, Tag.TRUE});
	}

	@Override
	public void queryInlineLikedByLimit(Integer[] worldIds,
			Integer limit, final RowCallback<HTWorldLikedInline> callback) {
		Object[] args = new Object[worldIds.length * 2];
		StringBuilder sqlBuilder = new StringBuilder();
		for(int i = 0; i < worldIds.length; i++) {
			if(i != 0) {
				sqlBuilder.append(" union all ");
			}
			sqlBuilder.append(QUERY_INLINE);
			int k = i * 2;
			args[k] = worldIds[i];
			args[k+1] = limit;
		}
		getJdbcTemplate().query(sqlBuilder.toString(), args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildLikedInline(rs));
			}
		});
	}
	
	@Override
	public void queryInlineLikedByLimit(Integer worldId,
			Integer limit, final RowCallback<HTWorldLikedInline> callback) {
		getJdbcTemplate().query(QUERY_INLINE_BY_WID, 
				new Object[]{worldId, limit}, 
				new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildLikedInline(rs));
			}
		});
	}
	
	@Override
	public List<HTWorldLikedUserDto> queryLikedUser(Integer worldId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LIKED_USER, 
				new Object[]{worldId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<HTWorldLikedUserDto>() {

					@Override
					public HTWorldLikedUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						return buildLikedUserDto(rs);
					}
		});
	}

	@Override
	public List<HTWorldLikedUserDto> queryLikedUser(Integer maxId, Integer worldId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LIKED_USER_BY_MAX_ID, 
				new Object[]{worldId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<HTWorldLikedUserDto>() {

					@Override
					public HTWorldLikedUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						return buildLikedUserDto(rs);
					}
			
		});
	}
	
	@Override
	public void queryLiked(Integer userId, Integer[] worldIds,
			final RowCallback<Integer> callback) {
		String inSelection = SQLUtil.buildInSelection(worldIds);
		String sql = QUERY_LIKED_ID_BY_UID + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(worldIds, new Object[]{userId}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("world_id"));
			}
		});
	}

	@Override
	public boolean isLiked(Integer userId, Integer worldId) {
		try {
			getJdbcTemplate().queryForInt(IS_LIKED, userId, worldId);
			return true;
		} catch(EmptyResultDataAccessException e) {
			return false;
		}
	}
}
