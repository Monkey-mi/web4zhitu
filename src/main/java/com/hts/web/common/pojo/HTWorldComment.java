package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 汇图世界评论POJO
 * </p>
 * 
 * @author ztj 2012-11-15 2015-11-03
 * 
 */
public class HTWorldComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4493586854877785377L;
	private Integer id;
	private Integer authorId; // 作者id
	private String content; // 内容
	private Date commentDate; // 评论时间
	private Integer worldId; // 世界ID
	private Integer reAuthorId; // 被回复评论作者id

	public HTWorldComment() {
		super();
	}

	public HTWorldComment(Integer id, Integer authorId, String content,
			Date commentDate, Integer worldId,
			Integer reAuthorId) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.content = content;
		this.commentDate = commentDate;
		this.worldId = worldId;
		this.reAuthorId = reAuthorId;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getReAuthorId() {
		return reAuthorId;
	}

	public void setReAuthorId(Integer reAuthorId) {
		this.reAuthorId = reAuthorId;
	}

}
