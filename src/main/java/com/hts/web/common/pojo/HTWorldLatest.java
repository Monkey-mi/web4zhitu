package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 最新织图POJO
 * </p>
 * 
 * 创建时间：2014-09-29
 * 
 * @author tianjie
 * 
 */
public class HTWorldLatest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6298269627418858025L;

	private Integer recommendId;
	private Integer id;
	private Integer authorId;
	private String titleThumbPath;
	private Integer interval;
	private Integer concerned;

	public HTWorldLatest() {
		super();
	}

	public HTWorldLatest(Integer id, Integer authorId, String titleThumbPath,
			Integer interval) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.titleThumbPath = titleThumbPath;
		this.interval = interval;
	}
	
	public HTWorldLatest(Integer recommendId,Integer id, Integer authorId, String titleThumbPath,
			Integer interval) {
		super();
		this.recommendId = recommendId;
		this.id = id;
		this.authorId = authorId;
		this.titleThumbPath = titleThumbPath;
		this.interval = interval;
	}
	
	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public Integer getConcerned() {
		return concerned;
	}

	public void setConcerned(Integer concerned) {
		this.concerned = concerned;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

}
