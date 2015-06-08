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
	
	private static final String QUERY_WORLD = "select distinct * from ("
			+ " (SELECT oc0.serial as recommend_id,oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and oc0.valid=1 and h0.valid=1 and h0.shield=0 and oc0.channel_id=?"
			+ " order by oc0.serial desc limit ?)"
			+ " UNION ALL"
			+" (SELECT oc0.serial as recommend_id,oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and h0.valid=1 and oc0.channel_id=? and h0.author_id=?"
			+ " order by serial desc limit ?)"
			+ " ) as o0 order by o0.recommend_id desc limit ?";
	
	
	
//	private static final String QUERY_WORLD_BY_MAX_ID = "SELECT oc0.serial as recommend_id, " + H0_INFO + ", " + U0_INFO 
//			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
//			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
//			+ " and oc0.valid=1 and h0.valid=1 and h0.shield=0 and oc0.channel_id=? and oc0.serial<=?"
//			+ " order by oc0.serial desc limit ?,?";
	
	private static final String QUERY_WORLD_BY_MAX_ID = "select distinct * from ("
			+ " (SELECT oc0.serial as recommend_id, oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and oc0.valid=1 and h0.valid=1 and h0.shield=0 and oc0.channel_id=? and oc0.serial<=?"
			+ " order by oc0.serial desc limit ?)"
			+ " UNION ALL"
			+" (SELECT oc0.serial as recommend_id, oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and h0.valid=1 and oc0.channel_id=? and h0.author_id=? and oc0.serial<=?"
			+ " order by serial desc limit ?)"
			+ " ) as o0 order by o0.recommend_id desc limit ?";
	
	private static final String QUERY_WEIGHT_WORLD = "SELECT oc0.serial as recommend_id, oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and oc0.valid=1 and h0.valid=1 and h0.shield=0 and oc0.weight=1 and oc0.channel_id=?"
			+ " order by oc0.serial desc limit ?,?";
	
	private static final String QUERY_WEIGHT_WORLD_BY_MAX_ID = "SELECT oc0.serial as recommend_id, oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and oc0.valid=1 and h0.valid=1 and h0.shield=0 and oc0.weight=1 and oc0.channel_id=? and oc0.serial<=?"
			+ " order by oc0.serial desc limit ?,?";
	
	
	private static final String QUERY_SUPERB_WORLD = "SELECT oc0.serial as recommend_id, oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and oc0.valid=1 and h0.valid=1 and h0.shield=0 and oc0.superb=1 and oc0.channel_id=?"
			+ " order by oc0.serial desc limit ?,?";
	
	private static final String QUERY_SUPERB_WORLD_BY_MAX_ID = "SELECT oc0.serial as recommend_id, oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and oc0.valid=1 and h0.valid=1 and h0.shield=0 and oc0.superb=1 and oc0.channel_id=? and oc0.serial<=?"
			+ " order by oc0.serial desc limit ?,?";
	
	private static final String QUERY_UNVALID_WORLD = "SELECT oc0.serial as recommend_id, oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and oc0.valid=0 and h0.valid=1 and h0.shield=0 and oc0.channel_id=?"
			+ " order by oc0.serial desc limit ?";
	
	private static final String QUERY_UNVALID_WORLD_BY_MAX_ID = "SELECT oc0.serial as recommend_id, oc0.superb," + H0_INFO + ", " + U0_INFO 
			+ " from " + table + " as oc0, " + HTS.HTWORLD_HTWORLD + " as h0, " + HTS.USER_INFO + " as u0"
			+ " where oc0.world_id=h0.id and h0.author_id = u0.id "
			+ " and oc0.valid=0 and h0.valid=1 and h0.shield=0 and oc0.channel_id=? and oc0.serial<=?"
			+ " order by oc0.serial desc limit ?";
	
	
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
	
	private static final String QUERY_SUPERB_COUNT = "select count(*) from " + table
			+ " where channel_id=? and superb=1 and valid=1";
	
	private static final String QUERY_CHILD_COUNT = "select sum(child_count) from " + table
			+ " c0, " + HTS.HTWORLD_HTWORLD + " h0 where c0.world_id=h0.id "
			+ " and c0.channel_id=?";
	
	private static final String UPDATE_VALID = "update " + table 
			+ " set valid=? where channel_id=? and world_id=?";
	
	private static final String UPDATE_VALID_AND_SERIAL = "update " + table 
			+ " set valid=?,serial=? where channel_id=? and world_id=?";
	
	private static final String UPDATE_SUPERB = "update " + table
			+ " set superb=? where channel_id=? and world_id=?";
	
	private static final String UPDATE_SUPERB_AND_SERIAL = "update " + table
			+ " set superb=?,serial=? where channel_id=? and world_id=?";
	
	private static final String QUERY_UNVALID_COUNT = "select count(*) from " + table
			+ " where channel_id=? and valid=0";
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
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
	public Long queryWorldCount(Integer channelId) {
		return getMasterJdbcTemplate().queryForLong(QUERY_WORLD_COUNT,
				new Object[]{channelId});
	}
	
	@Override
	public int queryChildCount(Integer channelId) {
		return getMasterJdbcTemplate().queryForInt(QUERY_CHILD_COUNT,
				new Object[]{channelId});
	}
	
	@Override
	public List<OpChannelWorldDto> queryChannelWorld(Integer channelId,
			RowSelection rowSelection, Integer userId) {
		return getJdbcTemplate().query(QUERY_WORLD, 
				new Object[]{channelId, rowSelection.getLimit(),channelId, 
				userId, rowSelection.getLimit(), rowSelection.getLimit()}, 
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
			Integer channelId, RowSelection rowSelection, Integer userId) {
		return getJdbcTemplate().query(QUERY_WORLD_BY_MAX_ID, 
				new Object[]{channelId, maxId, rowSelection.getLimit(), channelId, userId, maxId,
				rowSelection.getLimit(), rowSelection.getLimit()}, 
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
		world.setSuperb(rs.getInt("superb"));
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
	public List<OpChannelWorldDto> queryWeightChannelWorld(Integer channelId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_WEIGHT_WORLD, 
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
	public List<OpChannelWorldDto> queryWeightChannelWorld(Integer maxId,
			Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_WEIGHT_WORLD_BY_MAX_ID, 
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
	public Long querySuperbCount(Integer channelId) {
		return getMasterJdbcTemplate().queryForLong(QUERY_SUPERB_COUNT,
				new Object[]{channelId});
	}

	@Override
	public void updateValid(Integer channelId, Integer worldId, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_VALID, 
				new Object[]{valid, channelId, worldId});
	}
	
	@Override
	public void updateValidAndSerial(Integer channelId, Integer worldId, Integer valid, Integer serial) {
		getMasterJdbcTemplate().update(UPDATE_VALID_AND_SERIAL,
				new Object[]{valid,serial,channelId,worldId});
	}

	@Override
	public void updateSuperb(Integer channelId, Integer worldId,
			Integer superb) {
		getMasterJdbcTemplate().update(UPDATE_SUPERB, 
				new Object[]{superb, channelId, worldId});
	}
	
	@Override
	public void updateSuperbAndSerial(Integer channelId, Integer worldId,
			Integer superb, Integer serial) {
		getMasterJdbcTemplate().update(UPDATE_SUPERB_AND_SERIAL, 
				new Object[]{superb, serial, channelId, worldId});
	}

	@Override
	public List<OpChannelWorldDto> queryUnValidChannelWorld(Integer channelId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_UNVALID_WORLD, 
				new Object[]{channelId, rowSelection.getLimit()}, 
				new RowMapper<OpChannelWorldDto>() {

			@Override
			public OpChannelWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChannelWorld(rs);
			}
		});
	}

	@Override
	public List<OpChannelWorldDto> queryUnValidChannelWorld(Integer maxId,
			Integer channelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_UNVALID_WORLD_BY_MAX_ID, 
				new Object[]{channelId, maxId, rowSelection.getLimit()}, 
				new RowMapper<OpChannelWorldDto>() {

			@Override
			public OpChannelWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChannelWorld(rs);
			}
		});
	}

	@Override
	public long queryUnValidCount(Integer channelId) {
		return getJdbcTemplate().queryForLong(QUERY_UNVALID_COUNT,
				new Object[]{channelId});
	}

}
