package com.hts.web.operations;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.service.ChannelService;
import com.hts.web.operations.service.NearService;
import com.hts.web.operations.service.UserOperationsService;
import com.hts.web.operations.service.ZTWorldOperationsService;
import com.hts.web.stat.StatKey;
import com.hts.web.stat.service.StatService;
import com.hts.web.ztworld.service.ZTWorldLabelService;

/**
 * <p>
 * 织图运营子模块控制器
 * </p>
 * 
 * 创建时间：2013-8-8
 * 
 * @author ztj
 * 
 */
public class ZTWorldOperationsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3538307829445161930L;
	
	private static Logger logger = Logger.getLogger(ZTWorldOperationsAction.class);
	
	private Boolean isRandom = false;  // 是否随机获取
	private Integer squareLabel = Tag.SQUARE_LABEL_TRAVEL; // 广场分类标签代号
	private Integer worldId;
	private Integer activityId;
	
	private Boolean trimLabel = false;
	private Boolean trimNormal = false;
	private Boolean trimActivity = false;
	private Boolean trimCommercial = true; // 过滤商业活动
	private Boolean refreshCount = false;
	private Integer authorLimit = 0;
	private Boolean trimConcernId = true; // 过滤关注人id
	private Boolean trimTutorial = true; // 过滤教程
	private Boolean trimVer0 = true; // 过滤ver=0版本的织图
	private Boolean isRefresh = true;
	private Integer pageCount = 0;
	private Boolean random = true; // 是否随机
		
	private Integer maxSuperbId = 0; // 最大精品id
	private Integer superbStart = 1; // 精品页码
	private Integer superbLimit = 36; // 精品数量
	private Integer typeLimit = 6; // 分类数量
	private Integer activityLimit = 5; // 活动数量
	private Integer commentLimit = 3; // 评论数量
	private Integer likedLimit = 6; // 喜欢数量
	private Integer completeLimit = 3; // 包含评论和喜欢列表的织图数量
	private Integer nextCursor = 0;
	
	private Integer typeId = 0; // 分类id
	
	private Integer channelId; // 频道id
	
	private Double longitude; //纬度
	private Double latitude;//经度
	private String address;//地址
	private Integer labelId;//附近标签id
	private Integer distictId;//下一个区域的id
	

	@Autowired
	private ZTWorldOperationsService worldOperationsService;
	
	@Autowired
	private UserOperationsService userOperationsService;
	
	@Autowired
	private ZTWorldLabelService worldLabelService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private NearService nearService;
	
	@Autowired
	private StatService statService;
	
	public String queryRecommendCity(){
		try{
			statService.incPV(StatKey.NEAR_CITY_REC);
			nearService.buildRecommendCity(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询附近的织图
	 * @return
	 * @author zxx 2015-11-30 15:55:58
	 */
	public String queryNearWorld(){
		try{
			statService.inc2PagePV(StatKey.NRAR_WORLD, maxId, StatKey.NEAR_WORLD_NEXT);
			nearService.buildNearWorld(distictId,address, longitude, latitude, maxId, 
					limit, jsonMap, commentLimit, likedLimit, getCurrentLoginUserId());
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryNearLabelWorld(){
		try{
			statService.incSub2PagePV(StatKey.NEAR_LABEL_WORLD, labelId, maxId, 
					StatKey.NEAR_LABEL_WORLD_NEXT);
			nearService.buildNearLabelWorld(labelId , commentLimit, likedLimit, maxId, 
					limit, jsonMap, getCurrentLoginUserId());
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询附近的标签
	 * @return
	 * @author zxx 2015-12-1 21:00:45
	 */
	public String queryNearLabel(){
		try{
			nearService.buildNearLabel(address, longitude, latitude, maxId, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public String queryNearBanner(){
		try{
			nearService.buildNearBanner(address, longitude, latitude,maxId, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e ){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	/**
	 * 查询广场列表
	 * 
	 * @return
	 */
	public String querySquarePush() {
		try {
			worldOperationsService.buildSquarePush(isRandom, sinceId, maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 随机查询广场列表
	 * @return
	 */
	public String queryRandomSquarePush() {
		try {
			worldOperationsService.buildSquarePush(isRandom, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新广场话题列表
	 * 
	 * @return
	 */
	public String queryLatestSquarePushTopic() {
		try {
			worldOperationsService.buildLatestSquarePushTopic(limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场推送分类索引
	 * @return
	 */
	public String querySquarePushLabelIndex() {
		try {
			worldOperationsService.buildSquarePushLabelIndex(superbLimit, typeLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据标签获取广场推送列表
	 * 
	 * @return
	 */
	public String querySquarePushV2() {
		try {
			worldOperationsService.buildSquarePush(maxId, start, limit, squareLabel, 
					getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场分类
	 * 
	 * @return
	 */
	public String queryTypeSquare() {
		try {
			worldOperationsService.buildTypeSquare(maxId, start, limit, 
					commentLimit, likedLimit, typeId, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询精品广场分类
	 * 
	 * @return
	 */
	public String querySuperbTypeSquare() {
		try {
			worldOperationsService.buildSuperbTypeSquare(maxId, start, limit, 
					commentLimit, likedLimit, completeLimit, trimTutorial,
					trimConcernId,trimVer0, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场精品分类（列表模式）
	 * 
	 * @return
	 */
	public String querySuperbTypeSquareList() {
		try {
			worldOperationsService.buildSuperbTypeSquareList(maxId, start, limit,
					pageCount, isRefresh, commentLimit, likedLimit, completeLimit,
					trimConcernId, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场精品分类列表，无随机
	 * @return
	 */
	public String querySuperbTypeSquareListV2() {
		try {
			statService.incSub2PagePV(StatKey.OP_WORLD_SUPERB, typeId, 
					maxId, StatKey.OP_WORLD_SUPERB_NEXT);
			worldOperationsService.buildSuperbTypeSquareListV2(typeId, maxId, start, limit,
					commentLimit, likedLimit, completeLimit,
					trimConcernId, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询精选列表V3
	 * 
	 * @return
	 */
	public String querySuperbTypeSquareListV3() {
		try {
			worldOperationsService.buildSuperbTypeSquareListV3(getCurrentLoginUserId(), maxId, nextCursor, 
					start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场随机分类列表
	 * 
	 * @return
	 */
	public String queryRandomLabelPush() {
		try {
			worldOperationsService.buildRandomLabelPush(limit, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场索引
	 * v3.0.0弃用
	 * 
	 * @return
	 */
	@Deprecated
	public String querySquarePushIndex() {
		try {
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询分类索引
	 * 
	 * @return
	 */
	public String queryTypeIndex() {
		try {
			worldOperationsService.buildTypeIndex(getCurrentLoginUserId(), limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新活动
	 * 
	 * @return
	 */
	public String queryLatestActivity() {
		try {
			worldOperationsService.buildActivity(activityLimit, getCurrentLoginUserId(), 
					refreshCount, authorLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场活动织图列表
	 * 
	 * @return
	 */
	public String queryActivityWorld() {
		try {
			worldLabelService.buildLabelWorld(false, activityId, getCurrentLoginUserId(), 
					maxId, start, limit, jsonMap, trimTotal, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询活动赞排行
	 * 
	 * @return
	 */
	public String queryActivityLikeRank() {
		try {
			worldOperationsService.buildActivityLikeRank(getCurrentLoginUserId(), activityId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 查询活动获胜者
	 * 
	 * @return
	 */
	public String queryActivityWinner() {
		try {
			worldOperationsService.buildActivityWinner(false, activityId, getCurrentLoginUserId(), 
					maxId, start, limit, jsonMap, trimTotal, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询活动获胜者，根据获奖序号排序
	 * 
	 * @return
	 */
	public String queryActivityWinnerV2() {
		try {
			worldOperationsService.buildActivityWinner(true, activityId, getCurrentLoginUserId(), 
					maxId, start, limit, jsonMap, trimTotal, commentLimit, likedLimit);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新一条商业活动信息
	 * 
	 * @return
	 */
	public String queryMaxCommercialActivity() {
		try {
			Object activity = worldOperationsService.getMaxActivity(maxId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, activity, OptResult.JSON_KEY_ACTIVITY, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新一条普通活动信息
	 * 
	 * @return
	 */
	public String queryMaxNormalActivity() {
		try {
			Object activity = worldOperationsService.getMaxActivity(maxId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, activity, OptResult.JSON_KEY_ACTIVITY, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新商业活动列表
	 * 
	 * @return
	 */
	public String queryLatestCommercialActivity() {
		try {
			Object activity = worldOperationsService.getMaxActivity(maxId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, activity, OptResult.JSON_KEY_ACTIVITY, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询最新商标
	 * 
	 * @return
	 */
	public String queryLatestCommercialLogo() {
		try {
			worldOperationsService.buildCommercialActivityLogo(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询活动规则
	 * 
	 * @return
	 */
	public String queryOpRule() {
		JSONUtil.optSuccess(getText("op.activity_name"), jsonMap);
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场规则
	 * @return
	 */
	public String querySquareRule() {
		Integer userId = getCurrentLoginUserId();
		Integer state = 0;
		try {
			if(userId.equals(-1)) {
				request.setAttribute(OptResult.JSON_KEY_STATE, -1);
			} else {
				state = userOperationsService.getRecommendState(userId);
				request.setAttribute(OptResult.JSON_KEY_STATE, state);
			}
		} catch (Exception e) {
			request.setAttribute(OptResult.JSON_KEY_STATE, -1);
		}
		request.setAttribute("userId", userId);
		return "phone";
	}
	
	public String querySquareRule2() {
		Integer userId = getCurrentLoginUserId();
		Integer state = 0;
		try {
			if(userId.equals(-1)) {
				request.setAttribute(OptResult.JSON_KEY_STATE, -1);
			} else {
				state = userOperationsService.getRecommendState(userId);
				request.setAttribute(OptResult.JSON_KEY_STATE, state);
			}
		} catch (Exception e) {
			request.setAttribute(OptResult.JSON_KEY_STATE, -1);
		}
		request.setAttribute("userId", userId);
		String userAgent = request.getHeader("User-Agent");
		if(userAgent.toLowerCase().contains("android")) {
			return "phone2";
		}
		return "phone3";
	}
	
	public String querySquareRule3() {
		/*
		Integer userId = getCurrentLoginUserId();
		try {
			if(userId.equals(-1)) {
				request.setAttribute(OptResult.JSON_KEY_STATE, -1);
			} else {
				UserVerify verify = userOperationsService.getRecommendStateV2(userId);
				request.setAttribute(OptResult.JSON_KEY_STATE, verify.getId());
				request.setAttribute(OptResult.JSON_KEY_VERIFY_NAME, verify.getVerifyName());
				request.setAttribute(OptResult.JSON_KEY_VERIFY_ICON, verify.getVerifyIcon());
			}
		} catch (Exception e) {
			request.setAttribute(OptResult.JSON_KEY_STATE, -1);
		}
		request.setAttribute("userId", userId);
		*/
		return "phone4";
	}
	
	/**
	 * 查询教程
	 * 
	 * @return
	 */
	public String queryTutorial() {
		try {
			worldOperationsService.buildTutorial(commentLimit, likedLimit, completeLimit, 
					trimConcernId, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道列表
	 * 
	 * @return
	 */
	public String queryChannel() {
		try {
			channelService.buildChannel(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道红人信息
	 * 
	 * @return
	 */
	public String queryMaxChannelTopOne() {
		try {
			channelService.buildMaxChannelTopOne(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道织图列表
	 * 
	 * @return
	 */
	public String queryChannelWorld() {
		try {
			channelService.buildChannelWorld(channelId, getCurrentLoginUserId(),
					maxId, start, limit, commentLimit, likedLimit, jsonMap);;
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道top one列表
	 * 
	 * @return
	 */
	public String queryChannelTopOne() {
		try {
			channelService.buildChannelTopOne(getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Boolean getIsRandom() {
		return isRandom;
	}

	public void setIsRandom(Boolean isRandom) {
		this.isRandom = isRandom;
	}
	
	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getSquareLabel() {
		return squareLabel;
	}

	public void setSquareLabel(Integer squareLabel) {
		this.squareLabel = squareLabel;
	}


	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public Boolean getTrimLabel() {
		return trimLabel;
	}

	public void setTrimLabel(Boolean trimLabel) {
		this.trimLabel = trimLabel;
	}

	public Boolean getTrimNormal() {
		return trimNormal;
	}

	public void setTrimNormal(Boolean trimNormal) {
		this.trimNormal = trimNormal;
	}

	public Boolean getTrimActivity() {
		return trimActivity;
	}

	public void setTrimActivity(Boolean trimActivity) {
		this.trimActivity = trimActivity;
	}

	public Integer getSuperbLimit() {
		return superbLimit;
	}

	public void setSuperbLimit(Integer superbLimit) {
		this.superbLimit = superbLimit;
	}

	public Integer getTypeLimit() {
		return typeLimit;
	}

	public void setTypeLimit(Integer typeLimit) {
		this.typeLimit = typeLimit;
	}

	public Integer getActivityLimit() {
		return activityLimit;
	}

	public void setActivityLimit(Integer activityLimit) {
		this.activityLimit = activityLimit;
	}
	
	public Integer getCommentLimit() {
		return commentLimit;
	}

	public void setCommentLimit(Integer commentLimit) {
		this.commentLimit = commentLimit;
	}

	public Integer getLikedLimit() {
		return likedLimit;
	}

	public void setLikedLimit(Integer likedLimit) {
		this.likedLimit = likedLimit;
	}

	public Integer getCompleteLimit() {
		return completeLimit;
	}

	public void setCompleteLimit(Integer completeLimit) {
		this.completeLimit = completeLimit;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Boolean getTrimCommercial() {
		return trimCommercial;
	}

	public void setTrimCommercial(Boolean trimCommercial) {
		this.trimCommercial = trimCommercial;
	}

	public Integer getSuperbStart() {
		return superbStart;
	}

	public void setSuperbStart(Integer superbStart) {
		this.superbStart = superbStart;
	}

	public Integer getMaxSuperbId() {
		return maxSuperbId;
	}

	public void setMaxSuperbId(Integer maxSuperbId) {
		this.maxSuperbId = maxSuperbId;
	}

	public Boolean getRefreshCount() {
		return refreshCount;
	}

	public void setRefreshCount(Boolean refreshCount) {
		this.refreshCount = refreshCount;
	}

	public Boolean getTrimConcernId() {
		return trimConcernId;
	}

	public void setTrimConcernId(Boolean trimConcernId) {
		this.trimConcernId = trimConcernId;
	}

	public Boolean getTrimTutorial() {
		return trimTutorial;
	}

	public void setTrimTutorial(Boolean trimTutorial) {
		this.trimTutorial = trimTutorial;
	}

	public Boolean getTrimVer0() {
		return trimVer0;
	}

	public void setTrimVer0(Boolean trimVer0) {
		this.trimVer0 = trimVer0;
	}

	public Boolean getIsRefresh() {
		return isRefresh;
	}

	public void setIsRefresh(Boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getAuthorLimit() {
		return authorLimit;
	}

	public void setAuthorLimit(Integer authorLimit) {
		this.authorLimit = authorLimit;
	}

	public Boolean getRandom() {
		return random;
	}

	public void setRandom(Boolean random) {
		this.random = random;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(Integer nextCursor) {
		this.nextCursor = nextCursor;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public Integer getDistictId() {
		return distictId;
	}

	public void setDistictId(Integer distictId) {
		this.distictId = distictId;
	}
	
	
}
