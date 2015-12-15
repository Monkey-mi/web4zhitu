package com.hts.web.operations.dao.mongo.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.OpNearWorldDto;
import com.hts.web.operations.dao.mongo.NearWorldLastMongoDao;

@Repository("HTSNearWorldLastMongoDao")
public class NearWorldLastMongoDaoImpl extends BaseMongoDaoImpl implements NearWorldLastMongoDao {

	private static String collection = MongoHTS.NEAR_WORLD_LAST;
	
	@Override
	public void saveWorld(OpNearWorldDto world) {
		getMongoTemplate().insert(world, collection);
	}


}
