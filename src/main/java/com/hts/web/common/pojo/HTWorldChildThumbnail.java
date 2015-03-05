package com.hts.web.common.pojo;

import java.io.Serializable;

public class HTWorldChildThumbnail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2730050310048602007L;
	
	private Integer id;
	private Integer worldId;
	private String path;
	
	
	public HTWorldChildThumbnail() {
		super();
	}

	public HTWorldChildThumbnail(Integer id, Integer worldId, String path) {
		super();
		this.id = id;
		this.worldId = worldId;
		this.path = path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
