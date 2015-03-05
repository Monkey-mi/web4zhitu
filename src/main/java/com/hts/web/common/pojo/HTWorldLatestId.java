package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * 最新id
 * 
 * 创建时间: 2014-12-24
 * 
 * @author lynch
 *
 */
public class HTWorldLatestId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4729050091438595807L;

	private Integer recommendId;
	private Integer id;
	
	public HTWorldLatestId() {
		super();
	}

	public HTWorldLatestId(Integer recommendId, Integer id) {
		super();
		this.recommendId = recommendId;
		this.id = id;
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

}
