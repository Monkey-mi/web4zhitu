package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldCommentReId;
import com.hts.web.common.pojo.HTWorldCommentUser;
import com.hts.web.common.pojo.HTWorldThumbDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.util.CollectionUtil;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;
import com.hts.web.ztworld.dao.HTWorldCommentDao;
import com.hts.web.ztworld.dao.HTWorldDao;

/**
 * <p>
 * 织图评论数据访问对象
 * </p>
 * 
 * 创建时间：2013-8-9
 * @author ztj
 *
 */
@Repository("HTSHTWorldCommentDao")
public class HTWorldCommentDaoImpl extends BaseDaoImpl implements
		HTWorldCommentDao {
	
	private static final String table = HTS.HTWORLD_COMMENT;
	
	private static final String ORDER_BY_HC_ID_DESC = " order by hc.id desc";
	
	/**
	 * 评论用户信息
	 */
	private static final String COMMENT_USER_INFO = "u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar_l,u0.star,u0.platform_verify";
	
	/**
	 * 保存织图评论
	 * 
	 */
	private static final String SAVE_COMMENT = "insert into " + HTS.HTWORLD_COMMENT 
			+ "(id, author_id, content, comment_date, world_id, world_author_id, re_id," 
			+ "re_author_id, ck, valid, shield) values (?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 根据id查询评论
	 */
	private static final String QUERY_COMMENT_BY_ID = "select * from " + HTS.HTWORLD_COMMENT + " where id=?";
	
	/**
	 * 根据id查询评论数据传输对象
	 */
	private static final String QUERY_COMMENT_DTO_BY_ID = "select hc.*, " + U0_INFO + " from " 
			+ HTS.HTWORLD_COMMENT + " as hc, "  + HTS.USER_INFO + " as u0" 
			+ " where hc.author_id=u0.id and hc.id=?";
	
	/**
	 * 查询织图评论SQL公用头部
	 */
	private static final String QUERY_COMMENT_HEADER = "select hc.*, " + U0_INFO + " from " 
			+ HTS.HTWORLD_COMMENT + " as hc, "  + HTS.USER_INFO + " as u0" 
			+ " where hc.author_id=u0.id and hc.world_id=? and hc.valid=? and hc.shield=?";
	/**
	 * 查询织图评论
	 */
	private static final String QUERY_COMMENT = QUERY_COMMENT_HEADER + ORDER_BY_HC_ID_DESC;
	
	/**
	 * 根据最大id查询织图评论
	 */
	private static final String QUERY_COMMENT_BY_MAX_ID = QUERY_COMMENT_HEADER 
			+ " and hc.id<=?" + ORDER_BY_HC_ID_DESC;
	
	/**
	 * 根据最小id查询织图评论
	 */
	private static final String QUERY_COMMENT_BY_MIN_ID =  QUERY_COMMENT_HEADER 
			+ " and hc.id>?" + ORDER_BY_HC_ID_DESC;
	
	/**
	 * 查询织图评论总数
	 */
	private static final String QUERY_WORLD_COMMENTED_COUNT = "select count(*) from " 
			+ HTS.HTWORLD_COMMENT + " where world_id=? and valid=? and shield=?";
	
	/**
	 * 根据最大id查询织图评论总数
	 */
	private static final String QUERY_WORLD_COMMENTED_COUNT_BY_MAX_ID = QUERY_WORLD_COMMENTED_COUNT 
			+ " and id<=?";
	
	/**
	 * 根据最小id查询织图评论总数
	 */
	private static final String QUERY_WORLD_COMMENT_COUNT_BY_MIN_ID = QUERY_WORLD_COMMENTED_COUNT 
			+ " and id>?";
	
	/**
	 * 查询评论总数SQL头部
	 */
	private static final String QUERY_COMMENT_COUNT_HEADER = "select count(*) from " 
			+ HTS.HTWORLD_COMMENT + " as hc," + HTS.USER_INFO + " as u0"
			+" where hc.author_id=u0.id";
	
	/**
	 * 查询评论总数SQL尾部
	 */
	private static final String QUERY_COMMENT_COUNT_FOOT = " and hc.valid=?";
	
	/**
	 * 根据最大id查询评论总数SQL尾部
	 */
	private static final String QUERY_COMMENT_COUNT_BY_MAX_ID_FOOT = " and hc.valid=? and hc.id<=?";
	
	/**
	 * 根据最小id查询评论总数SQL尾部
	 */
	private static final String QUERY_COMMENT_COUNT_BY_MIN_ID_FOOT = " and hc.valid=? and hc.id>?";
	
	/**
	 * 更新评论屏蔽标记
	 */
	private static final String UPDATE_COMMENT_SHIELD = "update " + HTS.HTWORLD_COMMENT 
			+ " set shield=? where id=?";
	
	/**
	 * 更新推送标记
	 */
	private static final String UPDATE_PUSHED = "update " + HTS.HTWORLD_COMMENT 
			+ " set pushed=? where id=?";
	
	/**
	 * 更新指定用户的未读评论消息
	 */
	private static final String UPDATE_UNREAD_USER_COMMENT = "update " + HTS.HTWORLD_COMMENT 
			+ " set ck=? where ck=? and valid=? and shield=? and (world_author_id=? or re_author_id=?)";
	
	/**
	 * 查询指定用户的评论消息
	 */
	private static final String QUERY_USER_COMMENT = "select hc.*," + WORLD_THUMB + ","+ U0_INFO + " from " +
			HTS.HTWORLD_COMMENT + " as hc, " + HTS.USER_INFO + " as u0, " + HTS.HTWORLD_HTWORLD + " as h"
			+ " where hc.author_id=u0.id and hc.world_id=h.id and hc.valid=? and hc.shield=? and (hc.world_author_id=? or hc.re_author_id=?)"
			+ ORDER_BY_HC_ID_DESC;
	
	/**
	 * 根据最大id查询指定用户的评论消息
	 */
	private static final String QUERY_USER_COMMENT_BY_MAX_ID = "select hc.*," + WORLD_THUMB + "," + U0_INFO + " from " +
			HTS.HTWORLD_COMMENT + " as hc, " + HTS.USER_INFO + " as u0, " + HTS.HTWORLD_HTWORLD + " as h"
			+ " where hc.author_id=u0.id and hc.world_id=h.id and hc.valid=? and hc.shield=? and (hc.world_author_id=? or hc.re_author_id=?)  and hc.id<=?"
			+ ORDER_BY_HC_ID_DESC;
	
	/**
	 * 根据最小id查询指定用户的评论消息
	 */
	private static final String QUERY_USER_COMMENT_BY_MIN_ID = "select hc.*," + WORLD_THUMB + "," + U0_INFO + " from " +
			HTS.HTWORLD_COMMENT + " as hc, " + HTS.USER_INFO + " as u0, " + HTS.HTWORLD_HTWORLD + " as h"
			+ " where hc.author_id=u0.id and hc.world_id=h.id and hc.valid=? and hc.shield=? and (hc.world_author_id=? or hc.re_author_id=?) and hc.id>?"
			+ ORDER_BY_HC_ID_DESC;
	
	/**
	 * 查询指定用户的未读评论消息总数
	 */
	private static final String QUERY_UNCHECK_USER_COMMENT_COUNT = "select count(*) from " 
			+ HTS.HTWORLD_COMMENT + " where valid=? and shield=? and ck=? and (world_author_id=? or re_author_id=?)";
	
	/**
	 * 根据最大id查询指定用户的评论消息总数
	 */
	private static final String QUERY_USER_COMMENT_COUNT_BY_MAX_ID = "select count(*) from " +
			HTS.HTWORLD_COMMENT + " where valid=? and shield=? and (world_author_id=? or re_author_id=?) and id<=?";
	
	/**
	 * 根据最小id查询指定用户的评论消息总数
	 */
	private static final String QUERY_USER_COMMENT_COUNT_BY_MIN_ID = "select count(*) from " +
			HTS.HTWORLD_COMMENT + " where valid=? and shield=? and (world_author_id=? or re_author_id=?) and id<=?";
	
	/**
	 * 查询最新评论SQL头部
	 */
	private static final String QUERY_COMMENT_USER_INFO_HEAD = "select " + COMMENT_USER_INFO 
		+ ",hc.* from " + HTS.USER_INFO + " as u0, (";
	
	/**
	 * 查询最新评论SQL中部
	 */
	private static final String QUERY_COMMENT_USRE_INFO_MAIN = "(select id,re_id,content,comment_date,world_id,author_id from " 
			+ table + " where world_id=? and valid=? and shield=? order by id desc limit ?)";
	
	/**
	 * 查询最新评论SQL尾部
	 */
	private static final String QUERY_COMMENT_USER_INFO_FOOT = " ) as hc where u0.id=hc.author_id";
	
	/**
	 * 根据织图id查询最新评论
	 */
	private static final String QUERY_COMMENT_USER_INFO_BY_WID = "select " + COMMENT_USER_INFO 
			+ ",hc.id,hc.re_id,hc.content,hc.comment_date,hc.world_id,author_id from " 
			+ table + " as hc," + HTS.USER_INFO 
			+ " as u0 where u0.id=hc.author_id and hc.world_id=? and hc.valid=? and hc.shield=? order by id desc limit ?";
	
	/**
	 * 根据id来更新ck位
	 */
	private static final String UPDATE_CK_BY_ID = "update " + table 
			+ " set ck=? where id=?";
	
	/**
	 * 根据id更新valid
	 */
	private static final String UPDATE_VALID_BY_IDS = " update " + table 
			+ " set valid=1 where id in ";
	
	/**
	 * 根据id来修改评论内容
	 */
	private static final String UPDATE_CONTENT_BY_ID = " update " + table 
			+ " set content=? where id=?";
	
	/**
	 * 根据id来修改时间comment_date
	 */
	private static final String UPDATE_COMMENT_DATE_BY_ID = " update " + table 
			+ " set comment_date=? where id=?";
			
	/**
	 * 查询回复id
	 */
	private static final String QUERY_RE_IDS = "select id, re_id from " + table
			+ " where id in ";
	
	@Autowired
	private HTWorldDao worldDao;
	

	@Override
	public void updateCkById(Integer ck,Integer id){
		getMasterJdbcTemplate().update(UPDATE_CK_BY_ID, ck,id);
	}
	
	@Override
	public void updateValidByIds(Integer[] ids){
		String sql = UPDATE_VALID_BY_IDS + SQLUtil.buildInSelection(ids);
		getMasterJdbcTemplate().update(sql,(Object[])ids);
	}
	
	@Override
	public void updateCommentDateById(Integer id,Date commentDate){
		getMasterJdbcTemplate().update(UPDATE_COMMENT_DATE_BY_ID, new Object[]{commentDate,id});
	}
	
	@Override
	public long queryCommentCount(Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_WORLD_COMMENTED_COUNT, new Object[]{
			worldId,Tag.TRUE,Tag.FALSE
		});
	}
	
	
	@Override
	public long queryCommentCount(Map<String, Object> attrMap, Map<String, Object> userAttrMap) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_COUNT_HEADER, QUERY_COMMENT_COUNT_FOOT, 
				attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(Tag.TRUE);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}
	
	@Override
	public long queryCommentCountByMaxId(Integer maxId, Map<String, Object> attrMap, 
			Map<String, Object> userAttrMap) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_COUNT_HEADER, 
				QUERY_COMMENT_COUNT_BY_MAX_ID_FOOT, attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(Tag.TRUE);
		argsList.add(maxId);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}
	
	@Override
	public long queryCommentCountByMinId(Integer minId, Map<String, Object> attrMap, Map<String, Object> userAttrMap) {
		String sql = buildSelectWorldSQL(QUERY_COMMENT_COUNT_HEADER,
				QUERY_COMMENT_COUNT_BY_MIN_ID_FOOT, attrMap, userAttrMap);
		List<Object> argsList = new ArrayList<Object>();
		CollectionUtil.collectMapValues(argsList, attrMap);
		CollectionUtil.collectMapValues(argsList, userAttrMap);
		argsList.add(minId);
		return getJdbcTemplate().queryForLong(sql, argsList.toArray());
	}


	
	/**
	 * 构建评论查询条件
	 * 
	 * @param attrMap
	 * @param userAttrMap
	 * @return
	 */
	private static String buildSelection(Map<String, Object> attrMap,
			Map<String, Object> userAttrMap) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = attrMap.keySet();
		Object[] keies = keySet.toArray();
		for(int i = 0; i < keies.length; i++) {
			builder.append(" and hc.").append(keies[i]).append("=?");
		}
		Set<String> userKeySet = userAttrMap.keySet();
		Object[] userKeies = userKeySet.toArray();
		for(int i = 0; i < userKeies.length; i++) {
			builder.append(" and u0.").append(userKeies[i]).append("=?");
		}
		return builder.toString();
	}
	
	/**
	 * 构建评论查询SQL
	 * @param header
	 * @param foot
	 * @param attrMap
	 * @param userAttrMap
	 * @param orderKey
	 * @param orderBy
	 * @return
	 */
	private static String buildSelectWorldSQL(String header, String foot, 
			Map<String, Object> attrMap, Map<String, Object> userAttrMap) {
		String selection = buildSelection(attrMap,userAttrMap);
		String sql = header + selection + foot + ORDER_BY_HC_ID_DESC;
		return sql;
	}
	
	@Override
	public HTWorldComment queryCommentById(Integer id) {
		return queryForObjectWithNULL(QUERY_COMMENT_BY_ID, new RowMapper<HTWorldComment>(){

			@Override
			public HTWorldComment mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new HTWorldComment(
						rs.getInt("id"), 
						rs.getInt("author_id"), 
						rs.getString("content"),
						(Date)rs.getObject("comment_Date"),
						rs.getInt("world_id"),
						rs.getInt("world_author_id"),
						rs.getInt("re_id"),
						rs.getInt("re_author_id"),
						rs.getInt("ck"),
						rs.getInt("valid"),
						rs.getInt("shield"));
			}
		}, id);
	}
	
	@Override
	public HTWorldCommentDto queryCommentDtoById(Integer id) {
		return queryForObjectWithNULL(QUERY_COMMENT_DTO_BY_ID, 
				new RowMapper<HTWorldCommentDto>(){

			@Override
			public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldCommentDtoWithUser(rs);
			}
			
		}, new Object[]{id});
	}

	@Override
	public void saveWorldComment(HTWorldComment worldComment) {
		getMasterJdbcTemplate().update(SAVE_COMMENT, new Object[]{
				worldComment.getId(),
				worldComment.getAuthorId(),
				worldComment.getContent(),
				worldComment.getCommentDate(),
				worldComment.getWorldId(),
				worldComment.getWorldAuthorId(),
				worldComment.getReId(),
				worldComment.getReAuthorId(),
				worldComment.getCK(),
				worldComment.getValid(),
				worldComment.getShield(),
		});
	}
	
	@Override
	public List<HTWorldCommentDto> queryComment(Integer worldId, RowSelection rowSelection) {
		return queryForPage(QUERY_COMMENT, new Object[]{worldId,Tag.TRUE,Tag.FALSE},
				new RowMapper<HTWorldCommentDto>() {

			@Override
			public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldCommentDtoWithUser(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public List<HTWorldCommentDto> queryCommentByMaxId(Integer worldId, Integer maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_COMMENT_BY_MAX_ID, new Object[]{worldId,Tag.TRUE,Tag.FALSE,maxId},
				new RowMapper<HTWorldCommentDto>() {

			@Override
			public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldCommentDtoWithUser(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public List<HTWorldCommentDto> queryCommentByMinId(Integer worldId, Integer minId, RowSelection rowSelection) {
		return queryForPage(QUERY_COMMENT_BY_MIN_ID, new Object[]{worldId,Tag.TRUE,Tag.FALSE,minId},
				new RowMapper<HTWorldCommentDto>() {

			@Override
			public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildHTWorldCommentDtoWithUser(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public long queryCommentCountByMaxId(Integer worldId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_WORLD_COMMENTED_COUNT_BY_MAX_ID, new Object[]{
				worldId, Tag.TRUE, Tag.FALSE, maxId
		});
	}
	
	@Override
	public long queryCommentCountByMinId(Integer worldId, Integer minId) {
		return getJdbcTemplate().queryForLong(QUERY_WORLD_COMMENT_COUNT_BY_MIN_ID, new Object[]{
			worldId,Tag.TRUE,Tag.FALSE,minId
		});
	}
	
	@Override
	public void updateCommentShield(Integer id, Integer shield) {
		getMasterJdbcTemplate().update(UPDATE_COMMENT_SHIELD, new Object[]{shield, id});
	}
	
	@Override
	public void updatePushed(Integer commentId, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_PUSHED, new Object[]{valid, commentId});
	}
	
	@Override
	public List<HTWorldCommentDto> queryUserComment(Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_USER_COMMENT, new Object[]{Tag.TRUE, Tag.FALSE, userId, userId}, 
				new RowMapper<HTWorldCommentDto>() {

					@Override
					public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildHTWorldCommentDtoWithUserAndThumb(rs);
					}
			
		}, rowSelection);
	}
	
	@Override
	public List<HTWorldCommentDto> queryUserCommentByMaxId(Integer userId, Integer maxId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_USER_COMMENT_BY_MAX_ID, 
				new Object[]{Tag.TRUE, Tag.FALSE, userId, userId, maxId}, 
				new RowMapper<HTWorldCommentDto>() {

					@Override
					public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildHTWorldCommentDtoWithUserAndThumb(rs);
					}
		}, rowSelection);
	}
	
	@Override
	public List<HTWorldCommentDto> queryUserCommentByMinId(Integer userId, 
			Integer minId, RowSelection rowSelection) {
		return queryForPage(QUERY_USER_COMMENT_BY_MIN_ID, 
				new Object[]{Tag.TRUE, Tag.FALSE, userId, userId, minId}, 
				new RowMapper<HTWorldCommentDto>() {

					@Override
					public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildHTWorldCommentDtoWithUserAndThumb(rs);
					}
		}, rowSelection);
	}
	
	@Override
	public long queryUnCheckUserCommentCount(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_UNCHECK_USER_COMMENT_COUNT, 
				new Object[]{Tag.TRUE, Tag.FALSE, Tag.FALSE, userId, userId});
	}
	
	@Override
	public long queryUserCommentCountByMaxId(Integer userId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_COMMENT_COUNT_BY_MAX_ID, 
				new Object[]{Tag.TRUE, Tag.FALSE, userId, userId, maxId});
	}

	@Override
	public long queryUserCommentCountByMinId(Integer userId, Integer minId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_COMMENT_COUNT_BY_MIN_ID,
				new Object[]{Tag.TRUE, Tag.FALSE, userId, userId, minId});
	}
	
	@Override
	public void updateUnreadComment(Integer userId) {
		getMasterJdbcTemplate().update(UPDATE_UNREAD_USER_COMMENT,
				new Object[]{Tag.TRUE, Tag.FALSE, Tag.TRUE, Tag.FALSE, userId, userId});
	}
	
	@Override
	public void queryCommentUserByLimit(Integer[] worldIds,
			Integer limit, final RowCallback<HTWorldCommentUser> callback) {
		Object[] args = new Object[worldIds.length * 4];
		StringBuilder sqlBuilder = new StringBuilder(QUERY_COMMENT_USER_INFO_HEAD);
		for(int i = 0; i < worldIds.length; i++) {
			if(i != 0) {
				sqlBuilder.append(" union all ");
			}
			sqlBuilder.append(QUERY_COMMENT_USRE_INFO_MAIN);
			int k = i * 4;
			args[k] = worldIds[i];
			args[k+1] = Tag.TRUE;
			args[k+2] = Tag.FALSE;
			args[k+3] = limit;
		}
		sqlBuilder.append(QUERY_COMMENT_USER_INFO_FOOT);
		getJdbcTemplate().query(sqlBuilder.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldCommentUser comment = buildCommentUser(rs);
				callback.callback(comment);
			}
			
		});
	}
	
	@Override
	public void queryCommentUserByLimit(Integer worldId, Integer limit,
			final RowCallback<HTWorldCommentUser> callback) {
		getJdbcTemplate().query(QUERY_COMMENT_USER_INFO_BY_WID, 
				new Object[]{worldId, Tag.TRUE, Tag.FALSE, limit},
				new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldCommentUser comment = buildCommentUser(rs);
				callback.callback(comment);
			}
		});
	}
	
	@Override
	public void updateContentById(Integer id , String content){
		getMasterJdbcTemplate().update(UPDATE_CONTENT_BY_ID, content,id);
	}
	
	
	/**
	 * 根据结果集构建织图评论POJO对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public HTWorldCommentDto buildHTWorldCommentDtoWithUser(ResultSet rs) throws SQLException {
		HTWorldCommentDto dto = new HTWorldCommentDto(
				rs.getInt("id"),
				rs.getInt("author_id"),
				rs.getInt("re_id"),
				rs.getString("content"),
				(Date)rs.getObject("comment_date"),
				rs.getInt("world_id"),
				rs.getInt("world_author_id"));
		UserInfoDto userInfo = UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs);
		dto.setUserInfo(userInfo);
		return dto;
	}
	
	/**
	 * 根据结果集构建织图评论POJO对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldCommentDto buildHTWorldCommentDtoWithUserAndThumb(ResultSet rs) throws SQLException {
		HTWorldCommentDto dto = buildHTWorldCommentDtoWithUser(rs);
		HTWorldThumbDto thumb = worldDao.buildHTWorldThumbDtoByResultSet(dto.getWorldId(), rs);
		dto.setHtworld(thumb);
		return dto;
	}
	
	@Override
	public List<HTWorldCommentReId> queryReId(Integer[] ids) {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_RE_IDS + inSelection;
		return getJdbcTemplate().query(sql, ids, 
				new RowMapper<HTWorldCommentReId>() {

					@Override
					public HTWorldCommentReId mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new HTWorldCommentReId(
								rs.getInt("id"),
								rs.getInt("re_id"));
					}
		});
	}
	
	
	/**
	 * 构建评论用户信息
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldCommentUser buildCommentUser(ResultSet rs) throws SQLException {
		return new HTWorldCommentUser(
				rs.getInt("id"),
				rs.getInt("re_id"),
				rs.getString("content"),
				(Date)rs.getObject("comment_date"),
				rs.getInt("world_id"),
				rs.getInt("author_id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("star"),
				rs.getInt("platform_verify"));
	}


}
