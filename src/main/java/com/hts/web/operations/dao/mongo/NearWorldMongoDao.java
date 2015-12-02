package com.hts.web.operations.dao.mongo;

import java.util.List;

import com.hts.web.common.pojo.HTWorldInteractDto;

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
	public void saveWorld(HTWorldInteractDto world);
	
	/**
	 * 删除织图
	 * 
	 * @param id
	 */
	public void deleteWorld(Integer id);
	
	/**
	 * 查询附近的织图
	 * 
	 * @param longitude
	 * @param latitude
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<HTWorldInteractDto> queryNear(double longitude, double latitude, 
			int start, int limit);
}
