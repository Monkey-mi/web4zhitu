package com.hts.web.operations.dao.mongo.impl;

import java.util.List;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.OpNearLabelDto;
import com.hts.web.operations.dao.mongo.NearLabelMongoDao;

public class NearLabelMongoDaoImpl extends BaseMongoDaoImpl implements NearLabelMongoDao {

	private static String collection = MongoHTS.NEAR_LABEL;
	
	@Override
	public List<OpNearLabelDto> queryNearLabel(double longitude, 
			double latitude, int start, int limit) {
		Point p = new Point(latitude, latitude);
		return getMongoTemplate().find(
				new Query(Criteria.where("loc").near(p)).skip((start-1)*limit).limit(limit), 
				OpNearLabelDto.class, collection);
		
		
	}

}
