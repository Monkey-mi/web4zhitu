package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldLabelWorldAuthor;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.dao.ActivityDao;
import com.hts.web.ztworld.dao.HTWorldLabelWorldDao;

/**
 * <p>
 * 活动数据访问对象
 * </p>
 * 
 * 创建时间：2013-11-8
 * @author ztj
 *
 */
@Repository("HTSActivityDao")
public class ActivityDaoImpl extends BaseDaoImpl implements
		ActivityDao {

	private static final String table = HTS.OPERATIONS_SQUARE_PUSH_ACTIVITY;
	
	private static final String LABEL_WORLD_AUTHOR_INFO = "u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar_l,u0.sex,u0.user_label,u0.address,u0.province,u0.city,"
			+ "u0.signature,u0.platform_sign,u0.star,u0.platform_verify";
	
	/**
	 * 根据id查询活动
	 */
	private static final String QUERY_ACTIVITY_BY_ID = "select * from " + table
			+ " where id=?";
	
	/**
	 * 查询作者点赞排行
	 */
	private static final String QUERY_AUTHOR_LIKE_RANK = "select lw.*," + LABEL_WORLD_AUTHOR_INFO + " from"
			+ " (select lw0.serial,lw0.label_id,lw0.user_id,SUM(h0.like_count) as like_count from "
			+ HTS.HTWORLD_LABEL_WORLD + " as lw0, " + HTS.HTWORLD_HTWORLD + " as h0"
			+ " where lw0.world_id=h0.id and lw0.valid=1 and h0.valid=1 and shield=0"
			+ " and lw0.label_id=? and NOT EXISTS (select * from " 
			+ HTS.OPERATIONS_ACTIVITY_WINNER + " where user_id=lw0.user_id and activity_id=?)"
			+ " GROUP BY lw0.user_id ORDER BY like_count desc,lw0.serial desc limit ?) as lw, " 
			+ HTS.USER_INFO + " as u0 where lw.user_id=u0.id";
	
	/**
	 * 根据id查询活动
	 */
	private static final String QUERY_HTWORLD_BY_ACTIVITYID = "SELECT hh.* FROM hts.htworld_label_world as hlw,hts.htworld_htworld as hh"
			+  " WHERE  hlw.world_id = hh.id and hlw.valid=1 and hlw. label_id = ? order by hh.like_count desc"
			+ " LIMIT ? ";
	
	/**
	 * 根据活动id查询参加活动的人数
	 */
	private static final String QUERY_ACTIVITY_TOTAL_BY_AID = 	"select count(*) from hts.htworld_label_world where label_id = ? ";
	
	private static final String QUERY_LABEL_BY_AID = "select label_name from hts.htworld_label where id = ?";
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Override
	public OpActivity queryActivityById(Integer id) {
		return queryForObjectWithNULL(QUERY_ACTIVITY_BY_ID, new Object[]{id}, 
				new RowMapper<OpActivity>() {

					@Override
					public OpActivity mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildActivity(rs);
					}
		});
	}
	
	@Override
	public List<HTWorldLabelWorldAuthor> queryAuthorLikeRank(
			Integer activityId, int limit) {
		return getJdbcTemplate().query(QUERY_AUTHOR_LIKE_RANK, 
				new Object[]{activityId,activityId,limit}, new RowMapper<HTWorldLabelWorldAuthor>(){

					@Override
					public HTWorldLabelWorldAuthor mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						HTWorldLabelWorldAuthor user = worldLabelWorldDao.buildLabelWorldAuthor(rs);
						user.setLikeCount(rs.getInt("like_count"));
						user.setCurrPos(rowNum+1);
						return user;
					}
			
		});
	}
	
	
	public OpActivity buildActivity(ResultSet rs) throws SQLException {
		Date deadline = null;
		Object deadlineObj = rs.getObject("deadline");
		if(deadlineObj != null) {
			deadline = (Date) deadlineObj;
		}
		return new OpActivity(
				rs.getInt("id"), 
				rs.getString("title_path"),
				rs.getString("title_thumb_path"),
				rs.getString("channel_path"),
				rs.getString("activity_name"),
				rs.getString("activity_title"),
				rs.getString("activity_desc"),
				rs.getString("activity_link"),
				rs.getString("activity_logo"),
				(Date)rs.getObject("activity_date"),
				deadline,
				rs.getInt("obj_type"),
				rs.getInt("obj_id"),
				rs.getInt("commercial"),
				rs.getString("share_title"),
				rs.getString("share_desc"),
				rs.getInt("valid"),
				rs.getInt("serial"));
	}

	@Override
	public List<HTWorld> getHtWorldByAid(Integer activityId, int limit) {
		return getJdbcTemplate().query(QUERY_HTWORLD_BY_ACTIVITYID, 
				new Object[]{activityId,limit}, new RowMapper<HTWorld>(){

					@Override
					public HTWorld mapRow(ResultSet rs,
							int rowNum) throws SQLException {
							return buildHTworld(rs);
					}
			
		});
	}
	
	public HTWorld buildHTworld(ResultSet rs) throws SQLException {
		
		return new HTWorld(
				rs.getInt("id"), 
				rs.getString("short_link"), 
				rs.getInt("author_id"), 
				rs.getString("world_name"),
				rs.getString("world_desc"),
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_modified"),
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"),
				rs.getInt("keep_count"),
				rs.getString("cover_path"),
				rs.getString("title_path"),
				rs.getString("bg_path"),
				rs.getString("title_thumb_path"),
				null,//不需要这个数据，现在获取麻烦，且不必要实现，故先置为空。
				rs.getDouble("longitude"),
				rs.getDouble("latitude"), 
				rs.getString("location_desc"),
				rs.getString("location_addr"),
				rs.getInt("phone_code"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getInt("size"),
				rs.getInt("child_count"),
				rs.getInt("ver"),
				rs.getInt("tp"),
				rs.getInt("valid"),
				rs.getInt("shield"),
				JSONUtil.getJSObjectFromText(rs.getString("text_style"))
				);
	}
	
	public int getActivityCount(Integer activityId){
		return getJdbcTemplate().queryForObject(QUERY_ACTIVITY_TOTAL_BY_AID,new Object[] {activityId},Integer.class) ;
	}
	
	public String queryLableName(Integer activityId){
		return getJdbcTemplate().queryForObject(QUERY_LABEL_BY_AID,new Object[] {activityId},String.class) ;
	}
}
