package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 织图分类数据传输对象，没有互动信息
 * </p>
 * 
 * 创建时间：2013-10-17
 * 
 * @author ztj
 * 
 */
public class OpWorldTypeDto2 implements Serializable, ObjectWithUserVerify, ObjectWithLiked {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7418449788437718169L;

	private Integer recommendId; // 推荐id
	private Integer id; // 织图id
	private String shortLink;
	private Integer authorId; // 作者id
	private String worldDesc; // 织图描述
	private String titlePath; // 首页路径
	private String titleThumbPath; // 首页缩略图路径
	private Integer clickCount; // 播放次数
	private Integer likeCount; // 喜欢次数
	private Integer commentCount; // 评论次数
	private String worldLabel; // 织图标签
	private String worldType; // 织图分类
	private Integer typeId; // 分类id
	private Integer superb; // 精品标记
	private Integer squareLabel; // 广场分类标签代号(兼容2.7以前的版本)

	private String worldURL;

	private Integer concerned = Tag.FALSE; // 关注标记

	private String review;

	private Integer liked = Tag.FALSE; // 是否喜欢
	
	private UserInfoDto userInfo;

	public OpWorldTypeDto2() {
		super();
	}

	public OpWorldTypeDto2(Integer recommendId, Integer id, String shortLink,
			Integer authorId, String worldDesc, String titlePath,
			String titleThumbPath, Integer clickCount, Integer likeCount,
			Integer commentCount, String worldLabel, String worldType,
			Integer typeId, Integer superb, Integer squareLabel, String review) {
		super();
		this.recommendId = recommendId;
		this.id = id;
		this.shortLink = shortLink;
		this.authorId = authorId;
		this.worldDesc = worldDesc;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
		this.clickCount = clickCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.worldLabel = worldLabel;
		this.worldType = worldType;
		this.typeId = typeId;
		this.superb = superb;
		this.squareLabel = squareLabel;
		this.review = review;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getWorldLabel() {
		return worldLabel;
	}

	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public Integer getSquareLabel() {
		return squareLabel;
	}

	public void setSquareLabel(Integer squareLabel) {
		this.squareLabel = squareLabel;
	}

	public String getWorldDesc() {
		return worldDesc;
	}

	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}

	public String getWorldURL() {
		return worldURL;
	}

	public void setWorldURL(String worldURL) {
		this.worldURL = worldURL;
	}

	public Integer getConcerned() {
		return concerned;
	}

	public void setConcerned(Integer concerned) {
		this.concerned = concerned;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
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
	
	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getLiked() {
		return liked;
	}

	@Override
	public void setLiked(Integer liked) {
		this.liked = liked;
	}

	@Override
	public Integer getWorldId() {
		return id;
	}
	
}
