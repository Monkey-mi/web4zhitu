package com.hts.web.userinfo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserVerifyDto;

/**
 * <p>
 * 验证用户数据访问接口
 * </p>
 * 
 * 创建时间：2014-7-15
 * @author tianjie
 *
 */
public interface UserVerifyDao extends BaseDao {

	/**
	 * 构建UserVerifyDto
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	 public UserVerifyDto buildVerifyDto(ResultSet rs) throws SQLException;
}
