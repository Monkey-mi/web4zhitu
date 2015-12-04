package com.hts.web.operations.dao.mongo;

import java.util.List;

import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.OpNearLabelDto;

/**
 * 附近标签MongoDB数据访问接口
 * 
 * @author lynch 2015-12-03
 *
 */
public interface NearLabelMongoDao {

	/**
	 * 查询附近的织图
	 * 
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param limit
	 * @return
	 * @author lynch 2015-12-03
	 */
	public List<OpNearLabelDto> queryNear(double longitude, double latitude,
			double radius, int limit);
	
	/**
	 * 根据最大id查询附近织图
	 * 
	 * @param maxId
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param limit
	 * @return
	 * @author lynch 2015-12-03
	 */
	public List<OpNearLabelDto> queryNear(int maxId, double longitude, double latitude,
			double radius, int limit);
	
}
