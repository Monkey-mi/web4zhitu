package com.hts.web.common.pojo;

import java.io.Serializable;

public class UserMsgStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6624299612563972927L;

	private Integer id;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private String address; // 地址
	private String province; // 省份
	private String city; // 城市
	private Float ver;
	private Integer acceptUserMsg;
	private Integer online;
	private Integer im;
	private Integer shield;
	private Integer ishield;
	private Integer isMututal;
	private Integer iisMututal;
	private String remarkMe;

	public UserMsgStatus() {
		super();
	}

	public UserMsgStatus(Integer id, String userName, String userAvatar,
			String userAvatarL, String address, String province, String city,
			Float ver, Integer acceptUserMsg, Integer online) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatar;
		this.address = address;
		this.province = province;
		this.city = city;
		this.ver = ver;
		this.acceptUserMsg = acceptUserMsg;
		this.online = online;
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

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
	}

	public Integer getAcceptUserMsg() {
		return acceptUserMsg;
	}

	public void setAcceptUserMsg(Integer acceptUserMsg) {
		this.acceptUserMsg = acceptUserMsg;
	}
	
	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Integer getIshield() {
		return ishield;
	}

	public void setIshield(Integer ishield) {
		this.ishield = ishield;
	}

	public Integer getIm() {
		return im;
	}

	public void setIm(Integer im) {
		this.im = im;
	}

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

	public Integer getIisMututal() {
		return iisMututal;
	}

	public void setIisMututal(Integer iisMututal) {
		this.iisMututal = iisMututal;
	}

	public String getRemarkMe() {
		return remarkMe;
	}

	public void setRemarkMe(String remarkMe) {
		this.remarkMe = remarkMe;
	}

}
