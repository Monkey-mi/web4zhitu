package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.List;

public class OpNearCityGroupDto implements Serializable{
	private static final long serialVersionUID = 1148635085281647528L;
	private Integer id;
	private String description;
	private Integer serial;
	private List<AddrCity> cities;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public List<AddrCity> getCities() {
		return cities;
	}
	public void setCities(List<AddrCity> cities) {
		this.cities = cities;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
