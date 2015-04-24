package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * 喜欢我的用户信息
 * 
 * 创建时间: 2015-04-14
 * 
 * @author lynch
 *
 */
public class HTWorldLikeMe implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2857739629330336944L;

	private Integer id;
	private Date likedDate;
	private Integer userId;
	private String userName;
	private String userAvatar;
	private String userAvatarL;

	private Integer star;
	private Integer platformVerify = Tag.VERIFY_NONE;

	private Integer worldId;
	private String titleThumbPath;

	private String verifyName;
	private String verifyIcon;
	
	private String remark;
	
	private List<HTWorldLikeMeThumb> titleThumbs;

	public HTWorldLikeMe(Integer id, Date likedDate, Integer userId,
			String userName, String userAvatar, String userAvatarL,
			Integer star, Integer platformVerify) {
		super();
		this.id = id;
		this.likedDate = likedDate;
		this.userId = userId;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.star = star;
		this.platformVerify = platformVerify;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getLikedDate() {
		return likedDate;
	}

	public void setLikedDate(Date likedDate) {
		this.likedDate = likedDate;
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

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	@Override
	public Integer getVerifyId() {
		return this.star;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Integer getRemarkId() {
		return this.userId;
	}

	public List<HTWorldLikeMeThumb> getTitleThumbs() {
		return titleThumbs;
	}

	public void setTitleThumbs(List<HTWorldLikeMeThumb> titleThumbs) {
		this.titleThumbs = titleThumbs;
	}
	
}
