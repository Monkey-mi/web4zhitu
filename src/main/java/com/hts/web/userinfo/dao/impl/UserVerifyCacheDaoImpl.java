package com.hts.web.userinfo.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.hts.web.common.pojo.ObjectWithUserVerifyDesc;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.userinfo.dao.UserVerifyCacheDao;

@Repository("HTSUserVerifyCacheDao")
public class UserVerifyCacheDaoImpl extends BaseCacheDaoImpl<UserVerify> implements UserVerifyCacheDao {

	@Override
	public void setUpVerify(List<? extends ObjectWithUserVerify> objs) {
		BoundHashOperations<String, Integer, UserVerify> ops = getRedisTemplate()
				.boundHashOps(CacheKeies.USER_VERIFY);
		Map<Integer, UserVerify> map = ops.entries();
		for(ObjectWithUserVerify obj : objs) {
			Integer vid = obj.getVerifyId();
			if(map.containsKey(vid)) {
				UserVerify uv = map.get(vid);
				obj.setVerifyName(uv.getVerifyName());
				obj.setVerifyIcon(uv.getVerifyIcon());
			}
		}
	}

	@Override
	public void setUpVerify(ObjectWithUserVerify obj) {
		BoundHashOperations<String, Integer, UserVerify> ops = getRedisTemplate()
				.boundHashOps(CacheKeies.USER_VERIFY);
		Integer vid = obj.getVerifyId();
		if(ops.hasKey(vid)) {
			UserVerify uv = ops.get(vid);
			obj.setVerifyName(uv.getVerifyName());
			obj.setVerifyIcon(uv.getVerifyIcon());
		}
	}

	@Override
	public void setUpVerifyDesc(ObjectWithUserVerifyDesc obj) {
		BoundHashOperations<String, Integer, UserVerify> ops = getRedisTemplate()
				.boundHashOps(CacheKeies.USER_VERIFY);
		Integer vid = obj.getVerifyId();
		if(ops.hasKey(vid)) {
			UserVerify uv = ops.get(vid);
			obj.setVerifyName(uv.getVerifyName());
			obj.setVerifyIcon(uv.getVerifyIcon());
			obj.setVerifyDesc(uv.getVerifyDesc());
		}
	}

	@Override
	public Map<Integer, UserVerify> queryVerify() {
		BoundHashOperations<String, Integer, UserVerify> ops = getRedisTemplate()
				.boundHashOps(CacheKeies.USER_VERIFY);
		Map<Integer, UserVerify> map = ops.entries();
		return map;
	}

	
}
