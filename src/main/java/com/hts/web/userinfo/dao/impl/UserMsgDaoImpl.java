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
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserMsg;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.common.pojo.UserMsgIndex;
import com.hts.web.common.pojo.UserMsgRecipientDto;
import com.hts.web.userinfo.dao.UserMsgDao;

/**
 * <p>
 * 私信数据访问对象
 * </p>
 * 
 * 创建时间：2013-10-29
 * 
 * @author ztj
 * 
 */
@Repository("HTSUserMsgDao")
public class UserMsgDaoImpl extends BaseDaoImpl implements UserMsgDao {

	
	private static String table = HTS.USER_MSG;
	
	private static final String SAVE_MSG = "insert into " + table + 
			" (id,content) values (?,?)";

	private static final String DEL_MSG = "delete from " + table
			+ " where id=?";
	
	@Override
	public void saveMsg(UserMsg msg) {
		getMasterJdbcTemplate().update(SAVE_MSG, new Object[]{
				msg.getId(),
				msg.getContent(),
		});
	}

	@Override
	public void deleteMsg(Integer id) {
		getMasterJdbcTemplate().update(DEL_MSG, id);
	}
	
	
}
