package com.hts.web.common.pojo;

import java.io.Serializable;

public class OpAdAppLinkDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935026104794883391L;
	private Integer id;
	private String appName;
	private String appIcon;
	private String appIconL;
	private String appDesc;
	private String url;

	public OpAdAppLinkDto() {
		super();
	}

	public OpAdAppLinkDto(Integer id, String appName, String appIcon, 
			String appIconL, String appDesc, String url) {
		super();
		this.id = id;
		this.appName = appName;
		this.appIcon = appIcon;
		this.appIconL = appIconL;
		this.appDesc = appDesc;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}
	
	public String getAppIconL() {
		return appIconL;
	}

	public void setAppIconL(String appIconL) {
		this.appIconL = appIconL;
	}

	public String getAppDesc() {
		return appDesc;
	}
	
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
