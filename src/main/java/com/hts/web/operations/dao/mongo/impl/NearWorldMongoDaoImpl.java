package com.hts.web.operations.dao.mongo.impl;


import java.util.List;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.operations.dao.mongo.NearWorldMongoDao;

@Repository("HTSWorldMongoDao")
public class NearWorldMongoDaoImpl extends BaseMongoDaoImpl implements NearWorldMongoDao {

	private static String collection = MongoHTS.NEAR_WORLD;
	
	@Override
	public void saveWorld(HTWorldInteractDto world) {
		getMongoTemplate().insert(world, collection);
	}

	@Override
	public void deleteWorld(Integer id) {
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), collection);
	}

	@Override
	public List<HTWorldInteractDto> queryNear(double longitude, double latitude, 
			int start, int limit) {
		Point p = new Point(latitude, latitude);
		return getMongoTemplate().find(
				new Query(Criteria.where("loc").near(p)).skip((start-1)*limit).limit(limit), 
				HTWorldInteractDto.class, collection);
	}

}
