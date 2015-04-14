package com.hts.web.userinfo.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserConcern;
import com.hts.web.common.pojo.UserConcernDto;
import com.hts.web.common.pojo.UserConcernName;
import com.hts.web.common.pojo.UserFollowDto;
import com.hts.web.common.pojo.UserInfoDto;
import com.hts.web.common.pojo.UserIsMututal;

/**
 * <p>
 * 用户关注信息数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * 
 * @author ztj
 * 
 */
public interface UserConcernDao extends BaseDao {

	/**
	 * 查询关注
	 * 
	 * @param conn
	 * @param userId
	 * @param concernId
	 */
	public UserConcern queryConcern(Integer userId, Integer concernId);
	
	/**
	 * 保存关注
	 * 
	 * @param userId
	 * @param concernId
	 * @param isMututal
	 * @param concernDate
	 */
	public void saveConcern(Integer id, Integer userId, Integer concernId, Integer isMututal, Integer ck, Date concernDate);

	/**
	 * 更新关注
	 * 
	 * @param conn
	 * @param userId
	 * @param concernId
	 * @param isMututal
	 * @param concernDate
	 * @param valid
	 */
	public Integer updateConcern(Integer userId, Integer concernId, Integer isMututal, Date concernDate, Integer valid);
	
	
	/**
	 * 更新互相关注
	 * 
	 * @param con
	 * @param userId
	 * @param concernId
	 * @param isMututal
	 * @param concernDate
	 */
	public void updateIsMututal(Integer userId, Integer concernId, Integer isMututal, Date concernDate);

	/**
	 * 分页查询指定关注列表
	 * @param conn
	 * @param userId
	 * @return
	 */
	public List<UserConcernDto> queryConcerns(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id分页查询关注列表
	 * @param conn
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryConcernsByMaxId(Integer userId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据最小id分页查询关注列表
	 * @param userId
	 * @param minId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryConcernsByMinId(Integer userId, Integer minId, RowSelection rowSelection);
	
	/**
	 * 查询用户关注并查询与指定用户的关系
	 * @param userId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryConcernsWithJoin(Integer userId, Integer joinId,RowSelection rowSelection);
	
	/**
	 * 根据最大id查询用户关注并查询与指定用户的关系
	 * @param userId
	 * @param joinId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryConcernsWithJoinByMaxId(Integer userId, Integer joinId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据最小id查询用户关注并查询与指定用户的关系
	 * @param userId
	 * @param joinId
	 * @param minId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryConcernsWithJoinByMinId(Integer userId, Integer joinId, Integer minId, RowSelection rowSelection);
	
	
	/**
	 * 根据最大id查询关注总数
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 */
	public long queryConcernCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 根据最小id查询关注总数
	 * @param userId
	 * @param minId
	 * @return
	 */
	public long queryConcernCountByMinId(Integer userId, Integer minId);
	
	/**
	 * 查询关注总数
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 */
	public long queryConcernCount(Integer userId);
	
	/**
	 * 分页查询粉丝
	 * 
	 * @param conn
	 * @param concernId
	 * @param rowSelection
	 * @return
	 */
	public List<UserFollowDto> queryFollows(Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据最大id分页查询粉丝
	 * 
	 * @param conn
	 * @param userId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserFollowDto> queryFollowsByMaxId(Integer userId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据最小id分页查询粉丝
	 * @param userId
	 * @param minId
	 * @param rowSelection
	 * @return
	 */
	public List<UserFollowDto> queryFollowsByMinId(Integer userId, Integer minId, RowSelection rowSelection);
	
	/**
	 * 查询粉丝并查询与指定用户的关系
	 * @param userId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<UserFollowDto> queryFollowsWithJoin(Integer userId, Integer joinId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询粉丝并查询与指定用户的关系
	 * 
	 * @param userId
	 * @param joinId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<UserFollowDto> queryFollowsWithJoinByMaxId(Integer userId, Integer joinId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据最小id查询粉丝并查询与指定用户的关系
	 * 
	 * @param userId
	 * @param joinId
	 * @param minId
	 * @param rowSelection
	 * @return
	 */
	public List<UserFollowDto> queryFollowsWithJoinByMinId(Integer userId, Integer joinId, Integer minId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询粉丝总数
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 */
	public long queryFollowCountByMaxId(Integer userId, Integer maxId);
	
	/**
	 * 根据最小id查询粉丝总数
	 * 
	 * @param userId
	 * @param minId
	 * @return
	 */
	public long queryFollowCountByMinId(Integer userId, Integer minId);
	
	/**
	 * 查询粉丝总数
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 */
	public long queryFollowCount(Integer userId);
	
	/**
	 * 更新所有未查阅的粉丝
	 * @param conn
	 * @param userId
	 */
	public void updateConcernCK(Integer concernId);
	
	/**
	 * 更新未阅读的粉丝标记
	 * 
	 * @param userId
	 * @param concernId
	 */
//	public void updateConcernCK(Integer userId, Integer concernId);

	/**
	 * 更新推送标记
	 * 
	 * @param id
	 * @param valid
	 */
	public void updatePushed(Integer id, Integer valid);
	
	/**
	 * 更新未读粉丝总数
	 * 
	 * @param userId
	 * @return
	 */
	public long queryUnCheckFollowCount(Integer userId);
	
	/**
	 * 查询有效关注id
	 * 
	 * @param userId
	 * @return
	 */
	public Integer queryValidConcernId(Integer userId, Integer concernId);
	
	/**
	 * 根据用户名模糊查询关注
	 * 
	 * @param userName
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryConcernByName(String userName, Integer userId, RowSelection rowSelection);
	
	/**
	 * 根据用户名模糊查询关注
	 * 
	 * @param maxId
	 * @param userName
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<UserConcernDto> queryConcernByName(Integer maxId, String userName, Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询指定用户的关注ids列表
	 * 
	 * @param userId
	 * @param otherIds
	 * @return
	 */
	public Set<Integer> queryConcernIds(Integer userId, Integer[] otherIds);
	
	/**
	 * 查询关注关系
	 * 
	 * @param userId
	 * @return
	 */
	public Integer queryIsMututal(Integer userId, Integer concernId);
	
	/**
	 * 查询批量用户对我的关注状态
	 * 
	 * @param userIds
	 * @param concernId
	 * @param callback
	 */
	public void queryIsMututal(Integer[] userIds, Integer concernId, 
			RowCallback<UserIsMututal> callback);
	
	/**
	 * 查询我对批量用户关注状态
	 * 
	 * @param userId
	 * @param concernIds
	 * @param callback
	 */
	public void queryIsMututal(Integer userId, Integer[] concernIds, 
			RowCallback<UserIsMututal> callback);
	
	
	/**
	 * 查询未关注的用户信息
	 * 
	 * @param userId
	 * @param loginCodes
	 * @return
	 */
	public List<UserInfoDto> queryNotConcernUserInfo(Integer userId, String[] loginCodes);
	
	/**
	 * 查询最新粉丝
	 * 
	 * @param concernId
	 * @param rowSelection
	 * @return
	 */
	public List<UserFollowDto> queryNewFollow(Integer concernId, RowSelection rowSelection);
	
	/**
	 * 查询最新粉丝
	 * 
	 * @param maxId
	 * @param concernId
	 * @param rowSelection
	 * @return
	 */
	public List<UserFollowDto> queryNewFollow(Integer maxId, Integer concernId, RowSelection rowSelection);
	
	/**
	 * 更新新粉丝标记
	 * 
	 * @param concernId
	 */
	public void updateISNew(Integer concernId);

	/**
	 * 查询关注人名字
	 * 
	 * @param userId
	 * @param start
	 * @return
	 */
	public UserConcernName queryConcernName(Integer userId, Integer start);
}
