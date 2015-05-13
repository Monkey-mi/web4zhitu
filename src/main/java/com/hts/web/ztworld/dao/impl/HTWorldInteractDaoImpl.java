package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldInteract;
import com.hts.web.common.pojo.HTWorldThumbDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldInteractDao;

@Repository("HTSHTWorldInteractDao")
public class HTWorldInteractDaoImpl extends BaseDaoImpl implements
		HTWorldInteractDao {

	private static String comment = HTS.HTWORLD_COMMENT;
	private static String liked = HTS.HTWORLD_LIKED;
	private static String htworld = HTS.HTWORLD_HTWORLD;
	private static String user = HTS.USER_INFO;
	
	/**
	 * 查询互动
	 */
	private static final String QUERY_INTERACT = "select hi.*,h.title_path,h.bg_path,h.title_thumb_path,h.cover_path,h.valid,h.shield,"+U0_INFO+" from"
		+ " ((select id,author_id as user_id,re_id,content,world_id, comment_date as interact_date, '1' as interact_type from " + comment 
		+ " where world_author_id=? and valid=1 and shield=0 order by id desc limit ?,?)"
		+ " UNION ALL"
		+ " (select id,author_id as user_id,re_id,content,world_id, comment_date as interact_date, '1' as interact_type from " + comment 
		+ " where re_author_id=? and valid=1 and shield=0 order by id desc limit ?,?)"
		+ " UNION ALL"
		+ " (select id,user_id,'0' as re_id,'' as content,world_id,liked_date as interact_date, '2' as interact_type from " + liked
		+ " where valid=1 and world_author_id=? order by id desc limit ?,?) order by interact_date desc limit ?,?)"
		+ " as hi, " + htworld + " as h, " + user +" as u0"
		+ " where hi.world_id = h.id and hi.user_id = u0.id";
	
	/**
	 * 根据最大日期查询互动
	 */
	private static final String QUERY_INTERACT_BY_MAX_DATE = "select hi.*,h.title_path,h.bg_path,h.title_thumb_path,h.cover_path,h.valid,h.shield,"+U0_INFO+" from"
			+ " ((select id,author_id as user_id,re_id,content,world_id, comment_date as interact_date,'1' as interact_type from " + comment
			+ " where world_author_id=? and valid=1 and shield=0 and comment_date<=? order by id desc limit ?,?)"
			+ " UNION ALL"
			+ " (select id,author_id as user_id,re_id,content,world_id, comment_date as interact_date, '1' as interact_type from " + comment 
			+ " where re_author_id=? and valid=1 and shield=0 and comment_date<=? order by id desc limit ?,?)"
			+ " UNION ALL"
			+ " (select id,user_id,'0' as re_id,'' as content,world_id,liked_date as interact_date, '2' as interact_type from " + liked
			+ " where valid=1 and world_author_id=? and liked_date<=? order by id desc limit ?,?) order by interact_date desc limit ?,?)"
			+ " as hi, " + htworld + " as h, " + user +" as u0"
			+ " where hi.world_id = h.id and hi.user_id = u0.id";
	
	/**
	 * 根据最大日期查询互动总数
	 */
	private static final String QUERY_INTERACT_COUNT_BY_MAX_DATE = "select SUM(hi.count) from"
			+ " ((select count(*) as count from "+comment+" where shield=0 and valid=1 and world_author_id=? and comment_date<=?)"
			+ " UNION ALL" 
			+ " (select count(*) as count from "+liked+" where valid=1 and world_author_id=? and liked_date<=?))"
			+ " as hi";
	
	/** 
	 * 根据织图作者id更新评论未读状态 
	 */
	private static final String UPDATE_COMMENT_CK_BY_RE_AUTHOR = "update " + comment 
			+ " set ck=? where re_author_id=? and valid=1 and shield=0 and ck=? and comment_date<=?";
	
	/**
	 * 根据被回复作者id更新评论未读状态
	 */
	private static final String UPDATE_COMMENT_CK_BY_AUTHOR = "update " + comment 
			+ " set ck=? where world_author_id=? and valid=1 and shield=0 and ck=? and comment_date<=?";
	
	/** 
	 * 更新喜欢未读状态 
	 */
	private static final String UPDATE_LIKED_CK = "update " + liked 
			+ " set ck=? where world_author_id=? and ck=? and liked_date<=?";
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Override
	public List<HTWorldInteract> queryInteract(Integer userId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_INTERACT,
				new Object[]{userId,rowSelection.getFirstRow(),rowSelection.getLimit(),
					userId, rowSelection.getFirstRow(),rowSelection.getLimit(), 
					userId, rowSelection.getFirstRow(),rowSelection.getLimit(),
					rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<HTWorldInteract>() {

			@Override
			public HTWorldInteract mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildInteractByRs(rs);
			}
		});
	}

	@Override
	public List<HTWorldInteract> queryInteract(Date maxDate, Integer userId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_INTERACT_BY_MAX_DATE, 
				new Object[]{userId, maxDate,rowSelection.getFirstRow(),rowSelection.getLimit(), 
					userId, maxDate,rowSelection.getFirstRow(),rowSelection.getLimit(), 
					userId, maxDate,rowSelection.getFirstRow(),rowSelection.getLimit(), 
					rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<HTWorldInteract>() {

			@Override
			public HTWorldInteract mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildInteractByRs(rs);
			}
		});
	}

	@Override
	public long queryInteractCount(Date maxDate, Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_INTERACT_COUNT_BY_MAX_DATE, new Object[]{userId, maxDate, userId, maxDate});
	}

	@Override
	public void updateUnReadInteract(Date maxDate, Integer userId) {
		getMasterJdbcTemplate().update(UPDATE_COMMENT_CK_BY_RE_AUTHOR, new Object[]{Tag.TRUE, userId, Tag.FALSE, maxDate});
		getMasterJdbcTemplate().update(UPDATE_COMMENT_CK_BY_AUTHOR, new Object[]{Tag.TRUE, userId, Tag.FALSE, maxDate});
		getMasterJdbcTemplate().update(UPDATE_LIKED_CK, new Object[]{Tag.TRUE, userId, Tag.FALSE, maxDate});
	}
	
	public HTWorldInteract buildInteractByRs(ResultSet rs) throws SQLException {
		HTWorldInteract interact = new HTWorldInteract(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("re_id"),
				rs.getString("content"),
				rs.getInt("world_id"),
				(Date)rs.getObject("interact_date"),
				rs.getInt("interact_type"));
		UserInfoDto userInfo = UserInfoDaoImpl.buildUserInfoDtoByResult(interact.getUserId(), rs);
		HTWorldThumbDto thumb = worldDao.buildHTWorldThumbDtoByResultSet(interact.getWorldId(), rs);
		interact.setHtworld(thumb);
		interact.setUserInfo(userInfo);
		return interact;
	}

}
