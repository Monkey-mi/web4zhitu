package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;

import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.base.database.HTS;
import com.hts.web.userinfo.dao.UserRetrievePWDDao;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.common.pojo.RetrievePasswordDto;

@Repository("HTSUserRetrievePWDDao")
public class UserRetrievePWDDaoImpl extends BaseDaoImpl implements UserRetrievePWDDao {
	private static final String table = HTS.RETRIEVE_PASSWORD;
	private static final String QUERY_RPWD_BY_LOGIN_CODE = "select * from " + table + " where login_code=?";
	private static final String CHECK_RPWD_IS_EXIST = " select count(1) from " + table + " where login_code=?";
	private static final String DELETE_RPWD_BY_TIME = " delete from " + table + " where end_time<?";
	private static final String CHECK_USERINFO_IS_EXIST = " select count(1) from hts.user_info where login_code=?";
	private static final String REST_PASSWORD_BY_ID = " update hts.user_info  set password=? where id=?";
	private static final String REST_PASSWORD_BY_LOGIN_CODE = " update hts.user_info  set password=? where login_code=?"; 
	private static final String SAVE_RPWD = " insert into " + table + " (login_code,sid,end_time) values(?,?,?)";
	private static final String GET_LOGIN_CODE_BY_USERID = " select login_code from hts.user_info where id=?";
	private static final String DELETE_RPWD_BY_LOGIN_CODE = " delete from " + table + " where login_code=?";
	private static final String GET_USER_ID_BY_LOGIN_CODE = " select id from hts.user_info where login_code=?";
	private static final String GET_USER_NAME_BY_LOGIN_CODE = " select user_name from hts.user_info where login_code=?";
	
	@Override
	public RetrievePasswordDto queryRetrievePasswordByLoginCode(String loginCode){
		return getJdbcTemplate().queryForObject(QUERY_RPWD_BY_LOGIN_CODE, new Object[]{loginCode},new RowMapper<RetrievePasswordDto>(){
			@Override
			public RetrievePasswordDto mapRow(ResultSet rs ,int num)throws SQLException{
				return buildRetrievePasswordDto(rs);
			}
		});
	}
	
	@Override
	public void resetPWD(Integer userId,byte[] pwd){
		getJdbcTemplate().update(REST_PASSWORD_BY_ID, new Object[]{pwd, userId});
	}
	
	@Override
	public void resetPWD(String loginCode,byte[] pwd){
		getJdbcTemplate().update(REST_PASSWORD_BY_LOGIN_CODE, new Object[]{pwd, loginCode});
	}
	
	
	@Override
	public boolean checkUserInfoIsExist(String loginCode){
		long r = getJdbcTemplate().queryForLong(CHECK_USERINFO_IS_EXIST,loginCode);
		return r==0 ? false : true;
	}
	
	
	@Override
	public boolean checkRPWDIsExist(String loginCode){
		long r = getJdbcTemplate().queryForLong(CHECK_RPWD_IS_EXIST, loginCode);
		return r==0 ? false : true;
	}
	
	@Override
	public void deleteRPWDByTime(Date endTime){
		getJdbcTemplate().update(DELETE_RPWD_BY_TIME, endTime);
	}
	
	@Override
	public void saveRPWD(RetrievePasswordDto retrievePasswordDto){
		getJdbcTemplate().update(SAVE_RPWD, retrievePasswordDto.getLogin_code(),
				retrievePasswordDto.getSid(),
				retrievePasswordDto.getEnd_time());
	}
	
	@Override
	public String getLoginCodeByUID(Integer userId){
		return getJdbcTemplate().queryForObject(GET_LOGIN_CODE_BY_USERID, new Object[]{userId},String.class);
	}
	
	@Override 
	public void deleteRPWDByLoginCode(String loginCode){
		getJdbcTemplate().update(DELETE_RPWD_BY_LOGIN_CODE, loginCode);
	}
	
	@Override
	public String getUserIdByLoginCode(String loginCode){
		return getJdbcTemplate().queryForObject(GET_USER_ID_BY_LOGIN_CODE, new Object[]{loginCode}, String.class);
	}
	
	@Override
	public String getUserNameByLoginCode(String loginCode){
		try{
			return getJdbcTemplate().queryForObject(GET_USER_NAME_BY_LOGIN_CODE, new Object[]{loginCode}, String.class);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	/**
	 * 构建UserRetrievePWDDao
	 */
	public RetrievePasswordDto buildRetrievePasswordDto(ResultSet rs)throws SQLException{
		return new RetrievePasswordDto(rs.getString("login_code"),
				rs.getBytes("sid"),
				rs.getDate("end_time"));
	}
}
