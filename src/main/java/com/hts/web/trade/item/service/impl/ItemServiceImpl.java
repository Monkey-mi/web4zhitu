package com.hts.web.trade.item.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.operations.dao.ItemBulletinCache;
import com.hts.web.operations.pojo.ItemSetBulletin;
import com.hts.web.trade.item.dao.ItemCacheDao;
import com.hts.web.trade.item.dao.ItemDao;
import com.hts.web.trade.item.dao.ItemLikeDao;
import com.hts.web.trade.item.dao.ItemSetDao;
import com.hts.web.trade.item.dto.ItemDTO;
import com.hts.web.trade.item.dto.ItemSetDTO;
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
	private ItemCacheDao itemCache;
	
	@Autowired
	private ItemSetDao itemSetDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Override
	public void queryItemInfo(Integer itemSetId, Integer uid, Map<String, Object> jsonMap) throws Exception {
		// 根据商品集合id，查询其下商品列表
		final List<ItemDTO> itemList = itemCache.queryItemListBySetId(itemSetId, new RowSelection(1,0));
		
		// 查询是否点过赞
		if(uid != null && uid > 0 && itemList != null && !itemList.isEmpty()) {
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
					itemList.get(idx).setIsLiked(true);
				}
			});
		}
		
		// 得到商品集合
		ItemSetBulletin itemSet = getItemSet(itemSetId);
		
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
	 * @throws Exception 
	 */
	private ItemSetBulletin getItemSet(Integer itemSetId) throws Exception {
		ItemSetBulletin rtn = null;
		
		// 定义分页查询，设置起始页为1，每页数量为0，即为查询全部数据
		RowSelection rowSelection = new RowSelection(1, 0);
		
		// 先查询限时秒杀
		List<ItemSetBulletin> seckillList = ibCache.querySeckill(rowSelection);
		for (ItemSetBulletin seckill : seckillList) {
			if ( itemSetId.equals(seckill.getId()) ) {
				rtn = seckill;
			}
		}
		
		// 若不在秒杀中，则rtn为null，则根据id去数据库中查询对应的商品集合
		if ( rtn == null ) {
			ItemSetDTO itemSet = itemSetDao.getItemSet(itemSetId);
			
			rtn = new ItemSetBulletin();
			rtn.setId(itemSet.getId());
			rtn.setBulletinName(itemSet.getTitle());
			rtn.setBulletinDesc(itemSet.getDescription());
			rtn.setBulletinPath(itemSet.getPath());
			rtn.setBulletinThumb(itemSet.getThumb());
			rtn.setBulletinType(itemSet.getType());
			rtn.setLink(itemSet.getLink());
		}
		
		return rtn;
	}

	@Override
	public void likeItem(Integer itemId, Integer uid) throws Exception {
		// 保存此用户为商品点赞
		itemLikeDao.saveLikeItem(itemId, uid);
		
		// 为商品点赞数+1
		itemDao.likeNumPlusOne(itemId);
		
		// 根据商品id查询出所在的商品集合id
		
		// 刷新商品点赞数
		itemCache.refreshItemLikeNumPlusOne(itemId);
	}

	@Override
	public Integer getItemSetMaxId() throws Exception {
		// 定义限时秒杀最大id
		Integer seckillMaxId = 0;
		
		// 定义有奖活动最大id
		Integer activityMaxId = 0;
		
		// 定义推荐活动最大id
		Integer recommendItemMaxId = 0;
		
		// 定义分页查询，设置起始页为1，每页数量为0，即为查询全部数据
		RowSelection rowSelection = new RowSelection(1, 0);
		
		// 限时秒杀商品集合最大id，限时秒杀都是按照serial倒序排列，所以直接取第一个对象的serial
		List<ItemSetBulletin> seckillList = ibCache.querySeckill(rowSelection);
		if ( seckillList != null && seckillList.size() != 0 ) {
			seckillMaxId = seckillList.get(0).getSerial();
		}
		
		// 有奖活动最大id，有奖活动都是按照serial倒序排列，所以直接取第一个对象的serial
		List<ItemSetBulletin> awardActivityList = ibCache.queryAwardActivity(rowSelection);
		if ( awardActivityList != null && awardActivityList.size() != 0 ) {
			activityMaxId = awardActivityList.get(0).getSerial();
		}
		
		// 推荐商品集合最大id，推荐商品都是按照serial倒序排列，所以直接取第一个对象的serial
		List<ItemSetBulletin> recommendItemList = ibCache.queryRecommendItem(rowSelection);
		if ( recommendItemList != null && recommendItemList.size() != 0 ) {
			recommendItemMaxId = recommendItemList.get(0).getSerial();
		}
		
		return seckillMaxId + activityMaxId + recommendItemMaxId;
	}
	
}
