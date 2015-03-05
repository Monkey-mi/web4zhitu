package com.hts.web.common.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据访问基础类
 * </p>
 * 
 * 
 * 创建时间：2012-10-18
 * @author ztj
 *
 */
public interface BaseDao {
	
	/**
	 * 获取世界连接前缀
	 * 
	 * @return
	 */
	public String getUrlPrefix();
	
	/**
	 * 单表条件查询
	 * 
	 * @param attrMap 查询条件
	 * @param tableName 表名
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> attrMap, String tableName);
	
	/**
	 * 单表列表查询
	 * 
	 * @param tableName
	 * @return
	 */
	public List<Map<String, Object>> queryMetaList(String tableName);
	
	/**
	 * 单表列表查询
	 * @param tableName
	 * @param orderBy
	 * @return
	 */
	public List<Map<String, Object>> queryMetaList(String tableName, String orderBy);
	
	/**
	 * 分页：单表列表查询
	 * @param tableName
	 * @return
	 */
	public List<Map<String, Object>> queryMetaList(int start, int limit, String tableName);
	
	/**
	 * 分页：单表列表查询
	 * @param start
	 * @param limit
	 * @param tableName
	 * @return
	 */
	public List<Map<String, Object>> queryMetaList(int start, int limit, String tableName, String orderBy);
	
	/**
	 * 分页：单表列表条件查询
	 * 
	 * @param attrMap
	 * @param tableName
	 * @return
	 */
	public List<Map<String, Object>> queryMetaList(Map<String,Object> attrMap, int start, int limit, String tableName);
	
	/**
	 * 分页：单表列表条件查询
	 * @param attrMap
	 * @param tableName
	 * @param orderBy
	 * @return
	 */
	public List<Map<String, Object>> queryMetaList(Map<String,Object> attrMap, int start, int limit, String tableName, String orderBy);
	
	/**
	 * 单表列表条件查询
	 * @param attrMap
	 * @param tableName
	 * @return
	 */
	public List<Map<String, Object>> queryMetaList(Map<String,Object> attrMap, String tableName);
	
	/** 
	 * 单表保存操作
	 * 
	 * @param attrMap 保存条件
	 * @param tableName 表名
	 * @param isCloseConn 是否关闭连接
	 * @return
	 * @throws SQLException 
	 * 
	 */
	public Integer save(Map<String, Object> attrMap, String tableName);
	
	/**
	 * 单表更新操作
	 * @param conn
	 * @param attrMap
	 * @param idKey
	 * @param id
	 * @param tableName
	 * @param isCloseConn
	 * @throws SQLException
	 */
	public void updateById(Map<String,Object> attrMap, Integer id, String tableName);
	
	/**
	 * 单表条件查询记录总数
	 * 
	 * @param conn 数据库连接
	 * @param attrMap 保存条件
	 * @param tableName 表名
	 * @param isCloseConn 是否关闭连接
	 * @throws SQLException 
	 */
	public Integer queryTotal(Map<String, Object> attrMap, String tableName);
	
	/**
	 * 单表条件查询记录总数
	 * @param conn
	 * @param tableName
	 * @param isCloseConn
	 * @return
	 * @throws SQLException
	 */
	public Integer queryTotal(String tableName);
	
	/**
	 * 根据id选择数据
	 * @param conn
	 * @param selections
	 * @param tableName
	 * @param id
	 * @param isCloseConn
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Object> selectById(String[] metas, String tableName, Integer id);
	
	/**
	 * 根据id选择数据
	 * @param conn
	 * @param selections
	 * @param tableName
	 * @param id
	 * @param isCloseConn
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Object> selectById(String tableName, Integer id);
	
	/**
	 * 根据id批量删除
	 * @param conn
	 * @param tableName
	 * @param idsStr
	 * @param isCloseConn
	 * @throws SQLException 
	 */
	public void deleteByIds(String tableName, String idsStr);
	
	/**
	 * 根据id批量删除
	 * @param conn
	 * @param tableName
	 * @param ids
	 * @param isCloseConn
	 * @throws SQLException
	 */
	public void deleteByIds(String tableName, Integer[] ids);
	
	/**
	 * 根据id批量删除
	 * @param tableName
	 * @param ids
	 * @param name
	 */
	public void deleteByIds(String tableName, Integer[] ids,String name);
	
	/**
	 * 根据obj批量删除
	 * @param conn
	 * @param tableName
	 * @param ids
	 * @param isCloseConn
	 * @param name
	 * @throws SQLException
	 */
	public void deleteByObjs(String tableName, Object[] objs,String name);
	
	/**
	 * 检测记录是否存在
	 * @param sql
	 * @param attr
	 * @return
	 * @throws SQLException
	 */
	public boolean checkRecordExists(String sql, Object[] attr);
	
	/**
	 * 使记录有效/无效
	 * 
	 * @param tableName
	 * @param valid
	 * @param id
	 */
	public void validRecord(String tableName, int valid, int id);
	
	/**
	 * 保存并返回主键
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public int save(String sql, Object...args);
	
}
