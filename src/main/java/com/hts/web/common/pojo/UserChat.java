package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 私信聊天POJO对象
 * </p>
 * 
 * 创建时间：2013-11-27
 * @author ztj
 *
 */
public class UserChat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -969894350291926299L;
	private Integer senderId; // 发送者id
	private Integer recipientId; // 接收者id
	private Integer chatId;
	private Integer isMutual; 
	private Integer valid;
	private Integer shield;
	
	public UserChat() {
		super();
	}

	public UserChat(Integer senderId, Integer recipientId, Integer chatId, Integer isMutual,
			Integer valid, Integer shield) {
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.chatId = chatId;
		this.isMutual = isMutual;
		this.valid = valid;
		this.shield = shield;
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

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}
	

	public Integer getIsMutual() {
		return isMutual;
	}

	public void setIsMutual(Integer isMutual) {
		this.isMutual = isMutual;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}
	
	
	
}
