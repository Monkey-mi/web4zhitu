package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户推送信息POJO，用于记录用户是否接收推送消息
 * </p>
 * 
 * 创建时间：2013-8-20
 * 
 * @author ztj
 * 
 */
public class UserPushInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8305323103356876692L;

	private Integer id; // 用户id
	private String userName; // 用户名
	private Integer online; // 标记是否在线
	private Integer phoneCode; // 手机平台代号
	private String pushToken; // 推送Token
	private Float ver; // 用户版本号
	private Integer acceptSysPush; // 是否接收系统消息推送
	private Integer acceptCommentPush; // 是否接收评论消息推送
	private Integer acceptReplyPush; // 是否接收回复消息推送
	private Integer acceptLikedPush; // 是否接收喜欢消息推送
	private Integer acceptKeepPush; // 是否接收藏消息推送
	private Integer acceptConcernPush; // 是否接收关注消息推送
	private Integer acceptMsgPush; // 是否接收系统私信消息推送
	private Integer acceptUmsgPush; // 是否接收用户私信推送

	public UserPushInfo() {
		super();
	}

	public UserPushInfo(Integer id, String userName, Integer online,
			Integer phoneCode, String pushToken, Float ver,
			Integer acceptSysPush, Integer acceptCommentPush,
			Integer acceptReplyPush, Integer acceptLikedPush,
			Integer acceptKeepPush, Integer acceptConcernPush,
			Integer acceptMsgPush, Integer acceptUmsgPush) {
		this.id = id;
		this.userName = userName;
		this.online = online;
		this.phoneCode = phoneCode;
		this.pushToken = pushToken;
		this.ver = ver;
		this.acceptSysPush = acceptSysPush;
		this.acceptCommentPush = acceptCommentPush;
		this.acceptReplyPush = acceptReplyPush;
		this.acceptLikedPush = acceptLikedPush;
		this.acceptKeepPush = acceptKeepPush;
		this.acceptConcernPush = acceptConcernPush;
		this.acceptMsgPush = acceptMsgPush;
		this.acceptUmsgPush = acceptUmsgPush;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public Integer getAcceptSysPush() {
		return acceptSysPush;
	}

	public void setAcceptSysPush(Integer acceptSysPush) {
		this.acceptSysPush = acceptSysPush;
	}

	public Integer getAcceptCommentPush() {
		return acceptCommentPush;
	}

	public void setAcceptCommentPush(Integer acceptCommentPush) {
		this.acceptCommentPush = acceptCommentPush;
	}

	public Integer getAcceptReplyPush() {
		return acceptReplyPush;
	}

	public void setAcceptReplyPush(Integer acceptReplyPush) {
		this.acceptReplyPush = acceptReplyPush;
	}

	public Integer getAcceptLikedPush() {
		return acceptLikedPush;
	}

	public void setAcceptLikedPush(Integer acceptLikedPush) {
		this.acceptLikedPush = acceptLikedPush;
	}

	public Integer getAcceptKeepPush() {
		return acceptKeepPush;
	}

	public void setAcceptKeepPush(Integer acceptKeepPush) {
		this.acceptKeepPush = acceptKeepPush;
	}

	public Integer getAcceptConcernPush() {
		return acceptConcernPush;
	}

	public void setAcceptConcernPush(Integer acceptConcernPush) {
		this.acceptConcernPush = acceptConcernPush;
	}

	public Integer getAcceptMsgPush() {
		return acceptMsgPush;
	}

	public void setAcceptMsgPush(Integer acceptMsgPush) {
		this.acceptMsgPush = acceptMsgPush;
	}

	public Integer getAcceptUmsgPush() {
		return acceptUmsgPush;
	}

	public void setAcceptUmsgPush(Integer acceptUmsgPush) {
		this.acceptUmsgPush = acceptUmsgPush;
	}

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
	}
	
}
