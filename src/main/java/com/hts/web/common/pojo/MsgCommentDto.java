package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 评论消息DTO,主要用于用户查看被评论消息列表
 * </p>
 * 
 * @version 3.0.5
 * @author lynch 2015-09-28
 *
 */
public class MsgCommentDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4743989742874474977L;
	private Integer id;
	private Integer worldId;
	private Date commentDate;
	private String content;
	private MsgCommentUserDto userInfo;
	private MsgCommentWorldDto htworld;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MsgCommentUserDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(MsgCommentUserDto userInfo) {
		this.userInfo = userInfo;
	}

	public MsgCommentWorldDto getHtworld() {
		return htworld;
	}

	public void setHtworld(MsgCommentWorldDto htworld) {
		this.htworld = htworld;
	}

}
