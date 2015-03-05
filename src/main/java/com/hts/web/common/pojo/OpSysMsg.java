package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class OpSysMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9035556566597253665L;

	private Integer id;
	private Integer senderId;
	private Integer recipientId;
	private Date msgDate;
	private String content;
	private Integer objType;
	private Integer objId = 0;
	private String objMeta;
	private String objMeta2;
	private String thumbPath;
	private Integer ck;
	private Integer valid;
	private Integer recipientValid;
	private Integer weight;

	public OpSysMsg() {
		super();
	}

	public OpSysMsg(Integer id, Integer senderId, Integer recipientId,
			Date msgDate, String content, Integer objType, Integer objId,
			String objMeta, String objMeta2, String thumbPath, Integer ck, Integer valid,
			Integer recipientValid, Integer weight) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.msgDate = msgDate;
		this.content = content;
		this.objType = objType;
		this.objId = objId;
		this.objMeta = objMeta;
		this.objMeta2 = objMeta2;
		this.thumbPath = thumbPath;
		this.ck = ck;
		this.valid = valid;
		this.recipientValid = recipientValid;
		this.weight = weight;
	}
	
	public OpSysMsg(Integer senderId, Integer recipientId,
			Date msgDate, String content, Integer objType, Integer objId,
			String objMeta, String objMeta2, String thumbPath, Integer weight) {
		super();
//		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.msgDate = msgDate;
		this.content = content;
		this.objType = objType;
		this.objId = objId;
		this.objMeta = objMeta;
		this.objMeta2 = objMeta2;
		this.thumbPath = thumbPath;
		this.weight = weight;
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

	public Integer getCk() {
		return ck;
	}

	public void setCk(Integer ck) {
		this.ck = ck;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getRecipientValid() {
		return recipientValid;
	}

	public void setRecipientValid(Integer recipientValid) {
		this.recipientValid = recipientValid;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}
