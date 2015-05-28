package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 频道专题POJO
 * </p>
 * 
 * 创建时间: 2015-05-27
 * 
 * @author lynch
 *
 */
public class OpChannelTheme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3105067792987768946L;

	private Integer id;
	private String themeName;
	private Integer hasMore;
	
	public OpChannelTheme() {
		super();
	}
	
	public OpChannelTheme(Integer id, String themeName, Integer hasMore) {
		super();
		this.id = id;
		this.themeName = themeName;
		this.hasMore = hasMore;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Integer getHasMore() {
		return hasMore;
	}

	public void setHasMore(Integer hasMore) {
		this.hasMore = hasMore;
	}
	
}
