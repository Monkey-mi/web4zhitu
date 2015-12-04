package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 附近标签信息POJO
 * 
 * @author lynch 2015-12-03
 *
 */
public class OpNearLabelDto implements Serializable {
	
	private static final long serialVersionUID = 1115964417245334817L;
	private Integer id;
	private String labelName;
	private String description;
	private String bannerUrl;
	private Integer serial; // 用于排序
	private Double[] loc; // 用于基于经纬度查询附近标签信息

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
		return description;
	}

	public void setDesc(String desc) {
		this.description = desc;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public Double[] getLoc() {
		return loc;
	}

	public void setLoc(Double[] loc) {
		this.loc = loc;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	
}
