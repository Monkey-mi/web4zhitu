package com.hts.web.operations;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.service.ChannelService;

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
	
	/**
	 * 查询已经订阅的频道
	 * 
	 * @return
	 */
	public String querySubscribe() {
		try {
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
			channelService.buildChannelAbstract(channelId, 
					getCurrentLoginUserId(), jsonMap);
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
			channelService.buildChannelWorld(channelId, getCurrentLoginUserId(), 
					maxId, start, limit, trimExtras,  commentLimit, likedLimit,
					completeLimit, jsonMap);
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
			channelService.buildSuperbChannelWorld(channelId, getCurrentLoginUserId(), 
					maxId, start, limit, trimExtras,  commentLimit, likedLimit,
					completeLimit, jsonMap);
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
			channelService.buildThemeChannel(themeId, maxId, start, limit, jsonMap);
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
	
}
