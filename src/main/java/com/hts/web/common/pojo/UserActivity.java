package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 用户活跃度
 * </p>
 * 
 * 创建时间：2014-5-25
 * 
 * @author tianjie
 * 
 */
public class UserActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9151726897529859357L;
	private Integer id;
	private Integer userId;
	private Integer typeId;
	private Date dateAdded;
	private Integer score;
	
	public UserActivity(Integer id, Integer userId, Integer typeId,
			Date dateAdded, Integer score) {
		super();
		this.id = id;
		this.userId = userId;
		this.typeId = typeId;
		this.dateAdded = dateAdded;
		this.score = score;
	}
	
	public UserActivity(Integer userId, Integer typeId, Date dateAdded,
			Integer score) {
		super();
		this.userId = userId;
		this.typeId = typeId;
		this.dateAdded = dateAdded;
		this.score = score;
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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
