package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * <p>
 * 织图评论用户对象
 * </p>
 * 
 * 创建时间：2014-1-2
 * @author ztj
 *
 */
public class HTWorldCommentUser implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7215779811755298106L;
	
	private Integer id;
	private Integer reId; // 被回复评论id
	private String content; // 内容
	private Date commentDate; // 评论时间
	private Integer worldId; // 世界ID
	private Integer userId; // 作者id
	
	private UserInfoDto userInfo = new UserInfoDto();
	
	private String remark;
	
	public HTWorldCommentUser() {
		super();
	}

	public HTWorldCommentUser(Integer id, Integer reId, String content,
			Date commentDate, Integer worldId, Integer userId, String userName,
			String userAvatar, String userAvatarL, Integer star, Integer platformVerify) {
		super();
		this.id = id;
		this.reId = reId;
		this.content = content;
		this.commentDate = commentDate;
		this.worldId = worldId;
		this.userId = userId;
		userInfo.setUserName(userName);
		userInfo.setUserAvatar(userAvatar);
		userInfo.setUserAvatarL(userAvatarL);
		userInfo.setStar(star);
		userInfo.setPlatformVerify(platformVerify);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	@Override
	public Integer getRemarkId() {
		return this.userId;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public Integer getVerifyId() {
		return userInfo.getStar();
	}

	@Override
	public void setVerifyName(String verifyName) {
		userInfo.setVerifyName(verifyName);
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		userInfo.setVerifyIcon(verifyIcon);
	}
	
}
