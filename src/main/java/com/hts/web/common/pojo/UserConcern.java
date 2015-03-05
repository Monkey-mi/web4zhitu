package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户关注数据对象POJO
 * </p>
 * 
 * 创建时间：2013-7-22
 * 
 * @author ztj
 * 
 */
public class UserConcern implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4196894905977241642L;

	private Integer id;
	private Integer userId;
	private Integer concernId;
	private Integer isMututal;
	private Date concernDate;
	private Integer ck;
	private Integer valid;
	
	public UserConcern() {
		super();
	}

	public UserConcern(Integer id, Integer userId, Integer concernId,
			Integer isMututal, Date concernDate, Integer ck, Integer valid) {
		super();
		this.id = id;
		this.userId = userId;
		this.concernId = concernId;
		this.isMututal = isMututal;
		this.concernDate = concernDate;
		this.ck = ck;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getConcernDate() {
		return concernDate;
	}

	public void setConcernDate(Date concernDate) {
		this.concernDate = concernDate;
	}

	public Integer getCk() {
		return ck;
	}

	public void setCk(Integer ck) {
		this.ck = ck;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

}
