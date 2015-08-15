package com.hts.web.operations.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorldCount;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldLabelWorldAuthor;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpActivityLikeRank;
import com.hts.web.common.pojo.OpActivityLogo;
import com.hts.web.common.pojo.OpActivityStar;
import com.hts.web.common.pojo.OpActivityWinnerDto;
import com.hts.web.common.pojo.OpSquareTopic;
import com.hts.web.common.pojo.OpUserVerifyDto;
import com.hts.web.common.pojo.OpWorldType;
import com.hts.web.common.pojo.OpWorldTypeDto;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.operations.dao.ActivityAwardDao;
import com.hts.web.operations.dao.ActivityCacheDao;
import com.hts.web.operations.dao.ActivityDao;
import com.hts.web.operations.dao.ActivityLikeRankDao;
import com.hts.web.operations.dao.ActivityLogoCacheDao;
import com.hts.web.operations.dao.ActivityStarCacheDao;
import com.hts.web.operations.dao.ActivityWinnerDao;
import com.hts.web.operations.dao.OpUserVerifyDtoCacheDao;
import com.hts.web.operations.dao.OpWorldTypeCacheDao;
import com.hts.web.operations.dao.OpWorldTypeDto2CacheDao;
import com.hts.web.operations.dao.SquarePushDao;
import com.hts.web.operations.dao.SquarePushTopicDao;
import com.hts.web.operations.dao.UserVerifyRecCacheDao;
import com.hts.web.operations.dao.XiaoMiShuWorldDao;
import com.hts.web.operations.service.ZTWorldOperationsService;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserConcernTypeDao;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldLabelDao;
import com.hts.web.ztworld.dao.HTWorldLabelWorldDao;
import com.hts.web.ztworld.dao.HTWorldLikedDao;
import com.hts.web.ztworld.dao.TypeCacheDao;
import com.hts.web.ztworld.service.ZTWorldService;

/**
 * <p>
 * 织图子模块运营业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-8-8
 * 
 * @author ztj
 * 
 */
@Service("HTSZTWorldOperationsService")
public class ZTWorldOperationsServiceImpl extends BaseServiceImpl implements
		ZTWorldOperationsService {

	@Autowired
	private SquarePushDao squarePushDao;
	
	@Autowired
	private SquarePushTopicDao squarePushTopicDao;
	
	@Autowired
	private UserConcernTypeDao userConcernTypeDao;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private ActivityLogoCacheDao activityLogoCacheDao;
	
	@Autowired
	private ActivityCacheDao activityCacheDao;
	
	@Autowired
	private ActivityWinnerDao activityWinnerDao;
	
	@Autowired
	private OpWorldTypeCacheDao opWorldTypeCacheDao;
	
	@Autowired
	private OpWorldTypeDto2CacheDao opWorldTypeDto2CacheDao;
	
	@Autowired
	private HTWorldLabelDao worldLabelDao;
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private XiaoMiShuWorldDao xiaoMiShuWorldDao;
	
	@Autowired
	private ActivityAwardDao activityAwardDao;
	
	@Autowired
	private ActivityLikeRankDao activityLikeRankDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Autowired
	private ActivityStarCacheDao activityStarCacheDao;
	
	@Autowired
	private HTWorldLikedDao worldLikedDao;
	
	@Autowired
	private HTWorldDao worldDao;

	@Autowired
	private TypeCacheDao worldTypeCacheDao;
	
	@Autowired
	private OpUserVerifyDtoCacheDao opUserVerifyDtoCacheDao;
	
	@Autowired
	private UserVerifyRecCacheDao userVerifyRecCacheDao;
	
	@Value("${push.customerServiceId}")
	private Integer customerServiceId = 13;
	
	@Value("${op.superbTotal}")
	private Integer superbTotal = 150;
	
	private Integer superbRandomLimit = 8;
	
	/**
	 * 索引分类数量限制
	 */
	public static final int SQUARE_LABEL_INDEX_LIMIT = 10;
	
	/**
	 * 索引精品数量限制
	 */
	public static final int SQUARE_LABEL_INDEX_SUPERB_LIMIT = 8;
	
	/**
	 * 广场活动个数
	 */
	private Integer activityLimit = 5;
	
	/**
	 * 分类标签个数
	 */
	private Integer typeLabelLimit = 5;
	
	/**
	 * 活动LOGO个数
	 */
	private Integer activityLogoLimit = 10;
	
	private Integer superbListMaxPage = 18;
	
	private Integer tutorialLimit = 4;
	
	public Integer getTypeLabelLimit() {
		return typeLabelLimit;
	}

	public void setTypeLabelLimit(Integer typeLabelLimit) {
		this.typeLabelLimit = typeLabelLimit;
	}

	public Integer getActivityLimit() {
		return activityLimit;
	}

	public void setActivityLimit(Integer activityLimit) {
		this.activityLimit = activityLimit;
	}
	
	public Integer getActivityLogoLimit() {
		return activityLogoLimit;
	}

	public void setActivityLogoLimit(Integer activityLogoLimit) {
		this.activityLogoLimit = activityLogoLimit;
	}
	
	public Integer getSuperbTotal() {
		return superbTotal;
	}

	public void setSuperbTotal(Integer superbTotal) {
		this.superbTotal = superbTotal;
	}

	public Integer getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(Integer customerServiceId) {
		this.customerServiceId = customerServiceId;
	}
	
	public Integer getTutorialLimit() {
		return tutorialLimit;
	}

	public void setTutorialLimit(Integer tutorialLimit) {
		this.tutorialLimit = tutorialLimit;
	}
	
	public Integer getSuperbListMaxPage() {
		return superbListMaxPage;
	}

	public void setSuperbListMaxPage(Integer superbListMaxPage) {
		this.superbListMaxPage = superbListMaxPage;
	}
	

	@Override
	public void buildSquarePush(boolean isRandom, int sinceId, int maxId, int start, int limit,
			Map<String, Object> jsonMap) {
		Long totalCount = 0l;
		List<OpWorldTypeDto> dtoList = null;
		if(isRandom) {
			dtoList = squarePushDao.queryRandomSquare(limit);
			totalCount = new Long(dtoList.size());
		} else {
			if(sinceId > 0) { // 查询比指定时间晚的记录
				dtoList = squarePushDao.querySquareByMinSerial(sinceId, new RowSelection(start, limit));
				totalCount = squarePushDao.querySquareCountByMinId(sinceId);
				if(dtoList.size() > 0) {
					sinceId = dtoList.get(0).getId();
				}
			} else if(maxId <= 0) { // 默认查询
				dtoList = squarePushDao.querySquare(new RowSelection(start, limit));
				if(dtoList.size() > 0) {
					maxId = (Integer)dtoList.get(0).getId();
					totalCount = squarePushDao.querySquareCountByMaxSerial(maxId);
				}
			} else if(maxId > 0) { // 查询比指定时间早的记录
				dtoList = squarePushDao.querySquareByMaxSerial(maxId, new RowSelection(start, limit));
				totalCount = squarePushDao.querySquareCountByMaxSerial(maxId);
			}
		}
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, dtoList);
		jsonMap.put(OptResult.JSON_KEY_TOTAL_COUNT, totalCount);
	}
	
	@Override
	public void buildSquarePush(boolean isRandom, int limit, Map<String, Object> jsonMap) {
		List<OpWorldTypeDto> dtoList = null;
		if(isRandom) {
			dtoList = squarePushDao.queryRandomSquare(limit);
		} else {
			dtoList = squarePushDao.querySquare(new RowSelection(1, limit));
		}
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, dtoList);
	}
	
	@Override
	public List<OpWorldTypeDto> getSquarePushs(int sinceId, int maxId, int start, int limit) {
		List<OpWorldTypeDto> dtoList = null;
		if(sinceId > 0) { // 查询比指定时间晚的记录
			dtoList = squarePushDao.querySquareByMinSerial(sinceId, new RowSelection(start, limit));
			if(dtoList.size() > 0) {
				sinceId = dtoList.get(0).getId();
			}
		} else if(maxId <= 0) { // 默认查询
			dtoList = squarePushDao.querySquare(new RowSelection(start, limit));
			if(dtoList.size() > 0) {
				maxId = (Integer)dtoList.get(0).getId();
			}
		} else if(maxId > 0) { // 查询比指定时间早的记录
			dtoList = squarePushDao.querySquareByMaxSerial(maxId, new RowSelection(start, limit));
		}
		return dtoList;
	}
	
	@Override
	public List<OpWorldTypeDto> getSquarePushs(int sinceId, int maxId, int start, int limit, String label[]) {
		List<OpWorldTypeDto> dtoList = null;
		if(sinceId > 0) { // 查询比指定时间晚的记录
			dtoList = squarePushDao.querySquareByMinId(sinceId, new RowSelection(start, limit), label);
			if(dtoList.size() > 0) {
				sinceId = dtoList.get(0).getId();
			}
		} else if(maxId <= 0) { // 默认查询
			dtoList = squarePushDao.querySquare(new RowSelection(start, limit), label);
			if(dtoList.size() > 0) {
				maxId = (Integer)dtoList.get(0).getId();
			}
		} else if(maxId > 0) { // 查询比指定时间早的记录
			dtoList = squarePushDao.querySquareByMaxId(maxId, new RowSelection(start, limit), label);
		}
		return dtoList;
	}

	@Override
	public int getMaxSquarePushId() throws Exception {
		return squarePushDao.queryMaxSquareSerial();
	}

	@Override
	public void buildLatestSquarePushTopic(int limit, Map<String, Object> jsonMap) {
		List<OpSquareTopic> list = squarePushTopicDao.queryTopic(new RowSelection(1, limit));
		jsonMap.put(OptResult.JSON_KEY_TOPICS, list);
	}

	@Override
	public void buildSquarePushLabelIndex(Integer superbLimit, Integer typeLimit, Map<String, Object> jsonMap) throws Exception {
//		List<OpWorldType> labelList = opWorldTypeCacheDao.queryCacheLabel(typeLabelLimit);
//		List<OpWorldTypeDto2> list = squarePushDao.querySquarePushIndex(typeLimit, superbLimit, labelList);
//		jsonMap.put(OptResult.JSON_KEY_LABEL_COUNT, SQUARE_LABEL_INDEX_LIMIT);
//		jsonMap.put(OptResult.JSON_KEY_SUPERB_COUNT, SQUARE_LABEL_INDEX_SUPERB_LIMIT);
//		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, labelList);
//		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
	}

	@Override
	public void buildSquarePush(int maxId, int start,
			int limit, int typeId, Integer joinId, Map<String, Object> jsonMap) throws Exception {
		Long totalCount = 0l;
		List<OpWorldTypeDto> dtoList = null;
		if(maxId <= 0) { // 默认查询
			dtoList = squarePushDao.queryNormalSquare(typeId, joinId, new RowSelection(start, limit));
			if(dtoList.size() > 0) {
				maxId = squarePushDao.queryMaxSquareSerial(); // 兼容2.7以前版本
				dtoList.get(0).setRecommendId(maxId); 
				totalCount = squarePushDao.queryNormalSquareCountByMaxSerial(maxId, typeId);
			}
		} else if(maxId > 0) { // 查询比指定时间早的记录
			dtoList = squarePushDao.queryNormalSquareByMaxSerial(maxId, typeId, joinId, new RowSelection(start, limit));
			totalCount = squarePushDao.queryNormalSquareCountByMaxSerial(maxId, typeId);
		}
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, dtoList);
		jsonMap.put(OptResult.JSON_KEY_TOTAL_COUNT, totalCount);
	}
	
	@Override
	public void buildTypeSquare(int maxId, int start,
			int limit, final int commentLimit, final int likedLimit, 
			final int completeLimit, final int typeId, final Integer joinId, 
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap, new SerializableListAdapter<OpWorldTypeDto>() {

			@Override
			public List<OpWorldTypeDto> getSerializables(RowSelection rowSelection) {
				List<OpWorldTypeDto> list = squarePushDao.queryNormalSquare(typeId, joinId, rowSelection);
				worldService.extractExtraInfo(false, commentLimit, likedLimit, 
						Math.min(list.size(), completeLimit), list);
				return list;
			}

			@Override
			public List<OpWorldTypeDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpWorldTypeDto> list = squarePushDao.queryNormalSquareByMaxSerial(maxId, typeId, joinId, rowSelection);
				worldService.extractExtraInfo(false, commentLimit, likedLimit, 
						Math.min(list.size(), completeLimit), list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
			
		}, OptResult.JSON_KEY_HTWORLD, null);
	}
	
	
	@Override
	public void buildSuperbTypeSquare(int maxId, int start, int limit,
			final int commentLimit, final int likedLimit, final int completeLimit,
			final boolean trimTutorial, final boolean trimConcernId, final boolean trimVer0,
			final Integer joinId, Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap, new SerializableListAdapter<OpWorldTypeDto>() {

			@Override
			public List<OpWorldTypeDto> getSerializables(RowSelection rowSelection) {
				List<OpWorldTypeDto> list = null;
				if(trimVer0) {
					list = squarePushDao.querySuperbTypeSquare(joinId, rowSelection);
				} else {
					list = squarePushDao.querySuperbV4(rowSelection);
				}
				
				if(!trimTutorial) { // 加载教程
					List<OpWorldTypeDto> tutorialList = xiaoMiShuWorldDao.queryWorldTypeDto(customerServiceId, joinId, 
							new RowSelection(1, tutorialLimit));
					if(tutorialList.size() > 0 && list.size() > 0) {
						Integer maxRecId = list.get(0).getRecommendId();
						for(int i = tutorialList.size() - 1; i >= 0; i--) {
							OpWorldTypeDto dto = tutorialList.get(i);
							dto.setRecommendId(maxRecId);
							list.add(0, dto);
						}
					}
				}
				worldService.extractExtraInfo(false, commentLimit, likedLimit, 
						Math.min(list.size(), completeLimit), list);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(joinId, list);
				if(!trimConcernId) {
					extractConcerned(joinId, list);
				}
				return list;
			}

			@Override
			public List<OpWorldTypeDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<OpWorldTypeDto> list = null;
				if(trimVer0) {
					list = squarePushDao.querySuperbTypeSquare(maxId, joinId, rowSelection);
				} else {
					list = squarePushDao.querySuperbV4(maxId, rowSelection);
				}
				worldService.extractExtraInfo(false, commentLimit, likedLimit, 
						Math.min(list.size(), completeLimit), list);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(joinId, list);
				if(!trimConcernId) {
					extractConcerned(joinId, list);
				}
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
			
		}, OptResult.JSON_KEY_HTWORLD, null);
	}
	

	@Override
	public void buildRandomLabelPush(int limit, Integer joinId, Map<String, Object> jsonMap) {
//		List<OpWorldTypeDto> dtoList = null;
//		OpWorldType squareLabel = (OpWorldType)opWorldTypeCacheDao.queryCacheLabelByIndex(
//				NumberUtil.getRandomIndex(typeLabelLimit));
//		Long totalCount = squarePushDao.querySquareCount(squareLabel.getId());
//		if(totalCount > 0) {
//			int p = NumberUtil.getRandomPage(limit, totalCount.intValue());
//			dtoList = squarePushDao.querySquare(squareLabel.getId(), joinId, new RowSelection(p, limit));
//		} else {
//			dtoList = new ArrayList<OpWorldTypeDto>();
//		}
//		jsonMap.put(OptResult.JSON_KEY_HTWORLD, dtoList);
//		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, squareLabel);
//		
//	}
//
//	@Override
//	public void buildSquarePushIndex(Integer userId, boolean trimLabel, boolean trimNormal, 
//			boolean trimActivity, int maxSuperbId, Integer superbStart, Integer superbLimit,
//			boolean trimConcernId, Integer typeLimit, Boolean isRrandom, Map<String, Object> jsonMap)
//			throws Exception {
//		List<OpWorldType> labelList = null;
//		List<OpWorldTypeDto> list = null;
//		if(!trimNormal) {
//			labelList = opWorldTypeCacheDao.queryCacheLabel(typeLabelLimit);
//			jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, labelList);
//			list = squarePushDao.querySquarePushIndex(typeLimit, superbLimit, labelList);
//		} else {
//			if(maxSuperbId == 0) {
//				// 第一次查询缓存
//				list = opWorldTypeDto2CacheDao.querySuperbWorldType(new RowSelection(superbStart, superbLimit));
//			} else {
//				OpWorldTypeDto2 firstDto  = opWorldTypeDto2CacheDao.querySuperWorldType(0);
//				if(firstDto != null && firstDto.getRecommendId() > maxSuperbId) { // 有新数据更新
//					superbStart = 1;
//					list = opWorldTypeDto2CacheDao.querySuperbWorldType(new RowSelection(superbStart, superbLimit));
//				} else { // 加载下一页
//					RowSelection rowSelection = new RowSelection(superbStart, superbLimit);
//					if(rowSelection.getMaxRow() > superbTotal) { // 是否超出最大行数，返回第一页
//						superbStart = 1;
//						list = opWorldTypeDto2CacheDao.querySuperbWorldType(new RowSelection(superbStart, superbLimit));
//					} else {
//						list = squarePushDao.querySuperbSquareIndex(maxSuperbId, new RowSelection(superbStart, superbLimit));
//					}
//				}
//			}
//			
//			if(isRrandom) {
//				List<OpWorldTypeDto2> randomList = new ArrayList<OpWorldTypeDto2>();
//				if(superbStart.equals(1)) { // 第一页第一个不随机
//					randomList.add(list.get(0));
//					list.remove(0);
//				}
//				randomList(list, randomList, false);
//				list = randomList;
//			}
//			jsonMap.put(OptResult.JSON_KEY_NEXT_START, superbStart + 1);
//			
//			// 加载关注id列表
//			int size = list.size();
//			if(size > 0 && !trimConcernId) {
//				Set<Integer> cidSet = new HashSet<Integer>();
//				Integer[] concernIds = new Integer[size];
//				for(int i = 0; i < size; i++) {
//					OpWorldTypeDto2 dto = list.get(i);
//					Integer uid = dto.getAuthorId();
//					if(!cidSet.contains(uid)) {
//						concernIds[i] = dto.getAuthorId();
//						cidSet.add(dto.getAuthorId());
//					}
//				}
//				if(userId != -1) {
//					Set<Integer> cidSet2 = userConcernDao.queryConcernIds(userId, concernIds);
//					for(OpWorldTypeDto2 dto : list) {
//						if(cidSet2.contains(dto.getAuthorId())) {
//							dto.setConcerned(Tag.TRUE);
//						}
//					}
//				}
//			}
//		}
//		if(!trimActivity) {
//			List<OpActivity> activityList = activityCacheDao.queryActivity();
//			jsonMap.put(OptResult.JSON_KEY_ACTIVITY, activityList);
//			
//		}
//		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
	}
	
	
	@Override
	public void buildSuperbTypeSquareList(int maxId, int start, int limit, int pageCount,
			boolean isRefresh, int commentLimit, int likedLimit,
			int completeLimit, boolean trimConcernId,
			Integer joinId, Map<String, Object> jsonMap) throws Exception {
		
		List<OpWorldTypeDto> list = null;
		RowSelection rowSelection = null;
		
		if(maxId == 0) {
			rowSelection = new RowSelection(start, limit);
			list = squarePushDao.querySuperbV4(rowSelection); // 加载最新1页
			
		// 下拉刷新
		} else if(isRefresh) {
			pageCount = 0;
			OpWorldTypeDto firstDto  = opWorldTypeDto2CacheDao.querySuperWorldType(0);
			if(firstDto != null && firstDto.getRecommendId() > maxId) { // 有新数据更新
				start = 1;
				rowSelection = new RowSelection(start, limit);
				list = squarePushDao.querySuperbV4(rowSelection); // 加载最新1页
			} else if(start > superbListMaxPage){ // 超出最大限制页数，返回加载第1页
				start = 1;
				rowSelection = new RowSelection(start, limit);
				list = squarePushDao.querySuperbV4(maxId, rowSelection); // 以maxId为基础加载第1页
			} else {
				rowSelection = new RowSelection(start, limit);
				list = squarePushDao.querySuperbV4(maxId, rowSelection); // 以maxId为基础加载下1页
			}
			
		// 上拉加载更多，并未超出最大限制页数
		} else if(pageCount < superbListMaxPage){ 
			if(start > superbListMaxPage){ // 超出最大限制页数
				start = 1;
				rowSelection = new RowSelection(start, limit);
				list = squarePushDao.querySuperbV4(maxId, rowSelection); // 以maxId为基础加载第1页
			} else {
				rowSelection = new RowSelection(start, limit);
				list = squarePushDao.querySuperbV4(maxId, rowSelection); // 以maxId为基础加载下1页
			}
			
		// 上拉加载更多，已经超出最大限制页数，返回空列表
		} else {
			--start;
			--pageCount;
			list = new ArrayList<OpWorldTypeDto>();
		}
		
		int size = list.size();
		if(size > 0) {
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, Math.max(maxId, list.get(0).getRecommendId()));
			List<OpWorldTypeDto> randomList = new ArrayList<OpWorldTypeDto>();
			randomList2(list, randomList);
			list = randomList;
			worldService.extractExtraInfo(false, commentLimit, likedLimit, size, list);
			userInfoService.extractVerify(list);
			userInteractService.extractRemark(joinId, list);
			
			if(!trimConcernId) {
				Set<Integer> cidSet = new HashSet<Integer>();
				Integer[] concernIds = new Integer[size];
				for(int i = 0; i < size; i++) {
					OpWorldTypeDto dto = list.get(i);
					Integer uid = dto.getAuthorId();
					if(!cidSet.contains(uid)) {
						concernIds[i] = dto.getAuthorId();
						cidSet.add(dto.getAuthorId());
					}
				}
				if(joinId != -1) {
					Set<Integer> cidSet2 = userConcernDao.queryConcernIds(joinId, concernIds);
					for(OpWorldTypeDto dto : list) {
						if(cidSet2.contains(dto.getAuthorId())) {
							dto.setConcerned(Tag.TRUE);
						}
					}
				}
			}
		} else {
			jsonMap.put(OptResult.JSON_KEY_MAX_ID, maxId);
		}
		
		jsonMap.put(OptResult.JSON_KEY_NEXT_START, ++start);
		jsonMap.put(OptResult.JSON_KEY_PAGE_COUNT, ++pageCount);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
	}
	
	@Override
	public void buildSuperbTypeSquareListV2(final Integer typeId, int maxId, int start, final int limit,
			final int commentLimit, final int likedLimit, final int completeLimit, 
			final boolean trimConcernId, final Integer joinId, final Map<String, Object> jsonMap) throws Exception {
		buildSerializables("getRecommendId", maxId, start, limit, jsonMap, 
				new SerializableListAdapter<OpWorldTypeDto>() {

					@Override
					public List<OpWorldTypeDto> getSerializables(
							RowSelection rowSelection) {
						List<OpWorldTypeDto> list = null;
						
						
						OpUserVerifyDto verify = opUserVerifyDtoCacheDao.queryRandomVerify();
						
						if(typeId == 0) { // 加载全部精选
							list = squarePushDao.querySuperbV4(rowSelection);
							
							if(completeLimit == 0) {
								list = opWorldTypeDto2CacheDao.querySuperbWorldType(0, limit-1);
							}
							
							// 加载所有下拉菜单
							jsonMap.put(OptResult.JSON_KEY_RECOMMEND_TYPE, worldTypeCacheDao.queryType());
							// 随机加载一种达人
							jsonMap.put(OptResult.JSON_KEY_VERIFY, verify);
							
						} else { // 加载指定分类精选
							list = squarePushDao.querySuperbByTypeIdV4(typeId, rowSelection);
						}
						
						// 每次刷新都加载明星
						jsonMap.put(OptResult.JSON_KEY_STARS, 
								userVerifyRecCacheDao.queryUserByVerifyId(verify.getId(), 10));
						
						userInfoService.extractVerify(list);
						
						// 瀑布流状态下才加载赞列表
						if(completeLimit != 0) {
							// 加载点赞列表和关注状态等
							worldService.extractExtraInfo(true, false, joinId, false, commentLimit, likedLimit, list.size(), list);
							if(!trimConcernId) {
								extractConcerned(joinId, list);
							}
						}
						return list;
					}

					@Override
					public List<OpWorldTypeDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<OpWorldTypeDto> list = null;
						
						if(typeId == 0) {
							list = squarePushDao.querySuperbV4(maxId, rowSelection);
						} else {
							list = squarePushDao.querySuperbByTypeIdV4(maxId, typeId, rowSelection);
						}
						
						// 瀑布流状态下才加载赞列表
						if(commentLimit != 0) {
							worldService.extractExtraInfo(false, false, joinId, false, commentLimit, likedLimit, list.size(), list);
							userInfoService.extractVerify(list);
							if(!trimConcernId) {
								extractConcerned(joinId, list);
							}
						}
						return list;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0;
					}
					
		},OptResult.JSON_KEY_HTWORLD, null);
	}

	@Override
	public void buildSuperbTypeSquareListV3(Integer joinId, int maxId, 
			int cursor, int start, int limit, Map<String, Object> jsonMap) throws Exception {
		List<OpWorldTypeDto> list = null;
		// 查询首页（循环加载缓存中的分页）
		if(maxId == 0) { 
			List<OpWorldTypeDto> tempList = opWorldTypeDto2CacheDao.querySuperbWorldType(0, superbRandomLimit-1);
			list = opWorldTypeDto2CacheDao.querySuperbWorldType(superbRandomLimit, limit-1);
			randomList(tempList, list, true); // 打乱队列顺序
		
		// 从库中查询下一页
		} else {
			list = squarePushDao.querySuperbV4(maxId, new RowSelection(start, limit));
		}
		userInfoService.extractVerify(list);
		extractSuperbLikedAndCount(joinId, list);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
		jsonMap.put(OptResult.JSON_KEY_NEXT_CURSOR, 0);
	}
	
	/**
	 * 查询精选是否被赞和赞总数
	 * 
	 * @param joinId
	 * @param list
	 */
	private void extractSuperbLikedAndCount(Integer joinId, final List<OpWorldTypeDto> list) {
		if(list.size() > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] worldIds = new Integer[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Integer worldId = list.get(i).getWorldId();
				worldIds[i] = worldId;
				indexMap.put(worldId, i);
			}
			
			if(indexMap.size() > 0) {
				worldLikedDao.queryLiked(joinId, worldIds, new RowCallback<Integer>() {
		
					@Override
					public void callback(Integer t) {
						Integer index = indexMap.get(t);
						if(index != null) {
							list.get(index).setLiked(Tag.TRUE);
						}
					}
				});
				worldDao.queryCount(worldIds, new RowCallback<HTWorldCount>() {

					@Override
					public void callback(HTWorldCount t) {
						Integer index = indexMap.get(t.getId());
						if(index != null) {
							OpWorldTypeDto dto = list.get(index);
							dto.setLikeCount(t.getLikeCount());
							dto.setClickCount(t.getClickCount());
							dto.setCommentCount(t.getCommentCount());
						}
					}
				});
			}
		}
	}
	
	/**
	 * 打散序列
	 * 
	 * @param sourceList
	 * @param destList
	 * @param headInsert
	 */
	private void randomList(List<OpWorldTypeDto> sourceList, 
			List<OpWorldTypeDto> destList, boolean headInsert) {
		if(sourceList.size() > 0) {
			int index = NumberUtil.getRandomIndex(sourceList.size());
			OpWorldTypeDto i = sourceList.get(index);
			if(headInsert)
				destList.add(0, i);
			else 
				destList.add(i);
			sourceList.remove(index);
			randomList(sourceList, destList, headInsert);
		}
	}
	
	/**
	 * 打散队列2
	 * @param list
	 * @param randList
	 */
	private void randomList2(List<OpWorldTypeDto> list, List<OpWorldTypeDto> randList) {
		if(list.size() > 0) {
			int index = NumberUtil.getRandomIndex(list.size());
			OpWorldTypeDto i = list.get(index);
			randList.add(i);
			list.remove(index);
			randomList2(list, randList);
		}
	}
	
	private void extractConcerned(Integer joinId, List<OpWorldTypeDto> list) {
		// 加载关注id列表
		int size = list.size();
		if(size > 0) {
			Set<Integer> cidSet = new HashSet<Integer>();
			Integer[] concernIds = new Integer[size];
			for(int i = 0; i < size; i++) {
				OpWorldTypeDto dto = list.get(i);
				Integer uid = dto.getAuthorId();
				if(!cidSet.contains(uid)) {
					concernIds[i] = dto.getAuthorId();
					cidSet.add(dto.getAuthorId());
				}
			}
			if(joinId != -1) {
				Set<Integer> cidSet2 = userConcernDao.queryConcernIds(joinId, concernIds);
				for(OpWorldTypeDto dto : list) {
					if(cidSet2.contains(dto.getAuthorId())) {
						dto.setConcerned(Tag.TRUE);
					}
				}
			}
		}
	}
	
	@Override
	public void buildActivity(Integer activityLimit, Integer joinId, Boolean refreshCount, 
			Integer authorLimit, Map<String, Object> jsonMap)throws Exception {
		final List<OpActivity> activityList = activityCacheDao.queryActivity(activityLimit);
		int size = activityList.size();
		if(refreshCount && size > 0) {
			Integer[] ids = new Integer[size];
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			for(int i = 0; i < size; i++) {
				Integer id = activityList.get(i).getId();
				ids[i] = id;
				indexMap.put(id, i);
			}
			
			worldLabelDao.queryLabel(ids, new RowCallback<HTWorldLabel>() {

				@Override
				public void callback(HTWorldLabel label) {
					Integer index = indexMap.get(label.getId());
					activityList.get(index).setWorldCount(label.getWorldCount());
				}
			});
			
			activityWinnerDao.queryMaxWinnerId(ids, new RowCallback<OpActivityWinnerDto>() {

				@Override
				public void callback(OpActivityWinnerDto t) {
					Integer index = indexMap.get(t.getActivityId());
					activityList.get(index).setWinnerId(t.getId());
				}
			});
			
			if(authorLimit > 0) {
				final Map<Integer, UserVerify> verifyMap = userInfoService.getVerify();
				worldLabelWorldDao.queryParticipatorByLimit(joinId, ids, authorLimit, new RowCallback<HTWorldLabelWorldAuthor>() {

					@Override
					public void callback(HTWorldLabelWorldAuthor t) {
						Integer verifyId = t.getVerifyId();
						if(verifyMap.containsKey(verifyId)) {
							UserVerify uv = verifyMap.get(verifyId);
							t.setVerifyName(uv.getVerifyName());
							t.setVerifyIcon(uv.getVerifyIcon());
						}
						Integer index = indexMap.get(t.getLabelId());
						activityList.get(index).getAuthors().add(t);
					}
				});
			}
		}
		jsonMap.put(OptResult.JSON_KEY_ACTIVITY, activityList);
	}
	
	@Override
	public OpActivity getMaxActivity(Integer maxId) throws Exception {
		List<OpActivity> activityList = activityCacheDao.queryActivity(1);
		if(activityList.size() > 0) {
			OpActivity activity = activityList.get(0);
			// 动态页查询最新活动，如果当前id=maxId，将标题设置为空，动态页就不会显示标题
			if(activity != null && maxId != null && activity.getId().equals(maxId)) {
				activity.setActivityTitle(null);
			}
			return activity;
		}
		return null;
	}
	
	@Override
	public void buildCommercialActivityLogo(Map<String, Object> jsonMap) {
		List<OpActivityLogo> list = activityLogoCacheDao.queryCacheLogo(activityLogoLimit);
		jsonMap.put(OptResult.JSON_KEY_LOGO, list);
	}

	@Override
	public void buildTypeIndex(Integer userId, int limit, Map<String, Object> jsonMap)
			throws Exception {
		final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		final List<OpWorldType> labelList = opWorldTypeCacheDao.queryCacheLabel(typeLabelLimit);
		final List<OpWorldTypeDto> squareList = squarePushDao.querySuperbV4(new RowSelection(1, limit));
		for(int i = 0; i < labelList.size(); i++) {
			indexMap.put(labelList.get(i).getId(), i);
		}
		userConcernTypeDao.queryConcernType(userId, new RowCallback<Integer>() {

			@Override
			public void callback(Integer t) {
				Integer index = indexMap.get(t);
				labelList.get(index).setConcerned(Tag.TRUE);
			}
		});
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, squareList);
		jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, labelList);
	}

	@Override
	public void buildActivityWinner(final boolean isOrderBySerial, final Integer activityId, final Integer joinId,
			int maxId, int start, int limit, Map<String, Object> jsonMap,
			final boolean trimTotal, final boolean trimExtras, final int commentLimit,
			final int likedLimit) throws Exception{
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		String getIdMethod = "getId";
		if(isOrderBySerial) {
			getIdMethod = "getInteractId";
		}
		buildSerializables(getIdMethod, maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = null;
						if(!isOrderBySerial) {
							worldList = activityWinnerDao.queryActivityWorld(joinId, activityId, rowSelection);
						} else {
							worldList = activityWinnerDao.queryActivityWorldV2(joinId, activityId, rowSelection);
						}
						worldService.extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(),
								worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(joinId, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = null;
						if(!isOrderBySerial) {
							worldList = activityWinnerDao.queryActivityWorld(maxId, joinId, activityId, rowSelection);
						} else {
							worldList = activityWinnerDao.queryActivityWorldV2(maxId, joinId, activityId, rowSelection);
						}
						worldService.extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(),
								worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(joinId, worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						long count = 0L;
						if(!isOrderBySerial) {
							count = activityWinnerDao.queryActivityWorldCount(maxId, activityId);
						} else {
							count = activityWinnerDao.queryActivityWorldCountV2(maxId, activityId);
						}
						return count;
					}

				}, OptResult.JSON_KEY_HTWORLD, totalKey);
	}
	
	@Override
	public void buildActivityLikeRank(Integer joinId, Integer activityId, 
			Map<String, Object> jsonMap) throws Exception {
		Integer limit = activityAwardDao.querySumRemain(activityId);
		final List<HTWorldLabelWorldAuthor> list = activityDao.queryAuthorLikeRank(activityId, limit);
		int size = list.size();
		if(size > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] userIds = new Integer[size];
			for (int i = 0; i < size; i++) {
				Integer userId = list.get(i).getUserId();
				userIds[i] = userId;
				indexMap.put(userId, i);
			}
			
			activityLikeRankDao.queryLikeRank(activityId, userIds, new RowCallback<OpActivityLikeRank>() {

				@Override
				public void callback(OpActivityLikeRank t) {
					Integer uid = t.getUserId();
					int index = indexMap.get(uid);
					list.get(index).setLastPos(t.getLastPos());
				}
			});
			userInfoService.extractVerify(list);
			userInteractService.extractRemark(joinId, list);
		}
			
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, list);
	}

	@Override
	public void buildTutorial(int commentLimit, int likedLimit,
			int completeLimit, boolean trimConcernId, Integer joinId,
			Map<String, Object> jsonMap) throws Exception {
		List<OpWorldTypeDto> tutorialList = xiaoMiShuWorldDao.queryWorldTypeDto(customerServiceId, joinId, 
				new RowSelection(1, tutorialLimit));
		worldService.extractExtraInfo(false, commentLimit, likedLimit, 
				Math.min(tutorialList.size(), completeLimit), tutorialList);
		userInfoService.extractVerify(tutorialList);
		userInteractService.extractRemark(joinId, tutorialList);
		if(!trimConcernId) {
			extractConcerned(joinId, tutorialList);
		}
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, tutorialList);
	}

	@Override
	public void buildActivityStar(Integer joinId, Integer activityId, Map<String, Object> jsonMap) {
		List<OpActivityStar> starList = activityStarCacheDao.queryStar(activityId);
		userInfoService.extractVerify(starList);
		userInteractService.extractRemark(joinId, starList);
		jsonMap.put(OptResult.JSON_KEY_STARS, starList);
	}


}
