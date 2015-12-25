package com.hts.web.operations.dao.mongo.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.MongoHTS;
import com.hts.web.common.dao.impl.BaseMongoDaoImpl;
import com.hts.web.common.pojo.AddrDistrictDto;
import com.hts.web.operations.dao.mongo.NearDistrictMongoDao;

@Repository("NearDistrictMongoDao")
public class NearDistrictMongoDaoImpl extends BaseMongoDaoImpl implements NearDistrictMongoDao{

	private static String collection = MongoHTS.ADDR_CITY_DISTRICT;
	
	@Override
	public void insertDistrict(AddrDistrictDto dto) {
		getMongoTemplate().insert(dto,collection);
	}

	@Override
	public void deleteDistrict(Integer id) {
		getMongoTemplate().remove(new Query(Criteria.where("_id").is(id)), collection);
	}

	@Override
	public List<AddrDistrictDto> queryDistrict(Integer cityId) {
		Criteria criteria = Criteria.where("cityId").is(cityId);
		return getMongoTemplate().find(new Query(criteria), AddrDistrictDto.class,collection);
	}

	@Override
	public AddrDistrictDto queryDistrictById(Integer id) {
		return getMongoTemplate().findOne(new Query(Criteria.where("_id").is(id)), AddrDistrictDto.class,collection);
	}
}
