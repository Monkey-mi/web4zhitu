package com.hts.web.common.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 推荐织图传输对象
 * </p>
 * 
 * 创建时间：2012-12-13
 * 
 * @author ztj
 * 
 */
public class OpWorldTypeDto implements HTWorldWithExtra, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1081514059974397934L;

	private Integer recommendId;
	private Integer id; // 织图id
	private String shortLink; // 织图短链
	private String worldName; // 世界名称
	private String worldDesc; // 世界描述
	private String worldLabel; // 世界标签
	private String worldType; // 世界分类
	private Integer typeId; // 分类id
	private Date dateAdded; // 添加时间
	private Date dateModified; // 创建时间
	private Integer authorId; // 作者id
	private Integer clickCount; // 查看次数
	private Integer likeCount; // 被“赞”次数
	private Integer commentCount; // 被评论次数
	private Integer keepCount; // 收藏次数
	private String coverPath; // 封面路径
	private String titlePath; // 首页路径
	private String titleThumbPath; // 首页缩略图路径
	private Double longitude;// 经度
	private Double latitude;// 纬度
	private String locationDesc;// 位置描述
	private String locationAddr; // 位置地址
	private Integer phoneCode;// 手机辨别代号
	private String province;// 所在省份
	private String city;// 所在城市
	private Integer size; // 织图大小
	private Integer childCount; // 子世界张数
	private Integer ver; // 织图版本
	private Integer valid;
	private Integer shield;
	private String worldURL;

	private Integer superb;
	private Integer squareLabel;
	private Object liked = 0;
	private Object keep = 0;
	
	private Integer isTutorial = Tag.FALSE;
	private Integer concerned = Tag.FALSE;

	@Deprecated
	private Integer worldId; // 兼容2.0以前的版本
	@Deprecated
	private String recommender; // 兼容2.7以前的版本
	@Deprecated
	private Date recommendDate; // 兼容2.7以前的版本

	private UserInfoDto userInfo;

	private List<HTWorldLikedUser> likes = new ArrayList<HTWorldLikedUser>(); // 喜欢的用户
	private List<HTWorldCommentUser> comments = new ArrayList<HTWorldCommentUser>(); // 评论

	public OpWorldTypeDto() {
		super();
	}

	public OpWorldTypeDto(Integer recommendId, Integer id, Integer superb,
			Integer squareLabel, String shortLink, String worldName,
			String worldDesc, String worldLabel, String worldType,
			Integer typeId, Date dateAdded, Date dateModified,
			Integer authorId, Integer clickCount, Integer likeCount,
			Integer commentCount, Integer keepCount, String coverPath,
			String titlePath, String titleThumbPath, Double longitude,
			Double latitude, String locationDesc, String locationAddr,
			Integer phoneCode, String province, String city, Integer size,
			Integer childCount, Integer ver) {
		super();
		this.recommendId = recommendId;
		this.id = id;
		this.superb = superb;
		this.squareLabel = squareLabel;
		this.shortLink = shortLink;
		this.worldName = worldName;
		this.worldDesc = worldDesc;
		this.worldLabel = worldLabel;
		this.worldType = worldType;
		this.typeId = typeId;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.authorId = authorId;
		this.clickCount = clickCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.keepCount = keepCount;
		this.coverPath = coverPath;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
		this.longitude = longitude;
		this.latitude = latitude;
		this.locationDesc = locationDesc;
		this.locationAddr = locationAddr;
		this.phoneCode = phoneCode;
		this.province = province;
		this.city = city;
		this.size = size;
		this.childCount = childCount;
		this.ver = ver;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getKeepCount() {
		return keepCount;
	}

	public void setKeepCount(Integer keepCount) {
		this.keepCount = keepCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public String getWorldDesc() {
		return worldDesc;
	}

	public void setWorldDesc(String worldDesc) {
		this.worldDesc = worldDesc;
	}

	public String getWorldLabel() {
		return worldLabel;
	}

	public void setWorldLabel(String worldLabel) {
		this.worldLabel = worldLabel;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
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

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
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

	public String getWorldURL() {
		return worldURL;
	}

	public void setWorldURL(String worldURL) {
		this.worldURL = worldURL;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRecommendDate() {
		return recommendDate;
	}

	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}

	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Object getLiked() {
		return liked;
	}

	public void setLiked(Object liked) {
		this.liked = liked;
	}

	public Object getKeep() {
		return keep;
	}

	public void setKeep(Object keep) {
		this.keep = keep;
	}
	
	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}
	
	public Integer getIsTutorial() {
		return isTutorial;
	}

	public void setIsTutorial(Integer isTutorial) {
		this.isTutorial = isTutorial;
	}
	
	public Integer getConcerned() {
		return concerned;
	}

	public void setConcerned(Integer concerned) {
		this.concerned = concerned;
	}

	@Override
	public List<HTWorldLikedUser> getLikes() {
		return likes;
	}

	public void setLikes(List<HTWorldLikedUser> likes) {
		this.likes = likes;
	}

	@Override
	public List<HTWorldCommentUser> getComments() {
		return comments;
	}

	public void setComments(List<HTWorldCommentUser> comments) {
		this.comments = comments;
	}

	@Override
	public Integer getVerifyId() {
		return userInfo.getVerifyId();
	}

	@Override
	public void setVerifyName(String verifyName) {
		userInfo.setVerifyName(verifyName);
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		userInfo.setVerifyIcon(verifyIcon);
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
