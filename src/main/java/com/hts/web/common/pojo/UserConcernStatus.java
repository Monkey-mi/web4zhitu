package com.hts.web.common.pojo;

import java.io.Serializable;

public class UserConcernStatus implements Serializable, ObjectWithIsMututal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4869475867493961003L;
	private Integer userId;
	private Integer isMututal;
	
	public UserConcernStatus() {
		super();
	}
	
	public UserConcernStatus(Integer userId, Integer isMututal) {
		super();
		this.userId = userId;
		this.isMututal = isMututal;
	}

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public Integer getUserId() {
		return userId;
	}

	@Override
	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

}
