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
import com.hts.web.common.pojo.OpNearLabelDto;
import com.hts.web.operations.dao.mongo.NearLabelMongoDao;

@Repository("HTSNearLabelMongoDao")
public class NearLabelMongoDaoImpl extends BaseMongoDaoImpl implements NearLabelMongoDao {

	private static String collection = MongoHTS.NEAR_LABEL;

	@Override
	public List<OpNearLabelDto> queryNear(double longitude, double latitude, 
			double radius, int limit) {
		return queryNear(0, longitude, latitude, radius, limit);
	}

	@Override
	public List<OpNearLabelDto> queryNear(int maxId, double longitude, 
			double latitude, double radius, int limit) {
		Circle circle = new Circle(longitude, latitude, radius / 111);
		Criteria criteria = Criteria.where("loc").within(circle);
		if(maxId > 0) {
			criteria = criteria.and("serial").lte(maxId);
		}
		return getMongoTemplate()
				.find(new Query(criteria)
				.with(new Sort(Direction.DESC, "serial"))
				.limit(limit),
				OpNearLabelDto.class, collection);
	}
	

}
