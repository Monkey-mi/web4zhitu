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

	private Integer senderId = 2063;
	private UserInfoAvatar senderInfo = new UserInfoAvatar(
			2063, 
			"织图", 
			"http://imzhitu.qiniudn.com/avatar/m/2015/04/08/11/3326b4c5912036ff6637404642c9cfbd.jpg.thumbnail",
			null, null, null, null, null);
	
	public OpSysMsgDto() {
		super();
	}

	public OpSysMsgDto(Integer id, Integer recipientId,
			Date msgDate, String content, Integer objType, Integer objId,
			String objMeta, String objMeta2, String thumbPath) {
		super();
		this.id = id;
		this.recipientId = recipientId;
		this.msgDate = msgDate;
		this.content = content;
		this.objType = objType;
		this.objId = objId;
		this.objMeta = objMeta;
		this.objMeta2 = objMeta2;
		this.thumbPath = thumbPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public UserInfoAvatar getSenderInfo() {
		return senderInfo;
	}

	public void setSenderInfo(UserInfoAvatar senderInfo) {
		this.senderInfo = senderInfo;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

}
