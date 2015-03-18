package com.hts.web.common.pojo;

import java.io.Serializable;

public class UserConcernName implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7464245323976085604L;
	private Integer id;
	private String userName;
	
	
	public UserConcernName() {
		super();
	}

	public UserConcernName(Integer id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
