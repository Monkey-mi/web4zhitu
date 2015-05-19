package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户头像精简版
 * </p>
 * 
 * 创建时间: 2015-05-01
 * 
 * @author lynch
 *
 */
public class UserAvatarLite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4613927036765805762L;

	private Integer id;
	private String userName;
	private String userAvatar;

	public UserAvatarLite() {
		super();
	}

	public UserAvatarLite(Integer id, String userName, String userAvatar) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAvatar = userAvatar;
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

}
