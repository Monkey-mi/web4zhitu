package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * AT消息织图DTO
 * 
 * @version 3.0.5 
 * @author lynch 2015-09-22
 *
 */
public class MsgAtWorldDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4816639400378630230L;
	private Integer id;
//	private String worldDesc;
	private String titleThumbPath;
	private String titlePath;
	private Integer valid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public String getWorldDesc() {
//		return worldDesc;
//	}
//
//	public void setWorldDesc(String worldDesc) {
//		this.worldDesc = worldDesc;
//	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

}
