package com.hts.web.common.pojo;

public class HTWorldCount {

	private Integer id;
	private Integer likeCount;
	
	public HTWorldCount() {
		super();
	}

	public HTWorldCount(Integer id, Integer likeCount) {
		super();
		this.id = id;
		this.likeCount = likeCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

}
