package com.hts.web.operations.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.operations.pojo.AwardActivityBulletin;
import com.hts.web.operations.pojo.RecommendItemBulletin;
import com.hts.web.operations.pojo.SeckillBulletin;

/**
 * 商品公告缓存
 * 
 * @author zhangbo	2015年12月7日
 *
 */
@Repository
public class ItemBulletinCache {
	
	@Autowired
	private RedisTemplate<String, ? extends OpMsgBulletin> redisTemplate;
	
	/**
	 * 从redis中查询限时秒杀列表
	 * 
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	@SuppressWarnings("unchecked")
	public List<SeckillBulletin> querySeckill(RowSelection rowSelection) {
		return (List<SeckillBulletin>) redisTemplate.opsForList().range(CacheKeies.OP_MSG_SECKILL, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}

	/**
	 * 从redis中查询有奖活动列表
	 * 
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	@SuppressWarnings("unchecked")
	public List<AwardActivityBulletin> queryAwardActivity(RowSelection rowSelection) {
		return (List<AwardActivityBulletin>) redisTemplate.opsForList().range(CacheKeies.OP_MSG_AWARD_ACTIVITY, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}

	/**
	 * 从redis中查询推荐商品列表
	 * 
	 * @param rowSelection	分页对象
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	@SuppressWarnings("unchecked")
	public List<RecommendItemBulletin> queryRecommendItem(RowSelection rowSelection) {
		return (List<RecommendItemBulletin>) redisTemplate.opsForList().range(CacheKeies.OP_MSG_RECOMMEND_ITEM, rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}
}
