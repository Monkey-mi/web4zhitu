package com.hts.web.operations;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.operations.service.OpStarRecommendService;
import com.hts.web.operations.service.UserOperationsService;
import com.hts.web.ztworld.service.impl.ZTWorldServiceImpl;

/**
 * <p>
 * 用户运营子模块控制器
 * </p>
 * 
 * 创建时间：2013-8-8
 * 
 * @author ztj
 * 
 */
public class UserOperationsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2494042772822842790L;
	
	private Integer worldLimit = 0;
	private Boolean trimMe = true;
	private Boolean accepted = false;
	private Integer verifyId = 1;
	private Boolean trimSelf = false;
	
	@Autowired
	private UserOperationsService userOperationsService;
	
	@Autowired
	private OpStarRecommendService starRecommendService;
	
	
	
	/**
	 * 查询推荐用户
	 * 
	 * @return
	 */
	public String queryRecommendUser() {
		try {
			userOperationsService.buildUserRecommend(maxId, start, limit, getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询平台推荐用户
	 * 
	 * @return
	 */
	public String queryPlatformRecommendUser() {
		try {
			userOperationsService.buildPlatformUserRecomend(getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 申请成为推荐用户
	 * 
	 * @return
	 */
	public String applyRecommend() {
		try {
			userOperationsService.saveOrUpdateRecommend(userId, verifyId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(HTSException e){ 
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新推荐状态
	 * 
	 * @return
	 */
	public String updateRecommend() {
		try {
			userOperationsService.updateRecommendUserAccept(getCurrentLoginUserId(), accepted);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询推荐状态
	 * 
	 * @return
	 */
	public String queryRecommend() {
		Integer userId = getCurrentLoginUserId();
		Integer state = 0;
		try {
			if(userId.equals(-1)) {
				state = -1;
			} else {
				state = userOperationsService.getRecommendState(userId);
			}
			JSONUtil.optResult(OptResult.OPT_SUCCESS, state, OptResult.JSON_KEY_STATE, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 邀请用户
	 * @return
	 */
	public String invite() {
		String userAgent = request.getHeader("User-Agent");
		if(null == userAgent) return "phone";//若没有user-agent，则默认返回手机页面
		String page = ZTWorldServiceImpl.switchForwardByUserAgent(userAgent);
		return page;
	}
	
	/**
	 * 查询广场推荐用户
	 * 
	 * @return
	 */
	public String querySquareRecommend() {
		try {
			userOperationsService.buildSquareRecommendUser(maxId, start, limit, getCurrentLoginUserId(), worldLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场推荐用户V2，根据activity排序
	 * 
	 * @return
	 */
	public String querySquareRecommendV2() {
		try {
			userOperationsService.buildSquareRecommendUserV2(maxId, start, limit, 
					getCurrentLoginUserId(), worldLimit, trimSelf, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
//	/**
//	 * 查询标签推荐用户  用下面个方法代替
//	 * @return
//	 */
//	public String queryLabelRecommendUser()	 {
//		try {
//			userOperationsService.buildLabelRecommendUser(maxId, start, limit, 
//					worldLimit, getCurrentLoginUserId(), trimMe, jsonMap);
//			JSONUtil.optSuccess(jsonMap);
//		} catch (Exception e) {
//			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
//		}
//		return StrutsKey.JSON;
//	}
	
	/**
	 * 查询达人推荐
	 */
	public String queryLabelRecommendUser(){
		try{
			starRecommendService.queryStarRecommend(maxId,start,jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), OptResult.QUERY_FAILED, e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询认证列表索引
	 * 
	 * @return
	 */
	public String queryVerifyIndex() {
		try {
			userOperationsService.buildVerifyIndex(getCurrentLoginUserId(), limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}	
	
	/**
	 * 查询认证列表
	 * 
	 * @return
	 */
	public String queryVerifyRecommendUser() {
		try {
			userOperationsService.buildVerifyRecommendUser(maxId, start, limit, getCurrentLoginUserId(),
					verifyId, worldLimit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public String queryVerify() {
		try {
			userOperationsService.buildVerify(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 二维码下载
	 * @return
	 */
	public String qrdownload() {
		String userAgent = request.getHeader("User-Agent");
		if(userAgent.toLowerCase().contains("android")) {
			return "android";
		}
		return "iphone";
	}
	
	public Integer getWorldLimit() {
		return worldLimit;
	}

	public void setWorldLimit(Integer worldLimit) {
		this.worldLimit = worldLimit;
	}

	public Boolean getTrimMe() {
		return trimMe;
	}

	public void setTrimMe(Boolean trimMe) {
		this.trimMe = trimMe;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public Integer getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
	}

	public Boolean getTrimSelf() {
		return trimSelf;
	}

	public void setTrimSelf(Boolean trimSelf) {
		this.trimSelf = trimSelf;
	}
	
}
