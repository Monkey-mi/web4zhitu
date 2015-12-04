package com.hts.web.addr.dao.mongo.impl;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hts.web.addr.dao.mongo.CityMongoDao;
import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.AddrCity;

@Repository("HTSAddrCityMongoDao")
public class CityMongoDaoImpl extends BaseMongoDaoImpl implements CityMongoDao {

	private static String collection = MongoHTS.ADDR_CITY;
	
	@Override
	public AddrCity queryNearCity(double longitude, double latitude) {
		Point point = new Point(longitude, latitude);
		return getMongoTemplate().findOne(new Query(Criteria.where("loc").near(point)),
				AddrCity.class, collection);
	}

}
