package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserVerifyDto;
import com.hts.web.userinfo.dao.UserVerifyDao;

@Repository("HTSUserVerifyDao")
public class UserVerifyDaoImpl extends BaseDaoImpl implements UserVerifyDao {

	@Override
	public UserVerifyDto buildVerifyDto(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new UserVerifyDto(
				rs.getInt("verify_id"),
				rs.getInt("id"),
				rs.getString("user_name"), 
				rs.getString("user_avatar"), 
				rs.getString("user_avatar_l"), 
				rs.getInt("sex"), 
				rs.getString("email"),
				rs.getString("address"),
				rs.getString("u_province"),
				rs.getString("u_city"),
				birthday,
				rs.getString("signature"),
				(Date)rs.getObject("register_date"), 
				rs.getString("user_label"),
				rs.getInt("star"),
				rs.getInt("u_phone_code"),
				rs.getInt("online"));
	}

}
