package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class UserMsgDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5798499401466802711L;

	private Integer id;
	private Integer senderId;
	private Integer recipientId;
	private Date msgDate;
	private String content;

	private UserAvatar senderInfo; // 发送者信息
	private UserAvatar recipientInfo; // 接受者信息

	public UserMsgDto() {
		super();
	}

	public UserMsgDto(Integer id, Integer senderId, Integer recipientId,
			Date msgDate, String content) {
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.msgDate = msgDate;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}


	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserAvatar getSenderInfo() {
		return senderInfo;
	}

	public void setSenderInfo(UserAvatar senderInfo) {
		this.senderInfo = senderInfo;
	}

	public UserAvatar getRecipientInfo() {
		return recipientInfo;
	}

	public void setRecipientInfo(UserAvatar recipientInfo) {
		this.recipientInfo = recipientInfo;
	}

}
