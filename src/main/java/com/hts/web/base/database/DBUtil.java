package com.hts.web.base.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.Log;

/**
 * <p>
 * 数据库工具类
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 * 
 */
public class DBUtil {

	/**
	 * 设置参数
	 * @param pstmt
	 * @param param
	 * @throws SQLException
	 */
	public static void setparam(PreparedStatement pstmt, Object[] param) throws SQLException {
		if (param == null || param.length <= 0)
			return;
		for (int index = 0; index < param.length; index++) {
			pstmt.setObject(index + 1, param[index]);
		}
	}
	
	/**
	 * 释放资源
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	public static void releaseDbResource(ResultSet rs, PreparedStatement pstmt,
			Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				Log.warn(e);
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				Log.warn(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				Log.warn(e);
			}
		}
	}
	
	/**
	 * 释放资源
	 * 
	 * @param res
	 */
	public static void releaseDbResource(ResultSet res) {
		if (res != null) {
			try {
				res.close();
			} catch (Exception e) {
				Log.warn(e);
			}
		}
	}
	
	/**
	 * 释放资源
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	public static void releaseDbResource(ResultSet res, Connection conn) {
		if (res != null) {
			try {
				res.close();
			} catch (Exception e) {
				Log.warn(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				Log.warn(e);
			}
		}
	}
	
	/**
	 * 释放数据库连接
	 * @param conn
	 */
	public static void releaseConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				Log.warn(e);
			}
		}
	}
	
	/**
	 * <p>
	 * 元数据是否有效
	 * </p>
	 * 
	 * @param meta
	 * @return
	 */
	public static boolean isValid(Map<String, Object> meta) {
		if((Integer)meta.get("valid") == Tag.TRUE) {
			return true;
		}
		return false;
	}
	
	
}
