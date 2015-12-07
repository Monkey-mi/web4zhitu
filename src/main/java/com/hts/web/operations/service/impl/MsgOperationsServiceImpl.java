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
	public void buildItem(Integer start, Integer limit, Map<String, Object> jsonMap) {
		// 定义分类的返回列表
		List<Map<String, Serializable>> categoryList = new ArrayList<Map<String, Serializable>>();
		
		// 若start为1，证明为第一次查询，要返回全部的分类，若不为1，证明已经查询过了，只有好物推荐需要追加数据，则只返回好物推荐
		if ( start == 1) {
			Map<String, Serializable> seckill = new HashMap<String, Serializable>();
			seckill.put("id", 1);
			seckill.put("name", "限时秒杀");
			
			Map<String, Serializable> awardActivity = new HashMap<String, Serializable>();
			awardActivity.put("id", 2);
			awardActivity.put("name", "有奖活动");
			
			Map<String, Serializable> recommendItem = new HashMap<String, Serializable>();
			recommendItem.put("id", 3);
			recommendItem.put("name", "好物推荐");
			
			categoryList.add(seckill);
			categoryList.add(awardActivity);
			categoryList.add(recommendItem);
		} else {
			Map<String, Serializable> recommendItem = new HashMap<String, Serializable>();
			recommendItem.put("id", 3);
			recommendItem.put("name", "好物推荐");
			
			categoryList.add(recommendItem);
		}
		// 定义商品公告返回列表
		List<OpMsgBulletin> itemList = new ArrayList<OpMsgBulletin>();
		
		// 添加限时秒杀商品列表到返回值列表
		itemList.addAll(getSeckillList());
		
		// 添加有奖活动列表到返回值列表
		itemList.addAll(getAwardActivityList());
		
		// 添加推荐商品列表到返回值列表，推荐商品根据前台传递的分页与每页数量返回结果
		itemList.addAll(getRecommendItemList(start, limit));
		
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
		// 临时构造数据
		List<SeckillBulletin> list = new ArrayList<SeckillBulletin>();
		SeckillBulletin s = new SeckillBulletin();
		s.setBulletinName("这个是临时构造测试数据：限时秒杀");
		s.setBulletinPath("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		s.setBulletinThumb("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		s.setBulletinType(5);
		s.setDeadline(new Date().getTime());
		s.setId(10000);
		s.setLink("http://www.baidu.com");
		
		list.add(s);
		
//		List<SeckillBulletin> list = itemBulletinCache.querySeckill(new RowSelection(1,0));
		
		// 由于目前是写死的，限时秒杀的分类都是1
		for (SeckillBulletin bulletin : list) {
			bulletin.setCategory(1);
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
		
		// 临时构造数据
		List<AwardActivityBulletin> list = new ArrayList<AwardActivityBulletin>();
		AwardActivityBulletin a = new AwardActivityBulletin();
		a.setBulletinName("这个是临时构造测试数据：有奖活动1");
		a.setBulletinPath("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		a.setBulletinThumb("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		a.setBulletinType(6);
		a.setId(10001);
		a.setLink("清凉一夏");
		
		AwardActivityBulletin a1 = new AwardActivityBulletin();
		a1.setBulletinName("这个是临时构造测试数据：有奖活动2");
		a1.setBulletinPath("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		a1.setBulletinThumb("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		a1.setBulletinType(6);
		a1.setId(10002);
		a1.setLink("清凉一夏");
				
		list.add(a);
		list.add(a1);
		
		
		// 有奖活动不分页，返回运营配置的所有数据，传递参数start为1，limit为0，即查询全部数据
//		List<AwardActivityBulletin> list = itemBulletinCache.queryAwardActivity(new RowSelection(1,0));
		
		// 由于目前是写死的，有奖活动的分类都是2
		for (AwardActivityBulletin bulletin : list) {
			bulletin.setCategory(2);
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
		
		// 临时构造数据
		List<RecommendItemBulletin> list = new ArrayList<RecommendItemBulletin>();
		RecommendItemBulletin r = new RecommendItemBulletin();
		r.setBulletinName("这个是临时构造测试数据：推荐商品1");
		r.setBulletinPath("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r.setBulletinThumb("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r.setBulletinType(7);
		r.setId(10003);
		r.setLink("http://www.baidu.com");
		
		RecommendItemBulletin r1 = new RecommendItemBulletin();
		r1.setBulletinName("这个是临时构造测试数据：推荐商品2");
		r1.setBulletinPath("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r1.setBulletinThumb("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r1.setBulletinType(7);
		r1.setId(10004);
		r1.setLink("http://www.baidu.com");
		
		RecommendItemBulletin r2 = new RecommendItemBulletin();
		r2.setBulletinName("这个是临时构造测试数据：推荐商品3");
		r2.setBulletinPath("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r2.setBulletinThumb("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r2.setBulletinType(7);
		r2.setId(10005);
		r2.setLink("http://www.baidu.com");
		
		RecommendItemBulletin r3 = new RecommendItemBulletin();
		r3.setBulletinName("这个是临时构造测试数据：推荐商品4");
		r3.setBulletinPath("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r3.setBulletinThumb("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r3.setBulletinType(7);
		r3.setId(10006);
		r3.setLink("http://www.baidu.com");
		
		RecommendItemBulletin r4 = new RecommendItemBulletin();
		r4.setBulletinName("这个是临时构造测试数据：推荐商品5");
		r4.setBulletinPath("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r4.setBulletinThumb("http://static.imzhitu.com/op/notice/1435978781000.jpg");
		r4.setBulletinType(7);
		r4.setId(10007);
		r4.setLink("http://www.baidu.com");
		
		list.add(r);
		list.add(r1);
		list.add(r2);
		list.add(r3);
		list.add(r4);
		
//		List<RecommendItemBulletin> list = itemBulletinCache.queryRecommendItem(new RowSelection(start,limit));
		
		// 由于目前是写死的，推荐商品的分类都是3
		for (RecommendItemBulletin bulletin : list) {
			bulletin.setCategory(3);
		}
		return list;
	}
}
