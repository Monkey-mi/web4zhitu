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
import com.hts.web.common.pojo.OpChannelWorldDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.operations.dao.ChannelWorldDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;
import com.hts.web.ztworld.dao.HTWorldDao;

@Repository("HTSChannelWorldDao")
public class ChannelWorldDaoImpl extends BaseDaoImpl implements ChannelWorldDao {

	private static String table = HTS.OPERATIONS_CHANNEL_WORLD;
	
	private static final String QUERY_WORLD = "SELECT oc0.id as recommend_id, " + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and h0.valid=1 and h0.shield=0 and oc0.valid=1 and oc0.channel_id=?"
			+ " order by oc0.id desc limit ?,?";
	
	private static final String QUERY_WORLD_BY_MAX_ID = "SELECT oc0.id as recommend_id, " + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and h0.valid=1 and h0.shield=0 and oc0.valid=1 and oc0.channel_id=? and oc0.id<=?"
			+ " order by oc0.id desc limit ?,?";
	
	
	private static final String QUERY_SUPERB_WORLD = "SELECT oc0.id as recommend_id, " + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and h0.valid=1 and h0.shield=0 and oc0.valid=1 and superb=1 and oc0.channel_id=?"
			+ " order by oc0.id desc limit ?,?";
	
	private static final String QUERY_SUPERB_WORLD_BY_MAX_ID = "SELECT oc0.id as recommend_id, " + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and h0.valid=1 and h0.shield=0 and oc0.valid=1 and superb=1 and oc0.channel_id=? and oc0.id<=?"
			+ " order by oc0.id desc limit ?,?";
	
	private static final String QUERY_TITLE_THUMB_HEAD = "select cw.channel_id,w.title_thumb_path from " 
			+ HTS.HTWORLD_HTWORLD + " as w, (";
	private static final String QUERY_TITLE_THUMB = "(select channel_id,world_id from " + table 
			+ " where channel_id=? and valid=1 order by id desc limit ?)";
	
	private static final String QUERY_TITLE_THUMB_FOOT = ") as cw where w.id=cw.world_id";
	
	private static final String SAVE_CHANNEL_WORLD = "insert into " + table 
			+ " (id,channel_id,world_id,author_id,date_added,valid,notified,superb,serial)"
			+ " values (?,?,?,?,?,?,?,?,?)";
	
	private static final String QUERY_WORLD_COUNT = "select count(*) from " + table
			+ " where channel_id=? and valid=1";
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public List<OpChannelWorldDto> queryChannelWorld(Integer channelId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_WORLD, 
				new Object[]{channelId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelWorldDto>() {

			@Override
			public OpChannelWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChannelWorld(rs);
			}
		});
	}

	@Override
	public List<OpChannelWorldDto> queryChannelWorld(Integer maxId,
			Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_WORLD_BY_MAX_ID, 
				new Object[]{channelId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelWorldDto>() {

			@Override
			public OpChannelWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChannelWorld(rs);
			}
		});
	}
	
	public OpChannelWorldDto buildChannelWorld(ResultSet rs) throws SQLException{
		OpChannelWorldDto world = new OpChannelWorldDto();
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

	@Override
	public List<OpChannelWorldDto> querySuperbChannelWorld(Integer channelId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUPERB_WORLD, 
				new Object[]{channelId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelWorldDto>() {

			@Override
			public OpChannelWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChannelWorld(rs);
			}
		});
	}

	@Override
	public List<OpChannelWorldDto> querySuperbChannelWorld(Integer maxId,
			Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUPERB_WORLD_BY_MAX_ID, 
				new Object[]{channelId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelWorldDto>() {

			@Override
			public OpChannelWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChannelWorld(rs);
			}
		});
	}

	@Override
	public void saveChannelWorld(OpChannelWorld world) {
		getMasterJdbcTemplate().update(SAVE_CHANNEL_WORLD, new Object[]{
				world.getId(),
				world.getChannelId(),
				world.getWorldId(),
				world.getAuthorId(),
				world.getDateAdded(),
				world.getValid(),
				world.getNotified(),
				world.getSuperb(),
				world.getSerial()
		});
	}
	
	@Override
	public long queryWorldCount(Integer channelId) {
		return getJdbcTemplate().queryForLong(QUERY_WORLD_COUNT,
				new Object[]{channelId});
	}

}
