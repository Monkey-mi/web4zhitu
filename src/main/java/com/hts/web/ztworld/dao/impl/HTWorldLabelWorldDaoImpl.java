package com.hts.web.ztworld.dao.impl;

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
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.pojo.HTWorldLabelWorldAuthor;
import com.hts.web.userinfo.dao.impl.UserConcernDaoImpl;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldLabelWorldDao;

/**
 * <p>
 * 标签-织图数据访问对象
 * </p>
 * 
 * 创建时间：2014-5-5
 * @author tianjie
 *
 */
@Repository("HTSHTWorldLabelWorldDao")
public class HTWorldLabelWorldDaoImpl extends BaseDaoImpl implements
		HTWorldLabelWorldDao {
	
	@Autowired
	private HTWorldDao worldDao;
	
	private static String table = HTS.HTWORLD_LABEL_WORLD;
	
	/**
	 * 标签织图作者信息
	 */
	private static final String LABEL_WORLD_AUTHOR_INFO = "u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar_l,u0.sex,u0.user_label,u0.address,u0.province,u0.city,"
			+ "u0.signature,u0.platform_sign,u0.star,u0.platform_verify";
			
	
	/**
	 * 保存标签织图
	 */
	private static final String SAVE_LABEL_WORLD = "insert into " + 
		table +  " (id,world_id,user_id,label_id,valid,serial,weight) values (?,?,?,?,?,?,?)";
	
	/**
	 * 根据标签id查询织图总数
	 */
	private static final String QUERY_WORLD_COUNT_BY_LABEL_ID = "select count(*) from "
			+ table + " where label_id=? and valid=1";
	
	/**
	 * 查询标签织图,根据织图id排序
	 */
	private static final String QUERY_LABEL_WORLD = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from "
			+ "(select h0.*," + U0_INFO + " from " + table + " as lb0," + HTS.USER_INFO + " as u0," + HTS.HTWORLD_HTWORLD + " as h0"
			+ " where h0.author_id=u0.id and h0.id=lb0.world_id and h0.valid=1 and h0.shield=0 and lb0.valid=1 and lb0.label_id=?"
			+ " order by h0.id desc,lb0.weight desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询标签织图,根据织图id排序
	 */
	private static final String QUERY_LABEL_WORLD_BY_MAX_ID = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from "
			+ "(select h0.*," + U0_INFO + " from " + table + " as lb0," + HTS.USER_INFO + " as u0," + HTS.HTWORLD_HTWORLD + " as h0"
			+ " where h0.author_id=u0.id and h0.id=lb0.world_id and h0.valid=1 and h0.shield=0 and lb0.valid=1 and lb0.label_id=? and h0.id<=?"
			+ " order by h0.id desc,lb0.weight desc LIMIT ?,?) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=? and hl0.world_id<=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=? and hk0.world_id<=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询标签织图总数,根据织图id排序
	 */
	private static final String QUERY_LABEL_WORLD_COUNT_BY_MAX_ID = "select count(*) from " + table + " as lb0,"
			+ HTS.HTWORLD_HTWORLD + " as h0 where lb0.world_id=h0.id and h0.valid=1 and h0.shield=0 and lb0.valid=1 and lb0.label_id=? and h0.id<=?";
	
	
	/**
	 * 查询标签织图,根据标签serial排序
	 */
	private static final String QUERY_LABEL_WORLD_V2 = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from "
			+ "(select lb0.serial,h0.*," + U0_INFO + " from " + HTS.USER_INFO + " as u0," + HTS.HTWORLD_HTWORLD + " as h0,"
			+ "(select serial,world_id from " + table + " where label_id=? and (valid=1 or (valid=0 and user_id=?))"
			+ " order by serial desc,weight desc LIMIT ?,? ) as lb0"
			+ " where h0.author_id=u0.id and h0.id=lb0.world_id and h0.valid=1 and h0.shield=0) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询标签织图,根据标签serial排序
	 */
	private static final String QUERY_LABEL_WORLD_BY_MAX_ID_V2 = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from "
			+ "(select lb0.serial,h0.*," + U0_INFO + " from " + HTS.USER_INFO + " as u0," + HTS.HTWORLD_HTWORLD + " as h0,"
			+ "(select serial,world_id from " + table + " where label_id=? and (valid=1 or (valid=0 and user_id=?)) and serial<=?"
			+ " order by serial desc,weight desc LIMIT ?,? ) as lb0"
			+ " where h0.author_id=u0.id and h0.id=lb0.world_id and h0.valid=1 and h0.shield=0) as h"
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 根据最大id查询标签织图总数,根据标签serial排序
	 */
	private static final String QUERY_LABEL_WORLD_COUNT_BY_MAX_ID_V2 = "select count(*) from " + table + " as lb0,"
			+ HTS.HTWORLD_HTWORLD + " as h0 where lb0.world_id=h0.id and h0.valid=1 and h0.shield=0 and lb0.valid=1 and lb0.label_id=? and lb0.serial<=?";
	
	
	/**
	 * 查询标签织图作者
	 */
	private static final String QUERY_LABEL_WORLD_AUTHOR = "select u.*,uc.is_mututal from"
			+ " (select lw1.*," + LABEL_WORLD_AUTHOR_INFO + " from " + HTS.USER_INFO + " as u0,"
			+ "(select max(serial) as serial,label_id,user_id from " + table 
			+ " where label_id=? and (valid=1 or (valid=0 and user_id=?))"
			+ " GROUP BY user_id ORDER BY serial desc limit ?,?) as lw1"
			+ " where u0.id=lw1.user_id) as u"
			+ " left join " + HTS.USER_CONCERN + " as uc on u.user_id=uc.concern_id "
			+ "and uc.user_id=? and uc.valid=1";
	

	/**
	 * 根据最大id查询标签织图作者
	 */
	private static final String QUERY_LABEL_WORLD_AUTHOR_BY_MAX_ID = "select u.*,uc.is_mututal from"
			+ " (select lw1.*," + LABEL_WORLD_AUTHOR_INFO + " from " + HTS.USER_INFO + " as u0,"
			+ "(select max(serial) as serial,label_id,user_id from " + table 
			+ " where label_id=? and (valid=1 or (valid=0 and user_id=?)) and serial<=?"
			+ " GROUP BY user_id ORDER BY serial desc limit ?,?) as lw1"
			+ " where u0.id=lw1.user_id) as u"
			+ " left join " + HTS.USER_CONCERN + " as uc on u.user_id=uc.concern_id and uc.user_id=? and uc.valid=1";
	
	/**
	 * 查询最新参与者SQL头部
	 */
	private static final String QUERY_PARTICIPATOR_HEAD = "select lw0.*," + LABEL_WORLD_AUTHOR_INFO
			+ " from " + HTS.USER_INFO + " as u0, (";
	
	/**
	 * 查询最新参与者SQL中部
	 */
	private static final String QUERY_PARTICIPATOR_MAIN = "(select max(serial) as serial, label_id,user_id from " + table
			+ " where label_id=? and (valid=1 or (valid=0 and user_id=?)) group by user_id order by serial desc limit ?)";
	
	/**
	 * 查询最新参与者SQL尾部
	 */
	private static final String QUERY_PARTICIPATOR_FOOT = " ) as lw0 where u0.id=lw0.user_id";
	
	@Override
	public void saveLabelWorld(HTWorldLabelWorld labelWorld) {
		getMasterJdbcTemplate().update(SAVE_LABEL_WORLD, new Object[]{
			labelWorld.getId(),
			labelWorld.getWorldId(),
			labelWorld.getUserId(),
			labelWorld.getLabelId(),
			labelWorld.getValid(),
			labelWorld.getSerial(),
			labelWorld.getWeight()
		});
	}

	@Override
	public long queryWorldCountByLabelId(Integer labelId) {
		return getJdbcTemplate().queryForLong(QUERY_WORLD_COUNT_BY_LABEL_ID,
				new Object[]{labelId});
	}

	@Override
	public List<HTWorldInteractDto> queryLabelWorld(Integer joinId,
			Integer labelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LABEL_WORLD, 
				new Object[]{labelId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return worldDao.buildHTWorldInteractDtoByResultSet(rs);
					}
		});
	}

	@Override
	public List<HTWorldInteractDto> queryLabelWorld(int maxId, Integer joinId,
			Integer labelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LABEL_WORLD_BY_MAX_ID, 
				new Object[]{labelId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, maxId, joinId, maxId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return worldDao.buildHTWorldInteractDtoByResultSet(rs);
					}
		});
	}

	@Override
	public long queryLabelWorldCount(int maxId, Integer labelId) {
		return getJdbcTemplate().queryForLong(QUERY_LABEL_WORLD_COUNT_BY_MAX_ID, new Object[]{labelId,maxId});
	}
	
	@Override
	public List<HTWorldInteractDto> queryLabelWorldV2(Integer joinId,
			Integer labelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LABEL_WORLD_V2, 
				new Object[]{labelId, joinId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = worldDao.buildHTWorldInteractDtoByResultSet(rs);
						dto.setInteractId(rs.getInt("serial"));
						return dto;
					}
		});
	}

	@Override
	public List<HTWorldInteractDto> queryLabelWorldV2(int maxSerial, Integer joinId,
			Integer labelId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LABEL_WORLD_BY_MAX_ID_V2, 
				new Object[]{labelId,joinId, maxSerial, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId},
				new RowMapper<HTWorldInteractDto>() {

					@Override
					public HTWorldInteractDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HTWorldInteractDto dto = worldDao.buildHTWorldInteractDtoByResultSet(rs);
						dto.setInteractId(rs.getInt("serial"));
						return dto;
					}
		});
	}

	@Override
	public long queryLabelWorldCountV2(int maxSerial, Integer labelId) {
		return getJdbcTemplate().queryForLong(QUERY_LABEL_WORLD_COUNT_BY_MAX_ID_V2, 
				new Object[]{labelId,maxSerial});
	}

	@Override
	public List<HTWorldLabelWorldAuthor> queryLabelWorldAuthor(Integer labelId, 
			Integer joinId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LABEL_WORLD_AUTHOR, 
				new Object[]{labelId, joinId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId}, 
				new RowMapper<HTWorldLabelWorldAuthor>(){

			@Override
			public HTWorldLabelWorldAuthor mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HTWorldLabelWorldAuthor author = buildLabelWorldAuthor(rs);
				author.setIsMututal(UserConcernDaoImpl.getIsMututal(rs));
				return author;
			}
		});
	}

	@Override
	public List<HTWorldLabelWorldAuthor> queryLabelWorldAuthor(int maxId, Integer labelId, Integer joinId, 
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_LABEL_WORLD_AUTHOR_BY_MAX_ID,
				new Object[]{labelId, joinId, maxId, rowSelection.getFirstRow(),rowSelection.getLimit(), joinId}, 
				new RowMapper<HTWorldLabelWorldAuthor>() {

					@Override
					public HTWorldLabelWorldAuthor mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						HTWorldLabelWorldAuthor author = buildLabelWorldAuthor(rs);
						author.setIsMututal(UserConcernDaoImpl.getIsMututal(rs));
						return author;
					}
		});
	}
	
	@Override
	public void queryParticipatorByLimit(Integer joinId, Integer[] activityIds, 
			Integer limit, final RowCallback<HTWorldLabelWorldAuthor> callback) {
		Object[] args = new Object[activityIds.length * 3];
		StringBuilder sqlBuilder = new StringBuilder(QUERY_PARTICIPATOR_HEAD);
		for(int i = 0; i < activityIds.length; i++) {
			if(i != 0) {
				sqlBuilder.append(" union all ");
			}
			sqlBuilder.append(QUERY_PARTICIPATOR_MAIN);
			int k = i * 3;
			args[k] = activityIds[i];
			args[k+1] = joinId;
			args[k+2] = limit;
		}
		sqlBuilder.append(QUERY_PARTICIPATOR_FOOT);
		getJdbcTemplate().query(sqlBuilder.toString(), args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldLabelWorldAuthor user = buildLabelWorldAuthor(rs);
				callback.callback(user);
			}
		});
	}
	
	@Override
	public HTWorldLabelWorldAuthor buildLabelWorldAuthor(ResultSet rs) throws SQLException {
		return new HTWorldLabelWorldAuthor(
				rs.getInt("serial"),
				rs.getInt("label_id"),
				rs.getInt("user_id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("sex"),
				rs.getString("user_label"),
				rs.getString("address"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getString("signature"),
				rs.getString("platform_sign"),
				rs.getInt("star"),
				rs.getInt("platform_verify"));
	}
}
