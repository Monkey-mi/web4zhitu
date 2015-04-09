package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserMsg;
import com.hts.web.common.pojo.UserMsgDto;
import com.hts.web.common.pojo.UserMsgIndex;
import com.hts.web.common.pojo.UserMsgRecipientDto;
import com.hts.web.userinfo.dao.UserMsgDao;

/**
 * <p>
 * 私信数据访问对象
 * </p>
 * 
 * 创建时间：2013-10-29
 * 
 * @author ztj
 * 
 */
@Repository("HTSUserMsgDao")
public class UserMsgDaoImpl extends BaseDaoImpl implements UserMsgDao {

	
	public static String table = HTS.USER_MSG;
	
	private static final String SAVE_MSG = "insert into " + table + 
			" (id,msg_date,content,obj_type,obj_id,obj_meta,thumb_path) values (?,?,?,?,?,?,?)";
	
	private static final String US_UR_INFO = "us.user_name us_user_name, us.user_avatar us_user_avatar,"
			+ "us.user_avatar_l us_user_avatar_l,us.sex us_sex,us.email us_email,"
			+ "us.address us_address,us.province us_province, us.city us_city, us.birthday us_birthday,"
			+ "us.signature us_signature,us.register_date us_register_date,us.user_label us_user_label,"
			+ "us.`star` us_star,us.phone_code as us_phone_code, us.`online` us_online, us.platform_verify us_platform_verify,"
			+ "ur.user_name ur_user_name, ur.user_avatar ur_user_avatar,"
			+ "ur.user_avatar_l ur_user_avatar_l,ur.sex ur_sex,ur.email ur_email,"
			+ "ur.address ur_address,ur.province ur_province, ur.city ur_city,"
			+ "ur.birthday ur_birthday,ur.signature ur_signature,ur.register_date ur_register_date, "
			+ "ur.user_label ur_user_label,ur.`star` ur_star, ur.phone_code as ur_phone_code, ur.`online` ur_online,"
			+ "ur.platform_verify ur_platform_verify";

	
	/** 查询关注好友和我的对话索引 */
	private static final String QUERY_CONCERN_MSG_INDEX = "select um.*," + U0_INFO + ",umb.*, SUM(umb.ck) as unread_count from ("
			+ "select * from (select ums0.sender_id as user_id, ums0.recipient_id as other_id, ums0.content_id, ums0.ck from " + HTS.USER_MSG_SEND_BOX + " as ums0"
			+ " where ums0.sender_id=? and ums0.valid=1 and ums0.chat_valid=1"
			+ " UNION ALL"
			+ " select umr0.recipient_id as user_id, umr0.sender_id as other_id, umr0.content_id, umr0.ck from " + HTS.USER_MSG_RECIPIENT_BOX + " as umr0,"
			+ " user_concern uc0 where umr0.sender_id=uc0.concern_id and uc0.valid=1 and uc0.user_id=? and umr0.recipient_id=? and umr0.valid=1 and umr0.chat_valid=1) as o order by o.content_id desc"
			+ " ) as umb, user_msg as um, " + HTS.USER_INFO + " as u0 where umb.content_id=um.id and umb.other_id=u0.id"
			+ " group by umb.user_id, umb.other_id ORDER BY umb.content_id DESC";
	
	/** 根据最大id查询关注好友和我的对话索引 */
	private static final String QUERY_CONCERN_MSG_INDEX_BY_MAX_ID = "select um.*," + U0_INFO + ",umb.*, SUM(umb.ck) as unread_count from ("
			+ "select * from (select ums0.sender_id as user_id, ums0.recipient_id as other_id, ums0.content_id, ums0.ck from " + HTS.USER_MSG_SEND_BOX + " as ums0"
			+ " where ums0.sender_id=? and ums0.valid=1 and ums0.chat_valid=1 and ums0.content_id<=?"
			+ " UNION ALL"
			+ " select umr0.recipient_id as user_id, umr0.sender_id as other_id, umr0.content_id, umr0.ck from " + HTS.USER_MSG_RECIPIENT_BOX + " as umr0,"
			+ " user_concern uc0 where umr0.sender_id=uc0.concern_id and uc0.valid=1 and uc0.user_id=? and umr0.recipient_id=? and umr0.valid=1 and umr0.chat_valid=1 and umr0.content_id<=?) as o order by o.content_id desc"
			+ " ) as umb, " + table + " as um,  " + HTS.USER_INFO + " as u0 where umb.content_id=um.id and umb.other_id=u0.id"
			+ " group by umb.user_id, umb.other_id ORDER BY umb.content_id DESC";
	
	/** 查询关注好友和我的对话索引总数 */
	private static final String QUERY_CONCERN_MSG_INDEX_COUNT_BY_MAX_ID = "select count(*),SUM(unread_count) as unread_total from"
			+ " (SELECT *, SUM(umb.ck) as unread_count from (select ums0.sender_id as user_id, ums0.recipient_id as other_id, ums0.content_id, ums0.ck from " + HTS.USER_MSG_SEND_BOX + " as ums0"
			+ " where ums0.sender_id=? and ums0.valid=1 and ums0.chat_valid=1 and ums0.content_id<=?"
			+ " UNION ALL"
			+ " select umr0.recipient_id as user_id, umr0.sender_id as other_id, umr0.content_id, SUM(umr0.ck) as unread_count from " + HTS.USER_MSG_RECIPIENT_BOX + " as umr0, user_concern uc0"
			+ " where umr0.sender_id=uc0.concern_id and uc0.valid=1 and uc0.user_id=? and umr0.recipient_id=? and umr0.valid=1 and umr0.chat_valid=1 and umr0.content_id<=?) as umb"
			+ " group by umb.user_id, umb.other_id) as o";
	
	
	/** 查询非关注好友和我的对话索引 */
	private static final String QUERY_UNCONCERN_MSG_INDEX = "select um.*,"+ U0_INFO+ ", umr.recipient_id as user_id, umr.sender_id as other_id, SUM(umr.ck) as unread_count from" 
			+ " (select * from " + HTS.USER_MSG_RECIPIENT_BOX + ""
			+ " where recipient_id=? and valid=1 and chat_valid=1 and sender_id NOT IN" 
			+ " (SELECT recipient_id from " + HTS.USER_MSG_SEND_BOX + "  where sender_id=? and valid=1 and chat_valid=1 group by recipient_id" 
			+ " UNION ALL SELECT concern_id from user_concern where user_id=? and valid=1) ORDER BY content_id DESC)"
			+ " as umr, user_msg as um, " + HTS.USER_INFO + " as u0 where umr.sender_id=u0.id and umr.content_id=um.id" 
			+ " GROUP BY umr.sender_id ORDER BY umr.content_id DESC";
	
	/** 根据最大id查询非关注好友和我的对话索引*/
	private static final String QUERY_UNCONCERN_MSG_INDEX_BY_MAX_ID = "select um.*,"+ U0_INFO+ ", umr.recipient_id as user_id, umr.sender_id as other_id, SUM(umr.ck) as unread_count from" 
			+ " (select * from " + HTS.USER_MSG_RECIPIENT_BOX + ""
			+ " where recipient_id=? and valid=1 and chat_valid=1 and content_id<=? and sender_id NOT IN" 
			+ " (SELECT recipient_id from " + HTS.USER_MSG_SEND_BOX + "  where sender_id=? and valid=1 and chat_valid=1 and content_id<=? group by recipient_id" 
			+ " UNION ALL SELECT concern_id from user_concern where user_id=? and valid=1) ORDER BY content_id DESC)"
			+ " as umr,  " + table + "  as um, " + HTS.USER_INFO + " as u0 where umr.sender_id=u0.id and umr.content_id=um.id" 
			+ " GROUP BY umr.sender_id ORDER BY umr.content_id DESC";
	
	/** 根据最大id查询非关注好友和我的对话索引总数 */
	private static final String QUERY_UNCONCERN_MSG_INDEX_COUNT_BY_MAX_ID = "select count(*) from ("
			+ " select umr.sender_id from" 
			+ " (select * from " + HTS.USER_MSG_RECIPIENT_BOX + ""
			+ " where recipient_id=? and valid=1 and chat_valid=1 and content_id<=? and sender_id NOT IN" 
			+ " (SELECT recipient_id from " + HTS.USER_MSG_SEND_BOX + "  where sender_id=? and valid=1 and chat_valid=1 and content_id<=? group by recipient_id" 
			+ " UNION ALL SELECT concern_id from user_concern where user_id=? and valid=1))"
			+ " as umr,  " + table + "  as um, " + HTS.USER_INFO + " as u0 where umr.sender_id=u0.id and umr.content_id=um.id" 
			+ " GROUP BY umr.sender_id) as o";
	
	/** 查询未读消息总数 */
	private static final String QUERY_UNREAD_COUNT = "select count(*) from " + HTS.USER_MSG_RECIPIENT_BOX 
			+ " where valid=1 and chat_valid=1 and ck=1 and recipient_id=? and content_id<=?";
	
	/**
	 * 查询最大消息id
	 */
	private static final String QUERY_MAX_MSG_ID = "select max(id) from " + table;
	
	/** 查询和指定用户私信列表 */
	private static final String QUERY_USER_MSG = "select umb.sender_id,umb.recipient_id, um.*," + US_UR_INFO + " from"
		+ " (select * from " + HTS.USER_MSG_SEND_BOX + " where sender_id=? and recipient_id=? and valid=1"
		+ " UNION ALL" 
		+ " select * from " + HTS.USER_MSG_RECIPIENT_BOX + " where sender_id=? and recipient_id=? and valid=1)"
		+ " as umb,  " + table + "  as um, " + HTS.USER_INFO + " as us, " + HTS.USER_INFO + " as ur"
		+ " where umb.content_id=um.id and us.id=umb.sender_id and ur.id=umb.recipient_id"
		+ " ORDER BY um.id DESC";
	
	/** 根据最大id查询和指定用户的私信列表 */
	private static final String QUERY_USER_MSG_BY_MAX_ID = "select umb.sender_id,umb.recipient_id, um.*," + US_UR_INFO + " from"
			+ " (select * from " + HTS.USER_MSG_SEND_BOX + " where sender_id=? and recipient_id=? and valid=1 and content_id<=?"
			+ " UNION ALL" 
			+ " select * from " + HTS.USER_MSG_RECIPIENT_BOX + " where sender_id=? and recipient_id=? and valid=1 and content_id<=?)"
			+ " as umb,  " + table + "  as um, " + HTS.USER_INFO + " as us, " + HTS.USER_INFO + " as ur"
			+ " where umb.content_id=um.id and us.id=umb.sender_id and ur.id=umb.recipient_id"
			+ " ORDER BY um.id DESC";
	
	/** 根据最大id查询和指定用户的私信总数 */
	private static final String QUERY_USER_MSG_COUNT_BY_MAX_ID = "select count(*) from"
			+ " (select * from " + HTS.USER_MSG_SEND_BOX + " where sender_id=? and recipient_id=? and valid=1 and content_id<=?"
			+ " UNION ALL" 
			+ " select * from " + HTS.USER_MSG_RECIPIENT_BOX + " where sender_id=? and recipient_id=? and valid=1 and content_id<=?)"
			+ " as umb,  " + table + "  as um where umb.content_id=um.id";
	
	/** 查询收件箱消息 */
	private static final String QUERY_RECIPIENT_MSG_BY_MIN_ID = "select um.*," + U0_INFO + ",umr.sender_id,umr.recipient_id,umr.ck from " 
			+ HTS.USER_MSG_RECIPIENT_BOX + " as umr," + HTS.USER_INFO + " as u0," + table + " as um"
			+ " where umr.sender_id=? and umr.recipient_id=? and umr.content_id>? and umr.sender_id=u0.id and umr.content_id=um.id";
	
	/**
	 * 查询发送者id
	 */
	private static final String QUERY_SENDER_ID = "select DISTINCT mr.sender_id from " 
			+ HTS.USER_MSG_RECIPIENT_BOX + " as mr, " + HTS.USER_MSG + " as m "
			+ " where mr.content_id=m.id and mr.sender_id=? and mr.recipient_id=? and m.obj_type=?";
			
	
	@Override
	public void saveMsg(UserMsg msg) {
		getMasterJdbcTemplate().update(SAVE_MSG, new Object[]{
				msg.getId(),
				msg.getMsgDate(),
				msg.getContent(),
				msg.getObjType(),
				msg.getObjId(),
				msg.getObjMeta(),
				msg.getThumbPath()
		});
	}
	
	@Override
	public List<UserMsgIndex> queryConcernMsgIndex(Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_CONCERN_MSG_INDEX, new Object[]{userId,userId,userId}, new RowMapper<UserMsgIndex>() {

			@Override
			public UserMsgIndex mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildMsgIndexByRs(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<UserMsgIndex> queryConcernMsgIndex(Integer maxId,
			Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_CONCERN_MSG_INDEX_BY_MAX_ID, new Object[]{userId,maxId,userId,userId,maxId}, 
				new RowMapper<UserMsgIndex>() {

			@Override
			public UserMsgIndex mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildMsgIndexByRs(rs);
			}
		}, rowSelection);
	}
	
	
	@Override
	public Long[] queryConcernMsgIndexCount(Integer maxId, Integer userId) {
		return getJdbcTemplate().queryForObject(QUERY_CONCERN_MSG_INDEX_COUNT_BY_MAX_ID, 
				new Object[]{userId, maxId, userId,userId, maxId}, new RowMapper<Long[]>(){

					@Override
					public Long[] mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Long[] l = new Long[2];
						l[0] = rs.getLong("count(*)");
						l[1] = rs.getLong("unread_total");
						return l;
					}
					
				});
	}
	
	@Override
	public List<UserMsgIndex> queryUnConcernMsgIndex(Integer userId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_UNCONCERN_MSG_INDEX, new Object[]{userId,userId,userId}, new RowMapper<UserMsgIndex>() {

			@Override
			public UserMsgIndex mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildMsgIndexByRs(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<UserMsgIndex> queryUnConcernMsgIndex(Integer maxId,
			Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_UNCONCERN_MSG_INDEX_BY_MAX_ID, new Object[]{userId, maxId, userId, maxId, userId}, new RowMapper<UserMsgIndex>() {

			@Override
			public UserMsgIndex mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildMsgIndexByRs(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public long queryUnConcernMsgIndexCount(Integer maxId, Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_UNCONCERN_MSG_INDEX_COUNT_BY_MAX_ID,
				new Object[]{userId, maxId, userId, maxId, userId});
	}
	
	@Override
	public long queryUnReadCount(Integer maxId, Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_UNREAD_COUNT, new Object[]{userId, maxId});
	}
	
	@Override
	public Integer queryMaxMsgId() {
		return getJdbcTemplate().queryForInt(QUERY_MAX_MSG_ID);
	}

	@Override
	public List<UserMsgDto> queryUserMsg(Integer userId, Integer otherId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_USER_MSG, new Object[]{userId, otherId, otherId, userId}, new RowMapper<UserMsgDto>(){

			@Override
			public UserMsgDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserMessageDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public List<UserMsgDto> queryUserMsg(Integer maxId, Integer userId,
			Integer otherId, RowSelection rowSelection) {
		return queryForPage(QUERY_USER_MSG_BY_MAX_ID, new Object[]{userId, otherId, maxId, otherId, userId, maxId},
				new RowMapper<UserMsgDto>(){

			@Override
			public UserMsgDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserMessageDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}

	@Override
	public long queryUserMsgCount(Integer maxId, Integer userId,
			Integer otherId) {
		return getJdbcTemplate().queryForLong(QUERY_USER_MSG_COUNT_BY_MAX_ID, 
				new Object[]{userId, otherId, maxId, otherId, userId, maxId});
	}
	
	@Override
	public List<UserMsgRecipientDto> queryRecipientMsg(Integer minId,
			Integer senderId, Integer recipientId) {
		return getJdbcTemplate().query(QUERY_RECIPIENT_MSG_BY_MIN_ID, new Object[]{senderId, recipientId, minId},
				new RowMapper<UserMsgRecipientDto>() {

			@Override
			public UserMsgRecipientDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildRecipientMsgByRs(rs);
			}
			
		});
	}
	
	
	/**
	 * 从结果集构建UserMessageDto
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UserMsgDto buildUserMessageDtoByResultSet(ResultSet rs) throws SQLException {
		Integer senderId = rs.getInt("sender_id");
		Integer recipientId = rs.getInt("recipient_id");
				
		UserMsgDto dto =  new UserMsgDto(
				rs.getInt("id"),
				senderId, 
				recipientId,
				(Date)rs.getObject("msg_date"), 
				rs.getString("content"),
				rs.getInt("obj_type"),
				rs.getInt("obj_id"), 
				rs.getString("obj_meta"),
				rs.getString("thumb_path"));
		
		//发送者信息
		Object senderBirthdayObj = rs.getObject("us_birthday");
		Date senderBirthday = null;
		if(senderBirthdayObj != null) {
			senderBirthday = (Date)senderBirthdayObj;
		}
		UserInfoDto senderInfo = new UserInfoDto(
				senderId,
				rs.getString("us_user_name"), 
				rs.getString("us_user_avatar"), 
				rs.getString("us_user_avatar_l"), 
				rs.getInt("us_sex"), 
				rs.getString("us_email"),
				rs.getString("us_address"),
				rs.getString("us_province"),
				rs.getString("us_city"),
				senderBirthday,
				rs.getString("us_signature"),
				(Date)rs.getObject("us_register_date"), 
				rs.getString("us_user_label"),
				rs.getInt("us_star"),
				rs.getInt("us_phone_code"),
				rs.getInt("us_online"),
				rs.getInt("us_platform_verify"));
		
		//接收者信息
		Object recipientBirthdayObj = rs.getObject("ur_birthday");
		Date recipientBirthday = null;
		if(recipientBirthdayObj != null) {
			recipientBirthday = (Date)recipientBirthdayObj;
		}
		UserInfoDto recipientInfo = new UserInfoDto(
				recipientId,
				rs.getString("ur_user_name"), 
				rs.getString("ur_user_avatar"), 
				rs.getString("ur_user_avatar_l"), 
				rs.getInt("ur_sex"), 
				rs.getString("ur_email"),
				rs.getString("ur_address"),
				rs.getString("ur_province"),
				rs.getString("ur_city"),
				recipientBirthday,
				rs.getString("ur_signature"),
				(Date)rs.getObject("ur_register_date"), 
				rs.getString("ur_user_label"),
				rs.getInt("ur_star"),
				rs.getInt("ur_phone_code"),
				rs.getInt("ur_online"),
				rs.getInt("ur_platform_verify"));
		
		dto.setSenderInfo(senderInfo);
		dto.setRecipientInfo(recipientInfo);
		return dto;
	}
	
	/**
	 * 根据结果集构建对话索引
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public UserMsgIndex buildMsgIndexByRs(ResultSet rs) throws SQLException {
		//接收者信息
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		UserInfoDto otherInfo = new UserInfoDto(
				rs.getInt("other_id"),
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
		return new UserMsgIndex(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("other_id"),
				(Date)rs.getObject("msg_date"),
				rs.getString("content"),
				rs.getInt("obj_type"),
				rs.getString("obj_meta"),
				rs.getInt("obj_id"),
				rs.getString("thumb_path"),
				otherInfo,
				rs.getInt("unread_count"));
	}
	
	public UserMsgRecipientDto buildRecipientMsgByRs(ResultSet rs) throws SQLException {
		//接收者信息
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		UserInfoDto otherInfo = new UserInfoDto(
				rs.getInt("sender_id"),
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
		return new UserMsgRecipientDto(
				rs.getInt("id"),
				rs.getInt("sender_id"),
				rs.getInt("recipient_id"),
				(Date)rs.getObject("msg_date"),
				rs.getString("content"),
				rs.getInt("obj_type"),
				rs.getString("obj_meta"),
				rs.getInt("obj_id"),
				rs.getString("thumb_path"),
				rs.getInt("ck"),
				otherInfo);
	}

	@Override
	public Integer querySenderId(Integer senderId, Integer recipientId,
			Integer msgType) {
		return queryForObjectWithNULL(QUERY_SENDER_ID, new Object[]{senderId, recipientId, msgType}, 
				new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("sender_id");
			}
		});
	}

}
