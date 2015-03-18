package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 字幕DTO
 * </p>
 * 
 * 创建时间:2015-03-14
 * 
 * @author lynch
 *
 */
public class HTWorldSubtitleDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8732760177536019167L;
	
	private Integer recommendId;
	private String subtitle;
	private String subtitleEn;
	private String transTo = "en";
	
	public HTWorldSubtitleDto() {
		super();
	}

	public HTWorldSubtitleDto(Integer recommendId, String subtitle,
			String subtitleEn, String transTo) {
		super();
		this.recommendId = recommendId;
		this.subtitle = subtitle;
		this.subtitleEn = subtitleEn;
		this.transTo = transTo;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSubtitleEn() {
		return subtitleEn;
	}

	public void setSubtitleEn(String subtitleEn) {
		this.subtitleEn = subtitleEn;
	}

	public String getTransTo() {
		return transTo;
	}

	public void setTransTo(String transTo) {
		this.transTo = transTo;
	}
	
}
