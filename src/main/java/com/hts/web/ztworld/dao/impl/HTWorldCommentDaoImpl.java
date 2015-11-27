package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.pojo.HTWorldCommentDto;
import com.hts.web.common.pojo.HTWorldCommentInline;
import com.hts.web.common.pojo.HTWorldCommentInlineUser;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.ztworld.dao.HTWorldCommentDao;

/**
 * <p>
 * 织图评论数据访问对象
 * </p>
 * 
 * @author ztj 2013-8-9 2015-11-04
 *
 */
@Repository("HTSHTWorldCommentDao")
public class HTWorldCommentDaoImpl extends BaseDaoImpl implements
		HTWorldCommentDao {
	
	private static final String table = HTS.HTWORLD_COMMENT;
	
	
	private static final String FULL_COMMENT_INFO = "c0.id,c0.author_id,c0.content,"
			+ "c0.comment_date,c0.world_id,c0.world_author_id,c0.re_author_id";
	
	private static final String COMMENT_INFO = "c0.id,c0.author_id,c0.content,"
			+ "c0.comment_date,c0.world_id,c0.re_author_id";
	
	private static final String COMMENT_USER_INFO = "u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar_l,u0.star,u0.platform_verify";
	
	private static final String SAVE_COMMENT = "insert into " + table 
			+ "(id, author_id, content, comment_date, world_id," 
			+ "world_author_id,re_author_id) values (?,?,?,?,?,?,?)";
	
	private static final String DELETE_BY_WID = "delete from " + table
			+ " where world_id=? and id=?";
	
	private static final String DELETE_BY_ID = "delete from " + table
			+ " where id=?";
	
	private static final String QUERY_COMMENT_BY_WID = "select " 
			+ FULL_COMMENT_INFO + " from " + table 
			+ " c0 where c0.world_id=? and c0.id=?";
	
	private static final String QUERY_COMMENT_BY_ID = "select " 
			+ FULL_COMMENT_INFO + " from " + table 
			+ " c0 where c0.id=?";
	
	private static final String QUERY_COMMENT_DTO_BY_ID = "select " 
			+ COMMENT_INFO + ", " + COMMENT_USER_INFO 
			+ " from " + table + " as c0, "  + HTS.USER_INFO + " as u0" 
			+ " where c0.author_id=u0.id and c0.world_id=? and c0.id=?";
	
	private static final String QUERY_COMMENT = "select " 
			+ COMMENT_INFO + ", " + U0_INFO 
			+ " from " + table + " as c0, "  + HTS.USER_INFO + " as u0" 
			+ " where c0.author_id=u0.id and c0.world_id=?"
			+ " order by c0.id desc limit ?,?";
	
	private static final String QUERY_COMMENT_BY_MAX_ID = "select " 
			+ COMMENT_INFO + ", " + U0_INFO 
			+ " from " + table + " as c0, "  + HTS.USER_INFO + " as u0" 
			+ " where c0.author_id=u0.id and c0.world_id=? and c0.id<=?"
			+ " order by c0.id desc limit ?,?";
	
	private static final String QUERY_COMMENT_BY_MIN_ID = "select " 
			+ COMMENT_INFO + ", " + U0_INFO 
			+ " from " + table + " as c0, "  + HTS.USER_INFO + " as u0" 
			+ " where c0.author_id=u0.id and c0.world_id=? and c0.id>?"
			+ " order by c0.id desc limit ?,?";
	
	private static final String QUERY_COMMENT_COUNT = "select count(*) from " 
			+ table + " where world_id=?";
	
	private static final String QUERY_INLINE_COMMENT = "(select"
			+ " c0.world_id,c0.content,u0.user_name from "
			+ table + " c0," + HTS.USER_INFO + " u0"
			+ " where c0.author_id=u0.id and c0.world_id=?"
			+ " order by c0.id desc limit ?)";
	
	private static final String QUERY_AUTHOR_ID = "select author_id from " + table
			+ " where world_id=? and id=?";
	
	private static final String QUERY_EXISTS_BY_ID = "select 1 from " + table
			+ " where world_id=? and id=?";
	
	private static final String QUERY_ALL_AUTHOR_ID = "select DISTINCT(author_id) from " +table
			+ " where world_id=? limit ?";
	
	/**
	 * 根据结果集构建织图评论POJO对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private HTWorldCommentDto buildCommentDto(ResultSet rs) throws SQLException {
		HTWorldCommentDto dto = new HTWorldCommentDto(
				rs.getInt("id"),
				rs.getInt("author_id"),
				rs.getInt("re_author_id"),
				rs.getString("content"),
				(Date)rs.getObject("comment_date"),
				rs.getInt("world_id"));
		
		UserInfoDto user = new UserInfoDto();
		user.setId(rs.getInt("author_id"));
		user.setUserName(rs.getString("user_name"));
		user.setUserAvatar(rs.getString("user_avatar"));
		user.setUserAvatarL(rs.getString("user_avatar_l"));
		user.setStar(rs.getInt("star"));
		user.setPlatformVerify(rs.getInt("platform_verify"));
		
		dto.setUserInfo(user);
		
		return dto;
	}
	
	/**
	 * 构建内联评论信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private HTWorldCommentInline buildInlineComment(ResultSet rs) throws SQLException {
		HTWorldCommentInline comment = new HTWorldCommentInline();
		comment.setWorldId(rs.getInt("world_id"));
		comment.setContent(rs.getString("content"));
		
		HTWorldCommentInlineUser user = new HTWorldCommentInlineUser();
		user.setUserName(rs.getString("user_name"));
		comment.setUserInfo(user);
		
		return comment;
	}

	
	@Override
	public long queryCommentCount(Integer worldId) {
		return getMasterJdbcTemplate().queryForLong(QUERY_COMMENT_COUNT, 
				worldId);
	}
	
	@Override
	public HTWorldComment queryCommentByWID(Integer id, Integer worldId) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_COMMENT_BY_WID, new RowMapper<HTWorldComment>(){
	
				@Override
				public HTWorldComment mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					HTWorldComment comm = new HTWorldComment();
					comm.setId(rs.getInt("id"));
					comm.setAuthorId(rs.getInt("author_id"));
					comm.setContent(rs.getString("content"));
					comm.setCommentDate((Date)rs.getObject("comment_Date"));
					comm.setWorldId(rs.getInt("world_id"));
					comm.setWorldAuthorId(rs.getInt("world_author_id"));
					comm.setReAuthorId(rs.getInt("re_author_id"));
					return comm;
				}
			}, worldId, id);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public HTWorldCommentDto queryCommentDtoById(Integer id, Integer worldId) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_COMMENT_DTO_BY_ID, 
					new RowMapper<HTWorldCommentDto>(){
	
				@Override
				public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildCommentDto(rs);
				}
				
			}, new Object[]{worldId, id});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
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
				worldComment.getReAuthorId(),
		});
	}
	
	@Override
	public void delComment(Integer id, Integer worldId) {
		getMasterJdbcTemplate().update(DELETE_BY_WID, worldId, id);
	}
	
	
	public void delCommentById(Integer id) {
		getMasterJdbcTemplate().update(DELETE_BY_ID, id);
	}
	
	@Override
	public List<HTWorldCommentDto> queryComment(Integer worldId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_COMMENT, 
				new Object[]{worldId, rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<HTWorldCommentDto>() {

			@Override
			public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildCommentDto(rs);
			}
			
		});
	}
	
	@Override
	public List<HTWorldCommentDto> queryCommentByMaxId(Integer worldId, 
			Integer maxId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_COMMENT_BY_MAX_ID, 
				new Object[]{worldId,maxId,rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<HTWorldCommentDto>() {

			@Override
			public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildCommentDto(rs);
			}
			
		});
	}
	
	@Override
	public void queryInlineComment(Integer[] worldIds,
			Integer limit, final RowCallback<HTWorldCommentInline> callback) {
		Object[] args = new Object[worldIds.length * 2];
		StringBuilder sqlBuilder = new StringBuilder();
		for(int i = 0; i < worldIds.length; i++) {
			if(i != 0) {
				sqlBuilder.append(" union all ");
			}
			sqlBuilder.append(QUERY_INLINE_COMMENT);
			int k = i * 2;
			args[k] = worldIds[i];
			args[k+1] = limit;
		}
		getJdbcTemplate().query(sqlBuilder.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildInlineComment(rs));
			}
		});
	}
	
	@Override
	public Integer queryAuthorId(Integer id, Integer worldId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_AUTHOR_ID, worldId, id);
		} catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	@Override
	public boolean isCommentExist(Integer id, Integer worldId) {
		try {
			getJdbcTemplate().queryForInt(QUERY_EXISTS_BY_ID, worldId, id);
			return true;
		} catch(EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public List<Integer> queryAllAuthorId(Integer worldId, Integer limit) {
		return getJdbcTemplate().query(QUERY_ALL_AUTHOR_ID, new Object[]{worldId, limit},
				new RowMapper<Integer>() {

					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt("author_id");
					}
		});
	}

	@Override
	public List<HTWorldCommentDto> queryCommentByMinId(Integer worldId, 
			Integer minId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_COMMENT_BY_MIN_ID, 
				new Object[]{worldId,minId,rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<HTWorldCommentDto>() {

			@Override
			public HTWorldCommentDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildCommentDto(rs);
			}
			
		});
	}

}
