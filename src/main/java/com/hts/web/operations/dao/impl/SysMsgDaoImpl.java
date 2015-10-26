package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.hts.web.operations.dao.SysMsgDao;

@Repository("HTSSysMsgDao")
public class SysMsgDaoImpl extends BaseDaoImpl implements SysMsgDao {

	public static String table = HTS.OPERATIONS_SYS_MSG;

	private static final String MSG_INFO = "id,msg_date,content,"
			+ "obj_type,obj_id,obj_meta,obj_meta2,thumb_path";
	
	private static final String QUERY_MSG = "select " + MSG_INFO + 
			" from " + table + " where recipient_id=?"
			+ " order by id desc limit ?,?";
	
	private static final String QUERY_MSG_BY_MAXID = "select " + MSG_INFO + 
			" from " + table + " where recipient_id=? and id<=?"
			+ " order by id desc limit ?,?";
	
	private static final String QUERY_MSG_BY_OBJ_TYPE = "select " + MSG_INFO
			+ " from " + table + " where recipient_id=? and obj_type=? limit 1";
	
	private static final String DELETE_BY_OBJ_TYPE = "delete from " + table
			+ " where recipient_id=? where obj_type=?";
	
	private static final String DELETE_BY_ID = "delete from " + table 
			+ " where recipient_id=? and id=?";
	
	public OpSysMsgDto buildMsg(ResultSet rs, Integer userId) throws SQLException {
		OpSysMsgDto dto =  new OpSysMsgDto(
				rs.getInt("id"),
				userId,
				(Date)rs.getObject("msg_date"), 
				rs.getString("content"),
				rs.getInt("obj_type"),
				rs.getInt("obj_id"), 
				rs.getString("obj_meta"),
				rs.getString("obj_meta2"),
				rs.getString("thumb_path"));
		return dto;
	}

	@Override
	public List<OpSysMsgDto> queryMsg(final Integer userId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_MSG, 
				new Object[]{userId, rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<OpSysMsgDto>(){

					@Override
					public OpSysMsgDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						return buildMsg(rs, userId);
					}
		});
	}

	@Override
	public List<OpSysMsgDto> queryMsg(Integer maxId, final Integer userId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_MSG_BY_MAXID, 
				new Object[]{userId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<OpSysMsgDto>(){

					@Override
					public OpSysMsgDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						return buildMsg(rs, userId);
					}
		});
	}

	@Override
	public OpSysMsgDto queryMsgByObjType(final Integer userId, Integer objType) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_MSG_BY_OBJ_TYPE,
					new Object[]{userId, objType},
					new RowMapper<OpSysMsgDto>() {
	
						@Override
						public OpSysMsgDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							return buildMsg(rs, userId);
						}
			});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void deleteByObjType(Integer userId, Integer objType) {
		getMasterJdbcTemplate().update(DELETE_BY_OBJ_TYPE, userId, objType);
	}

	@Override
	public void deleteById(Integer userId, Integer id) {
		getMasterJdbcTemplate().update(DELETE_BY_ID, userId, id);
	}

}
