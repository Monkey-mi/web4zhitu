package com.hts.web.common.pojo;

import java.io.Serializable;

public class UserShieldInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4606481240695729289L;

	private Integer id;
	private Integer userId;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private String address; // 地址
	private String province; // 省份
	private String city; // 城市
	
	public UserShieldInfo() {
		super();
	}
	
	public UserShieldInfo(Integer id, Integer userId, String userName,
			String userAvatar, String userAvatarL, String address,
			String province, String city) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.address = address;
		this.province = province;
		this.city = city;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
