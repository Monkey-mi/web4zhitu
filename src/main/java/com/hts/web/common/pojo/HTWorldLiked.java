package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 织图世界喜欢POJO
 * </p>
 * 
 * @author ztj 2013-7-5 2015-11-05
 * 
 */
public class HTWorldLiked implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5497995694049951982L;
	private Integer id;
	private Integer userId;
	private Integer worldId;

	public HTWorldLiked() {
		super();
	}

	public HTWorldLiked(Integer id, Integer userId, Integer worldId) {
		super();
		this.id = id;
		this.userId = userId;
		this.worldId = worldId;
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

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

}
