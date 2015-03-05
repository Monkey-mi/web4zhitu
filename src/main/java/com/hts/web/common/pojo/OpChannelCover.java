package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 频道织图封面缩略图
 * </p>
 * 
 * 创建时间:2015-02-03
 * 
 * @author lynch
 *
 */
public class OpChannelCover implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4798374121162422091L;
	private Integer channelId;
	private String titleThumbPath;

	public OpChannelCover() {
		super();
	}

	public OpChannelCover(Integer channelId, String titleThumbPath) {
		super();
		this.channelId = channelId;
		this.titleThumbPath = titleThumbPath;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

}
