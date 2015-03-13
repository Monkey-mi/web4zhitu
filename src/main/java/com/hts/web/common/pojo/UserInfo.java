package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 用户信息POJO
 * </p>
 * 
 * 创建时间：2013-8-4
 * 
 * @author ztj
 * 
 */
public class UserInfo implements Serializable, ObjectWithUserVerifyDesc,
		ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7807414819468053714L;

	private Integer id;
	private Integer platformCode; // 社交平台代号
	private String platformToken; // 社交平台授权token
	private Long platformTokenExpires; // 社交平台授权过期时间
	private String platformSign; // 社交平台签名
	private Integer platformVerify = Tag.VERIFY_NONE; // 社交平台认证标记
	private String platformReason; // 社交平台认证原因
	private String loginCode;

	private byte[] passwordEncrypt;
	private String password;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer sex = Tag.SEX_UNKNOWN; // 性别
	private String email; // 邮箱
	private String address; // 地址
	private Date birthday; // 生日
	private String signature;
	private String userLabel;
	private String province;
	private String city;
	private Double longitude;
	private Double latitude;
	private Date registerDate;
	private Integer tradeId;
	private String job;
	private String pushToken;
	private Integer phoneCode;
	private String phoneSys; // 手机系统名
	private String phoneVer; // 手机系统版本号
	private Integer online = Tag.TRUE;
	private Integer acceptSysPush = Tag.TRUE; // 接收系统消息推送
	private Integer acceptCommentPush = Tag.TRUE; // 接收评论推送
	private Integer acceptReplyPush = Tag.TRUE; // 接收回复推送
	private Integer acceptLikedPush = Tag.TRUE; // 接收喜欢推送
	private Integer acceptKeepPush = Tag.TRUE; // 接收收藏推送
	private Integer acceptConcernPush = Tag.TRUE; // 接收关注推送
	private Integer acceptMsgPush = Tag.TRUE; // 接收系统私信推送
	private Integer acceptUmsgPush = Tag.TRUE; // 接收用户私信推送
	private Integer concernCount; // 关注总数
	private Integer followCount; // 粉丝总数
	private Integer worldCount; // 织图总数
	private Integer childCount; // 图片总数
	private Integer likedCount; // 喜欢总数
	private Integer likeMeCount; // 被喜欢数
	private Integer keepCount; // 收藏总数
	private Float ver; // app版本号
	private Integer star = Tag.FALSE; // 明星标记
	private String verifyName;
	private String verifyIcon;
	private String verifyDesc;
	private Integer isNewAdded = 0; // 是否为新添加的用户信息
	private Integer isMututal; // 是否互相关注

	private String remark;

	@Deprecated
	private List<Integer> socialAccounts; // 绑定的社交账号列表
	private List<UserSocialAccountDto> socialAccountInfo; // 绑定的社交账号详细信息

	public UserInfo() {
		super();
	}

	public UserInfo(Integer id, Integer platformCode, String platformToken,
			Long platformTokenExpires, String platformSign,
			Integer platformVerify, String platformReason, String loginCode, String userName,
			String userAvatar, String userAvatarL, Integer sex, String email,
			String address, Date birthday, String signature, String userLabel,
			String province, String city, Double longitude, Double latitude,
			Date registerDate, Integer tradeId, String job, String pushToken,
			Integer phoneCode, String phoneSys, String phoneVer,
			Integer online, Integer acceptSysPush, Integer acceptCommentPush,
			Integer acceptReplyPush, Integer acceptLikedPush,
			Integer acceptKeepPush, Integer acceptConcernPush,
			Integer acceptMsgPush, Integer acceptUmsgPush, Float ver,
			Integer concernCount, Integer followCount, Integer worldCount, Integer childCount,
			Integer likedCount, Integer likeMeCount, Integer keepCount, Integer star) {
		super();
		this.id = id;
		this.platformCode = platformCode;
		this.platformToken = platformToken;
		this.platformTokenExpires = platformTokenExpires;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.platformReason = platformReason;
		this.loginCode = loginCode;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.sex = sex;
		this.email = email;
		this.address = address;
		this.birthday = birthday;
		this.signature = signature;
		this.userLabel = userLabel;
		this.province = province;
		this.city = city;
		this.longitude = longitude;
		this.latitude = latitude;
		this.registerDate = registerDate;
		this.tradeId = tradeId;
		this.job = job;
		this.pushToken = pushToken;
		this.phoneCode = phoneCode;
		this.phoneSys = phoneSys;
		this.phoneVer = phoneVer;
		this.online = online;
		this.acceptSysPush = acceptSysPush;
		this.acceptCommentPush = acceptCommentPush;
		this.acceptReplyPush = acceptReplyPush;
		this.acceptLikedPush = acceptLikedPush;
		this.acceptKeepPush = acceptKeepPush;
		this.acceptConcernPush = acceptConcernPush;
		this.acceptMsgPush = acceptMsgPush;
		this.acceptUmsgPush = acceptUmsgPush;
		this.ver = ver;
		this.concernCount = concernCount;
		this.followCount = followCount;
		this.worldCount = worldCount;
		this.childCount = childCount;
		this.likedCount = likedCount;
		this.likeMeCount = likeMeCount;
		this.keepCount = keepCount;
		this.star = star;
	}

	public UserInfo(Integer id, Integer platformCode, String platformToken,
			Long platformTokenExpires, String platformSign,
			Integer platformVerify, String platformReason, String loginCode, String userName,
			String userAvatar, String userAvatarL, Integer sex,
			String signature, String address, String province, String city,
			Double longitude, Double latitude, Date registerDate,
			Integer tradeId, String job, String pushToken, Integer phoneCode,
			String phoneSys, String phoneVer, Integer online, Float ver) {
		super();
		this.id = id;
		this.platformCode = platformCode;
		this.platformToken = platformToken;
		this.platformTokenExpires = platformTokenExpires;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.platformReason = platformReason;
		this.loginCode = loginCode;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.sex = sex;
		this.signature = signature;
		this.address = address;
		this.province = province;
		this.city = city;
		this.longitude = longitude;
		this.latitude = latitude;
		this.registerDate = registerDate;
		this.tradeId = tradeId;
		this.job = job;
		this.pushToken = pushToken;
		this.phoneCode = phoneCode;
		this.phoneSys = phoneSys;
		this.phoneVer = phoneVer;
		this.online = online;
		this.ver = ver;
	}

	public Long getPlatformTokenExpires() {
		return platformTokenExpires;
	}

	public void setPlatformTokenExpires(Long platformTokenExpires) {
		this.platformTokenExpires = platformTokenExpires;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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
	
	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
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

	public String getPlatformToken() {
		return platformToken;
	}

	public void setPlatformToken(String platformToken) {
		this.platformToken = platformToken;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@JSON(deserialize = true)
	public byte[] getPasswordEncrypt() {
		return passwordEncrypt;
	}

	public void setPasswordEncrypt(byte[] passwordEncrypt) {
		this.passwordEncrypt = passwordEncrypt;
	}

	@JSON(deserialize = true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getPhoneSys() {
		return phoneSys;
	}

	public void setPhoneSys(String phoneSys) {
		this.phoneSys = phoneSys;
	}

	public String getPhoneVer() {
		return phoneVer;
	}

	public void setPhoneVer(String phoneVer) {
		this.phoneVer = phoneVer;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIsNewAdded() {
		return isNewAdded;
	}

	public void setIsNewAdded(Integer isNewAdded) {
		this.isNewAdded = isNewAdded;
	}

	public String getUserAvatarL() {
		return userAvatarL;
	}

	public void setUserAvatarL(String userAvatarL) {
		this.userAvatarL = userAvatarL;
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

	@JSON(format = "yyyy-MM-dd")
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

	public Integer getAcceptSysPush() {
		return acceptSysPush;
	}

	public List<Integer> getSocialAccounts() {
		return socialAccounts;
	}

	public void setSocialAccounts(List<Integer> socialAccounts) {
		this.socialAccounts = socialAccounts;
	}

	public List<UserSocialAccountDto> getSocialAccountInfo() {
		return socialAccountInfo;
	}

	public void setSocialAccountInfo(
			List<UserSocialAccountDto> socialAccountInfo) {
		this.socialAccountInfo = socialAccountInfo;
	}

	public void setAcceptSysPush(Integer acceptSysPush) {
		this.acceptSysPush = acceptSysPush;
	}

	public Integer getAcceptCommentPush() {
		return acceptCommentPush;
	}

	public void setAcceptCommentPush(Integer acceptCommentPush) {
		this.acceptCommentPush = acceptCommentPush;
	}

	public Integer getAcceptReplyPush() {
		return acceptReplyPush;
	}

	public void setAcceptReplyPush(Integer acceptReplyPush) {
		this.acceptReplyPush = acceptReplyPush;
	}

	public Integer getAcceptLikedPush() {
		return acceptLikedPush;
	}

	public void setAcceptLikedPush(Integer acceptLikedPush) {
		this.acceptLikedPush = acceptLikedPush;
	}

	public Integer getAcceptKeepPush() {
		return acceptKeepPush;
	}

	public void setAcceptKeepPush(Integer acceptKeepPush) {
		this.acceptKeepPush = acceptKeepPush;
	}

	public Integer getAcceptConcernPush() {
		return acceptConcernPush;
	}

	public void setAcceptConcernPush(Integer acceptConcernPush) {
		this.acceptConcernPush = acceptConcernPush;
	}

	public Integer getAcceptMsgPush() {
		return acceptMsgPush;
	}

	public void setAcceptMsgPush(Integer acceptMsgPush) {
		this.acceptMsgPush = acceptMsgPush;
	}

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}

	public Integer getAcceptUmsgPush() {
		return acceptUmsgPush;
	}

	public void setAcceptUmsgPush(Integer acceptUmsgPush) {
		this.acceptUmsgPush = acceptUmsgPush;
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

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
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

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	@Override
	public void setVerifyDesc(String verifyDesc) {
		this.verifyDesc = verifyDesc;
	}

	public String getVerifyDesc() {
		return verifyDesc;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Integer getRemarkId() {
		return id;
	}

	public Integer getLikeMeCount() {
		return likeMeCount;
	}

	public void setLikeMeCount(Integer likeMeCount) {
		this.likeMeCount = likeMeCount;
	}

	public String getPlatformReason() {
		return platformReason;
	}

	public void setPlatformReason(String platformReason) {
		this.platformReason = platformReason;
	}

}
