package com.hts.web.common.pojo;

import java.io.Serializable;

public class IndexUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4770277805670343917L;
	private Integer id;
	private String name;

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

}
