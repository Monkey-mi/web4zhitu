package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldChannelName;
import com.hts.web.common.pojo.OpWorldType;
import com.hts.web.common.pojo.OpWorldTypeDto;
import com.hts.web.common.pojo.OpWorldTypeDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.dao.SquarePushDao;
import com.hts.web.userinfo.dao.impl.UserInfoDaoImpl;
import com.hts.web.ztworld.dao.HTWorldDao;

/**
 * <p>
 * 广场推送数据访问对象
 * </p>
 * 
 * 创建时间：2013-1-26
 * 
 * @author ztj
 * 
 */
@Repository("HTSSquarePushDao")
public class SquarePushDaoImpl extends BaseDaoImpl implements SquarePushDao {


	private static final String table = HTS.HTWORLD_TYPE_WORLD;

	private static final String ORDER_BY_HW_SERIAL_DESC = " order by hw.serial desc";
	
	/**
	 * 精品信息
	 */
	private static final String SUPERB_INFO = "hw.serial,hw.world_id,hw.date_modify as rec_date_modify,"
			+ "hw.type_id as rec_type_id,hw.review";
	
	/**
	 * 查询广场织图SQL头部
	 */
	private static final String QUERY_SQUARE_HEAD = "select "+SUPERB_INFO+", h.*," + U0_INFO + " from " 
			+ HTS.HTWORLD_HTWORLD +" as h, " + table + " as hw, "
			+ HTS.USER_INFO + " as u0"
			+ " where h.id=hw.world_id"
			+ " and h.author_id=u0.id";
	
	/**
	 * 查询广场织图列表
	 */
	private static final String QUERY_SQUARE = QUERY_SQUARE_HEAD 
			+ " and h.valid=? and h.shield=? and hw.valid=?" + ORDER_BY_HW_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询广场织图列表
	 */
	private static final String QUERY_SQUARE_BY_MAX_SERIAL = QUERY_SQUARE_HEAD 
			+ " and h.valid=? and h.shield=? and hw.valid=? and hw.serial<=?" + ORDER_BY_HW_SERIAL_DESC;
	
	/**
	 * 根据最小序号查询广场织图列表
	 */
	private static final String QUERY_SQUARE_BY_MIN_SERIAL = QUERY_SQUARE_HEAD 
			+ " and h.valid=? and h.shield=? and hw.valid=? and hw.serial>?" + ORDER_BY_HW_SERIAL_DESC;
	
	/**
	 * 查询广场织图总数头部
	 */
	private static final String QUERY_SQUARE_COUNT_HEAD = "select count(*) from " 
			+ HTS.HTWORLD_HTWORLD + " as h," + table +" as hw" 
			+ " where h.id=hw.world_id";
	
	/**
	 * 根据最大序号查询广场织图总数
	 */
	private static final String QUERY_SQUARE_COUNT_BY_MAX_SERIAL = QUERY_SQUARE_COUNT_HEAD 
			+ " and h.valid=? and h.shield=? and hw.valid=? and hw.serial<=?";
	
	/**
	 * 根据最小序号查询广场织图总数
	 */
	private static final String QUERY_SQUARE_COUNT_BY_MIN_SREIAL = QUERY_SQUARE_COUNT_HEAD 
			+ " and h.valid=? and h.shield=? and hw.valid=? and hw.serial>?";
	
	/**
	 * 查询广场分类织图SQL头部
	 */
	private static final String QUERY_LABEL_SQUARE_HEAD = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from"
			+ " (select " + SUPERB_INFO + ", h0.*," + U0_INFO + " from " 
			+ HTS.HTWORLD_HTWORLD +" as h0, " + table + " as hw, "
			+ HTS.USER_INFO + " as u0"
			+ " where h0.id=hw.world_id"
			+ " and h0.author_id=u0.id"
			+ " and hw.type_id=? and h0.valid=? and h0.shield=? and hw.valid=?";
	
	/**
	 * 查询广场分类织图SQL尾部
	 */
	private static final String QUERY_LABEL_SQUARE_FOOT = ORDER_BY_HW_SERIAL_DESC + " limit ?,?) as h "
			+ " left join (select * from " + HTS.HTWORLD_LIKED + " hl0 where hl0.valid=1 and hl0.user_id=?) as hl on h.id = hl.world_id"
			+ " left join (select * from " + HTS.HTWORLD_KEEP + "  hk0 where hk0.valid=1 and hk0.user_id=?) as hk on h.id = hk.world_id";
	
	/**
	 * 查询广场分类织图
	 */
	private static final String QUERY_TYPE_SQUARE = QUERY_LABEL_SQUARE_HEAD 
			+ QUERY_LABEL_SQUARE_FOOT;
	
	/**
	 * 根据最大序号查询广场分类织图
	 */
	private static final String QUERY_TYPE_SQUARE_BY_MAX_SERIAL = QUERY_LABEL_SQUARE_HEAD 
			+ " and hw.serial<=?" + QUERY_LABEL_SQUARE_FOOT;
	
	/**
	 * 查询广场精品分类织图SQL头部
	 */
	private static final String QUERY_SUPERB_SQUARE_HEAD = "select h.*, 1-ISNULL(hl.user_id) as liked, 1-ISNULL(hk.user_id) as keep from"
			+ " (select " + SUPERB_INFO + ", h0.*," + U0_INFO + " from " 
			+ HTS.HTWORLD_HTWORLD +" as h0, " + table + " as hw, "
			+ HTS.USER_INFO + " as u0"
			+ " where h0.id=hw.world_id"
			+ " and h0.author_id=u0.id"
			+ " and h0.valid=? and h0.shield=? and hw.valid=?";
	
	/**
	 * 查询广场精品分类织图V1，只有方图
	 */
	private static final String QUERY_SUPERB_TYPE_SQUARE_V1 = QUERY_SUPERB_SQUARE_HEAD 
			+ " and h0.ver=1 and hw.superb=1"+ QUERY_LABEL_SQUARE_FOOT;
	
	/**
	 * 根据最大序号查询广场分类织图V1，只有方图
	 */
	private static final String QUERY_SUPERB_TYPE_SQUARE_BY_MAX_SERIAL_V1 =  QUERY_SUPERB_SQUARE_HEAD 
			+ " and h0.ver=1 and hw.superb=1 and hw.serial<=?" + QUERY_LABEL_SQUARE_FOOT;
	
	/**
	 * 根据最小序号查询广场分类织图
	 */
	private static final String QUERY_TYPE_SQUARE_COUNT_HEAD = "select count(*) from " 
			+ HTS.HTWORLD_HTWORLD + " as h," + table +" as hw" 
			+ " where h.id=hw.world_id and hw.type_id=? and h.valid=? and h.shield=? and hw.valid=?";
	
	/**
	 * 查询广场分类织图总数
	 */
	private static final String QUERY_TYPE_SQUARE_COUNT = QUERY_TYPE_SQUARE_COUNT_HEAD;
	
	/**
	 * 根据最大序号查询广场分类织图总数
	 */
	private static final String QUERY_TYPE_SQUARE_COUNT_BY_MAX_SERIAL = QUERY_TYPE_SQUARE_COUNT_HEAD 
			+ " and hw.serial<=?";
	
	
	/**
	 * 查询普通广场分类织图
	 */
	private static final String QUERY_NORMAL_TYPE_SQUARE = QUERY_LABEL_SQUARE_HEAD 
			+ " and hw.superb=?"  + QUERY_LABEL_SQUARE_FOOT;
	
	/**
	 * 根据最大序号查询普通广场分类织图
	 */
	private static final String QUERY_NORMAL_TYPE_SQUARE_BY_MAX_SERIAL = QUERY_LABEL_SQUARE_HEAD 
			+ "  and hw.superb=? and hw.serial<=?" + QUERY_LABEL_SQUARE_FOOT;
	
	/**
	 * 根据最大序号查询广场分类织图总数
	 */
	private static final String QUERY_NORMAL_TYPE_SQUARE_COUNT_BY_MAX_SERIAL = QUERY_TYPE_SQUARE_COUNT_HEAD 
			+ " and hw.superb=? and hw.serial<=?";
	
	
	/**
	 * 查询广场织图最大序号
	 */
	private static final String QUERY_MAX_SQUARE_SERIAL = "select max(serial) from " + table;
	
	/**
	 * 随机查询广场织图
	 */
	private static final String QUERY_RANDOM_SQUARE = "select "+SUPERB_INFO+", h.*," + U0_INFO + " from " 
			+ HTS.HTWORLD_HTWORLD +" as h, " 
			+ "(select * from " + table 
			+ " as hw1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM " +table+ " where valid=1)-(SELECT MIN(id) FROM " 
			+ table + " where valid=1))+(SELECT MIN(id) FROM " 
			+ table + " where valid=1) - ?) AS rand_id) AS hw2 WHERE hw1.id >= hw2.rand_id ORDER BY hw1.id LIMIT ?)"
			+ " as hw, "
			+ HTS.USER_INFO + " as u0"
			+ " where h.id=hw.world_id"
			+ " and h.author_id=u0.id and hw.valid=1";
	
	
	private static final String QUERY_SUPERB_BY_WID = "select " + SUPERB_INFO + ", h.*," + U0_INFO 
			+ " from " + HTS.USER_INFO + " as u0,"+ HTS.HTWORLD_HTWORLD +" as h, " + table 
			+ " as hw where h.id=hw.world_id and h.author_id=u0.id"
			+ " and hw.valid=1 and hw.world_id=?";
	
	
	private static final String QUERY_SUPERB_V4 = "select " + SUPERB_INFO + ", h.*," + U0_INFO
			+ " from " + HTS.HTWORLD_HTWORLD +" as h, " + table + " as hw, " + HTS.USER_INFO + " as u0"
			+ " where h.id=hw.world_id and h.author_id=u0.id and hw.valid=1"
			+ " order by hw.serial desc limit ?,?";
	
	private static final String QUERY_SUPERB_BY_MAXID_V4 = "select " + SUPERB_INFO + ", h.*," + U0_INFO 
			+ " from " + HTS.HTWORLD_HTWORLD +" as h, " + table + " as hw, " + HTS.USER_INFO + " as u0"
			+ " where h.id=hw.world_id and h.author_id=u0.id and hw.valid=1 and hw.serial<=?"
			+ " order by hw.serial desc limit ?,?";
	
	
	private static final String QUERY_TYPE_SUPERB_V4 = "select " + SUPERB_INFO + ", h.*," + U0_INFO 
			+ " from " + HTS.HTWORLD_HTWORLD +" as h, " + table + " as hw, " + HTS.USER_INFO + " as u0"
			+ " where h.id=hw.world_id and h.author_id=u0.id and hw.valid=1 and hw.type_id=?"
			+ " order by hw.serial desc limit ?,?";
	
	
	private static final String QUERY_TYPE_SUPERB_BY_MAX_ID_V4 = "select " + SUPERB_INFO + ", h.*," + U0_INFO 
			+ " from " + HTS.HTWORLD_HTWORLD +" as h, " + table + " as hw, " + HTS.USER_INFO + " as u0"
			+ " where h.id=hw.world_id and h.author_id=u0.id and hw.valid=1 and hw.type_id=? and hw.serial<=?"
			+ " order by hw.serial desc limit ?,?";
	
	private static final String QUERY_WEIGHT_SUPERB_V4 = "select " + SUPERB_INFO + ", h.*," + U0_INFO
			+ " from " + HTS.HTWORLD_HTWORLD +" as h, " + table + " as hw, " + HTS.USER_INFO + " as u0"
			+ " where h.id=hw.world_id and h.author_id=u0.id and hw.valid=1 and hw.weight>0"
			+ " order by hw.weight desc limit ?";
	
	@Autowired
	private HTWorldDao worldDao;
	
	
	@Override
	public List<OpWorldTypeDto> querySquare(int typeId, Integer joinId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_TYPE_SQUARE, new Object[]{typeId, Tag.TRUE, Tag.FALSE, Tag.TRUE,
				rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setLiked(rs.getObject("liked"));
						dto.setKeep(rs.getObject("keep"));
						if(dto.getAuthorId() != 0) {
							dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						}
						return dto;
					}
		});
	}

	@Override
	public List<OpWorldTypeDto> querySquareByMaxId(int maxId, int typeId, Integer joinId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_TYPE_SQUARE_BY_MAX_SERIAL, new Object[]{typeId, Tag.TRUE, Tag.FALSE, Tag.TRUE, maxId,
				rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setLiked(rs.getObject("liked"));
						dto.setKeep(rs.getObject("keep"));
						if(dto.getAuthorId() != 0) {
							dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						}
						return dto;
					}
		});
	}
	
	@Override
	public long querySquareCount(int typeId) {
		return getJdbcTemplate().queryForLong(QUERY_TYPE_SQUARE_COUNT,
				new Object[]{typeId, Tag.TRUE,Tag.FALSE, Tag.TRUE});
	}
	
	@Override
	public long querySquareCountByMaxId(int maxId, int typeId) {
		return getJdbcTemplate().queryForLong(QUERY_TYPE_SQUARE_COUNT_BY_MAX_SERIAL,
				new Object[]{typeId, Tag.TRUE,Tag.FALSE, Tag.TRUE, maxId});
	}

	
	@Override
	public List<OpWorldTypeDto> querySuperbTypeSquare(Integer joinId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUPERB_TYPE_SQUARE_V1,
				new Object[]{Tag.TRUE, Tag.FALSE, Tag.TRUE,
				rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setLiked(rs.getObject("liked"));
						dto.setKeep(rs.getObject("keep"));
						if(dto.getAuthorId() != 0) {
							dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						}
						return dto;
					}
		});
	}

	@Override
	public List<OpWorldTypeDto> querySuperbTypeSquare(Integer maxSerial, Integer joinId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUPERB_TYPE_SQUARE_BY_MAX_SERIAL_V1, 
				new Object[]{Tag.TRUE, Tag.FALSE, Tag.TRUE, maxSerial,
				rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setLiked(rs.getObject("liked"));
						dto.setKeep(rs.getObject("keep"));
						if(dto.getAuthorId() != 0) {
							dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						}
						return dto;
					}
		});
	}
	
	@Override
	public List<OpWorldTypeDto> querySuperbFromMaster(RowSelection rowSelection) {
		return getMasterJdbcTemplate().query(QUERY_SUPERB_V4,
				new Object[]{rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						return dto;
					}
		});
	}
	
	@Override
	public List<OpWorldTypeDto> querySuperbV4(RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUPERB_V4,
				new Object[]{rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						return dto;
					}
		});
	}
	
	@Override
	public List<OpWorldTypeDto> querySuperbV4(Integer maxSerial, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_SUPERB_BY_MAXID_V4, 
				new Object[]{maxSerial, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						return dto;
					}
		});
	}
	
	@Override
	public List<OpWorldTypeDto> querySuperbByTypeIdV4(Integer typeId, 
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_TYPE_SUPERB_V4, 
				new Object[]{typeId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						return dto;
					}
		});
	}

	@Override
	public List<OpWorldTypeDto> querySuperbByTypeIdV4(Integer maxSerial, Integer typeId, 
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_TYPE_SUPERB_BY_MAX_ID_V4, 
				new Object[]{typeId, maxSerial, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						return dto;
					}
		});
	}

	
	@Override
	public List<OpWorldTypeDto> querySquare(RowSelection rowSelection) {
		return queryForPage(QUERY_SQUARE, new Object[]{Tag.TRUE, Tag.FALSE, Tag.TRUE},
			new RowMapper<OpWorldTypeDto>(){
	
				@Override
				public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OpWorldTypeDto dto = buildSquareDto(rs);
					if(dto.getAuthorId() != 0) {
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
					}
					return dto;
				}
		
			},rowSelection);
	}

	@Override
	public List<OpWorldTypeDto> querySquareByMaxSerial(int maxId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_SQUARE_BY_MAX_SERIAL, 
				new Object[]{Tag.TRUE, Tag.FALSE, Tag.TRUE, maxId}, 
				new RowMapper<OpWorldTypeDto>() {

				@Override
				public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OpWorldTypeDto dto = buildSquareDto(rs);
					if(dto.getAuthorId() != 0) {
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
					}
					return dto;
				}
			}, rowSelection);
	}
	
	@Override
	public List<OpWorldTypeDto> querySquareByMinSerial(int minId, RowSelection rowSelection) {
		return queryForPage(QUERY_SQUARE_BY_MIN_SERIAL, new Object[]{
				Tag.TRUE,Tag.FALSE, Tag.TRUE, minId
			}, new RowMapper<OpWorldTypeDto>() {
	
				@Override
				public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OpWorldTypeDto dto = buildSquareDto(rs);
					if(dto.getAuthorId() != 0) {
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
					}
					return dto;
				}
			}, rowSelection);
	}

	@Override
	public long querySquareCountByMaxSerial(int maxId) {
		return getJdbcTemplate().queryForLong(QUERY_SQUARE_COUNT_BY_MAX_SERIAL,
				new Object[]{Tag.TRUE,Tag.FALSE, Tag.TRUE, maxId});
	}

	@Override
	public long querySquareCountByMinId(int minId) {
		return getJdbcTemplate().queryForLong(QUERY_SQUARE_COUNT_BY_MIN_SREIAL, 
				new Object[]{Tag.TRUE,Tag.FALSE, Tag.TRUE, minId});
	}
	
	@Override
	public List<OpWorldTypeDto> querySquare(RowSelection rowSelection, String[] label) {
		String inSelection = SQLUtil.buildInSelection(label);
		String sql = QUERY_SQUARE_HEAD + " and world_label in " + inSelection + " and h.valid=? and h.shield=? and hw.valid=?" + ORDER_BY_HW_SERIAL_DESC;
		Object[] args = SQLUtil.getArgsByInCondition(label, new Object[]{Tag.TRUE, Tag.FALSE, Tag.TRUE}, true);
		return queryForPage(sql, args,
			new RowMapper<OpWorldTypeDto>(){
	
				@Override
				public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OpWorldTypeDto dto = buildSquareDto(rs);
					if(dto.getAuthorId() != 0) {
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
					}
					return dto;
				}
			},rowSelection);
	}
	
	@Override
	public List<OpWorldTypeDto> querySquareByMaxId(int maxId,
			RowSelection rowSelection, String[] label) {
		String inSelection = SQLUtil.buildInSelection(label);
		String sql = QUERY_SQUARE_HEAD + " and world_label in " + inSelection + " and h.valid=? and h.shield=? and hw.valid=? and hw.serial<=?" + ORDER_BY_HW_SERIAL_DESC;
		Object[] args = SQLUtil.getArgsByInCondition(label, new Object[]{Tag.TRUE, Tag.FALSE, Tag.TRUE, maxId}, true);
		return queryForPage(sql, args, new RowMapper<OpWorldTypeDto>() {

				@Override
				public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OpWorldTypeDto dto = buildSquareDto(rs);
					if(dto.getAuthorId() != 0) {
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
					}
					return dto;
				}
			}, rowSelection);
	}
	
	@Override
	public List<OpWorldTypeDto> querySquareByMinId(int minId,
			RowSelection rowSelection, String label[]) {
		String inSelection = SQLUtil.buildInSelection(label);
		String sql = QUERY_SQUARE_HEAD + " and world_label in " + inSelection + " and h.valid=? and h.shield=? and hw.valid=? and hw.serial>?" + ORDER_BY_HW_SERIAL_DESC;
		Object[] args = SQLUtil.getArgsByInCondition(label, new Object[]{Tag.TRUE,Tag.FALSE, Tag.TRUE, minId}, true);
		return queryForPage(sql, args, new RowMapper<OpWorldTypeDto>() {
	
				@Override
				public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OpWorldTypeDto dto = buildSquareDto(rs);
					if(dto.getAuthorId() != 0) {
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
					}
					return dto;
				}
			}, rowSelection);
	}

	@Override
	public long querySquareCountByMaxId(int maxId, String[] label) {
		String inSelection = SQLUtil.buildInSelection(label);
		String sql = QUERY_SQUARE_COUNT_HEAD + " and world_label in " + inSelection + " and h.valid=? and h.shield=? and hw.valid=? and hw.serial<=?";
		Object[] args = SQLUtil.getArgsByInCondition(label, new Object[]{Tag.TRUE,Tag.FALSE, Tag.TRUE, maxId}, true);
		return getJdbcTemplate().queryForLong(sql, args);
	}

	@Override
	public long querySquareCountByMinId(int minId, String[] label) {
		String inSelection = SQLUtil.buildInSelection(label);
		String sql = QUERY_SQUARE_COUNT_HEAD + " and world_label in " + inSelection + " and h.valid=? and h.shield=? and hw.valid=? and hw.serial>?";
		Object[] args = SQLUtil.getArgsByInCondition(label, new Object[]{Tag.TRUE,Tag.FALSE, Tag.TRUE, minId}, true);
		return getJdbcTemplate().queryForLong(sql, args);
	}
	
	@Override
	public List<OpWorldTypeDto> queryNormalSquare(Integer typeId, Integer joinId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_NORMAL_TYPE_SQUARE, new Object[]{typeId, Tag.TRUE,Tag.FALSE, Tag.TRUE, Tag.FALSE,
				rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setLiked(rs.getObject("liked"));
						dto.setKeep(rs.getObject("keep"));
						if(dto.getAuthorId() != 0) {
							dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						}
						return dto;
					}
		});
	}

	@Override
	public List<OpWorldTypeDto> queryNormalSquareByMaxSerial(int maxSerial, Integer typeId, Integer joinId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_NORMAL_TYPE_SQUARE_BY_MAX_SERIAL, new Object[]{typeId, Tag.TRUE,Tag.FALSE, Tag.TRUE, Tag.FALSE, maxSerial,
				rowSelection.getFirstRow(), rowSelection.getLimit(), joinId, joinId}, 
				new RowMapper<OpWorldTypeDto>(){

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int num)
							throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setLiked(rs.getObject("liked"));
						dto.setKeep(rs.getObject("keep"));
						if(dto.getAuthorId() != 0) {
							dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						}
						return dto;
					}
		});
	}

	@Override
	public long queryNormalSquareCountByMaxSerial(int maxSerial, Integer typeId) {
		return getJdbcTemplate().queryForLong(QUERY_NORMAL_TYPE_SQUARE_COUNT_BY_MAX_SERIAL, 
				new Object[]{typeId, Tag.TRUE,Tag.FALSE, Tag.TRUE, Tag.FALSE, maxSerial});
	}

	@Override
	public List<OpWorldTypeDto> queryRandomSquare(int limit) {
		return getJdbcTemplate().query(QUERY_RANDOM_SQUARE, new Object[]{limit, limit}, new RowMapper<OpWorldTypeDto>() {

			@Override
			public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OpWorldTypeDto dto = buildSquareDto(rs);
				if(dto.getAuthorId() != 0) {
					dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
				}
				return dto;
			}
		});
	}

	@Override
	public Integer queryMaxSquareSerial() {
		return getJdbcTemplate().queryForInt(QUERY_MAX_SQUARE_SERIAL);
	}

	
	/**
	 * 构建OpSquareDto
	 * 
	 * @param rs
	 * @param urlPrefix
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public OpWorldTypeDto buildSquareDto(ResultSet rs) throws SQLException{
		List<HTWorldChannelName> channelNames = 
				worldDao.formatChannelNames(
						rs.getString("channel_name"),
						rs.getString("channel_id"));
		OpWorldTypeDto dto = new OpWorldTypeDto(
				rs.getInt("serial"),
				rs.getInt("rec_type_id"),
				(Date)rs.getObject("rec_date_modify"),
				rs.getString("review"),
				rs.getInt("world_id"),
				rs.getString("short_link"),
				rs.getString("world_name"),
				rs.getString("world_desc"),
				rs.getString("world_label"),
				rs.getString("world_type"),
				rs.getInt("type_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_modified"),
				rs.getInt("author_id"),
				rs.getInt("click_count"),
				rs.getInt("like_count"),
				rs.getInt("comment_count"),
				rs.getInt("keep_count"),
				rs.getString("cover_path"),
				rs.getString("title_path"),
				rs.getString("bg_path"),
				rs.getString("title_thumb_path"),
				channelNames,
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
				JSONUtil.getJSObjectFromText(rs.getString("text_style")));
		dto.setWorldId(dto.getId());
		if(urlPrefix != null) {
			if(dto.getShortLink() != null) {
				dto.setWorldURL(urlPrefix + dto.getShortLink()); 
			} else {
				dto.setWorldURL(urlPrefix + dto.getId());
			}
		}
		return dto;
	}

	@Override
	public OpWorldTypeDto querySuperByWID(int wid) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_SUPERB_BY_WID,
					new Object[]{wid}, new RowMapper<OpWorldTypeDto>() {
	
				@Override
				public OpWorldTypeDto mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OpWorldTypeDto dto = buildSquareDto(rs);
					dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
					return dto;
				}
			});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<OpWorldTypeDto> queryWeightSuperbFromMaster(int limit) {
		return getMasterJdbcTemplate().query(QUERY_WEIGHT_SUPERB_V4,
				new Object[]{limit}, new RowMapper<OpWorldTypeDto>() {

					@Override
					public OpWorldTypeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						OpWorldTypeDto dto = buildSquareDto(rs);
						dto.setUserInfo(UserInfoDaoImpl.buildUserInfoDtoByResult(dto.getAuthorId(), rs));
						return dto;
					}

					
		});
	}

}
