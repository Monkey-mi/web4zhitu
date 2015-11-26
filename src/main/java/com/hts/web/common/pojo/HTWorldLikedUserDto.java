package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * 喜欢织图的用户信息
 * 
 * @author lynch 2015-11-05
 *
 */
public class HTWorldLikedUserDto implements Serializable, ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 853944748084235389L;

	private Integer id;
	private Integer userId;
	private String userName;
	private String userAvatar;
	private String userAvatarL;

	private Integer star = Tag.FALSE;

	private String verifyName;
	private String verifyIcon;
	
	public HTWorldLikedUserDto() {
		super();
	}

	public HTWorldLikedUserDto(Integer id, Integer userId, String userName, 
			String userAvatar, String userAvatarL, Integer star) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.star = star;
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

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	@Override
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

}
