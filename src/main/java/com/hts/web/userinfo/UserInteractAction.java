package com.hts.web.userinfo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.Log;
import com.hts.web.userinfo.service.UserInteractService;

/**
 * <p>
 * 用户互动子模块Action控制器
 * </p>
 * 
 * 创建时间：2013-7-2
 * @author ztj
 *
 */
public class UserInteractAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3322899338081666045L;
	
	private Integer concernId; //关注的用户id
	private String concernIds; //关注的用户id列表
	private String userName; // 用户名
	private Integer typeId; // 织图分类id
	private String loginCodes;
	private Integer total = 0;
	private Boolean im = false;
	private Integer remarkId;
	private String remark;
	private Boolean clearUnCheck = true;
	private Integer followId;
	
	@Autowired
	private UserInteractService userInteractService;
	
	/**
	 * 分页查询用户关注
	 * 
	 * @return
	 */
	public String queryConcerns() {
		try {
			userInteractService.buildConcerns(userId, getCurrentLoginUserId(),
					sinceId, maxId, start, limit, total, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据名字分页查询用户关注
	 * 
	 * @return
	 */
	public String queryConcernByName() {
		try {
			userInteractService.buildConcerns(userName, getCurrentLoginUserId(), maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分页查询粉丝
	 * 
	 * @return
	 */
	public String queryFollows() {
		try {
			userInteractService.buildFollows(clearUnCheck, userId, getCurrentLoginUserId(),
					sinceId, maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 清空未读粉丝
	 * 
	 * @return
	 */
	public String clearUnCheckFollow() {
		try {
			userInteractService.updateUnCheckFollow(getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 关注某个用户
	 * @return
	 */
	public String concern() {
		try {
			PushStatus status = userInteractService.saveConcern(im, getCurrentLoginUserId(), concernId);
			if(!im)
				JSONUtil.optResult(OptResult.OPT_SUCCESS, status.getIsMututal(), 
						OptResult.JSON_KEY_IS_MUTUTAL, jsonMap);
			else {
				jsonMap.put(OptResult.JSON_KEY_PHONE, status.getPhone());
				jsonMap.put(OptResult.JSON_KEY_ACCEPT, status.getAccept());
				jsonMap.put(OptResult.JSON_KEY_SHIELD, status.getShield());
				jsonMap.put(OptResult.JSON_KEY_IS_MUTUTAL, status.getIsMututal());
				jsonMap.put(OptResult.JSON_KEY_USER_ID, status.getUserId());
				jsonMap.put(OptResult.JSON_KEY_REMARK_ME, status.getRemarkMe());
				JSONUtil.optSuccess(jsonMap);
			}
		} catch (HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 取消关注某个用户
	 * @return
	 */
	public String cancelConcern() {
		try {
			userInteractService.cancelConcern(getCurrentLoginUserId(), concernId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(HTSException e) { 
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量关注
	 * @return
	 */
	public String batchConcern() {
		try {
			List<PushStatus> results = userInteractService.batchSaveConcern(im, getCurrentLoginUserId(),
					concernIds);
			if(!im)
				JSONUtil.optSuccess(String.valueOf(results.size()), jsonMap);
			else 
				JSONUtil.optResult(OptResult.OPT_SUCCESS, results, 
						OptResult.JSON_KEY_CONCERNS, jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 取消关注某个用户
	 * @return
	 */
	public String batchCancelConcern() {
		try {
			int count = userInteractService.batchCancelConcern(getCurrentLoginUserId(), concernIds);
			JSONUtil.optSuccess(String.valueOf(count), jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询推荐用户
	 * 
	 * @return
	 */
	public String queryRecommend() {
		try {
			userInteractService.buildFollows(clearUnCheck, 265,0, 0, 0, start, 10, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryFriendCount() {
		return StrutsKey.JSON;
	}
	
	/**
	 * 搜索用户
	 * 
	 * @return
	 */
	public String search() {
		try {
			userInteractService.buildUserSearchInfo(userName, getCurrentLoginUserId(), minId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 订阅分类
	 * 
	 * @return
	 */
	public String concernType() {
		try {
			userInteractService.saveConcernType(getCurrentLoginUserId(), typeId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 订阅分类
	 * 
	 * @return
	 */
	public String cancelConcernType() {
		try {
			userInteractService.cancelConcernType(getCurrentLoginUserId(), typeId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	

	/**
	 * 查询未关注用户列表
	 * @return
	 */
	public String queryNotConcernByLoginCodes() {
		try {
			userInteractService.buildNotConcern(getCurrentLoginUserId(), loginCodes, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询已经注册的用户列表
	 * 
	 * @return
	 */
	public String queryRegisterByLoginCodes() {
		try {
			userInteractService.buildRegister(getCurrentLoginUserId(), loginCodes, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询已屏蔽的用户
	 * 
	 * @return
	 */
	public String queryShield() {
		try {
			userInteractService.buildShieldUser(getCurrentLoginUserId(), 
					maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 屏蔽用户
	 * 
	 * @return
	 */
	public String shield() {
		try {
			userInteractService.saveShield(getCurrentLoginUserId(), userId);
			JSONUtil.optSuccess(jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 解除用户屏蔽
	 * 
	 * @return
	 */
	public String unshield() {
		try {
			userInteractService.deleteShield(getCurrentLoginUserId(), userId);
			JSONUtil.optSuccess(jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 举报用户
	 * 
	 * @return
	 */
	public String report() {
		try {
			userInteractService.saveReport(getCurrentLoginUserId(), userId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新备注
	 * 
	 * @return
	 */
	public String updateRemark() {
		try {
			userInteractService.updateRemark(getCurrentLoginUserId(), remarkId, remark);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除备注
	 * 
	 * @return
	 */
	public String deleteRemark() {
		try {
			userInteractService.deleteRemark(getCurrentLoginUserId(), remarkId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询未读粉丝列表
	 * 
	 * @return
	 */
	public String queryNewFollow() {
		try {
			userInteractService.buildNewFollow(clearUnCheck, getCurrentLoginUserId(), 
					maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 清空未读
	 * @return
	 */
	public String clearNewFollow() {
		try {
			userInteractService.updateNewFollow(getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除用户粉丝
	 * 
	 * @return
	 */
	public String deleteFollow() {
		try {
			userInteractService.deleteFollow(getCurrentLoginUserId(), followId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public UserInteractService getUserInteractService() {
		return userInteractService;
	}

	public void setUserInteractService(UserInteractService userInteractService) {
		this.userInteractService = userInteractService;
	}

	public Integer getConcernId() {
		return concernId;
	}

	public void setConcernId(Integer concernId) {
		this.concernId = concernId;
	}

	public String getConcernIds() {
		return concernIds;
	}

	public void setConcernIds(String concernIds) {
		this.concernIds = concernIds;
	}

	public Integer getLimit() {
		return limit;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getLoginCodes() {
		return loginCodes;
	}

	public void setLoginCodes(String loginCodes) {
		this.loginCodes = loginCodes;
	}

	public Boolean getIm() {
		return im;
	}

	public void setIm(Boolean im) {
		this.im = im;
	}

	public Integer getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Integer remarkId) {
		this.remarkId = remarkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getClearUnCheck() {
		return clearUnCheck;
	}

	public void setClearUnCheck(Boolean clearUnCheck) {
		this.clearUnCheck = clearUnCheck;
	}

	public Integer getFollowId() {
		return followId;
	}

	public void setFollowId(Integer followId) {
		this.followId = followId;
	}
	
}
