package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 认证用户数据访问对象
 * </p>
 * 
 * @author tianjie
 * 
 */
public class UserVerifyDto implements Serializable, ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2679331775994013686L;
	private Integer verifyId;
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
	private Integer online;

	public UserVerifyDto() {
		super();
	}

	public UserVerifyDto(Integer verifyId, Integer id, String userName,
			String userAvatar, String userAvatarL, Integer sex, String email,
			String address, String province, String city, Date birthday,
			String signature, Date registerDate, String userLabel,
			Integer star, Integer phoneCode, Integer online) {
		super();
		this.verifyId = verifyId;
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
	}

	public Integer getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
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

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

}
