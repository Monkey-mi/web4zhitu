package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 贴纸系列POJO
 * 
 * @author lynch
 *
 */
public class HTWorldStickerSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1239248236459713126L;
	private Integer id;
	private String setName;

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

}
