package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 未读消息数量POJO
 * </p>
 * 
 * @author lynch 2015-10-24
 *
 */
public class UserMsgUnreadCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4013014543607386439L;

	private Integer userId;
	private Integer concernCount;
	private Integer likeCount;
	private Integer commentCount;
	private Integer atCount;
	private Integer sysmsgCount;
	private Integer umsgCount;

	private Integer sysmsgId;
	
	public UserMsgUnreadCount() {
		super();
	}

	public UserMsgUnreadCount(Integer userId, Integer concernCount, Integer likeCount, Integer commentCount,
			Integer atCount, Integer sysmsgCount, Integer umsgCount, Integer sysmsgId) {
		super();
		this.userId = userId;
		this.concernCount = concernCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.atCount = atCount;
		this.sysmsgCount = sysmsgCount;
		this.umsgCount = umsgCount;
		this.sysmsgId = sysmsgId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConcernCount() {
		return concernCount;
	}

	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
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

	public Integer getAtCount() {
		return atCount;
	}

	public void setAtCount(Integer atCount) {
		this.atCount = atCount;
	}

	public Integer getSysmsgCount() {
		return sysmsgCount;
	}

	public void setSysmsgCount(Integer sysmsgCount) {
		this.sysmsgCount = sysmsgCount;
	}

	public Integer getUmsgCount() {
		return umsgCount;
	}

	public void setUmsgCount(Integer umsgCount) {
		this.umsgCount = umsgCount;
	}

	public Integer getSysmsgId() {
		return sysmsgId;
	}

	public void setSysmsgId(Integer sysmsgId) {
		this.sysmsgId = sysmsgId;
	}
	

}
