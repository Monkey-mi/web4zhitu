package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.OpActivityWinnerDto;
import com.hts.web.operations.dao.ActivityWinnerDao;
import com.hts.web.ztworld.dao.HTWorldDao;

@Repository("HTSActivityWinnerDao")
public class ActivityWinnerDaoImpl extends BaseDaoImpl implements ActivityWinnerDao {

	private static final String table = HTS.OPERATIONS_ACTIVITY_WINNER;
	
	/**
	 * 查询活动织图
	 */
	private static final String QUERY_ACTIVITY_WORLD = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from "
			+ "(select h0.*," + U0_INFO + " from " + table + " as lb0," + HTS.USER_INFO + " as u0," + HTS.HTWORLD_HTWORLD + " as h0"
			+ " where h0.author_id=u0.id and h0.id=lb0.world_id and h0.valid=1 and h0.shield=0 and lb0.activity_id=?"
			+ " order by h0.id desc,lb0.weight desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询活动织图
	 */
	private static final String QUERY_ACTIVITY_WORLD_BY_MAX_ID = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from "
			+ "(select h0.*," + U0_INFO + " from " + table + " as lb0," + HTS.USER_INFO + " as u0," + HTS.HTWORLD_HTWORLD + " as h0"
			+ " where h0.author_id=u0.id and h0.id=lb0.world_id and h0.valid=1 and h0.shield=0 and lb0.activity_id=? and h0.id<=?"
			+ " order by h0.id desc,lb0.weight desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.user_id=? and hl0.world_id<=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=? and hk0.world_id<=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询活动织图总数
	 */
	private static final String QUERY_ACTIVITY_WORLD_COUNT_BY_MAX_ID = "select count(*) from " + table + " as lb0,"
			+ HTS.HTWORLD_HTWORLD + " as h0 where lb0.world_id=h0.id and h0.valid=1 and h0.shield=0 and lb0.activity_id=? and h0.id<=?";
	
	
	/**
	 * 查询活动织图
	 */
	private static final String QUERY_ACTIVITY_WORLD_V2 = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from "
			+ "(select lb0.serial,lb0.award_id,awa0.award_name,awa0.icon_thumb_path,h0.*," + U0_INFO + " from " + table + " as lb0," + HTS.USER_INFO + " as u0," + HTS.HTWORLD_HTWORLD + " as h0,"
			+ HTS.OPERATIONS_ACTIVITY_AWARD + " as awa0"
			+ " where h0.author_id=u0.id and h0.id=lb0.world_id and lb0.award_id=awa0.id and h0.valid=1 and h0.shield=0 and lb0.activity_id=?"
			+ " order by lb0.serial desc,lb0.weight desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询活动织图
	 */
	private static final String QUERY_ACTIVITY_WORLD_BY_MAX_ID_V2 = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from "
			+ "(select lb0.serial,lb0.award_id,awa0.award_name,awa0.icon_thumb_path,h0.*," + U0_INFO + " from " + table + " as lb0," + HTS.USER_INFO + " as u0," + HTS.HTWORLD_HTWORLD + " as h0,"
			+ HTS.OPERATIONS_ACTIVITY_AWARD + " as awa0"
			+ " where h0.author_id=u0.id and h0.id=lb0.world_id and lb0.award_id=awa0.id and h0.valid=1 and h0.shield=0 and lb0.activity_id=? and lb0.serial<=?"
			+ " order by lb0.serial desc,lb0.weight desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询活动织图总数
	 */
	private static final String QUERY_ACTIVITY_WORLD_COUNT_BY_MAX_ID_V2 = "select count(*) from " + table + " as lb0,"
			+ HTS.HTWORLD_HTWORLD + " as h0 where lb0.world_id=h0.id and h0.valid=1 and h0.shield=0 and lb0.activity_id=? and lb0.serial<=?";
	
	/**
	 * 查询最新获胜织图记录id
	 */
	private static final String QUERY_MAX_WINNER_IDS = "select max(id) as id, activity_id from " + table
			+ " where activity_id in ";
	
	/**
	 * 查询最新获胜织图记录idSQL尾部
	 */
	private static final String QUERY_MAX_WINNER_IDS_FOOT = " group by activity_id";
	
	/**
	 * 查询最新获奖织图记录id
	 */
	private static final String QUERY_MAX_WINNER_ID = "select MAX(id) from " + table
			+ " where activity_id=?";
	
	@Autowired
	private HTWorldDao worldDao;

	@Override
	public List<HTWorldInteractDto> queryActivityWorld(Integer joinId,
			Integer activityId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_ACTIVITY_WORLD, 
				new Object[]{activityId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return worldDao.buildHTWorldInteractDtoByResultSet(rs);
					};
			});
	}

	@Override
	public List<HTWorldInteractDto> queryActivityWorld(int maxId,
			Integer joinId, Integer activityId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_ACTIVITY_WORLD_BY_MAX_ID, 
				new Object[]{activityId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, maxId, joinId, maxId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return worldDao.buildHTWorldInteractDtoByResultSet(rs);
					}
		});
	}

	@Override
	public long queryActivityWorldCount(int maxId, Integer activityId) {
		return getJdbcTemplate().queryForLong(QUERY_ACTIVITY_WORLD_COUNT_BY_MAX_ID, new Object[]{activityId,maxId});
	}
	
	@Override
	public List<HTWorldInteractDto> queryActivityWorldV2(Integer joinId,
			Integer activityId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_ACTIVITY_WORLD_V2, 
				new Object[]{activityId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = worldDao.buildHTWorldInteractDtoByResultSet(rs);
						dto.setExtra(String.valueOf(rs.getInt("award_id")));
						dto.setExtra1(rs.getString("icon_thumb_path"));
						dto.setInteractId(rs.getInt("serial"));
						return dto;
					};
			});
	}

	@Override
	public List<HTWorldInteractDto> queryActivityWorldV2(int maxSerial,
			Integer joinId, Integer activityId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_ACTIVITY_WORLD_BY_MAX_ID_V2, 
				new Object[]{activityId, maxSerial, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = worldDao.buildHTWorldInteractDtoByResultSet(rs);
						dto.setExtra(String.valueOf(rs.getInt("award_id")));
						dto.setExtra1(rs.getString("icon_thumb_path"));
						dto.setInteractId(rs.getInt("serial"));
						return dto;
					}
		});
	}

	@Override
	public long queryActivityWorldCountV2(int maxSerial, Integer activityId) {
		return getJdbcTemplate().queryForLong(QUERY_ACTIVITY_WORLD_COUNT_BY_MAX_ID_V2,
				new Object[]{activityId,maxSerial});
	}

	@Override
	public void queryMaxWinnerId(Integer[] activityIds,
			final RowCallback<OpActivityWinnerDto> callback) {
		String inSelection = SQLUtil.buildInSelection(activityIds);
		String sql = QUERY_MAX_WINNER_IDS + inSelection + QUERY_MAX_WINNER_IDS_FOOT;
		getJdbcTemplate().query(sql, activityIds, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				OpActivityWinnerDto dto = new OpActivityWinnerDto(rs.getInt("id"),
						rs.getInt("activity_id"));
				callback.callback(dto);
			}
			
		});
	}
	
	

	@Override
	public Integer queryMaxWinnerId(Integer activityId) {
		return getJdbcTemplate().queryForInt(QUERY_MAX_WINNER_ID, activityId);
	}
	
}
