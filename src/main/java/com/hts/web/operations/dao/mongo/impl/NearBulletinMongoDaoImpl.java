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
import com.hts.web.operations.dao.mongo.NearBulletinMongoDao;
import com.hts.web.operations.pojo.NearBulletin;

@Repository("HTSNearBulletinMongoDao")
public class NearBulletinMongoDaoImpl extends BaseMongoDaoImpl implements NearBulletinMongoDao {

	private static String collection = MongoHTS.NEAR_BULLETIN;
	
	@Override
	public List<NearBulletin> queryNear(double longitude, double latitude, 
			double radius, int limit) {
		Circle circle = new Circle(longitude, latitude, radius / 111);
		Criteria criteria = Criteria.where("loc").within(circle);
		return getMongoTemplate()
				.find(new Query(criteria)
				.with(new Sort(Direction.DESC, "serial"))
				.limit(limit),
				NearBulletin.class, collection);
	}

}
