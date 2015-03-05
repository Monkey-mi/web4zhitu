package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 活动获胜者数据传输对象
 * </p>
 * 
 * 创建时间：2014-6-9
 * @author tianjie
 *
 */
public class OpActivityWinnerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8835662923643925882L;
	
	private Integer id;
	private Integer activityId;
	
	public OpActivityWinnerDto() {
		super();
	}

	public OpActivityWinnerDto(Integer id, Integer activityId) {
		this.id = id;
		this.activityId = activityId;
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
	
	
	

}
