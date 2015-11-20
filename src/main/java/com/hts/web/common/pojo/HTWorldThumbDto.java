package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 织图缩略图数据传输对象
 * </p>
 * 
 * 创建时间：2013-8-28
 * 
 * @author ztj
 * 
 */
public class HTWorldThumbDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2980254566791614785L;

	private Integer id;
	private String coverPath;
	private String titlePath;
	private String bgPath;
	private String titleThumbPath;
	private String shortLink;
	private Integer valid;
	private Integer shield;

	public HTWorldThumbDto() {
		super();
	}
	
	public HTWorldThumbDto(Integer id, String coverPath, String titlePath,
			String bgPath, String titleThumbPath, Integer valid, Integer shield) {
		this.id = id;
		this.coverPath = coverPath;
		this.titlePath = titlePath;
		this.bgPath = bgPath;
		this.titleThumbPath = titleThumbPath;
		this.valid = valid;
		this.shield = shield;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public String getBgPath() {
		return bgPath;
	}

	public void setBgPath(String bgPath) {
		this.bgPath = bgPath;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	
}
