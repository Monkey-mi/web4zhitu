package com.hts.web.common.pojo;

public class HTWorldCount {

	private Integer id;
	private Integer likeCount;
	private Integer clickCount;
	private Integer commentCount;
	
	public HTWorldCount() {
		super();
	}

	public HTWorldCount(Integer id, Integer likeCount, 
			Integer clickCount, Integer commentCount) {
		super();
		this.id = id;
		this.likeCount = likeCount;
		this.clickCount = clickCount;
		this.commentCount = commentCount;
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

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

}
