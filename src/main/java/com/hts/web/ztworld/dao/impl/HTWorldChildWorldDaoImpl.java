package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldChildThumbnail;
import com.hts.web.common.pojo.HTWorldChildWorld;
import com.hts.web.common.pojo.HTWorldChildWorldDto;
import com.hts.web.common.pojo.HTWorldChildWorldThumb;
import com.hts.web.ztworld.dao.HTWorldChildWorldDao;

/**
 * <p>
 * 子世界数据访问对象
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
@Repository("HTSHTWorldChildWorldDao")
public class HTWorldChildWorldDaoImpl extends BaseDaoImpl implements HTWorldChildWorldDao{

	
	/**
	 * 子世界表
	 */
	public static String table = HTS.HTWORLD_CHILD_WORLD;

	/**
	 * 保存子世界信息
	 */
	private static final String SAVE_CHILD_WORLD = "INSERT INTO " + table
			+ " (id, child_world_desc, world_id, path, width, height, is_title, coordinate_x,coordinate_y,"
			+ " at_id, thumb_path, angle, type, type_path) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/**
	 * 根据所在世界ID查询子世界信息
	 */
	private static final String QUERY_CHILD_WORLD_BY_WORLD_ID = "SELECT * from " + table + " where world_id=? order by id asc";
	
	/**
	 * 查询缩略图信息
	 */
	private static final String QUERY_CHILD_THUMBS_BY_AT_ID = "SELECT * from " + table + " where at_id=?";
	
	/**
	 * 查询缩略图信息
	 */
	private static final String QUERY_CHILD_THUMBS_MAP_BY_AT_ID = "SELECT * from " + table + " where at_id in";
	
	/**
	 * 根据所在世界ID查询子世界总数
	 */
	private static final String QUERY_CHILD_WORLD_COUNT_BY_WORLD_ID = "SELECT count(*) from " + table + " where world_id=?";
	
	/**
	 * 查询子世界索引
	 */
	private static final String QUERY_CHILD_WORLD_INDEX = "SELECT count(*) from " + table + " where world_id=? and id<=?";
	
	/**
	 * 查询封面子世界
	 */
	private static final String QUERY_TITLE_CHILD_WORLD = "SELECT id, thumb_path from " + table + " where world_id=? and is_title=?";
	
	/**
	 * 查询封面世界信息
	 */
	private static final String QUERY_TITLE_CHILD_WORLD_BY_WORLD_ID = "SELECT * from " + table + " where world_id=? and is_title=?";
	
	/**
	 * 根据所在子世界id查询其拥有的所有子世界信息
	 */
	private static final String QUERY_CHILD_WORLD_BY_AT_ID = "SELECT * from " + table + " where at_id=?";
	
	/**
	 * 根据wids删除子世界
	 */
	private static final String DELETE_BY_WORLD_IDS = "delete from " + table + " where world_id in ";
	
	/**
	 * 查询所有子世界
	 */
	private static final String QUERY_ALL_CHILD = "select * from " + table + " where world_id=?";
	
	/**
	 * 查询指定用户的子世界缩略图SQL头部
	 */
	private static final String QUERY_THUMBNAIL_HEAD = "select id, world_id, path from " 
			+ table + " where author_id=?";
	
	/**
	 * 查询某用户的子世界缩略图
	 */
	private static final String QUERY_THUMBNAIL = QUERY_THUMBNAIL_HEAD + ORDER_BY_ID_DESC;
	
	/**
	 * 根据最大id查询某用户的子世界缩略图
	 */
	private static final String QUERY_THUMBNAIL_BY_MAX_ID = QUERY_THUMBNAIL_HEAD 
			+ " and id<=? "+ ORDER_BY_ID_DESC;
	
	@Override
	public void saveChildWorld(HTWorldChildWorld childWorld) {
		getJdbcTemplate().update(SAVE_CHILD_WORLD, new Object[] { 
				childWorld.getId(),
				childWorld.getChildWorldDesc(),
				childWorld.getWorldId(), 
				childWorld.getPath(),
				childWorld.getWidth(), 
				childWorld.getHeight(),
				childWorld.getIsTitle(), 
				childWorld.getCoordinatex(),
				childWorld.getCoordinatey(), 
				childWorld.getAtId(),
				childWorld.getThumbPath(),
				childWorld.getAngle(),
				childWorld.getType(),
				childWorld.getTypePath()});
	}

	@Override
	public List<HTWorldChildWorldDto> queryShowChildWorldDtosByWorldId(Integer worldId,RowSelection rowSelection) {
		return queryForPage(QUERY_CHILD_WORLD_BY_WORLD_ID, new Object[]{worldId}, new RowMapper<HTWorldChildWorldDto>() {

			@Override
			public HTWorldChildWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HTWorldChildWorldDto dto = buildShowChildWorldDtoByResultSet(rs);
				return dto;
			}
			
		}, rowSelection);
	}
	
	@Override
	public List<HTWorldChildWorldDto> queryShowChildWorldDtosByWorldId(Integer worldId) {
		return getJdbcTemplate().query(QUERY_CHILD_WORLD_BY_WORLD_ID, new Object[]{worldId}, new RowMapper<HTWorldChildWorldDto>(){

			@Override
			public HTWorldChildWorldDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HTWorldChildWorldDto dto = buildShowChildWorldDtoByResultSet(rs);
				return dto;
			}
		});
	}
	
	@Override
	public List<HTWorldChildWorld> queryChildWorldMetaListByAtId(Integer atId) {
		return getJdbcTemplate().query(QUERY_CHILD_WORLD_BY_AT_ID, new RowMapper<HTWorldChildWorld>(){

			@Override
			public HTWorldChildWorld mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildChildWorldByResultSet(rs);
			}
			
		}, atId);
	}
	
	@Override
	public HTWorldChildWorld queryTitleChildWorldMetaByWorldId(Integer worldId) {
		return queryForObjectWithNULL(QUERY_TITLE_CHILD_WORLD_BY_WORLD_ID,new Object[]{worldId,1},
				new RowMapper<HTWorldChildWorld>(){

				@Override
				public HTWorldChildWorld mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildChildWorldByResultSet(rs);
				}
		});
	}

	@Override
	public List<HTWorldChildWorldThumb> queryThumbsByAtId(Integer atId) {
		return getJdbcTemplate().query(QUERY_CHILD_THUMBS_BY_AT_ID, new Object[]{atId}, new RowMapper<HTWorldChildWorldThumb>() {

			@Override
			public HTWorldChildWorldThumb mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HTWorldChildWorldThumb thumb = buildChildWorldThumbByResultSet(rs);
				return thumb;
			}
		});
	}
	
	@Override
	public Map<Integer, List<HTWorldChildWorldThumb>> queryThumbsMapByAtId(Integer[] atIds) {
		final Map<Integer, List<HTWorldChildWorldThumb>> map = new HashMap<Integer, List<HTWorldChildWorldThumb>>();
		String selection = SQLUtil.buildInSelection(atIds);
		String sql = QUERY_CHILD_THUMBS_MAP_BY_AT_ID + selection;
		getJdbcTemplate().query(sql, (Object[])atIds, new RowMapper<Void>(){

			@Override
			public Void mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				HTWorldChildWorldThumb thumb = buildChildWorldThumbByResultSet(rs);
				Integer atId = thumb.getAtId();
				if(map.containsKey(atId)) {
					map.get(atId).add(thumb);
				} else {
					List<HTWorldChildWorldThumb> list = new ArrayList<HTWorldChildWorldThumb>();
					list.add(thumb);
					map.put(atId, list);
				}
				return null;
			}
			
		});
		return map;
	}
	
	@Override
	public Long queryChildWorldCountByWorldId(Integer worldId) {
		return getJdbcTemplate().queryForLong(QUERY_CHILD_WORLD_COUNT_BY_WORLD_ID, worldId);
	}
	
	@Override
	public Long queryChildWorldIndex(Integer worldId, Integer childId) {
		return getJdbcTemplate().queryForLong(QUERY_CHILD_WORLD_INDEX, new Object[]{worldId, childId});
	}
	
	@Override
	public HTWorldChildWorld queryTitleChildWorld(Integer worldId) {
		HTWorldChildWorld childWorld = new HTWorldChildWorld();
		Map<String, Object> metaMap = getJdbcTemplate().queryForMap(QUERY_TITLE_CHILD_WORLD, new Object[]{worldId,1});
		childWorld.setId((Integer)metaMap.get("id"));
		childWorld.setThumbPath((String)metaMap.get("thumb_path"));
		metaMap.clear();
		metaMap = null;
		return childWorld;
	}
	
	@Override
	public void deleteByWorldIds(Integer[] worldIds) {
		String inSelection = SQLUtil.buildInSelection(worldIds);
		String sql = DELETE_BY_WORLD_IDS + inSelection;
		getJdbcTemplate().update(sql, (Object[])worldIds);
	}
	
	@Override
	public void queryAllChild(Integer worldId, final RowCallback<HTWorldChildWorld> callback) {
		getJdbcTemplate().query(QUERY_ALL_CHILD, new Object[]{worldId}, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildChildWorldByResultSet(rs));
			}
		});
	}
	
	/**
	 * 根据结果集构建子世界信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public HTWorldChildWorld buildChildWorldByResultSet(ResultSet rs) throws SQLException {
			HTWorldChildWorld childWorld = new HTWorldChildWorld(
				rs.getInt("id"),
				rs.getString("child_world_desc"), 
				rs.getInt("world_id"),
				rs.getString("path"), 
				rs.getInt("width"), 
				rs.getInt("height"),
				rs.getInt("is_title"), 
				rs.getInt("coordinate_x"),
				rs.getInt("coordinate_y"),
				rs.getInt("at_id"),
				rs.getString("thumb_path"),
				rs.getInt("angle"),
				rs.getInt("type"),
				rs.getString("type_path"));
			return childWorld;
	}
	
	/**
	 * 从结果集构建子世界缩略图信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static HTWorldChildWorldThumb buildChildWorldThumbByResultSet(ResultSet rs) throws SQLException {
		HTWorldChildWorldThumb thumb = new HTWorldChildWorldThumb(
				rs.getInt("id"),
				rs.getInt("coordinate_x"),
				rs.getInt("coordinate_y"), 
				rs.getInt("at_id"),
				rs.getString("thumb_path"),
				rs.getInt("angle"),
				rs.getInt("type"),
				rs.getString("type_path"));
		return thumb;
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static HTWorldChildWorldDto buildShowChildWorldDtoByResultSet(ResultSet rs) throws SQLException {
		HTWorldChildWorldDto dto = new HTWorldChildWorldDto(
				rs.getInt("id"),
				rs.getString("child_world_desc"),
				rs.getInt("world_id"),
				rs.getString("path"),
				rs.getInt("width"),
				rs.getInt("height"),
				rs.getInt("is_title"),
				rs.getInt("angle"),
				rs.getInt("type"),
				rs.getString("type_path"));
		return dto;
	}
	
	/**
	 * 构建子世界缩略图
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public HTWorldChildThumbnail buildThumbnail(ResultSet rs) throws SQLException {
		return new HTWorldChildThumbnail(
				rs.getInt("id"),
				rs.getInt("world_id"),
				rs.getString("path"));
	}


}
