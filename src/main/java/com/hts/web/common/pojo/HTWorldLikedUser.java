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
public class HTWorldLikedUser implements Serializable, ObjectWithUserVerify,
	ObjectWithUserRemark, ObjectWithChecksum {

	/**
	 * 
	 */
	private static final long serialVersionUID = -54337246555182643L;
	private Integer userId;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer worldId;
	private Integer star;
	private String verifyName;
	private String verifyIcon;
	private String remark;
	private Integer platformVerify = Tag.VERIFY_NONE;
	
	private Integer checksum;

	public HTWorldLikedUser() {
		super();
	}

	public HTWorldLikedUser(Integer userId, String userName, 
			String userAvatar, String userAvatarL,Integer worldId, 
			Integer star, Integer platformVerify) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
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
		return star;
	}

	@Override
	public Integer getRemarkId() {
		return this.userId;
	}
	
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}

	public Integer getChecksum() {
		return checksum;
	}

	public void setChecksum(Integer checksum) {
		this.checksum = checksum;
	}

	@Override
	public Integer getChecksumUID() {
		return userId;
	}

	
}
