package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 频道名称POJO
 * 
 * @author lynch
 *
 */
public class HTWorldChannelName implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1941803496452108913L;
	private Integer id;
	private String name;

	public HTWorldChannelName() {
		super();
	}

	public HTWorldChannelName(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

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

	@Override
	public String toString() {
		return "HTWorldChannelName [id=" + id + ", name=" + name + "]";
	}

	
}
