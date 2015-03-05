package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 用户基本信息POJO, 一般在查询织图信息附加的用户信息
 * </p>
 * 
 * 创建时间:2014-10-31
 * @author lynch
 *
 */
public abstract class UserWorldBase implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {
	
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	protected Integer id;
	protected String userName;
	protected String userAvatar;
	protected String userAvatarL;
	protected Integer sex = Tag.SEX_UNKNOWN; // 性别
	protected String email; // 邮箱
	protected String address; // 地址
	protected String province; // 省份
	protected String city; // 城市
	protected Date birthday; // 生日
	protected String signature;
	protected Date registerDate;
	protected String userLabel; // 用户标签
	protected Integer star = Tag.FALSE;
	protected String verifyName;
	protected String verifyIcon;
	protected Integer phoneCode = Tag.IOS;
	protected String phoneSys;
	protected String phoneVer;
	protected Integer online;
	
	protected Integer platformVerify = Tag.VERIFY_NONE;
	
	protected String remark;

	public UserWorldBase() {
		super();
	}

	public UserWorldBase(Integer id, String userName, String userAvatar,
			String userAvatarL, Integer sex, String email, String address,
			String province, String city, Date birthday, String signature,
			Date registerDate, String userLabel, Integer star,
			String verifyName, String verifyIcon, Integer phoneCode,
			String phoneSys, String phoneVer, Integer online, Integer platformVerify) {
		super();
		this.id = id;
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
		this.registerDate = registerDate;
		this.userLabel = userLabel;
		this.star = star;
		this.verifyName = verifyName;
		this.verifyIcon = verifyIcon;
		this.phoneCode = phoneCode;
		this.phoneSys = phoneSys;
		this.phoneVer = phoneVer;
		this.online = online;
		this.platformVerify = platformVerify;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getVerifyName() {
		return verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	@Override
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
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
	
}
