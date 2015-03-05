package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 用户关注运营POJO信息
 * </p>
 * 
 * 创建时间：2013-9-12
 * 
 * @author ztj
 * 
 */
public class OpUserConcernDto implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1690933396912037190L;

	private Integer id; // 推荐id
	private Integer userId;
	private Integer concernId;
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
	
	private Integer platformVerify = Tag.VERIFY_NONE;
	
	private String remark;
	
	protected OpUserConcernDto() {
		super();
	}

	public OpUserConcernDto(Integer id, Integer userId, Integer concernId,
			String userName, String userAvatar, String userAvatarL,
			Integer sex, String email, String address, String province, String city,
			Date birthday, String signature, String userLabel, Date registerDate, 
			Integer online, Integer concernCount, Integer followCount, Integer worldCount,
			Integer likedCount, Integer keepCount, Integer shield, Integer star, Integer platformVerify) {
		this.id = id;
		this.userId = userId;
		this.concernId = concernId;
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
		this.platformVerify = platformVerify;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	public Integer getShield() {
		return shield;
	}

	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public Integer getConcernId() {
		return concernId;
	}

	public void setConcernId(Integer concernId) {
		this.concernId = concernId;
	}
	
	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
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
		return userId;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}
	
}
