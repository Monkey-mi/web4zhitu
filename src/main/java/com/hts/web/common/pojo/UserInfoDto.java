package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 用户信息数据传输对象，只拥有用户的基本信息
 * </p>
 * 
 * 创建时间：2013-7-24
 * 
 * @author ztj
 * 
 */
public class UserInfoDto implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7309579763921507138L;

	private Integer id;
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
	private Date registerDate;
	private String userLabel; // 用户标签
	private Integer star = Tag.FALSE;
	private String verifyName;
	private String verifyIcon;
	private Integer phoneCode = Tag.IOS;
	private String phoneSys;
	private String phoneVer;
	private Integer online;
	
	private Integer isMututal = -1;
	
	private String loginCode;
	
	private Float ver;
	
	private Integer platformVerify = Tag.VERIFY_NONE;
	
	private String remark;
	

	public UserInfoDto() {
		super();
	}
	
	public UserInfoDto(Integer id, String userName, String userAvatar,
			String userAvatarL, Integer sex, String email, String address, 
			String province, String city, Date birthday,String signature,
			Date registerDate, String userLabel,Integer star,
			Integer phoneCode, Integer online, Integer platformVerify) {
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
		this.phoneCode = phoneCode;
		this.online = online;
		this.platformVerify = platformVerify;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	@Override
	public Integer getVerifyId() {
		return star;
	}

	public Float getVer() {
		return ver;
	}

	public void setVer(Float ver) {
		this.ver = ver;
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

	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Integer getRemarkId() {
		return this.id;
	}

	public Integer getPlatformVerify() {
		return platformVerify;
	}

	public void setPlatformVerify(Integer platformVerify) {
		this.platformVerify = platformVerify;
	}
	
}
