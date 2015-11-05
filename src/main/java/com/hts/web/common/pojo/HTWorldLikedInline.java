package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 喜欢用户信息POJO
 * </p>
 * 
 * 创建时间：2014-1-1
 * 
 * @author ztj
 * 
 */
public class HTWorldLikedInline implements Serializable, ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = -54337246555182643L;
	private Integer userId;
	private String userAvatar;
	private Integer worldId;
	private Integer star;
	private String verifyIcon;
	private Integer platformVerify = Tag.VERIFY_NONE;

	public HTWorldLikedInline() {
		super();
	}

	public HTWorldLikedInline(Integer userId, String userAvatar, 
			Integer worldId, Integer star, Integer platformVerify) {
		super();
		this.userId = userId;
		this.userAvatar = userAvatar;
		this.worldId = worldId;
		this.star = star;
		this.platformVerify = platformVerify;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}

	@Override
	public void setVerifyName(String verifyName) {
	}

}
