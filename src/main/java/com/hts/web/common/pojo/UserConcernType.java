package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 用户关注分类POJO
 * </p>
 * 
 * 创建时间：2014-1-21
 * 
 * @author lynch
 * 
 */
public class UserConcernType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7014499065947632207L;
	
	private Integer userId;
	private Integer typeId;
	private Integer valid;
	
	
	public UserConcernType() {
		super();
	}

	public UserConcernType(Integer userId, Integer typeId, Integer valid) {
		super();
		this.userId = userId;
		this.typeId = typeId;
		this.valid = valid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

}
