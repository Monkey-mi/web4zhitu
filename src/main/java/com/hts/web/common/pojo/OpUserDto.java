package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * 
 * @author ztj
 * 
 */
public class OpUserDto implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6711032001488246064L;

	private Integer recommendId; // 推荐id
	private Integer id;
	private Integer platformCode; // 社交平台代号
	private String platformSign; // 社交平台签名
	private Integer platformVerify; //社交平台验证标记

	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer sex = Tag.SEX_UNKNOWN; // 性别
	private String email; // 邮箱
	private String address; // 地址
	private String province;
	private String city;
	private Date birthday; // 生日
	private String signature;
	private String userLabel;
	private Date registerDate;
	private Integer phoneCode;
	private Integer online = Tag.TRUE;
	private Integer concernCount; // 关注总数
	private Integer followCount; // 粉丝总数
	private Integer worldCount; // 织图总数
	private Integer likedCount; // 喜欢总数
	private Integer keepCount; // 收藏总数
	private Integer shield; // 是否屏蔽
	private Integer star = Tag.FALSE; // 明星标记
	private String verifyName;
	private String verifyIcon;
	private Integer trust = Tag.FALSE; // 信任标记
	private Integer activity = 0;
	
	private String recommender; // 推荐人
	private Date recommendDate; // 推荐日期
	private String recommendDesc; // 推荐描述
	private Integer recommendValid;

	private Object msg = 0; // 是否私信了
	
	private String remark;

	public OpUserDto() {
		super();
	}

	/**
	 * 
	 * @param recommendId
	 * @param id
	 * @param recommender
	 * @param recommendDate
	 * @param recommendDesc
	 * @param recommendValid
	 * @param platformCode
	 * @param platformSign
	 * @param platformVerify
	 * @param userName
	 * @param userAvatar
	 * @param userAvatarL
	 * @param sex
	 * @param email
	 * @param address
	 * @param province
	 * @param city
	 * @param birthday
	 * @param signature
	 * @param userLabel
	 * @param registerDate
	 * @param phoneCode
	 * @param online
	 * @param concernCount
	 * @param followCount
	 * @param worldCount
	 * @param likedCount
	 * @param keepCount
	 * @param star
	 * @param trust
	 * @param msg
	 */
	public OpUserDto(Integer recommendId, Integer id,
			String recommender, Date recommendDate, String recommendDesc,
			Integer recommendValid, Integer platformCode, String platformSign, Integer platformVerify, String userName,
			String userAvatar, String userAvatarL, Integer sex, String email,
			String address, String province, String city, Date birthday,
			String signature, String userLabel, Date registerDate,
			Integer phoneCode, Integer online, Integer concernCount,
			Integer followCount, Integer worldCount, Integer likedCount,
			Integer keepCount, Integer star, Integer trust, Integer activity, Object msg) {
		this.recommendId = recommendId;
		this.id = id;
		this.recommender = recommender;
		this.recommendDate = recommendDate;
		this.recommendDesc = recommendDesc;
		this.recommendValid = recommendValid;
		this.platformCode = platformCode;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.sex = sex;
		this.email = email;
		this.address = address;
		this.province = province;
		this.city = city;
		this.birthday = birthday;
		this.signature = signature;
		this.userLabel = userLabel;
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
		this.activity = activity;
		this.msg = msg;
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
	
	public String getPlatformSign() {
		return platformSign;
	}

	public void setPlatformSign(String platformSign) {
		this.platformSign = platformSign;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	public Integer getRecommendValid() {
		return recommendValid;
	}

	public void setRecommendValid(Integer recommendValid) {
		this.recommendValid = recommendValid;
	}

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
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

	public Integer getTrust() {
		return trust;
	}

	public void setTrust(Integer trust) {
		this.trust = trust;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Integer getRemarkId() {
		return id;
	}
	
}
