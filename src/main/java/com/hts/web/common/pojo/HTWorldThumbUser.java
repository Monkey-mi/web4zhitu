package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 织图缩略图用户信息POJO
 * </p>
 * 
 * 创建时间：2014-1-13
 * 
 * @author ztj
 * 
 */
public class HTWorldThumbUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8473107911605144374L;
	private Integer id;
	private Integer userId;
	private String coverPath;
	private String titlePath;
	private String titleThumbPath;
	
	public HTWorldThumbUser() {
		super();
	}

	public HTWorldThumbUser(Integer id, Integer userId, String coverPath,
			String titlePath, String titleThumbPath) {
		super();
		this.id = id;
		this.userId = userId;
		this.coverPath = coverPath;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
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

}
