package com.hts.web.operations.dao.mongo;

import com.hts.web.common.dao.BaseMongoDao;
import com.hts.web.common.pojo.OpNearWorldDto;


/**
 * 织图MongoDB数据访问接口
 * 
 * @author lynch 2015-12-01
 *
 */
public interface NearWorldLastMongoDao extends BaseMongoDao {

	/**
	 * 保存织图
	 * 
	 * @param world
	 */
	public void saveWorld(OpNearWorldDto world);
}
