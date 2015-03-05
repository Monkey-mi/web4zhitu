package com.hts.web.common.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 运营模块用户信息POJO
 * </p>
 * 
 * 创建时间：2013-8-29
 * 
 * @author ztj
 * 
 */
public class OpUser implements UserWithWorld, ObjectWithUserVerify,
		ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8066391663965134736L;

	private Integer recommendId; // 推荐id
	private Integer id; // 用户id
	private Integer platformCode;
	private String platformSign; // 社交平台签名
	private Integer platformVerify; // 社交平台验证标记
	private String platformReason;

	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer sex = Tag.SEX_UNKNOWN; // 性别
	private String email; // 邮箱
	private String address; // 地址
	private String province; // 省份
	private String city; // 城市
	private Date birthday; // 生日
	private String signature;
	private String userLabel;
	private Date registerDate;
	private Integer online;
	private Integer concernCount; // 关注总数
	private Integer followCount; // 粉丝总数
	private Integer worldCount; // 织图总数
	private Integer likedCount; // 喜欢总数
	private Integer keepCount; // 收藏总数
	private Integer shield; // 屏蔽标记
	private Integer star = Tag.FALSE; // 明星标记
	private String verifyName;
	private String verifyIcon;
	private Integer trust = Tag.FALSE; // 信任标记
	private Integer activity = 0;
	private Integer isMututal = -1; // 是否互相关注

	private String recommendDesc; // 推荐描述
	private String recommender; // 推荐人
	private Date recommendDate; // 推荐日期
	private Integer lastPos = 0; // 最后一次排名
	private Integer lastVerifyPos = 0; // 最后一次分榜排名
	private Integer currPos = 0; // 当前排名
	private Integer currVerifyPos = 0; // 当前分榜排名

	private Integer fixPos = 0;// 固定排名

	private String remark;

	private List<HTWorldThumbUser> htworld = new ArrayList<HTWorldThumbUser>();

	public OpUser() {
		super();
	}

	public OpUser(Integer id, Integer platformCode, String platformSign,
			Integer platformVerify, String platformReason, String userName,
			String userAvatar, String userAvatarL, Integer sex, String email,
			String address, String province, String city, Date birthday,
			String signature, String userLabel, Date registerDate,
			Integer online, Integer concernCount, Integer followCount,
			Integer worldCount, Integer likedCount, Integer keepCount,
			Integer shield, Integer star, Integer trust, Integer activity) {
		this.id = id;
		this.platformCode = platformCode;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.platformReason = platformReason;
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
		this.online = online;
		this.concernCount = concernCount;
		this.followCount = followCount;
		this.worldCount = worldCount;
		this.likedCount = likedCount;
		this.keepCount = keepCount;
		this.shield = shield;
		this.star = star;
		this.trust = trust;
		this.activity = activity;
	}

	// 有固定排名的
	public OpUser(Integer recommendId, Integer id, Integer platformCode,
			String platformSign, Integer platformVerify, String platformReason,
			Date recommendDate, String recommendDesc, String userName,
			String userAvatar, String userAvatarL, Integer sex, String email,
			String address, String province, String city, Date birthday,
			String signature, String userLabel, Date registerDate,
			Integer online, Integer concernCount, Integer followCount,
			Integer worldCount, Integer likedCount, Integer keepCount,
			Integer shield, Integer star, Integer activity, Integer lastPos,
			Integer lastVerifyPos, Integer fixPox) {
		this.recommendId = recommendId;
		this.recommendDate = recommendDate;
		this.recommendDesc = recommendDesc;
		this.id = id;
		this.platformCode = platformCode;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.platformReason = platformReason;
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
		this.online = online;
		this.concernCount = concernCount;
		this.followCount = followCount;
		this.worldCount = worldCount;
		this.likedCount = likedCount;
		this.keepCount = keepCount;
		this.shield = shield;
		this.star = star;
		this.activity = activity;
		this.lastPos = lastPos;
		this.lastVerifyPos = lastVerifyPos;
		this.fixPos = fixPox;
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

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
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

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
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

	public Integer getTrust() {
		return trust;
	}

	public void setTrust(Integer trust) {
		this.trust = trust;
	}

	public Integer getLastPos() {
		return lastPos;
	}

	public void setLastPos(Integer lastPos) {
		this.lastPos = lastPos;
	}

	public Integer getCurrPos() {
		return currPos;
	}

	public void setCurrPos(Integer currPos) {
		this.currPos = currPos;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public List<HTWorldThumbUser> getHtworld() {
		return htworld;
	}

	public void setHtworld(List<HTWorldThumbUser> htworld) {
		this.htworld = htworld;
	}

	public Integer getLastVerifyPos() {
		return lastVerifyPos;
	}

	public void setLastVerifyPos(Integer lastVerifyPos) {
		this.lastVerifyPos = lastVerifyPos;
	}

	public Integer getCurrVerifyPos() {
		return currVerifyPos;
	}

	public void setCurrVerifyPos(Integer currVerifyPos) {
		this.currVerifyPos = currVerifyPos;
	}

	public void setFixPos(Integer fixPos) {
		this.fixPos = fixPos;
	}

	public Integer getFixPos() {
		return this.fixPos;
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

	@Override
	public Integer getRemarkId() {
		return id;
	}

	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlatformReason() {
		return platformReason;
	}

	public void setPlatformReason(String platformReason) {
		this.platformReason = platformReason;
	}

}
