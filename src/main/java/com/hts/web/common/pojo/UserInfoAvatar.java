package com.hts.web.common.pojo;

import java.io.Serializable;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 用户头像POJO
 * </p>
 * 
 * 创建时间：2014-09-11
 * 
 * @author tianjie
 * 
 */
public class UserInfoAvatar implements Serializable, ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5015720587251035558L;

	private Integer id;
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private String province;
	private String city;
	private Integer star;
	private Integer platformVerify = Tag.VERIFY_NONE;
	private String verifyName;
	private String verifyIcon;
	private Integer isMututal = Tag.UN_CONCERN;
	private Integer iisMututal = Tag.UN_CONCERN;
	private String remark;
	
	public UserInfoAvatar() {
		super();
	}

	public UserInfoAvatar(Integer id, String userName, String userAvatar, 
			String userAvatarL, String province, String city, Integer star,
			Integer platformVerify) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.province = province;
		this.city = city;
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

	public String getUserAvatarL() {
		return userAvatarL;
	}

	public void setUserAvatarL(String userAvatarL) {
		this.userAvatarL = userAvatarL;
	}

	@Override
	public Integer getVerifyId() {
		return this.star;
	}

	@Override
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	@Override
	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
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

	public String getVerifyIcon() {
		return verifyIcon;
	}

	public Integer getIsMututal() {
		return isMututal;
	}

	public void setIsMututal(Integer isMututal) {
		this.isMututal = isMututal;
	}

	public Integer getIisMututal() {
		return iisMututal;
	}

	public void setIisMututal(Integer iisMututal) {
		this.iisMututal = iisMututal;
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
