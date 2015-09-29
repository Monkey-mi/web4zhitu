package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户关注列表头像信息，包括id,名字
 * </p>
 * 
 * @author lynch 2015-09-22
 *
 */
public class UserConcernAvatar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -912301847967327415L;
	private Integer id;
	private String userName;
	private String userAvatar;


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
