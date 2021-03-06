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
	private Integer authorId;
	private String thumbnail;
	private String worldDesc;
	private Integer valid;

	public HTWorldThumbnail() {
		super();
	}

	public HTWorldThumbnail(Integer id, String thumbnail) {
		super();
		this.id = id;
		this.thumbnail = thumbnail;
	}

	public HTWorldThumbnail(Integer id, Integer authorId, 
			String thumbnail, String worldDesc, Integer valid) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.thumbnail = thumbnail;
		this.worldDesc = worldDesc;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
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

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
}
