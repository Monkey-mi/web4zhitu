package com.hts.web.userinfo.dao;

import com.hts.web.base.database.RowCallback;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserConcernType;

/**
 * <p>
 * 用户关注分类数据访问接口
 * </p>
 * 
 * 创建时间：2014-1-21
 * @author lynch
 *
 */
public interface UserConcernTypeDao extends BaseDao {
	
	/**
	 * 保存分类关注
	 * 
	 * @param type
	 */
	public void saveConcernType(UserConcernType type);
	
	/**
	 * 查询分类关注
	 * 
	 * @param userId
	 * @param typeId
	 * @return
	 */
	public UserConcernType queryConcernType(Integer userId, Integer typeId);

	/**
	 * 查询用户关注分类
	 * 
	 * @param userId
	 * @param callback
	 */
	public void queryConcernType(Integer userId, RowCallback<Integer> callback);
	
	/**
	 * 更新分类关注有效性
	 * 
	 * @param userId
	 * @param typeId
	 */
	public void updateConcernTypeValid(Integer userId, Integer typeId, Integer valid);
	
}
