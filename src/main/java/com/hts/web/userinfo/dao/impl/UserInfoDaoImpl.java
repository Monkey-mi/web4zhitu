package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserAvatarLite;
import com.hts.web.common.pojo.UserInfo;
import com.hts.web.common.pojo.UserInfoAvatar;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserMsgStatus;
import com.hts.web.common.pojo.UserPushInfo;
import com.hts.web.common.pojo.UserRecInfo;
import com.hts.web.common.pojo.UserSearchInfo;
import com.hts.web.common.pojo.UserWorldBase;
import com.hts.web.operations.dao.UserRecommendDao;
import com.hts.web.userinfo.dao.UserInfoDao;

/**
 * <p>
 * 用户信息数据访问对象
 * </p>
 * 
 * 创建时间：2013-6-21
 * @author ztj
 *
 */
@Repository("HTSUserInfoDao")
public class UserInfoDaoImpl extends BaseDaoImpl implements UserInfoDao{

	/**
	 * 用户信息表
	 */
	private static String table = HTS.USER_INFO;
	
	/**
	 * 用户推送信息字段
	 */
	private static final String U_PUSH_INFO = " u.id,u.user_name,u.push_token,u.phone_code,"
			+ "u.ver,u.online,u.accept_sys_push," 
			+ "u.accept_comment_push,u.accept_reply_push,u.accept_liked_push,"
			+ "u.accept_keep_push,u.accept_concern_push,u.accept_msg_push,u.accept_umsg_push ";
	
	/**
	 * 用户搜索信息字段
	 */
	protected static final String U0_SEARCH_INFO = "u0.id,u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar_l,u0.sex,u0.user_label,u0.address,u0.province,u0.city,"
			+ "u0.signature,u0.platform_sign,u0.star,u0.platform_verify";
	
	/**
	 * 头像缩略图信息字段
	 */
	private static final String THUMBNAIL_INFO = "id,user_name,user_avatar,user_avatar_l,"
			+ "province,city,star,platform_verify";
	
	/**
	 * 推荐信息
	 */
	private static final String REC_INFO = "id,platform_code,province,city,user_label,concern_count";
	
	/**
	 * 保存用户信息
	 */
	private static final String SAVE_USER_INFO = "insert into " + table + " (id, platform_code,platform_token," 
			+ "platform_token_expires,platform_sign,platform_verify,platform_reason,login_code,password,user_name,user_avatar,"
			+ "user_avatar_l,sex,signature,email,address,birthday,province,city,longitude,latitude," 
			+ "register_date,trade_id,job,push_token,phone_code,phone_sys,phone_ver,online, ver)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 根据账号查询平台用户信息
	 */
	private static final String QUERY_BY_LOGIN_CODE = "select distinct * from " + table 
			+ " where login_code=? and platform_code=?";

	/**
	 * 根据用户名查询用户总数
	 */
	private static final String QUERY_USER_COUNT_BY_USER_NAME = "select count(*) from " + table 
			+ " where user_name=?";
	
	/**
	 * 根据账号查询用户总数
	 */
	private static final String QUERY_USER_COUNT_BY_LOGIN_CODE = "select count(*) from " + table 
			+ " where login_code=? and platform_code=?";
	
	/**
	 * 根据id查询用户信息
	 */
	private static final String QUERY_BY_USER_ID = "select * from " + table + " where id=?";
	
	/**
	 * 根据id查询用户及其与指定用户的关联信息
	 */
	private static final String QUERY_WITH_JOIN_BY_USER_ID = "select u.*,uc.is_mututal from"
			+ " (select * from " + table + " as u0 where u0.id=?) as u"
			+ " left join " + HTS.USER_CONCERN + " as uc on uc.concern_id=u.id and uc.user_id=? and uc.valid=?";
	
	/**
	 * 根据id查询用户名
	 */
	private static final String QUERY_USER_NAME_BY_ID = "select user_name from " + table + " where id=?";
	
	/**
	 * 更新登陆状态
	 */
	private static final String UPDATE_LOGIN_STATUS = "update " + table 
			+ " set platform_token=?,platform_token_expires=?,platform_sign=?,platform_verify=?,platform_reason=?,"
			+ "push_token=?,phone_code=?,phone_sys=?,phone_ver=?,online=?,ver=? where login_code=? and platform_code=?";
	
	/**
	 * 更新退出登陆状态
	 */
	private static final String UPDATE_LOGOUT_STATUS = "update " + table + " set online=? where id=?";
	
	/**
	 * 根据id查询推送Token
	 */
	private static final String QUERY_PUSH_TOKEN_BY_USER_ID = "select push_token from " + table 
			+ " where id=?";
	
	/**
	 * 根据id更新推送Token
	 */
	private static final String UPDATE_PUSH_TOKEN_BY_USER_ID = "update " + table 
			+ " set push_token=? and phone_code=?,online=1 where id=?";
	
	/**
	 * 查询关注总数
	 */
	private static final String QUERY_CONCERN_COUNT_FOR_UPDATE = "select concern_count from " + table 
			+ " where id=? for update";
	
	/**
	 * 更新关注总数
	 */
	private static final String UPDATE_CONCERN_COUNT = "update " + table 
			+ " set concern_count=? where id=?";
	
	/**
	 * 查询粉丝总数
	 */
	private static final String QUERY_FOLLOW_COUNT_FOR_UPDATE = "select follow_count from " + table 
			+ " where id=? for update";
	
	/** 
	 * 更新粉丝总数
	 */
	private static final String UPDATE_FOLLOW_COUNT = "update " + table 
			+ " set follow_count=? where id=?";
	
	/**
	 * 查询织图总数
	 */
	private static final String QUERY_WORLD_COUNT_FOR_UPDATE = "select world_count from " + table
			+ " where id=? for update";
	
	/**
	 * 更新织图总数
	 */
	private static final String UPDATE_WORLD_AND_CHILD_COUNT = "update " + table
			+ " set world_count=?, child_count=? where id=?";
	
	/**
	 * 查询喜欢总数
	 */
	private static final String QUERY_LIKED_COUNT_FOR_UPDATE = "select liked_count from " + table
			+ " where id=? for update";
	
	/**
	 * 更新喜欢总数
	 */
	private static final String UPDATE_LIKED_COUNT = "update " + table
			+ " set liked_count=? where id=?";
	
	/**
	 * 查询收藏总数
	 */
	private static final String QUERY_KEEP_COUNT_FOR_UPDATE = "select keep_count from " + table 
			+ " where id=? for update";
	
	/**
	 * 更新收藏总数
	 */
	private static final String UPDATE_KEEP_COUNT = "update " + table
			+ " set keep_count=? where id=?";
	
	
	/**
	 * 更新个人资料
	 */
	private static final String UPDATE_PROFILE = "update " + table + 
			" set user_name=?,user_avatar=?,user_avatar_l=?,sex=?,email=?,"
			+ "address=?,birthday=?,province=?,city=?,longitude=?,latitude=? where id=?";
	
	/**
	 * 更新个人资料和密码
	 */
	private static final String UPDATE_PROFILE_AND_PASS = "update " + table 
			+ " set user_name=?,user_avatar=?,user_avatar_l=?,sex=?,email=?,address=?,"
			+ "birthday=?,province=?,city=?,longitude=?,latitude=?,password=? where id=?";
	
	/**
	 * 更新密码
	 */
	private static final String UPDATE_PASSWORD = "update " + table + " set password=? where id=?";
	
	/**
	 * 更新个性签名
	 */
	private static final String UPDATE_SIGNATURE = "update " + table + " set signature=? where id=?";
	
	/**
	 * 更新屏蔽标志
	 */
	private static final String UPDATE_SHIELD = "update " + table + " set shield=? where id=?";
	
	/**
	 * 查询用户推送信息头部
	 */
	private static final String QUERY_PUSH_INFO_HEAD = "select " + U_PUSH_INFO
			+ " from " + table + " as u";
	
	/**
	 * 根据id查询用户推送设置信息
	 */
	private static final String QUERY_PUSH_INFO = QUERY_PUSH_INFO_HEAD + " where u.id=?";
	
	/**
	 * 根据织图id查询用户推送设置信息
	 */
	private static final String QUERY_PUSH_INFO_BY_WORLD_ID = "select " + U_PUSH_INFO
			+ " from " + table + " as u," + HTS.HTWORLD_HTWORLD + " as h where h.author_id=u.id and h.id=?";
	
	/**
	 * 根据评论id查询用户推送设置信息
	 */
	private static final String QUERY_PUSH_INFO_BY_COMMENT_ID = "select " + U_PUSH_INFO
			+ " from " + table + " as u," + HTS.HTWORLD_COMMENT + " as c where c.author_id=u.id and c.id=?";
	
	/**
	 * 根据最小id和昵称模糊查询用户信息
	 */
	private static final String QUERY_USER_SEARCH_INFO_BY_NAME_AND_MIN_ID = "select u.*,uc.is_mututal from" 
			+ " (select " + U0_SEARCH_INFO + " from " + table 
			+ " as u0 where u0.user_name like ? and u0.id >=? and u0.shield=0 order by u0.id asc limit ?,?) as u"
			+ " left join " + HTS.USER_CONCERN + " as uc on u.id=uc.concern_id and uc.user_id=? and uc.valid=1";
	
	/**
	 * 查询用户总数
	 */
	private static final String QUERY_USRE_INFO_COUNT = "select count(*) from " + table;
	
	/**
	 * 根据最大id查询用户总数
	 */
	private static final String QUERY_USER_INFO_COUNT_BY_MAX_ID = "select count(*) from " + table + " where id<=?";
	
	/**
	 * 查询用户标记，star,trust,shield
	 */
	private static final String QUERY_TAG_BY_ID = "select star, trust, shield, ver from " + table + " where id=?";
	
	/**
	 * 更新用户 标签
	 */
	private static final String UPDATE_USER_LABEL = "update " + table + " set user_label=? where id=?";
	
	/**
	 * 更新用户明星标记
	 */
	private static final String UPDATE_STAR_BY_ID = "update " + table + " set star=? where id=?";
	
	/**
	 * 更新社交账号绑定信息
	 */
	private static final String UPDATE_SOCIAL_BING_INFO = "update " + table 
			+ " set platform_sign=?, platform_verify=?,platform_reason=? where id=?";
	
	/**
	 * 更新用户名
	 */
	private static final String UPDATE_USER_NAME = "update " + table 
			+ " set user_name=? where id=?";
	
	/**
	 * 更新头像
	 */
	private static final String UPDATE_AVATAR = "update " + table
			+ " set user_avatar=?, user_avatar_l=? where id=?";
	
	/**
	 * 更新邮箱
	 */
	private static final String UPDATE_EMAIL = "update " + table
			+ " set email=? where id=?";
	
	/**
	 * 更新性别
	 */
	private static final String UPDATE_SEX = "update " + table
			+ " set sex=? where id=?";
	
	/**
	 * 更新地址
	 */
	private static final String UPDATE_ADDRESS = "update " + table
			+ " set province=?,city=?,longitude=?,latitude=?,address=? where id=?";
	
	/**
	 * 更新生日
	 */
	private static final String UPDATE_BIRTHDAY = "update " + table
			+ " set birthday=? where id=?";
	
	/**
	 * 更新职业
	 */
	private static final String UPDATE_JOB = "update " + table
			+ " set trade_id=?,job=? where id=?";
	
	/**
	 * 根据id查询密码
	 */
	private static final String QUERY_PASS_BY_ID = "select password from " + table
			+ " where id=?";
	
	/**
	 * 更新版本号
	 */
	private static final String UPDATE_VER_AND_PUSH_TOKEN = "update " + table 
			+ " set ver=?,push_token=?,phone_sys=?,phone_ver=?,online=1 where id=?";
	
	/**
	 * 更新活跃度
	 */
	private static final String UPDATE_ACTIVITY = "update " + table + " set activity=activity+? where id=?";
	
	/**
	 * 根据loginCodes查询已经注册的用户
	 */
	private static final String QUERY_REGISTER_BY_LOGINCODES = "select u0.id,u0.login_code," + U0_INFO + " from " + table
			+ " as u0 where login_code in ";
	
	/**
	 * 根据推送Token更新在线状态
	 */
	private static final String UPDATE_ONLINE_BY_TOKEN = "update " + table
			+ " set online=? where push_token=? and id!=?";

	/**
	 * 根据ids查询头像缩略图
	 */
	private static final String QUERY_USER_THUMBNAIL_BY_IDS = "select "+ 
			THUMBNAIL_INFO + " from " + table + " where id in ";
	
	/**
	 * 根据id查询头像缩略图
	 */
	private static final String QUERY_USER_THUMBNAIL_BY_ID = "select "
			+ THUMBNAIL_INFO + " from " + table + " where id=?";
	
	/**
	 * 查询用户消息接受状态
	 */
	private static final String QUERY_USER_MSG_STATUS = "select id,user_name,user_avatar,user_avatar_l,"
			+ "address,province,city,ver,accept_umsg_push,online from "
			+ table + " where id=?";
	
	/**
	 * 查询关注数量
	 */
	private static final String QUERY_CONCERN_COUNT = "select concern_count from " + table
			+ " where id=?";
	
	/**
	 * 更新被赞次数
	 */
	private static final String UPDATE_LIKE_ME_COUNT = "update " + table
			+ " set like_me_count=? where id=?";
	
	/**
	 * 根据登录账号查询id
	 */
	private static final String QUERY_UID_BY_LOGIN_CODE = "select id from " + table
			+ " where login_code=? and platform_code=?";
	
	/**
	 * 查询位置信息
	 */
	private static final String QUERY_REC_INFO = "select " + REC_INFO 
			+ " from " + table + " where id=?";
	
	/**
	 * 根据id更新登录账号
	 */
	private static final String UPDATE_LOGIN_CODE_BY_UID = "update " + table
			+ " set login_code=? where id=?";
	
	/**
	 * 根据id查询UserInfoDto
	 */
	private static final String QUERY_USER_INFO_DTO_BY_ID = "select u0.id," + U0_INFO
			+ " from " + table + " as u0 where u0.id=?";
	
	
	private static final String QUERY_USER_AVATAR_LITE = "select u0.id,u0.user_name,u0.user_avatar" 
			+ " from " + table + " u0 where u0.id=?";
	
	
	@Autowired
	private UserRecommendDao userRecommendDao;
	
	@Override
	public UserInfoDto buildUserInfoDto(Integer userId, ResultSet rs) throws SQLException {
		return buildUserInfoDtoByResult(userId, rs);
	}
	
	@Override
	public void saveUserInfo(UserInfo userInfo, byte[] passwordEncrypt) {
		getMasterJdbcTemplate().update(SAVE_USER_INFO, new Object[]{
				userInfo.getId(),
				userInfo.getPlatformCode(),
				userInfo.getPlatformToken(),
				userInfo.getPlatformTokenExpires(),
				userInfo.getPlatformSign(),
				userInfo.getPlatformVerify(),
				userInfo.getPlatformReason(),
				userInfo.getLoginCode(),
				passwordEncrypt,
				userInfo.getUserName(),
				userInfo.getUserAvatar(),
				userInfo.getUserAvatarL(),
				userInfo.getSex(),
				userInfo.getSignature(),
				userInfo.getEmail(),
				userInfo.getAddress(),
				userInfo.getBirthday(),
				userInfo.getProvince(),
				userInfo.getCity(),
				userInfo.getLongitude(),
				userInfo.getLatitude(),
				userInfo.getRegisterDate(),
				userInfo.getTradeId(),
				userInfo.getJob(),
				userInfo.getPushToken(),
				userInfo.getPhoneCode(),
				userInfo.getPhoneSys(),
				userInfo.getPhoneVer(),
				userInfo.getOnline(),
				userInfo.getVer()});
	}
	
	@Override
	public boolean checkUserNameExists(String userName) {
		long count = getJdbcTemplate().queryForLong(QUERY_USER_COUNT_BY_USER_NAME, new Object[]{userName});
		if(count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean checkLoginCodeExists(String loginCode, Integer platformCode) {
		long count = getJdbcTemplate().queryForLong(QUERY_USER_COUNT_BY_LOGIN_CODE, new Object[]{loginCode, platformCode});
		if(count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public UserInfo queryUserInfoByLoginCode(String loginCode, Integer platformCode) {
		return queryForObjectWithNULL(QUERY_BY_LOGIN_CODE, new Object[]{loginCode, platformCode}, new RowMapper<UserInfo>(){

				@Override
				public UserInfo mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildUserInfoAndPassByResultSet(rs);
				}
			});
	}
	
	@Override
	public String queryUserNameById(Integer userId) {
		return queryForObjectWithNULL(QUERY_USER_NAME_BY_ID, String.class, userId);
	}
	
	@Override
	public void updateLoginStatus(String loginCode, Integer platformCode, String platformToken,
			Long platformTokenExpires, String platformSign, Integer platformVerify, 
			String platformReason,String pushToken, Integer phoneCode, String phoneSys, String phoneVer, 
			Integer online, Float ver){
		getMasterJdbcTemplate().update(UPDATE_LOGIN_STATUS, new Object[]{
				platformToken,platformTokenExpires,platformSign, platformVerify, platformReason,
				pushToken, phoneCode, phoneSys, phoneVer, online, ver, loginCode, platformCode
		});
	}
	
	@Override
	public void updateLogoutStatus(Integer userId) {
		getMasterJdbcTemplate().update(UPDATE_LOGOUT_STATUS, new Object[]{Tag.OFFLINE, userId});
	}
	
	@Override
	public String queryPushTokenByUserId(Integer userId){
		return queryForObjectWithNULL(QUERY_PUSH_TOKEN_BY_USER_ID, new Object[]{userId}, String.class);
	}
	
	@Override
	public void updatePushTokenByUserId(Integer userId, String pushToken, Integer phoneCode){
		getMasterJdbcTemplate().update(UPDATE_PUSH_TOKEN_BY_USER_ID, new Object[]{pushToken, phoneCode, userId});
	}
	
	@Override
	public UserInfo queryUserInfoById(Integer userId) {
		return queryForObjectWithNULL(QUERY_BY_USER_ID, new Object[]{userId}, new RowMapper<UserInfo>(){

			@Override
			public UserInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoByResultSet(rs);
			}
		});
	}
	
	@Override
	public UserInfo queryUserInfoWithJoinById(Integer userId, Integer joinId) {
		return queryForObjectWithNULL(QUERY_WITH_JOIN_BY_USER_ID, 
				new Object[]{userId,joinId, Tag.TRUE}, new RowMapper<UserInfo>() {

			@Override
			public UserInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserInfo userInfo = buildUserInfoByResultSet(rs);
				userInfo.setIsMututal(UserConcernDaoImpl.getIsMututal(rs));
				return userInfo;
			}
		
		});
	}
	
	@Override
	public UserInfo queryUserInfoWithPassById(Integer userId)  {
		return queryForObjectWithNULL(QUERY_BY_USER_ID, new Object[]{userId}, new RowMapper<UserInfo>(){

			@Override
			public UserInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserInfoAndPassByResultSet(rs);
			}
		});
	}
	
	@Override
	public void updateProfile(UserInfo userInfo) {
		getMasterJdbcTemplate().update(UPDATE_PROFILE, new Object[]{
				userInfo.getUserName(),
				userInfo.getUserAvatar(),
				userInfo.getUserAvatarL(),
				userInfo.getSex(),
				userInfo.getEmail(),
				userInfo.getAddress(),
				userInfo.getBirthday(),
				userInfo.getProvince(),
				userInfo.getCity(),
				userInfo.getLongitude(),
				userInfo.getLatitude(),
				userInfo.getId()
			});
	}
	
	@Override
	public void updateProfileAndPass(UserInfo userInfo, byte[] passEncrypt) {
		getMasterJdbcTemplate().update(UPDATE_PROFILE_AND_PASS, new Object[]{
				userInfo.getUserName(),
				userInfo.getUserAvatar(),
				userInfo.getUserAvatarL(),
				userInfo.getSex(),
				userInfo.getEmail(),
				userInfo.getAddress(),
				userInfo.getBirthday(),
				userInfo.getProvince(),
				userInfo.getCity(),
				userInfo.getLongitude(),
				userInfo.getLatitude(),
				passEncrypt,
				userInfo.getId()
			});
	}
	
	/**
	 * 根据游标构建用户信息POJO
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public static UserInfo buildUserInfoAndPassByResultSet(ResultSet rs) throws SQLException {
		UserInfo userInfo = buildUserInfoByResultSet(rs);
		userInfo.setPasswordEncrypt(rs.getBytes("password"));
		return userInfo;
	}
	
	@Override
	public UserPushInfo queryUserPushInfoById(Integer userId) {
		return queryForObjectWithNULL(QUERY_PUSH_INFO, new RowMapper<UserPushInfo>(){

			@Override
			public UserPushInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserPushInfoByResult(rs);
			}
		}, userId);
	}
	
	@Override
	public List<UserPushInfo> queryUserPushInfoByIds(Integer[] userIds) {
		String selection = SQLUtil.buildInSelection(userIds);
		String sql = QUERY_PUSH_INFO_HEAD + " where u.id in " + selection;
		return getJdbcTemplate().query(sql, new RowMapper<UserPushInfo>(){

			@Override
			public UserPushInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserPushInfoByResult(rs);
			}
		}, (Object[])userIds);
	}
	
	@Override
	public List<UserPushInfo> queryUserPushInfoByLoginCodes(String[] loginCodes,Integer platformCode,
			float minVer){
		String selection = SQLUtil.buildInSelection(loginCodes);
		String sql = QUERY_PUSH_INFO_HEAD + " where u.platform_code=" + platformCode 
				+ " and ver>=" + minVer + " and u.login_code in " + selection;
		return getJdbcTemplate().query(sql, new RowMapper<UserPushInfo>(){

			@Override
			public UserPushInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserPushInfoByResult(rs);
			}
		}, (Object[])loginCodes);
	}

	@Override
	public UserPushInfo queryUserPushInfoByWorldId(Integer worldId) {
		return queryForObjectWithNULL(QUERY_PUSH_INFO_BY_WORLD_ID, new RowMapper<UserPushInfo>(){

			@Override
			public UserPushInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserPushInfoByResult(rs);
			}
		}, worldId);
	}

	@Override
	public UserPushInfo queryUserPushInfoByCommentId(Integer commentId) {
		return queryForObjectWithNULL(QUERY_PUSH_INFO_BY_COMMENT_ID, new RowMapper<UserPushInfo>(){

			@Override
			public UserPushInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserPushInfoByResult(rs);
			}
		}, commentId);
	}
	
	@Override
	public List<UserSearchInfo> queryUserSearchInfoByNameAndMinId(String userName,
			Integer joinId, Integer minId, RowSelection rowSelection) {
		return queryForPage(QUERY_USER_SEARCH_INFO_BY_NAME_AND_MIN_ID, new Object[]{"%"+userName+"%", minId, 
					rowSelection.getFirstRow(), rowSelection.getLimit(), joinId},
					new RowMapper<UserSearchInfo>() {

					@Override
					public UserSearchInfo mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildUserSearchInfoByResult(rs);
					}
				}, rowSelection);
	}
	
	@Override
	public void updateAcceptPush(Integer userId, Integer pushType, Integer valid) {
		updateAcceptPush(userId, new Integer[]{pushType}, valid);
	}
	
	@Override
	public void updateAcceptPush(Integer userId, Integer[] pushTypes, Integer valid) {
		Object[] args = new Object[pushTypes.length + 1];
		StringBuilder builder = new StringBuilder("update ").append(table).append(" set ");
		for(int i = 0; i < pushTypes.length; i++){
			Integer action = pushTypes[i];
			if(i > 0) {
				builder.append(", ");
			}
			String type = getPushTypeByAction(action);
			builder.append(type).append("=? ");
			args[i]= valid;
		}
		args[args.length - 1] = userId;
		builder.append("where id=?");
		String sql = builder.toString();
		getMasterJdbcTemplate().update(sql, args);
		
	}
	
	private String getPushTypeByAction(Integer action) {
		String type = null;
		switch (action) {
		case Tag.PUSH_ACTION_COMMENT:
			type = "accept_comment_push";
			break;
		case Tag.PUSH_ACTION_REPLY:
			type = "accept_reply_push";
			break;
		case Tag.PUSH_ACTION_LIKED:
			type = "accept_liked_push";
			break;
		case Tag.PUSH_ACTION_KEEP:
			type = "accept_keep_push";
			break;
		case Tag.PUSH_ACTION_CONCERN:
			type = "accept_concern_push";
			break;
		case Tag.PUSH_ACTION_SYS_MSG:
			type = "accept_msg_push";
			break;
		case Tag.PUSH_ACTION_USER_MSG:
			type = "accept_umsg_push";
			break;
		default:
			type = "accept_sys_push";
			break;
		}
		return type;
	}
	
	@Override
	public long queryUserInfoCount() {
		return getJdbcTemplate().queryForLong(QUERY_USRE_INFO_COUNT);
	}

	@Override
	public long queryUseInfoCountByMaxId(int maxId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_INFO_COUNT_BY_MAX_ID, maxId);
	}
	
	@Override
	public long queryUserInfoCountByAttrMap(Map<String, Object> attrMap) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = "select count(*) from " + table + selection;
		Object[] args = attrMap.values().toArray();
		return getJdbcTemplate().queryForLong(sql, args);
	}

	@Override
	public long queryUseInfoCountByMaxIdAndAttrMap(int maxId, Map<String, Object> attrMap) {
		String selection = SQLUtil.buildSelection(attrMap);
		String sql = "select count(*) from " + table + selection + " and id<=?";
		attrMap.put("id", maxId);
		Object[] args = attrMap.values().toArray();
		return getJdbcTemplate().queryForLong(sql, args);
	}
	
	@Override
	public long queryUserInfoCountByUserName(String userName) {
		String sql = "select count(*) from " + table + " where user_name like ? ";
		return getJdbcTemplate().queryForLong(sql, "%"+userName+"%");
	}
	

	@Override
	public long queryUseInfoCountByMaxIdAndUserName(int maxId, String userName) {
		String sql = "select count(*) from " + table + " where user_name like ? and id<=?";
		return getJdbcTemplate().queryForLong(sql, new Object[]{"%"+userName+"%", maxId});
	}
	
	@Override
	public void updateShield(Integer userId, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_SHIELD, new Object[]{valid, userId});
	}
	
	@Override
	public Map<String, Object> queryTagById(Integer userId) {
		return getJdbcTemplate().queryForMap(QUERY_TAG_BY_ID, userId);
	}
	
	@Override
	public void updateUserLabel(Integer userId, String userLabel) {
		getMasterJdbcTemplate().update(UPDATE_USER_LABEL, new Object[]{userLabel, userId});
	}

	@Override
	public int queryConcernCountForUpdate(Integer userId) {
		return getJdbcTemplate().queryForInt(QUERY_CONCERN_COUNT_FOR_UPDATE, userId);
	}

	@Override
	public void updateConcernCount(Integer userId, Integer count) {
		getMasterJdbcTemplate().update(UPDATE_CONCERN_COUNT, new Object[]{count, userId});
	}

	@Override
	public int queryFollowCountForUpdate(Integer userId) {
		return getJdbcTemplate().queryForInt(QUERY_FOLLOW_COUNT_FOR_UPDATE, userId);
	}
	
	@Override
	public void updateFollowCount(Integer userId, Integer count) {
		getMasterJdbcTemplate().update(UPDATE_FOLLOW_COUNT, new Object[]{count, userId});
	}

	@Override
	public int queryWorldCountForUpdate(Integer userId) {
		return getJdbcTemplate().queryForInt(QUERY_WORLD_COUNT_FOR_UPDATE, userId);
	}

	@Override
	public void updateWorldAndChildCount(Integer userId, 
			Integer worldCount, Integer childCount) {
		getMasterJdbcTemplate().update(UPDATE_WORLD_AND_CHILD_COUNT, 
				new Object[]{worldCount, childCount, userId});
	}

	@Override
	public int queryLikedCountForUpdate(Integer userId) {
		return getJdbcTemplate().queryForInt(QUERY_LIKED_COUNT_FOR_UPDATE, userId);
	}

	@Override
	public void updateLikedCount(Integer userId, Integer count) {
		getMasterJdbcTemplate().update(UPDATE_LIKED_COUNT, new Object[]{count, userId});
	}

	@Override
	public int queryKeepCountForUpdate(Integer userId) {
		return getJdbcTemplate().queryForInt(QUERY_KEEP_COUNT_FOR_UPDATE, userId);
	}

	@Override
	public void updateKeepCount(Integer userId, Integer count) {
		getMasterJdbcTemplate().update(UPDATE_KEEP_COUNT, new Object[]{count, userId});
	}
	
	@Override
	public void updateStartById(Integer userId, Integer start) {
		getMasterJdbcTemplate().update(UPDATE_STAR_BY_ID, new Object[]{start, userId});
	}
	
	@Override
	public void updateSocialBindInfo(Integer userId, Integer platformVerify, 
			String platformReason, String platformSign) {
		getMasterJdbcTemplate().update(UPDATE_SOCIAL_BING_INFO, 
				new Object[]{platformSign, platformVerify, platformReason, userId});
	}
	
	@Override
	public void updateUserName(Integer userId, String userName) {
		getMasterJdbcTemplate().update(UPDATE_USER_NAME, 
				new Object[]{userName,userId});
	}

	@Override
	public void updateAvatar(Integer userId, String userAvatar,
			String userAvatarL) {
		getMasterJdbcTemplate().update(UPDATE_AVATAR, 
				new Object[]{userAvatar, userAvatarL, userId});
	}

	@Override
	public void updateEmail(Integer userId, String email) {
		getMasterJdbcTemplate().update(UPDATE_EMAIL, 
				new Object[]{email, userId});
	}
	
	@Override
	public void updateBirthday(Integer userId, Date birthday) {
		getMasterJdbcTemplate().update(UPDATE_BIRTHDAY, new Object[]{birthday, userId});
	}

	@Override
	public void updateAddress(Integer userId, String province, String city,
			Double longitude, Double latitude, String address) {
		getMasterJdbcTemplate().update(UPDATE_ADDRESS, 
				new Object[]{province,city, longitude, latitude, address, userId});
	}

	@Override
	public void updateSex(Integer userId, Integer sex) {
		getMasterJdbcTemplate().update(UPDATE_SEX, 
				new Object[]{sex, userId});
	}
	
	@Override
	public void updatePassword(Integer userId, byte[] password) {
		getMasterJdbcTemplate().update(UPDATE_PASSWORD, new Object[]{password, userId});
	}
	
	@Override
	public void updateSignature(Integer userId, String signature) {
		getMasterJdbcTemplate().update(UPDATE_SIGNATURE, new Object[]{signature, userId});
	}
	
	@Override
	public void updateJob(Integer userId, Integer tradeId, String job) {
		getMasterJdbcTemplate().update(UPDATE_JOB, new Object[]{tradeId, job, userId});
	}
	
	@Override
	public byte[] queryPassword(Integer userId) {
		return queryForObjectWithNULL(QUERY_PASS_BY_ID, new Object[]{userId},
				byte[].class);
	}
	
	@Override
	public void updateVerAndPushToken(Integer userId, Float ver, String pushToken, 
			String phoneSys, String phoneVer) {
		getMasterJdbcTemplate().update(UPDATE_VER_AND_PUSH_TOKEN, new Object[]{ver, pushToken,
				phoneSys, phoneVer, userId});
	}

	@Override
	public void updateActivity(Integer userId, Integer activity) {
		getMasterJdbcTemplate().update(UPDATE_ACTIVITY, new Object[]{activity,userId});
	}

	@Override
	public List<UserInfoDto> queryRegister(String[] loginCodes) {
		String inSelection = SQLUtil.buildInSelection(loginCodes);
		String sql = QUERY_REGISTER_BY_LOGINCODES + inSelection;
		return getJdbcTemplate().query(sql, (Object[])loginCodes, new RowMapper<UserInfoDto>() {

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserInfoDto dto = buildUserInfoDtoByResult(rs.getInt("id"), rs);
				dto.setLoginCode(rs.getString("login_code"));
				return dto;
			}
			
		});
	}

	@Override
	public void updateOnlineByPushToken(String pushToken, Integer exceptId, Integer online) {
		getMasterJdbcTemplate().update(UPDATE_ONLINE_BY_TOKEN, new Object[]{online, pushToken, exceptId});
	}
	
	@Override
	public List<UserInfoAvatar> queryUserThumbnail(Integer[] uids) {
		String inSelection = SQLUtil.buildInSelection(uids);
		String sql = QUERY_USER_THUMBNAIL_BY_IDS + inSelection;
		return getJdbcTemplate().query(sql, uids, new RowMapper<UserInfoAvatar>() {

			@Override
			public UserInfoAvatar mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildThumbnail(rs);
			}
		});
	}
	
	@Override
	public UserInfoAvatar queryUserAvatar(Integer uid) {
		return queryForObjectWithNULL(QUERY_USER_THUMBNAIL_BY_ID, new Object[]{uid}, 
				new RowMapper<UserInfoAvatar>() {

			@Override
			public UserInfoAvatar mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildThumbnail(rs);
			}
		});
	}
	
	@Override
	public Integer queryConcernCount(Integer id) {
		return getJdbcTemplate().queryForInt(QUERY_CONCERN_COUNT, id);
	}
	
	@Override
	public void updateLikeMeCount(Integer uid, Integer count) {
		getMasterJdbcTemplate().update(UPDATE_LIKE_ME_COUNT, new Object[]{count, uid});
	}
	
	@Override
	public Integer queryUIDByLoginCode(String loginCode, Integer platformCode) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_UID_BY_LOGIN_CODE,
					new Object[]{loginCode, platformCode});
		} catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	@Override
	public UserMsgStatus queryUserMsgStatus(Integer id) {
		return queryForObjectWithNULL(QUERY_USER_MSG_STATUS, new Object[]{id}, 
				new RowMapper<UserMsgStatus>() {

					@Override
					public UserMsgStatus mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new UserMsgStatus(
								rs.getInt("id"), 
								rs.getString("user_name"), 
								rs.getString("user_avatar"),
								rs.getString("user_avatar_l"),
								rs.getString("address"),
								rs.getString("province"),
								rs.getString("city"),
								rs.getFloat("ver"),
								rs.getInt("accept_umsg_push"),
								rs.getInt("online"));
					}
		});
	}
	
	/**
	 * 根据游标构建用户信息POJO
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public static UserInfo buildUserInfoByResultSet(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new UserInfo(
				rs.getInt("id"),
				rs.getInt("platform_code"),
				rs.getString("platform_token"),
				rs.getLong("platform_token_expires"),
				rs.getString("platform_sign"),
				rs.getInt("platform_verify"),
				rs.getString("platform_reason"),
				rs.getString("login_code"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("sex"),
				rs.getString("email"),
				rs.getString("address"),
				birthday,
				rs.getString("signature"),
				rs.getString("user_label"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getDouble("longitude"),
				rs.getDouble("latitude"),
				(Date)rs.getObject("register_date"),
				rs.getInt("trade_id"),
				rs.getString("job"),
				rs.getString("push_token"),
				rs.getInt("phone_code"),
				rs.getString("phone_sys"),
				rs.getString("phone_ver"),
				rs.getInt("online"),
				rs.getInt("accept_sys_push"),
				rs.getInt("accept_comment_push"),
				rs.getInt("accept_reply_push"),
				rs.getInt("accept_liked_push"),
				rs.getInt("accept_keep_push"),
				rs.getInt("accept_concern_push"),
				rs.getInt("accept_msg_push"),
				rs.getInt("accept_umsg_push"),
				rs.getFloat("ver"),
				rs.getInt("concern_count"),
				rs.getInt("follow_count"),
				rs.getInt("world_count"),
				rs.getInt("child_count"),
				rs.getInt("liked_count"),
				rs.getInt("like_me_count"),
				rs.getInt("keep_count"),
				rs.getInt("star"));
	}
	
	
	/**
	 * 从结果集构建用户信息POJO
	 * @param res
	 * @return
	 * @throws SQLException 
	 */
	public static UserInfoDto buildUserInfoDtoByResult(int userId, ResultSet rs) throws SQLException{
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new UserInfoDto(
				userId,
				rs.getString("user_name"), 
				rs.getString("user_avatar"), 
				rs.getString("user_avatar_l"), 
				rs.getInt("sex"), 
				rs.getString("email"),
				rs.getString("address"),
				rs.getString("u_province"),
				rs.getString("u_city"),
				birthday,
				rs.getString("signature"),
				(Date)rs.getObject("register_date"), 
				rs.getString("user_label"),
				rs.getInt("star"),
				rs.getInt("u_phone_code"),
				rs.getInt("online"),
				rs.getInt("platform_verify"));
	}
	
	/**
	 * 从结果集构建用户推送信息POJO
	 * 
	 * @param userId
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public  UserPushInfo buildUserPushInfoByResult(ResultSet rs) throws SQLException {
		return new UserPushInfo(
				rs.getInt("id"),
				rs.getString("user_name"),
				rs.getInt("online"), 
				rs.getInt("phone_code"),
				rs.getString("push_token"), 
				rs.getFloat("ver"),
				rs.getInt("accept_sys_push"),
				rs.getInt("accept_comment_push"), 
				rs.getInt("accept_reply_push"), 
				rs.getInt("accept_liked_push"), 
				rs.getInt("accept_keep_push"), 
				rs.getInt("accept_concern_push"),
				rs.getInt("accept_msg_push"),
				rs.getInt("accept_umsg_push"));
	}
	
	/**
	 * 从结果集构建用户搜索信息
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static UserSearchInfo buildUserSearchInfoByResult(ResultSet rs) throws SQLException {
		return new UserSearchInfo(
				rs.getInt("id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getInt("sex"),
				rs.getString("user_label"),
				rs.getString("address"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getString("signature"),
				rs.getString("platform_sign"),
				UserConcernDaoImpl.getIsMututal(rs),
				rs.getInt("star"),
				rs.getInt("platform_verify"));
	}
	
	/**
	 * 构建用户缩略图信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UserInfoAvatar buildThumbnail(ResultSet rs) throws SQLException {
		return new UserInfoAvatar(
				rs.getInt("id"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getInt("star"),
				rs.getInt("platform_verify"));
	}

	@Override
	public void setUserWorldBaseInfo(ResultSet rs, UserWorldBase user) throws SQLException{
		setUserWorldBaseInfo(rs, user, rs.getInt("id"));
	}
	
	@Override
	public void setUserWorldBaseInfo(ResultSet rs, UserWorldBase user, Integer userId) throws SQLException{
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		user.setId(userId);
		user.setUserName(rs.getString("user_name"));
		user.setUserAvatar(rs.getString("user_name"));
		user.setUserAvatarL(rs.getString("user_avatar_l"));
		user.setSex(rs.getInt("sex"));
		user.setEmail(rs.getString("email"));
		user.setAddress(rs.getString("address"));
		user.setProvince(rs.getString("u_province"));
		user.setCity(rs.getString("u_city"));
		user.setBirthday(birthday);
		user.setSignature(rs.getString("signature"));
		user.setBirthday((Date)rs.getObject("register_date"));
		user.setUserLabel(rs.getString("user_label"));
		user.setStar(rs.getInt("star"));
		user.setPhoneCode(rs.getInt("u_phone_code"));
		user.setOnline(rs.getInt("online"));
		user.setPlatformVerify(rs.getInt("platform_verify"));
	}

	@Override
	public UserRecInfo queryRecInfo(Integer uid) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_REC_INFO, 
					new Object[]{uid}, new RowMapper<UserRecInfo>() {
	
						@Override
						public UserRecInfo mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return new UserRecInfo(
									rs.getInt("id"),
									rs.getInt("platform_code"),
									rs.getString("province"),
									rs.getString("city"),
									rs.getString("user_label"),
									rs.getInt("concern_count"));
						}
			});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateLoginCodeByUID(Integer id, String loginCode) {
		getMasterJdbcTemplate().update(UPDATE_LOGIN_CODE_BY_UID, 
				new Object[]{loginCode, id});
	}

	@Override
	public UserInfoDto queryUserInfoDtoById(Integer id) {
		return getJdbcTemplate().queryForObject(QUERY_USER_INFO_DTO_BY_ID,
				new Object[]{id}, new RowMapper<UserInfoDto>() {

					@Override
					public UserInfoDto mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return buildUserInfoDtoByResult(rs.getInt("id"), rs);
					}
		});
	}

	@Override
	public UserAvatarLite queryUserAvatarLite(Integer id) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_USER_AVATAR_LITE, new Object[]{id},
					new RowMapper<UserAvatarLite>() {
	
						@Override
						public UserAvatarLite mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return new UserAvatarLite(
									rs.getInt("id"),
									rs.getString("user_name"),
									rs.getString("user_avatar"));
						}
			});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Integer> queryUID() {
		return getJdbcTemplate().query("select id from user_info", new RowMapper<Integer>(){

			@Override
			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getInt("id");
			}
		});
	}

	@Override
	public void updateShortLink(Integer uid, String shortLink) {
		getMasterJdbcTemplate().update("update user_info set short_link=? where id=?",
				new Object[]{shortLink, uid});
	}

}
