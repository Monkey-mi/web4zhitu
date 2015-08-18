package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpUser;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.operations.dao.UserVerifyRecCacheDao;

@Repository("HTSUserVerifyRecCacheDao")
public class UserVerifyRecCacheDaoImpl extends BaseCacheDaoImpl<OpUser> 
	implements UserVerifyRecCacheDao {
	
	
	@Override
	public List<OpUser> queryUserByVerifyId(Integer verifyId, Integer limit) {
		BoundListOperations<String, OpUser> op = getRedisTemplate().boundListOps(
				CacheKeies.OP_USER_VERIFY_REC + verifyId);
		List<OpUser> list = op.range(0, limit - 1);
		return list;
	}
	
	@Override
	public List<OpUser> queryUserByVerifyIdWithTop(Integer verifyId, Integer limit) {

		List<OpUser> list = queryUserByVerifyId(verifyId, limit);
		
		List<OpUser> topList = getRedisTemplate().boundListOps(
				CacheKeies.OP_USER_VERIFY_REC_TOP).range(0, -1);
		
		if(topList != null && topList.size() > 0) {
			list.addAll(0, topList);
		}
		
		return list;
	}
	
	@Override
	public List<OpUser> queryRandomUserByVerifyId(Integer verifyId, Integer limit) {
		BoundListOperations<String, OpUser> op = getRedisTemplate().boundListOps(
				CacheKeies.OP_USER_VERIFY_REC + verifyId);
		int size = op.size().intValue();
		int start = NumberUtil.getRandomIndex(size-limit);
		
		List<OpUser> list = op.range(start, start + limit - 1);
		return list;
	}
	

	@Override
	public List<OpUser> queryRandomUserByVerifyIdWithTop(Integer verifyId, Integer limit) {
		
		List<OpUser> list = queryRandomUserByVerifyId(verifyId, limit);
		
		List<OpUser> topList = getRedisTemplate().boundListOps(
				CacheKeies.OP_USER_VERIFY_REC_TOP).range(0, -1);
		
		if(topList != null && topList.size() > 0) {
			list.addAll(0, topList);
		}
		
		return list;
	}


}
