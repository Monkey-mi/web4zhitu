package com.hts.web.common.pojo;

import java.util.ArrayList;
import java.util.List;

import com.hts.web.base.constant.Tag;

/**
 * <p>
 * 标签推荐用户POJO
 * </p>
 * 
 * 创建时间：2014-1-13
 * 
 * @author ztj
 * 
 */
public class OpUserLabelRecommend implements UserWithWorld,ObjectWithUserVerify, ObjectWithUserRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1213788868320904046L;

	private Integer recommendId;
	private String recommendDesc;

	private Integer id;
	private String platformSign; // 社交平台签名
	private Integer platformVerify; // 社交平台认证标记
	private String userName;
	private String userAvatar;
	private String userAvatarL;
	private String address;
	private String province;
	private String city;
	private String signature;
	private String userLabel;
	private Integer star = Tag.FALSE;
	private String verifyName;
	private String verifyIcon;
	private Integer activity = 0;
	private List<HTWorldThumbUser> htworld = new ArrayList<HTWorldThumbUser>();
	
	private String remark;
	private String platformReason;

	public OpUserLabelRecommend(Integer recommendId, String recommendDesc,
			Integer id, String platformSign, Integer platformVerify, String platformReason,
			String userName, String userAvatar, String userAvatarL,
			String address, String province, String city, String signature,
			String userLabel, Integer star, Integer activity) {
		super();
		this.recommendId = recommendId;
		this.recommendDesc = recommendDesc;
		this.id = id;
		this.platformSign = platformSign;
		this.platformVerify = platformVerify;
		this.platformReason = platformReason;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userAvatarL = userAvatarL;
		this.address = address;
		this.province = province;
		this.city = city;
		this.signature = signature;
		this.userLabel = userLabel;
		this.star = star;
		this.activity = activity;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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
		return null;
	}

	public String getPlatformReason() {
		return platformReason;
	}

	public void setPlatformReason(String platformReason) {
		this.platformReason = platformReason;
	}
	
	
}
