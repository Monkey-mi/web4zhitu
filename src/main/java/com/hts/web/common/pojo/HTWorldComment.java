package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 汇图世界评论POJO
 * </p>
 * 
 * 创建时间：2012-11-15
 * 
 * @author ztj
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
	private Integer worldAuthorId; // 织图作者id
	private Integer reId; // 被回复评论id
	private Integer reAuthorId; // 被回复评论作者id
	private Integer ck; // 被评论织图作者是否查看过标记
	private Integer valid; // 有效标记
	private Integer shield; // 被屏蔽标识，0为屏蔽，1为屏蔽

	public HTWorldComment() {
		super();
	}

	public HTWorldComment(Integer id, Integer authorId, String content,
			Date commentDate, Integer worldId, Integer worldAuthorId, Integer reId, Integer reAuthorId, Integer ck, Integer valid,
			Integer shield) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.content = content;
		this.commentDate = commentDate;
		this.worldId = worldId;
		this.worldAuthorId = worldAuthorId;
		this.reId = reId;
		this.reAuthorId = reAuthorId;
		this.ck = ck;
		this.valid = valid;
		this.shield = shield;
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

	public Integer getCK() {
		return ck;
	}

	public void setCK(Integer ck) {
		this.ck = ck;
	}

	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Integer getCk() {
		return ck;
	}

	public void setCk(Integer ck) {
		this.ck = ck;
	}

	public Integer getWorldAuthorId() {
		return worldAuthorId;
	}

	public void setWorldAuthorId(Integer worldAuthorId) {
		this.worldAuthorId = worldAuthorId;
	}

	public Integer getReAuthorId() {
		return reAuthorId;
	}

	public void setReAuthorId(Integer reAuthorId) {
		this.reAuthorId = reAuthorId;
	}

}
