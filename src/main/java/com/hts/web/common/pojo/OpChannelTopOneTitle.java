package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 频道Top One标题POJO
 * </p>
 * 
 * 创建时间：2014-11-06
 * @author lynch
 *
 */
public class OpChannelTopOneTitle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7256580952579281566L;
	
	private String text;
	private String dateInterval;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDateInterval() {
		return dateInterval;
	}
	public void setDateInterval(String dateInterval) {
		this.dateInterval = dateInterval;
	}
	
	

}
