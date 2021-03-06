package com.hts.web.operations;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.service.ChannelService;
import com.hts.web.stat.StatKey;
import com.hts.web.stat.service.StatService;

/**
 * <p>
 * 频道控制器
 * </p>
 * 
 * 创建时间: 2015-05-05
 * @author lynch
 *
 */
public class ChannelAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6900044146841031837L;
	
	@Autowired
	private ChannelService channelService;
	
	private Integer channelId;
	private Integer memberLimit = 0;
	private Integer completeLimit = 3; // 包含评论和喜欢列表的织图数量
	private Boolean nameOnly = false;
	private Integer themeId;
	private Integer worldId;
	private Boolean hasChannelStar = false;
	private Integer channelStarLimit = 10;
	private Integer topicId;
	
	@Autowired
	private StatService statService;
	
	/**
	 * 查询已经订阅的频道
	 * 
	 * @return
	 */
	public String querySubscribe() {
		try {
			statService.inc2PagePV(StatKey.CHANNEL_MY_SUBSCRIBE, channelStarLimit, 
					StatKey.CHANNEL_MY_SUBSCRIBE_NEXT);
			channelService.buildSubscribedChannel(nameOnly, userId, maxId,
					start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询热门频道
	 * 
	 * @return
	 */
	public String queryHot() {
		try {
			channelService.buildHot(start, limit, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 订阅频道
	 * 
	 * @return
	 */
	public String subscribe() {
		try {
			statService.incPV(StatKey.CHANNEL_SUBSCRIBE);
			channelService.saveMember(channelId, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 取消订阅
	 * 
	 * @return
	 */
	public String unsubscribe() {
		try {
			channelService.deleteMember(channelId, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道详情
	 * 
	 * @return
	 */
	public String queryDetail() {
		try {
			channelService.buildChannelDetail(channelId, getCurrentLoginUserId(),
					memberLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道摘要
	 * 
	 * @return
	 */
	public String queryAbstract() {
		try {
			channelService.buildChannelAbstract(channelId, getCurrentLoginUserId(),
					hasChannelStar, channelStarLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道织图
	 * 
	 * @return
	 */
	public String queryChannelWorld() {
		try {
			statService.incSub2PagePV(StatKey.CHANNEL_WORLD, channelId, maxId, StatKey.CHANNEL_WORLD_NEXT);
			channelService.buildChannelWorld(channelId, getCurrentLoginUserId(), 
					maxId, start, limit,  commentLimit, likedLimit,
					jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询频道精选织图
	 * 
	 * @return
	 */
	public String querySuperbChannelWorld() {
		try {
			statService.incSub2PagePV(StatKey.CHANNEL_WORLD_SUPERB, channelId, maxId, 
					StatKey.CHANNEL_WORLD_SUPERB_NEXT);
			channelService.buildSuperbChannelWorld(channelId, getCurrentLoginUserId(), 
					maxId, start, limit, commentLimit, likedLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询未审核织图
	 * @return
	 */
	public String queryUnValidChannelWorld() {
		try {
			channelService.buildUnValidChannelWorld(channelId, getCurrentLoginUserId(), 
					maxId, start, limit, commentLimit, likedLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询未审核织图总数
	 * 
	 * @return
	 */
	public String queryUnValidCount() {
		try {
			channelService.buildUnValidWorldCount(channelId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询系统弹幕
	 * 
	 * @return
	 */
	public String querySysDanmu() {
		try {
			channelService.buildSysDanmu(channelId, userId, 
					maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询关联频道
	 * 
	 * @return
	 */
	public String queryLink() {
		try {
			channelService.buildLinkChannel(channelId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询主题频道
	 * 
	 * @return
	 */
	public String queryThemeChannel() {
		try {
			channelService.buildThemeChannel(themeId, maxId, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除织图
	 * 
	 * @return
	 */
	public String deleteWorld()	 {
		try {
			channelService.deleteWorld(channelId, worldId, 
					getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 织图审核通过
	 * 
	 * @return
	 */
	public String acceptWorld() {
		try {
			channelService.updateAcceptWorld(channelId, worldId, 
					getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 织图审核拒绝
	 * 
	 * @return
	 */
	public String rejectWorld() {
		try {
			channelService.updateRejectWorld(channelId, worldId, 
					getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 织图加精
	 * 
	 * @return
	 */
	public String superbWorld()	 {
		try {
			channelService.addWorldSuperb(channelId, worldId,
					getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 取消织图加精
	 * 
	 * @return
	 */
	public String unsuperbWorld() {
		try {
			channelService.deleteWorldSuperb(channelId, worldId,
					getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 获取达人图片推广页面信息
	 * @return 
		*	2015年9月27日(mid-autumn-festival)
		*	mishengliang
	 */
	public String getStarRecommendTopicInfo(){
		try {
			channelService.getStarRecommendTopicInfo(topicId,jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取达人织图推广页面信息
	 * @return 
		*	2015年10月21日
		*	mishengliang
	 */
	public String getStarWorldRecommendTopicInfo(){
		try {
			channelService.getStarWorldRecommendTopicInfo(topicId,jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 利用channelId查询出频道分享页面的所有信息
	 * 包括：
	 * 1.频道banner图
	 * 2.频道描述
	 * 3.频道关注人数
	 * 4.频道参与人数
	 * 5.频道中织图精选数
	 * 6.缩略banner（用于微信分享）
	 * 7.一定量的精选织图封面
	 * 8.织图对应的
	 * @return 
		*	2015年11月7日
		*	mishengliang
	 */
	public String  getChannelSharePageInfo(){
		try{
		channelService.getChannelSharePageInfo(channelId,jsonMap);
		JSONUtil.optSuccess(jsonMap);
	} catch(Exception e) {
		JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
	}
		return StrutsKey.JSON;
	}
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
	public Integer getMemberLimit() {
		return memberLimit;
	}

	public void setMemberLimit(Integer memberLimit) {
		this.memberLimit = memberLimit;
	}

	public Integer getCompleteLimit() {
		return completeLimit;
	}

	public void setCompleteLimit(Integer completeLimit) {
		this.completeLimit = completeLimit;
	}

	public Boolean getNameOnly() {
		return nameOnly;
	}

	public void setNameOnly(Boolean nameOnly) {
		this.nameOnly = nameOnly;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Boolean getHasChannelStar() {
		return hasChannelStar;
	}

	public void setHasChannelStar(Boolean hasChannelStar) {
		this.hasChannelStar = hasChannelStar;
	}

	public Integer getChannelStarLimit() {
		return channelStarLimit;
	}

	public void setChannelStarLimit(Integer channelStarLimit) {
		this.channelStarLimit = channelStarLimit;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	
	
	
}
