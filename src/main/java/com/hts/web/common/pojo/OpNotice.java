package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户公告POJO
 * </p>
 * 
 * 创建时间：2014-8-11
 * 
 * @author tianjie
 * 
 */
public class OpNotice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7253538324711134608L;
	private Integer id;
	private String path;
	private String link;
	private Integer phoneCode;
	
	public OpNotice() {
		super();
	}
	
	public OpNotice(Integer id, String path, String link, Integer phoneCode) {
		super();
		this.id = id;
		this.path = path;
		this.link = link;
		this.phoneCode = phoneCode;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}


	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

}
