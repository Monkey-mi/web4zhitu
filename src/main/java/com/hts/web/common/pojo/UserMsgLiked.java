package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 喜欢消息POJO
 * </p>
 * 
 * 创建时间：2014-09-24
 * @author tianjie
 *
 */
public class UserMsgLiked implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5820751841529438725L;
	private Integer id;
	private Integer userId;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer star;
	private String verifyName;
	private String verifyIcon;
	private Integer worldId;
	private Integer worldAuthorId;
	private String titleThumbPath;
	private Date likedDate;
	private String remark;
	private Integer platformVerify = Tag.VERIFY_NONE;
	
	
	public UserMsgLiked(Integer id, Integer userId, Integer worldId,
			Integer worldAuthorId, String titleThumbPath, Date likedDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.worldId = worldId;
		this.worldAuthorId = worldAuthorId;
		this.titleThumbPath = titleThumbPath;
		this.likedDate = likedDate;
	}

	public UserMsgLiked() {
		super();
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	
	public String getUserAvatarL() {
		return userAvatarL;
	}

	public void setUserAvatarL(String userAvatarL) {
		this.userAvatarL = userAvatarL;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getWorldAuthorId() {
		return worldAuthorId;
	}

	public void setWorldAuthorId(Integer worldAuthorId) {
		this.worldAuthorId = worldAuthorId;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getLikedDate() {
		return likedDate;
	}

	public void setLikedDate(Date likedDate) {
		this.likedDate = likedDate;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	@Override
	public Integer getVerifyId() {
		return this.star;
	}

	@Override
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Integer getRemarkId() {
		return userId;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}

}
