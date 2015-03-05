package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 用户搜索信息POJO
 * </p>
 * 
 * 创建时间：2013-8-22
 * 
 * @author ztj
 * 
 */
public class UserSearchInfo implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -24422566548922551L;
	private Integer id;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private Integer sex;
	private String userLabel;
	private String address;
	private String province;
	private String city;
	private String signature;
	private String platformSign;
	private Integer isMututal;
	private Integer star = Tag.FALSE;
	private String verifyName;
	private String verifyIcon;
	private String remark;
	private Integer platformVerify = Tag.VERIFY_NONE;

	public UserSearchInfo() {
		super();
	}

	public UserSearchInfo(Integer id, String userName, String userAvatar, String userAvatarL,
			Integer sex, String userLabel, String address, String province, String city,
			String signature, String platformSign, Integer isMututal, Integer star, Integer platformVerify) {
		this.id = id;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.sex = sex;
		this.userLabel = userLabel;
		this.address = address;
		this.province = province;
		this.city = city;
		this.signature = signature;
		this.platformSign = platformSign;
		this.isMututal = isMututal;
		this.star = star;
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

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getUserAvatarL() {
		return userAvatarL;
	}

	public void setUserAvatarL(String userAvatarL) {
		this.userAvatarL = userAvatarL;
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
	
}
