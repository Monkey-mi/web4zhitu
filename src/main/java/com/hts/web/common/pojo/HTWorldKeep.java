package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 织图世界收藏POJO
 * </p>
 * 
 * 创建时间：2013-7-5
 * 
 * @author ztj
 * 
 */
public class HTWorldKeep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3993442153747764390L;
	private Integer id;
	private Integer userId;
	private Date keepDate;
	private Integer worldId;
	private Integer worldAuthorId;
	private Integer ck;
	private Integer valid;

	public HTWorldKeep() {
		super();
	}

	public HTWorldKeep(Integer id, Integer userId, Date keepDate,
			Integer worldId, Integer worldAuthorId, Integer ck, Integer valid) {
		super();
		this.id = id;
		this.userId = userId;
		this.keepDate = keepDate;
		this.worldId = worldId;
		this.worldAuthorId = worldAuthorId;
		this.ck = ck;
		this.valid = valid;
	}

	public HTWorldKeep(Integer userId, Date keepDate, Integer worldId,
			Integer worldAuthorId, Integer ck, Integer valid) {
		super();
		this.userId = userId;
		this.keepDate = keepDate;
		this.worldId = worldId;
		this.worldAuthorId = worldAuthorId;
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

	public Date getKeepDate() {
		return keepDate;
	}

	public void setKeepDate(Date keepDate) {
		this.keepDate = keepDate;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
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

	public Integer getWorldAuthorId() {
		return worldAuthorId;
	}

	public void setWorldAuthorId(Integer worldAuthorId) {
		this.worldAuthorId = worldAuthorId;
	}

}
