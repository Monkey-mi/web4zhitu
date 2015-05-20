package com.hts.web.userinfo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.BaseAction;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.pojo.UserSocialAccount;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.security.service.LoginService;
import com.hts.web.userinfo.service.UserActivityService;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.impl.UserInfoServiceImpl;

/**
 * <p>
 * 用户信息子模块Action控制器
 * </p>
 * 
 * 创建时间：2013-6-20
 * 
 * @author ztj
 * 
 */
public class UserInfoAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6624712753708108526L;
	
	private Integer id; //用户id
	private Integer platformCode; //社交平台代号
	private String platformToken; //社交平台token
	private Long platformTokenExpires; // 社交平台token有效期
	private String platformSign; // 社交平台签名
	private Integer platformVerify = Tag.VERIFY_NONE; // 社交平台验证标记
	private String platformReason;
	private String loginCode; //登陆账号

	private String password; //密码
	private String userName; //用户名
	private String userAvatar; //头像
	private String userAvatarL; //头像大图
	private Integer sex = 0; //性别
	private String email; // 邮箱
	private String address; // 地址
	private Date birthday; // 生日
	private String userLabel; // 用户标签
	private String pushToken; //推送token
	private Integer phoneCode; //手机代号
	private String phoneSys;
	private String phoneVer;
	private Integer online; //是否在线
	private Integer isCheckSocialAccount = 0; //是否查询绑定的社交账号
	private String platformId; //社交平台账号id
	private String platformName;
	private String platformAvatar;
	private String platformAvatarL;
	private String signature; // 个性签名
	private String province;
	private String city;
	private Double longitude;
	private Double latitude;
	
	private Boolean trimSocialAccount = true; // 是否过来用户绑定的社交账号id列表
	private Boolean trimSocialAccountInfo = true; // 是否过滤用户绑定的社交账号详细信息
	private String oriPassword; // 旧密码
	
	private String actions; // 消息推送动作类型列表
	private String labelIds; 
	private Float ver = Tag.VERSION_2_8_2; // app版本号
	
	private Integer tradeId = 0;
	private String job;
	
	// 为了兼容2.9.88微信登录ios和android使用了不同的字段
	private String platformUnionId; 
	
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserActivityService userActivityService;
	/**
	 * 向织图注册
	 * 
	 * @return
	 */
	public String register() {
		try {
			UserInfo userInfo = userInfoService.register(loginCode,
					password, userName, userAvatar, userAvatarL, sex, signature, address, province, 
					city, longitude, latitude, tradeId, job, pushToken, phoneCode, phoneSys, phoneVer, ver);
			loginService.persistentLoginStatus(userInfo.getId(), request, response);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, userInfo, OptResult.JSON_KEY_USER_INFO, jsonMap);
		} catch(HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			StringBuilder build = new StringBuilder("register error,");
			build.append(e.getMessage()).append(",")
			.append("loginCode=").append(loginCode).append(",")
			.append("password=").append(password).append(",")
			.append("userName=").append(userName).append(",")
			.append("userAvatar=").append(userAvatar).append(",")
			.append("userAvatarL=").append(userAvatarL).append(",")
			.append("sex=").append(sex).append(",")
			.append("signature=").append(signature).append(",")
			.append("address=").append(address).append(",")
			.append("city=").append(city).append(",")
			.append("longitude=").append(longitude).append(",")
			.append("latitude=").append(latitude).append(",")
			.append("tradeId=").append(tradeId).append(",")
			.append("job=").append("job").append(",")
			.append("pushToken=").append(pushToken).append(",")
			.append("phoneCode=").append(phoneCode).append(",")
			.append("phoneSys=").append(phoneSys).append(",")
			.append("phoneVer=").append(phoneVer).append(",")
			.append("ver=").append(ver);
			JSONUtil.optFailed(getCurrentLoginUserId(), build.toString(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 检测织图账号是否存在
	 * 
	 * @return
	 */
	public String checkLoginCodeExists() {
		try {
			boolean isExists = userInfoService.checkLoginCodeExists(loginCode);
			if(isExists) {
				JSONUtil.optResult(Tag.EXIST, UserInfoServiceImpl.TIP_LOGIN_CODE_EXIST, jsonMap);
			} else {
				JSONUtil.optResult(Tag.NOT_EXIST, UserInfoServiceImpl.TIP_LOGIN_CODE_NOT_EXIST, jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 检测织图用户名是否存在
	 * 
	 * @return
	 */
	public String checkUserNameExists() {
		try {
			boolean isExists = userInfoService.checkUserNameExists(userName);
			if(isExists) {
				JSONUtil.optResult(Tag.EXIST, UserInfoServiceImpl.TIP_USER_NAME_EXIST, jsonMap);
			} else {
				JSONUtil.optResult(Tag.NOT_EXIST, UserInfoServiceImpl.TIP_USER_NAME_NOT_EXIST, jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 登陆织图
	 * 
	 * @return
	 */
	public String login() {
		boolean isCheckAccount = false;
		if(isCheckSocialAccount == 1) {
			isCheckAccount = true;
		}
		SecurityContextHolder.clearContext();
		try {
			UserInfo userInfo = userInfoService.login(loginCode, password, 
					pushToken, phoneCode, phoneSys, phoneVer, ver);
			/*
			 * 获取绑定的社交账号
			 */
			if(isCheckAccount) {
				int userId = userInfo.getId();
				List<UserSocialAccount> list = userInfoService.getSocialAccountByUserId(userId);
				jsonMap.put(OptResult.JSON_KEY_SOCIAL_ACCOUNT, list);
			}
			loginService.persistentLoginStatus(userInfo.getId(), request, response);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, userInfo, OptResult.JSON_KEY_USER_INFO, jsonMap);
			
		} catch(HTSException e) {
			JSONUtil.optFailed(e.getErrorCode(), e.getMessage(), jsonMap);
			
		} catch (Exception e) {
			StringBuilder build = new StringBuilder("login error,");
			build.append(e.getMessage()).append(",")
			.append("loginCode=").append(loginCode).append(",")
			.append("password=").append(password).append(",")
			.append("pushToken=").append(pushToken).append(",")
			.append("phoneCode=").append(phoneCode).append(",")
			.append("phoneSys=").append(phoneSys).append(",")
			.append("phoneVer=").append(phoneVer).append(",")
			.append("ver=").append(ver);
			JSONUtil.optFailed(getCurrentLoginUserId(), build.toString(), e, jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 使用社交账号登陆织图
	 * 
	 * @return
	 */
	public String loginBySocialAccount() {
		boolean isCheckAccount = false;
		if(isCheckSocialAccount == 1) {
			isCheckAccount = true;
		}
		try {
			UserInfo userInfo = userInfoService.loginBySocialAccount(platformCode, platformToken, 
					platformTokenExpires, platformSign, platformVerify,platformReason, platformId, 
					userName, userAvatar,userAvatarL, sex, pushToken, phoneCode, phoneSys, phoneVer, ver,
					platformUnionId);
			/*
			 * 获取绑定的社交账号
			 */
			if(isCheckAccount) {
				int userId = userInfo.getId();
				List<UserSocialAccount> list = userInfoService.getSocialAccountByUserId(userId);
				jsonMap.put(OptResult.JSON_KEY_SOCIAL_ACCOUNT, list);
			}
			loginService.persistentLoginStatus(userInfo.getId(), request, response);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, userInfo, OptResult.JSON_KEY_USER_INFO, jsonMap);
			
		} catch(HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			StringBuilder build = new StringBuilder("loginBySocialAccount error,");
			build.append(e.getMessage()).append(",")
			.append("platformCode=").append(platformCode).append(",")
			.append("platformToken=").append(platformToken).append(",")
			.append("platformTokenExpires=").append(platformTokenExpires).append(",")
			.append("platformSign=").append(platformSign).append(",")
			.append("platformVerify=").append(platformVerify).append(",")
			.append("platformReason=").append(platformReason).append(",")
			.append("platformId=").append(platformId).append(",")
			.append("userName=").append(userName).append(",")
			.append("userAvatar=").append(userAvatar).append(",")
			.append("userAvatarL=").append(userAvatarL).append(",")
			.append("sex=").append(sex).append(",")
			.append("pushToken=").append(pushToken).append(",")
			.append("phoneCode=").append(phoneCode).append(",")
			.append("phoneSys=").append(phoneSys).append(",")
			.append("phoneVer=").append(phoneVer).append(",")
			.append("ver=").append(ver);
			JSONUtil.optFailed(getCurrentLoginUserId(), build.toString(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 绑定社交账号
	 * 
	 * @return
	 */
	public String bindSocialAccount() {
		try {
			userInfoService.saveOrUpdateSocialAccount(getCurrentLoginUserId(), 
					platformCode, platformToken, platformTokenExpires, platformId, 
					platformName, platformAvatar, platformAvatarL, platformSign, platformVerify, 
					platformReason, jsonMap);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 解除社交账号绑定
	 * 
	 * @return
	 */
	public String unBindSocialAccount() {
		try {
			userInfoService.deleteSocialAccount(getCurrentLoginUserId(), platformCode);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新推送Token
	 * 
	 * @return
	 */
	public String updatePushToken() {
		try {
			userInfoService.updatePushToken(getCurrentLoginUserId(), pushToken, phoneCode);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户个人资料
	 * 
	 * @return
	 */
	public String queryProfile() {
		try {
			UserInfo userInfo = userInfoService.getUserInfoById(userId, 
					getCurrentLoginUserId(), trimSocialAccount, trimSocialAccountInfo);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, userInfo, OptResult.JSON_KEY_USER_INFO, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新个人资料
	 * 
	 * @return
	 */
	public String updateProfile() {
		try {
			UserInfo userInfo = userInfoService.updateProfile(getCurrentLoginUserId(), userName, userAvatar, 
					userAvatarL, sex, email, address, birthday, province, 
					city, longitude, latitude,  oriPassword, password);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, userInfo, OptResult.JSON_KEY_USER_INFO, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 接收消息推送
	 * 
	 * @return
	 */
	public String acceptPush() {
		try {
			userInfoService.updateAcceptPushEnable(getCurrentLoginUserId(), actions);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 屏蔽消息推送
	 * 
	 * @return
	 */
	public String shieldPush() {
		try {
			userInfoService.updateAcceptPushDisable(getCurrentLoginUserId(), actions);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新用户名
	 * 
	 * @return
	 */
	public String updateUserName() {
		try {
			userInfoService.updateUserName(getCurrentLoginUserId(), userName);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新密码
	 * 
	 * @return
	 */
	public String updatePassword() {
		try {
			userInfoService.updatePassword(getCurrentLoginUserId(), oriPassword, password);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新头像
	 * 
	 * @return
	 */
	public String updateAvatar() {
		try {
			userInfoService.updateAvatar(getCurrentLoginUserId(), userAvatar, userAvatarL);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新性别
	 * 
	 * @return
	 */
	public String updateSex() {
		try {
			userInfoService.updateSex(getCurrentLoginUserId(), sex);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新地址
	 * 
	 * @return
	 */
	public String updateAddress() {
		try {
			userInfoService.updateAddress(getCurrentLoginUserId(), 
					province, city, longitude, latitude, address);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新邮箱
	 * 
	 * @return
	 */
	public String updateEmail() {
		try {
			userInfoService.updateEmail(getCurrentLoginUserId(), email);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新生日
	 * 
	 * @return
	 */
	public String updateBirthday() {
		try {
			userInfoService.updateBirthday(getCurrentLoginUserId(), birthday);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新个性签名
	 * 
	 * @return
	 */
	public String updateSignature() {
		try {
			userInfoService.updateSignature(getCurrentLoginUserId(), signature);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新用户标签
	 * 
	 * @return
	 */
	public String updateUserLabel() {
		try {
			userInfoService.updateUserLabel(getCurrentLoginUserId(), labelIds,  userLabel);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新邮箱
	 * 
	 * @return
	 */
	public String updateJob() {
		try {
			userInfoService.updateJob(getCurrentLoginUserId(), tradeId, job);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (HTSException e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getErrorCode(), e.getMessage(), e, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新用户客户端版本号
	 * 
	 * @return
	 */
	public String updateVerAndPushToken() {
		try {
			userInfoService.updateVerAndPushToken(getCurrentLoginUserId(), ver, pushToken, phoneSys, phoneVer);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 搜集社交平台token
	 * @return
	 */
	public String clpi() {
		try {
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询用户头像
	 * 
	 * @return
	 */
	public String queryProfileLite() {
		try {
			userInfoService.buildUserAvatarLite(id, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 每日登录接口
	 * 
	 * @return
	 */
	public String dailyLogin() {
		try {
			userActivityService.addActivityScore(Tag.ACT_TYPE_LOGIN, 
					getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(getCurrentLoginUserId(), e.getMessage(), e, jsonMap);
		}
		return StrutsKey.JSON;
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

	public Integer getIsCheckSocialAccount() {
		return isCheckSocialAccount;
	}

	public void setIsCheckSocialAccount(Integer isCheckSocialAccount) {
		this.isCheckSocialAccount = isCheckSocialAccount;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getUserAvatarL() {
		return userAvatarL;
	}

	public void setUserAvatarL(String userAvatarL) {
		this.userAvatarL = userAvatarL;
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

	public String getOriPassword() {
		return oriPassword;
	}

	public void setOriPassword(String oriPassword) {
		this.oriPassword = oriPassword;
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
	
	public String getActions() {
		return actions;
	}


	public void setActions(String actions) {
		this.actions = actions;
	}


	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformAvatar() {
		return platformAvatar;
	}

	public void setPlatformAvatar(String platformAvatar) {
		this.platformAvatar = platformAvatar;
	}

	public String getPlatformAvatarL() {
		return platformAvatarL;
	}

	public void setPlatformAvatarL(String platformAvatarL) {
		this.platformAvatarL = platformAvatarL;
	}

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

	public Boolean getTrimSocialAccount() {
		return trimSocialAccount;
	}

	public void setTrimSocialAccount(Boolean trimSocialAccount) {
		this.trimSocialAccount = trimSocialAccount;
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

	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public Boolean getTrimSocialAccountInfo() {
		return trimSocialAccountInfo;
	}

	public void setTrimSocialAccountInfo(Boolean trimSocialAccountInfo) {
		this.trimSocialAccountInfo = trimSocialAccountInfo;
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public String getPlatformReason() {
		return platformReason;
	}

	public void setPlatformReason(String platformReason) {
		this.platformReason = platformReason;
	}

	public String getPlatformUnionId() {
		return platformUnionId;
	}

	public void setPlatformUnionId(String platformUnionId) {
		this.platformUnionId = platformUnionId;
	}
	
}
