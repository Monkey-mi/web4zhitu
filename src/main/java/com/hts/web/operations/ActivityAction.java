package com.hts.web.operations;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.service.ActivityService;
import com.hts.web.userinfo.service.UserInfoService;

/**
 * <p>
 * 获取活动信息
 * </p>
 * 11-16-2015
 * mishengliang
 *
 */
public class ActivityAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7929884362711339754L;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	private Integer aid;
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}


	public String getActivityPageInfo() {
		try {
			activityService.getactivityPageInfoByAid(aid,jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String isUsePlatformCode() {
		try {
			int isSina = 2;
			boolean isSinaPlatform = userInfoService.isUsePlatformCode(userId,isSina);
			jsonMap.put("isPlatfrom", isSinaPlatform);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}
