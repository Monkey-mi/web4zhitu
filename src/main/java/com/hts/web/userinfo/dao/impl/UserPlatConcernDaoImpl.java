package com.hts.web.userinfo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.operations.dao.UserRecommendDao;
import com.hts.web.userinfo.dao.UserPlatConcernDao;

@Repository("HTSUserPlatConcernDao")
public class UserPlatConcernDaoImpl extends BaseDaoImpl implements
		UserPlatConcernDao {

	private static String table = HTS.USER_PLAT_CONCERN;
	
	/**
	 * 保存社交平台关注的用户id
	 */
	private static final String SAVE_PLAT_CONCERN = "insert into " + table
			+ " (user_id, plat_concern_id) values (?,?)";
	
	/**
	 * 根据plat_concern_id查询user_id
	 */
	private static final String QUERY_CID_BY_UID = "select plat_concern_id from " + table
			+ " where user_id=? and plat_concern_id=?";
	
	
	@Autowired
	private UserRecommendDao recommendDao;
	
	@Override
	public void savePlatConcern(Integer userId, Integer platConcernId) {
		getMasterJdbcTemplate().update(SAVE_PLAT_CONCERN, new Object[]{
			userId,
			platConcernId
		});
	}

	@Override
	public Integer queryCid(Integer userId, Integer platConcernId) {
		try {
			return getJdbcTemplate().queryForInt(QUERY_CID_BY_UID, new Object[]{userId, platConcernId});
		} catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}

	

}
