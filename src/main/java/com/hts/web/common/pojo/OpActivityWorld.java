package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 广场活动织图POJO
 * </p>
 * 
 * 创建时间：2013-11-20
 * @author ztj
 *
 */
public class OpActivityWorld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4487012778692504595L;

	private Integer id;
	private Integer activityId;
	private Integer worldId;
	private Integer valid;
	private Integer recommend;
	private Integer ck;

	public OpActivityWorld() {
		super();
	}
	
	public OpActivityWorld(Integer id, Integer activityId,
			Integer worldId, Integer valid, Integer recommend, Integer ck) {
		this.id = id;
		this.activityId = activityId;
		this.worldId = worldId;
		this.valid = valid;
		this.recommend = recommend;
		this.ck = ck;
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

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getCk() {
		return ck;
	}

	public void setCk(Integer ck) {
		this.ck = ck;
	}

}
