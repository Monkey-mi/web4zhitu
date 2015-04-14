package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldLikeMe;
import com.hts.web.common.pojo.HTWorldLiked;
import com.hts.web.common.pojo.HTWorldLikedUser;
import com.hts.web.common.pojo.HTWorldThumbDto;
import com.hts.web.common.pojo.HTWorldThumbnail;
import com.hts.web.common.pojo.UserConcernDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.userinfo.dao.impl.UserConcernDaoImpl;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldLikedDao;

/**
 * <p>
 * 织图世界喜欢数据访问对象
 * </p>
 * 
 * 创建时间：2013-7-4
 * @author ztj
 *
 */
@Repository("HTSHTWorldLikedDao")
public class HTWorldLikedDaoImpl extends BaseDaoImpl implements HTWorldLikedDao{

	private static final String ORDERY_BY_HL_ID_DESC = " order by hl.id desc ";
	
	/**
	 * 喜欢用户信息
	 */
	private static final String LIKED_USER_INFO = "u0.id,u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar_l,u0.star,u0.platform_verify";
	
	/**
	 *　喜欢我的用户信息
	 */
	private static final String LIKE_ME_INFO = "l0.id,l0.liked_date,l0.user_id,l0.world_id,u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar_l,u0.star,u0.platform_verify,w0.title_thumb_path";
	
	/**
	 * 织图喜欢表
	 */
	public static String table = HTS.HTWORLD_LIKED;
	
	/**
	 * 保存织图喜欢
	 */
	private static final String SAVE_LIKED = "insert into " + table 
			+ " (user_id,liked_date,world_id,world_author_id, ck,valid) values (?,?,?,?,?,?)";
	
	
	/**
	 * 更新喜欢有效标志
	 */
	private static final String UPDATE_LIKED_VALID = "update " + table 
			+ " set valid=?, liked_date=? where user_id=? and world_id=?";
	
	/**
	 * 更新推送标记
	 */
	private static final String UPDATE_PUSHED = "update " + table + " set pushed=? where id=?";
	
	/**
	 * 查询织图喜欢
	 */
	private static final String QUERY_LIKED = "select * from " + table 
			+ " where user_id=? and world_id=?";
	
	/**
	 * 查询织图喜欢总数
	 */
	private static final String QUERY_WORLD_LIKED_COUNT = "select count(*) from " + table 
			+ " where world_id=? and valid=?";
	
	/**
	 * 查询用户喜欢总数
	 */
	private static final String QUERY_USER_LIKE_COUNT = "select count(*) from " + table 
			+ " where user_id=? and valid=1";
	
	/**
	 * 更新未读喜欢信息
	 */
	private static final String UPDATE_UNREAD_USER_LIKED = "update " + table 
			+ " set ck=? where ck=? and valid=? and world_author_id=?";
	
	/**
	 * 查询用户喜欢消息
	 */
	private static final String QUERY_USER_LIKED = "select hl.*," + WORLD_THUMB + "," + U0_INFO 
			+ " from " + table + " as hl, " + HTS.USER_INFO + " as u0, " +  HTS.HTWORLD_HTWORLD 
			+ " as h where hl.user_id=u0.id and hl.world_id=h.id and hl.valid=? and hl.world_author_id=?"
			+ ORDERY_BY_HL_ID_DESC;
	
	/**
	 * 根据最大id查询用户喜欢消息
	 */
	private static final String QUERY_USER_LIKED_BY_MAX_ID = "select hl.*," + WORLD_THUMB + "," + U0_INFO 
			+ " from " + table + " as hl, " + HTS.USER_INFO + " as u0, " +  HTS.HTWORLD_HTWORLD 
			+ " as h where hl.user_id=u0.id and hl.world_id=h.id and hl.valid=? and hl.world_author_id=? and hl.id<=?"
			+ ORDERY_BY_HL_ID_DESC;
	
	/**
	 * 根据最小id查询用户喜欢消息
	 */
	private static final String QUERY_USER_LIKED_BY_MIN_ID = "select hl.*," + WORLD_THUMB + "," + U0_INFO 
			+ " from " + table + " as hl, " + HTS.USER_INFO + " as u0, " +  HTS.HTWORLD_HTWORLD 
			+ " as h where hl.user_id=u0.id and hl.world_id=h.id and hl.valid=? and hl.world_author_id=? and hl.id>?"
			+ ORDERY_BY_HL_ID_DESC;
	
	/**
	 * 查询用户喜欢消息总数
	 */
	private static final String QUERY_UNCHECK_USER_LIKED_COUNT = "select count(*) from " + table 
			+ " where valid=? and ck=? and world_author_id=?";
	
	/**
	 * 根据最大id查询用户喜欢消息总数
	 */
	private static final String QUERY_USER_LIKED_COUNT_BY_MAX_ID = "select count(*) from " + table 
			+ " where valid=? and world_author_id=? and id<=?";
	
	/**
	 * 根据最小id查询用户喜欢消息总数
	 */
	private static final String QUERY_USER_LIKED_COUNT_BY_MIN_ID = "select count(*) from " + table 
			+ " where valid=? and world_author_id=? and id>?";
	
	private static final String QUERY_OPERATIONS_LIKED_USER_COUNT_BY_WORLD_ID = "select count(*) from " 
			+ table + " as hl where hl.valid=? and hl.world_id=?"; 
	
	
	private static final String QUERY_OPERATIONS_LIKED_USER_COUNT_BY_WORLD_ID_AND_MAX_ID = QUERY_OPERATIONS_LIKED_USER_COUNT_BY_WORLD_ID
			+ " and hl.id<=?"; 
	
	/**
	 * 查询最新喜欢SQL头部
	 */
	private static final String QUERY_LIKED_USER_INFO_HEAD = "select " + LIKED_USER_INFO 
			+ ",hl.world_id,hl.user_id from " + HTS.USER_INFO + " as u0, (";
	
	/**
	 * 查询最新喜欢SQL中部
	 */
	private static final String QUERY_LIKED_USRE_INFO_MAIN = "(select world_id,user_id from " 
			+ table + " where world_id=? and valid=? order by id desc limit ?)";
	
	/**
	 * 查询最新喜欢SQL尾部
	 */
	private static final String QUERY_LIKED_USER_INFO_FOOT = " ) as hl where u0.id=hl.user_id";
	
	/**
	 * 根据织图id查询最新喜欢
	 */
	private static final String QUERY_LIKED_USER_INFO_BY_WID = "select " + LIKED_USER_INFO 
			+ ",hl.world_id,hl.user_id from " 
			+ table + " as hl," + HTS.USER_INFO 
			+ " as u0 where u0.id=hl.user_id and hl.world_id=? and hl.valid=? order by id desc limit ?";
	
	
	/**
	 * 查询喜欢指定织图的用户信息
	 */
	public static final String QUERY_LIKED_USER_WITH_JOIN  = "select uc.*,uc1.* from (select " + UserConcernDaoImpl.CONCERN_USER_INFO + ",uc0.* from " + HTS.HTWORLD_LIKED
			+ " as uc0, " + HTS.USER_INFO + " as u"
			+ " where uc0.user_id = u.id and uc0.world_id=? and uc0.valid=?) as uc"
			+ " left join " + HTS.USER_CONCERN +  " as uc1 on uc.user_id = uc1.concern_id and uc1.user_id=? and uc1.valid=?" + UserConcernDaoImpl.ORDER_BY_UC_ID_DESC;
	
	/**
	 * 查询喜欢指定织图的用户信息
	 */
	public static final String QUERY_LIKED_USER_WITH_JOIN_BY_MAX_ID  = "select uc.*,uc1.* from (select " + UserConcernDaoImpl.CONCERN_USER_INFO + ",uc0.* from " + HTS.HTWORLD_LIKED
			+ " as uc0, " + HTS.USER_INFO + " as u"
			+ " where uc0.user_id = u.id and uc0.world_id=? and uc0.valid=? and uc0.id<=?) as uc"
			+ " left join " + HTS.USER_CONCERN +  " as uc1 on uc.user_id = uc1.concern_id and uc1.user_id=? and uc1.valid=?" + UserConcernDaoImpl.ORDER_BY_UC_ID_DESC;
	
	
	/**SELECT * FROM 
	 * 查询用户喜欢指定用户的织图
	 */
	public static final String QUERY_LIKED_WORLD = "select h.id, h.title_thumb_path from ("
			+ "select world_id from " + table + " where user_id=? and world_author_id=? and valid=1 order by id desc limit ?) as hl,"
			+ HTS.HTWORLD_HTWORLD + " as h where hl.world_id=h.id"; 
	
	/**
	 * 查询用户喜欢指定用户的织图总数
	 */
	public static final String QUERY_LIKED_WORLD_COUNT = "select count(*) from " + table 
			+ " where user_id=? and world_author_id=? and valid=1";
	
	/**
	 * 根据用户id查询其喜欢的织图id
	 */
	private static final String QUERY_LIKED_WORLD_ID_BY_UID = "select world_id from " + table 
			+ " where user_id=? and valid=1 and world_id in ";
	
	/**
	 * 查询被赞次数
	 */
	private static final String QUERY_LIKE_ME_COUNT_BY_WUID = "select count(*) from " + table 
			+ " where world_author_id=? and valid=1";

	/**
	 * 查询喜欢我的用户信息
	 */
	private static final String QUERY_LIKE_ME = "select " + LIKE_ME_INFO + " from "
			+ table + " as l0," + HTS.USER_INFO + " u0, " + HTS.HTWORLD_HTWORLD + " as w0"
			+ " where l0.user_id=u0.id and l0.world_id=w0.id and l0.valid=1 and l0.world_author_id=?"
			+ " order by l0.id desc limit ?,?";
	
	/**
	 * 查询喜欢我的用户信息
	 */
	private static final String QUERY_LIKE_ME_BY_MAXID = "select " + LIKE_ME_INFO + " from "
			+ table + " as l0," + HTS.USER_INFO + " u0, " + HTS.HTWORLD_HTWORLD + " as w0"
			+ " where l0.user_id=u0.id and l0.world_id=w0.id and l0.valid=1 and l0.world_author_id=?"
			+ " and l0.id<=? order by l0.id desc limit ?,?";
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Override
	public HTWorldLiked queryLiked(Integer userId, Integer worldId) {
		return queryForObjectWithNULL(QUERY_LIKED, new RowMapper<HTWorldLiked>(){

			@Override
			public HTWorldLiked mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldLiked(rs);
			}
			
		}, new Object[] { userId, worldId });
	}
	
	@Override
	public Integer saveLiked(HTWorldLiked liked) {
		return save(SAVE_LIKED, new Object[]{
				liked.getUserId(),
				liked.getLikedDate(),
				liked.getWorldId(),
				liked.getWorldAuthorId(),
				liked.getCk(),
				liked.getValid()
		});
	}
	
	@Override
	public void updateLiked(Integer userId, Integer worldId, Integer valid, Date date) {
		getMasterJdbcTemplate().update(UPDATE_LIKED_VALID, new Object[]{valid, date, userId, worldId});
	}
	
	
	@Override
	public long queryLikedCount(Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_WORLD_LIKED_COUNT, new Object[]{worldId, Tag.TRUE});
	}

	@Override
	public void updatePushed(Integer id, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_PUSHED, new Object[]{valid, id});
	}

	@Override
	public List<HTWorldLiked> queryUserLiked(Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_USER_LIKED, new Object[]{Tag.TRUE, userId}, new RowMapper<HTWorldLiked>() {

			@Override
			public HTWorldLiked mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldLikedWithUserAndThumb(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<HTWorldLiked> queryUserLikedByMaxId(Integer userId, Integer maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_USER_LIKED_BY_MAX_ID, new Object[]{Tag.TRUE, userId, maxId}, new RowMapper<HTWorldLiked>() {

			@Override
			public HTWorldLiked mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldLikedWithUserAndThumb(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<HTWorldLiked> queryUserLikedByMinId(Integer userId, Integer minId, RowSelection rowSelection) {
		return queryForPage(QUERY_USER_LIKED_BY_MIN_ID, new Object[]{Tag.TRUE, userId, minId}, new RowMapper<HTWorldLiked>() {

			@Override
			public HTWorldLiked mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldLikedWithUserAndThumb(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public long queryUnCheckUserLikedCount(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_UNCHECK_USER_LIKED_COUNT, new Object[]{Tag.TRUE, Tag.FALSE, userId});
	}
	
	@Override
	public long queryUserLikedCountByMaxId(Integer userId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_LIKED_COUNT_BY_MAX_ID, new Object[]{Tag.TRUE, userId, maxId});
	}
	
	@Override
	public long queryUserLikedCountByMinId(Integer userId, Integer minId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_LIKED_COUNT_BY_MIN_ID, new Object[]{Tag.TRUE, userId, minId});
	}
	
	@Override
	public void updateUnreadUserLiked(Integer userId) {
		getMasterJdbcTemplate().update(UPDATE_UNREAD_USER_LIKED, new Object[]{Tag.TRUE, Tag.FALSE, Tag.TRUE, userId});
	}

	@Override
	public long queryOperationsLikedUserCountByWorldId(Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_OPERATIONS_LIKED_USER_COUNT_BY_WORLD_ID, new Object[]{
			Tag.TRUE,worldId
		});
	}


	@Override
	public long queryOperationsLikedUserCountByWorldIdAndMaxId(Integer maxId, Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_OPERATIONS_LIKED_USER_COUNT_BY_WORLD_ID_AND_MAX_ID, new Object[]{
			Tag.TRUE,worldId,maxId
		});
	}
	
	@Override
	public void queryLikedUserByLimit(Integer[] worldIds,
			Integer limit, final RowCallback<HTWorldLikedUser> callback) {
		Object[] args = new Object[worldIds.length * 3];
		StringBuilder sqlBuilder = new StringBuilder(QUERY_LIKED_USER_INFO_HEAD);
		for(int i = 0; i < worldIds.length; i++) {
			if(i != 0) {
				sqlBuilder.append(" union all ");
			}
			sqlBuilder.append(QUERY_LIKED_USRE_INFO_MAIN);
			int k = i * 3;
			args[k] = worldIds[i];
			args[k+1] = Tag.TRUE;
			args[k+2] = limit;
		}
		sqlBuilder.append(QUERY_LIKED_USER_INFO_FOOT);
		getJdbcTemplate().query(sqlBuilder.toString(), args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldLikedUser user = buildHTWorldLikedUser(rs);
				callback.callback(user);
			}
		});
	}
	
	@Override
	public void queryLikedUserByLimit(Integer worldId,
			Integer limit, final RowCallback<HTWorldLikedUser> callback) {
		getJdbcTemplate().query(QUERY_LIKED_USER_INFO_BY_WID, 
				new Object[]{worldId, Tag.TRUE, limit}, 
				new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldLikedUser user = buildHTWorldLikedUser(rs);
				callback.callback(user);
			}
		});
	}
	
	@Override
	public List<UserConcernDto> queryLikedUser(Integer worldId, Integer userId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_LIKED_USER_WITH_JOIN, new Object[]{worldId, Tag.TRUE, userId, Tag.TRUE}, 
				new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int num)
					throws SQLException {
				return UserConcernDaoImpl.buildUserConcernDtoByResultSet(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<UserConcernDto> queryLikedUser(Integer maxId, Integer worldId,
			Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_LIKED_USER_WITH_JOIN_BY_MAX_ID, new Object[]{worldId, Tag.TRUE, maxId, userId, Tag.TRUE}, 
				new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int num)
					throws SQLException {
				return UserConcernDaoImpl.buildUserConcernDtoByResultSet(rs);
			}
		}, rowSelection);
	}

	@Override
	public long queryUserLikeCount(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_LIKE_COUNT, userId);
	}

	
	@Override
	public List<HTWorldThumbnail> queryLikeOthersWorldThumbnail(Integer limit,
			Integer userId, Integer authorId) {
		return getJdbcTemplate().query(QUERY_LIKED_WORLD, new Object[]{userId, authorId, limit},
				new RowMapper<HTWorldThumbnail>() {

					@Override
					public HTWorldThumbnail mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new HTWorldThumbnail(rs.getInt("id"), 
								rs.getString("title_thumb_path"));
					}
		});
	}
	
	@Override
	public long queryLikeOthersWorldCount(Integer userId, Integer authorId) {
		return getJdbcTemplate().queryForLong(QUERY_LIKED_WORLD_COUNT, 
				new Object[]{userId, authorId});
	}

	
	
	/**
	 * 从结果集构建HTWorldLiked
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public HTWorldLiked buildHTWorldLiked(ResultSet rs) throws SQLException {
		return new HTWorldLiked(
				rs.getInt("id"), 
				rs.getInt("user_id"),
				(Date)rs.getObject("liked_date"), 
				rs.getInt("world_id"), 
				rs.getInt("world_author_id"),
				rs.getInt("ck"),
				rs.getInt("valid"));
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldLikedUser buildHTWorldLikedUser(ResultSet rs) throws SQLException {
		return new HTWorldLikedUser(
				rs.getInt("id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("world_id"),
				rs.getInt("star"),
				rs.getInt("platform_verify"));
	}
	
	/**
	 * 从结果集构建HTWorldLiked
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public HTWorldLiked buildHTWorldLikedWithUserAndThumb(ResultSet rs) throws SQLException {
		HTWorldLiked dto = buildHTWorldLiked(rs);
		UserInfoDto user = UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getUserId(), rs);
		dto.setUserInfo(user);
		HTWorldThumbDto thumb = worldDao.buildHTWorldThumbDtoByResultSet(dto.getWorldId(), rs);
		dto.setHtworld(thumb);
		return dto;
	}

	@Override
	public void queryLiked(Integer userId, Integer[] worldIds,
			final RowCallback<Integer> callback) {
		String inSelection = SQLUtil.buildInSelection(worldIds);
		String sql = QUERY_LIKED_WORLD_ID_BY_UID + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(worldIds, new Object[]{userId}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(rs.getInt("world_id"));
			}
		});
	}

	@Override
	public long queryLikeMeCount(Integer worldAuthorId) {
		return getJdbcTemplate().queryForLong(QUERY_LIKE_ME_COUNT_BY_WUID, worldAuthorId);
	}

	@Override
	public List<HTWorldLikeMe> queryLikeMe(Integer userId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LIKE_ME, 
				new Object[]{userId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<HTWorldLikeMe>(){

					@Override
					public HTWorldLikeMe mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildLikeMe(rs);
					}
		});
	}

	@Override
	public List<HTWorldLikeMe> queryLikeMe(Integer maxId, Integer userId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LIKE_ME_BY_MAXID, 
				new Object[]{userId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<HTWorldLikeMe>(){

					@Override
					public HTWorldLikeMe mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildLikeMe(rs);
					}
		});
	}
	
	public HTWorldLikeMe buildLikeMe(ResultSet rs) throws SQLException {
		return new HTWorldLikeMe(
				rs.getInt("id"),
				(Date)rs.getObject("liked_date"),
				rs.getInt("user_id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("star"),
				rs.getInt("platform_verify"),
				rs.getInt("world_id"),
				rs.getString("title_thumb_path"));
	}

}
