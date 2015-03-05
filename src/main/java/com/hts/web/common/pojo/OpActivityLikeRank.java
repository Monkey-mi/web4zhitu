package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 活动点赞排行POJO
 * </p>
 * 
 * 创建时间：2014-6-30
 * @author tianjie
 *
 */
public class OpActivityLikeRank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8037900451456688616L;
	private Integer userId;
	private Integer activityId;
	private Integer likeCount;
	private Integer lastPos;
	
	public OpActivityLikeRank() {
		super();
	}

	public OpActivityLikeRank(Integer userId, Integer activityId,
			Integer likeCount, Integer lastPos) {
		super();
		this.userId = userId;
		this.activityId = activityId;
		this.likeCount = likeCount;
		this.lastPos = lastPos;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getLastPos() {
		return lastPos;
	}

	public void setLastPos(Integer lastPos) {
		this.lastPos = lastPos;
	}
	
	
	
	
}
