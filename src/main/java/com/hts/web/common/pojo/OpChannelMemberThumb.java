package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 频道成员缩略图
 * </p>
 * 
 * 创建时间:2015-05-05
 * 
 * @author lynch
 *
 */
public class OpChannelMemberThumb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105003103102410832L;

	private Integer recommendId;
	private Integer id;
	private Integer channelId;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer platformVerify = Tag.VERIFY_NONE;
	private Integer star = Tag.FALSE; // 明星标记

	private String verifyName;
	private String verifyIcon;

	public OpChannelMemberThumb(Integer recommendId, 
			Integer id, Integer channelId, String userName,
			String userAvatar, String userAvatarL, Integer platformVerify, 
			Integer star) {
		super();
		this.recommendId = recommendId;
		this.id = id;
		this.channelId = channelId;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.star = star;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
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

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}

}
