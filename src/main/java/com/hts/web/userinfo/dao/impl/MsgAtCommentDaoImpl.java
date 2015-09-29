package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.MsgAt;
import com.hts.web.common.pojo.MsgAtId;
import com.hts.web.userinfo.dao.MsgAtCommentDao;

@Repository("HTSMsgAtCommentDao")
public class MsgAtCommentDaoImpl extends BaseDaoImpl implements MsgAtCommentDao {

	private static String table = HTS.USER_MSG_AT_COMMENT;
	
	private static final String SAVE_MSG = "insert into " + table
			+ "(id,comment_id,at_id,at_name) values ";
	private static final String SAVE_MSG_VALUES = "(?,?,?,?)";
	private static final int SAVE_MSG_VALUES_LEN = 4;
	
	private static final String QUERY_AT_ID = "select at_id,at_name from "
			+ table + " where world_id=? order by id asc limit ?,1";
	
	@Override
	public void saveAtMsgs(MsgAt[] msgs) {
		if(msgs == null || msgs.length == 0) 
			return;
		
		Object[] args;
		args = new Object[msgs.length * SAVE_MSG_VALUES_LEN];
		
		StringBuilder sb = new StringBuilder(SAVE_MSG);
		for(int i = 0; i < msgs.length; i++) {
			int k = i * SAVE_MSG_VALUES_LEN;
			if(i > 0) {
				sb.append(",");
			}
			sb.append(SAVE_MSG_VALUES);
			args[k+0] = msgs[i].getId();
			args[k+1] = msgs[i].getObjId();
			args[k+2] = msgs[i].getAtId();
			args[k+3] = msgs[i].getAtName();
		}
		getJdbcTemplate().update(sb.toString(), args);
	}

	@Override
	public MsgAtId queryAtId(Integer worldId, Integer index) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_AT_ID, new RowMapper<MsgAtId>() {
	
				@Override
				public MsgAtId mapRow(ResultSet rs, int rowNum) throws SQLException {
					MsgAtId msg = new MsgAtId();
					msg.setAtId(rs.getInt("at_id"));
					msg.setAtName(rs.getString("at_name"));
					return msg;
				}
				
			}, worldId, index);
		} catch(DataAccessException e) {
			return null;
		}
	}

}
