package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 系统弹幕POJO
 * </p>
 * 
 * 创建时间: 2015-05-15
 * 
 * @author lynch
 *
 */
public class OpChannelSysDanmuDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6586100222771128277L;

	private Integer recommendId;
	private String content;

	private Integer authorId;
	private String userAvatar;
	
	public OpChannelSysDanmuDto() {
		super();
	}

	public OpChannelSysDanmuDto(Integer recommendId, String content,
			Integer authorId, String userAvatar) {
		super();
		this.recommendId = recommendId;
		this.content = content;
		this.authorId = authorId;
		this.userAvatar = userAvatar;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

}
