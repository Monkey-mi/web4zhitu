package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

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
	private String userName; // 作者名字
	private String userAvatar; // 作者头像
	private String userAvatarL; // 作者大头像
	private Integer star = 0; // 明星标记
	private String verifyName;
	private String verifyIcon;
	private Integer platformVerify = Tag.VERIFY_NONE;
	
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
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.star = star;
		this.platformVerify = platformVerify;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserAvatarL() {
		return userAvatarL;
	}

	public void setUserAvatarL(String userAvatarL) {
		this.userAvatarL = userAvatarL;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getVerifyName() {
		return verifyName;
	}

	@Override
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	@Override
	public Integer getVerifyId() {
		return star;
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

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}
	
}
