package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 私信收件/发件箱POJO
 * </p>
 * 
 * 创建时间：2013-11-28
 * @author ztj
 *
 */
public class UserMsgBox implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -779119487155559778L;
	
	private Integer id;
	private Integer senderId;
	private Integer recipientId;
	private Integer contentId;
	private Integer ck;
	private Integer chatValid;
	
	public UserMsgBox() {
		super();
	}

	public UserMsgBox(Integer id, Integer senderId, Integer recipientId,
			Integer contentId, Integer ck, Integer chatValid) {
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.contentId = contentId;
		this.ck = ck;
		this.chatValid = chatValid;
	}
	
	public UserMsgBox(Integer id, Integer senderId, Integer recipientId,
			Integer contentId) {
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.contentId = contentId;
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

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getCk() {
		return ck;
	}

	public void setCk(Integer ck) {
		this.ck = ck;
	}

	public Integer getChatValid() {
		return chatValid;
	}

	public void setChatValid(Integer chatValid) {
		this.chatValid = chatValid;
	}
	
	
	

}
