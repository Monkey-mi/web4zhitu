package com.hts.web.common.pojo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.hts.web.base.constant.Tag;

/**
 * 附近织图POJO
 * 
 * @author lynch 2015-12-05
 *
 */
public class OpNearWorldDto extends HTWorldBase
		implements Serializable, HTWorldWithExtra, ObjectWithUserVerify, ObjectWithConcerned, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3018658357158789697L;
	private Integer recommendId; // 兼容以前的接口, 实际赋值为标签排序字段
	private UserInfoDto userInfo;
	private Integer concerned = Tag.FALSE;
	private byte[] worldDescByte;
	
	private Integer cityId = 0;
	
	private Double[] loc;
	
	private List<HTWorldLikedInline> likes = new ArrayList<HTWorldLikedInline>(); // 喜欢的用户
	private List<HTWorldCommentInline> comments = new ArrayList<HTWorldCommentInline>(); // 评论

	public OpNearWorldDto() {
		super();
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}


	public UserInfoDto getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}
	
	public List<HTWorldLikedInline> getLikes() {
		return likes;
	}

	public void setLikes(List<HTWorldLikedInline> likes) {
		this.likes = likes;
	}


	public List<HTWorldCommentInline> getComments() {
		return comments;
	}


	public void setComments(List<HTWorldCommentInline> comments) {
		this.comments = comments;
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
	
	public Integer getConcerned() {
		return concerned;
	}

	@Override
	public void setConcerned(Integer concerned) {
		this.concerned = concerned;
	}

	@Override
	public Integer getRemarkId() {
		return userInfo.getId();
	}

	@Override
	public void setRemark(String remark) {
		userInfo.setRemark(remark);
	}

	public Double[] getLoc() {
		return loc;
	}

	public void setLoc(Double[] loc) {
		this.loc = loc;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public byte[] getWorldDescByte() {
		return worldDescByte;
	}

	public void setWorldDescByte(byte[] worldDescByte) {
		this.worldDescByte = worldDescByte;
		try {
			this.worldDesc = new String(worldDescByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
	}
	
}
