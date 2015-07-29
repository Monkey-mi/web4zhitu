package com.hts.web.common.pojo;

public class HTWorldLikeMeRelate {

	private Integer relType;
	private String relText;
	private Integer relCount;

	public HTWorldLikeMeRelate() {
		super();
	}
	
	public HTWorldLikeMeRelate(Integer relType, String relText, Integer relCount) {
		super();
		this.relType = relType;
		this.relText = relText;
		this.relCount = relCount;
	}

	public Integer getRelType() {
		return relType;
	}

	public void setRelType(Integer relType) {
		this.relType = relType;
	}

	public String getRelText() {
		return relText;
	}

	public void setRelText(String relText) {
		this.relText = relText;
	}

	public Integer getRelCount() {
		return relCount;
	}

	public void setRelCount(Integer relCount) {
		this.relCount = relCount;
	}
	
}
