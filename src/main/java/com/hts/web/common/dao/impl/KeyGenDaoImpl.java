package com.hts.web.common.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.KeyGenDao;


/**
 * <p>
 * 主键生成器数据访问对象
 * </p>
 * 
 * 创建时间：2013-11-13
 * @author ztj
 *
 */
@Repository("HTSKeyGenDao")
public class KeyGenDaoImpl extends BaseDaoImpl implements KeyGenDao{

	@Autowired
	private RedisTemplate<String, String> redisKeyTemplate;
	
	
	public RedisTemplate<String, String> getRedisKeyTemplate() {
		return redisKeyTemplate;
	}

	public static String table = HTS.KEYGEN;
	
	@Override
	public Integer nextId(String keyId) {
		return nextId(keyId, 1);
	}

	@Override
	public Integer nextId(final String keyId, final long step) {
		return getRedisKeyTemplate().execute(new SessionCallback<Integer>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Integer execute(RedisOperations operations)
					throws DataAccessException {
				ValueOperations<String, String> vops = operations.opsForValue();
				operations.multi();
				vops.increment(keyId, step);
				List<Object> reslist = operations.exec();
				if(reslist != null && !reslist.isEmpty()) {
					return Integer.parseInt(reslist.get(0).toString());
				}
				return null;
			}
		});
//		return 0;
	}
}
