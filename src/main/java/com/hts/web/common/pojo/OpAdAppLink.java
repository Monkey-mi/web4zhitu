package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * App链接POJO
 * </p>
 * 
 * 创建时间：2013-11-30
 * 
 * @author ztj
 * 
 */
public class OpAdAppLink implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8504599741789777888L;
	private Integer id;
	private String appName;
	private String appIcon;
	private String appIconL;
	private String appDesc;
	private String appLink;
	private String shortLink;
	private Integer phoneCode;
	private Integer clickCount;
	private Integer serial;
	private Integer open;
	private String url;

	public OpAdAppLink() {
		super();
	}

	public OpAdAppLink(Integer id, String appName, String appIcon, 
			String appIconL, String appDesc, String appLink,
			String shortLink, Integer phoneCode,
			Integer clickCount, Integer serial, Integer open) {
		super();
		this.id = id;
		this.appName = appName;
		this.appIcon = appIcon;
		this.appIconL = appIconL;
		this.appDesc = appDesc;
		this.appLink = appLink;
		this.shortLink = shortLink;
		this.phoneCode = phoneCode;
		this.clickCount = clickCount;
		this.serial = serial;
		this.open = open;
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

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppLink() {
		return appLink;
	}

	public void setAppLink(String appLink) {
		this.appLink = appLink;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	
	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppIconL() {
		return appIconL;
	}

	public void setAppIconL(String appIconL) {
		this.appIconL = appIconL;
	}
	
}
