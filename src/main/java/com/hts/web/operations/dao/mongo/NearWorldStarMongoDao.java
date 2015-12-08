package com.hts.web.operations.dao.mongo;

import java.util.List;

import com.hts.web.common.dao.BaseMongoDao;
import com.hts.web.common.pojo.OpNearWorldDto;

/**
 * 明星织图MongoDB数据访问接口
 * 
 * @author lynch
 *
 */
public interface NearWorldStarMongoDao extends BaseMongoDao {

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
	 * 查询附近的织图
	 * 
	 * @param maxId
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param limit
	 * @return
	 */
	public List<OpNearWorldDto> queryNear(int maxId, double longitude, 
			double latitude, double radius, int limit);
	
	/**
	 * 查询附近的织图
	 * 
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param limit
	 * @return
	 */
	public List<OpNearWorldDto> queryNear(double longitude, 
			double latitude, double radius, int limit);
	
	/**
	 * 查询附近织图
	 * @param cityId
	 * @param limit
	 * @return
	 * @author zxx 2015-12-8 19:33:48
	 */
	public List<OpNearWorldDto> queryNear(int maxId,int cityId,int limit);
	
}
