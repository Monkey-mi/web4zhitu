package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 频道名称POJO
 * </p>
 * 
 * 创建时间: 2015-05-09
 * 
 * @author lynch
 *
 */
public class OpChannelName implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7696130355490625455L;

	private Integer id;
	private String channelName;

	public OpChannelName() {
		super();
	}

	public OpChannelName(Integer id, String channelName) {
		super();
		this.id = id;
		this.channelName = channelName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}
