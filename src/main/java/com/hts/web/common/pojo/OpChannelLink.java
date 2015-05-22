package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 关联频道POJO
 * </p>
 * 
 * 创建时间: 2015-05-21
 * 
 * @author lynch
 *
 */
public class OpChannelLink implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8075186443566752897L;
	
	private Integer id;
	private String channelName;
	
	public OpChannelLink() {
		super();
	}
	
	public OpChannelLink(Integer id, String channelName) {
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
