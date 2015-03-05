package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.common.pojo.OpChannelWorld;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.operations.dao.ChannelWorldDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;
import com.hts.web.ztworld.dao.HTWorldDao;

@Repository("HTSChannelWorldDao")
public class ChannelWorldDaoImpl extends BaseDaoImpl implements ChannelWorldDao {

	private static String table = HTS.OPERATIONS_CHANNEL_WORLD;
	
	/**
	 * 查询频道织图
	 */
	private static final String QUERY_WORLD = "SELECT oc.id as recommend_id, " + H0_INFO + ", " + U0_INFO 
			+ " from (select oc.id, oc.world_id from " + table + " as oc, " + HTS.HTWORLD_HTWORLD 
			+ " as h where oc.world_id=h.id and h.valid=1 and h.shield=0 and oc.valid=1 and oc.channel_id=? order by id desc limit ?,?)"
			+ " as oc, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO 
			+ " as u0 where oc.world_id = h0.id and h0.author_id = u0.id";
	
	/**
	 * 根据最大id查询频道织图
	 */
	private static final String QUERY_WORLD_BY_MAX_ID = "SELECT oc.id as recommend_id, " + H0_INFO + ", " + U0_INFO 
			+ " from (select oc.id, oc.world_id from " + table + " as oc, " + HTS.HTWORLD_HTWORLD 
			+ " as h where oc.world_id=h.id and h.valid=1 and h.shield=0 and oc.valid=1 and oc.channel_id=? and oc.id<=? order by id desc limit ?,?)"
			+ " as oc, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO 
			+ " as u0 where oc.world_id = h0.id and h0.author_id = u0.id";
	
	/**
	 * 查询封面缩略图SQL头部
	 */
	private static final String QUERY_TITLE_THUMB_HEAD = "select cw.channel_id,w.title_thumb_path from " 
			+ HTS.HTWORLD_HTWORLD + " as w, (";
	/**
	 * 查询封面缩略图
	 */
	private static final String QUERY_TITLE_THUMB = "(select channel_id,world_id from " + table 
			+ " where channel_id=? and valid=1 order by id desc limit ?)";
	
	/**
	 * 查询封面缩略图SQL尾部
	 */
	private static final String QUERY_TITLE_THUMB_FOOT = ") as cw where w.id=cw.world_id";
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public List<OpChannelWorld> queryChannelWorld(Integer channelId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_WORLD, 
				new Object[]{channelId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelWorld>() {

			@Override
			public OpChannelWorld mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChannelWorld(rs);
			}
		});
	}

	@Override
	public List<OpChannelWorld> queryChannelWorld(Integer maxId,
			Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_WORLD_BY_MAX_ID, 
				new Object[]{channelId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelWorld>() {

			@Override
			public OpChannelWorld mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChannelWorld(rs);
			}
		});
	}
	
	public OpChannelWorld buildChannelWorld(ResultSet rs) throws SQLException{
		OpChannelWorld world = new OpChannelWorld();
		worldDao.setWorldInfo(rs, world);
		world.setRecommendId(rs.getInt("recommend_id"));
		UserInfoDto user = UserInfoDaoImpl.buildUserInfoDtoByResult(world.getAuthorId(), rs);
		world.setUserInfo(user);
		return world;
	}

	@Override
	public List<OpChannelCover> queryTitleThumb(List<OpChannel> channels,
			Integer limit) {
		StringBuilder builder = new StringBuilder(QUERY_TITLE_THUMB_HEAD);
		Object[] args = new Object[2*channels.size()];
		for(int i = 0; i < channels.size(); i++) {
			if(i != 0) {
				builder.append(" union all ");
			}
			builder.append(QUERY_TITLE_THUMB);
			int k = i * 2;
			args[k] = channels.get(i).getId();
			args[k+1] = limit;
		}
		builder.append(QUERY_TITLE_THUMB_FOOT);
		return getJdbcTemplate().query(builder.toString(), args,
				new RowMapper<OpChannelCover>() {

					@Override
					public OpChannelCover mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new OpChannelCover(
								rs.getInt("channel_id"),
								rs.getString("title_thumb_path"));
					}
			
		});
			
	}

}
