package com.hts.web.operations.pojo;

import java.io.Serializable;

/**
 * 附近公告POJO
 * 
 * @author lynch 2015-12-15
 *
 */
public class NearBulletin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 783086277409470353L;

	private Integer id;
	private String bulletinPath;
	private String bulletinThumb;
	private String bulletinName;
	private Integer bulletinType;
	private String link;
	private Integer serial;
	
	private Double loc[]; // 经纬度
	private Integer bulletinId; // 关联的公告id
	private Integer cityId; // 城市id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getBulletinName() {
		return bulletinName;
	}

	public void setBulletinName(String bulletinName) {
		this.bulletinName = bulletinName;
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

	public Double[] getLoc() {
		return loc;
	}

	public void setLoc(Double[] loc) {
		this.loc = loc;
	}

	public Integer getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(Integer bulletinId) {
		this.bulletinId = bulletinId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	
}
