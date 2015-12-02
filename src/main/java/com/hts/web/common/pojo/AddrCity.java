package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 城市信息
 * 
 * @author lynch 2015-12-02
 *
 */
public class AddrCity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4849145639831686821L;

	private Integer id;
	private String name;
	private Double longitude;
	private Double latitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
