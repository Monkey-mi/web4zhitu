package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 评论消息用户信息DTO
 * 
 * @author lynch 2015-09-22
 *
 */
public class MsgCommentUserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2725750188984954035L;
	private Integer id;
	private String userName;
	private String userAvatar;
	private String userAvatarL;

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
