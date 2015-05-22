package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpSysMsg;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.hts.web.operations.dao.SysMsgDao;

@Repository("HTSSysMsgDao")
public class SysMsgDaoImpl extends BaseDaoImpl implements SysMsgDao {

	public static String table = HTS.OPERATIONS_SYS_MSG;

	private static final String SAVE_MSG = "insert into " + table + 
			" (sender_id,recipient_id,msg_date,content,obj_type,obj_id,obj_meta,"
			+ "obj_meta2,thumb_path,weight)" +
			" values (?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 私信查询SQL公用HEAD
	 */
	private static final String QUERY_MSG_HEAD = "select * from " + table
			+ " where recipient_id=? and recipient_valid=?"; 
	
	/**
	 * 查询接收到的私信
	 */
	private static final String QUERY_SYS_MSG = QUERY_MSG_HEAD 
			+ " order by weight desc, id desc";
	
	/**
	 * 根据最大id查询接收到的私信 
	 */
	private static final String QUERY_SYS_MSG_BY_MAX_ID = QUERY_MSG_HEAD + " and weight=0 and id<=?" 
			+ " order by id desc";
	
	/**
	 * （排除推荐用户）查询系统消息
	 */
	private static final String QUERY_SYS_MSG_2 = "select * from " + table
			+ " where recipient_id=? and recipient_valid=1 and obj_type!=? order by id desc";
	
	/**
	 * （排除推荐用户）根据最大id查询系统消息
	 */
	private static final String QUERY_SYS_MSG_BY_MAX_ID_2 = "select * from " + table
			+ " where recipient_id=? and recipient_valid=1 and obj_type!=? and id<=? order by id desc";
	
	/** 
	 * 根据最大id查询接收到的私信总数 
	 */
	private static final String QUERY_SYS_MSG_COUNT_BY_MAX_ID = "select count(*) from " + table 
			+ " where recipient_id=? and recipient_valid=? and id<=?";
	
	/** 
	 * 更新未读系统信息 
	 */
	private static final String UPDATE_UNREAD_SYS_MSG = "update " + table 
			+ " set ck=? where recipient_id=? and ck=? and recipient_valid=? and weight=0";
	
	/** 
	 * 查询未读系统消息总数
	 */
	private static final String QUERY_UNREAD_SYS_MSG_COUNT = "select count(*) from " 
			+ table + " where recipient_id=? and recipient_valid=? and ck=?";
	
	/**
	 * 
	 */
	private static final String QUERY_UNREAD_SYS_MSG_COUNT_2 = "select count(*) from " 
			+ table + " where recipient_id=? and recipient_valid=1 and ck=0 and obj_type!=?";
	
	/** 
	 * 根据id删除私信
	 */
	private static final String DELETE_BY_ID = "delete from " + table + " where id=?";

	/**
	 * 根据id查询接收用户id
	 */
	private static final String QUERY_RECEPIENT_ID_BY_ID = "select recipient_id from " + table + " where id=?";
	
	/**
	 * 根据objID查询私信
	 */
	private static final String QUERY_MSG_ID_BY_OBJID = "select DISTINCT id from " 
			+ table + " where sender_id=? and recipient_id=? and obj_type=? and obj_id=? and valid=?";
	
	/**
	 * 根据objId和objMeta查询私信
	 */
	private static final String QUERY_MSG_ID_BY_OBJID_AND_META2 = "select DISTINCT id from " 
			+ table + " where sender_id=? and recipient_id=? and obj_type=? and obj_id=? and obj_meta2=? and valid=?";
	
	/**
	 * 更新接受有效状态
	 */
	private static final String UPDATE_RECIPIENT_VALID_BY_OBJ_TYPE = "update " + table 
			+ " set recipient_valid=? where recipient_id=? and obj_type=?";
	
	/**
	 * 跟进objType查询私信
	 */
	private static final String QUERY_MSG_BY_OBJ_TYPE = "select * from " + table
			+ " where recipient_id=? and obj_type=? and recipient_valid=1 limit 1"; 

	/**
	 * 更新最新标记
	 */
	private static final String UPDATE_IS_NEW = "update " + table 
			+ " set is_new=0 where recipient_id=? and is_new=1 and id<=?";
	
	@Override
	public void saveMsg(OpSysMsg msg) {
		getMasterJdbcTemplate().update(SAVE_MSG, new Object[]{
//				msg.getId(),
				msg.getSenderId(),
				msg.getRecipientId(),
				msg.getMsgDate(),
				msg.getContent(),
				msg.getObjType(),
				msg.getObjId(),
				msg.getObjMeta(),
				msg.getObjMeta2(),
				msg.getThumbPath(),
				msg.getWeight()
		});
	}
	
	@Override
	public List<OpSysMsgDto> querySysMsgDto(Integer userId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_SYS_MSG, new Object[]{userId, Tag.TRUE}, new RowMapper<OpSysMsgDto>(){

			@Override
			public OpSysMsgDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserMessageDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<OpSysMsgDto> querySysMsgDtoByMaxId(Integer userId,
			Integer maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_SYS_MSG_BY_MAX_ID, 
				new Object[]{userId, Tag.TRUE, maxId}, new RowMapper<OpSysMsgDto>(){

			@Override
			public OpSysMsgDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserMessageDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}

	
	@Override
	public List<OpSysMsgDto> querySysMsgDto2(Integer userId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_SYS_MSG_2, new Object[]{userId, Tag.USER_MSG_USER_RECOMMEND}, new RowMapper<OpSysMsgDto>(){

			@Override
			public OpSysMsgDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserMessageDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<OpSysMsgDto> querySysMsgDtoByMaxId2(Integer userId,
			Integer maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_SYS_MSG_BY_MAX_ID_2, 
				new Object[]{userId, Tag.USER_MSG_USER_RECOMMEND, maxId}, new RowMapper<OpSysMsgDto>(){

			@Override
			public OpSysMsgDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserMessageDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public long querySysMsgCountByMaxId( Integer userId, Integer maxId) {
		return getJdbcTemplate().queryForLong(QUERY_SYS_MSG_COUNT_BY_MAX_ID,
				new Object[]{userId, Tag.TRUE, maxId});
	}
	
	@Override
	public void updateUnreadSysMsg(Integer userId) {
		getMasterJdbcTemplate().update(UPDATE_UNREAD_SYS_MSG, 
				new Object[]{Tag.TRUE, userId, Tag.FALSE, Tag.TRUE});
	}

	@Override
	public long queryUnCheckSysMsgCount(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_UNREAD_SYS_MSG_COUNT, 
				new Object[]{userId, Tag.TRUE, Tag.FALSE});
	}
	
	@Override
	public long queryUnCheckSysMsgCount2(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_UNREAD_SYS_MSG_COUNT_2, 
				new Object[]{userId, Tag.USER_MSG_USER_RECOMMEND});
	}
	
	@Override
	public void deleteMsgById(Integer id) {
		getMasterJdbcTemplate().update(DELETE_BY_ID, new Object[]{id});
	}

	@Override
	public Integer queryValidMessage(Integer senderId, Integer recipientId, Integer objType,
			Integer objId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_MSG_ID_BY_OBJID, 
					new Object[]{senderId, recipientId, objType, objId, Tag.TRUE});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Integer queryValidMessage(Integer senderId, Integer recipientId, Integer objType,
			Integer objId, String objMeta2) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_MSG_ID_BY_OBJID_AND_META2, 
					new Object[]{senderId, recipientId, objType, objId, objMeta2, Tag.TRUE});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public void updateRecipientValid(Integer recipientId, Integer objType, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_RECIPIENT_VALID_BY_OBJ_TYPE, 
				new Object[]{valid, recipientId, objType});
	}
	
	@Override
	public OpSysMsgDto querySysMsgByObjType(Integer userId, Integer objType) {
		return queryForObjectWithNULL(QUERY_MSG_BY_OBJ_TYPE, new Object[]{userId, objType}, 
				new RowMapper<OpSysMsgDto>() {

			@Override
			public OpSysMsgDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserMessageDtoByResultSet(rs);
			}
			
		});
	}
	
	@Override
	public Integer queryRecipientId(Integer id) {
		return queryForObjectWithNULL(QUERY_RECEPIENT_ID_BY_ID, new Object[]{id},
				Integer.class);
	}
	
	@Override
	public void updateIsNew(Integer recipientId, Integer maxId) {
		getMasterJdbcTemplate().update(UPDATE_IS_NEW, new Object[]{recipientId, maxId});
	}
	
	/**
	 * 从结果集构建UserMessageDto
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpSysMsgDto buildUserMessageDtoByResultSet(ResultSet rs) throws SQLException {
		OpSysMsgDto dto =  new OpSysMsgDto(
				rs.getInt("id"),
				rs.getInt("sender_id"), 
				rs.getInt("recipient_id"),
				(Date)rs.getObject("msg_date"), 
				rs.getString("content"),
				rs.getInt("obj_type"),
				rs.getInt("obj_id"), 
				rs.getString("obj_meta"),
				rs.getString("obj_meta2"),
				rs.getString("thumb_path"),
				rs.getInt("is_new"));
		return dto;
	}

}
