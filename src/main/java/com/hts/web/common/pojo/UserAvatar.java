package com.hts.web.common.pojo;

import java.io.Serializable;

public class UserAvatar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1705098259306018359L;
	private Integer id;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	
	public UserAvatar() {
		super();
	}

//	public UserAvatar(Integer id, String userName, String userAvatar,
//			String userAvatarL) {
//		super();
//		this.id = id;
//		this.userName = userName;
//		this.userAvatar = userAvatar;
//		this.userAvatarL = userAvatarL;
//	}

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

}
