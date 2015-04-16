package com.hts.web.common.pojo;

import com.hts.web.base.constant.Tag;

public class UserWithIsMututal implements ObjectWithIsMututal {

	private Integer userId;
	private Integer isMututal = Tag.UN_CONCERN;
	
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
