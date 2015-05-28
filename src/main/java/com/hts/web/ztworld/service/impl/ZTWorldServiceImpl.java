package com.hts.web.ztworld.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSException;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldChildWorld;
import com.hts.web.common.pojo.HTWorldChildWorldAndThumbDto;
import com.hts.web.common.pojo.HTWorldChildWorldDto;
import com.hts.web.common.pojo.HTWorldChildWorldThumb;
import com.hts.web.common.pojo.HTWorldChildWorldType;
import com.hts.web.common.pojo.HTWorldChildWorldTypeDto2;
import com.hts.web.common.pojo.HTWorldCommentUser;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.HTWorldFilterLogo;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.pojo.HTWorldLatest;
import com.hts.web.common.pojo.HTWorldLatestId;
import com.hts.web.common.pojo.HTWorldLatestIndex;
import com.hts.web.common.pojo.HTWorldLikedUser;
import com.hts.web.common.pojo.HTWorldTextStyle;
import com.hts.web.common.pojo.HTWorldWithExtra;
import com.hts.web.common.pojo.UserDynamicRec;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.ActivityDao;
import com.hts.web.operations.dao.ChannelWorldDao;
import com.hts.web.operations.service.ChannelService;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserRecDao;
import com.hts.web.userinfo.service.UserActivityService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.ztworld.dao.HTWorldCacheDao;
import com.hts.web.ztworld.dao.HTWorldChildWorldDao;
import com.hts.web.ztworld.dao.HTWorldChildWorldTypeCacheDao;
import com.hts.web.ztworld.dao.HTWorldChildWorldTypeDao;
import com.hts.web.ztworld.dao.HTWorldCommentDao;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldFilterLogoCacheDao;
import com.hts.web.ztworld.dao.HTWorldKeepDao;
import com.hts.web.ztworld.dao.HTWorldLabelDao;
import com.hts.web.ztworld.dao.HTWorldLabelWorldDao;
import com.hts.web.ztworld.dao.HTWorldLikedDao;
import com.hts.web.ztworld.dao.HTWorldTypeDao;
import com.hts.web.ztworld.service.ZTWorldService;

/**
 * <p>
 * 织图世界业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-8-3
 * 
 * @author ztj
 * 
 */
@Service("HTSZTWorldService")
public class ZTWorldServiceImpl extends BaseServiceImpl implements
		ZTWorldService {

	/**
	 * 织图管理密码
	 */
	public static final String ADMIN_PASS = "zhangjiaxin";
	/**
	 * 织图世界访问前缀_NO
	 */
	public static final String WORLD_TAG_PREFIX_NO = "NO";

	/**
	 * 织图世界访问前缀_DT
	 */
	public static final String WORLD_TAG_PREFIX_DT = "DT";

	/**
	 * 手机页面标志
	 */
	public static final String PHONE_PAGE_TAG = "phone";

	/**
	 * PC页面标志
	 */
	public static final String WEB_PAGE_TAG = "web";
	
	/**
	 * 最新织图发布时自动添加的播放数，最小值
	 */
	public static final int RANDOM_CLICK_COUNT_MIN = 10;
	
	/**
	 * 最新织图发布时自动添加的播放数，最大值
	 */
	public static final int RANDOM_CLICK_COUNT_MAX = 25;
	
	/**
	 * 智能手机User-Agent标识
	 */
	private static final String[] PHONE_USER_AGENTS = new String[] { "Android",
			"iPhone", "iPad" };
	
	/**
	 * 最新时间间隔
	 */
	private static final int[][] LATEST_INTERVAL = new int[][]{
		{180000, 300000, 43200000},
		{180000, 300000, 600000, 900000, 1200000, 1500000,
		1800000, 2100000, 2400000, 2700000, 3000000, 3300000,
		3600000, 7200000, 14400000, 21600000, 28800000, 43200000, 
		86400000, 172800000, 259200000}
	};
	
	/**
	 * 推荐用户信息，系统推荐
	 */
	private static final String USER_REC_MSG_SYS_REC = "推荐用户";
	
	/**
	 * 最新索引size
	 */
	private static final int LATEST_INDEX_SIZE = 4;
	
	/**
	 * 系统推荐用户限制
	 */
	private Integer sysRecLimit = 6;
	
	/**
	 * 系统推荐起始位置
	 */
	private Integer sysRecStart = 200;
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private HTWorldChildWorldDao worldChildWorldDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private HTWorldCommentDao worldCommentDao;
	
	@Autowired
	private HTWorldLikedDao worldLikedDao;
	
	@Autowired
	private HTWorldTypeDao worldTypeDao;
	
	@Autowired
	private HTWorldLabelDao worldLabelDao;
	
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	
	@Autowired
	private HTWorldCacheDao worldCacheDao;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private HTWorldChildWorldTypeDao worldChildWorldTypeDao;
	
	@Autowired
	private HTWorldChildWorldTypeCacheDao worldChildWorldTypeCacheDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private HTWorldFilterLogoCacheDao worldFilterLogoCacheDao;
	
	@Autowired
	private HTWorldKeepDao worldKeepDao;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Autowired
	private UserRecDao userRecDao;
	
	@Autowired
	private ChannelWorldDao channelWorldDao;

	@Autowired
	private ChannelService channelService;
	
	private String baseThumbPathAixin = "http://imzhitu.qiniudn.com/world/thumbs/1403056393000.png";
	private String baseThumbPathXing = "http://imzhitu.qiniudn.com/world/thumbs/1403057093000.png";
	private String baseThumbPathHuabian = "http://imzhitu.qiniudn.com/world/thumbs/1403056953000.png";
	private String baseThumbPathHua = "http://imzhitu.qiniudn.com/world/thumbs/1403057046000.png";
	private Integer latestDateInterval = 7; // 最新织图查询时间间隔
	

	public String getBaseThumbPath2() {
		return baseThumbPathAixin;
	}

	public void setBaseThumbPath2(String baseThumbPath2) {
		this.baseThumbPathAixin = baseThumbPath2;
	}

	public String getBaseThumbPath3() {
		return baseThumbPathXing;
	}

	public void setBaseThumbPath3(String baseThumbPath3) {
		this.baseThumbPathXing = baseThumbPath3;
	}

	public String getBaseThumbPath4() {
		return baseThumbPathHuabian;
	}

	public void setBaseThumbPath4(String baseThumbPath4) {
		this.baseThumbPathHuabian = baseThumbPath4;
	}

	public String getBaseThumbPath5() {
		return baseThumbPathHua;
	}

	public void setBaseThumbPath5(String baseThumbPath5) {
		this.baseThumbPathHua = baseThumbPath5;
	}
	
	public Integer getLatestDateInterval() {
		return latestDateInterval;
	}

	public void setLatestDateInterval(Integer latestDateInterval) {
		this.latestDateInterval = latestDateInterval;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HTWorld saveWorld(String childsJSON, Integer titleId,
			Integer phoneCode, Integer id, Integer authorId, String worldName,
			String worldDesc, String worldLabel, String labelIds, String worldType, 
			Integer typeId, String coverPath, String titlePath, String bgPath,
			String titleThumbPath, String thumbs, Double longitude, Double latitude, String locationDesc,
			String locationAddr, String province, String city, Integer size,
			String activityIds, Integer ver, String channelIds, Integer tp, 
			String color, Integer mask) throws Exception {
		
		Date date = new Date();
		Integer worldId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_HTWORLD_ID);
		Map<String, Object> tags = userInfoDao.queryTagById(authorId);
		Integer shield = ((Integer)tags.get("shield")).equals(Tag.TRUE) ? Tag.TRUE : Tag.FALSE;
		Integer trust = shield.equals(Tag.FALSE) && ((Integer)tags.get("trust")).equals(Tag.TRUE)
				? worldId : Tag.FALSE;
		Float userVer = (Float)tags.get("ver");
		HTWorld world = null;
		int worldChildCount = 1;
		HTWorldTextStyle textStyle = null;
		
		switch(tp) {
		case Tag.WORLD_TYPE_DEFAULT:
			//构造子世界信息 
			Object[] childWorldInfo = JSONUtil
					.coverChildWorldInfoFromString(childsJSON);
			Map<Integer, HTWorldChildWorld> childWorldMap = (Map<Integer, HTWorldChildWorld>) childWorldInfo[0];
			Map<Integer, HTWorldChildWorldThumb> thumbsMap = (Map<Integer, HTWorldChildWorldThumb>) childWorldInfo[1];
			setUpTitleWorld(titleId, titleThumbPath, titlePath, childWorldMap, phoneCode, userVer); // 设置封面世界
			
			saveChildWorldInfo(titleId, childWorldMap, thumbsMap, worldId, phoneCode, userVer);// 循环保存子世界信息
			if (StringUtil.checkIsNULL(titlePath)) {
				titlePath = childWorldMap.get(titleId).getPath();
			}
			worldChildCount = childWorldMap.size();
			break;
		case Tag.WORLD_TYPE_TEXT:
			textStyle = new HTWorldTextStyle();
			textStyle.setColor(color);
			textStyle.setMask(mask);
			break;
		default:
			break;
		}
		// 生成短链
		String shortLink = MD5Encrypt.shortUrl(worldId);
		
		worldDesc = StringUtil.filterXSS(worldDesc);
		world = new HTWorld(worldId, shortLink, authorId, worldName, worldDesc, 
				null, null,  null, date, date, coverPath, titlePath, bgPath,
				titleThumbPath, thumbs, longitude, latitude, locationDesc, locationAddr, 
				phoneCode, province, city, size, worldChildCount, ver, tp, Tag.TRUE, 
				trust, shield, textStyle);
		
		world.setWorldURL(worldDao.getUrlPrefix() + shortLink);
		
//		// 兼容旧版本参加活动
//		if (!StringUtil.checkIsNULL(activityIds)) { 
//			Integer[] actIds = StringUtil.convertStringToIds(activityIds);
//			for (Integer actId : actIds) {
//				Integer lwid = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
//				worldLabelWorldDao.saveLabelWorld(new HTWorldLabelWorld(lwid, worldId, authorId,
//						actId, Tag.FALSE, lwid, 0));
//			}
//		}
		
		// 保存织图标签
		try {
			if(!StringUtil.checkIsNULL(worldLabel)) {
				String[] names = worldLabel.split(",");
				Set<String> nameSet = new LinkedHashSet<String>();
				Map<String, HTWorldLabel> labelMap = worldLabelDao.queryLabelByNames(names);
				for(String name : names) {
					name = StringUtil.filterXSS(name);
					if(StringUtil.checkIsNULL(name) || name.trim().equals("") || nameSet.contains(name)) {
						continue;
					}
					nameSet.add(name);
					HTWorldLabel label = labelMap.get(name);
					Integer labelId = 0;
					Integer valid = Tag.TRUE;
					if(label == null) {
						labelId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
						String pinyin = StringUtil.getPinYin(name);
						label = new HTWorldLabel(labelId, name, pinyin, 0, new Date(), Tag.FALSE, Tag.TRUE, 0, 0);
						worldLabelDao.saveLabel(label);
					} else {
						labelId = label.getId();
						if(shield.equals(Tag.TRUE) || trust.equals(Tag.FALSE)) {
							valid = Tag.FALSE;
						}
	//					if(label.getLabelState().equals(Tag.WORLD_LABEL_ACTIVITY)) {
	//						valid = Tag.FALSE;
	//					}
					}
					Integer lwid = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
					worldLabelWorldDao.saveLabelWorld(new HTWorldLabelWorld(lwid, worldId, authorId, 
							labelId, valid, lwid, 0));
					int count = 0;
					if(label.getLabelState().equals(Tag.WORLD_LABEL_NORMAL)) { // 普通标签算真实总数，其他标签等审核
						Long labelWorldCount = worldLabelWorldDao.queryWorldCountByLabelId(labelId);
						count = labelWorldCount.intValue();
						worldLabelDao.updateWorldCount(labelId, count);
					} else if(shield.equals(Tag.FALSE) && !trust.equals(Tag.FALSE)) {
						count = worldLabelDao.queryWorldCount(labelId);
						worldLabelDao.updateWorldCount(labelId, ++count);
					}
				}
				String[] labelArray = new String[nameSet.size()];
				world.setWorldLabel(StringUtil.convertArrayToString(nameSet.toArray(labelArray)));
			}
			
			if(!StringUtil.checkIsNULL(channelIds)) {
				Integer[] cids = StringUtil.convertStringToIds(channelIds);
				for(Integer cid : cids) {
					channelService.saveChannelWorld(cid, worldId, authorId, worldChildCount);
				}
			}
			
		} catch(Exception e) {
			Log.warn(authorId, "save world label error, " + e.getMessage());
		}
		
		worldDao.saveWorld(world); // 保存世界信息
		
		// 更新织图总数
		Long worldCount = worldDao.queryWorldCountByAuthorId(authorId);
		Integer childCount = worldDao.queryChildCount(authorId);
		userInfoDao.updateWorldAndChildCount(authorId, worldCount.intValue(), childCount);
		
		userActivityService.addActivityScore(Tag.ACT_TYPE_WORLD, authorId);
		
//		if(trust >= Tag.TRUE && shield.equals(Tag.FALSE)) {
//			worldCacheDao.saveLatestCache(world);
//		}
		return world;
	}
	
	/**
	 * 保存子世界信息
	 * 
	 * @param authorId
	 * @param childId
	 * @param childWorldMap
	 * @param thumbMap
	 * @param worldId
	 * @throws Exception
	 */
	protected void saveChildWorldInfo(Integer childId,
			Map<Integer, HTWorldChildWorld> childWorldMap,
			Map<Integer, HTWorldChildWorldThumb> thumbMap, Integer worldId, Integer phoneCode, Float userVer)
			throws Exception {
		HTWorldChildWorld childWorld = childWorldMap.get(childId);
		childWorld.setWorldId(worldId);
		String childDesc = StringUtil.filterXSS(childWorld.getChildWorldDesc());
		childWorld.setChildWorldDesc(childDesc);

		HTWorldChildWorldThumb thumb = thumbMap.get(childId);
		if (thumb != null) {
			String typePath = thumb.getTypePath();
			Integer type = thumb.getType();
			if(!StringUtil.checkIsNULL(typePath)) {
				type = getTypeByTypePath(typePath);
			} else {
				typePath = getTypePathByType(type);
			}
			
			childWorld.setAtId(thumb.getAtId());
			childWorld.setCoordinatex(thumb.getCoordinatex());
			childWorld.setCoordinatey(thumb.getCoordinatey());
			childWorld.setThumbPath(thumb.getThumbPath());
			childWorld.setAngle(thumb.getAngle());
			childWorld.setType(type);
			childWorld.setTypePath(typePath);
			
			// TODO 过几个版本要删除掉
			// 修复IOS2.9.84发布图片出现窜位的bug，强制试用缩略图中的.thumbnail之前路径替代大图路径
			if(phoneCode.equals(Tag.IOS) && userVer.equals(Tag.VERSION_2_9_84)) {
				if(thumb.getThumbPath().endsWith("thumbnail")) {
					int i = thumb.getThumbPath().lastIndexOf(".");
					String path = thumb.getThumbPath().substring(0, i);
					if(path != null && !path.equals(childWorld.getPath())) {
						childWorld.setPath(path);
					}
				}
			}
		}

		Integer atId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_CHILD_WORLD_ID);
		childWorld.setId(atId);
		worldChildWorldDao.saveChildWorld(childWorld);

		Map<Integer, HTWorldChildWorldThumb> thumbs = childWorld.getThumbs();
		Set<Integer> keies = thumbs.keySet();
		Iterator<Integer> it = keies.iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			HTWorldChildWorldThumb childThumb = thumbs.get(key);
			childThumb.setAtId(atId);
			/*
			 * 递归保存包含缩略图对应的子世界
			 */
			saveChildWorldInfo(childThumb.getToId(), childWorldMap, thumbMap,
					worldId, phoneCode, userVer);
		}
	}
	
	private Integer getTypeByTypePath(String typePath) {
		Integer type = Tag.BASE_THUMB_CIRCLE;
		if(!StringUtil.checkIsNULL(typePath)) {
			if(typePath.equals(baseThumbPathAixin)) {
				type = Tag.BASE_THUMB_TYPE_AIXIN;
				
			} else if(typePath.equals(baseThumbPathXing)) {
				type = Tag.BASE_THUMB_TYPE_XING;
				
			} else if(typePath.equals(baseThumbPathHuabian)) {
				type = Tag.BASE_THUMB_TYPE_HUABIAN;
				
			} else if(typePath.equals(baseThumbPathHua)) {
				type = Tag.BASE_THUMB_TYPE_HUA;
			}
		}
		return type;
	}
	
	private String getTypePathByType(Integer type) {
		String typePath = null;
		switch(type) {
		case Tag.BASE_THUMB_TYPE_AIXIN:
			typePath = baseThumbPathAixin;
			break;
		case Tag.BASE_THUMB_TYPE_XING:
			typePath = baseThumbPathXing;
			break;
		case Tag.BASE_THUMB_TYPE_HUABIAN:
			typePath = baseThumbPathHuabian;
			break;
		case Tag.BASE_THUMB_TYPE_HUA:
			typePath = baseThumbPathHua;
			break;
		default:
			break;
		}
		return typePath;
	}

	/**
	 * 初始化封面世界
	 * 
	 * @param titleId
	 * @param titleThumbPath
	 * @param childWorldMap
	 */
	private void setUpTitleWorld(Integer titleId, String titleThumbPath, String titlePath,
			Map<Integer, HTWorldChildWorld> childWorldMap, Integer phoneCode, Float userVer) {
		HTWorldChildWorld titleChild = childWorldMap.get(titleId);
		titleChild.setThumbPath(titleThumbPath);
		titleChild.setIsTitle(1); // 设置封面标志
		titleChild.setAtId(0); // 表示其并不在任何子世界之中
		titleChild.setCoordinatex(0);
		titleChild.setCoordinatey(0);
		
		// TODO 过几个版本要删除掉
		// 修复IOS2.9.84发布织图封面图和首个子世界大图不一致的bug
		if(Tag.IOS == phoneCode && Tag.VERSION_2_9_84 == userVer) {
			if(!titlePath.equals(titleChild.getPath())) {
				titleChild.setPath(titlePath);
			}
		}
	}

	@Override
	public JSONObject getTitleChildPageInfo(Integer worldId, int limit,
			boolean isNotAddClick, boolean isAdmin) throws Exception {
		JSONObject jsonObj = null;
		JSONArray childsArray = null;
		jsonObj = new JSONObject();
		HTWorldChildWorld childWorld = worldChildWorldDao
				.queryTitleChildWorld(worldId);
		Integer titleId = childWorld.getId();
		childsArray = getChildWorldJSONObj(worldId, 1, limit);
		jsonObj.put("title", titleId);
		jsonObj.put("childs", childsArray);
		if (!isNotAddClick || isAdmin) {
			// 更新播放次数
			addClickCount(worldId, 1);
		}

		return jsonObj;
	}

	@Override
	public JSONObject getChildWorldPageInfoById(Integer worldId,
			Integer childId, int limit) throws Exception {
		JSONObject jsonObj = null;
		JSONArray childsArray = null;
		jsonObj = new JSONObject();
		long index = worldChildWorldDao.queryChildWorldIndex(worldId, childId);
		int page = new Double(Math.ceil((float) index / (float) limit))
				.intValue();
		childsArray = getChildWorldJSONObj(worldId, page, limit);
		jsonObj.put("childs", childsArray);
		return jsonObj;
	}


	/**
	 * 分页获取子世界JSON对象
	 * 
	 * @param conn
	 * @param worldId
	 * @param start
	 * @param limit
	 * @param childWorldCount
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	private JSONArray getChildWorldJSONObj(Integer worldId, Integer start,
			Integer limit) throws Exception {
		// 分页查询子世界信息
		List<HTWorldChildWorldDto> childWorldList = worldChildWorldDao
				.queryShowChildWorldDtosByWorldId(worldId, new RowSelection(
						start, limit));
		return getChildWorldJSONObj(childWorldList);
	}

	/**
	 * 获取子世界JSON对象
	 * 
	 * @param conn
	 * @param childWorldList
	 * @return
	 * @throws Exception
	 */
	private JSONArray getChildWorldJSONObj(
			List<HTWorldChildWorldDto> childWorldList) throws Exception {
		List<HTWorldChildWorldThumb> childs = new ArrayList<HTWorldChildWorldThumb>();
		JSONArray childsJSONArray = JSONArray.fromObject(childs);
		if(childWorldList.size() > 0) {
			Integer[] atIds = new Integer[childWorldList.size()];
			for (int i = 0; i < childWorldList.size(); i++) {
				atIds[i] = childWorldList.get(i).getId();
			}
			Map<Integer, List<HTWorldChildWorldThumb>> thumbMap = worldChildWorldDao
					.queryThumbsMapByAtId(atIds);
			for (HTWorldChildWorldDto dto : childWorldList) {
				JSONObject childJSO = new JSONObject();
				Integer childId = dto.getId();
				List<HTWorldChildWorldThumb> thumbList = thumbMap.get(childId);
				if (thumbList == null) {
					thumbList = new ArrayList<HTWorldChildWorldThumb>();
				}
				childJSO.put("child", dto);
				childJSO.put("thumbs", thumbList);
				childsJSONArray.add(childJSO);
			}
			thumbMap.clear();
			thumbMap = null;
		}
		return childsJSONArray;
	}

	@Override
	public void deleteWorld(Integer worldId, Integer userId) throws Exception {
		HTWorld world = worldDao.queryWorldById(worldId);
		if (world != null && world.getValid() == Tag.TRUE
				&& world.getAuthorId().equals(userId)
				&& world.getShield() == Tag.FALSE) {
			worldDao.validRecord(HTS.HTWORLD_HTWORLD, Tag.FALSE, worldId);
			Long count = worldDao.queryWorldCountByAuthorId(userId);
			Integer childCount = worldDao.queryChildCount(userId);
			userInfoDao.updateWorldAndChildCount(userId, count.intValue(), childCount);
		} else {
			throw new HTSException("织图不存在或已经被删除", ERROR_CODE_REPEAT_OPT);
		}
	}

	@Override
	public HTWorld getWorldById(Integer worldId, boolean isAddClick)
			throws Exception {
		HTWorld world = worldDao.queryWorldById(worldId);
		if (isAddClick) {
			addClickCount(worldId, 1);
		}
		return world;
	}

	@Override
	public void extractExtraInfo(boolean trimExtra, int commentLimit,
			int likedLimit, int worldLimit, final List<? extends HTWorldWithExtra> worldList) {
		extractExtraInfo(false, false, null, trimExtra, commentLimit, likedLimit, worldLimit, worldList);
	}
	
	@Override
	public void extractExtraInfo(boolean extractLiked, boolean extractKeep, Integer userId, 
			boolean trimExtra, int commentLimit, int likedLimit, int worldLimit, 
			final List<? extends HTWorldWithExtra> worldList) {
		if (!trimExtra && worldLimit > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] worldIds = new Integer[worldLimit];
			for (int i = 0; i < worldLimit; i++) {
				Integer worldId = worldList.get(i).getId();
				worldIds[i] = worldId;
				indexMap.put(worldId, i);
			}
			
			// 设置喜欢标记
			if(extractLiked && userId != null) {
				worldLikedDao.queryLiked(userId, worldIds, new RowCallback<Integer>() {

					@Override
					public void callback(Integer t) {
						Integer index = indexMap.get(t);
						if(index != null) {
							worldList.get(index).setLiked(Tag.TRUE);
						}
					}
				});
			}
			
//			// 设置收藏标记
//			if(extractKeep && userId != null) {
//				worldKeepDao.queryKeep(userId, worldIds, new RowCallback<Integer>() {
//
//					@Override
//					public void callback(Integer t) {
//						Integer index = indexMap.get(t);
//						if(index != null) {
//							worldList.get(index).setLiked(Tag.TRUE);
//						}
//					}
//				});
//			}
			
			if (commentLimit > 0) {
				worldCommentDao.queryCommentUserByLimit(worldIds, commentLimit,
						new RowCallback<HTWorldCommentUser>() {

							@Override
							public void callback(HTWorldCommentUser comment) {
								Integer wid = comment.getWorldId();
								Integer index = indexMap.get(wid);
								worldList.get(index).getComments().add(comment);
							}
						});
			}
			if (likedLimit > 0) {
				final Map<Integer, UserVerify> verifyMap = userInfoService.getVerify();
				worldLikedDao.queryLikedUserByLimit(worldIds, likedLimit,
						new RowCallback<HTWorldLikedUser>() {

							@Override
							public void callback(HTWorldLikedUser likedUser) {
								Integer verifyId = likedUser.getVerifyId();
								if(verifyMap.containsKey(verifyId)) {
									UserVerify uv = verifyMap.get(verifyId);
									likedUser.setVerifyName(uv.getVerifyName());
									likedUser.setVerifyIcon(uv.getVerifyIcon());
								}
								Integer wid = likedUser.getWorldId();
								Integer index = indexMap.get(wid);
								worldList.get(index).getLikes().add(likedUser);
							}

						});
			}
		}
	}

	@Override
	public void extractExtraInfo(boolean extractLiked, boolean extractKeep, Integer userId, 
			boolean trimExtra, int commentLimit,
			int likedLimit, final HTWorldInteractDto world) {
		if (!trimExtra && world != null) {
			final Map<Integer, UserVerify> verifyMap = userInfoService.getVerify();
			Integer worldId = world.getId();
			
			if(extractLiked && userId != null) {
				Integer liked = worldLikedDao.queryLikedId(userId, worldId) != 0 ? Tag.TRUE : Tag.FALSE;
				world.setLiked(liked);
			}
			
			if (commentLimit > 0) {
				worldCommentDao.queryCommentUserByLimit(worldId, commentLimit,
						new RowCallback<HTWorldCommentUser>() {

							@Override
							public void callback(HTWorldCommentUser comment) {
								world.getComments().add(comment);
							}

						});
			}
			if (likedLimit > 0) {
				worldLikedDao.queryLikedUserByLimit(worldId, likedLimit,
						new RowCallback<HTWorldLikedUser>() {

							@Override
							public void callback(HTWorldLikedUser likedUser) {
								Integer verifyId = likedUser.getVerifyId();
								if(verifyMap.containsKey(verifyId)) {
									UserVerify uv = verifyMap.get(verifyId);
									likedUser.setVerifyName(uv.getVerifyName());
									likedUser.setVerifyIcon(uv.getVerifyIcon());
								}
								world.getLikes().add(likedUser);
							}
						});
			}
		}
	}

	@Override
	public void buildConcernWorld(final Integer userId, int maxId,
			int start, int limit, final Map<String, Object> jsonMap,
			boolean trimTotal, final boolean trimExtras,
			final int commentLimit, final int likedLimit) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryConcernWorld(userId, rowSelection);
						extractExtraInfo(true, true, userId, trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(userId, worldList);
						if(worldList.size() > sysRecLimit) {
							recommendUser(userId, jsonMap);
						}
						
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryConcernWorld(userId, maxId,
										rowSelection);
						extractExtraInfo(true, true, userId, trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(userId, worldList);
						if(worldList.size() > sysRecLimit) {
							recommendUser(userId, jsonMap);
						}
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0l;
					}

				}, OptResult.JSON_KEY_HTWORLD, null);
	}
	
	/**
	 * 从信息流推荐用户
	 * 
	 * @param userId
	 * @param jsonMap
	 * @return
	 */
	private List<UserDynamicRec> recommendUser(Integer userId, Map<String, Object> jsonMap)	 {
		List<UserDynamicRec> recList = null;
		int start = NumberUtil.getRandomNum(0, sysRecStart);
		recList = userRecDao.queryOpRecList(userId, start, sysRecLimit);
		if(recList != null && recList.size() > 0) {
			userInfoService.extractVerify(recList);
			jsonMap.put(OptResult.JSON_KEY_USER_REC_MSG, USER_REC_MSG_SYS_REC);
			jsonMap.put(OptResult.JSON_KEY_USER_REC, recList);
		}
		return recList;
	}
	
	@Override
	public void buildKeepWorld(final Integer userId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,
			boolean trimTotal, final boolean trimExtras,
			final int commentLimit, final int likedLimit) throws Exception {
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryKeepWorld(userId, rowSelection);
						extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(userId, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryKeepWorld(userId, maxId,
										rowSelection);
						extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(userId, worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return worldDao.queryKeepWorldCountByMaxId(userId, maxId);
					}

				}, OptResult.JSON_KEY_HTWORLD, totalKey);
	}

	@Override
	public void buildLikedWorld(final Integer userId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,
			boolean trimTotal, final boolean trimExtras,
			final int commentLimit, final int likedLimit) throws Exception {
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryLikedWorld(userId, rowSelection);
						extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(),
								worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryLikedWorld(userId, maxId,
										rowSelection);
						extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(),
								worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return worldDao.queryLikedWorldCountByMaxId(userId, maxId);
					}

				}, OptResult.JSON_KEY_HTWORLD, totalKey);

	}

	@Override
	public void buildUserWorld(final Integer userId, Integer joinId,
			int maxId, int start, int limit,
			Map<String, Object> jsonMap, boolean trimTotal,
			final boolean trimExtras, final int commentLimit,
			final int likedLimit) throws Exception {
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		if (joinId == null || joinId == 0) {
			joinId = userId;
		}
		final Integer tmpJoinId = joinId;
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryUserWorld(userId, tmpJoinId, rowSelection);
						extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
						userInfoService.extractVerify(worldList);
						if(!tmpJoinId.equals(userId))
							userInteractService.extractRemark(tmpJoinId, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryUserWorld(userId, tmpJoinId,
										maxId, rowSelection);
						extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
						userInfoService.extractVerify(worldList);
						if(!tmpJoinId.equals(userId))
							userInteractService.extractRemark(tmpJoinId, worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return worldDao.queryUserWorldCountByMaxId(userId, maxId);
					}

				}, OptResult.JSON_KEY_HTWORLD, totalKey);
	}
	
	@Override
	public void buildLatestWorld(final Integer joinId, final int maxId, int start, final int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final boolean trimExtras, final int commentLimit, final int likedLimit) throws Exception {
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = new ArrayList<HTWorldInteractDto>();
						List<HTWorld> cacheList = worldCacheDao.queryCacheLatestList(limit-1); // 为了保证此次查询的结果最后一条记录id为最小
						if(cacheList.size() > 0) {
							final Map<Integer, HTWorldInteractDto> map = new HashMap<Integer, HTWorldInteractDto>();
							Integer[] ids = new Integer[cacheList.size()];
							for(int i = 0; i < cacheList.size(); i++) {
								ids[i] = cacheList.get(i).getId();
							}
							worldDao.queryLatestWorld(joinId, ids, new RowCallback<HTWorldInteractDto>() {
	
								@Override
								public void callback(HTWorldInteractDto t) {
									map.put(t.getId(), t);
								}
							});
							int maxId = ids[0];
							for(Integer id : ids) { // 按照缓存队列重新排序
								if(id < maxId) 
									maxId = id; // 找到队列中最小的id
								HTWorldInteractDto dto = map.get(id);
								if(dto != null) {
									worldList.add(dto);
								}
							}
							List<HTWorldInteractDto> otherList = worldDao.queryLatestWorld(joinId, maxId-1, new RowSelection(1, limit - worldList.size()));
							worldList.addAll(otherList);
						} else {
							worldList = worldDao.queryLatestWorld(joinId, rowSelection);
						}
						extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(joinId, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryLatestWorld(joinId, maxId, rowSelection);
						extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(),
								worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return worldDao.queryLatestWorldCount(maxId);
					}

				}, OptResult.JSON_KEY_HTWORLD, totalKey);
		
	}
	
	@Override
	public void buildLatestAndUserWorld(final Integer joinId, final int maxId, int start, final int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final boolean trimExtras, final int commentLimit, final int likedLimit) throws Exception {
		final Date endDate = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(endDate);
		ca.add(Calendar.DAY_OF_MONTH, -latestDateInterval);
		final Date beginDate = ca.getTime();
		
		buildSerializables("getInteractId", maxId, start, limit, jsonMap, new SerializableListAdapter<HTWorldInteractDto>() {

			@Override
			public List<HTWorldInteractDto> getSerializables(
					RowSelection rowSelection) {
				List<HTWorldInteractDto> worldList = worldDao.queryLatestAndUserWorld(joinId, 
						beginDate, endDate, rowSelection);
				extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
				userInfoService.extractVerify(worldList);
				userInteractService.extractRemark(joinId, worldList);
				return worldList;
			}

			@Override
			public List<HTWorldInteractDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<HTWorldInteractDto> worldList = worldDao.queryLatestAndUserWorld(maxId, 
						joinId, beginDate, endDate, rowSelection);
				extractExtraInfo(trimExtras, commentLimit, likedLimit, worldList.size(), worldList);
				userInfoService.extractVerify(worldList);
				userInteractService.extractRemark(joinId, worldList);
				return worldList;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return 0;
			}
		}, OptResult.JSON_KEY_HTWORLD, OptResult.JSON_KEY_TOTAL_COUNT);
	}
	
	@Override
	public void buildRandomUserWorld(Integer userId, Integer joinId, int limit,
			Map<String, Object> jsonMap) throws Exception {
		List<HTWorldDto> dtoList = null;
		Long totalCount = worldDao.queryUserWorldCount(userId);

		if (totalCount > 1) {
			int start = NumberUtil.getRandomPage(limit, totalCount.intValue());
			dtoList = worldDao.queryHTWorldDtoByUserId(userId, new RowSelection(start, limit));
			userInfoService.extractVerify(dtoList);
			userInteractService.extractRemark(joinId, dtoList);
		} else {
			dtoList = new ArrayList<HTWorldDto>();
		}
		
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, dtoList);

	}

	@Override
	public HTWorldDto getHTWorldDtoFromURL(String requestURL, boolean isAdmin)
			throws Exception {
		String link = parseShortLinkOrIdFromURL(requestURL);
		if (link != null) {
			Integer worldId = 0;
			try {
				worldId = Integer.parseInt(link);
				if (isAdmin) {
					return worldDao.queryHTWorldDtoByIdNoValidCheck(worldId);
				}
				return worldDao.queryHTWorldDtoById(worldId);
			} catch (NumberFormatException e) {
				if (isAdmin) {
					return worldDao.queryHTWorldDtoByShortLinkNoValidCheck(link);
				}
				return worldDao.queryHTWorldDtoByShortLink(link);
			}
		} else {
			return null;
		}
	}

	/**
	 * 从URL解析短链或id
	 * 
	 * @param url
	 * @return
	 */
	public static String parseShortLinkOrIdFromURL(String url) {

		String prefix = null;
		int offset = url.lastIndexOf("/") + 1;
		if (url.startsWith(WORLD_TAG_PREFIX_NO,offset)) {
			prefix = WORLD_TAG_PREFIX_NO;
		} else if (url.startsWith(WORLD_TAG_PREFIX_DT,offset)) {
			prefix = WORLD_TAG_PREFIX_DT;
		} else {
			return null;
		}

		Integer index = url.indexOf(prefix);
		String shortLink = url.substring(index + 2, url.length()).trim();
		return StringUtil.checkIsNULL(shortLink) ? null : shortLink;
	}

	/**
	 * 根据user-agent选择跳转页面
	 * 
	 * @param userAgent
	 * @return
	 */
	public static String switchForwardByUserAgent(String userAgent) {
		for (String key : PHONE_USER_AGENTS) {
			if (userAgent.equals("") || userAgent == null
					|| userAgent.contains(key)) {
				return PHONE_PAGE_TAG;
			}
		}
		return WEB_PAGE_TAG;
	}

	@Override
	public void buildWorldWithTitleChild(Integer worldId, Integer limit,
			boolean isAdmin, Map<String, Object> jsonMap) throws Exception {
		HTWorldDto dto = null;
		if (!isAdmin) {
			dto = worldDao.queryHTWorldDtoById(worldId);
			// 增加播放次数
			addClickCount(worldId, 1);
		} else {
			dto = worldDao.queryHTWorldDtoByIdNoValidCheck(worldId);
		}
		if (dto == null) {
			throw new HTSException("织图不存在");
		}
		// 查询首页子世界信息
		HTWorldChildWorld titleChild = worldChildWorldDao
				.queryTitleChildWorld(worldId);
		JSONArray childsJSONObj = getChildWorldJSONObj(worldId, 1, limit);

		jsonMap.put(OptResult.JSON_KEY_CHILDS, childsJSONObj);
		jsonMap.put(OptResult.JSON_KEY_TITLE, titleChild.getId());
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, dto);

	}

	@Override
	public HTWorldDto getHTWorldDtoById(Integer worldId, boolean isAddClick)
			throws Exception {
		HTWorldDto dto = worldDao.queryHTWorldDtoById(worldId);
		if (isAddClick) {
			addClickCount(worldId, 1);
		}
		return dto;
	}

	@Override
	public void addClickCount(final Integer worldId, final Integer clickCount)
			throws Exception {
		worldDao.addClickCount(worldId, clickCount);
	}

	@Override
	public void buildLatestWorld(int limit, Map<String, Object> jsonMap) throws Exception {
		List<HTWorld> list = worldCacheDao.queryCacheLatestList(limit);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, list);
	}

	@Override
	public void buildHTWorld(final String query, final Integer userId, int maxId,
			int start, int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final boolean trimExtra, final int commentLimit, final int likedLimit) throws Exception {
		if(StringUtil.checkIsNULL(query))
			throw new HTSException("查询条件不允许为空");
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<HTWorldInteractDto>() {

			@Override
			public List<HTWorldInteractDto> getSerializables(
					RowSelection rowSelection) {
				List<HTWorldInteractDto> list = worldDao.queryWorldInteractByDesc(userId, query, rowSelection);
				extractExtraInfo(trimExtra, commentLimit, likedLimit, list.size(), list);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public List<HTWorldInteractDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<HTWorldInteractDto> list = worldDao.queryWorldInteractByDesc(maxId, userId, query, rowSelection);
				extractExtraInfo(trimExtra, commentLimit, likedLimit, list.size(), list);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return worldDao.queryWorldInteractCountByDesc(maxId, query);
			}
		}, OptResult.JSON_KEY_HTWORLD, totalKey);
	}

	@Override
	public void buildLatestChildType(Map<String, Object> jsonMap) throws Exception {
		final List<HTWorldChildWorldType> list = worldChildWorldTypeCacheDao.queryLatestType();
		final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		Integer[] ids = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Integer id = list.get(i).getId();
			ids[i] = id;
			indexMap.put(id, i);
		}
		worldChildWorldTypeDao.queryUseCount(ids, new RowCallback<HTWorldChildWorldTypeDto2>() {

			@Override
			public void callback(HTWorldChildWorldTypeDto2 t) {
				Integer id = t.getId();
				Integer index = indexMap.get(id);
				list.get(index).setUseCount(t.getUseCount());
			}
			
		});
		jsonMap.put(OptResult.JSON_KEY_TYPES, list);
		
	}

	@Override
	public void addChildTypeUseCount(Integer id) throws Exception {
		worldChildWorldTypeDao.addUseCount(id);
	}

	@Override
	public void buildFilterLogo(Float ver, Map<String, Object> jsonMap) throws Exception {
		HTWorldFilterLogo logo = worldFilterLogoCacheDao.queryLogo();
		if(logo != null && logo.getValid().equals(Tag.FALSE) && logo.getVer().equals(ver)) {
			logo = null;
		}
		if(logo == null)
			jsonMap.put(OptResult.JSON_KEY_LOGO, "");
		else 
			jsonMap.put(OptResult.JSON_KEY_LOGO, logo);
	}

	@Override
	public void buildLatestIndex(Integer userId, Long startTime, Integer intervalType,
			Integer nextCursor, Map<String, Object> jsonMap) throws Exception {
		int[] intervalArray = null;
		int preInterval = 0;
		if(startTime == null || startTime.equals(0l)) {
			startTime = new Date().getTime();
			intervalType = getLatestInterval(startTime);
		}
		intervalArray = LATEST_INTERVAL[intervalType];
		if(nextCursor != -1 && intervalArray.length > nextCursor) {
			if(nextCursor > 0)
				preInterval = intervalArray[nextCursor-1];
			int intervalSize = intervalArray.length - nextCursor < LATEST_INDEX_SIZE ? intervalArray.length - nextCursor : LATEST_INDEX_SIZE;
			int[] intervals = new int[intervalSize];
			for(int i = 0; i < intervalSize; i++) {
				intervals[i] = intervalArray[nextCursor++];
			}
			List<HTWorldLatestIndex> ilist = worldDao.queryLatestIndex(userId, startTime, preInterval, intervals);
			int[] limits = new int[intervalSize];
			for(int i = 0; i < intervalSize; i++) {
				if(i % 2 == 0) {
					int d = ilist.get(i).getTotal() > 2*3 ? -1 : 0;
					limits[i] = 2 * 3 + d;
				} else {
					int d = ilist.get(i).getTotal() > 3*3 ? -1 : 0;
					limits[i] = 3 * 3 + d;
				}	
			}
			List<HTWorldLatest> wlist = worldDao.queryLatest(userId, startTime, preInterval, intervals, limits);
			
			if(nextCursor >= intervalArray.length) 
				nextCursor = -1;
			
			// 加载关注id列表
			if(wlist.size() > 0) {
				Set<Integer> cidSet = new HashSet<Integer>();
				Integer[] concernIds = new Integer[wlist.size()];
				for(int i = 0; i < wlist.size(); i++) {
					HTWorldLatest world = wlist.get(i);
					Integer uid = world.getAuthorId();
					if(!cidSet.contains(uid)) {
						concernIds[i] = world.getAuthorId();
						cidSet.add(world.getAuthorId());
					}
				}
				Set<Integer> cidSet2 = userConcernDao.queryConcernIds(userId, concernIds);
				for(HTWorldLatest w : wlist) {
					if(cidSet2.contains(w.getAuthorId()))
						w.setConcerned(Tag.TRUE);
					else 
						w.setConcerned(Tag.FALSE);
				}
			}

			jsonMap.put(OptResult.JSON_KEY_START_TIME, startTime);
			jsonMap.put(OptResult.JSON_KEY_INTERVAL_TYPE, intervalType);
			jsonMap.put(OptResult.JSON_KEY_NEXT_CURSOR, nextCursor);
			jsonMap.put(OptResult.JSON_KEY_HTWORLD, wlist);
			jsonMap.put(OptResult.JSON_KEY_INDEX, ilist);
		} else {
			jsonMap.put(OptResult.JSON_KEY_START_TIME, startTime);
			jsonMap.put(OptResult.JSON_KEY_INTERVAL_TYPE, intervalType);
			jsonMap.put(OptResult.JSON_KEY_NEXT_CURSOR, nextCursor);
			jsonMap.put(OptResult.JSON_KEY_HTWORLD, new ArrayList<HTWorldLatest>());
			jsonMap.put(OptResult.JSON_KEY_INDEX, new ArrayList<HTWorldLatestIndex>());
		}
	}
	
	private int getLatestInterval(long now) {
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		ca.getTimeInMillis();
		ca.set(Calendar.HOUR_OF_DAY, 3);
		long start = ca.getTimeInMillis();
		ca.set(Calendar.HOUR_OF_DAY, 8);
		long end = ca.getTimeInMillis();
		if(start <= now && now <= end) {
			return 0;
		}
		return 1;
	}
	
	@Override
	public void buildLatest(Integer userId, Long startTime, Long endTime, Integer maxId, 
			Integer limit, Map<String, Object> jsonMap) throws Exception {
		if(startTime <= endTime) {
			throw new HTSException("startTime必须大于endTime");
		}
		List<HTWorldLatest> wlist = null;
		Long total = 0l;
		if(!endTime.equals(0)) {
			wlist = worldDao.queryLatest(maxId, userId, startTime, endTime, limit);
			total = worldDao.queryLatestCount(maxId, userId, startTime, endTime);
		} else {
			wlist = worldDao.queryLatest(maxId, userId, startTime, limit);
			total = worldDao.queryLatestCount(maxId, userId, startTime);
		}	
		
		// 加载关注id列表
		if(wlist.size() > 0) {
			Set<Integer> cidSet = new HashSet<Integer>();
			Integer[] concernIds = new Integer[wlist.size()];
			for(int i = 0; i < wlist.size(); i++) {
				HTWorldLatest world = wlist.get(i);
				Integer uid = world.getAuthorId();
				if(!cidSet.contains(uid)) {
					concernIds[i] = world.getAuthorId();
					cidSet.add(world.getAuthorId());
				}
			}
			Set<Integer> cidSet2 = userConcernDao.queryConcernIds(userId, concernIds);
			for(HTWorldLatest w : wlist) {
				if(cidSet2.contains(w.getAuthorId()))
					w.setConcerned(Tag.TRUE);
				else 
					w.setConcerned(Tag.FALSE);
			}
		}
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, wlist);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
	}
	
	@Override
	public void buildLatestIndex2(Integer userId, Long startTime, Integer intervalType,
			Integer nextCursor, Map<String, Object> jsonMap) throws Exception {
		int[] intervalArray = null;
		int preInterval = 0;
		if(startTime == null || startTime.equals(0l)) {
			startTime = new Date().getTime();
			intervalType = getLatestInterval(startTime);
		}
		intervalArray = LATEST_INTERVAL[intervalType];
		if(nextCursor != -1 && intervalArray.length > nextCursor) {
			if(nextCursor > 0)
				preInterval = intervalArray[nextCursor-1];
			int intervalSize = intervalArray.length - nextCursor < LATEST_INDEX_SIZE ? intervalArray.length - nextCursor : LATEST_INDEX_SIZE;
			int[] intervals = new int[intervalSize];
			for(int i = 0; i < intervalSize; i++) {
				intervals[i] = intervalArray[nextCursor++];
			}
			List<HTWorldLatestIndex> ilist = worldDao.queryLatestIndex2(userId, startTime, preInterval, intervals);
			int[] limits = new int[intervalSize];
			for(int i = 0; i < intervalSize; i++) {
				if(i % 2 == 0) {
					int d = ilist.get(i).getTotal() > 2*3 ? -1 : 0;
					limits[i] = 2 * 3 + d;
				} else {
					int d = ilist.get(i).getTotal() > 3*3 ? -1 : 0;
					limits[i] = 3 * 3 + d;
				}	
			}
			List<HTWorldLatest> wlist = worldDao.queryLatest2(userId, startTime, preInterval, intervals, limits);
			
			if(nextCursor >= intervalArray.length) 
				nextCursor = -1;
			
			// 加载关注id列表
			if(wlist.size() > 0) {
				Set<Integer> cidSet = new HashSet<Integer>();
				Integer[] concernIds = new Integer[wlist.size()];
				for(int i = 0; i < wlist.size(); i++) {
					HTWorldLatest world = wlist.get(i);
					Integer uid = world.getAuthorId();
					if(!cidSet.contains(uid)) {
						concernIds[i] = world.getAuthorId();
						cidSet.add(world.getAuthorId());
					}
				}
				Set<Integer> cidSet2 = userConcernDao.queryConcernIds(userId, concernIds);
				for(HTWorldLatest w : wlist) {
					if(cidSet2.contains(w.getAuthorId()))
						w.setConcerned(Tag.TRUE);
					else 
						w.setConcerned(Tag.FALSE);
				}
			}

			jsonMap.put(OptResult.JSON_KEY_START_TIME, startTime);
			jsonMap.put(OptResult.JSON_KEY_INTERVAL_TYPE, intervalType);
			jsonMap.put(OptResult.JSON_KEY_NEXT_CURSOR, nextCursor);
			jsonMap.put(OptResult.JSON_KEY_HTWORLD, wlist);
			jsonMap.put(OptResult.JSON_KEY_INDEX, ilist);
		} else {
			jsonMap.put(OptResult.JSON_KEY_START_TIME, startTime);
			jsonMap.put(OptResult.JSON_KEY_INTERVAL_TYPE, intervalType);
			jsonMap.put(OptResult.JSON_KEY_NEXT_CURSOR, nextCursor);
			jsonMap.put(OptResult.JSON_KEY_HTWORLD, new ArrayList<HTWorldLatest>());
			jsonMap.put(OptResult.JSON_KEY_INDEX, new ArrayList<HTWorldLatestIndex>());
		}
	}
	
	@Override
	public void buildLatest2(Integer userId, Long startTime, Long endTime, Integer maxId, 
			Integer limit, Map<String, Object> jsonMap) throws Exception {
		if(startTime <= endTime) {
			throw new HTSException("startTime必须大于endTime");
		}
		List<HTWorldLatest> wlist = null;
		Long total = 0l;
		if(!endTime.equals(0)) {
			wlist = worldDao.queryLatest2(maxId, userId, startTime, endTime, limit);
			total = worldDao.queryLatestCount2(maxId, userId, startTime, endTime);
		} else {
			wlist = worldDao.queryLatest2(maxId, userId, startTime, limit);
			total = worldDao.queryLatestCount2(maxId, userId, startTime);
		}	
		
		// 加载关注id列表
		if(wlist.size() > 0) {
			Set<Integer> cidSet = new HashSet<Integer>();
			Integer[] concernIds = new Integer[wlist.size()];
			for(int i = 0; i < wlist.size(); i++) {
				HTWorldLatest world = wlist.get(i);
				Integer uid = world.getAuthorId();
				if(!cidSet.contains(uid)) {
					concernIds[i] = world.getAuthorId();
					cidSet.add(world.getAuthorId());
				}
			}
			Set<Integer> cidSet2 = userConcernDao.queryConcernIds(userId, concernIds);
			for(HTWorldLatest w : wlist) {
				if(cidSet2.contains(w.getAuthorId()))
					w.setConcerned(Tag.TRUE);
				else 
					w.setConcerned(Tag.FALSE);
			}
		}
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, wlist);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
	}

	@Override
	public void buildAllChild(Integer worldId, final Map<String, Object> jsonMap) {
		
		final JSONObject result = new JSONObject();
		final List<HTWorldChildWorldAndThumbDto> childs = new ArrayList<HTWorldChildWorldAndThumbDto>();
		final List<HTWorldChildWorldThumb> thumbs = new ArrayList<HTWorldChildWorldThumb>();
		final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		
		worldChildWorldDao.queryAllChild(worldId, new RowCallback<HTWorldChildWorld>() {

			@Override
			public void callback(HTWorldChildWorld t) {
				if(t.getIsTitle() != null && t.getIsTitle().equals(Tag.TRUE)) {
					result.put(OptResult.JSON_KEY_TITLE, t.getId());
				}
				
				HTWorldChildWorldDto child = new HTWorldChildWorldDto(
						t.getId(), 
						t.getChildWorldDesc(),
						t.getWorldId(),
						t.getPath(),
						t.getWidth(),
						t.getHeight(),
						t.getIsTitle(),
						t.getAngle(),
						t.getType(),
						t.getTypePath());
				childs.add(new HTWorldChildWorldAndThumbDto(child));
				thumbs.add(new HTWorldChildWorldThumb(
					t.getId(), 
					t.getCoordinatex(),
					t.getCoordinatey(), 
					t.getAtId(), 
					t.getThumbPath(), 
					t.getAngle(), 
					t.getType(),
					t.getTypePath()));
				indexMap.put(t.getId(), childs.size() - 1);
			}
		});

		for(HTWorldChildWorldThumb thumb : thumbs) {
			Integer index = indexMap.get(thumb.getAtId());
			if(index != null) {
				HTWorldChildWorldAndThumbDto dto  = childs.get(index);
				dto.addThumb(thumb);
			}
		}
		result.put(OptResult.JSON_KEY_CHILDS, childs);
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, result);
	}

	@Override
	public void buildLatestId(Integer maxId, Integer minId,
			Integer limit, Map<String, Object> jsonMap) throws Exception {
		if(maxId != null && maxId != 0) {
			List<HTWorldLatestId> ids = worldDao.queryLatestIdByMaxId(maxId, limit);
			jsonMap.put(OptResult.JSON_KEY_NEXT_ID, ids);
		}
		if(minId != null && minId != 0) {
			List<HTWorldLatestId> ids = worldDao.queryLatestIdByMinId(minId, limit);
			jsonMap.put(OptResult.JSON_KEY_PREVIOUS_ID, ids);
		}
	}

}
