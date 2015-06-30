package com.hts.web.ztworld.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.ztworld.dao.HTWorldStickerDao;

@Repository("HTSHTWorldStickerDao")
public class HTWorldStickerDaoImpl extends BaseDaoImpl implements
		HTWorldStickerDao {
	
	private static String table = HTS.HTWORLD_STICKER;
	
	private static final String STICKER_INFO = "id,type_id,set_id,sticker_path,"
			+ "sticker_thumb_path,sticker_demo_path,sticker_name,sticker_desc,"
			+ "serial,has_lock,label_id,fill";

	/**
	 * 查询贴纸
	 */
	private static final String QUERY_STICKER = "select " + STICKER_INFO + " from " + table
			+ " where valid=1 and type_id=?" + ORDER_BY_SERIAL_DESC;
	
	/**
	 * 根据最大序号查询贴纸
	 */
	private static final String QUERY_STICKER_BY_MAX_ID = "select " + STICKER_INFO + " from " + table
			+ " where valid=1 and type_id=? and serial<=? " + ORDER_BY_SERIAL_DESC;
	
	private static final String QUERY_STICKER_BY_ID = "select " + STICKER_INFO
			+ " from " + table + " where id=?";
	
	private static final String QUERY_STICKER_BY_LABEL_IDS = "select " + STICKER_INFO +
			" from " + table + " where valid=1 and label_id in ";
	
	private static final String QUERY_STICKER_BY_LABEL_ID = "select " + STICKER_INFO
			+ " from " + table + " where valid=1 and label_id=?";

	@Override
	public List<HTWorldStickerDto> querySticker(Integer typeId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_STICKER, new Object[]{typeId}, 
				new RowMapper<HTWorldStickerDto>(){

			@Override
			public HTWorldStickerDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildStickerDto(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<HTWorldStickerDto> querySticker(Integer maxId, Integer typeId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_STICKER_BY_MAX_ID, new Object[]{typeId,maxId}, 
				new RowMapper<HTWorldStickerDto>(){

					@Override
					public HTWorldStickerDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildStickerDto(rs);
					}
			
		}, rowSelection);
	}
	

	@Override
	public HTWorldStickerDto queryStickerById(Integer id) {
		return queryForObjectWithNULL(QUERY_STICKER_BY_ID, 
				new Object[]{id}, new RowMapper<HTWorldStickerDto>() {

					@Override
					public HTWorldStickerDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildStickerDto(rs);
					}
		});
	}
	
	@Override
	public void queryStickerByLabelIds(Integer[] labelIds, 
			final RowCallback<HTWorldStickerDto> callback) {
		String inSelection = SQLUtil.buildInSelection(labelIds);
		String sql = QUERY_STICKER_BY_LABEL_IDS + inSelection;
		getJdbcTemplate().query(sql, labelIds, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(buildStickerDto(rs));
			}
		});
	}
	
	@Override
	public HTWorldStickerDto queryStickerByLabelId(Integer labelId) {
		return queryForObjectWithNULL(QUERY_STICKER_BY_LABEL_ID, new Object[]{labelId}, 
				new RowMapper<HTWorldStickerDto>() {

					@Override
					public HTWorldStickerDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildStickerDto(rs);
					}
		});
	}
	
	public HTWorldStickerDto buildStickerDto(ResultSet rs) throws SQLException {
		return new HTWorldStickerDto(
				rs.getInt("serial"),
				rs.getInt("id"),
				rs.getInt("type_id"),
				rs.getInt("set_id"),
				rs.getString("sticker_path"),
				rs.getString("sticker_thumb_path"),
				rs.getString("sticker_demo_path"),
				rs.getString("sticker_name"),
				rs.getString("sticker_desc"),
				rs.getInt("has_lock"),
				rs.getInt("label_id"),
				rs.getInt("fill"));
	}


}
