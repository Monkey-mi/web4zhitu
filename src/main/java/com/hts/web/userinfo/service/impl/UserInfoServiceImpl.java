package com.hts.web.userinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.service.OsUserInfoService;
import com.hts.web.base.HTSException;
import com.hts.web.base.constant.LoggerKeies;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.PlatFormCode;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.hts.web.common.pojo.ObjectWithUserVerifyDesc;
import com.hts.web.common.pojo.UserAvatarLite;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.pojo.UserSocialAccount;
import com.hts.web.common.pojo.UserSocialAccountDto;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.PushUtil;
import com.hts.web.common.util.StringUtil;
import com.hts.web.plat.service.PlatService;
import com.hts.web.plat.service.SinaWeiboService;
import com.hts.web.push.service.PushService;
import com.hts.web.push.service.impl.PushServiceImpl.PushFailedCallback;
import com.hts.web.stat.dao.StatUserRegisterCacheDao;
import com.hts.web.userinfo.dao.SocialAccountDao;
import com.hts.web.userinfo.dao.UserConcernDao;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.userinfo.dao.UserLabelDao;
import com.hts.web.userinfo.dao.UserVerifyCacheDao;
import com.hts.web.userinfo.service.UserInfoService;
import com.hts.web.userinfo.service.UserInteractService;
import com.hts.web.userinfo.service.UserMsgService;

/**
 * <p>
 * 用户信息业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-6-21
 * @author ztj
 *
 */
@Service("HTSUserInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl implements UserInfoService{
	
	private static Logger loginLogger = Logger.getLogger(LoggerKeies.USER_LOGIN);
	private static Logger registerLogger = Logger.getLogger(LoggerKeies.USER_REGISTER);
	private static Logger loginBySocialLogger = Logger.getLogger(LoggerKeies.USER_LOGIN_BY_SOCIAL);
	private static Logger registerBySocialLogger = Logger.getLogger(LoggerKeies.USER_REGISTER_BY_SOCIAL);
	/**
	 * 账号错误
	 */
	public static final int LOGIN_CODE_ERROR = 1;
	
	/**
	 * 密码错误
	 */
	public static final int PASSWORD_ERROR = 2;
	
	/**
	 * 账号已经存在
	 */
	public static final int LOGIN_CODE_EXIST = 3;
	
	/**
	 * 用户名已经存在
	 */
	public static final int USER_NAME_EXIST = 4;
	
	/**
	 * 推送Token错误
	 */
	public static final int PUSH_TOKEN_ERROR = 5;
	
	/**
	 * 头像为空错误
	 */
	public static final int AVATAR_ERROR = 6;
	
	/**
	 * 社交平台已经关闭错误
	 */
	public static final int PLATFORM_OFF_ERROR = 7;
	
	/**
	 * 解绑登陆平台错误
	 */
	public static final int UN_BIND_LOGIN_PLATFORM = 8;
	
	/**
	 * 用户名不存在错误
	 */
	public static final int USER_NAME_NOT_EXITS_ERROR = 9;
	
	/**
	 * 账号错误提示
	 */
	public static final String TIP_LOGIN_CODE_ERROR = "账号错误";
	
	/**
	 * 账号已经存在提示
	 */
	public static final String TIP_LOGIN_CODE_EXIST = "该邮箱已经被注册";
	
	/**
	 * 账号不存在提示
	 */
	public static final String TIP_LOGIN_CODE_NOT_EXIST = "账号不存在";
	
	/**
	 * 用户名已经存在提示
	 */
	public static final String TIP_USER_NAME_EXIST = "用户名已经存在";
	
	/**
	 * 用户名不存在提示
	 */
	public static final String TIP_USER_NAME_NOT_EXIST = "用户名不存在";
	
	/**
	 * 密码错误提示
	 */
	public static final String TIP_PASSWORD_ERROR = "密码错误";
	
	/**
	 * 退出成功提示
	 */
	public static final String TIP_LOGOUT_SUCCESS = "退出成功";

	/**
	 * 推送Token错误
	 */
	public static final String TIP_PUSH_TOKEN_ERROR = "推送Token错误";
	
	private static final int PLATFORM_REASON_MAX_LENGTH = 140;
	
	private static final int PHONE_SYS_MAX_LENGTH = 10;
	
	private static final int PHONE_VER_MAX_LENGTH = 9;
	
	private static final float VER_MAX = 999.99f;
	
//	private static final String DEFAULT_NAME = "该用户未设置名字";
	
	private static final String DEFAULT_AVATAR_L = "http://static.imzhitu.com/avatar/m/no-avatar-l.png";
	
	private static final String DEFAULT_AVATAR_S = DEFAULT_AVATAR_L + ".thumbnail";
	
	private Integer officialId = 2063;
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	@Qualifier("HTSUserLoginPersistentDao")
	private PersistentTokenRepository tokenRepository;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private SocialAccountDao socialAccountDao;
	
	@Autowired
	private UserLabelDao userLabelDao;
	
	@Autowired
	private UserVerifyCacheDao userVerifyCacheDao;
	
	@Autowired
	private TaskExecutor taskExecutor; // 多线程执行器
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private SinaWeiboService sinaWeiboService;
	
	@Autowired
	private UserInteractService userInteractService;
	
	@Autowired
	private OsUserInfoService osUserService;
	
	@Autowired
	private UserConcernDao userConcernDao;
	
	@Autowired
	private PlatService platService;
	
	@Autowired
	private StatUserRegisterCacheDao statUserRegisterCacheDao;
	
	@Autowired
	private UserMsgService userMsgService;
	
	public Integer getOfficialId() {
		return officialId;
	}

	public void setOfficialId(Integer officialId) {
		this.officialId = officialId;
	}

	@Override
	public Integer checkLoginCodeExists(String loginCode, Integer platformCode) throws Exception {
		boolean flag;
		if(platformCode == null) {
			platformCode = PlatFormCode.ZHITU;
		}
		
		flag = userInfoDao.checkLoginCodeExists(loginCode, platformCode);
		if(!flag) {
			flag = socialAccountDao.queryPlatformIdExist(loginCode);
		}
		
		return flag ? Tag.TRUE : Tag.FALSE;
	}
	
	@Override
	public Integer checkUserNameExists(String userName) throws Exception {
		boolean flag = userInfoDao.checkUserNameExists(userName);
		return flag ? Tag.TRUE : Tag.FALSE;
	}

	@Override
	public UserInfo register(String loginCode,String password, String userName,
			String userAvatar, String userAvatarL,  Integer sex, String signature, String address, 
			String province, String city,Double longitude, Double latitude, Integer tradeId,
			String job, String pushToken, Integer phoneCode, String phoneSys, String phoneVer,
			Float ver) throws Exception {
		UserInfo userInfo = null;
		userName = StringUtil.trimName(userName);
		if(userInfoDao.checkLoginCodeExists(loginCode, PlatFormCode.ZHITU)) { //判断账号是否存在
			throw new HTSException(TIP_LOGIN_CODE_EXIST, LOGIN_CODE_EXIST);
		}
		
		userAvatar = StringUtil.checkIsNULL(userAvatar) ? DEFAULT_AVATAR_S : 
			StringUtil.replaceQiniuDomain(userAvatar);
		userAvatarL = filterUserAvatarL(StringUtil.replaceQiniuDomain(userAvatarL), userAvatar);
		// 过滤空字符串
		address = StringUtil.checkIsNULL(address) ? null : address;
		pushToken = StringUtil.checkIsNULL(pushToken) ? null : pushToken;
		if(address != null && address.contains("(null)")) 
			address = address.replace("(null)", "");
		province = StringUtil.checkIsNULL(province) ? null : province;
		city = StringUtil.checkIsNULL(city) ? null : city;
		phoneSys = phoneSys != null && phoneSys.length() > PHONE_SYS_MAX_LENGTH 
				? phoneSys.substring(0, PHONE_SYS_MAX_LENGTH) : phoneSys;
		phoneVer = phoneVer != null && phoneVer.length() > PHONE_VER_MAX_LENGTH 
				? phoneVer.substring(0, PHONE_VER_MAX_LENGTH) : phoneVer;
		ver = ver > VER_MAX ? VER_MAX : ver;
		
		Integer id = keyGenService.generateId(KeyGenServiceImpl.USER_ID);
		userInfo = new UserInfo(id, PlatFormCode.ZHITU, null, null, null, Tag.VERIFY_NONE, null, loginCode,
				userName, userAvatar, userAvatarL, sex, signature, address, province, city, longitude, latitude,
				new Date(), tradeId, job, pushToken, phoneCode, phoneSys, phoneVer, Tag.ONLINE, ver);
		initNewUserInfo(userInfo);
		byte[] passwordEncrypt = null;
		passwordEncrypt = MD5Encrypt.encryptByMD5(password);
		osUserService.saveUser(id, userName, userAvatar, signature, null, 0, 0); // 保存用户索引
		userInfoDao.saveUserInfo(userInfo, passwordEncrypt);
		userInfo.setIsNewAdded(Tag.TRUE);
		
		extractVerify(userInfo);
		
		// 默认关注织图官方账号
		try {
			userInteractService.saveConcern(true, id, officialId);
		} catch(Exception e) {
			registerLogger.warn("concern offical error:" + e.getMessage());
		}
		
		statUserRegisterCacheDao.saveRegisterStat(id, phoneCode, new Date());
		
		return userInfo;
	}

	@Override
	public UserInfo registerBySocialAccount(Integer platformCode, 
			String platformToken, Long platformTokenExpires, String platformSign, 
			Integer platformVerify, String platformReason, String loginCode, String userName,
			String userAvatar, String userAvatarL, Integer sex, String pushToken, 
			Integer phoneCode, String phoneSys, String phoneVer, Float ver) throws Exception {
		userAvatar = StringUtil.checkIsNULL(userAvatar) ? DEFAULT_AVATAR_S : userAvatar;
		userAvatarL = filterUserAvatarL(userAvatarL, userAvatar);
		userName = StringUtil.trimName(userName);
		UserInfo userInfo = null;
		
		if(userInfoDao.checkUserNameExists(userName)) {
			userName = userName + StringUtil.getRandomUserName();
		}

		Integer id = keyGenService.generateId(KeyGenServiceImpl.USER_ID);
		userInfo = new UserInfo(id, platformCode, platformToken, platformTokenExpires, 
				platformSign, platformVerify, platformReason, loginCode, userName,userAvatar, userAvatarL, 
				sex, null, null, null, null, null, null, new Date(),0,null, pushToken, phoneCode, phoneSys, 
				phoneVer, Tag.ONLINE, ver);
		osUserService.saveUser(id, userName, userAvatar, null, platformSign, 0, 0);
		userInfoDao.saveUserInfo(userInfo, null);
		userInfo.setIsNewAdded(Tag.TRUE);
		
		// 默认关注织图官方账号
		try {
			userInteractService.saveConcern(true, id, officialId);
		} catch(Exception e) {
			registerBySocialLogger.warn("concern offical error:" + e.getMessage());
		}

		statUserRegisterCacheDao.saveRegisterStat(id, phoneCode, new Date());
		
		return userInfo;
	}
	
	
	@Override
	public UserInfo login(String loginCode, String password, String pushToken, 
			Integer phoneCode, String phoneSys, String phoneVer, Float ver) throws Exception {
		UserInfo userInfo = userInfoDao.queryUserInfoByLoginCode(loginCode, PlatFormCode.ZHITU);
		if(userInfo == null) {
			loginLogger.warn("login logincode error,loginCode=" + loginCode + ", pwd=" + password
					+ ",phoneCode=" + phoneCode + ",phoneSys=" + phoneSys + "," + ",phoneVer=" + phoneVer
					+ ",ver=" + ver);
			//账号错误
			throw new HTSException(TIP_LOGIN_CODE_ERROR, LOGIN_CODE_ERROR);
		} else {
			byte[] encryptPassword = userInfo.getPasswordEncrypt();
			if(!MD5Encrypt.validatePassword(password, encryptPassword)) {
				//密码错误
				throw new HTSException(TIP_PASSWORD_ERROR, PASSWORD_ERROR);
			}
		}
		
		// 更新登陆状态
		Integer onlineStatus = Tag.ONLINE;
		pushToken = StringUtil.checkIsNULL(pushToken) ? null : pushToken;
		phoneSys = phoneSys != null && phoneSys.length() > PHONE_SYS_MAX_LENGTH 
				? phoneSys.substring(0, PHONE_SYS_MAX_LENGTH) : phoneSys;
		phoneVer = phoneVer != null && phoneVer.length() > PHONE_VER_MAX_LENGTH 
				? phoneVer.substring(0, PHONE_VER_MAX_LENGTH) : phoneVer;
		ver = ver > VER_MAX ? VER_MAX : ver;
		userInfoDao.updateLoginStatus(loginCode, PlatFormCode.ZHITU, null, null, null,
				Tag.VERIFY_NONE, null, pushToken, phoneCode, phoneSys, phoneVer, onlineStatus, ver); // 更新登陆状态
		userInfo.setPushToken(pushToken);
		userInfo.setPlatformCode(PlatFormCode.ZHITU);
		userInfo.setPhoneCode(phoneCode);
		userInfo.setPhoneSys(phoneSys);
		userInfo.setPhoneVer(phoneVer);
		userInfo.setPasswordEncrypt(null);
		
		extractVerify(userInfo);
		
		return userInfo;
		
	}
	
	/**
	 * 初始化新用户信息
	 * @param userInfo
	 */
	private void initNewUserInfo(UserInfo userInfo) {
		userInfo.setConcernCount(0);
		userInfo.setFollowCount(0);
		userInfo.setWorldCount(0);
		userInfo.setLikedCount(0);
		userInfo.setKeepCount(0);
	}
	
	@Override
	public UserInfo loginBySocialAccount(Integer platformCode, 
			String platformToken, Long platformTokenExpires, String platformSign,
			Integer platformVerify, String platformReason, String loginCode, String userName,
			String userAvatar, String userAvatarL,Integer sex, String pushToken, 
			Integer phoneCode, String phoneSys, String phoneVer, Float ver, String platformUnionId) throws Exception {
		userAvatar = StringUtil.checkIsNULL(userAvatar) ? DEFAULT_AVATAR_S : userAvatar;
		userAvatarL = filterUserAvatarL(userAvatarL, userAvatar);
		
		pushToken = StringUtil.checkIsNULL(pushToken) ? null : pushToken;
		platformReason = platformReason != null && platformReason.length() > PLATFORM_REASON_MAX_LENGTH 
				? platformReason.substring(0, PLATFORM_REASON_MAX_LENGTH) : platformReason;
		phoneSys = phoneSys != null && phoneSys.length() > PHONE_SYS_MAX_LENGTH 
				? phoneSys.substring(0, PHONE_SYS_MAX_LENGTH) : phoneSys;
		phoneVer = phoneVer != null && phoneVer.length() > PHONE_VER_MAX_LENGTH 
				? phoneVer.substring(0, PHONE_VER_MAX_LENGTH) : phoneVer;
		ver = ver > VER_MAX ? VER_MAX : ver;
		if(StringUtil.checkIsNULL(loginCode)) {
			loginBySocialLogger.warn("loginBySocialAccount logincode error,platformCode=" + platformCode 
					+ "loginCode=" + loginCode
					+ ",phoneCode=" + phoneCode + ",phoneSys=" + phoneSys 
					+ ",phoneVer=" + phoneVer + ",ver=" + ver);
			throw new HTSException(TIP_LOGIN_CODE_ERROR, LOGIN_CODE_ERROR);
		}
		UserInfo userInfo = null;
		UserSocialAccount account = socialAccountDao.queryUserId(loginCode, platformCode);
		if(account != null) // 绑定的账号直接登陆到主账号
			userInfo = userInfoDao.queryUserInfoById(account.getUserId());
		else
			userInfo = userInfoDao.queryUserInfoByLoginCode(loginCode, platformCode);

		// 兼容2.9.89之前微信登录ios和android使用了不同的loginCode字段
		if(userInfo == null && platformCode.equals(PlatFormCode.WEI_SIN)) 
			userInfo = patch4Weixin2988(platformCode, phoneCode, ver, loginCode, platformUnionId);
		
		if(userInfo == null) { //还没有账号时新建账号
			
			checkPlatformAvaliable(platformCode, phoneCode, ver);
			
			userInfo = registerBySocialAccount(platformCode, platformToken, platformTokenExpires, 
					platformSign, platformVerify, platformReason, loginCode, userName, userAvatar, 
					userAvatarL, sex, pushToken, phoneCode, phoneSys, phoneVer, ver);
			initNewUserInfo(userInfo);
			
		} else { // 登陆成功，更新登陆状态
			osUserService.updateUserWithoutNULL(userInfo.getId(), null, null, 
					null, platformSign, null, null);
			Integer onlineStatus = Tag.ONLINE;
			userInfoDao.updateLoginStatus(loginCode, platformCode, platformToken,
					platformTokenExpires, platformSign, platformVerify, platformReason, pushToken, phoneCode, 
					phoneSys, phoneVer, onlineStatus, ver);
			userInfo.setPushToken(pushToken);
			userInfo.setPlatformSign(platformSign);
			userInfo.setPlatformVerify(platformVerify);
			userInfo.setPlatformReason(platformReason);
			userInfo.setPhoneCode(phoneCode);
			userInfo.setPhoneSys(phoneSys);
			userInfo.setPhoneVer(phoneVer);
		}
		
		// 绑定当前社交账号
		
		if(account == null) { // 如果之前没有绑定过
			account = new UserSocialAccount(platformCode, loginCode, userName, userAvatar, 
					userAvatarL, platformToken, platformTokenExpires, platformSign,
					platformVerify, platformReason, userInfo.getId(), Tag.TRUE);
			socialAccountDao.saveSocialAccount(account);
		} else {
			account.setPlatformName(userName);
			account.setPlatformAvatar(userAvatar);
			account.setPlatformAvatarL(userAvatarL);
			account.setPlatformToken(platformToken);
			account.setPlatformTokenExpires(platformTokenExpires);
			account.setPlatformSign(platformSign);
			account.setPlatformVerify(platformVerify);
			account.setPlatformReason(platformReason);
			account.setUserId(userInfo.getId());
			socialAccountDao.udpateSocialAccount(account);
		}
		
		extractVerify(userInfo);
		return userInfo;
	}
	
	/**
	 * 过滤用户大头像
	 * 
	 * @param userAvatarL
	 * @param userAvatar
	 * @return
	 */
	private String filterUserAvatarL(String userAvatarL, String userAvatar) {
		if(StringUtil.checkIsNULL(userAvatarL)) {
			if(!StringUtil.checkIsNULL(userAvatar))
				userAvatarL = userAvatar;
			else
				userAvatarL = DEFAULT_AVATAR_L;
		}
		return userAvatarL;
	}
	
	/**
	 * 检测社交平台是否注册
	 * 
	 * @param platformCode
	 * @param ver
	 * @throws HTSException
	 */
	private void checkPlatformAvaliable(Integer platformCode, Integer phoneCode, Float ver) throws HTSException {
		if(platformCode.equals(PlatFormCode.REN_REN)) {
			throw new HTSException("不好意思，由于人人网的权限问题，用人人帐号登录暂时关闭，请选择新浪微博登录或者是QQ登录", PLATFORM_OFF_ERROR);
		}	
//		} else if(platformCode.equals(PlatFormCode.WEI_SIN) && ver.equals(Tag.VERSION_2_9_88)) {
//			throw new HTSException("不好意思，此版本暂停使用微信注册，请使用其他平台注册或升级版本", PLATFORM_OFF_ERROR);
//		}
	}
	
	/**
	 * 因为2.9.88之前的微信账号登录ios和安卓使用了不同的loginCode，
	 * 所以用户登录之后需要将账号更新为微信提供的openid
	 * 
	 * @param platformCode
	 * @param phoneCode
	 * @param ver
	 * @param platformId
	 * @param platformUnionId
	 * @return
	 */
	private UserInfo patch4Weixin2988(Integer platformCode, Integer phoneCode, 
			Float ver, String platformId, String platformUnionId) { 
		if(StringUtil.checkIsNULL(platformUnionId) || ver <= Tag.VERSION_2_9_88) {
			return null; 
		}
		
		UserInfo userInfo = null;
		UserSocialAccount account = socialAccountDao.queryUserId(platformUnionId, platformCode);
		if(account != null) // 绑定的账号直接登陆到主账号
			userInfo = userInfoDao.queryUserInfoById(account.getUserId());
		else
			userInfo = userInfoDao.queryUserInfoByLoginCode(platformUnionId, platformCode);
		
		// 说明之前用了错误的账号登录
		if(userInfo != null) {
			userInfoDao.updateLoginCodeByUID(userInfo.getId(), platformId);
			socialAccountDao.updatePlatformId(userInfo.getId(), platformCode, platformId);
		}
		return userInfo;
		
	}
	
	/**
	 * 根据用户id获取绑定社交账号JSONArray对象
	 * @param userId
	 * @return
	 */
	public List<UserSocialAccount> getSocialAccountByUserId(int userId) {
		List<UserSocialAccount> accountList = socialAccountDao.querySocialAccountByUserId(userId);
		return accountList;
	}

	
	@Override
	public void logout(Integer userId, String pushToken, Map<String, Object> jsonMap) throws Exception {
		userInfoDao.updateLogoutStatus(userId);
	}
	
	@Override
	public void updatePushToken(Integer userId, String pushToken, Integer phoneCode) throws Exception {
		userInfoDao.updatePushTokenByUserId(userId, pushToken, phoneCode);
	}
	
	@Override
	public void saveSocialAccount(Integer userId, Integer platformCode, 
			String platformToken, Long platformTokenExpires, String platformId,
			String platformName, String platformAvatar, String platformAvatarL,
			String platformSign, Integer platformVerify, String platformReason) {
		UserSocialAccount newAccount = new UserSocialAccount(platformCode, platformId, 
				platformName, platformAvatar, platformAvatarL, platformToken, platformTokenExpires,
				platformSign, platformVerify, platformReason, userId, Tag.TRUE);
		socialAccountDao.saveSocialAccount(newAccount);
	}
	
	@Override
	public void saveOrUpdateSocialAccount(Integer userId, Integer platformCode, 
			String platformToken, Long platformTokenExpires, String platformId, String platformName,
			String platformAvatar, String platformAvatarL, String platformSign, Integer platformVerify, 
			String platformReason, Map<String, Object> jsonMap) throws Exception {
		platformReason = platformReason != null && platformReason.length() > PLATFORM_REASON_MAX_LENGTH 
				? platformReason.substring(0, PLATFORM_REASON_MAX_LENGTH) : platformReason;
		UserSocialAccount account = socialAccountDao.queryUserId(platformId, platformCode);
		if(account == null) {
			account = new UserSocialAccount(platformCode, platformId, platformName, platformAvatar, 
					platformAvatarL, platformToken, platformTokenExpires, platformSign, platformVerify,
					platformReason, userId, Tag.TRUE);
			socialAccountDao.saveSocialAccount(account);
			
		} else {
			// 如果绑定了其他账号，则提示出其他账号的绑定
			if(!account.getUserId().equals(userId)) {
				String uname = userInfoDao.queryUserNameById(account.getUserId());
				jsonMap.put(OptResult.JSON_KEY_UN_BIND_TIP, "同时解除和\"" + uname + "\"的账号绑定");
			}
			
			account.setPlatformName(platformName);
			account.setPlatformAvatar(platformAvatar);
			account.setPlatformAvatarL(platformAvatarL);
			account.setPlatformToken(platformToken);
			account.setPlatformTokenExpires(platformTokenExpires);
			account.setPlatformSign(platformSign);
			account.setPlatformVerify(platformVerify);
			account.setPlatformReason(platformReason);
			account.setUserId(userId);
			socialAccountDao.udpateSocialAccount(account);
			
		}
		
		// 更新新浪平台绑定信息
		if(platformCode.equals(PlatFormCode.SINA)) {
			userInfoDao.updateSocialBindInfo(userId, platformVerify, platformReason, platformSign);
			platService.savePlatConcern(platformToken, PlatFormCode.SINA);
		}
	}
	
	@Override
	public void deleteSocialAccount(Integer userId, Integer platformCode)
			throws Exception {
		// 删除社交平台绑定信息
//		UserInfo userInfo = userInfoDao.queryUserInfoById(userId);
//		if(userInfo != null && userInfo.getPlatformCode().equals(platformCode)) {
////					userInfoDao.updateSocialBindInfo(userId, 0, null);
//			throw new HTSException("解绑登陆平台错误", UN_BIND_LOGIN_PLATFORM);
//		}
		socialAccountDao.deleteByPlatformCode(platformCode, userId);
		if(platformCode.equals(PlatFormCode.SINA)) {
			userInfoDao.updateSocialBindInfo(userId, Tag.VERIFY_NONE, null, null);
		}
	}
	
	
	@Override
	public UserInfo getUserInfoById(Integer userId, Integer joinId,
			boolean trimSocialAccount, boolean trimSocialAccountInfo,
			Integer atIndex, String atName, Integer objType, Integer objId) throws Exception {
		UserInfo userInfo = null;
		
		// 从at入口查询用户信息
		if(atIndex != null) {
			userId = userMsgService.queryAtId(objType, objId, atIndex, atName);
			if(userId == 0)
				throw new HTSException(TIP_USER_NAME_NOT_EXIST, USER_NAME_NOT_EXITS_ERROR);
			else { 
				userInfo = userInfoDao.queryUserInfoById(userId);
				if(!userInfo.getUserName().equals(atName)) // 用户改名,提示找不到用户
					throw new HTSException(TIP_USER_NAME_NOT_EXIST, USER_NAME_NOT_EXITS_ERROR);
			}
				
			
		} else {
			userInfo = userInfoDao.queryUserInfoById(userId);
		}
		
		if(joinId != 0 && !userId.equals(joinId)) {
			userInfo.setIsMututal(userConcernDao.queryIsMututal(joinId, userId));
			userInfo.setPlatformToken(null);
		}
		
		if(!trimSocialAccountInfo || !trimSocialAccount) {
			List<UserSocialAccountDto> accountInfo = socialAccountDao.querySocialAccountDtoByUserId(userId);
			if(!trimSocialAccountInfo) {
				userInfo.setSocialAccountInfo(accountInfo);
			}
			
			if(!trimSocialAccount) {
				// 兼容281以前的版本
				List<Integer> accounts = new ArrayList<Integer>();
				for(UserSocialAccountDto account : accountInfo) {
					accounts.add(account.getPlatformCode());
				}
				userInfo.setSocialAccounts(accounts);
			}
		}
		extractVerifyDesc(userInfo);
		userInteractService.extractRemark(joinId, userInfo);
		
		return userInfo;
	}
	
	@Override
	public UserInfo updateProfile(Integer userId, String userName, String userAvatar, String userAvatarL, 
			Integer sex, String email, String address, Date birthday, String province, 
			String city, Double longitude, Double latitude, String oriPassword, String password) throws Exception {
		UserInfo userInfo = userInfoDao.queryUserInfoWithPassById(userId);
		userName = StringUtil.trimName(userName);
		address = StringUtil.filterXSS(address);
		province = StringUtil.filterXSS(province);
		city = StringUtil.filterXSS(city);
		userAvatarL = StringUtil.replaceQiniuDomain(userAvatarL);
		userAvatar = StringUtil.replaceQiniuDomain(userAvatar);
//		if(userName != null && !userName.equals(userInfo.getUserName()) &&
//				userInfoDao.checkUserNameExists(userName)) { //检测用户名是否存在
//			HTSException e = new HTSException(TIP_USER_NAME_EXIST);
//			e.setErrorCode(USER_NAME_EXIST);
//			throw e;
//		}
		if(StringUtil.checkIsNULL(password)) {
			// 不设置密码
			
			setUpProfile(userInfo, userName, userAvatar, userAvatarL, 
					sex, email, address, birthday, province, city, longitude, latitude);
			osUserService.updateUserWithoutNULL(userInfo.getId(), userName,
					userAvatar, null, null, null, null);
			userInfoDao.updateProfile(userInfo);
			userInfo.setPasswordEncrypt(null);
		} else {
			//同时设置密码
			setUpProfile(userInfo, userName, userAvatar, userAvatarL, sex, email, address, birthday, province, 
					city, longitude, latitude);
			byte[] currentPass = userInfo.getPasswordEncrypt();
			if(currentPass != null && MD5Encrypt.validatePassword(oriPassword, currentPass)) {
				byte[] passEncrypt = MD5Encrypt.encryptByMD5(password);
				osUserService.updateUserWithoutNULL(userInfo.getId(), userName,
						userAvatar, null, null, null, null);
				userInfoDao.updateProfileAndPass(userInfo, passEncrypt);
				userInfo.setPasswordEncrypt(null);
			} else {
				HTSException e = new HTSException("旧密码错误");
				e.setErrorCode(PASSWORD_ERROR);
				throw e;
			}
		}
		return userInfo;
	}
	
	/**
	 * 设置用户个人资料
	 * 
	 * @param userInfo
	 * @param userName
	 * @param userAvatar
	 * @param userAvatarL
	 * @param sex
	 * @param email
	 * @param address
	 * @param birthday
	 * @param province
	 * @param city
	 * @param longitude
	 * @param latitude
	 */
	private void setUpProfile(UserInfo userInfo, String userName, String userAvatar, String userAvatarL, 
			Integer sex, String email, String address, Date birthday, String province, 
			String city, Double longitude, Double latitude) {
		
		userAvatarL = StringUtil.replaceQiniuDomain(userAvatarL);
		userAvatar = StringUtil.replaceQiniuDomain(userAvatar);
		userName = StringUtil.trimName(userName);
		
		if(!StringUtil.checkIsNULL(address))
		{
			if(address.contains("(null)")) 
				userInfo.setAddress(address.replace("(null)", ""));
			userInfo.setAddress(address);
		}
		
		if(!StringUtil.checkIsNULL(province)) 
			userInfo.setProvince(province);
		
		if(!StringUtil.checkIsNULL(city))
			userInfo.setCity(city);
		
		userInfo.setUserName(userName);
		userInfo.setUserAvatar(userAvatar);
		userInfo.setUserAvatarL(userAvatarL);
		userInfo.setSex(sex);
		userInfo.setEmail(email);
		userInfo.setBirthday(birthday);
		userInfo.setLongitude(longitude);
		userInfo.setLatitude(latitude);
	}


	@Override
	public void updateAcceptPushEnable(Integer userId, Integer action) {
		userInfoDao.updateAcceptPush(userId, action, Tag.TRUE);
	}
	
	@Override
	public void updateAcceptPushEnable(Integer userId, String actionStrs) {
		Integer[] actions = StringUtil.convertStringToIds(actionStrs);
		userInfoDao.updateAcceptPush(userId, actions, Tag.TRUE);
	}

	@Override
	public void updateAcceptPushDisable(Integer userId, Integer action) {
		userInfoDao.updateAcceptPush(userId, action, Tag.FALSE);
	}
	
	@Override
	public void updateAcceptPushDisable(Integer userId, String actionStrs) {
		if(!StringUtil.checkIsNULL(actionStrs)) {
			Integer[] actions = StringUtil.convertStringToIds(actionStrs);
			userInfoDao.updateAcceptPush(userId, actions, Tag.FALSE);
		}
	}

	@Override
	public void updateUserLabel(Integer userId, String userLabel)
			throws Exception {
		userLabel = StringUtil.filterXSS(userLabel);
		if(StringUtil.checkIsNULL(userLabel)) {
			userLabel = null;
		} else {
			if(userLabel.endsWith(",")) {userLabel = userLabel.substring(0, userLabel.lastIndexOf(","));}
		}
		userInfoDao.updateUserLabel(userId, userLabel);
	}

	@Override
	public void updateUserLabel(Integer userId, String labelIdsStr, String labels)
			throws Exception {
		if(StringUtil.checkIsNULL(labels)) {
			labels = null;
			userLabelDao.deleteUserLabel(userId);
		} else {
			if(labels.endsWith(",")) {labels = labels.substring(0, labels.lastIndexOf(","));}
			userLabelDao.deleteUserLabel(userId); // 删除原来的所有标签
			if(labelIdsStr != null) {
				Integer[] labelIds = StringUtil.convertStringToIds(labelIdsStr);
				for(Integer labelId : labelIds) {
					labelId = labelIdConvert(labelId);
					userLabelDao.saveUserLabel(userId, labelId);
				}
			}
		}
		userInfoDao.updateUserLabel(userId, labels);
	}
	
	@Override
	public void updateUserName(Integer userId, String userName)
			throws Exception {
		userName = StringUtil.trimName(userName);
		if(userInfoDao.checkUserNameExists(userName)) {
			throw new HTSException(TIP_USER_NAME_EXIST, USER_NAME_EXIST);
		}
		osUserService.updateUserWithoutNULL(userId, userName, null, null, null, null, null);
		userInfoDao.updateUserName(userId, userName);
	}

	@Override
	public void updateAvatar(Integer userId, String userAvatar,
			String userAvatarL) throws Exception {
		if(StringUtil.checkIsNULL(userAvatar) || StringUtil.checkIsNULL(userAvatarL)) {
			throw new HTSException("头像不能为空", AVATAR_ERROR);
		}
		userAvatarL = StringUtil.replaceQiniuDomain(userAvatarL);
		userAvatar = StringUtil.replaceQiniuDomain(userAvatar);
		osUserService.updateUserWithoutNULL(userId, null,
				userAvatar, null, null, null, null);
		userInfoDao.updateAvatar(userId, userAvatar, userAvatarL);
	}

	@Override
	public void updateEmail(Integer userId, String email) throws Exception {
		email = StringUtil.filterXSS(email);
		userInfoDao.updateEmail(userId, StringUtil.convertEmpty2NULL(email));
	}

	@Override
	public void updateAddress(Integer userId, String province, String city,
			Double longitude, Double latitude, String address) throws Exception {
		province = StringUtil.filterXSS(province);
		city = StringUtil.filterXSS(city);
		address = StringUtil.filterXSS(address);
		if(address != null && address.contains("(null)")) 
			address = address.replace("(null)", "").trim();
		
		userInfoDao.updateAddress(userId, 
				StringUtil.convertEmpty2NULL(province),
				StringUtil.convertEmpty2NULL(city),
				longitude, latitude,
				StringUtil.convertEmpty2NULL(address));
	}
	
	@Override
	public void updateSex(Integer userId, Integer sex) throws Exception {
		userInfoDao.updateSex(userId, sex);
	}
	
	@Override
	public void updateBirthday(Integer userId, Date birthday) throws Exception {
		userInfoDao.updateBirthday(userId, birthday);
	}
	
	@Override
	public void updatePassword(Integer userId, String oriPassword, String password) throws Exception {
		byte[] currentPass = userInfoDao.queryPassword(userId);
		
		if(currentPass != null && MD5Encrypt.validatePassword(oriPassword, currentPass)) {
			byte[] passEncrypt = MD5Encrypt.encryptByMD5(password);
			userInfoDao.updatePassword(userId, passEncrypt);
		} else {
			HTSException e = new HTSException("旧密码错误");
			e.setErrorCode(PASSWORD_ERROR);
			throw e;
		}
	}

	@Override
	public void updateSignature(Integer userId, String signature) throws Exception{
		signature = StringUtil.filterXSS(signature);
		osUserService.updateUserWithoutNULL(userId, null, null, signature, null, null, null);
		userInfoDao.updateSignature(userId, signature);
	}
	
	@Override
	public Integer labelIdConvert(Integer labelId) {
		switch (labelId) {
		case 15: //旅行达人
			labelId = 1;
			break;
		case 16: //时尚潮人
			labelId = 2;
			break;
		case 17: //摄影达人
			labelId = 3;
			break;
		case 18: //吃货
			labelId = 4;
			break;
		case 19: //死宅
			labelId = 5;
			break;
		case 20: //萌宠控
			labelId = 6;
			break;
		case 21: //文艺
			labelId = 7;
			break;
		case 22: //屌丝
			labelId = 8;
			break;
		case 23: //电影达人
			labelId = 9;
			break;
		case 24: //音乐达人
			labelId = 10;
			break;
		default:
			break;
		}
		return labelId;
	}

	@Override
	public void updateVerAndPushToken(Integer userId, Float ver, String pushToken,
			String phoneSys, String phoneVer) throws Exception {
		pushToken = StringUtil.checkIsNULL(pushToken) ? null : pushToken;
		phoneSys = phoneSys != null && phoneSys.length() > PHONE_SYS_MAX_LENGTH 
				? phoneSys.substring(0, PHONE_SYS_MAX_LENGTH) : phoneSys;
		phoneVer = phoneVer != null && phoneVer.length() > PHONE_VER_MAX_LENGTH 
				? phoneVer.substring(0, PHONE_VER_MAX_LENGTH) : phoneVer;
		ver = ver > VER_MAX ? VER_MAX : ver;
		userInfoDao.updateVerAndPushToken(userId, ver, pushToken, phoneSys, phoneVer);
//		updateOnlineByPushToken(pushToken, userId, Tag.FALSE);// 更新其他账号的登录状态
	}
	
	@Override
	public void pushRecommandUser(final String platformId, final String platformToken, final Integer userId, 
			final String userName) throws Exception{
		taskExecutor.execute(new Runnable(){
			@Override
			public void run(){
				try {
					String[] loginCodes = sinaWeiboService.getFollowerIdsById(platformId, platformToken, 500);
					if(loginCodes == null || loginCodes.length <= 0) return ;
					List<UserPushInfo> userList = userInfoDao.queryUserPushInfoByLoginCodes(loginCodes, PlatFormCode.SINA, Tag.VERSION_2_9_5);
					if(userList != null && userList.size() > 0){
						pushService.pushListMessage("微博好友@"+ PushUtil.getShortName(userName) + "加入了织图",Tag.PUSH_ACTION_WEIBO_FRIEND,
								String.valueOf(userId),userList,new PushFailedCallback() {
							@Override
							public void onPushFailed(Exception e) {
							}
						});
					}
				} catch(Exception e) {
				}
			}
		});
	}

	@Override
	public void extractVerify(List<? extends ObjectWithUserVerify> objs) {
		userVerifyCacheDao.setUpVerify(objs);
	}
	
	@Override
	public void extractVerify(ObjectWithUserVerify obj) {
		userVerifyCacheDao.setUpVerify(obj);
	}
	
	@Override
	public void extractVerifyDesc(ObjectWithUserVerifyDesc obj) {
		userVerifyCacheDao.setUpVerifyDesc(obj);
	}
	
	@Override
	public Map<Integer, UserVerify> getVerify() {
		return userVerifyCacheDao.queryVerify();
	}
	
	@Override
	public void updateJob(Integer userId, Integer tradeId, String job) throws Exception {
		userInfoDao.updateJob(userId, tradeId, job);
	}

	@Override
	public void buildUserAvatarLite(Integer id, Map<String, Object> jsonMap) throws Exception {
		UserAvatarLite avatar = userInfoDao.queryUserAvatarLite(id);
		if(avatar == null) {
			throw new HTSException("没有这个用户");
		}
		jsonMap.put(OptResult.JSON_KEY_USER_INFO, avatar);
	}


}
