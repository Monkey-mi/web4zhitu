package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户备注表
 * </p>
 * 
 * 创建时间：2014-12-15
 * @author lynch
 *
 */
public class UserRemark implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6864910365290870712L;

	private Integer userId;
	private Integer remarkId;
	private String remark;
	
	public UserRemark() {
		super();
	}
	
	public UserRemark(Integer userId, Integer remarkId, String remark) {
		super();
		this.userId = userId;
		this.remarkId = remarkId;
		this.remark = remark;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Integer remarkId) {
		this.remarkId = remarkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
