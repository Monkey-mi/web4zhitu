package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 织图文字样式POJO
 * </p>
 * 
 * 创建时间: 2015-05-13
 * @author lynch
 *
 */
public class HTWorldTextStyle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5921648494584830525L;
	private String color = "000000";
	private Integer mask = 0;
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getMask() {
		return mask;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

}
