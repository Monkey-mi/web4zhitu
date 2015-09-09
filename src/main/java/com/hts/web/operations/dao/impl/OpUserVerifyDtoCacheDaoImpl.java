package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpUserVerifyDto;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.operations.dao.OpUserVerifyDtoCacheDao;

@Repository("HTSOpUserVerifyDtoCacheDao")
public class OpUserVerifyDtoCacheDaoImpl extends BaseCacheDaoImpl<OpUserVerifyDto> implements
		OpUserVerifyDtoCacheDao {

	@Override
	public List<OpUserVerifyDto> queryVerify() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_USER_VERIFY, 0, -1);
	}

	@Override
	public OpUserVerifyDto queryRandomVerify() {
		BoundListOperations<String, OpUserVerifyDto> ops = 
				getRedisTemplate().boundListOps(CacheKeies.OP_USER_VERIFY);
		Long realSize = ops.size();
		
		if(realSize != null && realSize > 0) {
			int size = realSize.intValue() + 1;
			int idx = NumberUtil.getRandomIndex(size);
			
			if(idx < realSize) {
				return ops.index(idx);
			} else {
				return new OpUserVerifyDto(0, "推荐", "推荐达人", null);
			}
		}
		return null;
	}
	
}
