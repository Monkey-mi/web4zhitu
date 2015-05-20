package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCount;
import com.hts.web.common.pojo.OpChannelDetail;
import com.hts.web.common.pojo.OpChannelName;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.util.TimeUtil;
import com.hts.web.operations.dao.ChannelDao;
import com.hts.web.userinfo.dao.UserInfoDao;

@Repository("HTSChannelDao")
public class ChannelDaoImpl extends BaseDaoImpl implements ChannelDao {

	private static final String CHANNEL_ABSTRACT = "c0.id,c0.owner_id,c0.channel_name,"
			+ "c0.channel_title,c0.subtitle,c0.channel_desc,c0.channel_icon,"
			+ "c0.sub_icon, c0.channel_type,c0.channel_label,c0.label_ids,c0.world_count,c0.child_count,"
			+ "c0.member_count,c0.superb_count,c0.create_time,c0.last_modified,c0.superb,c0.serial,"
			+ "c0.danmu,c0.mood,c0.world";
	
	private static final String CHANNEL_DETAIL = "c0.id,c0.owner_id,c0.channel_name,"
			+ "c0.channel_title,c0.subtitle,c0.channel_desc,c0.channel_icon,"
			+ "c0.sub_icon, c0.channel_type,c0.channel_label,c0.label_ids,c0.world_count,c0.child_count,"
			+ "c0.member_count,c0.superb_count,c0.create_time,c0.last_modified,c0.superb,c0.serial,"
			+ "c0.danmu,c0.mood,c0.world," + U0_INFO;
	
	private static String table = HTS.OPERATIONS_CHANNEL;
	
	private static final String QUERY_SUBSCRIBED_CHANNEL = "select " + CHANNEL_ABSTRACT + " from " 
			+ table + " c0, " + HTS.OPERATIONS_CHANNEL_MEMBER + " m0"
			+ " where c0.id=m0.channel_id and m0.user_id=?"
			+ " order by m0.id desc limit ?,?";
	
	private static final String QUERY_SUBSCRIBED_CHANNEL_BY_MAX_ID = "select " + CHANNEL_ABSTRACT + " from " 
			+ table + " c0, " + HTS.OPERATIONS_CHANNEL_MEMBER + " m0"
			+ " where c0.id=m0.channel_id and m0.user_id=? and m0.id<=?"
			+ " order by m0.id desc limit ?,?";
	
	private static final String QUERY_SUBSCRIBED_NAME = "select c0.id,c0.channel_name from " 
			+ table + " c0, " + HTS.OPERATIONS_CHANNEL_MEMBER + " m0"
			+ " where c0.id=m0.channel_id and m0.user_id=?"
			+ " order by m0.id desc limit ?,?";
	
	private static final String QUERY_SUBSCRIBED_NAME_BY_MAX_ID = "select c0.id,c0.channel_name from " 
			+ table + " c0, " + HTS.OPERATIONS_CHANNEL_MEMBER + " m0"
			+ " where c0.id=m0.channel_id and m0.user_id=? and m0.id<=?"
			+ " order by m0.id desc limit ?,?";
	
	private static final String QUERY_CHANNEL_DETAIL = "select " + CHANNEL_DETAIL + " from "
			+ table + " c0," + HTS.USER_INFO + " u0"
			+ " where c0.owner_id=u0.id and c0.id=?";
	
	private static final String QUERY_CHANNEL_ABSTRACT = "select " + CHANNEL_ABSTRACT + " from "
			+ table + " c0 where c0.id=?";
	
	private static final String QUERY_CHANNEL_COUNT_BY_IDS = "select "
			+ "id,world_count,child_count,member_count,superb_count" 
			+ " from " + table + " where id in "; 
	
	private static final String QUERY_SUPERB_CHANNEL = "select " + CHANNEL_ABSTRACT + " from " 
			+ table + " c0 where c0.superb=? order by serial desc limit ?";
	
	
	private static final String ADD_WORLD_AND_CHILD_COUNT = "update " + table
			+ " set world_count=world_count+?, child_count=child_count+?"
			+ " where id=?";
	
	private static final String UPDATE_WORLD_AND_CHILD_COUNT = "update " + table
			+ " set world_count=?, child_count=? where id=?";
	
	private static final String UPDATE_MEMBER_COUNT = "update " + table
			+ " set member_count=? where id=?";
	
	private static final String UPDATE_SUPERB_COUNT = "update " + table
			+ " set superb_count=? where id=?";
	
	private static final String SAVE_CHANNEL = "insert into " + table
			+ " (id,owner_id,channel_name,channel_title,subtitle,channel_desc,"
			+ "channel_icon,sub_icon,channel_type,channel_label,label_ids,create_time,"
			+ "last_modified,danmu,mood,world) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	public OpChannel buildChannel(ResultSet rs) throws SQLException {
		return new OpChannel(
				rs.getInt("id"),
				rs.getInt("owner_id"),
				rs.getString("channel_name"),
				rs.getString("channel_title"),
				rs.getString("subtitle"),
				rs.getString("channel_desc"),
				rs.getString("channel_icon"),
				rs.getString("sub_icon"),
				rs.getInt("channel_type"),
				rs.getString("channel_label"),
				rs.getString("label_ids"),
				rs.getInt("world_count"),
				rs.getInt("child_count"),
				rs.getInt("member_count"),
				rs.getInt("superb_count"),
				TimeUtil.getDate(rs.getLong("create_time")),
				TimeUtil.getDate(rs.getLong("last_modified")),
				rs.getInt("superb"),
				rs.getInt("serial"),
				rs.getInt("danmu"),
				rs.getInt("mood"),
				rs.getInt("world"));
	}
	
	public OpChannelName buildName(ResultSet rs) throws SQLException {
		return new OpChannelName(
				rs.getInt("id"),
				rs.getString("channel_name"));
	}
	
	public OpChannelDetail buildDetail(ResultSet rs) throws SQLException {
		Integer uid = rs.getInt("owner_id");
		UserInfoDto owner = userInfoDao.buildUserInfoDto(uid, rs);
		OpChannelDetail detail =  new OpChannelDetail(
				rs.getInt("id"),
				uid,
				rs.getString("channel_name"),
				rs.getString("channel_title"),
				rs.getString("subtitle"),
				rs.getString("channel_desc"),
				rs.getString("channel_icon"),
				rs.getString("sub_icon"),
				rs.getInt("channel_type"),
				rs.getString("channel_label"),
				rs.getString("label_ids"),
				rs.getInt("world_count"),
				rs.getInt("child_count"),
				rs.getInt("member_count"),
				rs.getInt("superb_count"),
				TimeUtil.getDate(rs.getLong("create_time")),
				TimeUtil.getDate(rs.getLong("last_modified")),
				rs.getInt("superb"),
				rs.getInt("danmu"),
				rs.getInt("mood"),
				rs.getInt("world"));
		detail.setOwner(owner);
		return detail;
	}
	
	public OpChannelCount buildCount(ResultSet rs) throws SQLException {
		return new OpChannelCount(
				rs.getInt("id"),
				rs.getInt("world_count"),
				rs.getInt("child_count"),
				rs.getInt("member_count"),
				rs.getInt("superb_count"));
	}
	
	@Override
	public List<OpChannel> querySubscribedChannel(Integer userId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUBSCRIBED_CHANNEL, 
				new Object[]{userId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannel>() {

					@Override
					public OpChannel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildChannel(rs);
					}
		});
	}

	@Override
	public List<OpChannel> querySubscribedChannel(Integer maxId,
			Integer userId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUBSCRIBED_CHANNEL_BY_MAX_ID, 
				new Object[]{userId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannel>() {

					@Override
					public OpChannel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildChannel(rs);
					}
		});
	}

	@Override
	public OpChannelDetail queryChannelDetail(Integer channelId) {
		return getJdbcTemplate().queryForObject(QUERY_CHANNEL_DETAIL, 
				new Object[]{channelId}, 
				new RowMapper<OpChannelDetail>() {

					@Override
					public OpChannelDetail mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildDetail(rs);
					}
		});
	}
	
	@Override
	public OpChannel queryChannel(Integer channelId) {
		return getJdbcTemplate().queryForObject(QUERY_CHANNEL_ABSTRACT, 
				new Object[]{channelId}, 
				new RowMapper<OpChannel>() {

					@Override
					public OpChannel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildChannel(rs);
					}
		});
	}


	@Override
	public void queryChannelCount(Integer[] channelIds, 
			final RowCallback<OpChannelCount> callback) {
		String inSelection = SQLUtil.buildInSelection(channelIds);
		String sql = QUERY_CHANNEL_COUNT_BY_IDS + inSelection;
		getJdbcTemplate().query(sql, channelIds, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildCount(rs));
			}
		});
	}


	@Override
	public List<OpChannel> querySuperbChannel(Integer limit) {
		return getJdbcTemplate().query(QUERY_SUPERB_CHANNEL, 
				new Object[]{Tag.TRUE, limit}, new RowMapper<OpChannel>() {

					@Override
					public OpChannel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildChannel(rs);
					}
		});
	}


	@Override
	public void addWorldAndChildCount(Integer id, Integer addWorldCount,
			Integer addChildCount) {
		getMasterJdbcTemplate().update(ADD_WORLD_AND_CHILD_COUNT, 
				new Object[]{addWorldCount, addChildCount, id});
	}
	
	@Override
	public void updateWorldAndChildCount(Integer id, Integer worldCount,
			Integer childCount) {
		getMasterJdbcTemplate().update(UPDATE_WORLD_AND_CHILD_COUNT, 
				new Object[]{worldCount, childCount, id});
	}



	@Override
	public List<OpChannelName> querySubscribedName(Integer userId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUBSCRIBED_NAME, 
				new Object[]{userId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelName>() {

					@Override
					public OpChannelName mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildName(rs);
					}
		});
	}


	@Override
	public List<OpChannelName> querySubscribedName(Integer maxId, Integer userId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUBSCRIBED_NAME_BY_MAX_ID, 
				new Object[]{userId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpChannelName>() {

					@Override
					public OpChannelName mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildName(rs);
					}
		});
	}

	
	@Override
	public void saveChannel(OpChannel channel) {
		getMasterJdbcTemplate().update(SAVE_CHANNEL, new Object[]{
				channel.getId(),
				channel.getOwnerId(),
				channel.getChannelName(),
				channel.getChannelTitle(),
				channel.getSubtitle(),
				channel.getChannelDesc(),
				channel.getChannelIcon(),
				channel.getSubIcon(),
				channel.getChannelType(),
				channel.getChannelLabel(),
				channel.getLabelIds(),
				channel.getCreateTime().getTime(),
				channel.getLastModified().getTime(),
				channel.getDanmu(),
				channel.getMood(),
				channel.getWorld()
		});
	}

	@Override
	public void updateMemberCount(Integer id, Integer memberCount) {
		getMasterJdbcTemplate().update(UPDATE_MEMBER_COUNT,
				new Object[]{memberCount, id});
	}

	@Override
	public void updateSuperbCount(Integer id, Integer superbCount) {
		getMasterJdbcTemplate().update(UPDATE_SUPERB_COUNT,
				new Object[]{superbCount, id});
	}

}
