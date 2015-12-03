package com.hts.web.operations.dao.mongo.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.operations.dao.mongo.NearWorldStarMongoDao;

@Repository("HTSWorldStarMongoDao")
public class NearWorldStarMongoDaoImpl extends BaseMongoDaoImpl implements NearWorldStarMongoDao {

	private static String collection = MongoHTS.NEAR_WORLD_STAR;
	
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
			double radius, int limit) {
		return queryNear(0, longitude, latitude, radius, limit);
	}

	@Override
	public List<HTWorldInteractDto> queryNear(int maxId, double longitude,
			double latitude, double radius, int limit) {
		Circle circle = new Circle(longitude, latitude, radius / 111);
		Criteria criteria = Criteria.where("loc").within(circle);
		if(maxId > 0) {
			criteria = criteria.and("_id").lt(maxId);
		}
		return getMongoTemplate()
				.find(new Query(criteria)
				.with(new Sort(Direction.DESC, "_id"))
				.limit(limit),
				HTWorldInteractDto.class, collection);
	}

}
