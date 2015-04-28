package com.hts.web.common.pojo;

import java.util.ArrayList;
import java.util.List;

import com.hts.web.base.constant.Tag;

public class OpStarRecommendDto implements UserWithWorld,ObjectWithUserVerify{

	private static final long serialVersionUID = 8137994168336281019L;
	private Integer id;			//用户id
	private String platformSign; // 社交平台签名
	private Integer platformVerify; // 社交平台认证标记
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private String signature;
	private String userLabel;
	private Integer star = Tag.FALSE;
	private String verifyName;
	private String verifyIcon;
	private Integer activity = 0;
	private List<HTWorldThumbUser> htworld = new ArrayList<HTWorldThumbUser>();
	private String platformReason;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
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

	@Override
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public List<HTWorldThumbUser> getHtworld() {
		return htworld;
	}

	public void setHtworld(List<HTWorldThumbUser> htworld) {
		this.htworld = htworld;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	public String getPlatformReason() {
		return platformReason;
	}

	public void setPlatformReason(String platformReason) {
		this.platformReason = platformReason;
	}

}
