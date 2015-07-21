package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HTWorldStickerSetDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6761688701142924827L;
	private Integer id;
	private String setName;
	private Integer typeId;
	private List<HTWorldStickerDto> sets = new ArrayList<HTWorldStickerDto>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public List<HTWorldStickerDto> getSets() {
		return sets;
	}

	public void setSets(List<HTWorldStickerDto> sets) {
		this.sets = sets;
	}

}
