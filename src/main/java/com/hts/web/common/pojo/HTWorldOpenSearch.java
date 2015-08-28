package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 织图OpenSearch POJO
 * 
 * @author lynch
 *
 */
public class HTWorldOpenSearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5312962267755434399L;
	private Integer id;
	private Double longitude;
	private Double latitude;
	private String locDesc;
	private String province;
	private String city;

	public HTWorldOpenSearch() {
		super();
	}

	public HTWorldOpenSearch(Integer id, Double longitude, Double latitude, String locDesc, String province,
			String city) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.locDesc = locDesc;
		this.province = province;
		this.city = city;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getLocDesc() {
		return locDesc;
	}

	public void setLocDesc(String locDesc) {
		this.locDesc = locDesc;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
