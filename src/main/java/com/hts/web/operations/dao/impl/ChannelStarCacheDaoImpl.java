package com.hts.web.operations.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannelStar;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.operations.dao.ChannelStarCacheDao;

@Repository("HTSChannelStarCacheDao")
public class ChannelStarCacheDaoImpl extends BaseCacheDaoImpl<UserInfoDto> implements
		ChannelStarCacheDao {

	@Override
	public List<OpChannelStar> queryStar(Integer channelId) {
		HashOperations<String, Integer, List<OpChannelStar>> ops = getRedisTemplate().opsForHash();
		List<OpChannelStar> list = ops.get(CacheKeies.OP_CHANNEL_STAR, channelId);
		if(list == null) 
			list = new ArrayList<OpChannelStar>();
		return list;
	}
}
