package com.hts.web.common.pojo;

import java.io.Serializable;

public class HTWorldGeo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9094094994118692981L;

	private Integer id; // 织图id
	private String titlePath; // 首页路径
	private String titleThumbPath; // 首页缩略图路径
	private Double longitude;// 经度
	private Double latitude;// 纬度
	private String locationDesc;// 位置描述
	private String locationAddr; // 位置地址

	protected HTWorldGeo() {
		super();
	}

	public HTWorldGeo(Integer id, String titlePath, String titleThumbPath,
			Double longitude, Double latitude, String locationDesc,
			String locationAddr) {
		this.id = id;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
		this.longitude = longitude;
		this.latitude = latitude;
		this.locationDesc = locationDesc;
		this.locationAddr = locationAddr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

}
