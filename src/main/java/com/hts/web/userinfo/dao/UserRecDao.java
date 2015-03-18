package com.hts.web.userinfo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserDynamicRec;

public interface UserRecDao extends BaseDao {

	/**
	 * 根据城市查询用户总数
	 * 
	 * @param city
	 * @return
	 */
	public long queryCityUserCount(Integer userId, String city);
	
	/**
	 * 根据城市查询推荐用户
	 * 
	 * @param userId
	 * @param city
	 * @param limit
	 * @return
	 */
	public UserDynamicRec queryCityRecUser(Integer userId, String city, Integer start);

	/**
	 * 查询社交平台推荐总数
	 * 
	 * @param userId
	 * @return
	 */
	public long queryPlatRecCount(Integer userId);
	
	/**
	 * 查询社交平台推荐
	 * 
	 * @param userId
	 * @param start
	 * @return
	 */
	public UserDynamicRec queryPlatRec(Integer userId, Integer start);
	
	
	/**
	 * 查询关注人的推荐总数
	 * 
	 * @param userId
	 * @param concernId
	 * @return
	 */
	public long queryConcernRecCount(Integer userId, Integer concernId);
	
	/**
	 * 查询关注人的推荐
	 * @param userId
	 * @param concernId
	 * @param start
	 * @return
	 */
	public UserDynamicRec queryConcernRec(Integer userId, Integer concernId, 
			Integer start);
	
	/**
	 *　查询运营推荐用户，在信息流展示
	 *
	 * @return
	 */
	public List<UserDynamicRec> queryOpRecList(Integer userId, 
			int start, int limit);
	
	/**
	 * 查询运营推荐用户
	 * 
	 * @param userId
	 * @param start
	 * @return
	 */
	public UserDynamicRec queryOpRec(Integer userId, 
			int start);
	
	/**
	 * 构建信息流推荐用户
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UserDynamicRec buildUserDynamicRec(ResultSet rs) throws SQLException;
	
	/**
	 * 查询标签推荐总数
	 * 
	 * @param userId
	 * @param labelId
	 * @return
	 */
	public long queryLabelRecCount(Integer userId, Integer labelId);
	
	/**
	 * 查询标签推荐用户
	 * 
	 * @param userId
	 * @param labelId
	 * @return
	 */
	public UserDynamicRec queryLabelRec(Integer userId, Integer labelId);
}
