package com.hts.web.userinfo;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.BaseAction;
import com.hts.web.common.pojo.UserMsgStatus;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.stat.StatKey;
import com.hts.web.stat.service.StatService;
import com.hts.web.userinfo.service.UserMsgService;

/**
 * <p>
 * 用户消息模块Action控制器
 * </p>
 * 
 * 创建时间：2013-8-28
 * @author ztj
 *
 */
public class UserMsgAction extends BaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2506932172969973833L;
	
	private Integer id;
	private Integer otherId;
	private String content;
	private Boolean isSender;
	private String maxDate;
	private Boolean trimTotal = true; // 过滤总数
	
	private String userIds;
	private String worldIds;
	private Integer likeOtherUID; // 我喜欢对方标记，对方id
	private Integer likeMeUID; // 对方喜欢我标记，对方id
	private Boolean trimUserRecMsg = false;
	private Boolean clearUnCheck = false;
	
	@Autowired
	private UserMsgService userMsgService;
	
	@Autowired
	private StatService statService;
	
	/**
	 * 获取评论/回复消息
	 * 
	 * @return
	 * @author lynch
	 * @deprecated v3.0.5弃用
	 */
	public String queryComment() {
		JSONUtil.optSuccess(jsonMap);
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取喜欢消息
	 * 
	 * @return
	 * @deprecated v3.0.5
	 */
	public String queryLiked() {
		JSONUtil.optSuccess(jsonMap);
		return StrutsKey.JSON;
	}
	
	/**
	 * 构建互动列表
	 * 
	 * @return
	 * @deprecated v3.0.5弃用
	 */
	@Deprecated
	public String queryInteract() {
		JSONUtil.optSuccess(jsonMap);
		return StrutsKey.JSON;
	}

	/**
	 * 获取接收到的私信
	 * 
	 * @return
	 */
	public String queryReceivePrivateMsg() {
		try {
			statService.incPV(StatKey.OP_MSG_SYS);
			userMsgService.buildSysMsg(getCurrentLoginUserId(), maxId, 
					start, limit, trimTotal, trimUserRecMsg, clearUnCheck, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除系统消息
	 * 
	 * @return
	 */
	public String deleteSysMsg() {
		try {
			userMsgService.deleteSysMsg(getCurrentLoginUserId(), id);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 获取未读喜欢总数
	 * 
	 * @return
	 */
	public String queryUnreadMsgCount() {
		try {
			userMsgService.buildUnreadSysMsgCount(getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取未读消息总数V2
	 * 
	 * <p>
	 * 2015-10-15:合并v1,v2
	 * </p>
	 * 
	 * @return
	 * 
	 * @author lynch 2015-10-15
	 */
	public String queryUnreadMsgCountV2() {
		try {
			userMsgService.buildUnreadSysMsgCount(getCurrentLoginUserId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 发送私信
	 * @return
	 */
	public String sendMsg() {
		try {
			Integer uid = getCurrentLoginUserId();
			if(uid.equals(-1)) {
				uid = 0;
			}
			Integer id = userMsgService.saveUserMsg(uid, otherId, content);
			jsonMap.put(OptResult.JSON_KEY_MSG_ID, id);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 发送私信，无需登陆
	 * @return
	 */
	public String sendMsgNoLogin() {
		try {
			Integer id = userMsgService.saveUserMsg(0, otherId, content);
			jsonMap.put(OptResult.JSON_KEY_MSG_ID, id);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询和指定用户的私信列表
	 * @return
	 */
	public String queryMsg() {
		try {
			userMsgService.buildUserMsg(getCurrentLoginUserId(), otherId, maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除私信
	 * 
	 * @return
	 */
	public String deleteMsg() {
		try {
			userMsgService.delUserMsg(id, getCurrentLoginUserId(), otherId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(HTSException e){
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 屏蔽指定用户的私信
	 * 
	 * @return
	 */
	public String shield() {
		try {
			userMsgService.saveShield(getCurrentLoginUserId(), otherId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 解除屏蔽指定用户的私信
	 * 
	 * @return
	 */
	public String unshield() {
		try {
			userMsgService.deleteShield(getCurrentLoginUserId(), otherId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 发送广场规则消息
	 * 
	 * @return
	 */
	public String requestSquareRuleMsg() {
		try {
			userMsgService.pushWelcomeMsg(getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询消息接受状态
	 * 
	 * @return
	 */
	public String queryMsgStatus() {
		try {
			UserMsgStatus status = userMsgService.getMsgStatus(userId, getCurrentLoginUserId());
			JSONUtil.optResult(OptResult.OPT_SUCCESS, status, OptResult.JSON_KEY_STATUS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询对方喜欢我的消息列表
	 * 
	 * @return
	 */
	@Deprecated
	public String queryOtherLikeMeMsg() {
		JSONUtil.optSuccess(jsonMap);
		return StrutsKey.JSON;
	}
	
	/**
	 * v3.0.5弃用
	 * 查询我喜欢对方的消息列表
	 * @return
	 */
	@Deprecated 
	public String queryILikeOtherMsg() {
		JSONUtil.optSuccess(jsonMap);
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询喜欢我的消息列表
	 * 
	 * @return
	 */
	public String queryLikeMeMsg() {
		try {
			userMsgService.buildLikeMeMsg(maxId, getCurrentLoginUserId(), limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		
		return StrutsKey.JSON;
	}

	/**
	 * 查询喜欢我的消息列表（不分组）
	 * 
	 * @return
	 */
	public String queryLikeMeMsgWithoutGroup() {
		try {
			userMsgService.buildLikeMeMsgWithoutGroup(maxId, 
					getCurrentLoginUserId(), limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户和织图缩略图信息
	 * 
	 * @return
	 */
	public String queryThumbnail() {
		try {
			userMsgService.buildThumbnail(userIds, worldIds, getCurrentLoginUserId(), 
					likeOtherUID, likeMeUID, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 时间同步
	 * 
	 * @return
	 */
	public String timeSync() {
		JSONUtil.optSuccess(String.valueOf(new Date().getTime()), jsonMap);
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询AT消息列表
	 * 
	 * @return
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-28
	 */
	public String queryAtMsg() {
		try {
			userMsgService.buildAtMsg(getCurrentLoginUserId(),
					maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询评论消息列表
	 * 
	 * @return
	 * 
	 * @version 3.0.5
	 * @author lynch 2015-09-28
	 */
	public String queryCommentMsg() {
		try {
			userMsgService.buildCommentMsg(getCurrentLoginUserId(),
					maxId, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOtherId() {
		return otherId;
	}

	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Boolean getIsSender() {
		return isSender;
	}

	public void setIsSender(Boolean isSender) {
		this.isSender = isSender;
	}

	public UserMsgService getUserMsgService() {
		return userMsgService;
	}

	public void setUserMsgService(UserMsgService userMsgService) {
		this.userMsgService = userMsgService;
	}
	
	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public Boolean getTrimTotal() {
		return trimTotal;
	}

	public void setTrimTotal(Boolean trimTotal) {
		this.trimTotal = trimTotal;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getWorldIds() {
		return worldIds;
	}

	public void setWorldIds(String worldIds) {
		this.worldIds = worldIds;
	}

	public Integer getLikeOtherUID() {
		return likeOtherUID;
	}

	public void setLikeOtherUID(Integer likeOtherUID) {
		this.likeOtherUID = likeOtherUID;
	}

	public Integer getLikeMeUID() {
		return likeMeUID;
	}

	public void setLikeMeUID(Integer likeMeUID) {
		this.likeMeUID = likeMeUID;
	}

	public Boolean getTrimUserRecMsg() {
		return trimUserRecMsg;
	}

	public void setTrimUserRecMsg(Boolean trimUserRecMsg) {
		this.trimUserRecMsg = trimUserRecMsg;
	}

	public Boolean getClearUnCheck() {
		return clearUnCheck;
	}

	public void setClearUnCheck(Boolean clearUnCheck) {
		this.clearUnCheck = clearUnCheck;
	}
	
}
