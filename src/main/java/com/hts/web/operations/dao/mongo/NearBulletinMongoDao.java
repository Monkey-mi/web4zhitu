package com.hts.web.operations.dao.mongo;

import java.util.List;

import com.hts.web.common.dao.BaseMongoDao;
import com.hts.web.operations.pojo.NearBulletin;

/**
 * 
 * @author lynch
 *
 */
public interface NearBulletinMongoDao extends BaseMongoDao {

	/**
	 * 查询附近公告
	 * 
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param limit
	 * @return
	 */
	public List<NearBulletin> queryNear(double longitude, double latitude,
			double radius, int limit);
	
}
