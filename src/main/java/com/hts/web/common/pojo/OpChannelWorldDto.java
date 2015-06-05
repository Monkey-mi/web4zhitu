package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hts.web.base.constant.Tag;

public class OpChannelWorldDto extends HTWorldBase implements Serializable, 
	HTWorldWithExtra, ObjectWithUserVerify, ObjectWithConcerned, ObjectWithUserRemark {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 157956695027833615L;
	
	private Integer recommendId;
	private Integer superb = Tag.FALSE;
	private UserInfoDto userInfo;
	private Integer concerned = Tag.FALSE;
	
	private List<HTWorldLikedUser> likes = new ArrayList<HTWorldLikedUser>(); // 喜欢的用户
	private List<HTWorldCommentUser> comments = new ArrayList<HTWorldCommentUser>(); // 评论
	
	public OpChannelWorldDto() {
		super();
	}

	public OpChannelWorldDto(Integer id, String shortLink, Integer authorId,
			String worldName, String worldDesc, String worldLabel,
			String worldType, Integer typeId, Date dateAdded,
			Date dateModified, Integer clickCount, Integer likeCount,
			Integer commentCount, Integer keepCount, String coverPath,
			String titlePath, String bgPath, String titleThumbPath, String thumbs,
			Double longitude, Double latitude, String locationDesc,
			String locationAddr, Integer phoneCode, String province,
			String city, Integer size, Integer childCount, Integer ver, Integer tp,
			Integer valid, Integer shield, HTWorldTextStyle textStyle, String worldURL) {
		
		super(id, shortLink, authorId, worldName, worldDesc, worldLabel,
				worldType, typeId, dateAdded, dateModified, clickCount,
				likeCount, commentCount, keepCount, coverPath, titlePath,
				bgPath, titleThumbPath, longitude, latitude, locationDesc,
				locationAddr, phoneCode, province, city, size, childCount, ver, tp,
				valid, shield, textStyle, worldURL);
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
	
	public List<HTWorldLikedUser> getLikes() {
		return likes;
	}

	public void setLikes(List<HTWorldLikedUser> likes) {
		this.likes = likes;
	}


	public List<HTWorldCommentUser> getComments() {
		return comments;
	}


	public void setComments(List<HTWorldCommentUser> comments) {
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
	
	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
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
	
}
