package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hts.web.base.constant.Tag;

public class OpUserZombie implements Serializable,ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8740825801377549646L;
	private Integer recommendId; // 推荐id
	private Integer id;
	private Integer platformCode; // 社交平台代号

	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer sex = Tag.SEX_UNKNOWN; // 性别
	private String email; // 邮箱
	private String address; // 地址
	private Date birthday; // 生日
	private String signature;
	private Date registerDate;
	private Integer phoneCode;
	private Integer online = Tag.TRUE;
	private Integer concernCount; // 关注总数
	private Integer followCount; // 粉丝总数
	private Integer worldCount; // 织图总数
	private Integer likedCount; // 喜欢总数
	private Integer keepCount; // 收藏总数
	private Integer star;  // 明星标记
	private String verifyName;
	private String verifyIcon;
	
	private Integer likeMeCount;	//被别人赞的总数
	private String job;				//职业
	private String province;		//省份
	private String city;			//城市
	
	private Integer platformVerify = Tag.VERIFY_NONE;
	
	private Integer trust; // 信任标记
	private Integer shield; // 是否屏蔽

	private String recommender; // 推荐人
	private Date recommendDate; // 推荐日期
	
	private String remark;

	public OpUserZombie() {
		super();
	}
	public OpUserZombie(Integer recommendId, Integer id,
			Integer platformCode, String userName, String userAvatar,
			String userAvatarL, Integer sex, String email, String address,
			Date birthday, String signature, Date registerDate,
			Integer phoneCode, Integer online, Integer concernCount,
			Integer followCount, Integer worldCount, Integer likedCount,
			Integer keepCount, Integer star, Integer trust, 
			Integer shield, String recommender, Date recommendDate, Integer platformVerify) {
		this.recommendId = recommendId;
		this.id = id;
		this.platformCode = platformCode;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.sex = sex;
		this.email = email;
		this.address = address;
		this.birthday = birthday;
		this.signature = signature;
		this.registerDate = registerDate;
		this.phoneCode = phoneCode;
		this.online = online;
		this.concernCount = concernCount;
		this.followCount = followCount;
		this.worldCount = worldCount;
		this.likedCount = likedCount;
		this.keepCount = keepCount;
		this.star = star;
		this.trust = trust;
		this.shield = shield;
		this.recommender = recommender;
		this.recommendDate = recommendDate;
		this.platformVerify = platformVerify;
	}
	
	public OpUserZombie(Integer recommendId, Integer id,
			Integer platformCode, String userName, String userAvatar,
			String userAvatarL, Integer sex, String email, String address,
			Date birthday, String signature, Date registerDate,
			Integer phoneCode, Integer online, Integer concernCount,
			Integer followCount, Integer worldCount, Integer likedCount,
			Integer keepCount, Integer star, Integer trust, 
			Integer shield, String recommender, Date recommendDate, Integer platformVerify,
			Integer likeMeCount,
			String job,String province,String city) {
		this.recommendId = recommendId;
		this.id = id;
		this.platformCode = platformCode;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.sex = sex;
		this.email = email;
		this.address = address;
		this.birthday = birthday;
		this.signature = signature;
		this.registerDate = registerDate;
		this.phoneCode = phoneCode;
		this.online = online;
		this.concernCount = concernCount;
		this.followCount = followCount;
		this.worldCount = worldCount;
		this.likedCount = likedCount;
		this.keepCount = keepCount;
		this.star = star;
		this.trust = trust;
		this.shield = shield;
		this.recommender = recommender;
		this.recommendDate = recommendDate;
		this.platformVerify = platformVerify;
		this.likeMeCount = likeMeCount;
		this.job	= job;
		this.province = province;
		this.city = city;
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

	public Integer getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(Integer platformCode) {
		this.platformCode = platformCode;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getConcernCount() {
		return concernCount;
	}

	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Integer getWorldCount() {
		return worldCount;
	}

	public void setWorldCount(Integer worldCount) {
		this.worldCount = worldCount;
	}

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public Integer getKeepCount() {
		return keepCount;
	}

	public void setKeepCount(Integer keepCount) {
		this.keepCount = keepCount;
	}

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	public Date getRecommendDate() {
		return recommendDate;
	}

	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
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

	public Integer getTrust() {
		return trust;
	}

	public void setTrust(Integer trust) {
		this.trust = trust;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Integer getRemarkId() {
		return id;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}

	public Integer getLikeMeCount() {
		return likeMeCount;
	}

	public void setLikeMeCount(Integer likeMeCount) {
		this.likeMeCount = likeMeCount;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
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
	
}
