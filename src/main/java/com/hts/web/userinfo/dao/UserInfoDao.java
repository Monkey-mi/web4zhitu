package com.hts.web.userinfo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserAvatar;
import com.hts.web.common.pojo.UserAvatarLite;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.pojo.UserInfoAvatar;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserMsgStatus;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.pojo.UserRecInfo;
import com.hts.web.common.pojo.UserSearchInfo;
import com.hts.web.common.pojo.UserWorldBase;

/**
 * <p>
 * 用户信息数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface UserInfoDao extends BaseDao {
	
	/**
	 * 构建用户信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UserInfoDto buildUserInfoDto(Integer userId, ResultSet rs) throws SQLException;

	/**
	 * 保存用户信息
	 * 
	 * @param userInfo
	 * @param passwordEncrypt
	 */
	public void saveUserInfo(UserInfo userInfo, byte[] passwordEncrypt);
	
	/**
	 * 查询指定平台的用户名是否存在
	 * 
	 * @param userName
	 * @return
	 */
	public boolean checkUserNameExists(String userName);
	
	/**
	 * 查询指定平台的用户名是否存在
	 * @param loginCode
	 * @param platformCode
	 * @return
	 */
	public boolean checkLoginCodeExists(String loginCode, Integer platformCode);
	
	/**
	 * 查询用户信息
	 * 
	 * @param loginCode
	 * @param platformCode
	 * @return 查询结果为空则返回NULL
	 */
	public UserInfo queryUserInfoByLoginCode(String loginCode, Integer platformCode);

	/**
	 * 根据登录账号查询用户id
	 * 
	 * @param loginCode
	 * @param platformCode
	 * @return
	 */
	public Integer queryUIDByLoginCode(String loginCode, Integer platformCode);
	
	/**
	 * 根据id查询用户名
	 * 
	 * @param userId
	 * @return
	 */
	public String queryUserNameById(Integer userId);
	
	/**
	 * 更新登陆状态
	 * 
	 * @param loginCode
	 * @param platformCode
	 * @param platformToken
	 * @param platformTokenExpires
	 * @param platformSign
	 * @param platformVerify
	 * @param platformReason
	 * @param pushToken
	 * @param phoneCode
	 * @param phoneSys
	 * @param phoneVer
	 * @param online
	 * @param ver
	 */
	public void updateLoginStatus(String loginCode, Integer platformCode, String platformToken, 
			Long platformTokenExpires, String platformSign, Integer platformVerify, String platformReason,
			String pushToken, Integer phoneCode, String phoneSys, String phoneVer, 
			Integer online, Float ver);
	
	/**
	 * 更新退出状态
	 * @param userId
	 */
	public void updateLogoutStatus(Integer userId);
	
	/**
	 * 根据id查询推送token
	 * @param userId
	 * @return
	 */
	public String queryPushTokenByUserId(Integer userId);
	
	/**
	 * 更新推送Token
	 * 
	 * @param userId
	 * @param push_token
	 * @param phoneCode
	 * @return
	 * @throws SQLException 
	 */
	public void updatePushTokenByUserId(Integer userId, String pushToken, Integer phoneCode);
	
	/**
	 * 根据id查询用户信息
	 * @param userId
	 * @return
	 */
	public UserInfo queryUserInfoById(Integer userId);
	
	/**
	 * 根据id查询用户信息并查询关联关系
	 * 
	 * @param userId
	 * @param joinId
	 * @return
	 */
	public UserInfo queryUserInfoWithJoinById(Integer userId, Integer joinId);
	
	/**
	 * 根据id查询用户信息及其密码
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfo queryUserInfoWithPassById(Integer userId);
	
	/**
	 * 查询关注总数
	 * 
	 * @param userId
	 * @return
	 */
	public int queryConcernCountForUpdate(Integer userId);
	
	/**
	 * 更新关注总数
	 * 
	 * @param userId
	 * @param count
	 */
	public void updateConcernCount(Integer userId, Integer count);
	
	/**
	 * 查询粉丝总数
	 * 
	 * @param userId
	 * @return
	 */
	public int queryFollowCountForUpdate(Integer userId);
	
	/**
	 * 更新粉丝总数
	 * @param userId
	 * @param count
	 */
	public void updateFollowCount(Integer userId, Integer count);
	
	/**
	 * 查询织图总数
	 * 
	 * @param userId
	 * @return
	 */
	public int queryWorldCountForUpdate(Integer userId);
	
	/**
	 * 更新织图总数
	 * 
	 * @param userId
	 * @param worldCount
	 * @param childCount
	 */
	public void updateWorldAndChildCount(Integer userId, Integer worldCount, Integer childCount);
	
	/**
	 * 查询喜欢总数
	 * 
	 * @param userId
	 * @return
	 */
	public int queryLikedCountForUpdate(Integer userId);
	
	/**
	 * 更新喜欢总数
	 * 
	 * @param userId
	 * @param count
	 */
	public void updateLikedCount(Integer userId, Integer count);
	
	/**
	 * 查询收藏总数
	 * 
	 * @param userId
	 * @return
	 */
	public int queryKeepCountForUpdate(Integer userId);
	
	/**
	 * 更新收藏总数
	 * 
	 * @param userId
	 * @param count
	 */
	public void updateKeepCount(Integer userId, Integer count);
	
	/**
	 * 更新个人资料
	 * 
	 * @param userInfo
	 */
	public void updateProfile(UserInfo userInfo);
	
	/**
	 * 更改密码
	 * 
	 * @param userId
	 * @param password
	 */
	public void updatePassword(Integer userId, byte[] password);
	
	/**
	 * 更新个人资料和密码
	 * 
	 * @param userInfo
	 * @param passEncrypt
	 */
	public void updateProfileAndPass(UserInfo userInfo, byte[] passEncrypt);
	
	/**
	 * 更新个性签名
	 * 
	 * @param userId
	 * @param signature
	 */
	public void updateSignature(Integer userId, String signature);
	
	/**
	 * 更新职业
	 * 
	 * @param userId
	 * @param job
	 */
	public void updateJob(Integer userId, Integer tradeId, String job);
	
	/**
	 * 查询用户推送信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserPushInfo queryUserPushInfoById(Integer userId);
	
	/**
	 * 查询用户推送信息列表
	 * 
	 * @param idsStr
	 * @return
	 */
	public List<UserPushInfo> queryUserPushInfoByIds(Integer[] userIds);
	
	/**
	 * 根据织图id查询用户推送信息
	 * 
	 * @param worldId
	 * @return
	 */
	public UserPushInfo queryUserPushInfoByWorldId(Integer worldId);
	
	
	/**
	 * 根据评论id查询用户推送信息
	 * 
	 * @param commentId
	 * @return
	 */
	public UserPushInfo queryUserPushInfoByCommentId(Integer commentId);
	
	/**
	 * 更新消息推送设置
	 * 
	 * @param userId
	 * @param pushType
	 * @param valid
	 */
	public void updateAcceptPush(Integer userId, Integer pushType, Integer valid);
	
	/**
	 * 更新消息推送设置
	 * 
	 * @param userId
	 * @param pushType
	 * @param valid
	 */
	public void updateAcceptPush(Integer userId, Integer[] pushTypes, Integer valid);
	
	/**
	 * 根据最小id和名字查询用户搜索信息
	 * 
	 * @param userName
	 * @param joinId
	 * @param minId
	 * @param rowSelection
	 * @return
	 */
	public List<UserSearchInfo> queryUserSearchInfoByNameAndMinId(String userName, Integer joinId, Integer minId, RowSelection rowSelection);
	
	/**
	 * 更新屏蔽标志
	 * 
	 * @param userId
	 * @param valid
	 */
	public void updateShield(Integer userId, Integer valid);
	
	/**
	 * 查询用户总数
	 * 
	 * @return
	 */
	public long queryUserInfoCount();
	
	/**
	 * 根据最大id查询用户总数
	 * 
	 * @param maxId
	 * @return
	 */
	public long queryUseInfoCountByMaxId(int maxId);
	
	
	/**
	 * 条件查询用户总数
	 * @param attrMap
	 * @return
	 */
	public long queryUserInfoCountByAttrMap(Map<String, Object> attrMap);
	
	

	/**
	 * 根据最大id和条件查询用户总数
	 * @param maxId
	 * @param attrMap
	 * @return
	 */
	public long queryUseInfoCountByMaxIdAndAttrMap(int maxId, Map<String, Object> attrMap);
	
	
	/**
	 * 根据用户名模糊查询用户总数
	 * @param userName
	 * @return
	 */
	public long queryUserInfoCountByUserName(String userName);
	
	
	/**
	 * 根据最大id和用户名模糊查询用户总数
	 * 
	 * @param maxId
	 * @param userName
	 * @return
	 */
	public long queryUseInfoCountByMaxIdAndUserName(int maxId, String userName);
	
	
	/**
	 * 根据id查询用户Tag标记，包括明星标记：star，屏蔽标记：shield
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> queryTagById(Integer userId);
	
	/**
	 * 查询用户id
	 * 
	 * @param userId
	 * @return
	 */
	public Integer queryShield(Integer userId);
	
	/**
	 * 更新用户标签
	 * @param userId
	 * @param userLabel
	 */
	public void updateUserLabel(Integer userId, String userLabel);
	
	/**
	 * 更新明星标记
	 * 
	 * @param userId
	 * @param start
	 */
	public void updateStartById(Integer userId, Integer start);
	
	/**
	 * 更新社交账号绑定信息
	 * 
	 * @param userId
	 * @param platformVerify
	 * @param platformSign
	 */
	public void updateSocialBindInfo(Integer userId, Integer platformVerify,
			String platformReason, String platformSign);
	
	/**
	 * 更新用户名
	 * 
	 * @param userId
	 * @param userName
	 */
	public void updateUserName(Integer userId, String userName);
	
	/**
	 * 更新头像
	 * 
	 * @param userId
	 * @param userAvatar
	 * @param userAvatarL
	 */
	public void updateAvatar(Integer userId, String userAvatar, String userAvatarL);
	
	/**
	 * 更新邮箱
	 * 
	 * @param userId
	 * @param email
	 */
	public void updateEmail(Integer userId, String email);
	
	/**
	 * 更新位置信息
	 * 
	 * @param userId
	 * @param province
	 * @param city
	 * @param longitude
	 * @param latitude
	 * @param address
	 */
	public void updateAddress(Integer userId, String province, String city,
			Double longitude, Double latitude, String address);
	
	
	/**
	 * 更新性别
	 * 
	 * @param userId
	 * @param sex
	 */
	public void updateSex(Integer userId, Integer sex);
	
	/**
	 * 更新生日
	 * 
	 * @param userId
	 * @param birthday
	 */
	public void updateBirthday(Integer userId, Date birthday);
	
	/**
	 * 查询用户密码
	 * 
	 * @param userId
	 * @return
	 */
	public byte[] queryPassword(Integer userId);
	
	/**
	 * 更新版本号
	 * 
	 * @param userId
	 * @param ver
	 * @param pushToken
	 * @param phoneSys
	 * @param phoneVer
	 */
	public void updateVerAndPushToken(Integer userId, Float ver, String pushToken,
			String phoneSys, String phoneVer);
	
	/**
	 * 更新活跃值
	 * 
	 * @param userId
	 * @param activity
	 */
	public void updateActivity(Integer userId, Integer activity);
	
	/**
	 * 
	 * @param loginCodes
	 * @return
	 */
	public List<UserInfoDto> queryRegister(String[] loginCodes);
	
	/**
	 * 查询平台用户对应的粉丝在织图注册的信息
	 * @param loginCodes
	 * @param platformCode
	 * @param minVer
	 * @return
	 */
	public List<UserPushInfo> queryUserPushInfoByLoginCodes(String[] loginCodes, Integer platformCode, 
			float minVer);
	
	/**
	 * 根据PushToken更新在线状态
	 * 
	 * @param pushToken
	 * @param exceptId
	 * @param online
	 */
	public void updateOnlineByPushToken(String pushToken, Integer exceptId, Integer online);
	
	/**
	 * 查询用户缩略图
	 * 
	 * @param uids
	 * @return
	 */
	public List<UserInfoAvatar> queryUserThumbnail(Integer[] uids);
	
	/**
	 * 查询用户缩略图
	 * 
	 * @param uid
	 * @return
	 */
	public UserInfoAvatar queryUserInfoAvatar(Integer uid);
	
	/**
	 * 查询用户头像
	 * 
	 * @param uid
	 * @return
	 */
	public UserAvatar queryUserAvatar(Integer uid);
	
	
	/**
	 * 查询用户消息状态
	 * 
	 * @param id
	 * @return
	 */
	public UserMsgStatus queryUserMsgStatus(Integer id);
	
	/**
	 * 设置{@link UserWorldBase}信息
	 * 
	 * @param rs
	 * @param user
	 */
	public void setUserWorldBaseInfo(ResultSet rs, UserWorldBase user) throws SQLException;
	
	/**
	 * 设置{@link UserWorldBase}信息
	 * 
	 * @param rs
	 * @param user
	 * @param userId
	 */
	public void setUserWorldBaseInfo(ResultSet rs, UserWorldBase user, Integer userId) throws SQLException;
	
	/**
	 * 查询关注总数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryConcernCount(Integer id);
	
	/**
	 * 更新被赞次数
	 * 
	 * @param uid
	 */
	public void updateLikeMeCount(Integer uid, Integer count);
	
	/**
	 * 查询省市信息
	 * 
	 * @param uid
	 * @return
	 */
	public UserRecInfo queryRecInfo(Integer uid);
	
	
	/**
	 * 更新loginCode
	 * 
	 * @param id
	 * @param loginCode
	 */
	public void updateLoginCodeByUID(Integer id, String loginCode);
	
	/**
	 * 查询UserInfoDto
	 * 
	 * @param id
	 * @return
	 */
	public UserInfoDto queryUserInfoDtoById(Integer id);

	/**
	 * 查询用户头像精简版本
	 * 
	 * @param id
	 * @return
	 */
	public UserAvatarLite queryUserAvatarLite(Integer id);
	
	public List<Integer> queryUID();
	
	public void updateShortLink(Integer uid, String shortLink);
	
	/**
	 * 查询不接受at的用户ids
	 * 
	 * @param uids
	 * @return
	 * @throws Exception
	 */
	public Set<Integer> queryNotAcceptAtUIds(Integer[] uids) throws Exception;
	
}
