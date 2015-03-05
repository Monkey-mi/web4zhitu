package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 社交账号数据传输POJO
 * </p>
 * 
 * 创建时间：2013-6-21
 * 
 * @author ztj
 * 
 */
public class UserSocialAccountDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4389263666919869236L;
	
	private Integer id;
	private Integer platformCode;
	private String platformId;
	private String platformName;
	private String platformAvatar;
	private String platformAvatarL;
	private String platformSign;
	private Integer platformVerify = Tag.VERIFY_NONE;
	private String platformReason;
	private Integer userId;
	
	public UserSocialAccountDto() {
		super();
	}
	
	public UserSocialAccountDto(Integer id, Integer platformCode,
			String platformId, String platformName, String platformAvatar,
			String platformAvatarL, String platformSign,
			Integer platformVerify, String platformReason, Integer userId) {
		super();
		this.id = id;
		this.platformCode = platformCode;
		this.platformId = platformId;
		this.platformName = platformName;
		this.platformAvatar = platformAvatar;
		this.platformAvatarL = platformAvatarL;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.platformReason = platformReason;
		this.userId = userId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(Integer platformCode) {
		this.platformCode = platformCode;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getPlatformAvatar() {
		return platformAvatar;
	}
	public void setPlatformAvatar(String platformAvatar) {
		this.platformAvatar = platformAvatar;
	}
	public String getPlatformAvatarL() {
		return platformAvatarL;
	}
	public void setPlatformAvatarL(String platformAvatarL) {
		this.platformAvatarL = platformAvatarL;
	}
	public String getPlatformSign() {
		return platformSign;
	}
	public void setPlatformSign(String platformSign) {
		this.platformSign = platformSign;
	}
	public Integer getPlatformVerify() {
		return platformVerify;
	}
	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPlatformReason() {
		return platformReason;
	}

	public void setPlatformReason(String platformReason) {
		this.platformReason = platformReason;
	}
	
}
