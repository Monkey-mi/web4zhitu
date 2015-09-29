package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 评论消息织图DTO
 * 
 * @version 3.0.5 
 * @author lynch 2015-09-22
 *
 */
public class MsgCommentWorldDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6372230167055909738L;
	private Integer id;
	private String titleThumbPath;
	private String titlePath;
	private Integer valid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
