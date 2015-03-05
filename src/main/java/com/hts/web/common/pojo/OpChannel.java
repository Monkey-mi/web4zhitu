package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 频道POJO
 * </p>
 * 
 * 创建时间:2014-10-29
 * 
 * @author lynch
 *
 */
public class OpChannel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5720016134600357550L;
	private Integer id;
	private String channelName;
	private String channelTitle;
	private String channelIcon;
	private String subIcon;
	private Integer childCount;
	private Integer serial;
	
	private List<String> titleThumbnails;

	public OpChannel() {
		super();
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

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}
	
	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public String getChannelIcon() {
		return channelIcon;
	}

	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}
	
	public String getSubIcon() {
		return subIcon;
	}

	public void setSubIcon(String subIcon) {
		this.subIcon = subIcon;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public List<String> getTitleThumbnails() {
		return titleThumbnails;
	}

	public void setTitleThumbnails(List<String> titleThumbnails) {
		this.titleThumbnails = titleThumbnails;
	}
	
}
