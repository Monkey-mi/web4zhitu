package com.hts.web.common.pojo;

import java.io.Serializable;

public class OpNearLabelDto implements Serializable{
	private static final long serialVersionUID = 1115964417245334817L;
	private Integer id;
	private String labelName;
	private String desc;
	private String bannerUrl;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	
}
