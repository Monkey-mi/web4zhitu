package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 活动获胜织图POJO
 * </p>
 * 
 * 创建时间：2014-6-11
 * @author tianjie
 *
 */
public class OpActivityWinner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 846679135809352986L;

	private Integer id;
	private Integer activityId;
	private Integer worldId;
	private Integer userId;
	private Integer awardId;
	private Integer serial;
	private Integer weight;
	

	public OpActivityWinner() {
		super();
	}

	public OpActivityWinner(Integer id, Integer activityId, Integer worldId, 
			Integer userId, Integer awardId, Integer serial, Integer weight) {
		super();
		this.id = id;
		this.activityId = activityId;
		this.worldId = worldId;
		this.userId = userId;
		this.awardId = awardId;
		this.serial = serial;
		this.weight = weight;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAwardId() {
		return awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
