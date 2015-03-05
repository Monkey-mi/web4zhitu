package com.hts.web.ztworld.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.HTWorldChildWorld;
import com.hts.web.common.pojo.HTWorldChildWorldDto;
import com.hts.web.common.pojo.HTWorldChildWorldThumb;

/**
 * <p>
 * 子世界数据访问对象
 * </p>
 * 
 * 创建时间：2012-11-01
 * 
 * @author ztj
 * 
 */
public interface HTWorldChildWorldDao extends BaseDao {

	/**
	 * 保存子世界信息
	 * 
	 * @param childWorld
	 * @return
	 */
	public void saveChildWorld(HTWorldChildWorld childWorld);

	/**
	 * 分页查询：根据所在世界ID查询子世界
	 * 
	 * @param conn
	 * @param worldId
	 * @return
	 */
	public List<HTWorldChildWorldDto> queryShowChildWorldDtosByWorldId(
			Integer worldId,RowSelection rowSelection);
	
	/**
	 * 查询指定世界的子世界信息
	 * @param conn
	 * @param worldId
	 * @return
	 */
	public List<HTWorldChildWorldDto> queryShowChildWorldDtosByWorldId(
			Integer worldId);
	
	/**
	 * 根据所在子世界id查询其拥有的所有子世界信息元数据
	 * 
	 * @param conn
	 * @param atId
	 * @return
	 */
	
	public List<HTWorldChildWorld> queryChildWorldMetaListByAtId(Integer atId);
	
	/**
	 * 根据世界id查询其封面子世界信息
	 * 
	 * @param conn
	 * @param worldId
	 * @return
	 */
	public HTWorldChildWorld queryTitleChildWorldMetaByWorldId(Integer worldId);

	/**
	 * 根据子世界的所有缩略图
	 * 
	 * @return
	 */
	public List<HTWorldChildWorldThumb> queryThumbsByAtId(Integer atId);
	
	/**
	 * 根据所在id查询所有缩略图
	 * @param atIds
	 * @return
	 */
	public Map<Integer, List<HTWorldChildWorldThumb>> queryThumbsMapByAtId(Integer[] atIds);
	
	/**
	 * 查询子世界总数
	 * @param conn
	 * @param worldId
	 * @return
	 */
	public Long queryChildWorldCountByWorldId(Integer worldId);
	
	/**
	 * 查询指定子世界的索引
	 * @param worldId
	 * @param childId
	 * @return
	 */
	public Long queryChildWorldIndex(Integer worldId, Integer childId);
	
	/**
	 * 查询封面子世界
	 * @param worldId
	 * @return
	 */
	public HTWorldChildWorld queryTitleChildWorld(Integer worldId);
	
	/**
	 * 根据织图id删除子世界
	 * 
	 * @param worldIds
	 */
	public void deleteByWorldIds(Integer[] worldIds);
	
	/**
	 * 查询所有子世界
	 * 
	 * @param worldId
	 * @return
	 */
	public void queryAllChild(Integer worldId, RowCallback<HTWorldChildWorld> callback);
		
	
	/**
	 * 根据结果集构建子世界信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public HTWorldChildWorld buildChildWorldByResultSet(ResultSet rs) throws SQLException;
	
}
