package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 收件箱私信POJO
 * </p>
 * 
 * 创建时间：2013-11-30
 * @author ztj
 *
 */
public class UserMsgRecipientDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4777757364392181711L;

	private Integer id;
	private Integer senderId;
	private Integer recipientId;
	private Date msgDate;
	private String content;
	private Integer objType;
	private String objMeta;
	private Integer objId;
	private String thumbPath;
	private Integer ck = Tag.FALSE;

	private UserInfoDto senderInfo; // 发送者信息

	public UserMsgRecipientDto() {
		super();
	}

	public UserMsgRecipientDto(Integer id, Integer senderId,
			Integer recipientId, Date msgDate, String content, Integer objType,
			String objMeta, Integer objId, String thumbPath, Integer ck, UserInfoDto senderInfo) {
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.msgDate = msgDate;
		this.content = content;
		this.objType = objType;
		this.objMeta = objMeta;
		this.objId = objId;
		this.thumbPath = thumbPath;
		this.ck = ck;
		this.senderInfo = senderInfo;
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

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public String getObjMeta() {
		return objMeta;
	}

	public void setObjMeta(String objMeta) {
		this.objMeta = objMeta;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
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

	public UserInfoDto getSenderInfo() {
		return senderInfo;
	}

	public void setSenderInfo(UserInfoDto senderInfo) {
		this.senderInfo = senderInfo;
	}

	
	
}
