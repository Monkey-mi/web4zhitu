package com.hts.web.ztworld;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.LoggerKeies;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.BaseAction;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldDto;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.UserInfoUtil;
import com.hts.web.operations.service.UserOperationsService;
import com.hts.web.ztworld.service.ZTWorldService;
import com.hts.web.ztworld.service.impl.ZTWorldServiceImpl;

/**
 * <p>
 * 织图世界Action控制器
 * </p>
 * 
 * 创建时间：2013-7-5
 * 
 * @author ztj
 * 
 */
public class ZTWorldAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6544742573214995778L;
	
	private static Logger worldLogger = Logger.getLogger(LoggerKeies.WORLD_WORLD);
	private static Logger textLogger = Logger.getLogger(LoggerKeies.WORLD_TEXT);
	
	private Integer id;
	private Integer authorId = 0;
	private String authorName;
	private String authorAvatar;
	private String worldName;
	private String worldDesc;
	private String worldLabel;
	private String worldType;
	private String coverPath;
	private String titlePath;
	private String bgPath;
	private String titleThumbPath;
	private Double longitude;
	private Double latitude;
	private String locationDesc;
	private String locationAddr;
	private String province;
	private String city;
	private Integer size = 0;
	private String childs;
	private Integer phoneCode;
	private Integer titleId;
	private Integer worldId;
	private Integer platformCode;
	private String shareTag;
	private Integer childId;
	private Integer isCheckChildCount;
	private String activityIds;
	
	private Integer sinceId = 0;
	private Integer maxId = 0;
	private Boolean trimUser = true;

	private DateFormat formatter;
	private Boolean isAddClick = false; // 添加播放次数标记位
	private Boolean  isNotAddClick = false; // 不添加播放次数标记位
	private String labelIds;
	private String channelIds;
	private Integer typeId;
	private Integer ver = 0; // 织图版本，默认为1
	private String query; // 查询调价
	private Float logoVer = 0f;
	
	private Long startTime;
	private Long endTime;
	private Integer intervalType;
	private Integer nextCursor;
	
	private String color;
	private Integer mask = Tag.FALSE;
	private Integer recType = 0; // 关注信息流推荐类型
	private Integer recPage = 1; // 关注信息页码
	private String atIds;
	private String atNames;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private UserOperationsService userOptService;

	
	public String getLastNWorldByUserId(){
		
		try{
			String strId = request.getParameter("s");
			int iId = Integer.parseInt(strId);
			int userId = UserInfoUtil.decode(iId);
			List<HTWorldDto> worldList = worldService.queryLastNHtworldInfoByUserId(userId, 9);
			List<String>titlePathList = new ArrayList<String>();
			for(HTWorldDto dto:worldList){
				titlePathList.add(dto.getTitlePath());
			}
			jsonMap.put("myWorldList", titlePathList);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			jsonMap.put(OptResult.RESULT, OptResult.OPT_FAILED);			
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 检查织图是否存在
	 * 
	 * @return
	 */
	public String checkExists() {
		boolean isAdmin = false;
		String admin = request.getParameter("adminKey");
		if(!StringUtil.checkIsNULL(admin) && admin.equals(ZTWorldServiceImpl.ADMIN_PASS)) {
			isAdmin = true;
			request.setAttribute("adminKey", ZTWorldServiceImpl.ADMIN_PASS);
		}
		String url = request.getServletPath();
		HTWorldDto worldDto = null;
		try {
			worldDto = worldService.getHTWorldDtoFromURL(url, isAdmin);
		} catch (Exception e) {
			return ERROR;
		}
		
		if(worldDto != null) {
			if (this.formatter == null)
		        this.formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String userAgent = request.getHeader("User-Agent");
			String page = ZTWorldServiceImpl.switchForwardByUserAgent(userAgent);
			request.setAttribute("commentCount", worldDto.getCommentCount());
			request.setAttribute("authorId", worldDto.getAuthorId());
			request.setAttribute("dateAdded", formatter.format(worldDto.getDateAdded()));
			request.setAttribute("likeCount", worldDto.getLikeCount());
			request.setAttribute("clickCount", worldDto.getClickCount());
			request.setAttribute("worldId", worldDto.getId());
			request.setAttribute("latitude", worldDto.getLatitude());
			request.setAttribute("longitude", worldDto.getLongitude());
			request.setAttribute("locationDesc", worldDto.getLocationDesc());
			request.setAttribute("childCount", worldDto.getChildCount());
			String authorName = worldDto.getUserInfo().getUserName();
			if(StringUtil.checkIsNULL(authorName)) {
				authorName = "织图用户";
				request.setAttribute("authorName", "织图用户");
			}
			request.setAttribute("authorName", authorName);
			String title = worldDto.getWorldDesc();
			if(StringUtil.checkIsNULL(title)) {
				title = authorName + "的织图";
			}
			request.setAttribute("title", title);
			request.setAttribute("worldDesc", worldDto.getWorldDesc());
			request.setAttribute("authorAvatar", worldDto.getUserInfo().getUserAvatar());
			request.setAttribute("coverPath", worldDto.getCoverPath());
			request.setAttribute("titlePath", worldDto.getTitlePath());
			request.setAttribute("worldURL", worldDto.getWorldURL());
			request.setAttribute("worldLabel", worldDto.getWorldLabel());
			request.setAttribute("ver", worldDto.getVer());
			return page;
		}
		return ERROR;
	}
	
	
	
	/**
	 * 分享织图
	 * 
	 * @return
	 */
	public String shareWorld() {
		try {
			worldService.saveWorld(childs, titleId, phoneCode,
					id, getCurrentLoginUserId(), worldName,worldDesc, worldLabel, 
					labelIds, worldType, typeId, coverPath, titlePath, bgPath, titleThumbPath, 
					longitude, latitude, locationDesc, locationAddr, province, 
					city, size, activityIds, ver, channelIds, Tag.WORLD_TYPE_DEFAULT, color, mask,
					atIds, atNames, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed2(getCurrentLoginUserId(), e, jsonMap, worldLogger);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 运营马甲分享接口
	 * 
	 * @return
	 */
	public String shareWorldForOpt() {
		try {
			Integer uid = userOptService.getRandomZombieId();
			if(uid == null || uid == 0) {
				uid = getCurrentLoginUserId();
			}
			worldService.saveWorld(childs, titleId, phoneCode,
					id, uid, worldName,worldDesc, worldLabel, 
					labelIds, worldType, typeId, coverPath, titlePath, bgPath, titleThumbPath, 
					longitude, latitude, locationDesc, locationAddr, province, 
					city, size, activityIds, ver, channelIds, Tag.WORLD_TYPE_DEFAULT, color, mask,
					atIds, atNames, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed2(getCurrentLoginUserId(), e, jsonMap, worldLogger);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分享文本
	 * 
	 * @return
	 */
	public String shareText() {
		try {
			worldService.saveWorld(null, 0, phoneCode,
					id, getCurrentLoginUserId(), worldName,worldDesc, worldLabel, 
					labelIds, worldType, typeId, coverPath, titlePath, bgPath, titleThumbPath, 
					longitude, latitude, locationDesc, locationAddr, province, 
					city, size, activityIds, ver, channelIds, Tag.WORLD_TYPE_TEXT, color, mask,
					atIds, atNames, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed2(getCurrentLoginUserId(), e, jsonMap, textLogger);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 运营马甲分享接口
	 * 
	 * @return
	 */
	public String shareTextForOpt() {
		try {
			Integer uid = userOptService.getRandomZombieId();
			if(uid == null || uid == 0) {
				uid = getCurrentLoginUserId();
			}
			worldService.saveWorld(null, 0, phoneCode,
					id, uid, worldName,worldDesc, worldLabel, 
					labelIds, worldType, typeId, coverPath, titlePath, bgPath, titleThumbPath, 
					longitude, latitude, locationDesc, locationAddr, province, 
					city, size, activityIds, ver, channelIds, Tag.WORLD_TYPE_TEXT, color, mask,
					atIds, atNames, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed2(getCurrentLoginUserId(), e, jsonMap, textLogger);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分享织图，无需登录（内部接口，不对外）
	 * @return
	 */
	public String shareWorldDirect() {
		try {
			worldService.saveWorld(childs, titleId, phoneCode,
					id, authorId, worldName,worldDesc, worldLabel, 
					labelIds, worldType, typeId, coverPath, titlePath, bgPath, titleThumbPath, 
					longitude, latitude, locationDesc, locationAddr, province, 
					city, size, activityIds, ver, channelIds, Tag.WORLD_TYPE_DEFAULT, color, mask,
					atIds, atNames, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed2(getCurrentLoginUserId(), e, jsonMap, worldLogger);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 删除织图
	 * 
	 * @return
	 */
	public String deleteWorld() {
		try {
			worldService.deleteWorld(worldId, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询世界信息
	 * @return
	 */
	public String queryWorld() {
		try {
			if(!trimUser) {
				HTWorldDto dto = worldService.getHTWorldDtoById(worldId, isAddClick);
				JSONUtil.optResult(OptResult.OPT_SUCCESS, dto, OptResult.JSON_KEY_HTWORLD, jsonMap);
			} else {
				HTWorld world = worldService.getWorldById(worldId, isAddClick);
				JSONUtil.optResult(OptResult.OPT_SUCCESS, world, OptResult.JSON_KEY_HTWORLD, jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 搜索织图
	 * 
	 * @return
	 */
	public String searchWorld() {
		try {
			worldService.buildHTWorld(query, getCurrentLoginUserId(), maxId, start, limit,
					jsonMap, trimTotal, trimExtras, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询织图和第一页子世界信息,用于web访问
	 * 
	 * @return
	 */
	public String queryWorldAndTitle() {
		boolean isAdmin = false;
		String admin = request.getParameter("adminKey");
		if(!StringUtil.checkIsNULL(admin) && admin.equals(ZTWorldServiceImpl.ADMIN_PASS)) {
			isAdmin = true;
		}
		try {
			worldService.buildWorldWithTitleChild(worldId, limit, isAdmin, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询封面子世界所在分页的子世界信息
	 * 
	 * @return
	 */
	public String queryTitleChildWorldPage() {
		try {
			boolean isAdmin = false;
			String admin = request.getParameter("adminKey");
			if(!StringUtil.checkIsNULL(admin) && admin.equals(ZTWorldServiceImpl.ADMIN_PASS)) {
				isAdmin = true;
			}
			JSONObject json = worldService.getTitleChildPageInfo(worldId, limit, isNotAddClick, isAdmin);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, json, OptResult.JSON_KEY_HTWORLD, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询指定子世界所在分页的子世界信息
	 * @return
	 */
	public String queryChildWorldPage() {
		try {
			JSONObject json = worldService.getChildWorldPageInfoById(worldId, childId, limit);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, json, OptResult.JSON_KEY_HTWORLD, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分页查询指定用户及其关注好友的织图
	 * 
	 * @return
	 */
	public String queryConcernWorld() {
		try {
			worldService.buildConcernWorld(recType, recPage, getCurrentLoginUserId(), 
					maxId, start, limit, jsonMap, trimTotal, trimExtras, commentLimit,
					likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分页查询指定用户收藏的织图
	 * 
	 * @return
	 */
	public String queryKeepWorld() {
		try {
			worldService.buildKeepWorld(getCurrentLoginUserId(), maxId, start, limit, jsonMap, 
					trimTotal, trimExtras, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分页查询指定用户喜欢的织图
	 * 
	 * @return
	 */
	public String queryLikedWorld() {
		return StrutsKey.JSON;
	}
	
	/**
	 * 分页查询指定用户的织图
	 * 
	 * @return
	 */
	public String queryMyWorld() {
		try {
			worldService.buildUserWorld(userId, getCurrentLoginUserId(), maxId, start, limit, jsonMap,
					trimTotal, trimExtras, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 随机查询指定用户的织图信息
	 * 
	 * @return
	 */
	public String queryUserRandomWorld() {
		try {
			worldService.buildRandomUserWorld(userId, getCurrentLoginUserId(), limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新织图
	 * @return
	 */
	public String queryLatestWorld() {
		try {
			worldService.buildLatestWorld(limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新织图，分页
	 * 
	 * @return
	 */
	public String queryLatestWorldV2() {
		try {
			worldService.buildLatestWorld(getCurrentLoginUserId(), maxId, start, limit, jsonMap,
			trimTotal, trimExtras, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新，同时查询用户发出但未审核通过的织图
	 * @return
	 */
	public String queryLatestWorldV3() {
		try {
			worldService.buildLatestAndUserWorld(getCurrentLoginUserId(), maxId, start, limit, jsonMap,
			trimTotal, trimExtras, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新子世界类型
	 * 
	 * @return
	 */
	public String queryLatestChildType() {
		try {
			worldService.buildLatestChildType(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加子世界类型使用次数
	 * 
	 * @return
	 */
	public String addChildTypeUseCount() {
		try {
			worldService.addChildTypeUseCount(id);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询滤镜logo
	 * @return
	 */
	public String queryFilterLogo() {
		try {
			worldService.buildFilterLogo(logoVer, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新索引列表
	 * 
	 * @return
	 */
	public String queryLatestIndex() {
		try {
			worldService.buildLatestIndex(getCurrentLoginUserId(), startTime, intervalType, nextCursor, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新
	 * 
	 * @return
	 */
	public String queryLatest()	 {
		try {
			worldService.buildLatest(getCurrentLoginUserId(), startTime, endTime, maxId, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 查询最新索引列表2
	 * 
	 * @return
	 */
	public String queryLatestIndex2() {
		try {
			worldService.buildLatestIndex2(getCurrentLoginUserId(), startTime, intervalType, nextCursor, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新2
	 * 
	 * @return
	 */
	public String queryLatest2()	 {
		try {
			worldService.buildLatest2(getCurrentLoginUserId(), startTime, endTime, maxId, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询织图所有子世界
	 * 
	 * @return
	 */
	public String queryAllChild() {
		try {
			worldService.buildAllChild(worldId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新id
	 * 
	 * @return
	 */
	public String queryLatestId() {
		try {
			worldService.buildLatestId(maxId, minId, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public ZTWorldService getWorldService() {
		return worldService;
	}

	public void setWorldService(ZTWorldService worldService) {
		this.worldService = worldService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorAvatar() {
		return authorAvatar;
	}

	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public String getWorldDesc() {
		return worldDesc;
	}

	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}

	public String getWorldLabel() {
		return worldLabel;
	}

	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getChilds() {
		return childs;
	}

	public void setChilds(String childs) {
		this.childs = childs;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(Integer platformCode) {
		this.platformCode = platformCode;
	}

	public String getShareTag() {
		return shareTag;
	}

	public void setShareTag(String shareTag) {
		this.shareTag = shareTag;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public Integer getIsCheckChildCount() {
		return isCheckChildCount;
	}

	public void setIsCheckChildCount(Integer isCheckChildCount) {
		this.isCheckChildCount = isCheckChildCount;
	}

	public Integer getSinceId() {
		return sinceId;
	}

	public void setSinceId(Integer sinceId) {
		this.sinceId = sinceId;
	}

	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

	public Boolean getTrimUser() {
		return trimUser;
	}

	public void setTrimUser(Boolean trimUser) {
		this.trimUser = trimUser;
	}

	public String getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(String activityIds) {
		this.activityIds = activityIds;
	}

	public Boolean getIsAddClick() {
		return isAddClick;
	}

	public void setIsAddClick(Boolean isAddClick) {
		this.isAddClick = isAddClick;
	}

	public Boolean getIsNotAddClick() {
		return isNotAddClick;
	}

	public void setIsNotAddClick(Boolean isNotAddClick) {
		this.isNotAddClick = isNotAddClick;
	}

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Float getLogoVer() {
		return logoVer;
	}

	public void setLogoVer(Float logoVer) {
		this.logoVer = logoVer;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getIntervalType() {
		return intervalType;
	}

	public void setIntervalType(Integer intervalType) {
		this.intervalType = intervalType;
	}

	public Integer getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(Integer nextCursor) {
		this.nextCursor = nextCursor;
	}

	public String getBgPath() {
		return bgPath;
	}

	public void setBgPath(String bgPath) {
		this.bgPath = bgPath;
	}

	public String getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getMask() {
		return mask;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public Integer getRecType() {
		return recType;
	}

	public void setRecType(Integer recType) {
		this.recType = recType;
	}

	public Integer getRecPage() {
		return recPage;
	}

	public void setRecPage(Integer recPage) {
		this.recPage = recPage;
	}

	public String getAtIds() {
		return atIds;
	}

	public void setAtIds(String atIds) {
		this.atIds = atIds;
	}

	public String getAtNames() {
		return atNames;
	}

	public void setAtNames(String atNames) {
		this.atNames = atNames;
	}
	
	
}
