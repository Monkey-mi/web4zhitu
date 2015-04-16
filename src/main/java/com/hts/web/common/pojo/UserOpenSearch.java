package com.hts.web.common.pojo;

import java.io.Serializable;

public class UserOpenSearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6877114142180678415L;
	private Integer id;
	private String userName;
	private String userAvatar;
	private String signature;
	private String platformSign;
	private Integer star;
	private Integer activity;
	
	public UserOpenSearch(Integer id, String userName, String userAvatar,
			String signature, String platformSign, Integer star, Integer activity) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.signature = signature;
		this.platformSign = platformSign;
		this.star = star;
		this.activity = activity;
	}

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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPlatformSign() {
		return platformSign;
	}

	public void setPlatformSign(String platformSign) {
		this.platformSign = platformSign;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}
	
}
