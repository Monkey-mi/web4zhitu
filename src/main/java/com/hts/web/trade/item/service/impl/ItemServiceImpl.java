package com.hts.web.trade.item.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.operations.dao.ItemBulletinCache;
import com.hts.web.operations.pojo.RecommendItemBulletin;
import com.hts.web.operations.pojo.SeckillBulletin;
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
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemSetDao itemSetDao;
	
	@Autowired
	private ItemLikeDao itemLikeDao;
	
	@Autowired
	private ItemBulletinCache ibCache;

	@Override
	public void queryItemInfo(Integer itemSetId, Integer uid, Map<String, Object> jsonMap) throws Exception {
		List<ItemDTO> itemList = itemSetDao.queryItemListBySetId(itemSetId);
		
		for (ItemDTO dto : itemList) {
			// 设置是否已经赞过
			dto.setLiked(itemLikeDao.isExist(dto.getId(), uid));
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
	
}