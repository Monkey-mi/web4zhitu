package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 用户私信对话数据访问接口
 * 
 * @author lynch
 *
 */
public class UserMsgConver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8357831455116754091L;
	private Integer userId;
	private Integer otherId;
	private Integer contentId;
	private Integer unreadCount;
	private Integer msgType;

	public UserMsgConver() {
		super();
	}

	public UserMsgConver(Integer userId, Integer otherId, Integer contentId, 
			Integer unreadCount, Integer msgType) {
		super();
		this.userId = userId;
		this.otherId = otherId;
		this.contentId = contentId;
		this.unreadCount = unreadCount;
		this.msgType = msgType;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOtherId() {
		return otherId;
	}

	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}

	public Integer getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

}
