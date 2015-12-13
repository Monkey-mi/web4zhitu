package com.hts.web.trade.item.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.operations.dao.ItemBulletinCache;
import com.hts.web.operations.pojo.AwardActivityBulletin;
import com.hts.web.operations.pojo.RecommendItemBulletin;
import com.hts.web.operations.pojo.SeckillBulletin;
import com.hts.web.trade.item.dao.ItemCache;
import com.hts.web.trade.item.dao.ItemLikeDao;
import com.hts.web.trade.item.dao.ItemSetDao;
import com.hts.web.trade.item.dto.ItemDTO;
import com.hts.web.trade.item.service.ItemService;

/**
 * 商品业务实现类
 * 
 * @author zhangbo	2015年12月10日
 *
 */
@Service("com.hts.web.trade.item.service.impl.ItemServiceImpl")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemLikeDao itemLikeDao;
	
	@Autowired
	private ItemBulletinCache ibCache;
	
	@Autowired
	private ItemCache itemCache;

	@Override
	public void queryItemInfo(Integer itemSetId, Integer uid, Map<String, Object> jsonMap) throws Exception {
		// 根据商品集合id，查询其下商品列表
		final List<ItemDTO> itemList = itemCache.queryItemListBySetId(itemSetId, new RowSelection(1,0));
		
		// 查询是否点过赞
		if(uid > 0 && itemList != null && !itemList.isEmpty()) {
			final Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
			Integer[] itemIds = new Integer[itemList.size()];
			
			for(int i = 0; i < itemList.size(); i++) {
				idxMap.put(itemList.get(i).getId(), i);
				itemIds[i] = itemList.get(i).getId();
			}
			
			itemLikeDao.queryLike(itemIds, uid, new RowCallback<Integer>() {
				
				@Override
				public void callback(Integer t) {
					Integer idx = idxMap.get(t);
					itemList.get(idx).setLiked(true);
				}
			});
		}
		
		// 得到商品集合
		OpMsgBulletin itemSet = getItemSet(itemSetId);
		
		// 构造返回值
		jsonMap.put(OptResult.JSON_KEY_ITEMSET, itemSet);
		jsonMap.put(OptResult.JSON_KEY_ITEMS, itemList);
	}

	/**
	 * 获取商品集合，根据id
	 * 
	 * @param itemSetId
	 * @return
	 * @author zhangbo	2015年12月10日
	 */
	private OpMsgBulletin getItemSet(Integer itemSetId) {
		OpMsgBulletin rtn = null;
		
		// 定义分页查询，设置起始页为1，每页数量为0，即为查询全部数据
		RowSelection rowSelection = new RowSelection(1, 0);
		
		// 先查询限时秒杀
		List<SeckillBulletin> seckillList = ibCache.querySeckill(rowSelection);
		for (SeckillBulletin seckill : seckillList) {
			if ( itemSetId == seckill.getId() ) {
				rtn = seckill;
			}
		}
		
		// 再查询推荐商品
		List<RecommendItemBulletin> recommendItemList = ibCache.queryRecommendItem(rowSelection);
		for (RecommendItemBulletin recommendItem : recommendItemList) {
			if ( itemSetId == recommendItem.getId() ) {
				rtn = recommendItem;
			}
		}
		
		return rtn;
	}

	@Override
	public void likeItem(Integer itemId, Integer uid) throws Exception {
		itemLikeDao.saveLikeItem(itemId, uid);
	}

	@Override
	public Integer getItemSetMaxId() throws Exception {
		// 定义商品集合最大id
		Integer itemSetMaxId = 0;
		
		// 定义有奖活动最大id
		Integer activityMaxId = 0;
		
		// 定义分页查询，设置起始页为1，每页数量为0，即为查询全部数据
		RowSelection rowSelection = new RowSelection(1, 0);
		
		// 冒泡查询限时秒杀商品集合最大id
		List<SeckillBulletin> seckillList = ibCache.querySeckill(rowSelection);
		for (SeckillBulletin seckill : seckillList) {
			if ( seckill.getId() > itemSetMaxId ) {
				itemSetMaxId = seckill.getId();
			}
		}
		
		// 冒泡查询推荐商品集合最大id
		List<RecommendItemBulletin> recommendItemList = ibCache.queryRecommendItem(rowSelection);
		for (RecommendItemBulletin recommendItem : recommendItemList) {
			if ( recommendItem.getId() > itemSetMaxId ) {
				itemSetMaxId = recommendItem.getId();
			}
		}
		
		// 冒泡查询有奖活动最大id
		List<AwardActivityBulletin> awardActivityList = ibCache.queryAwardActivity(rowSelection);
		for (AwardActivityBulletin awardActivity : awardActivityList) {
			if ( awardActivity.getId() > activityMaxId ) {
				activityMaxId = awardActivity.getId();
			}
		}
		
		return itemSetMaxId + activityMaxId;
	}
	
}
