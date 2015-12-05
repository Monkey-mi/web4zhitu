package com.hts.web.ztworld.service.impl;

import java.io.Serializable;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.HTSErrorCode;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.LoggerKeies;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.base.constant.Version;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldChannelName;
import com.hts.web.common.pojo.HTWorldChildWorld;
import com.hts.web.common.pojo.HTWorldChildWorldAndThumbDto;
import com.hts.web.common.pojo.HTWorldChildWorldDto;
import com.hts.web.common.pojo.HTWorldChildWorldThumb;
import com.hts.web.common.pojo.HTWorldChildWorldType;
import com.hts.web.common.pojo.HTWorldChildWorldTypeDto2;
import com.hts.web.common.pojo.HTWorldCommentInline;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.pojo.HTWorldFilterLogo;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.pojo.HTWorldLatest;
import com.hts.web.common.pojo.HTWorldLatestId;
import com.hts.web.common.pojo.HTWorldLatestIndex;
import com.hts.web.common.pojo.HTWorldLikedInline;
import com.hts.web.common.pojo.HTWorldTextStyle;
import com.hts.web.common.pojo.HTWorldThumbDto;
import com.hts.web.common.pojo.HTWorldWithExtra;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.common.pojo.OpUser;
import com.hts.web.common.pojo.OpUserVerifyDto;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.operations.dao.BulletinCacheDao;
import com.hts.web.operations.dao.ChannelAutoRejectIdCacheDao;
import com.hts.web.operations.dao.ChannelCacheDao;
import com.hts.web.operations.dao.ChannelDao;
import com.hts.web.operations.dao.OpUserVerifyDtoCacheDao;
import com.hts.web.operations.dao.UserVerifyRecCacheDao;
import com.hts.web.operations.service.ChannelService;
import com.hts.web.operations.service.NearService;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.service.UserActivityService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.userinfo.service.UserMsgService;
import com.hts.web.ztworld.dao.HTWorldCacheDao;
import com.hts.web.ztworld.dao.HTWorldChildWorldDao;
import com.hts.web.ztworld.dao.HTWorldChildWorldTypeCacheDao;
import com.hts.web.ztworld.dao.HTWorldChildWorldTypeDao;
import com.hts.web.ztworld.dao.HTWorldCommentDao;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldFilterLogoCacheDao;
import com.hts.web.ztworld.dao.HTWorldLabelDao;
import com.hts.web.ztworld.dao.HTWorldLabelWorldDao;
import com.hts.web.ztworld.dao.HTWorldLikedDao;
import com.hts.web.ztworld.dao.HTWorldWeekDao;
import com.hts.web.ztworld.service.ZTWorldService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

	private static Logger saveWorldLogger = Logger.getLogger(LoggerKeies.WORLD_WORLD);
	
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
	 * 推荐类型
	 */
	private static final int[] REC_TYPES = new int[]{ Tag.BULLETIN_ACT,
			Tag.BULLETIN_USER,
			Tag.BULLETIN_CHANNEL,
			Tag.BULLETIN_PAGE};
	
	
	/**
	 * 最少推荐数量
	 */
	private static final int REC_MIN_SIZE = 2;
	
	/**
	 * 最新索引size
	 */
	private static final int LATEST_INDEX_SIZE = 4;
	
	/**
	 * 官方频道id
	 */
	private Integer officialChannelId = 45162;
	/**
	 * 官方频道主id
	 */
	private Integer officialChannelOwnerId = 2063;
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private HTWorldDao worldDao;
	
	@Autowired
	private HTWorldChildWorldDao worldChildWorldDao;
	
	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private HTWorldCommentDao worldCommentDao;
	
	@Autowired
	private HTWorldLikedDao worldLikedDao;
	
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
	private UserConcernDao userConcernDao;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private BulletinCacheDao bulletinCacheDao;
	
	@Autowired
	private ChannelCacheDao channelCacheDao;
	
	@Autowired
	private OpUserVerifyDtoCacheDao opUserVerifyDtoCacheDao;
	
	@Autowired
	private UserVerifyRecCacheDao userVerifyRecCacheDao;

	@Autowired
	private ChannelAutoRejectIdCacheDao channelAutoPassIdCacheDao;
	
	@Autowired
	private UserMsgService userMsgService;
	
	@Autowired
	private HTWorldWeekDao worldWeekDao;
	
	@Autowired
	private NearService nearService;
	
	@Autowired
	private ChannelDao channelDao;
	
	private String baseThumbPathAixin = "http://static.imzhitu.com/world/thumbs/1403056393000.png";
	private String baseThumbPathXing = "http://static.imzhitu.com/world/thumbs/1403057093000.png";
	private String baseThumbPathHuabian = "http://static.imzhitu.com/world/thumbs/1403056953000.png";
	private String baseThumbPathHua = "http://static.imzhitu.com/world/thumbs/1403057046000.png";
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
	public void saveWorld(String childsJSON, Integer titleId,
			Integer phoneCode, Integer id, Integer authorId, String worldName,
			String worldDesc, String worldLabel, String labelIds, String worldType, 
			Integer typeId, String coverPath, String titlePath, String bgPath,
			String titleThumbPath, Double longitude, Double latitude, String locationDesc,
			String locationAddr, String province, String city, Integer size,
			String activityIds, Integer ver, String channelIds, Integer tp, 
			String color, Integer mask, String atIdsStr, String atNamesStr,
			Map<String, Object> jsonMap) throws Exception {

		// 将七牛域名强制转换成织图域名
		coverPath = StringUtil.replaceQiniuDomain(coverPath);
		titlePath = StringUtil.replaceQiniuDomain(titlePath);
		bgPath = StringUtil.replaceQiniuDomain(bgPath);
		titleThumbPath = StringUtil.replaceQiniuDomain(titleThumbPath);
		childsJSON = StringUtil.replaceQiniuDomain(childsJSON);
		
		Date date = new Date();
		Integer worldId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_HTWORLD_ID);
		Map<String, Object> tags = userInfoDao.queryTagById(authorId);
		Integer shield = ((Integer)tags.get("shield")).equals(Tag.TRUE) ? Tag.TRUE : Tag.FALSE;
		Integer valid = shield.equals(Tag.TRUE) ? Tag.FALSE : Tag.TRUE;
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
		List<HTWorldChannelName> channelNames = new ArrayList<HTWorldChannelName>();
		world = new HTWorld(worldId, shortLink, authorId, worldName, worldDesc, 
				null, null,  null, date, date, coverPath, titlePath, bgPath,
				titleThumbPath,channelNames, longitude, latitude, locationDesc, locationAddr, 
				phoneCode, province, city, size, worldChildCount, ver, tp, valid, 
				trust, shield, textStyle);
		
		world.setWorldURL(worldDao.getUrlPrefix() + shortLink);
		
		// 保存织图标签
		try {
			if(!StringUtil.checkIsNULL(worldLabel)) {
				worldLabel = worldLabel.trim();
				String[] names = worldLabel.split(",");
//				StringUtil.trimStrArray(names); // 过滤每个标签的空格
				Set<String> nameSet = new LinkedHashSet<String>();
				Map<String, HTWorldLabel> labelMap = worldLabelDao.queryLabelByNames(names);
				for(String name : names) {
					name = StringUtil.trimLabel(name);
					if(StringUtil.checkIsNULL(name) || name.trim().equals("") || nameSet.contains(name)) {
						continue;
					}
					nameSet.add(name);
					HTWorldLabel label = labelMap.get(name);
					Integer labelId = 0;
					Integer lwvalid = Tag.TRUE;
					if(label == null) {
						labelId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
						String pinyin = StringUtil.getPinYin(name);
						label = new HTWorldLabel(labelId, name, pinyin, 0, 0, new Date(), Tag.FALSE, Tag.TRUE, 0, 0);
						worldLabelDao.saveLabel(label);
					} else {
						labelId = label.getId();
						if(shield.equals(Tag.TRUE) || trust.equals(Tag.FALSE)) {
							lwvalid = Tag.FALSE;
						}
					}
					Integer lwid = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
					worldLabelWorldDao.saveLabelWorld(new HTWorldLabelWorld(lwid, worldId, authorId, 
							labelId, lwvalid, lwid, 0));
					int count = 0;
					if(label.getLabelState().equals(Tag.WORLD_LABEL_NORMAL)) { // 普通标签算真实总数，其他标签等审核
						Long labelWorldCount = worldLabelWorldDao.queryWorldCountByLabelId(labelId);
						count = labelWorldCount.intValue();
						worldLabelDao.updateWorldCount(labelId, count);
					} else if(shield.equals(Tag.FALSE) && !trust.equals(Tag.FALSE)) {
						Long labelWorldCount = worldLabelWorldDao.queryWorldCountByLabelId(labelId);
						int addend = getActivityAddNum(labelWorldCount);
						count = worldLabelDao.queryWorldCount(labelId);
						count += addend;
						worldLabelDao.updateWorldCount(labelId, count);
					}
				}
				String[] labelArray = new String[nameSet.size()];
				world.setWorldLabel(StringUtil.convertArrayToString(nameSet.toArray(labelArray)));
			}
		} catch(Exception e) {
			saveWorldLogger.warn("save world label error, " + e.getMessage(), e);
		}
		
		try {
			if(!StringUtil.checkIsNULL(channelIds)) {
				Integer[] cids = StringUtil.convertStringToIds(channelIds);
				for(Integer cid : cids) {
					if(!authorId.equals(officialChannelOwnerId) && cid.equals(officialChannelId)) // 非官号不允许向官方频道发图
						continue;
					
					String channelName = channelService.queryChannelNameById(cid);
					if(!StringUtil.checkIsNULL(channelName)) {
						world.getChannelNames().add(new HTWorldChannelName(cid, channelName));
						Integer cwvalid = (!channelAutoPassIdCacheDao.isAutoReject(cid) && trust >= Tag.TRUE) ? Tag.TRUE : Tag.FALSE;
						channelService.saveChannelWorld(cid, worldId, authorId, worldChildCount, cwvalid);
					}
				}
			}
		} catch(Exception e) {
			saveWorldLogger.warn("save channel world error, " + e.getMessage(), e);
		}
		
		worldWeekDao.saveWorld(world);
		worldDao.saveWorld(world); // 保存世界信息
		
		// 更新织图总数
		Long worldCount = worldDao.queryWorldCountByAuthorId(authorId);
		Integer childCount = worldDao.queryChildCount(authorId);
		userInfoDao.updateWorldAndChildCount(authorId, worldCount.intValue(), childCount);
		
		userActivityService.addActivityScore(Tag.ACT_TYPE_WORLD, authorId);
		
		jsonMap.put(OptResult.JSON_KEY_HTWORLD, world);
		
		// 添加AT信息
		if(!StringUtil.checkIsNULL(atIdsStr)) {
			List<PushStatus> atPushStatus = userMsgService.saveAtMsgs(atIdsStr, atNamesStr, 
					false, authorId, Tag.ACT_TYPE_WORLD, worldId, worldId, worldDesc);
			jsonMap.put(OptResult.JSON_KEY_AT_PUSH_STATUS, atPushStatus);
		}
		
		// 保存进附近织图列表
		if(trust > 0) {
			try {
				nearService.saveNearWorld(world);
			} catch(Exception e) {
				saveWorldLogger.warn("save near world fail");
			}
		}
		
	}
	
	private int getActivityAddNum(Long realCount) {
		if(realCount > 30) {
			return NumberUtil.getRandomNum(3, 10);
		} else if(realCount > 200){
			return NumberUtil.getRandomNum(20, 30);
		}
		return 1;
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
			nearService.deleteNearWorld(worldId); // 从附近织图列表删除织图
			worldWeekDao.validRecord(HTS.HTWORLD_HTWORLD_WEEK, Tag.FALSE, worldId);
			worldDao.validRecord(HTS.HTWORLD_HTWORLD, Tag.FALSE, worldId);
			Long count = worldDao.queryWorldCountByAuthorId(userId);
			Integer childCount = worldDao.queryChildCount(userId);
			userInfoDao.updateWorldAndChildCount(userId, count.intValue(), childCount);
		} else {
			throw new HTSException(HTSErrorCode.INVALID_WORLD);
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
	public void extractLikeComment(int commentLimit, int likedLimit, 
			final List<? extends HTWorldWithExtra> worldList) {
		extractLikeComment(0, commentLimit, likedLimit, worldList);
	}
	
	@Override
	public void extractLikeComment(int userId, int commentLimit, int likedLimit,
			final List<? extends HTWorldWithExtra> worldList) {
		if(worldList == null || worldList.isEmpty())
			return;
		
		if (userId != 0 || commentLimit > 0 || likedLimit > 0) {
			final Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			Integer[] worldIds = new Integer[worldList.size()];
			for (int i = 0; i < worldList.size(); i++) {
				Integer worldId = worldList.get(i).getId();
				worldIds[i] = worldId;
				indexMap.put(worldId, i);
			}
			
			// 设置喜欢标记
			if(userId != 0) {
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
			
			if (commentLimit > 0) {
				worldCommentDao.queryInlineComment(worldIds, commentLimit,
						new RowCallback<HTWorldCommentInline>() {

							@Override
							public void callback(HTWorldCommentInline comment) {
								Integer wid = comment.getWorldId();
								Integer index = indexMap.get(wid);
								worldList.get(index).getComments().add(comment);
							}
						});
			}
			
			if (likedLimit > 0) {
				final Map<Integer, UserVerify> verifyMap = userInfoService.getVerify();
				worldLikedDao.queryInlineLikedByLimit(worldIds, likedLimit,
						new RowCallback<HTWorldLikedInline>() {

							@Override
							public void callback(HTWorldLikedInline likedUser) {
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
	public void extractLikeComment(int userId, int commentLimit,
			int likedLimit, final HTWorldInteractDto world) {
		
		if(world == null) 
			return;
		
		if (userId != 0 || likedLimit > 0 || commentLimit > 0) {
			final Map<Integer, UserVerify> verifyMap = userInfoService.getVerify();
			Integer worldId = world.getId();
			
			if(userId != 0) {
				world.setLiked(worldLikedDao.isLiked(userId, worldId));
			}
			
			if (likedLimit > 0) {
				worldLikedDao.queryInlineLikedByLimit(worldId, likedLimit,
						new RowCallback<HTWorldLikedInline>() {

							@Override
							public void callback(HTWorldLikedInline likedUser) {
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
	public void buildConcernWorld(final Integer recType, final Integer recPage,
			final Integer userId, int maxId, int start, int limit, 
			final Map<String, Object> jsonMap, boolean trimTotal,
			final int commentLimit, final int likedLimit, final Float appVer) throws Exception {
		buildSerializables(maxId, 1, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryConcernWorld(userId, rowSelection);
						extractLikeComment(userId, commentLimit, likedLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(userId, worldList);
						recFromConcern(userId, recPage, recType, appVer, jsonMap);
						
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryConcernWorld(userId, maxId,
										rowSelection);
						extractLikeComment(userId, commentLimit, likedLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(userId, worldList);
						recFromConcern(userId, recPage, recType, appVer, jsonMap);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return 0l;
					}

				}, OptResult.JSON_KEY_HTWORLD, null);
	}
	
	/**
	 * 从信息流推荐内容
	 * 
	 * @param userId
	 * @param recPage
	 * @param recType
	 * @return
	 */
	private void recFromConcern(Integer userId,
			Integer recPage, Integer recType, Float appVer, Map<String, Object> jsonMap) {
		
		// 定义推荐展示位置，默认为3，即展示在第4张织图下面
		int recIdx = 3;
		// 定义返回的推荐类型
		int type = 0;
		// 定义返回推荐信息
		String recMsg = null;
		// 定义返回推荐信息集合
		List<? extends Serializable> recList = null;
		
		// 当版本高于3.1.5时，返回值为0，TODO 下面逻辑为了快速开发而编写，要整体整改掉，else中为旧版本逻辑，整改时要注意保留
		if ( appVer >= Version.V_3_1_5) {
			// 要翻5页以上才不显示推荐，而REC_TYPES里面只有4个，因为我关注的频道特殊，必须特殊处理，所以在recPage=2到5时，把剩余的推荐加完
			if(recPage == 1) {
				// 推荐位置都在第一张织图下面，并且只为我关注的频道
				recIdx = 0;
				type = Tag.BULLETIN_CHANNEL_SUBSCRIBE;
			} else {
				if(recPage > 5) {
					return;
				}
				type = REC_TYPES[recPage-2];
			}
			
		} else {
			if(recPage > REC_TYPES.length) {
				return;
			}
			
			
			int idx = recPage - 1;
			
			type = REC_TYPES[idx];
		}
		
		switch(type) {
			case Tag.BULLETIN_PAGE:
				recMsg = "推荐专题";
				recList = recPage(userId);
				break;
				
			case Tag.BULLETIN_CHANNEL:
				recMsg = "推荐频道";
				recList = recChannel(userId);
				break;
				
			case Tag.BULLETIN_USER:
				recMsg = "可能感兴趣的人";
				recList = recUser(userId);
				break;
				
			case Tag.BULLETIN_ACT:
				recMsg = "推荐活动";
				recList = recAct(userId);
				break;
				
			case Tag.BULLETIN_CHANNEL_SUBSCRIBE:
				recMsg = "我关注的频道";
				recList = recSubscribeChannel(userId);
				break;
				
			default:
				break;
		}
		
		if(recList != null) {
			jsonMap.put(OptResult.JSON_KEY_REC, recList);
			jsonMap.put(OptResult.JSON_KEY_REC_INDEX, recIdx);
			jsonMap.put(OptResult.JSON_KEY_REC_TYPE, type);
			jsonMap.put(OptResult.JSON_KEY_REC_MSG, recMsg);
		}
	}
	
	/**
	 * 推荐专题网页
	 * 
	 * @param userId
	 * @return
	 */
	private List<OpMsgBulletin> recPage(Integer userId) {
		List<OpMsgBulletin> recList = new ArrayList<OpMsgBulletin>();
		List<OpMsgBulletin> srcList = bulletinCacheDao.queryBulletin();
		for(OpMsgBulletin pmsg : srcList) {
			if(pmsg.getBulletinType().equals(Tag.BULLETIN_PAGE) && 
					recList.size() < REC_MIN_SIZE) {
				recList.add(pmsg);
			}
		}
		if(recList.size() >= REC_MIN_SIZE) {
			return recList;
		}
		return null;
	}
	
	/**
	 * 推荐用户专题
	 * 
	 * @param userId
	 * @return
	 */
	private List<OpUser> recUser(Integer userId) {
		OpUserVerifyDto verify = opUserVerifyDtoCacheDao.queryRandomVerify();
		List<OpUser> list =  userVerifyRecCacheDao.queryRandomUserByVerifyId(
				verify.getId(), REC_MIN_SIZE);
		if(list != null && list.size() >= REC_MIN_SIZE) {
			return list;
		}
		return null;
	}
	
	/**
	 * 推荐活动
	 * 
	 * @param userId
	 * @return
	 */
	private List<OpMsgBulletin> recAct(Integer userId) {
		List<OpMsgBulletin> recList = new ArrayList<OpMsgBulletin>();
		List<OpMsgBulletin> srcList = bulletinCacheDao.queryBulletin();
		for(OpMsgBulletin pmsg : srcList) {
			if(pmsg.getBulletinType().equals(Tag.BULLETIN_ACT) && 
					recList.size() < REC_MIN_SIZE) {
				recList.add(pmsg);
			}
		}
		if(recList.size() >= REC_MIN_SIZE) {
			return recList;
		}
		return null;
	}
	
	/**
	 * 推荐频道
	 * 
	 * @param userId
	 * @return
	 */
	private List<OpChannel> recChannel(Integer userId) {
		return channelCacheDao.queryRandomChannel(REC_MIN_SIZE);
	}
	
	/**
	 * 用户关注的频道
	 * 
	 * @param userId	用户id
	 * @return
	 * @author zhangbo	2015年12月4日
	 */
	private List<OpChannel> recSubscribeChannel(Integer userId) {
		List<OpChannel> clist = new ArrayList<OpChannel>();
		// 根据登陆用户id查找用户关注的频道
		clist = channelDao.querySubscribedChannel(userId, new RowSelection(1,100));
		
		int size = clist.size();
		if(size  > REC_MIN_SIZE) {
			int start = NumberUtil.getRandomIndex(size - REC_MIN_SIZE);
			return clist.subList(start, start + REC_MIN_SIZE - 1);
		} else if(size == REC_MIN_SIZE) {
			return clist.subList(0, 1);
		}
		
		return clist;
	}

	@Override
	public void buildKeepWorld(final Integer userId, int maxId,
			int start, int limit, Map<String, Object> jsonMap,
			boolean trimTotal, final int commentLimit, final int likedLimit) throws Exception {
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		buildSerializables(maxId, start, limit, jsonMap,
				new SerializableListAdapter<HTWorldInteractDto>() {

					@Override
					public List<HTWorldInteractDto> getSerializables(
							RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryKeepWorld(userId, rowSelection);
						extractLikeComment(commentLimit, likedLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(userId, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryKeepWorld(userId, maxId,
										rowSelection);
						extractLikeComment(commentLimit, likedLimit, worldList);
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
						extractLikeComment(commentLimit, likedLimit, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryLikedWorld(userId, maxId,
										rowSelection);
						extractLikeComment(commentLimit, likedLimit, worldList);
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
			Map<String, Object> jsonMap, boolean trimTotal, final int commentLimit,
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
						extractLikeComment(tmpJoinId, commentLimit, likedLimit, worldList);
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
						extractLikeComment(tmpJoinId, commentLimit, likedLimit, worldList);
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
	public void buildLatestWorld(final Integer joinId, final int maxId, int start, 
			final int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final int commentLimit, final int likedLimit) throws Exception {
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
						extractLikeComment(commentLimit, likedLimit, worldList);
						userInfoService.extractVerify(worldList);
						userInteractService.extractRemark(joinId, worldList);
						return worldList;
					}

					@Override
					public List<HTWorldInteractDto> getSerializableByMaxId(
							int maxId, RowSelection rowSelection) {
						List<HTWorldInteractDto> worldList = worldDao.queryLatestWorld(joinId, maxId, rowSelection);
						extractLikeComment(commentLimit, likedLimit, worldList);
						return worldList;
					}

					@Override
					public long getTotalByMaxId(int maxId) {
						return worldDao.queryLatestWorldCount(maxId);
					}

				}, OptResult.JSON_KEY_HTWORLD, totalKey);
		
	}
	
	@Override
	public void buildLatestAndUserWorld(final Integer joinId, final int maxId, 
			int start, final int limit, Map<String, Object> jsonMap, boolean trimTotal, 
			final int commentLimit, final int likedLimit) throws Exception {
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
				extractLikeComment(commentLimit, likedLimit, worldList);
				userInfoService.extractVerify(worldList);
				userInteractService.extractRemark(joinId, worldList);
				return worldList;
			}

			@Override
			public List<HTWorldInteractDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<HTWorldInteractDto> worldList = worldDao.queryLatestAndUserWorld(maxId, 
						joinId, beginDate, endDate, rowSelection);
				extractLikeComment(commentLimit, likedLimit, worldList);
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
			throw new HTSException(HTSErrorCode.INVALID_WORLD);
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
			final int commentLimit, final int likedLimit) throws Exception {
		if(StringUtil.checkIsNULL(query))
			throw new HTSException(HTSErrorCode.PARAMATER_ERR, "query can't be null");
		String totalKey = trimTotal ? null : OptResult.JSON_KEY_TOTAL_COUNT;
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<HTWorldInteractDto>() {

			@Override
			public List<HTWorldInteractDto> getSerializables(
					RowSelection rowSelection) {
				List<HTWorldInteractDto> list = worldDao.queryWorldInteractByDesc(userId, query, rowSelection);
				extractLikeComment(commentLimit, likedLimit, list);
				userInfoService.extractVerify(list);
				userInteractService.extractRemark(userId, list);
				return list;
			}

			@Override
			public List<HTWorldInteractDto> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				List<HTWorldInteractDto> list = worldDao.queryWorldInteractByDesc(maxId, userId, query, rowSelection);
				extractLikeComment(commentLimit, likedLimit, list);
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
			throw new HTSException(HTSErrorCode.PARAMATER_ERR);
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
			throw new HTSException(HTSErrorCode.PARAMATER_ERR);
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
	
	@Override
	public void queryLastNHtworldInfoByUserId(Integer userId,
			Integer limit, Map<String, Object> jsonMap)throws Exception{
		if(limit == 4) {
			List<HTWorldThumbDto> worldList =  worldDao.queryLastNHtworldInfoByUserId(userId, limit);
			jsonMap.put(OptResult.JSON_KEY_HTWORLD, worldList);
		} else {
			List<HTWorldInteractDto> worldList = worldDao.queryUserWorld(userId, -1, new RowSelection(1, limit));
			extractLikeComment(2, 0, worldList);
			userInfoService.extractVerify(worldList);
			jsonMap.put(OptResult.JSON_KEY_HTWORLD, worldList);
		}
		
	}

	
}
