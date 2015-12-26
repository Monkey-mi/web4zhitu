package com.hts.web.userinfo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.base.HTSException;
import com.hts.web.common.pojo.ObjectWithChecksum;
import com.hts.web.common.pojo.ObjectWithUserVerify;
import com.hts.web.common.pojo.ObjectWithUserVerifyDesc;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.pojo.UserSocialAccount;
import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户信息业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface UserInfoService extends BaseService {

	/**
	 * 检测账号是否存在
	 * @param loginCode
	 * @return
	 * @throws HTSException 
	 */
	public Integer checkLoginCodeExists(String loginCode, Integer platformCode) throws Exception;
	
	/**
	 * 检测用户名是否存在
	 * @param userName
	 * @param platformCode
	 * @return
	 * @throws HTSException
	 */
	public Integer checkUserNameExists(String userName) throws Exception;
	
	/**
	 * 检测是否使用指定平台登陆或绑定
	 * 
	 * @param platformCode
	 * @return
	 */
	public boolean isUsePlatformCode(Integer uid, Integer platformCode);
	
	/**
	 * 保存用户信息
	 * 
	 * @param loginCode
	 * @param password
	 * @param userName
	 * @param userAvatar
	 * @param userAvatarL
	 * @param sex
	 * @param signature
	 * @param address
	 * @param province
	 * @param city
	 * @param longitude
	 * @param latitude
	 * @param tradeId
	 * @param job
	 * @param pushToken
	 * @param phoneCode
	 * @param phoneSys
	 * @param phoneVer
	 * @param ver
	 * @return
	 * @throws Exception
	 */
	public UserInfo register(String loginCode, String password, String userName,
			String userAvatar, String userAvatarL, Integer sex, String signature,
			String address, String province, String city, Double longitude, Double latitude, 
			Integer tradeId, String job, String pushToken, Integer phoneCode, 
			String phoneSys, String phoneVer, Float ver) throws Exception;
	
	/**
	 * 通过第三方社交账号注册账号信息
	 * 
	 * @param platformCode
	 * @param platformToken
	 * @param platformTokenExpires
	 * @param platformSign
	 * @param platformVerify
	 * @param platformReason
	 * @param loginCode
	 * @param userName
	 * @param userAvatar
	 * @param userAvatarL
	 * @param sex
	 * @param pushToken
	 * @param phoneCode
	 * @param phoneSys
	 * @param phoneVer
	 * @param ver
	 * @return
	 * @throws Exception
	 */
	public UserInfo registerBySocialAccount(Integer platformCode, 
			String platformToken, Long platformTokenExpires, String platformSign, 
			Integer platformVerify, String platformReason, String loginCode, String userName,
			String userAvatar, String userAvatarL, Integer sex, String pushToken, 
			Integer phoneCode, String phoneSys, String phoneVer, Float ver) throws Exception;
	
	/**
	 * 登陆织图
	 * 
	 * @param loginCode
	 * @param password
	 * @param pushToken
	 * @param phoneCode
	 * @param phoneSys
	 * @param phoneVer
	 * @param ver
	 * @return
	 * @throws Exception
	 */
	public UserInfo login(String loginCode, String password, String pushToken, 
			Integer phoneCode, String phoneSys, String phoneVer, Float ver) throws Exception;
	
	/**
	 * 使用社交账号登陆
	 * 
	 * @param platformCode
	 * @param platformToken
	 * @param platformTokenExpires
	 * @param platformSign
	 * @param platformVerify
	 * @param loginCode
	 * @param userName
	 * @param userAvatar
	 * @param userAvatarL
	 * @param sex
	 * @param pushToken
	 * @param phoneCode
	 * @param phoneSys
	 * @param phoneVer
	 * @param ver
	 * @return
	 * @throws Exception
	 */
	public UserInfo loginBySocialAccount(Integer platformCode, 
			String platformToken, Long platformTokenExpires, String platformSign, 
			Integer platformVerify, String platformReason, String loginCode, String userName,
			String userAvatar, String userAvatarL,Integer sex, String pushToken, 
			Integer phoneCode, String phoneSys, String phoneVer, Float ver, String platformUnionId) throws Exception;
	
	
	/**
	 * 退出登陆状态
	 * @param userId
	 * @throws HTSException 
	 */
	public void logout(Integer userId, String pushToken, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 更新推送Token
	 * 
	 * @param userId
	 * @param pushToken
	 * @param phoneCode
	 * @throws HTSException 
	 */
	public void updatePushToken(Integer userId, String pushToken, Integer phoneCode) throws Exception;

	/**
	 * 保存社交绑定账号
	 * 
	 * @param userId
	 * @param platformCode
	 * @param platformToken
	 * @param platformTokenExpires
	 * @param platformId
	 * @param platformName
	 * @param platformAvatar
	 * @param platformAvatarL
	 */
	public void saveSocialAccount(Integer userId, Integer platformCode, 
			String platformToken, Long platformTokenExpires, String platformId, String platformName,
			String platformAvatar, String platformAvatarL, String platformSign, 
			Integer platformVerify, String platformReason);
	
	/**
	 * 保存或更新社交绑定账号
	 * 
	 * @param userId
	 * @param platformCode
	 * @param platformToken
	 * @param loginCode
	 * @param userName
	 * @param userAvatar
	 * @param userAvatarL
	 * @throws HTSException 
	 */
	public void saveOrUpdateSocialAccount(Integer userId, Integer platformCode, 
			String platformToken, Long platformTokenExpires, String platformId, String platformName,
			String platformAvatar, String platformAvatarL, String platformSign, 
			Integer platformVerify, String platformReason,  Map<String, Object> jsonMap) throws Exception; 
	
	/**
	 * 删除社交绑定账号
	 * 
	 * @param userId
	 * @param platformCode
	 */
	public void deleteSocialAccount(Integer userId, Integer platformCode) throws Exception;
		
	
	/**
	 * 根据id获取用户信息
	 * 
	 * @param userId
	 * @param joinId
	 * @param trimSocialAccount
	 * @param trimSocialAccountInfo
	 * @param atIndex
	 * @param atName
	 * @param objType
	 * @param objId
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUserInfoById(Integer userId, Integer joinId, 
			boolean trimSocialAccount, boolean trimSocialAccountInfo, 
			Integer atIndex, String atName, Integer objType, Integer objId) throws Exception;
	
	/**
	 * 根据用户Id查询用户的信息。
	 * @param userId 用户Id
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUserInfoById(Integer userId)throws Exception;
	
	/**
	 * 更新个人资料
	 * 
	 * @param userId
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
	 * @param oriPassword
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public UserInfo updateProfile(Integer userId, String userName, String userAvatar, String userAvatarL, 
			Integer sex, String email, String address, Date birthday, String province, 
			String city, Double longitude, Double latitude, String oriPassword, String password) throws Exception;
	
	/**
	 * 接收消息推送
	 * 
	 * @param userId
	 * @param action
	 */
	public void updateAcceptPushEnable(Integer userId, Integer action);
	
	/**
	 * 接收消息推送
	 * 
	 * @param userId
	 * @param actions
	 */
	public void updateAcceptPushEnable(Integer userId, String actions);
	
	/**
	 * 不接收消息推送
	 * 
	 * @param userId
	 * @param action
	 */
	public void updateAcceptPushDisable(Integer userId, Integer action);

	/**
	 * 不接收消息推送
	 * 
	 * @param userId
	 * @param actions
	 */
	public void updateAcceptPushDisable(Integer userId, String actions);
	
	/**
	 * 根据用户id绑定的社交账号列表
	 * @param userId
	 * @return
	 */
	public List<UserSocialAccount> getSocialAccountByUserId(int userId);
	
	/**
	 * 修改用户标签
	 * 
	 * @param userId
	 * @param userLabel
	 * @throws Exception
	 */
	public void updateUserLabel(Integer userId, String userLabel) throws Exception;
	
	/**
	 * 更新用户标签
	 * 
	 * @param userId
	 * @param labelIds
	 * @param labels
	 * @throws Exception
	 */
	public void updateUserLabel(Integer userId, String labelIds, String labels) throws Exception;

	/**
	 * 更新用户名
	 * 
	 * @param userId
	 * @param userName
	 */
	public void updateUserName(Integer userId, String userName) throws Exception;

	/**
	 * 更新头像
	 * 
	 * @param userId
	 * @param userAvatar
	 * @param userAvatarL
	 */
	public void updateAvatar(Integer userId, String userAvatar,
			String userAvatarL) throws Exception;


	/**
	 * 更新邮箱
	 * 
	 * @param userId
	 * @param email
	 */
	public void updateEmail(Integer userId, String email) throws Exception;

	/**
	 * 更新地址
	 * 
	 * @param userId
	 * @param province
	 * @param city
	 * @param longitude
	 * @param latitude
	 * @param address
	 */
	public void updateAddress(Integer userId, String province, String city,
			Double longitude, Double latitude, String address) throws Exception;

	/**
	 * 更新性别
	 * 
	 * @param userId
	 * @param sex
	 */
	public void updateSex(Integer userId, Integer sex) throws Exception;
	
	/**
	 * 更新生日
	 * 
	 * @param userId
	 * @param birthday
	 * @throws Exception
	 */
	public void updateBirthday(Integer userId, Date birthday) throws Exception;
	
	/**
	 * 更新密码
	 * 
	 * @param userId
	 * @param oriPassword
	 * @param password
	 */
	public void updatePassword(Integer userId, String oriPassword, String password) throws Exception;
	
	/**
	 * 更新个人资料
	 * 
	 * @param signature
	 */
	public void updateSignature(Integer userId, String signature) throws Exception;
	
	/**
	 * 更新职业
	 * 
	 * @param userId
	 * @param job
	 * @throws Exception
	 */
	public void updateJob(Integer userId, Integer tradeId, String job) throws Exception;
	
	/**
	 * 用户标签转换，为了兼容一些冗余并且被删除的标签
	 * 
	 * @param labelId
	 * @return
	 */
	public Integer labelIdConvert(Integer labelId);
	
	/**
	 * 更新版本号
	 * 
	 * @param userId
	 * @param ver
	 * @param pushToken
	 * @param phoneSys
	 * @param phoneVer
	 * @throws Exception
	 */
	public void updateVerAndPushToken(Integer userId, Float ver, String pushToken,
			String phoneSys, String phoneVer) throws Exception;
	
	/**
	 * 获取认证信息
	 * 
	 * @param objs
	 * @throws Exception
	 */
	public void extractVerify(List<? extends ObjectWithUserVerify> objs);
	
	/**
	 * 获取认证信息
	 * 
	 * @param obj
	 */
	public void extractVerify(ObjectWithUserVerify obj);
	
	/**
	 * 获取认证信息（包含描述信息）
	 * @param obj
	 */
	public void extractVerifyDesc(ObjectWithUserVerifyDesc obj);
	
	/**
	 * 获取所有认证信息
	 * 
	 * @return
	 */
	public Map<Integer, UserVerify> getVerify();
	
	/**
	 * 向用新浪账号在微博注册的织友推送他们在新浪关注的并刚注册织图账号的人
	 * 
	 * @param platformId
	 * @param platformToken
	 * @param userId
	 * @param userName
	 * @throws Exception
	 */
	public void pushRecommandUser(String platformId, String platformToken, Integer userId, String userName) throws Exception;
	
	/**
	 * 构建用户头像信息
	 * 
	 * @param id
	 * @param jsonMap
	 */
	public void buildUserAvatarLite(Integer id, 
			Map<String, Object> jsonMap) throws Exception;

	/**
	 * UID加密
	 * 
	 * @param obj
	 */
	public void checksum(ObjectWithChecksum obj);
	
	/**
	 * UID批量加密
	 * 
	 * @param objs
	 */
	public void checksum(List<? extends ObjectWithChecksum> objs);

	/**
	 * 更新背景图片
	 * 
	 * @param background	背景图片路径
	 * @param uid			用户id
	 * @author zhangbo	2015年12月21日
	 */
	void updateBackground(String background, Integer uid) throws Exception;

	/**
	 * 查询为用户提供的默认背景图片
	 * 
	 * @param jsonMap	返回值map
	 * @author zhangbo	2015年12月25日
	 */
	void queryDefaultUserBackground(Map<String, Object> jsonMap) throws Exception;

}
