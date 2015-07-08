package com.hts.web.operations.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpUser;
import com.hts.web.common.pojo.OpUserLabelRecommend;
import com.hts.web.common.pojo.OpUserRecommend;
import com.hts.web.common.pojo.UserDynamicRec;
import com.hts.web.common.pojo.UserVerifyDto;

/**
 * <p>
 * 推荐用户数据访问接口
 * </p>
 * 
 * 创建时间：2013-8-29
 * @author ztj
 *
 */
public interface UserRecommendDao extends BaseDao {
	
	/**
	 * 查询推荐用户
	 * 
	 * @param joinIdF
	 * @param rowSelection
	 * @return
	 */
	public List<OpUser> queryRecommendUser(Integer joinId, RowSelection rowSelection);
	
	/**
	 * 根据最大id查询推荐用户
	 * 
	 * @param joinId
	 * @param maxId
	 * @param rowSelection
	 * @return
	 */
	public List<OpUser> queryRecommendUserByMaxId(
			Integer joinId, Integer maxId, RowSelection rowSelection);
	
	/**
	 * 根据用户id查询推荐用户
	 * @param uid
	 * @return
	 */
	public OpUser queryOpUserByUID(Integer uid);
	
	/**
	 * 根据平台id查询
	 * @param platformIds
	 * @param joinId
	 * @return
	 */
	public List<OpUser> queryPlatformRecommendUser(String platformIds, Integer joinId);
	
	/**
	 * 根据用户id查询标签推荐用户
	 * @param uid
	 * @return
	 */
	public OpUserLabelRecommend queryLabelRecommendUserByUID(Integer uid);
	
	/**
	 * 查询标签推荐用户
	 * 
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpUserLabelRecommend> queryLabelRecommendUser(Integer joinId,
			RowSelection rowSelection);
	
	/**
	 * 查询标签推荐用户
	 * 
	 * @param maxId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpUserLabelRecommend> queryLabelRecommendUser(Integer maxId, Integer joinId, 
			RowSelection rowSelection);
	
	
	/**
	 * 保存推荐用户
	 * 
	 * @param recommend
	 */
	public void saveRecommendUser(OpUserRecommend recommend);
	
	/**
	 * 根据用户id查询推荐信息
	 * UserRecommendDao
	 * @param userId
	 * @return
	 */
	public OpUserRecommend queryRecommendUserByUID(Integer userId);
	
	/**
	 * 查询系统接受但用户未接受的推荐
	 * 
	 * @param userId
	 * @return
	 */
	public OpUserRecommend queryUserUnAcceptedRecommend(Integer userId);
	
	
	/**
	 * 更新用户允许标记
	 * 
	 * @param userId
	 * @param state
	 * @param dateModified
	 */
	public void updateUserAcceptByUID(Integer userId, Integer state, Date dateModified);
	
	/**
	 * 更新系统允许标志
	 * 
	 * @param userId
	 * @param state
	 * @param dateModified
	 */
	public void updateSysAcceptByUID(Integer userId, Integer state, Date dateModified);
	
	/**
	 * 更新允许标志
	 * 
	 * @param userId
	 * @param userState
	 * @param sysState
	 * @param dateModified
	 */
	public void updateAcceptByUID(Integer userId, Integer userState, 
			Integer sysState, Date dateModified);
	
	/**
	 * 查询推荐用户
	 * 
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpUser> queryRecommendUserOrderByAct(Integer joinId, RowSelection rowSelection);
	
	/**
	 * 查询推荐用户
	 * 
	 * @param maxId
	 * @param joinId
	 * @param rowSelection
	 * @return
	 */
	public List<OpUser> queryRecommendUserOrderByAct(
			Integer maxId, Integer joinId, RowSelection rowSelection);
	
	public List<OpUser> queryWeightRec(Integer userId, Integer limit);
	
	public List<OpUser> queryWeightVerifyRec(Integer userId, Integer verifyId, Integer limit);
	
	
	/**
	 * 查询推荐总数
	 * 
	 * @param activity
	 * @return
	 */
	public long queryRecommendCount(Integer activity);
	
	/**
	 * 查询认证用户
	 * 
	 * @param verifyIds
	 * @param limit
	 * @param callback
	 */
	public void queryVerifyUser(Integer userId, Integer[] verifyIds, Integer limit, RowCallback<UserVerifyDto> callback);

	/**
	 * 查询认证分榜推荐
	 * 
	 * @param userId
	 * @param verifyId
	 * @param rowSelection
	 * @return
	 */
	public List<OpUser> queryVerifyRecommendUserOrderByAct(Integer userId, Integer verifyId,
			RowSelection rowSelection);

	/**
	 * 查询认证分榜推荐
	 * 
	 * @param maxId
	 * @param userId
	 * @param verifyId
	 * @param rowSelection
	 * @return
	 */
	public List<OpUser> queryVerifyRecommendUserOrderByAct(Integer maxId, Integer userId,
			Integer verifyId, RowSelection rowSelection);

	/**
	 * 查询置顶推荐
	 * 
	 * @return
	 */
	public List<OpUserLabelRecommend> queryWeightLabelRecommendUser();
	
}
