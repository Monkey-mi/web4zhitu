package com.hts.web.userinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.HTS;
import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.base.database.SQLUtil;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.common.pojo.UserConcern;
import com.hts.web.common.pojo.UserConcernDto;
import com.hts.web.common.pojo.UserConcernName;
import com.hts.web.common.pojo.UserFollowDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserIsMututal;
import com.hts.web.userinfo.dao.UserConcernDao;

/**
 * <p>
 * 用户关注信息数据访问对象
 * </p>
 * 
 * 创建时间：2013-7-2
 * 
 * @author ztj
 * 
 */
@Repository("HTSUserConcernDao")
public class UserConcernDaoImpl extends BaseDaoImpl implements UserConcernDao{

	public static final String ORDER_BY_UC_ID_DESC = " order by uc.id desc";
	
	/**
	 * 关注用户的基本信息
	 */
	public static final String CONCERN_USER_INFO = "u.user_name,u.user_avatar,"
			+ "u.user_avatar_l,u.sex,u.user_label,u.address,u.province,u.city,"
			+ "u.signature,u.platform_sign,u.star,u.online,u.platform_verify";
	
	/**
	 * 关注人用户名信息
	 */
	private static final String CONCERN_NAME_INFO = "u0.id,u0.user_name";
	
	/**
	 * 表：用户关注表
	 */
	private static String table = HTS.USER_CONCERN;

	/**
	 * 查询用户关注
	 */
	private static final String QUERY_CONCERN_WITH_LOCK = "select * from " + table 
			+ " where user_id=? and concern_id=?";
	
	/**
	 * 保存用户关注
	 */
	private static final String SAVE_CONCERN = "insert into " + table 
			+ " (id, user_id, concern_id, is_mututal, concern_date, ck, valid) values (?,?,?,?,?,?,?)";
	
	/**
	 * 更新关注，但不更新CK字段
	 */
	private static final String UPDATE_CONCERN = "update " + table 
			+ " set is_mututal=?, concern_date=?, valid=? where user_id=? and concern_id=? and valid=?";
	
	/**
	 * 更新互相关注
	 */
	private static final String UPDATE_IS_MUTUTAL = "update " + table 
			+ " set is_mututal=?, concern_date=? where user_id=? and concern_id=?";
	
	/**
	 * 查询关注用户SQL头部
	 */
	private static final String QUERY_CONCERN_HEADER = "select uc.*," + CONCERN_USER_INFO + " from " 
			+ table + " as uc, " + HTS.USER_INFO + 
			" as u where uc.concern_id = u.id and uc.user_id=? and uc.valid=? and u.shield=?";
	
	/**
	 * 查询关注用户
	 */
	private static final String QUERY_CONCERN = QUERY_CONCERN_HEADER + ORDER_BY_UC_ID_DESC;
	
	/**
	 * 根据最大id查询关注用户
	 */
	private static final String QUERY_CONCERN_BY_MAX_ID = QUERY_CONCERN_HEADER 
			+ " and uc.id<=?" + ORDER_BY_UC_ID_DESC;
	
	/**
	 * 根据最小id查询关注用户
	 */
	private static final String QUERY_CONCERN_BY_MIN_ID = QUERY_CONCERN_HEADER 
			+ " and uc.id>?" + ORDER_BY_UC_ID_DESC;
	
	/**
	 * 查询关注用户总数SQL头部
	 */
	private static final String QUERY_CONCERN_COUNT_HEADER = "select count(*) from " 
			+ table + " as uc where uc.user_id=? and uc.valid=?";
	
	/**
	 * 根据最大id查询关注用户总数
	 */
	private static final String QUERY_CONCERN_COUNT_BY_MAX_ID = QUERY_CONCERN_COUNT_HEADER + " and uc.id<=?";
	
	/**
	 * 根据最小id查询关注用户总数
	 */
	private static final String QUERY_CONCERN_COUNT_BY_MIN_ID = QUERY_CONCERN_COUNT_HEADER + " and uc.id>?";
	
	/**
	 * 查询粉丝SQL头部
	 */
	private static final String QUERY_FOLLOW_HEADER = "select uc.*, " + CONCERN_USER_INFO + " from " 
			+ table + " as uc, " + HTS.USER_INFO + " as u where uc.user_id = u.id" 
			+ " and uc.concern_id=? and uc.valid=? and u.shield=?";
	
	/**
	 * 查询粉丝
	 */
	private static final String QUERY_FOLLOW = QUERY_FOLLOW_HEADER + ORDER_BY_UC_ID_DESC;
	
	/**
	 * 根据最大id查询粉丝
	 */
	private static final String QUERY_FOLLOW_BY_MAX_ID = QUERY_FOLLOW_HEADER + " and uc.id<=?" + ORDER_BY_UC_ID_DESC; 
	
	/**
	 * 根据最小id查询粉丝
	 */
	private static final String QUERY_FOLLOW_BY_MIN_ID = QUERY_FOLLOW_HEADER + " and uc.id>?" + ORDER_BY_UC_ID_DESC;
	
	/**
	 * 查询粉丝总数SQL头部
	 */
	private static final String QUERY_FOLLOW_COUNT_HEADER =  "select count(*) from " 
			+ table + " as uc where uc.concern_id=? and uc.valid=?";
	
	/**
	 * 根据最大id查询粉丝总数
	 */
	private static final String QUERY_FOLLOW_COUNT_BY_MAX_ID = QUERY_FOLLOW_COUNT_HEADER + " and uc.id<=?";
	
	/**
	 * 根据最小id查询粉丝总数
	 */
	private static final String QUERY_FOLLOW_COUNT_BY_MIN_ID = QUERY_FOLLOW_COUNT_HEADER + " and uc.id>?";
	
	/**
	 * 更新所有未查阅过的粉丝
	 */
	private static final String UPDATE_CONCERN_CK = "update " + table 
			+ " set ck=1 where concern_id=? and valid=1 and ck=0";
	
	/**
	 * 查询关注用户并查询与指定用户的关系
	 */
	private static final String QUERY_CONCERN_WITH_JOIN  = "select uc1.is_mututal,uc.* from (select " 
			+ CONCERN_USER_INFO + ",uc0.* from user_concern as uc0, user_info as u"
			+ " where uc0.concern_id = u.id and uc0.user_id=? and uc0.valid=1 and u.shield=0"
			+ " order by uc0.id desc limit ?,?) as uc"
			+ " left join user_concern as uc1 on uc.concern_id = uc1.concern_id and uc1.user_id=? and uc1.valid=1";
	
	/**
	 * 根据最大id查询关注用户并查询与指定用户的关系
	 */
	private static final String QUERY_CONCERN_WITH_JOIN_BY_MAX_ID  = "select uc1.is_mututal,uc.* from (select " 
			+ CONCERN_USER_INFO + ",uc0.* from user_concern as uc0, user_info as u"
			+ " where uc0.concern_id = u.id and uc0.user_id=? and uc0.valid=1 and u.shield=0 and uc0.id<=? " 
			+ " order by uc0.id desc limit ?,?) as uc"
			+ " left join user_concern as uc1 on uc.concern_id = uc1.concern_id and uc1.user_id=? and uc1.valid=1";
	
	/**
	 * 根据最小id查询关注用户并查询与指定用户的关系
	 */
	private static final String QUERY_CONCERN_WITH_JOIN_BY_MIN_ID = "select uc1.is_mututal,uc.* from (select " 
			+ CONCERN_USER_INFO + ",uc0.* from user_concern as uc0, user_info as u"
			+ " where uc0.concern_id = u.id and uc0.user_id=? and uc0.valid=0 and u.shield=0 and uc0.id>? " 
			+ " order by uc0.id desc limit ?,?) as uc"
			+ " left join user_concern as uc1 on uc.concern_id = uc1.concern_id and uc1.user_id=? and uc1.valid=1";
	
	/**
	 * 查询粉丝并查询与指定用户的关系
	 */
	private static final String QUERY_FOLLOW_WITH_JOIN = "select uc1.is_mututal,uc.* from (select uc0.*, " 
			+ CONCERN_USER_INFO + " from user_concern as uc0, user_info as u"
			+ " where uc0.user_id = u.id and uc0.concern_id=? and uc0.valid=1 and u.shield=0 " 
			+ " order by uc0.id desc limit ?,?) as uc"
			+ " left join user_concern as uc1 on uc.user_id=uc1.concern_id and uc1.user_id=? and uc1.valid=1";
	
	/**
	 * 根据最大id查询粉丝并查询与指定用户的关系
	 */
	private static final String QUERY_FOLLOW_WITH_JOIN_BY_MAX_ID = "select uc1.is_mututal,uc.* from (select uc0.*, " 
			+ CONCERN_USER_INFO + " from user_concern as uc0, user_info as u"
			+ " where uc0.user_id = u.id and uc0.concern_id=? and uc0.valid=1 and u.shield=0 and uc0.id<=?" 
			+ " order by uc0.id desc limit ?,?) as uc"
			+ " left join user_concern as uc1 on uc.user_id=uc1.concern_id and uc1.user_id=? and uc1.valid=1";
	
	/**
	 * 根据最小id查询粉丝并查询与指定用户的关系
	 */
	private static final String QUERY_FOLLOW_WITH_JOIN_BY_MIN_ID = "select uc1.is_mututal,uc.* from (select uc0.*, " 
			+ CONCERN_USER_INFO + " from user_concern as uc0, user_info as u"
			+ " where uc0.user_id = u.id and uc0.concern_id=? and uc0.valid=1 and u.shield=0 and uc0.id>?" 
			+ " order by uc0.id desc limit ?,?) as uc"
			+ " left join user_concern as uc1 on uc.user_id=uc1.concern_id and uc1.user_id=? and uc1.valid=1";
	
	
	/**
	 * 查询新粉
	 */
	private static final String QUERY_NEW_FOLLOW = "select uc0.*, " 
			+ CONCERN_USER_INFO + " from user_concern as uc0, user_info as u"
			+ " where uc0.user_id = u.id and uc0.concern_id=? and uc0.valid=1 and is_new=1" 
			+ " order by uc0.id desc limit ?,?";
	
	/**
	 * 根据最大id查询新粉
	 */
	private static final String QUERY_NEW_FOLLOW_BY_MAX_ID = "select uc0.*, " 
			+ CONCERN_USER_INFO + " from user_concern as uc0, user_info as u"
			+ " where uc0.user_id = u.id and uc0.concern_id=? and uc0.valid=1 and is_new=1 and uc0.id<=?" 
			+ " order by uc0.id desc limit ?,?";
	
	/**
	 * 更新推送标记
	 */
	private static final String UPDATE_PUSHED = "update " + table + " set pushed=? where id=?";
	
	/**
	 * 查询未读粉丝总数
	 */
	private static final String QUERY_UNCHECK_FOLLOW_COUNT ="select count(*) from " 
			+ table + " where valid=? and ck=? and concern_id=?";
	
	/** 
	 * 查询有效关注用户id 
	 */
	private static final String QUERY_VALID_CONCERN_ID = "select concern_id from " + table 
			+ " where user_id=? and concern_id=? and valid=?";
	
	/**
	 * 根据名字查询关注
	 */
	private static final String QUERY_CONCERN_BY_NAME = QUERY_CONCERN_HEADER 
			+ " and u.user_name like ? " + ORDER_BY_UC_ID_DESC;
	
	/**
	 * 根据名字和最大id
	 */
	private static final String QUERY_CONCERN_BY_NAME_AND_MAX_ID = QUERY_CONCERN_HEADER 
			+ " and u.user_name like ? and uc.id<=? " + ORDER_BY_UC_ID_DESC;
	
	/**
	 * 根据其他用户ids查询指定用户的关注用户id
	 */
	private static final String QUERY_CONCERN_ID_BY_OTHER_IDS = "select concern_id from " + table
			+ " where valid=1 and user_id=? and concern_id in ";
	
	/**
	 * 查询关注关联
	 */
	private static final String QUERY_IS_MUTUTAL = "select is_mututal from " + table 
			+ " where user_id=? and concern_id=? and valid=1";
	
	/**
	 * 根据loginCodes查询未关注人SQL头部
	 */
	private static final String QUERY_NOT_CONCERN_BY_LOGINCODES_HEAD = "select u0.id,u0.login_code," + U0_INFO + " from " 
			+ HTS.USER_INFO + " as u0 where u0.login_code in "; 
			
	/**
	 * 根据loginCodes查询未关注人SQL尾部
	 */
	private static final String QUERY_NOT_CONCERN_BY_LOGINCODES_FOOT = " and u0.id NOT IN"
			+ " (select uc.concern_id from " + HTS.USER_CONCERN + " as uc where uc.user_id=? and uc.valid=1)";
	
	/**
	 * 根据uids查询关注状态
	 */
	private static final String QUERY_IS_MUTUTAL_BY_UIDS = "select user_id,is_mututal from " + HTS.USER_CONCERN
			+ " where valid=1 and concern_id=? and user_id in";
	
	/**
	 * 根据cids查询关注状态
	 */
	private static final String QUERY_IS_MUTUTAL_BY_CIDS = "select concern_id,is_mututal from " + HTS.USER_CONCERN
			+ " where valid=1 and user_id=? and concern_id in";
	
	/**
	 * 更新新粉丝标记字段为０
	 */
	private static final String UPDATE_IS_NEW = "update " + table
			+ " set is_new=0 where concern_id=? and valid=1 and is_new=1";
	
	/**
	 * 查询关注用户名
	 */
	private static final String QUERY_CONCERN_NAME = "select " + CONCERN_NAME_INFO
			+ " from " + table + " ur0, " + HTS.USER_INFO + " as u0"
			+ " where u0.id=ur0.concern_id and ur0.user_id=? and valid=1 limit ?,1";
	
	@Override
	public UserConcern queryConcern(Integer userId, Integer concernId){
		return queryForObjectWithNULL(QUERY_CONCERN_WITH_LOCK, new RowMapper<UserConcern>(){

			@Override
			public UserConcern mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernByResult(rs);
			}
			
		}, new Object[] { userId, concernId });
	}
	
	@Override
	public void saveConcern(Integer id, Integer userId, Integer concernId, Integer isMututal, Integer ck, Date concernDate){
		try {
			getMasterJdbcTemplate().update(SAVE_CONCERN, new Object[]{
				id, userId, concernId, isMututal, concernDate, ck, Tag.TRUE});
		} catch(DuplicateKeyException e) {
		}
	}

	@Override
	public Integer updateConcern(Integer userId, Integer concernId, Integer isMututal, Date concernDate, Integer valid){
		return getMasterJdbcTemplate().update(UPDATE_CONCERN, new Object[]{
				isMututal, concernDate, valid, userId, concernId,1-valid });
	}
	
	@Override
	public void updateIsMututal(Integer userId, Integer concernId, Integer isMututal, Date concernDate){
		getMasterJdbcTemplate().update(UPDATE_IS_MUTUTAL, new Object[]{
				isMututal, concernDate, userId, concernId});
	}


	@Override
	public long queryConcernCount(Integer userId){
		return getMasterJdbcTemplate().queryForLong(QUERY_CONCERN_COUNT_HEADER,
				new Object[]{userId, Tag.TRUE});
	}
	
	@Override
	public List<UserConcernDto> queryConcerns(Integer userId, RowSelection rowSelection){
		return queryForPage(QUERY_CONCERN, new Object[]{userId, Tag.TRUE, Tag.FALSE}, new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public List<UserConcernDto> queryConcernsByMaxId(Integer userId, Integer maxId, RowSelection rowSelection){
		return queryForPage(QUERY_CONCERN_BY_MAX_ID, new Object[]{userId, Tag.TRUE, Tag.FALSE, maxId}, new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public List<UserConcernDto> queryConcernsByMinId(Integer userId,
			Integer minId, RowSelection rowSelection) {
		return queryForPage(QUERY_CONCERN_BY_MIN_ID, new Object[]{userId, Tag.TRUE, Tag.FALSE, minId}, new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public List<UserConcernDto> queryConcernsWithJoin(Integer userId, Integer joinId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_CONCERN_WITH_JOIN, 
				new Object[]{userId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId}, 
				new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernDtoByResultSet(rs);
			}
			
		});
	}
	
	@Override
	public List<UserConcernDto> queryConcernsWithJoinByMaxId(Integer userId, Integer joinId, Integer maxId, RowSelection rowSelection){
		return getJdbcTemplate().query(QUERY_CONCERN_WITH_JOIN_BY_MAX_ID, 
				new Object[]{userId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId}, 
				new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernDtoByResultSet(rs);
			}
			
		});
	}
	
	@Override
	public List<UserConcernDto> queryConcernsWithJoinByMinId(Integer userId, Integer joinId, Integer minId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_CONCERN_WITH_JOIN_BY_MIN_ID, 
				new Object[]{userId, rowSelection.getFirstRow(), rowSelection.getLimit(), minId, joinId}, 
				new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernDtoByResultSet(rs);
			}
		});
	}

	
	@Override
	public long queryConcernCountByMaxId(Integer userId, Integer maxId){
		return getJdbcTemplate().queryForLong(QUERY_CONCERN_COUNT_BY_MAX_ID, 
				new Object[]{userId, Tag.TRUE,  maxId});
	}
	
	@Override
	public long queryConcernCountByMinId(Integer userId, Integer minId) {
		return getJdbcTemplate().queryForLong(QUERY_CONCERN_COUNT_BY_MIN_ID, 
				new Object[]{userId, Tag.TRUE, minId});
	}

	@Override
	public List<UserFollowDto> queryFollows(Integer userId, RowSelection rowSelection){
		return queryForPage(QUERY_FOLLOW, new Object[]{userId, Tag.TRUE, Tag.FALSE}, new RowMapper<UserFollowDto>() {

			@Override
			public UserFollowDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserFollowDtoByResultSet(rs);
			}
			
		}, rowSelection);
	}
	
	@Override
	public List<UserFollowDto> queryFollowsByMaxId(Integer userId, Integer maxId, RowSelection rowSelection){
		return queryForPage(QUERY_FOLLOW_BY_MAX_ID, new Object[]{userId, Tag.TRUE, Tag.FALSE, maxId}, new RowMapper<UserFollowDto>() {

			@Override
			public UserFollowDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserFollowDtoByResultSet(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<UserFollowDto> queryFollowsByMinId(Integer userId,
			Integer minId, RowSelection rowSelection) {
		return queryForPage(QUERY_FOLLOW_BY_MIN_ID, new Object[]{userId, Tag.TRUE, Tag.FALSE, minId}, new RowMapper<UserFollowDto>() {

			@Override
			public UserFollowDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserFollowDtoByResultSet(rs);
			}
		}, rowSelection);
	}
	
	@Override
	public List<UserFollowDto> queryFollowsWithJoin(Integer userId, Integer joinId, RowSelection rowSelection){
		return getJdbcTemplate().query(QUERY_FOLLOW_WITH_JOIN, 
				new Object[]{userId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId}, 
				new RowMapper<UserFollowDto>() {

			@Override
			public UserFollowDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserFollowDtoByResultSet(rs);
			}
		});
	}
	
	@Override
	public List<UserFollowDto> queryFollowsWithJoinByMaxId(Integer userId, Integer joinId, Integer maxId, RowSelection rowSelection){
		return getJdbcTemplate().query(QUERY_FOLLOW_WITH_JOIN_BY_MAX_ID, 
				new Object[]{userId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit(), joinId}, 
				new RowMapper<UserFollowDto>() {

			@Override
			public UserFollowDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserFollowDtoByResultSet(rs);
			}
		});
	}
	
	@Override
	public List<UserFollowDto> queryFollowsWithJoinByMinId(Integer userId, Integer joinId, Integer minId, RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_FOLLOW_WITH_JOIN_BY_MIN_ID, 
				new Object[]{userId, minId, joinId, rowSelection.getFirstRow(), rowSelection.getLimit(), Tag.TRUE},
				new RowMapper<UserFollowDto>() {

			@Override
			public UserFollowDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserFollowDtoByResultSet(rs);
			}
		});
	}
	
	@Override
	public long queryFollowCountByMaxId(Integer userId, Integer maxId){
		return getJdbcTemplate().queryForLong(QUERY_FOLLOW_COUNT_BY_MAX_ID, 
				new Object[]{userId, Tag.TRUE, maxId});
	}
	
	@Override
	public long queryFollowCountByMinId(Integer userId, Integer minId) {
		return getJdbcTemplate().queryForLong(QUERY_FOLLOW_COUNT_BY_MIN_ID, 
				new Object[]{userId, Tag.TRUE, minId});
	}
	
	@Override
	public long queryFollowCount(Integer userId){
		return getMasterJdbcTemplate().queryForLong(QUERY_FOLLOW_COUNT_HEADER, 
				new Object[]{userId, Tag.TRUE});
	}
	
	@Override
	public void updateConcernCK(Integer concernId){
		getMasterJdbcTemplate().update(UPDATE_CONCERN_CK, 
				new Object[]{concernId});
	}
	
//	@Override
//	public void updateConcernCK(Integer userId, Integer concernId){
//		getMasterJdbcTemplate().update(UPDATE_CONCERN_CK_BY_UID, new Object[]{
//			Tag.TRUE, userId, concernId, Tag.FALSE
//		});
//	}
	
	@Override
	public void updatePushed(Integer id, Integer valid) {
		getMasterJdbcTemplate().update(UPDATE_PUSHED, new Object[]{valid,id});
	}
	
	@Override
	public long queryUnCheckFollowCount(Integer userId) {
		return getJdbcTemplate().queryForLong(QUERY_UNCHECK_FOLLOW_COUNT, 
				new Object[]{Tag.TRUE, Tag.FALSE, userId});
	}
	
	@Override
	public Integer queryValidConcernId(Integer userId, Integer concernId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_VALID_CONCERN_ID, new Object[]{userId, concernId, Tag.TRUE});
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
		
	}

	@Override
	public List<UserConcernDto> queryConcernByName(String userName,
			Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_CONCERN_BY_NAME, 
				new Object[]{userId, Tag.TRUE, Tag.FALSE, "%"+userName+"%"}, 
				new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernDtoByResultSet(rs);
			}
		}, rowSelection);
	}

	@Override
	public List<UserConcernDto> queryConcernByName(Integer maxId,
			String userName, Integer userId, RowSelection rowSelection) {
		return queryForPage(QUERY_CONCERN_BY_NAME_AND_MAX_ID, 
				new Object[]{userId, Tag.TRUE, Tag.FALSE, "%"+userName+"%", maxId}, 
				new RowMapper<UserConcernDto>() {

			@Override
			public UserConcernDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return buildUserConcernDtoByResultSet(rs);
			}
		}, rowSelection);
	}

	@Override
	public Set<Integer> queryConcernIds(Integer userId, Integer[] otherIds) {
		final Set<Integer> cids = new HashSet<Integer>();
		String inSelection = SQLUtil.buildInSelection(otherIds);
		Object[] args = SQLUtil.getArgsByInCondition(otherIds, new Object[]{userId}, true);
		String sql = QUERY_CONCERN_ID_BY_OTHER_IDS + inSelection;
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				cids.add(rs.getInt("concern_id"));
			}
		});
		return cids;
	}
	
	
	@Override
	public Integer queryIsMututal(Integer userId, Integer concernId) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_IS_MUTUTAL, new Object[]{userId, concernId}, 
					new RowMapper<Integer>(){
	
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return getIsMututal(rs);
				}
			});
		} catch(DataAccessException e) {
			return Tag.UN_CONCERN;
		}
	}
	
	@Override
	public void queryIsMututal(Integer[] userIds, final Integer concernId,
			final RowCallback<UserIsMututal> callback) {
		String inSelection  = SQLUtil.buildInSelection(userIds);
		String sql = QUERY_IS_MUTUTAL_BY_UIDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(userIds, new Object[]{concernId}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(new UserIsMututal(
						rs.getInt("user_id"),
						concernId,
						rs.getInt("is_mututal")));
			}
		});
	}

	@Override
	public void queryIsMututal(final Integer userId, Integer[] concernIds,
			final RowCallback<UserIsMututal> callback) {
		String inSelection  = SQLUtil.buildInSelection(concernIds);
		String sql = QUERY_IS_MUTUTAL_BY_CIDS + inSelection;
		Object[] args = SQLUtil.getArgsByInCondition(concernIds, new Object[]{userId}, true);
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				callback.callback(new UserIsMututal(
						userId,
						rs.getInt("concern_id"),
						getIsMututal(rs)));
			}
		});
	}
	
	@Override
	public List<UserInfoDto> queryNotConcernUserInfo(Integer userId,
			String[] loginCodes) {
		String inSelection = SQLUtil.buildInSelection(loginCodes);
		String sql = QUERY_NOT_CONCERN_BY_LOGINCODES_HEAD + inSelection + QUERY_NOT_CONCERN_BY_LOGINCODES_FOOT;
		Object[] args = SQLUtil.getArgsByInCondition(loginCodes, new Object[]{userId}, false);
		return getJdbcTemplate().query(sql, args, new RowMapper<UserInfoDto>() {

			@Override
			public UserInfoDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserInfoDto dto = UserInfoDaoImpl.buildUserInfoDtoByResult(rs.getInt("id"), rs);
				dto.setLoginCode(rs.getString("login_code"));
				return dto;
			}
		});
	}
	
	
	@Override
	public List<UserFollowDto> queryNewFollow(Integer concernId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_NEW_FOLLOW, 
				new Object[]{concernId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<UserFollowDto>() {

			@Override
			public UserFollowDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserFollowDto dto = buildUserFollowDtoByResultSet(rs);
				return dto;
			}
		});
	}

	@Override
	public List<UserFollowDto> queryNewFollow(Integer maxId, Integer concernId,
			RowSelection rowSelection) {
		return getJdbcTemplate().query(QUERY_NEW_FOLLOW_BY_MAX_ID, 
				new Object[]{concernId, maxId, rowSelection.getFirstRow(), rowSelection.getLimit()}, 
				new RowMapper<UserFollowDto>() {

			@Override
			public UserFollowDto mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserFollowDto dto = buildUserFollowDtoByResultSet(rs);
				return dto;
			}
		});
	}
	
	@Override
	public void updateISNew(Integer concernId) {
		getMasterJdbcTemplate().update(UPDATE_IS_NEW, concernId);
	}
	
	/**
	 * 从结果集构建UserConcern对象
	 * @param res
	 * @return
	 * @throws SQLException 
	 */
	public static UserConcern buildUserConcernByResult(ResultSet rs) throws SQLException {
		return new UserConcern(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("concern_id"),
				rs.getInt("is_mututal"), 
				(Date)rs.getObject("concern_date"),
				rs.getInt("ck"), 
				rs.getInt("valid"));
	}
	
	/**
	 * 从结果集构建UserConcernDto对象
	 * @param res
	 * @return
	 * @throws SQLException 
	 */
	public static UserConcernDto buildUserConcernDtoByResultSet(ResultSet rs) throws SQLException {
		UserConcernDto dto = new UserConcernDto(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("concern_id"),
				getIsMututal(rs),
				(Date)rs.getObject("concern_date"),
				rs.getInt("ck"),
				rs.getString("user_name"),
				rs.getInt("sex"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getString("user_label"),
				rs.getString("address"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getString("signature"),
				rs.getString("platform_sign"),
				rs.getInt("star"),
				rs.getInt("online"),
				rs.getInt("platform_verify"));
		return dto;
	}
	
	public static UserFollowDto buildUserFollowDtoByResultSet(ResultSet rs) throws SQLException {
		UserFollowDto dto = new UserFollowDto(
				rs.getInt("id"),
				rs.getInt("user_id"),
				rs.getInt("concern_id"),
				getIsMututal(rs),
				(Date)rs.getObject("concern_date"),
				rs.getInt("ck"),
				rs.getInt("is_new"),
				rs.getString("user_name"),
				rs.getInt("sex"),
				rs.getString("user_avatar"),
				rs.getString("user_avatar_l"),
				rs.getString("user_label"),
				rs.getString("address"),
				rs.getString("province"),
				rs.getString("city"),
				rs.getString("signature"),
				rs.getString("platform_sign"),
				rs.getInt("star"),
				rs.getInt("online"),
				rs.getInt("platform_verify"));
		return dto;
	}
	
	/**
	 * 获取用户关联关系
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static int getIsMututal(ResultSet rs) throws SQLException {
		Object isMutualObj = rs.getObject("is_mututal");
		int isMututal = Tag.UN_CONCERN;
		if(isMutualObj != null) {
			isMututal = (Integer)isMutualObj;
		}
		
		return isMututal;
	}

	@Override
	public UserConcernName queryConcernName(Integer userId, Integer start) {
		try {
			return getJdbcTemplate().queryForObject(QUERY_CONCERN_NAME,
					new Object[]{userId, start}, 
					new RowMapper<UserConcernName>() {
	
						@Override
						public UserConcernName mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return new UserConcernName(
									rs.getInt("id"), 
									rs.getString("user_name"));
						}
				
			});
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}


}
