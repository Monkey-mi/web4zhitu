package com.hts.web.trade.item.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.trade.item.dao.ItemCacheDao;
import com.hts.web.trade.item.dto.ItemDTO;

/**
 * 商品redis缓存操作实现类
 * 
 * @author zhangbo	2015年12月21日
 *
 */
@Repository("com.hts.web.trade.item.dao.ItemCache")
public class ItemCacheDaoImpl extends BaseCacheDaoImpl<ItemDTO> implements ItemCacheDao {

	@Override
	public List<ItemDTO> queryItemListBySetId(Integer itemSetId, RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.ITEM_LIST_BY_SETID + itemSetId, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}

	@Override
	public void refreshItemLikeNumPlusOne(Integer itemId) {
		if(getRedisTemplate().hasKey(CacheKeies.ITEM_POSITION_IN_SET + itemId)) {
			
			// 获取商品位置key，对应下的Map，Map结构为key：商品集合id，value：此商品集合其下的商品list的index脚标。注：由于下列代码泛型不允许强制转换，故在后续的循环中强制转换
			Map<Object, Object> entries = getRedisTemplate().opsForHash().entries(CacheKeies.ITEM_POSITION_IN_SET + itemId);
			
			// 为每个商品集合其下的商品list中，商品点赞数都加1
			for (Object itemSetId : entries.keySet()) {
				// 得到商品集合其下的商品列表redis缓存key，itemSetId强制转换为Integer类型
				String redisKey = CacheKeies.ITEM_LIST_BY_SETID + (Integer)itemSetId;
				
				// 得到商品在列表中对应的被记录起来的脚标
				Integer index = (Integer)entries.get(itemSetId);
				
				// 根据记录的脚标，获取redis缓存中商品列表对应的商品对象，注：脚标也要强制转换为Integer
				ItemDTO item = getRedisTemplate().opsForList().index(redisKey, index);
				
				// 为点赞数加1
				item.setLike(item.getLike() + 1);
				
				// 在重新刷新相应脚标位置的对象
				getRedisTemplate().opsForList().set(redisKey, index, item);
			}
		}
		
	}

}
