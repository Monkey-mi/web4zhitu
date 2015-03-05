package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 用户私信对话索引
 * </p>
 * 
 * 创建时间：2013-11-29
 * @author ztj
 *
 */
public class UserMsgIndex implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6383529599141646078L;
	private Integer id;
	private Integer userId; // 用户id
	private Integer otherId; // 对方id
	private Date msgDate;
	private String content;
	private Integer objType;
	private String objMeta;
	private Integer objId;
	private String thumbPath;
	private UserInfoDto otherInfo; // 对方信息
	private Integer unreadCount;
	
	public UserMsgIndex() {
		super();
	}

	public UserMsgIndex(Integer id, Integer userId, Integer otherId,
			Date msgDate, String content, Integer objType, String objMeta,
			Integer objId, String thumbPath, UserInfoDto otherInfo, Integer unreadCount) {
		this.id = id;
		this.userId = userId;
		this.otherId = otherId;
		this.msgDate = msgDate;
		this.content = content;
		this.objType = objType;
		this.objMeta = objMeta;
		this.objId = objId;
		this.thumbPath = thumbPath;
		this.otherInfo = otherInfo;
		this.unreadCount = unreadCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public UserInfoDto getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(UserInfoDto otherInfo) {
		this.otherInfo = otherInfo;
	}

	public Integer getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}
	
}
