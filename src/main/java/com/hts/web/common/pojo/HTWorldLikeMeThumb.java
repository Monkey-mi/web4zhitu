package com.hts.web.common.pojo;

import java.io.Serializable;

public class HTWorldLikeMeThumb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8599097837562273375L;

	private Integer userId;
	private Integer worldId;
	private String titleThumbPath;
	
	public HTWorldLikeMeThumb(Integer userId, Integer worldId,
			String titleThumbPath) {
		super();
		this.userId = userId;
		this.worldId = worldId;
		this.titleThumbPath = titleThumbPath;
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

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

}
