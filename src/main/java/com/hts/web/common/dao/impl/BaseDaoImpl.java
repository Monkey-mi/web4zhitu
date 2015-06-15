package com.hts.web.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.hts.web.base.database.DBUtil;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.util.StringUtil;

/**
 * <p>
 * 数据访问基础类
 * </p>
 * 
 * 
 * 创建时间：2012-10-18
 * @author ztj
 *
 */
public class BaseDaoImpl implements BaseDao{
	
	
	@Autowired
	private JdbcTemplate masterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public JdbcTemplate getMasterJdbcTemplate() {
		return masterJdbcTemplate;
	}

	public void setMasterJdbcTemplate(JdbcTemplate masterJdbcTemplate) {
		this.masterJdbcTemplate = masterJdbcTemplate;
	}



	/**
	 * 查询织图同时查询的用户信息字段
	 */
	protected static final String U0_INFO = "u0.user_name, u0.user_avatar,u0.user_avatar_l,u0.sex,u0.email,u0.birthday,"
			+ "u0.signature,u0.address,u0.province as u_province,u0.city as u_city,u0.register_date,u0.user_label,u0.star,u0.trust,"
			+ "u0.phone_code as u_phone_code,u0.online,u0.platform_verify";
	
	/**
	 * 织图公用信息
	 */
	protected static final String H0_INFO = "h0.id,h0.short_link, h0.author_id, h0.world_name, h0.world_desc, h0.world_label,"
			+ "h0.world_type, h0.type_id, h0.date_added, h0.date_modified, h0.click_count,"
			+ "h0.like_count, h0.comment_count, h0.keep_count, h0.cover_path, h0.title_path,h0.bg_path,"
			+ "h0.title_thumb_path, h0.channel_name, h0.channel_id, h0.longitude, h0.latitude, h0.location_desc,"
			+ "h0.location_addr, h0.phone_code, h0.province, h0.city, h0.size, h0.child_count, h0.ver,"
			+ "h0.tp,h0.valid, h0.shield,h0.text_style";
	
	/**
	 * 查询织图缩略图信息字段
	 */
	protected static final String WORLD_THUMB = "h.cover_path,h.title_path,h.bg_path,h.title_thumb_path,h.valid,h.shield";

	protected static final String DEFAULT_ID_KEY = "id";
	
	protected static final String ORDER_BY_ID_DESC = " order by id desc";
	
	protected static final String ORDER_BY_ID_ASC = " order by id asc";
	
	protected static final String ORDER_BY_SERIAL_DESC = " order by serial desc";
	
	/**
	 * 单表查询
	 */
	protected static final String QUERY_SINGLE_TAGLE = "SELECT distinct * from ";
	
	/**
	 * 单表保存
	 */
	protected static final String SAVE_SINGLE_TAGLE = "INSERT INTO ";
	
	/**
	 * 单表更新
	 */
	protected static final String UPDATE_SINGLE_TAGLE = "UPDATE ";
	
	/**
	 * 单表删除
	 */
	protected static final String DELETE_SINGLE_TABLE = "DELETE FROM ";
	
	/**
	 * 单表总数查询
	 */
	protected static final String QUERY_SINGLE_TABLE_TOTAL = "SELECT count(*) from ";
	
	@Value("${urlPrefix}")
	protected String urlPrefix = "http://www.imzhitu.com/DT";
	
	@Override
	public String getUrlPrefix() {
		return urlPrefix;
	}
	
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	@Override
	public Map<String, Object> query(Map<String, Object> attrMap, String tableName) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = QUERY_SINGLE_TAGLE + tableName + selection;
		return getJdbcTemplate().queryForMap(sql, attrMap.values().toArray());
	}
	
	@Override
	public List<Map<String, Object>> queryMetaList(String tableName) {
		String sql = QUERY_SINGLE_TAGLE + tableName + ORDER_BY_ID_DESC;
		return getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryMetaList(String tableName, String orderBy) {
		String sql = QUERY_SINGLE_TAGLE + tableName + " order by " + orderBy;
		return getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryMetaList(int start, int limit, String tableName) {
		String sql = QUERY_SINGLE_TAGLE + tableName + ORDER_BY_ID_DESC;
		return queryForPage(sql, new RowSelection(start, limit));
	}
	
	@Override
	public List<Map<String, Object>> queryMetaList(int start, int limit, String tableName, String orderBy) {
		String sql = QUERY_SINGLE_TAGLE + tableName + " order by " + orderBy;
		return queryForPage(sql, new RowSelection(start, limit));
	}
	
	@Override
	public List<Map<String, Object>> queryMetaList(Map<String,Object> attrMap, int start, int limit, String tableName) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = QUERY_SINGLE_TAGLE + tableName + selection + ORDER_BY_ID_DESC;
		return queryForPage(sql, attrMap.values().toArray(), new RowSelection(start, limit));
	}
	
	@Override
	public List<Map<String, Object>> queryMetaList(Map<String,Object> attrMap, int start, int limit, String tableName, String orderBy) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = QUERY_SINGLE_TAGLE + tableName + selection + " order by " + orderBy;
		return queryForPage(sql, attrMap.values().toArray(), new RowSelection(start, limit));
	}
	
	@Override
	public List<Map<String, Object>> queryMetaList(Map<String,Object> attrMap, String tableName) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = QUERY_SINGLE_TAGLE + tableName + selection + ORDER_BY_ID_DESC;
		return getJdbcTemplate().queryForList(sql, attrMap.values().toArray());
	}
	
	@Override
	public Integer save(Map<String, Object> attrMap, String tableName) {
		String selection = SQLUtil.buildSaveSelection(attrMap);
		String sql = SAVE_SINGLE_TAGLE + tableName + selection;
		return save(sql, attrMap.values().toArray());
	}
	
	@Override
	public void updateById(Map<String,Object> attrMap, Integer id, String tableName) {
		attrMap.put(DEFAULT_ID_KEY, id);
		String selection = SQLUtil.buildUpdateSelection(attrMap);
		String sql = UPDATE_SINGLE_TAGLE + tableName + selection;
		getMasterJdbcTemplate().update(sql, attrMap.values().toArray());
	}
	
	@Override
	public Integer queryTotal(Map<String, Object> attrMap, String tableName) {
		Long total = 0L;
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = QUERY_SINGLE_TABLE_TOTAL + tableName + selection;
		total = getJdbcTemplate().queryForLong(sql, attrMap.values().toArray());
		return total.intValue();
	}
	
	@Override
	public Integer queryTotal(String tableName) {
		Long total = 0L;
		String sql = QUERY_SINGLE_TABLE_TOTAL + tableName;
		total = getJdbcTemplate().queryForLong(sql);
		return total.intValue();
	}
	
	@Override
	public Map<String, Object> selectById(String[] metas, String tableName, Integer id) {
		String metaSelection = SQLUtil.buildMetaSelection(metas);
		String sql = metaSelection + tableName + " where id=?";
		return queryForMapWithNULL(sql, new Object[]{id});
	}
	
	@Override
	public Map<String, Object> selectById(String tableName, Integer id) {
		String sql = QUERY_SINGLE_TAGLE + tableName + " where id=?";
		return queryForMapWithNULL(sql, new Object[]{id});
	}
	
	
	@Override
	public void deleteByIds(String tableName, String idsStr) {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		deleteByIds(tableName, ids);
	}
	
	@Override
	public void deleteByIds(String tableName, Integer[] ids) {
		String selection = SQLUtil.buildDeleteInSelection(ids, DEFAULT_ID_KEY);
		String sql = DELETE_SINGLE_TABLE + tableName + selection;
		Object[] args = new Object[ids.length];
		for(int i = 0; i < ids.length; i++) {
			args[i] = ids[i];
		}
		getMasterJdbcTemplate().update(sql, args);
		
	}
	
	@Override
	public void deleteByIds(String tableName, Integer[] ids,String name) {
		String selection = SQLUtil.buildDeleteInSelection(ids, name);
		String sql = DELETE_SINGLE_TABLE + tableName + selection;
		Object[] args = new Object[ids.length];
		for(int i = 0; i < ids.length; i++) {
			args[i] = ids[i];
		}
		getMasterJdbcTemplate().update(sql, args);
	}
	
	
	@Override
	public void deleteByObjs(String tableName, Object[] objs,String name) {
		String selection = SQLUtil.buildDeleteInSelection(objs, name);
		String sql = DELETE_SINGLE_TABLE + tableName + selection;
		getMasterJdbcTemplate().update(sql, objs);
	}
	
	@Override
	public boolean checkRecordExists(String sql, Object[] attr){
		final AtomicBoolean bool = new AtomicBoolean(false);
		getJdbcTemplate().query(sql, attr, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				if (rs.next()) {				
					bool.set(true);
				}
			}
		});
		return bool.get();
	}
	
	@Override
	public void validRecord(String tableName, int valid, int id) {
		String sql = UPDATE_SINGLE_TAGLE + tableName + " set valid=? where id=?";
		getMasterJdbcTemplate().update(sql, new Object[]{ valid, id });
	}
	
	
	/**
	 * 条件查询
	 * 
	 * @param conn
	 * @param attrMap
	 * @param tableName
	 * @param requiredType
	 * @return
	 * @throws SQLException
	 */
	public <T> T query(Map<String, Object> attrMap, String tableName, RowMapper<T> requiredType) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = QUERY_SINGLE_TAGLE + tableName + selection;
		return queryForObjectWithNULL(sql, attrMap.values().toArray(), requiredType);
	}

	/**
	 * 条件查询Long
	 * 
	 * @param attrMap
	 * @param tableName
	 * @return
	 */
	public Long queryForLong(Map<String, Object> attrMap, String tableName) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = QUERY_SINGLE_TABLE_TOTAL + tableName + selection;
		return getJdbcTemplate().queryForLong(sql, attrMap.values().toArray());
	}
	
	/**
	 * 查询对象，若查询失败即返回null
	 * 
	 * @param sql
	 * @param args
	 * @param requiredType
	 * @return
	 */
	public <T> T queryForObjectWithNULL(String sql, Object[] args, Class<T> requiredType) {
		try {
			return getJdbcTemplate().queryForObject(sql, args, requiredType);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 查询对象，若查询失败即返回null
	 * 
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 */
	public <T> T queryForObjectWithNULL(String sql, Class<T> requiredType, Object...args) {
		try {
			return getJdbcTemplate().queryForObject(sql, requiredType, args);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	

	/**
	 * 查询对象，若查询失败即返回null
	 * @param sql
	 * @param requiredType
	 * @return
	 */
	public <T> T queryForObjectWithNULL(String sql, RowMapper<T> rowMapper) {
		try {
			return getJdbcTemplate().queryForObject(sql, rowMapper);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 查询对象，若查询失败即返回null
	 * 
	 * @param sql
	 * @param requiredType
	 * @param args
	 */
	public <T> T queryForObjectWithNULL(String sql, RowMapper<T> rowMapper, Object...args) {
		try {
			return getJdbcTemplate().queryForObject(sql, rowMapper, args);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 查询对象，若查询失败即返回null
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> T queryForObjectWithNULL(String sql, Object[] args, RowMapper<T> rowMapper) {
		try {
			return getJdbcTemplate().queryForObject(sql, args, rowMapper);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 查询Map：若查询失败即返回null
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> Map<String, Object> queryForMapWithNULL(String sql, Object[] args) {
		try {
			return getJdbcTemplate().queryForMap(sql, args);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public int save(final String sql, final Object... args) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getMasterJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				DBUtil.setparam(ps, args);
				return ps;
			}
		}, generatedKeyHolder);
		Number key = generatedKeyHolder.getKey();
		return key != null ? key.intValue() : 0;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @param rowSelection
	 * @return
	 */
	public <T> List<T> queryForPage(String sql, Object[] args, RowMapper<T> rowMapper, RowSelection rowSelection) {
		sql = SQLUtil.preparePage(sql, rowSelection);
		return getJdbcTemplate().query(sql, args, rowMapper);
	}

	/**
	 * 分页查询
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @param rowSelection
	 * @return
	 */
	public <T> List<T> queryForPage(String sql, RowMapper<T> rowMapper, RowSelection rowSelection) {
		sql = SQLUtil.preparePage(sql, rowSelection);
		return getJdbcTemplate().query(sql, rowMapper);
	}
	
	/**
	 * 分页查询元数据
	 * @param sql
	 * @param rowSelection
	 * @return
	 */
	public List<Map<String, Object>> queryForPage(String sql, Object[] args, RowSelection rowSelection) {
		sql = SQLUtil.preparePage(sql, rowSelection);
		return getJdbcTemplate().queryForList(sql, args);
	}
	
	/**
	 * 分页查询元数据，不带参数
	 * 
	 * @param sql
	 * @param rowSelection
	 * @return
	 */
	public List<Map<String, Object>> queryForPage(String sql, RowSelection rowSelection) {
		sql = SQLUtil.preparePage(sql, rowSelection);
		return getJdbcTemplate().queryForList(sql);
	}
	
}
