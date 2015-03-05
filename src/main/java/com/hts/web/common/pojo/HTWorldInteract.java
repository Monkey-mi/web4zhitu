package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 织图互动通用POJO
 * </p>
 * 
 * 创建时间：2013-12-3
 * 
 * @author ztj
 * 
 */
public class HTWorldInteract implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8102916034736703868L;
	private Integer id;
	private Integer userId; // 用户id
	private Integer reId; // 被回复互动id
	private String content; // 内容
	private Integer worldId; // 世界ID
	private Date interactDate; // 评论时间
	private UserInfoDto userInfo; // 用户信息
	private HTWorldThumbDto htworld; // 织图信息

	private Integer interactCode; // 互动代号
	
	public HTWorldInteract() {
		super();
	}

	public HTWorldInteract(Integer id, Integer userId, Integer reId,
			String content, Integer worldId, Date interactDate,
			Integer interactCode) {
		super();
		this.id = id;
		this.userId = userId;
		this.reId = reId;
		this.content = content;
		this.worldId = worldId;
		this.interactDate = interactDate;
		this.interactCode = interactCode;
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

	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getInteractDate() {
		return interactDate;
	}

	public void setInteractDate(Date interactDate) {
		this.interactDate = interactDate;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	public HTWorldThumbDto getHtworld() {
		return htworld;
	}

	public void setHtworld(HTWorldThumbDto htworld) {
		this.htworld = htworld;
	}

	public Integer getInteractCode() {
		return interactCode;
	}

	public void setInteractCode(Integer interactCode) {
		this.interactCode = interactCode;
	}

	@Override
	public Integer getVerifyId() {
		return userInfo.getVerifyId();
	}

	@Override
	public void setVerifyName(String verifyName) {
		userInfo.setVerifyName(verifyName);
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		userInfo.setVerifyIcon(verifyIcon);
	}
	
	@Override
	public Integer getRemarkId() {
		return userInfo.getId();
	}
	
	@Override
	public void setRemark(String remark) {
		userInfo.setRemark(remark);
	}
	
}
