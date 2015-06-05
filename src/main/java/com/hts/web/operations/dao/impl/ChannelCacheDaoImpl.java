package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.operations.dao.ChannelCacheDao;

@Repository("HTSChannelCacheDao")
public class ChannelCacheDaoImpl extends BaseCacheDaoImpl<OpChannel> implements
		ChannelCacheDao {

	@Override
	public List<OpChannel> queryChannel() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL, 0, -1);
	}
	
	@Override
	public List<OpChannel> queryChannel(RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL, 
				rowSelection.getFirstRow(), rowSelection.getMaxRow()-1);
	}

	@Override
	public void updateChannel(List<OpChannel> topList,
			List<OpChannel> superbList) {
		for(OpChannel c : topList) {
			c.setThemeId(0);
		}
		topList.addAll(superbList);
		if(getRedisTemplate().hasKey(CacheKeies.OP_CHANNEL)) {
			getRedisTemplate().delete(CacheKeies.OP_CHANNEL);
		}
		if(topList.size() > 0) {
			OpChannel[] list = new OpChannel[topList.size()];
			getRedisTemplate().opsForList().rightPushAll(CacheKeies.OP_CHANNEL, topList.toArray(list));
		}
	}

	@Override
	public List<OpChannel> queryOldChannel() {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_CHANNEL_OLD, 0, -1);
	}

}
