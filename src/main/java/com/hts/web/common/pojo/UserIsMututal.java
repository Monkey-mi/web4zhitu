package com.hts.web.common.pojo;

import com.hts.web.base.constant.Tag;

public class UserIsMututal {

	private Integer userId;
	private Integer concernId;
	private Integer isMututal = Tag.UN_CONCERN;

	public UserIsMututal() {
		super();
	}

	public UserIsMututal(Integer userId, Integer concernId, Integer isMututal) {
		super();
		this.userId = userId;
		this.concernId = concernId;
		this.isMututal = isMututal;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConcernId() {
		return concernId;
	}

	public void setConcernId(Integer concernId) {
		this.concernId = concernId;
	}

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

}
