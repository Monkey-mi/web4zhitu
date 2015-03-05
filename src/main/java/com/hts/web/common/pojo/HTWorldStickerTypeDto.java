package com.hts.web.common.pojo;

import java.io.Serializable;

public class HTWorldStickerTypeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8667281707826045688L;
	private Integer id;
	private String typeName;
	
	public HTWorldStickerTypeDto() {
		super();
	}

	public HTWorldStickerTypeDto(Integer id, String typeName) {
		super();
		this.id = id;
		this.typeName = typeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
