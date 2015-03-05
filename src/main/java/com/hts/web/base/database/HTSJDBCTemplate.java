package com.hts.web.base.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p>
 * 弃用，使用Spring JDBC模板方法{@link JdbcTemplate}代替
 * </p>
 * 
 * <p>
 * JDBC模版类，提取公用的数据库操作方法
 * </p>
 * 
 * 创建时间：2012-10-19
 * @author ztj
 *
 */
@Deprecated
public class HTSJDBCTemplate {
	
	private HTSJDBCTemplate(){}
	private static HTSJDBCTemplate jdbcTemplate = new HTSJDBCTemplate();
	public static HTSJDBCTemplate getInstance() {
		return jdbcTemplate;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @param param
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	public int executeCommand(String sql, Object[] param, final Connection conn, boolean isCloseConn) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int rt = -1;
		try {
			pstmt = conn.prepareStatement(sql);
			DBUtil.setparam(pstmt, param);
			rt = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(isCloseConn) {
				DBUtil.releaseDbResource(rs, pstmt, conn);
			} else {
				DBUtil.releaseDbResource(rs, pstmt, null);
			}
			
		}
		return rt;
	}
	
	/**
	 * 保存操作
	 * @param sql
	 * @param param
	 * @param conn
	 * @param isCloseConn
	 * @return
	 * @throws SQLException
	 */
	public int save(String sql, Object[] param, final Connection conn, boolean isCloseConn) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int rt = -1;
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			DBUtil.setparam(pstmt, param);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				rt = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(isCloseConn) {
				DBUtil.releaseDbResource(rs, pstmt, conn);
			} else {
				DBUtil.releaseDbResource(rs, pstmt, null);
			}
			
		}
		return rt;
	}
	
	/**
	 * 带1个参数（？）的查询方法
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Object> select(Connection conn,String sql, Object param, boolean isCloseConn) throws SQLException {
		return select(conn, sql, new Object[]{param}, isCloseConn);
	}
	
	/**
	 * 带多个参数（？，？，？...）的查询方法
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Object> select(Connection conn,String sql, Object[] params, boolean isCloseConn) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ps = conn.prepareStatement(sql);
			// 设置参数
			DBUtil.setparam(ps, params);
			rs = ps.executeQuery();
			if (rs.next()) {				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object obj = rs.getObject(i);
					map.put(key, obj);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(isCloseConn) {
				DBUtil.releaseDbResource(rs, ps, conn);
			} else {
				DBUtil.releaseDbResource(rs, ps, null);
			}
		}
		return map;
	}
	

	/**
	 * 带1个参数（？）的查询方法的查询方法，返回多条记录所对应的集合
	 * @param conn
	 * @param sql
	 * @param param
	 * @param isCloseConn
	 * @return
	 * @throws SQLException 
	 */
	public List<Map> selectList(Connection conn,String sql,Object param, boolean isCloseConn) throws SQLException {
		return selectList(conn, sql, new Object[]{param}, isCloseConn);
	}
	
	/**
	 * 分页查询：带1个参数（？）的查询方法的查询方法，返回多条记录所对应的集合
	 * @param conn
	 * @param sql
	 * @param param
	 * @param isCloseConn
	 * @return
	 * @throws SQLException 
	 */
	public List<Map> selectList(Connection conn, String sql, Object param, boolean isCloseConn, RowSelection rowSelection) throws SQLException {
		return selectList(conn, sql, new Object[]{param}, isCloseConn, rowSelection);
	}
	
	/**
	 * 分页查询：带多个参数（？，？，？...）的查询方法的查询方法，返回多条记录所对应的集合
	 * @param conn
	 * @param sql
	 * @param params
	 * @param isCloseConn
	 * @return
	 * @throws SQLException 
	 */
	public List<Map> selectList(Connection conn, String sql, Object[] params, boolean isCloseConn, RowSelection rowSelection) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<Map> list = new ArrayList<Map>();
		if(rowSelection.getFirstRow() >= 0) {
			sql = SQLUtil.preparePage(sql, rowSelection);
		}
		try {
			ps = conn.prepareStatement(sql);
			DBUtil.setparam(ps, params);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object obj = rs.getObject(i);
					map.put(key, obj);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(isCloseConn) {
				DBUtil.releaseDbResource(rs, ps, conn);
			} else {
				DBUtil.releaseDbResource(rs, ps, null);
			}
		}
		return list;
	}
	/**
	 * 带多个参数（？，？，？...）的查询方法的查询方法，返回多条记录所对应的集合
	 * @param conn
	 * @param sql
	 * @param params
	 * @param isCloseConn
	 * @return
	 * @throws SQLException 
	 */
	public List<Map> selectList(Connection conn,String sql,Object[] params, boolean isCloseConn) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<Map> list = new ArrayList<Map>();
		try {
			ps = conn.prepareStatement(sql);
			DBUtil.setparam(ps, params);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object obj = rs.getObject(i);
					map.put(key, obj);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(isCloseConn) {
				DBUtil.releaseDbResource(rs, ps, conn);
			} else {
				DBUtil.releaseDbResource(rs, ps, null);
			}
		}
		return list;
	}
	
	/**
	 * 带1个参数（？）的查询方法的查询方法，返回多条记录所对应带索引的集合
	 * @param conn
	 * @param sql
	 * @param param
	 * @param isCloseConn
	 * @return
	 * @throws SQLException 
	 */
	public Map<Object, Map> selectMap(Connection conn,String sql, Object param, String keyStr, boolean isCloseConn) throws SQLException {
		return selectMap(conn, sql, new Object[]{param}, keyStr, isCloseConn);
	}
	
	/**
	 * 分页查询：带1个参数（？）的查询方法的查询方法，返回多条记录所对应带索引的集合
	 * 
	 * @param conn
	 * @param sql
	 * @param param
	 * @param keyStr
	 * @param isCloseConn
	 * @param rowSelection
	 * @return
	 * @throws SQLException
	 */
	public Map<Object, Map> selectMap(Connection conn, String sql, Object param, String keyStr, boolean isCloseConn, RowSelection rowSelection) throws SQLException {
		return selectMap(conn, sql, new Object[]{param}, keyStr, isCloseConn, rowSelection);
	}
	
	/**
	 * 分页查询：带多个参数（？，？，？...）的查询方法的查询方法，返回多条记录所对应的集合
	 * @param conn
	 * @param sql
	 * @param params
	 * @param isCloseConn
	 * @return
	 * @throws SQLException 
	 */
	public Map<Object, Map> selectMap(Connection conn, String sql, Object[] params, String keyStr, boolean isCloseConn, RowSelection rowSelection) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Map<Object, Map> contentMap = new LinkedHashMap<Object, Map>();
		if(rowSelection.getFirstRow() >= 0) {
			sql = SQLUtil.preparePage(sql, rowSelection);
		}
		try {
			ps = conn.prepareStatement(sql);
			DBUtil.setparam(ps, params);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object obj = rs.getObject(i);
					map.put(key, obj);
				}
				contentMap.put(map.get(keyStr), map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(isCloseConn) {
				DBUtil.releaseDbResource(rs, ps, conn);
			} else {
				DBUtil.releaseDbResource(rs, ps, null);
			}
		}
		return contentMap;
	}

	/**
	 * 带多个参数（？，？，？...）的查询方法的查询方法，返回多条记录所对应的带索引集合
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @param keyStr
	 * @param isCloseConn
	 * @return
	 * @throws SQLException
	 */
	public Map<Object, Map> selectMap(Connection conn, String sql, Object[] params, String keyStr, boolean isCloseConn) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Map<Object, Map> contentMap = new LinkedHashMap<Object, Map>();
		try {
			ps = conn.prepareStatement(sql);
			DBUtil.setparam(ps, params);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object obj = rs.getObject(i);
					map.put(key, obj);
				}
				contentMap.put(map.get(keyStr), map);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(isCloseConn) {
				DBUtil.releaseDbResource(rs, ps, conn);
			} else {
				DBUtil.releaseDbResource(rs, ps, null);
			}
		}
		return contentMap;
	}
	
}
