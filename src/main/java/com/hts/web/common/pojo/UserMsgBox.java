package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 私信收件/发件箱POJO
 * </p>
 * 
 * @author ztj 2013-11-28 2015-10-25
 *
 */
public class UserMsgBox implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -779119487155559778L;

	private Integer contentId;
	private Integer senderId;
	private Integer recipientId;

	public UserMsgBox() {
		super();
	}
	
	public UserMsgBox(Integer contentId, Integer senderId, Integer recipientId) {
		super();
		this.contentId = contentId;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}



	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
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

}
