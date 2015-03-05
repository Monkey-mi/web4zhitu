package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 织图封面缩略图POJO
 * </p>
 * 
 * 创建时间：2014-09-11
 * 
 * @author tianjie
 * 
 */
public class HTWorldThumbnail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2153919059498251725L;

	private Integer id;
	private String thumbnail;
	private String worldDesc;

	public HTWorldThumbnail() {
		super();
	}

	public HTWorldThumbnail(Integer id, String thumbnail) {
		super();
		this.id = id;
		this.thumbnail = thumbnail;
	}

	public HTWorldThumbnail(Integer id, String thumbnail, String worldDesc) {
		super();
		this.id = id;
		this.thumbnail = thumbnail;
		this.worldDesc = worldDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getWorldDesc() {
		return worldDesc;
	}

	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}
	
}
