package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 活动LogoPOJO
 * 
 * @author tianjie
 * 
 */
public class OpActivityLogo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7030770084262253819L;
	private Integer id;
	private Integer activityId;
	private String logoPath;
	private Integer serial;
	private Integer valid;

	public OpActivityLogo() {
		super();
	}

	public OpActivityLogo(Integer id, Integer activityId, String logoPath, Integer serial, Integer valid) {
		super();
		this.id = id;
		this.activityId = activityId;
		this.logoPath = logoPath;
		this.serial = serial;
		this.valid = valid;
	}

	public OpActivityLogo(Integer activityId, String logoPath, Integer serial, Integer valid) {
		super();
		this.activityId = activityId;
		this.logoPath = logoPath;
		this.serial = serial;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
	
	

}
