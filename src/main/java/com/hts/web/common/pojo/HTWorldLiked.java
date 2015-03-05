package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 织图世界喜欢POJO
 * </p>
 * 
 * 创建时间：2013-7-5
 * 
 * @author ztj
 * 
 */
public class HTWorldLiked implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5497995694049951982L;
	private Integer id;
	private Integer userId;
	private Date likedDate;
	private Integer worldId;
	private Integer worldAuthorId;
	private Integer ck;
	private Integer valid;
	private UserInfoDto userInfo;
	private HTWorldThumbDto htworld;
	
	public HTWorldLiked() {
		super();
	}

	public HTWorldLiked(Integer id, Integer userId, Date likedDate,
			Integer worldId, Integer worldAuthorId, Integer ck, Integer valid) {
		super();
		this.id = id;
		this.userId = userId;
		this.likedDate = likedDate;
		this.worldId = worldId;
		this.worldAuthorId = worldAuthorId;
		this.ck = ck;
		this.valid = valid;
	}

	public HTWorldLiked(Integer userId, Date likedDate,
			Integer worldId, Integer worldAuthorId, Integer ck, Integer valid) {
		super();
		this.userId = userId;
		this.likedDate = likedDate;
		this.worldId = worldId;
		this.worldAuthorId = worldAuthorId;
		this.ck = ck;
		this.valid = valid;
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getLikedDate() {
		return likedDate;
	}

	public void setLikedDate(Date likedDate) {
		this.likedDate = likedDate;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getCk() {
		return ck;
	}

	public void setCk(Integer ck) {
		this.ck = ck;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getWorldAuthorId() {
		return worldAuthorId;
	}

	public void setWorldAuthorId(Integer worldAuthorId) {
		this.worldAuthorId = worldAuthorId;
	}

	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	public HTWorldThumbDto getHtworld() {
		return htworld;
	}

	public void setHtworld(HTWorldThumbDto htworld) {
		this.htworld = htworld;
	}
	
	
}
