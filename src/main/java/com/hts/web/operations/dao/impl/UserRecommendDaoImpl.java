package com.hts.web.operations.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.OpUser;
import com.hts.web.common.pojo.OpUserLabelRecommend;
import com.hts.web.common.pojo.OpUserRecommend;
import com.hts.web.common.pojo.UserDynamicRec;
import com.hts.web.common.pojo.UserVerifyDto;
import com.hts.web.operations.dao.UserRecommendDao;
import com.hts.web.userinfo.dao.UserVerifyDao;

@Repository("HTSUserRecommendDao")
public class UserRecommendDaoImpl extends BaseDaoImpl implements
		UserRecommendDao {
	
	private static String table = HTS.OPERATIONS_USER_RECOMMEND;
	
	private static final String ORDER_BY_UR_ID_DESC = " order by ur.id desc";
	
	/**
	 * 推荐用户信息 
	 */
	private static final String U_INFO = "u.id as user_id,u.platform_code,u.platform_sign, "
			+ "u.platform_verify,u.platform_reason,u.user_name,u.user_avatar,u.user_avatar_l,u.sex,u.email," 
			+ "u.address,u.province,u.city,u.birthday,u.signature,u.user_label,"
			+ "u.register_date,u.online,u.concern_count,u.follow_count," 
			+ "u.world_count,u.liked_count,u.keep_count,u.shield,u.star,u.trust,u.activity";

	/**
	 * 标签推荐用户信息 
	 */
	private static final String U_LABEL_INFO = "u.id as user_id,u.platform_sign,u.platform_verify,"
			+ "u.platform_reason,u.user_name,u.user_avatar,u.user_avatar_l,"
			+ "u.address,u.province,u.city, u.signature,u.user_label,u.star,u.trust,u.activity";
	
	/**
	 * 信息流推荐的用户信息格式
	 */
	public static final String WORLD_RECOMMEND_INFO = "u0.id,u0.user_name,u0.user_avatar,"
			+ "u0.user_avatar,u0.user_avatar_l,u0.star";
	
	/**
	 * 保存推荐用户
	 */
	private static final String SAVE_RECOMMEND_USER = "insert into " + table
			+ " (id,user_id,verify_id,recommend_desc,recommender_id,date_added,date_modified,user_accept,sys_accept)"
			+ " values (?,?,?,?,?,?,?,?,?)";
	
	
	private static final String QUERY_RECOMMEND_USER = "select ur.*," + U_INFO + " from " 
			+ HTS.USER_INFO + " as u," + HTS.OPERATIONS_USER_RECOMMEND + " as ur"
			+ " where u.shield=? and ur.user_accept=1 and ur.sys_accept=1 and u.id=ur.user_id"
			+ " and ur.user_id not in"
			+ " (select concern_id from " + HTS.USER_CONCERN + " where valid=? and user_id=?)"
			+ " and ur.user_id!=?" + ORDER_BY_UR_ID_DESC;
	
	
	/** 
	 * 根据最大id查询有效推荐用户 
	 */
	private static final String QUERY_RECOMMEND_USER_BY_MAX_ID = "select ur.*," + U_INFO + " from " 
			+ HTS.USER_INFO + " as u," + HTS.OPERATIONS_USER_RECOMMEND + " as ur"
			+ " where u.shield=? and ur.user_accept=1 and ur.sys_accept=1 and u.id=ur.user_id"
			+ " and ur.user_id not in (select concern_id from "
			+ HTS.USER_CONCERN + " where valid=? and user_id=?) and ur.user_id!=? and ur.id<=?"
			+ ORDER_BY_UR_ID_DESC;
	
	
	/** 
	 * 查询推荐用户，根据activity排序
	 */
	private static final String QUERY_RECOMMEND_USER_ORDER_BY_ACT = "select u3.*,if(u3.fix_pos = 0 ,@rownum1 :=@rownum1 + 1,u3.fix_pos) as rownum1 from (select u2.*,@rownum1:=0 from"
			+ " (SELECT u1.*,@rownum:=@rownum+1 as rownum"
			+ " from (SELECT @rownum:=0,ur0.id,ur0.recommend_desc,ur0.date_modified,ur0.last_pos,ur0.last_verify_pos,ur0.fix_pos,"
			+ U_INFO+" from " + HTS.OPERATIONS_USER_RECOMMEND + " as ur0, " 
			+ HTS.USER_INFO + " as u"
			+ " where ur0.user_id=u.id and ur0.user_accept=1 and ur0.sys_accept=1 ORDER BY u.activity desc, ur0.id desc) u1) as u2"
			+ " where u2.user_id!=?  and NOT EXISTS"
			+ " (select concern_id from " + HTS.USER_CONCERN + " where u2.user_id=concern_id and valid=1 and user_id=?)) as u3 order by rownum1 asc, u3.fix_pos desc";

	
	/**
	 * 查询推荐用户，根据activity排序
	 */
	private static final String QUERY_RECOMMEND_USER_ORDER_BY_ACT_BY_MAX_ID = "select u3.*,if(u3.fix_pos = 0 ,@rownum1 :=@rownum1 + 1,u3.fix_pos) as rownum1 from (select u2.*,@rownum1:=0 from"
			+ " (SELECT u1.*,@rownum:=@rownum+1 as rownum"
			+ " from (SELECT @rownum:=0,ur0.id,ur0.recommend_desc,ur0.date_modified,ur0.last_pos,ur0.last_verify_pos,ur0.fix_pos,"
			+ U_INFO+" from " + HTS.OPERATIONS_USER_RECOMMEND + " as ur0, " 
			+ HTS.USER_INFO + " as u"
			+ " where ur0.user_id=u.id and ur0.user_accept=1 and ur0.sys_accept=1 ORDER BY u.activity desc, ur0.id desc) u1) as u2"
			+ " where u2.user_id!=?  and NOT EXISTS"
			+ " (select concern_id from " + HTS.USER_CONCERN + " where u2.user_id=concern_id and valid=1 and user_id=?) and u2.activity<=? ) as u3 order by rownum1 asc, u3.fix_pos desc";
	
	/** 
	 * 查询分榜推荐用户，根据activity排序
	 */
	private static final String QUERY_VERIFY_RECOMMEND_USER_ORDER_BY_ACT = " SELECT u1.*,@rownum:=@rownum+1 as rownum"
			+ " from (SELECT @rownum:=0,ur0.id,ur0.recommend_desc,ur0.date_modified,ur0.last_pos,ur0.last_verify_pos,ur0.fix_pos,"
			+ U_INFO + " from "
			+ "(select * from " + HTS.OPERATIONS_USER_RECOMMEND + " ur1 where ur1.user_accept=1 and ur1.sys_accept=1 and ur1.verify_id=?"
			+ " and NOT EXISTS (select concern_id from " + HTS.USER_CONCERN 
			+ " where ur1.user_id=concern_id and valid=1 and user_id=?)) as ur0, " 
			+ HTS.USER_INFO + " as u"
			+ " where ur0.user_id=u.id ORDER BY ur0.weight desc, u.activity desc, ur0.id desc) u1";

	
	/**
	 * 查询分榜推荐用户，根据activity排序
	 */
	private static final String QUERY_VERIFY_RECOMMEND_USER_ORDER_BY_ACT_MAX_ID = " SELECT u1.*,@rownum:=@rownum+1 as rownum"
			+ " from (SELECT @rownum:=0,ur0.id,ur0.recommend_desc,ur0.date_modified,ur0.last_pos,ur0.last_verify_pos,ur0.fix_pos,"
			+ U_INFO + " from "
			+ "(select * from " + HTS.OPERATIONS_USER_RECOMMEND + " ur1 where ur1.user_accept=1 and ur1.sys_accept=1 and ur1.verify_id=?"
			+ " and NOT EXISTS (select concern_id from " + HTS.USER_CONCERN 
			+ " where ur1.user_id=concern_id and valid=1 and user_id=?)) as ur0, " 
			+ HTS.USER_INFO + " as u"
			+ " where ur0.user_id=u.id and u.activity<=? ORDER BY ur0.weight desc, u.activity desc, ur0.id desc) u1";
	
	/** 
	 * 查询社交平台推荐用户SQL头部 
	 */
	private static final String QUERY_PLATFORM_RECOMMEND_USER_HEAD = "select " + U_INFO + " from " 
			+ HTS.USER_INFO + " as u where "
			+ " u.shield=0 and u.login_code in (";
			
	
	/** 
	 * 查询社交平台推荐用户SQL尾部 
	 */
	private static final String QUERY_PLATFORM_RECOMMEND_USER_FOOT = 
		") and u.id NOT IN (select concern_id from user_concern as uc where uc.valid=1 and uc.user_id=?)";;
	
	/** 
	 * 根据UID查询推荐用户 
	 */
	private static final String QUERY_OP_USER_BY_UID = "select '1000000' as id,'2' as last_pos,'2' as last_verify_pos, '0' as fix_pos, u.register_date as date_modified,'' as recommend_desc," 
			+ U_INFO + " from " 
			+ HTS.USER_INFO + " as u where u.id=? and u.star=1";
	
	/** 
	 * 根据UID查询标签推荐用户 
	 */
	private static final String QUERY_LABEL_RECOMMEND_USER_BY_UID = "select ur.id, ur.recommend_desc," + U_LABEL_INFO + " from " 
			+ HTS.USER_INFO + " as u," + HTS.OPERATIONS_USER_RECOMMEND + " as ur" 
			+ " where u.shield=? and ur.user_accept=1 and ur.sys_accept=1 and ur.weight=0 and u.id=ur.user_id and u.id=?";
	
	/** 
	 * 查询标签推荐用户SQL公用头部 
	 */
	private static final String QUERY_LABEL_RECOMMEND_USER_HEAD = "select ur.id, ur.recommend_desc," + U_LABEL_INFO + " from " 
			+ HTS.USER_INFO + " as u," + HTS.OPERATIONS_USER_RECOMMEND + " as ur, (select label_id from " 
			+ HTS.USER_INFO_LABEL + " where user_id=?) as ul0," + HTS.USER_INFO_LABEL + " as ul"
			+ " where ul0.label_id=ul.label_id and ul.user_id=u.id and u.id=ur.user_id and u.shield=?"
			+ " and ur.user_accept=1 and ur.sys_accept=1 and ur.weight=0 and ur.user_id not in"
			+ " (select concern_id from " + HTS.USER_CONCERN + " where valid=? and user_id=?)"
			+ " and ur.user_id!=? ";
	
	/** 
	 * 查询标签推荐用户 
	 */
	private static final String QUERY_LABEL_RECOMMEND_USER = QUERY_LABEL_RECOMMEND_USER_HEAD 
			+ " group by u.id" + ORDER_BY_UR_ID_DESC;
	
	/** 
	 * 根据最大id查询标签推荐用户
	 */
	private static final String QUERY_LABEL_RECOMMEND_USER_BY_MAX_ID = QUERY_LABEL_RECOMMEND_USER_HEAD 
			+ " and ur.id<=? group by u.id" + ORDER_BY_UR_ID_DESC;
		
	/**
	 * 根据用户id查询推荐信息
	 */
	private static final String QUERY_RECOMMEND_USER_BY_UID = "select * from " + table
			+ " where user_id=?";
	
	/**
	 * 更新用户允许标记
	 */
	private static final String UPDATE_USER_ACCEPT_BY_UID = "update " + table 
			+ " set user_accept=?,date_modified=? where user_id=?";
	
	/**
	 * 更新系统允许标记
	 */
	private static final String UPDATE_SYS_ACCEPT_BY_UID = "update " + table 
			+ " set sys_accept=?,date_modified=? where user_id=?";
	
	/**
	 * 更新允许标记
	 */
	private static final String UPDATE_ACCEPT_BY_UID = "update " + table 
			+ " set user_accept=?,sys_accept=?,date_modified=? where user_id=?";
	
	/**
	 * 查询系统接受但用户未接受的推荐
	 */
	private static final String QUERY_USER_UNACCEPTED_RECOMMEND = "select * from " + table
			+ " where user_id=? and user_accept=0 and sys_accept=1";
	
	/**
	 * 根据最小活跃度查询推荐总数
	 */
	private static final String QUERY_RECOMMEND_COUNT_BY_MIN_ACT = "select count(*) from " + table
			+ " ur, " + HTS.USER_INFO + " u where ur.user_id=u.id and ur.sys_accept=1 and ur.user_accept=1 "
			+ " and u.activity>=?";

	/**
	 * 查询验证用户索引SQL头部
	 */
	private static final String QUERY_VERIFY_INDEX_HEAD = "(select u0.id," + U0_INFO 
		+ ",ur0.verify_id from " + HTS.USER_INFO + " as u0, " + table + " as ur0"
		+ " where u0.id=ur0.user_id and ur0.user_accept=1 and ur0.sys_accept=1 and ur0.verify_id=?"
		+ " and NOT EXISTS"
		+ " (select concern_id from " + HTS.USER_CONCERN + " where u0.id=concern_id and valid=1 and user_id=?)"
		+ " order by ur0.weight desc, u0.activity desc, ur0.id desc limit ?)";
	
	/**
	 * 查询标签推荐置顶用户
	 */
	private static final String QUERY_WEIGHT_LABEL_RECOMMEND = "select ur.id, ur.recommend_desc," + U_LABEL_INFO + " from " 
			+ table + " ur, " + HTS.USER_INFO + " u where u.id=ur.user_id and ur.weight>0 order by ur.weight desc";
	
	
//	/**
//	 * 查询信息流推荐的用户信息
//	 */
//	private static final String QUERY_WORLD_RECOMMEND = "select " + WORLD_RECOMMEND_INFO + " from " 
//			+ HTS.OPERATIONS_USER_RECOMMEND + " as ur0, " + HTS.USER_INFO + " as u0"
//			+ " where ur0.user_id=u0.id and ur0.user_accept=1 and ur0.sys_accept=1 and ur0.user_id!=? and NOT EXISTS"
//			+ " (select concern_id from " + HTS.USER_CONCERN + " where u0.id=concern_id and valid=1 and user_id=?) "
//					+ "ORDER BY u0.activity desc, ur0.id desc limit ?,?";
	
	@Autowired
	private UserVerifyDao userVerifyDao;
	
	@Override
	public List<OpUser> queryRecommendUser(
			Integer joinId, RowSelection rowSelection) {
		return queryForPage(QUERY_RECOMMEND_USER,
				new Object[]{Tag.FALSE, Tag.TRUE, joinId, joinId}, new RowMapper<OpUser>() {

			@Override
			public OpUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpUser(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<OpUser> queryRecommendUserByMaxId(
			Integer joinId, Integer maxId, RowSelection rowSelection) {
		return queryForPage(QUERY_RECOMMEND_USER_BY_MAX_ID, 
				new Object[]{Tag.FALSE, Tag.TRUE, joinId, joinId, maxId}, new RowMapper<OpUser>() {

			@Override
			public OpUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpUser(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<OpUser> queryPlatformRecommendUser(
			String platformIds, Integer joinId) {
		String sql = new StringBuilder(QUERY_PLATFORM_RECOMMEND_USER_HEAD)
			.append(platformIds)
			.append(QUERY_PLATFORM_RECOMMEND_USER_FOOT)
			.toString();
		return getJdbcTemplate().query(sql, new Object[]{joinId}, new RowMapper<OpUser>() {

			@Override
			public OpUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildOpUserWithoutRecommendId(rs);
			}
			
		});
	}
	
	@Override
	public OpUser queryOpUserByUID(Integer uid) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_OP_USER_BY_UID, 
					new Object[]{uid}, new RowMapper<OpUser>() {

				@Override
				public OpUser mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildOpUser(rs);
				}
				
			});
		} catch(DataAccessException e) {
			return null;
		}
	}

	@Override
	public OpUserLabelRecommend queryLabelRecommendUserByUID(Integer uid) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_LABEL_RECOMMEND_USER_BY_UID, 
					new Object[]{Tag.FALSE, uid}, new RowMapper<OpUserLabelRecommend>() {

				@Override
				public OpUserLabelRecommend mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return buildLabelRecommendUser(rs);
				}
				
			});
		} catch (DataAccessException e) {
			return null;
		}
	}

	
	@Override
	public List<OpUserLabelRecommend> queryLabelRecommendUser(Integer joinId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_LABEL_RECOMMEND_USER, 
				new Object[]{joinId, Tag.FALSE, Tag.TRUE, joinId, joinId},
				new RowMapper<OpUserLabelRecommend>() {

					@Override
					public OpUserLabelRecommend mapRow(ResultSet rs, int num)
							throws SQLException {
						return buildLabelRecommendUser(rs);
					}
		}, rowSelection);
	}
	
	@Override
	public List<OpUserLabelRecommend> queryLabelRecommendUser(Integer maxId,
			Integer joinId, RowSelection rowSelection) {
		return queryForPage(QUERY_LABEL_RECOMMEND_USER_BY_MAX_ID, 
				new Object[]{joinId, Tag.FALSE, Tag.TRUE, joinId, joinId, maxId},
				new RowMapper<OpUserLabelRecommend>() {

					@Override
					public OpUserLabelRecommend mapRow(ResultSet rs, int num)
							throws SQLException {
						return buildLabelRecommendUser(rs);
					}
		}, rowSelection);
	}
	

	@Override
	public void saveRecommendUser(OpUserRecommend recommend) {
		getJdbcTemplate().update(SAVE_RECOMMEND_USER, new Object[]{
			recommend.getId(),
			recommend.getUserId(),
			recommend.getVerifyId(),
			recommend.getRecommendDesc(),
			recommend.getRecommenderId(),
			recommend.getDateAdded(),
			recommend.getDateModified(),
			recommend.getUserAccept(),
			recommend.getSysAccept()
		});
	}
	
	@Override
	public OpUserRecommend queryRecommendUserByUID(Integer userId) {
		return queryForObjectWithNULL(QUERY_RECOMMEND_USER_BY_UID, new Object[]{userId},
				new RowMapper<OpUserRecommend>() {

			@Override
			public OpUserRecommend mapRow(ResultSet rs, int num) throws SQLException {
				return buildUserRecommend(rs);
			}
		});
	}
	
	@Override
	public OpUserRecommend queryUserUnAcceptedRecommend(Integer userId) {
		return queryForObjectWithNULL(QUERY_USER_UNACCEPTED_RECOMMEND,
				new Object[]{userId}, new RowMapper<OpUserRecommend>() {

					@Override
					public OpUserRecommend mapRow(ResultSet rs, int num)
							throws SQLException {
						return buildUserRecommend(rs);
					}
		});
	}
	
	@Override
	public void updateUserAcceptByUID(Integer userId, Integer state, Date dateModified) {
		getJdbcTemplate().update(UPDATE_USER_ACCEPT_BY_UID, 
				new Object[]{state, dateModified, userId});
	}
	
	@Override
	public void updateSysAcceptByUID(Integer userId, Integer state,
			Date dateModified) {
		getJdbcTemplate().update(UPDATE_SYS_ACCEPT_BY_UID, 
				new Object[]{state, dateModified, userId});
	}

	@Override
	public void updateAcceptByUID(Integer userId, Integer userState,
			Integer sysState, Date dateModified) {
		getJdbcTemplate().update(UPDATE_ACCEPT_BY_UID, 
				new Object[]{userState, sysState, dateModified, userId});
	}
	
	@Override
	public List<OpUser> queryRecommendUserOrderByAct(Integer joinId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_RECOMMEND_USER_ORDER_BY_ACT,
				new Object[]{joinId, joinId}, new RowMapper<OpUser>() {

			@Override
			public OpUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OpUser user = buildOpUser(rs);
				user.setCurrPos(rs.getInt("rownum"));
				return user;
			}
		}, rowSelection);
	}

	@Override
	public List<OpUser> queryRecommendUserOrderByAct(Integer maxId,
			Integer joinId, RowSelection rowSelection) {
		return queryForPage(QUERY_RECOMMEND_USER_ORDER_BY_ACT_BY_MAX_ID,
				new Object[]{joinId, joinId, maxId}, new RowMapper<OpUser>() {

			@Override
			public OpUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OpUser user = buildOpUser(rs);
				user.setCurrPos(rs.getInt("rownum"));
				return user;
			}
		}, rowSelection);
	}
	
	@Override
	public List<OpUser> queryVerifyRecommendUserOrderByAct(Integer userId, Integer verifyId,
			RowSelection rowSelection) {
		return queryForPage(QUERY_VERIFY_RECOMMEND_USER_ORDER_BY_ACT,
				new Object[]{verifyId, userId}, new RowMapper<OpUser>() {

			@Override
			public OpUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OpUser user = buildOpUser(rs);
				user.setCurrPos(rs.getInt("rownum"));
				return user;
			}
		}, rowSelection);
	}

	@Override
	public List<OpUser> queryVerifyRecommendUserOrderByAct(Integer maxId, Integer userId, 
			Integer verifyId, RowSelection rowSelection) {
		return queryForPage(QUERY_VERIFY_RECOMMEND_USER_ORDER_BY_ACT_MAX_ID,
				new Object[]{verifyId, userId, maxId}, new RowMapper<OpUser>() {

			@Override
			public OpUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OpUser user = buildOpUser(rs);
				user.setCurrPos(rs.getInt("rownum"));
				return user;
			}
		}, rowSelection);
	}
	
	@Override
	public long queryRecommendCount(Integer activity) {
		return getJdbcTemplate().queryForLong(QUERY_RECOMMEND_COUNT_BY_MIN_ACT, activity);
	}
	
	@Override
	public void queryVerifyUser(Integer userId, Integer[] verifyIds, Integer limit,
			final RowCallback<UserVerifyDto> callback) {
		Object[] args = new Object[verifyIds.length * 3];
		StringBuilder sqlBuilder = new StringBuilder();
		for(int i = 0; i < verifyIds.length; i++) {
			if(i != 0) {
				sqlBuilder.append(" union all ");
			}
			sqlBuilder.append(QUERY_VERIFY_INDEX_HEAD);
			int k = i * 3;
			args[k] = verifyIds[i];
			args[k+1] = userId;
			args[k+2] = limit;
		}
		getJdbcTemplate().query(sqlBuilder.toString(), args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				UserVerifyDto user = userVerifyDao.buildVerifyDto(rs);
				callback.callback(user);
			}
		});
	}
	
	@Override
	public List<OpUserLabelRecommend> queryWeightLabelRecommendUser() {
		return getJdbcTemplate().query(QUERY_WEIGHT_LABEL_RECOMMEND, new RowMapper<OpUserLabelRecommend>() {

			@Override
			public OpUserLabelRecommend mapRow(ResultSet rs, int num)
					throws SQLException {
				return buildLabelRecommendUser(rs);
			}
		});
	}
	
	/**
	 * 构建推荐信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpUserRecommend buildUserRecommend(ResultSet rs) throws SQLException {
		return new OpUserRecommend(
				rs.getInt("id"), 
				rs.getInt("user_id"), 
				rs.getInt("verify_id"),
				rs.getString("recommend_desc"),
				rs.getInt("recommender_id"),
				(Date)rs.getObject("date_added"),
				(Date)rs.getObject("date_modified"),
				rs.getInt("user_accept"),
				rs.getInt("sys_accept"));
	}

	/**
	 * 从结果集构建推荐用户
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static OpUser buildOpUser(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new OpUser(
				rs.getInt("id"), 
				rs.getInt("user_id"),
				rs.getInt("platform_code"),
				rs.getString("platform_sign"),
				rs.getInt("platform_verify"),
				rs.getString("platform_reason"),
				(Date)rs.getObject("date_modified"),
				rs.getString("recommend_desc"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"), 
				rs.getInt("sex"), 
				rs.getString("email"),
				rs.getString("address"), 
				rs.getString("province"),
				rs.getString("city"),
				birthday,
				rs.getString("signature"),
				rs.getString("user_label"),
				(Date)rs.getObject("register_date"), 
				(Integer)rs.getInt("online"), 
				(Integer)rs.getInt("concern_count"), 
				(Integer)rs.getInt("follow_count"), 
				(Integer)rs.getInt("world_count"), 
				(Integer)rs.getInt("liked_count"), 
				(Integer)rs.getInt("keep_count"),
				(Integer)rs.getInt("shield"),
				rs.getInt("star"),
				rs.getInt("activity"),
				rs.getInt("last_pos"),
				rs.getInt("last_verify_pos"),
				rs.getInt("fix_pos"));
	}
	
	/**
	 * 从结果集构建运营用户数据，并且不包含推荐信息
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static OpUser buildOpUserWithoutRecommendId(ResultSet rs) throws SQLException {
		Object birthdayObj = rs.getObject("birthday");
		Date birthday = null;
		if(birthdayObj != null) {
			birthday = (Date)birthdayObj;
		}
		return new OpUser(
				rs.getInt("user_id"), 
				rs.getInt("platform_code"),
				rs.getString("platform_sign"),
				rs.getInt("platform_verify"),
				rs.getString("platform_reason"),
				rs.getString("user_name"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"), 
				rs.getInt("sex"), 
				rs.getString("email"),
				rs.getString("address"), 
				rs.getString("province"),
				rs.getString("city"),
				birthday,
				rs.getString("signature"),
				rs.getString("user_label"),
				(Date)rs.getObject("register_date"), 
				(Integer)rs.getInt("online"), 
				(Integer)rs.getInt("concern_count"), 
				(Integer)rs.getInt("follow_count"), 
				(Integer)rs.getInt("world_count"), 
				(Integer)rs.getInt("liked_count"), 
				(Integer)rs.getInt("keep_count"),
				(Integer)rs.getInt("shield"),
				rs.getInt("star"),
				rs.getInt("trust"),
				rs.getInt("activity"));
	}
	
	/**
	 * 构建OpLabelRecommendUser
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public OpUserLabelRecommend buildLabelRecommendUser(ResultSet rs) throws SQLException {
		return new OpUserLabelRecommend(
				rs.getInt("id"),
				rs.getString("recommend_desc"),
				rs.getInt("user_id"), 
				rs.getString("platform_sign"),
				rs.getInt("platform_verify"),
				rs.getString("platform_reason"),
				rs.getString("user_name"),
				rs.getString("user_avatar"), 
				rs.getString("user_avatar_l"),
				rs.getString("address"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getString("signature"),
				rs.getString("user_label"),
				rs.getInt("star"),
				rs.getInt("activity"));
	}

}
