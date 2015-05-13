package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 频道订阅POJO
 * </p>
 * 
 * 创建时间: 2015-05-08
 * 
 * @author lynch
 *
 */
public class OpChannelSub implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8444883211037037418L;
	private Integer channelId;
	private Integer userId;

	public OpChannelSub() {
		super();
	}

	public OpChannelSub(Integer channelId, Integer userId) {
		super();
		this.channelId = channelId;
		this.userId = userId;
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

}
