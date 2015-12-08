package com.hts.web.operations.dao.mongo;

import java.util.List;

import com.hts.web.common.pojo.OpNearWorldDto;

/**
 * 织图MongoDB数据访问接口
 * 
 * @author lynch 2015-12-01
 *
 */
public interface NearWorldMongoDao {

	/**
	 * 保存织图
	 * 
	 * @param world
	 */
	public void saveWorld(OpNearWorldDto world);
	
	/**
	 * 删除织图
	 * 
	 * @param id
	 */
	public void deleteWorld(Integer id);
	
	/**
	 * 查询半径内的织图
	 * 
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param limit
	 * @return
	 */
	public List<OpNearWorldDto> queryNear(double longitude, double latitude,
			double radius, int limit);
	
	/**
	 * 根据最大id查询半径内织图
	 * 
	 * @param maxId
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param limit
	 * @return
	 */
	public List<OpNearWorldDto> queryNear(int maxId, double longitude, double latitude,
			double radius, int limit);
	
	/**
	 * 根据城市id查询织图
	 * 
	 * @param cityId
	 * @param limit
	 * @return
	 */
	public List<OpNearWorldDto> queryWorldByCityId(int maxId, Integer cityId,
			int limit);
}
