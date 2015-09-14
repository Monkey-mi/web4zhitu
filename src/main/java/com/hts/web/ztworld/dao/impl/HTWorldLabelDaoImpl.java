package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.ztworld.dao.HTWorldLabelDao;

/**
 * <p>
 * 织图标签数据访问对象
 * </p>
 * 
 * 创建时间：2014-1-11
 * @author ztj
 *
 */
@Repository("HTSHTWorldLabelDao")
public class HTWorldLabelDaoImpl extends BaseDaoImpl implements HTWorldLabelDao {
	
	private static String table = HTS.HTWORLD_LABEL;
	
	private static final String ORDER_BY_SERIAL_DESC = " order by serial desc";
	
	/**
	 * 保存标签
	 */
	private static final String SAVE_LABEL = "insert into " + table 
			+ " (id,label_name,label_pinyin,world_count,"
			+ " superb_count,date_added, label_state,valid,serial,weight)"
			+ " values (?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 根据名字查询标签
	 */
	private static final String QUERY_LABEL_BY_NAME = "select * from " 
			+ table + " where BINARY label_name=?";
	
	/**
	 * 根据名字模糊查询标签
	 */
	private static final String QUERY_FUZZY_LABEL_BY_NAME = "select DISTINCT * from " 
			+ table + " where label_name like ? " + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 更新织图总数
	 */
	private static final String UPDATE_WORLD_COUNT = "update " + table 
			+ " set world_count=? where id=?";
	
	/**
	 * 根据标签名查询ids
	 */
	private static final String QUERY_IDS_BY_NAMES = "select * from "
			+ table + " where BINARY label_name in";
	
	/**
	 * 根据ids查询织图总数
	 */
	private static final String QUERY_WORLD_COUNT_BY_IDS = "select * from " 
			+ table + " where id in ";
	
	/**
	 * 根据id删除织图总数
	 */
	private static final String QUERY_WORLD_COUNT_BY_ID = "select world_count from " 
			+ table + " where id=?";
	
	@Override
	public void saveLabel(HTWorldLabel label) {
		getMasterJdbcTemplate().update(SAVE_LABEL, new Object[]{
			label.getId(),
			label.getLabelName(),
			label.getLabelPinyin(),
			label.getWorldCount(),
			label.getSuperbCount(),
			label.getDateAdded(),
			label.getLabelState(),
			label.getValid(),
			label.getSerial(),
			label.getWeight()
		});
	}

	@Override
	public Integer queryWorldCount(Integer labelId) {
		return getJdbcTemplate().queryForInt(QUERY_WORLD_COUNT_BY_ID, labelId);
	}
	
	@Override
	public void updateWorldCount(Integer labelId, int count) {
		getMasterJdbcTemplate().update(UPDATE_WORLD_COUNT, new Object[]{count, labelId});
	}

	@Override
	public HTWorldLabel queryLabelByName(String name) {
		return queryForObjectWithNULL(QUERY_LABEL_BY_NAME, new Object[]{name}, 
				new RowMapper<HTWorldLabel>() {

			@Override
			public HTWorldLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLabel(rs);
			}
		});
	}
	
	@Override
	public Map<String, HTWorldLabel> queryLabelByNames(String[] names) {
		String inSelection = SQLUtil.buildInSelection(names);
		String sql = QUERY_IDS_BY_NAMES + inSelection;
		final Map<String, HTWorldLabel> map = new HashMap<String, HTWorldLabel>();
		getJdbcTemplate().query(sql, names, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HTWorldLabel label = buildLabel(rs);
				map.put(label.getLabelName(), label);
			}
		});
		return map;
	}

	@Override
	public List<HTWorldLabel> queryFuzzyLabelByName(String name, RowSelection rowSelection) {
		return queryForPage(QUERY_FUZZY_LABEL_BY_NAME, new Object[]{"%"+name+"%"}, new RowMapper<HTWorldLabel>() {

			@Override
			public HTWorldLabel mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildLabel(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public void queryLabel(Integer[] ids, final RowCallback<HTWorldLabel> callback)
			throws Exception {
		String inSelection = SQLUtil.buildInSelection(ids);
		String sql = QUERY_WORLD_COUNT_BY_IDS + inSelection;
		getJdbcTemplate().query(sql, (Object[])ids, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildLabel(rs));
			}
		});
	}

	@Override
	public HTWorldLabel buildLabel(ResultSet rs) throws SQLException {
		return new HTWorldLabel(
				rs.getInt("id"),
				rs.getString("label_name"),
				rs.getString("label_pinyin"),
				rs.getInt("world_count"),
				rs.getInt("superb_count"),
				(Date)rs.getObject("date_added"),
				rs.getInt("label_state"),
				rs.getInt("valid"),
				rs.getInt("serial"),
				rs.getInt("weight"));
	}


}
