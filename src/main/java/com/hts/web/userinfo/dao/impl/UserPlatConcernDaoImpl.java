package com.hts.web.userinfo.dao.impl;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.hts.web.base.database.HTS;
import com.hts.web.common.dao.impl.BaseDaoImpl;
import com.hts.web.userinfo.dao.UserPlatConcernDao;

@Repository("HTSUserPlatConcernDao")
public class UserPlatConcernDaoImpl extends BaseDaoImpl implements
		UserPlatConcernDao {

	private static String table = HTS.USER_PLAT_CONCERN;
	
	/**
	 * 保存社交平台关注的用户id
	 */
	private static final String SAVE_PLAT_CONCERN = "insert into " + table
			+ " (user_id, plat_concern_id) values ";
	
	/**
	 * 根据plat_concern_id查询user_id
	 */
	private static final String QUERY_CID_BY_UID = "select plat_concern_id from " + table
			+ " where user_id=? and plat_concern_id=?";
	
	@Override
	public void savePlatConcern(Integer userId, List<Integer> platConcernIds) {
		
		if(platConcernIds == null || platConcernIds.isEmpty()) 
			return;
		
		StringBuilder sb = new StringBuilder(SAVE_PLAT_CONCERN);
		
		for(int i = 0; i < platConcernIds.size(); i++) {
			if(i > 0) {
				sb.append(",");
			}
			sb.append("(")
			.append(userId).append(",")
			.append(platConcernIds.get(i))
			.append(")");
		}
		getMasterJdbcTemplate().update(sb.toString());
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
