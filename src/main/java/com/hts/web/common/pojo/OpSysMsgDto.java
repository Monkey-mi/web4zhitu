package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

public class OpSysMsgDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3691368561321495623L;
	private Integer id;
	private Integer recipientId;
	private Date msgDate;
	private String content;
	private Integer objType;
	private Integer objId = 0;
	private String objMeta;
	private String objMeta2;
	private String thumbPath;
	private Integer isNew = Tag.FALSE;
	
	
	public OpSysMsgDto() {
		super();
	}

	public OpSysMsgDto(Integer id, Integer recipientId,
			Date msgDate, String content, Integer objType, Integer objId,
			String objMeta, String objMeta2, String thumbPath) {
		super();
		this.id = id;
//		this.senderId = senderId;
		this.recipientId = recipientId;
		this.msgDate = msgDate;
		this.content = content;
		this.objType = objType;
		this.objId = objId;
		this.objMeta = objMeta;
		this.objMeta2 = objMeta2;
		this.thumbPath = thumbPath;
		this.isNew = isNew;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Integer getSenderId() {
//		return senderId;
//	}
//
//	public void setSenderId(Integer senderId) {
//		this.senderId = senderId;
//	}

	public Integer getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getObjMeta() {
		return objMeta;
	}

	public void setObjMeta(String objMeta) {
		this.objMeta = objMeta;
	}
	
	public String getObjMeta2() {
		return objMeta2;
	}

	public void setObjMeta2(String objMeta2) {
		this.objMeta2 = objMeta2;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	
	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

//	public UserInfoDto getSenderInfo() {
//		return senderInfo;
//	}
//
//	public void setSenderInfo(UserInfoDto senderInfo) {
//		this.senderInfo = senderInfo;
//	}
//
//	public UserInfoDto getRecipientInfo() {
//		return recipientInfo;
//	}
//
//	public void setRecipientInfo(UserInfoDto recipientInfo) {
//		this.recipientInfo = recipientInfo;
//	}
	
}
