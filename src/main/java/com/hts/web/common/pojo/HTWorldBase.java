package com.hts.web.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

public abstract class HTWorldBase {

	protected Integer id;
	protected String shortLink;
	protected Integer authorId;
	protected String worldName; // 世界名称
	protected String worldDesc; // 世界描述
	protected String worldLabel; // 世界标签
	protected String worldType; // 世界分类
	protected Integer typeId; // 分类id
	protected Date dateAdded; // 添加时间
	protected Date dateModified; // 创建时间
	protected Integer clickCount = 0; // 查看次数
	protected Integer likeCount = 0; // 被“赞”次数
	protected Integer commentCount = 0; // 被评论次数
	protected Integer keepCount = 0; // 被收藏次数
	protected String coverPath; // 封面路径
	protected String titlePath; // 首页路径
	protected String bgPath;
	protected String titleThumbPath; // 首页缩略图路径
	protected Double longitude;// 经度
	protected Double latitude;// 纬度
	protected String locationDesc;// 位置描述
	protected String locationAddr; // 位置地址
	protected Integer phoneCode;// 手机辨别代号
	protected String province;// 所在省份
	protected String city;// 所在城市
	protected Integer size; // 织图大小
	protected Integer childCount; // 子世界总数
	protected Integer ver; // 织图版本
	protected Integer tp = Tag.WORLD_TYPE_DEFAULT; //织图分类
	protected Integer valid; // 是否有效标志
	protected Integer shield; // 是否被屏蔽
	protected String worldURL; // 连接路径
	
	private Object liked = 0; // 是否喜欢
	private Object keep = 0; // 是否收藏
	
	public HTWorldBase() {
		super();
	}

	public HTWorldBase(Integer id, String shortLink, Integer authorId,
			String worldName, String worldDesc, String worldLabel,
			String worldType, Integer typeId, Date dateAdded,
			Date dateModified, Integer clickCount, Integer likeCount,
			Integer commentCount, Integer keepCount, String coverPath,
			String titlePath, String bgPath, String titleThumbPath,
			Double longitude, Double latitude, String locationDesc,
			String locationAddr, Integer phoneCode, String province,
			String city, Integer ver, Integer tp, Integer size, Integer childCount,
			Integer valid, Integer shield, String worldURL) {
		super();
		this.id = id;
		this.shortLink = shortLink;
		this.authorId = authorId;
		this.worldName = worldName;
		this.worldDesc = worldDesc;
		this.worldLabel = worldLabel;
		this.worldType = worldType;
		this.typeId = typeId;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.clickCount = clickCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.keepCount = keepCount;
		this.coverPath = coverPath;
		this.titlePath = titlePath;
		this.bgPath = bgPath;
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
		this.tp = tp;
		this.valid = valid;
		this.shield = shield;
		this.worldURL = worldURL;
		
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

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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

	public Integer getKeepCount() {
		return keepCount;
	}

	public void setKeepCount(Integer keepCount) {
		this.keepCount = keepCount;
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

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
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

	public String getWorldURL() {
		return worldURL;
	}

	public void setWorldURL(String worldURL) {
		this.worldURL = worldURL;
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

	public String getBgPath() {
		return bgPath;
	}

	public void setBgPath(String bgPath) {
		this.bgPath = bgPath;
	}

	public Integer getTp() {
		return tp;
	}

	public void setTp(Integer tp) {
		this.tp = tp;
	}
	
}
