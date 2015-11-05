package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 织图评论数据传输对象
 * </p>
 * 
 * 
 * @author ztj 2013-8-5 2015-11-04
 * 
 */
public class HTWorldCommentDto implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3089176801041691951L;

	private Integer id;
	private Integer authorId; // 作者id
	private Integer reAuthorId; //　被回复作者id
	private String content; // 内容
	private Date commentDate; // 评论时间
	private Integer worldId; // 世界ID
	private UserInfoDto userInfo; // 用户信息
	
	public HTWorldCommentDto() {
		super();
	}

	public HTWorldCommentDto(Integer id, Integer authorId, 
			Integer reAuthorId, String content,
			Date commentDate, Integer worldId) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.reAuthorId = reAuthorId;
		this.content = content;
		this.commentDate = commentDate;
		this.worldId = worldId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
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
	
	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public Integer getVerifyId() {
		return this.userInfo.getStar();
	}

	@Override
	public void setVerifyName(String verifyName) {
		this.userInfo.setVerifyName(verifyName);
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		this.userInfo.setVerifyIcon(verifyIcon);
	}
	
	@Override
	public Integer getRemarkId() {
		return userInfo.getId();
	}
	
	@Override
	public void setRemark(String remark) {
		userInfo.setRemark(remark);
	}

	public Integer getReAuthorId() {
		return reAuthorId;
	}

	public void setReAuthorId(Integer reAuthorId) {
		this.reAuthorId = reAuthorId;
	}
	
}
