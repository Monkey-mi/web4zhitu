package com.hts.web.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 评论消息POJO
 * </p>
 * 
 * @author lynch 2015-10-16
 *
 */
public class MsgComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6111760336023313396L;
	private Integer commentId;
	private Integer authorId;
	private Integer receiveId;
	private Integer worldId;
	
	public MsgComment() {
		super();
	}

	public MsgComment(Integer commentId, Integer authorId, 
			Integer receiveId, Integer worldId) {
		super();
		this.commentId = commentId;
		this.authorId = authorId;
		this.receiveId = receiveId;
		this.worldId = worldId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

}
