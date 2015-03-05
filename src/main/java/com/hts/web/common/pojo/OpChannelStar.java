package com.hts.web.common.pojo;

import java.io.Serializable;

public class OpChannelStar extends UserWorldBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6185971948227554625L;
	private Integer recommendId;

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

}
