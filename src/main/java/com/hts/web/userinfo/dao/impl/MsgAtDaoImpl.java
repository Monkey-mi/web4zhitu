package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.MsgAt;
import com.hts.web.common.pojo.MsgAtDto;
import com.hts.web.common.pojo.MsgAtUserDto;
import com.hts.web.common.pojo.MsgAtWorldDto;
import com.hts.web.userinfo.dao.MsgAtDao;

@Repository("HTSMsgAtDao")
public class MsgAtDaoImpl extends BaseDaoImpl implements MsgAtDao {
	
	private static String table = HTS.USER_MSG_AT;
	
	private static final String MSG_INFO = "m0.id,m0.user_id,m0.world_id,m0.obj_type,"
			+ "m0.obj_id,m0.at_time,m0.content";
	private static final String USER_INFO = "u0.user_name,u0.user_avatar,u0.user_avatar_l";
	private static final String WORLD_INFO = "h0.title_thumb_path,h0.title_path,h0.valid";
	
	private static final String SAVE_MSG = "insert into " + table
			+ "(id,user_id,at_id,world_id,obj_type,obj_id,content) values ";
	private static final String SAVE_MSG_VALUES = "(?,?,?,?,?,?,?)";
	private static final int SAVE_MSG_VALUES_LEN = 7;

	private static final String QUERY_MSG = "select " + MSG_INFO + "," + USER_INFO + "," + WORLD_INFO
			+ " from " + table + " m0," + HTS.USER_INFO + " u0," + HTS.HTWORLD_HTWORLD + " h0"
			+ " where m0.user_id=u0.id and m0.world_id=h0.id and m0.at_id=?"
			+ " order by m0.id desc limit ?,?";
	
	private static final String QUERY_MSG_BY_MAX_ID = "select " + MSG_INFO + "," + USER_INFO + "," + WORLD_INFO
			+ " from " + table + " m0," + HTS.USER_INFO + " u0," + HTS.HTWORLD_HTWORLD + " h0"
			+ " where m0.user_id=u0.id and m0.world_id=h0.id and m0.at_id=? and m0.id<=?"
			+ " order by m0.id desc limit ?,?";
			
	@Override
	public void saveAtMsgs(List<MsgAt> msgs) {
		if(msgs == null || msgs.size() == 0) 
			return;
		
		Object[] args;
		args = new Object[msgs.size() * SAVE_MSG_VALUES_LEN];
		
		StringBuilder sb = new StringBuilder(SAVE_MSG);
		for(int i = 0; i < msgs.size(); i++) {
			int k = i * SAVE_MSG_VALUES_LEN;
			if(i > 0) {
				sb.append(",");
			}
			sb.append(SAVE_MSG_VALUES);
			args[k+0] = msgs.get(i).getId();
			args[k+1] = msgs.get(i).getUserId();
			args[k+2] = msgs.get(i).getAtId();
			args[k+3] = msgs.get(i).getWorldId();
			args[k+4] = msgs.get(i).getObjType();
			args[k+5] = msgs.get(i).getObjId();
			args[k+6] = msgs.get(i).getContent();
		}
		getMasterJdbcTemplate().update(sb.toString(), args);
	}
//
//	@Override
//	public Long queryUnCheckCount(Integer atId) {
//		return getJdbcTemplate().queryForLong(QUERY_UN_CK_COUNT, atId);
//	}
//
//	@Override
//	public void updateCK(Integer atId) {
//		getMasterJdbcTemplate().update(UPDATE_CK, atId);
//	}

	@Override
	public List<MsgAtDto> queryMsg(Integer atId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_MSG,
				new Object[]{atId, rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<MsgAtDto>() {

			@Override
			public MsgAtDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				return buildMsgDto(rs);
			}
			
		});
	}

	@Override
	public List<MsgAtDto> queryMsg(Integer maxId, Integer atId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_MSG_BY_MAX_ID,
				new Object[]{atId,maxId,rowSelection.getFirstRow(), rowSelection.getLimit()},
				new RowMapper<MsgAtDto>() {

			@Override
			public MsgAtDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				return buildMsgDto(rs);
			}
			
		});
	}
	
	public MsgAtDto buildMsgDto(ResultSet rs) throws SQLException {
		MsgAtDto msg = new MsgAtDto();
		msg.setId(rs.getInt("id"));
		msg.setObjType(rs.getInt("obj_type"));
		msg.setObjId(rs.getInt("obj_id"));
		msg.setWorldId(rs.getInt("world_id"));
		msg.setAtTime((Date)rs.getObject("at_time"));
		msg.setContent(rs.getString("content"));
		
		MsgAtUserDto user = new MsgAtUserDto();
		user.setId(rs.getInt("user_id"));
		user.setUserName(rs.getString("user_name"));
		user.setUserAvatar(rs.getString("user_avatar"));
		user.setUserAvatarL(rs.getString("user_avatar_l"));
		
		MsgAtWorldDto world = new MsgAtWorldDto();
		world.setId(rs.getInt("world_id"));
		world.setTitleThumbPath(rs.getString("title_thumb_path"));
		world.setTitlePath(rs.getString("title_path"));
//		world.setWorldDesc(rs.getString("world_desc"));
		world.setValid(rs.getInt("valid"));
		
		msg.setUserInfo(user);
		msg.setHtworld(world);
		
		return msg;
	}

}
