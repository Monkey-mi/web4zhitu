package com.hts.web.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.hts.web.common.dao.BaseMongoDao;

public class BaseMongoDaoImpl implements BaseMongoDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	protected MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

}
