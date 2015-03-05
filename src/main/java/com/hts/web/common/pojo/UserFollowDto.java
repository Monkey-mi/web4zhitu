package com.hts.web.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

public class UserFollowDto implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 17940873445026972L;
	private Integer id;
	private Integer userId;
	private Integer concernId;
	private Integer isMututal;
	private Date concernDate;
	private Integer ck;
	private Integer isNew;
	private String userName;
	private Integer sex = Tag.SEX_UNKNOWN;
	private String userAvatar;
	private String userAvatarL;
	private String userLabel;
	private String address; // 地址
	private String province;
	private String city;
	private String signature;
	private String platformSign;
	private Integer star = Tag.FALSE;
	private String verifyName;
	private String verifyIcon;
	private Integer online;
	
	private String loginCode;
	
	private Integer platformVerify = Tag.VERIFY_NONE;
	
	private String remark;

	public UserFollowDto() {
		super();
	}
	
	public UserFollowDto(Integer id, Integer userId, Integer concernId,
			Integer isMututal, Date concernDate, Integer ck, Integer isNew, String userName,
			Integer sex, String userAvatar, String userAvatarL, String userLabel,
			String address, String province, String city, String signature, String platformSign,
			Integer star,  Integer online, Integer platformVerify) {
		super();
		this.id = id;
		this.userId = userId;
		this.concernId = concernId;
		this.isMututal = isMututal;
		this.concernDate = concernDate;
		this.ck = ck;
		this.isNew = isNew;
		this.userName = userName;
		this.sex = sex;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.userLabel = userLabel;
		this.address = address;
		this.province = province;
		this.city = city;
		this.signature = signature;
		this.platformSign = platformSign;
		this.star = star;
		this.online = online;
		this.platformVerify = platformVerify;
	}

	public Integer getCk() {
		return ck;
	}

	public void setCk(Integer ck) {
		this.ck = ck;
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

	public Integer getConcernId() {
		return concernId;
	}

	public void setConcernId(Integer concernId) {
		this.concernId = concernId;
	}

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getConcernDate() {
		return concernDate;
	}

	public void setConcernDate(Date concernDate) {
		this.concernDate = concernDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
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

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
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
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getPlatformSign() {
		return platformSign;
	}

	public void setPlatformSign(String platformSign) {
		this.platformSign = platformSign;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	
	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
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
