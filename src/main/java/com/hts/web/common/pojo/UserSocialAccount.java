package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 社交账号POJO
 * </p>
 * 
 * 创建时间：2013-6-21
 * 
 * @author ztj
 * 
 */
public class UserSocialAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8871304882962180510L;

	private Integer id;
	private Integer platformCode;
	private String platformId;
	private String platformName;
	private String platformAvatar;
	private String platformAvatarL;
	private String platformToken;
	private Long platformTokenExpires;
	private String platformSign;
	private Integer platformVerify = Tag.VERIFY_NONE;
	private String platformReason;
	private Integer userId;
	private Integer valid;

	public UserSocialAccount() {
		super();
	}

	public UserSocialAccount(Integer id, Integer platformCode, String platformId, String platformName,
			String platformAvatar, String platformAvatarL, String platformToken, Long platformTokenExpires, 
			String platformSign, Integer platformVerify, String platformReason, Integer userId, Integer valid) {
		super();
		this.id = id;
		this.platformCode = platformCode;
		this.platformId = platformId;
		this.platformName = platformName;
		this.platformAvatar = platformAvatar;
		this.platformAvatarL = platformAvatarL;
		this.platformToken = platformToken;
		this.platformTokenExpires = platformTokenExpires;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.platformReason = platformReason;
		this.userId = userId;
		this.valid = valid;
	}
	
	public UserSocialAccount(Integer platformCode, String platformId,
			String platformName, String platformAvatar, String platformAvatarL,
			String platformToken, Long platformTokenExpires, String platformSign,
			Integer platformVerify, String platformReason, Integer userId, Integer valid) {
		super();
		this.platformCode = platformCode;
		this.platformId = platformId;
		this.platformName = platformName;
		this.platformAvatar = platformAvatar;
		this.platformAvatarL = platformAvatarL;
		this.platformToken = platformToken;
		this.platformTokenExpires = platformTokenExpires;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.platformReason = platformReason;
		this.userId = userId;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPlatformToken() {
		return platformToken;
	}

	public void setPlatformToken(String platformToken) {
		this.platformToken = platformToken;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPlatformAvatarL() {
		return platformAvatarL;
	}

	public void setPlatformAvatarL(String platformAvatarL) {
		this.platformAvatarL = platformAvatarL;
	}

	public Integer getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(Integer platformCode) {
		this.platformCode = platformCode;
	}

	public Long getPlatformTokenExpires() {
		return platformTokenExpires;
	}

	public void setPlatformTokenExpires(Long platformTokenExpires) {
		this.platformTokenExpires = platformTokenExpires;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
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

	public String getPlatformReason() {
		return platformReason;
	}

	public void setPlatformReason(String platformReason) {
		this.platformReason = platformReason;
	}
	
}
