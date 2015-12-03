package com.hts.web.operations.dao.mongo;

import java.util.List;

import com.hts.web.common.pojo.OpNearLabelDto;

/**
 * 附近标签MongoDB数据访问接口
 * 
 * @author lynch 2015-12-03
 *
 */
public interface NearLabelMongoDao {

	/**
	 * 查询附近标签
	 * 
	 * @param longitude
	 * @param latitude
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<OpNearLabelDto> queryNearLabel(double longitude, double latitude,
			int start, int limit);
	
}
