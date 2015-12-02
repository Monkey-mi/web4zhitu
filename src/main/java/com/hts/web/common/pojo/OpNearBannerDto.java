package com.hts.web.common.pojo;

import java.io.Serializable;

public class OpNearBannerDto implements Serializable{

	private static final long serialVersionUID = -5061713617613609667L;
	private Integer id;
	private Integer cityId;
	private String bulletinPath;
	private String bulletinThumb;
	private Integer bulletinType;
	private String link;
	private Integer serial;
	private Double longitude;
	private Double latitude;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getBulletinPath() {
		return bulletinPath;
	}
	public void setBulletinPath(String bulletinPath) {
		this.bulletinPath = bulletinPath;
	}
	public String getBulletinThumb() {
		return bulletinThumb;
	}
	public void setBulletinThumb(String bulletinThumb) {
		this.bulletinThumb = bulletinThumb;
	}
	public Integer getBulletinType() {
		return bulletinType;
	}
	public void setBulletinType(Integer bulletinType) {
		this.bulletinType = bulletinType;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
