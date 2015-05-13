package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 频道成员POJO
 * </p>
 * 
 * 创建时间:2015-05-05
 * 
 * @author lynch
 *
 */
public class OpChannelMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 929047952838987242L;

	private Integer id;
	private Integer channelId;
	private Integer userId;
	private Integer degree;
	private Date subTime;

	public OpChannelMember() {
		super();
	}
	
	public OpChannelMember(Integer id, Integer channelId, Integer userId,
			Integer degree, Date subTime) {
		super();
		this.id = id;
		this.channelId = channelId;
		this.userId = userId;
		this.degree = degree;
		this.subTime = subTime;
	}
	
	public OpChannelMember(Integer channelId, Integer userId, Integer degree,
			Date subTime) {
		this.channelId = channelId;
		this.userId = userId;
		this.degree = degree;
		this.subTime = subTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getJoinTime() {
		return subTime;
	}

	public void setJoinTime(Date subTime) {
		this.subTime = subTime;
	}

}
