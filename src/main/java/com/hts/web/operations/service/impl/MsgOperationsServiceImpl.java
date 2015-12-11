package com.hts.web.operations.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.common.pojo.OpNotice;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.operations.dao.BulletinCacheDao;
import com.hts.web.operations.dao.ItemBulletinCache;
import com.hts.web.operations.dao.NoticeCacheDao;
import com.hts.web.operations.dao.StartPageCacheDao;
import com.hts.web.operations.dao.SysMsgDao;
import com.hts.web.operations.pojo.AwardActivityBulletin;
import com.hts.web.operations.pojo.RecommendItemBulletin;
import com.hts.web.operations.pojo.SeckillBulletin;
import com.hts.web.operations.service.MsgOperationsService;

@Service("HTSMsgOperationsService")
public class MsgOperationsServiceImpl extends BaseServiceImpl implements MsgOperationsService{

	@Autowired
	private NoticeCacheDao noticeCacheDao;
	
	@Autowired
	private StartPageCacheDao startPageCacheDao;
	
	@Autowired
	private SysMsgDao sysMsgDao;

	@Autowired
	private BulletinCacheDao bulletinCacheDao;
	
	@Autowired
	private ItemBulletinCache itemBulletinCache;
	
	@Override
	public void buildNotice(Integer userId, Integer phoneCode, Map<String, Object> jsonMap) throws Exception {
		OpNotice notice = noticeCacheDao.queryNotice(phoneCode);
		jsonMap.put(OptResult.MSG, notice);
		
		OpSysMsgDto userRecMsg = sysMsgDao.queryMsgByObjType(userId, Tag.USER_MSG_USER_RECOMMEND);
		if(userRecMsg != null) {
			jsonMap.put(OptResult.JSON_KEY_USER_REC_MSG, userRecMsg);
		}
	}
	
	@Override
	public void buildStartPage(Map<String, Object> jsonMap) throws Exception {
		jsonMap.put(OptResult.JSON_KEY_MSG, 
				startPageCacheDao.queryStartPage());
	}

	@Override
	public void buildBulletin(Map<String, Object> jsonMap) throws Exception {
		jsonMap.put(OptResult.JSON_KEY_MSG, 
				bulletinCacheDao.queryBulletin());
	}

	@Override
	public void buildTheme(int start, int limit, 
			Map<String, Object> jsonMap) throws Exception {
		jsonMap.put(OptResult.JSON_KEY_MSG, 
				bulletinCacheDao.queryTheme(new RowSelection(start, limit)));
	}

	@Override
	public void buildUserTheme(int start, int limit, Map<String, Object> jsonMap) throws Exception {
		jsonMap.put(OptResult.JSON_KEY_MSG, 
				bulletinCacheDao.queryUserTheme(new RowSelection(start, limit)));
	}
	
	@Override
	public void buildChannelTheme(int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		jsonMap.put(OptResult.JSON_KEY_MSG, 
				bulletinCacheDao.queryChannelTheme(new RowSelection(start, limit)));
	}

	@Override
	public void buildItemSet(Integer start, Integer limit, Map<String, Object> jsonMap) {
		// 定义分类的返回列表
		List<Map<String, Serializable>> categoryList = new ArrayList<Map<String, Serializable>>();
		
		// 定义商品公告返回列表
		List<OpMsgBulletin> itemList = new ArrayList<OpMsgBulletin>();
		
		// 若start为1，证明为第一次查询，要返回全部的商品集合列表，若不为1，证明已经查询过了，只有好物推荐需要追加数据，则只返回好物推荐
		if ( start == 1) {
			// 添加限时秒杀商品集合列表到返回值列表
			itemList.addAll(getSeckillList());
			
			// 添加有奖活动列表到返回值列表
			itemList.addAll(getAwardActivityList());
		}
		
		// 添加推荐商品集合列表到返回值列表，推荐商品集合根据前台传递的分页与每页数量返回结果
		itemList.addAll(getRecommendItemList(start, limit));
		
		// 若商品公告返回值不为空，则拼装分类
		if ( !itemList.isEmpty()) {
			// 若start为1，证明为第一次查询，要返回全部的分类，若不为1，证明已经查询过了，只有好物推荐需要追加数据，则只返回好物推荐
			if ( start == 1) {
				Map<String, Serializable> seckill = new HashMap<String, Serializable>();
				seckill.put("id", 1);
				seckill.put("name", "限时秒杀");
				seckill.put("iconURL", "http://imzhitu.qiniudn.com/op/item/seckill.png");
				
				Map<String, Serializable> awardActivity = new HashMap<String, Serializable>();
				awardActivity.put("id", 2);
				awardActivity.put("name", "有奖活动");
				awardActivity.put("iconURL", "http://imzhitu.qiniudn.com/op/item/act.png");
				
				Map<String, Serializable> recommendItem = new HashMap<String, Serializable>();
				recommendItem.put("id", 3);
				recommendItem.put("name", "好物推荐");
				recommendItem.put("iconURL", "http://imzhitu.qiniudn.com/op/item/love.png");
				
				categoryList.add(seckill);
				categoryList.add(awardActivity);
				categoryList.add(recommendItem);
			} else {
				Map<String, Serializable> recommendItem = new HashMap<String, Serializable>();
				recommendItem.put("id", 3);
				recommendItem.put("name", "好物推荐");
				recommendItem.put("iconURL", "http://imzhitu.qiniudn.com/op/item/love.png");
				
				categoryList.add(recommendItem);
			}
		}
		
		// 拼装返回值 
		jsonMap.put(OptResult.JSON_KEY_CATEGORY, categoryList);
		jsonMap.put(OptResult.JSON_KEY_ITEMS, itemList);
	}
	
	/**
	 * 获取限时秒杀商品列表
	 * 
	 * @return List<SeckillBulletin>	限时秒杀商品列表
	 * @author zhangbo	2015年12月7日
	 */
	private List<SeckillBulletin> getSeckillList() {
		
		// 限时秒杀不分页，返回运营配置的所有数据，传递参数start为1，limit为0，即查询全部数据
		List<SeckillBulletin> list = itemBulletinCache.querySeckill(new RowSelection(1,0));
		// 因为redis不会返回null，只会返回空列表
		if ( list.size() != 0 ) {
			// 由于目前是写死的，限时秒杀的分类都是1
			for (SeckillBulletin bulletin : list) {
				bulletin.setCategory(1);
			}
		}
		return list;
	}
	
	/**
	 * 获取有奖活动列表
	 * 
	 * @return List<AwardActivityBulletin>	有奖活动列表
	 * @author zhangbo	2015年12月7日
	 */
	private List<AwardActivityBulletin> getAwardActivityList() {
		
		// 有奖活动不分页，返回运营配置的所有数据，传递参数start为1，limit为0，即查询全部数据
		List<AwardActivityBulletin> list = itemBulletinCache.queryAwardActivity(new RowSelection(1,0));
		
		// 因为redis不会返回null，只会返回空列表
		if ( list.size() != 0 ) {
			// 由于目前是写死的，有奖活动的分类都是2
			for (AwardActivityBulletin bulletin : list) {
				bulletin.setCategory(2);
			}
		}
		return list;
	}
	
	/**
	 * 获取推荐商品列表
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @author zhangbo	2015年12月7日
	 */
	private List<RecommendItemBulletin> getRecommendItemList(Integer start, Integer limit) {
		
		List<RecommendItemBulletin> list = itemBulletinCache.queryRecommendItem(new RowSelection(start,limit));
		
		// 因为redis不会返回null，只会返回空列表
		if ( list.size() != 0 ) {
			// 由于目前是写死的，推荐商品的分类都是3
			for (RecommendItemBulletin bulletin : list) {
				bulletin.setCategory(3);
			}
		}
		return list;
	}

}
