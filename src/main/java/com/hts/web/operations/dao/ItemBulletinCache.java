package com.hts.web.operations.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.operations.pojo.ItemSetBulletin;

/**
 * 商品公告缓存
 * 
 * @author zhangbo	2015年12月7日
 *
 */
@Repository
public class ItemBulletinCache extends BaseCacheDaoImpl<ItemSetBulletin>{
	
	/**
	 * 从redis中查询限时秒杀列表
	 * 
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	public List<ItemSetBulletin> querySeckill(RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_MSG_SECKILL, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}

	/**
	 * 从redis中查询有奖活动列表
	 * 
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	public List<ItemSetBulletin> queryAwardActivity(RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_MSG_AWARD_ACTIVITY, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}

	/**
	 * 从redis中查询推荐商品列表
	 * 
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	public List<ItemSetBulletin> queryRecommendItem(RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.OP_MSG_RECOMMEND_ITEM, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}
}
