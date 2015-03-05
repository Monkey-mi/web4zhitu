package com.hts.web.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.hts.web.common.dao.BaseCacheDao;

@Repository
public class BaseCacheDaoImpl<T> implements BaseCacheDao {

	@Autowired
	private RedisTemplate<String, T> redisTemplate;
	
	public RedisTemplate<String, T> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
