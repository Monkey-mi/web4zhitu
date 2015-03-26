package com.hts.web.userinfo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.ObjectWithUserRemark;
import com.hts.web.common.pojo.PushStatus;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户互动子模块业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-3
 * @author ztj
 *
 */
public interface UserInteractService extends BaseService {
	

	/**
	 * 保存用户关注
	 * 
	 * @param userId
	 * @param concernId
	 * @throws Exception
	 */
	public PushStatus saveConcern(Boolean im, Integer userId, Integer concernId) throws Exception;
	
	/**
	 * 保存用户关注
	 * 
	 * @param im
	 * @param userId
	 * @param concernId
	 * @param concernCount 手动填写的关注数量
	 * @return
	 * @throws Exception
	 */
	public PushStatus saveConcern(Boolean im, Integer userId, Integer concernId,
			Integer concernCount) throws Exception;
	
	/**
	 * 批量保存用户关注
	 * 
	 * @param userId
	 * @param concernIdsStr
	 * @return
	 */
	public List<PushStatus> batchSaveConcern(Boolean im,Integer userId, 
			String concernIdsStr) throws Exception;
	
	
	/**
	 * 批量保存关注用户
	 * 
	 * @param im
	 * @param userId
	 * @param concernIdsStr
	 * @param concernCount
	 * @return
	 * @throws Exception
	 */
	public List<PushStatus> batchSaveConcern(Boolean im, Integer userId,
			String concernIdsStr, Integer concernCount) throws Exception;
	
	
	
	/**
	 * 更新互相关注
	 * 
	 * @param userId
	 * @param concernId
	 * @param concernDate
	 * @param mututal 是否设定为互相关注
	 * @return
	 */
	public Integer updateMututal(Integer userId, Integer concernId, Date concernDate, 
			boolean mututal) throws Exception;
	
	
	/**
	 * 取消关注
	 * 
	 * @param userId
	 * @param concernId
	 */
	public void cancelConcern(Integer userId, Integer concernId) throws Exception;
	
	/**
	 * 批量取消用户关注
	 * 
	 * @param userId
	 * @param concernIdsStr
	 */
	public int batchCancelConcern(Integer userId, String concernIdsStr) throws Exception;
	
	/**
	 * 构建用户关注列表
	 * 
	 * @param userId
	 * @param joinId
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param total
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildConcerns(Integer userId, Integer joinId, int sinceId, int maxId, 
			int start, int limit, int total, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建关注用户列表
	 * 
	 * @param userName
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildConcerns(String userName, Integer userId, int maxId, 
			int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 构建粉丝列表
	 * @param userId
	 * @param joinId
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildFollows(Boolean clearUnCheck, Integer userId, Integer joinId, int sinceId, int maxId, 
			int start, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建未读粉丝列表
	 * 
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildNewFollow(Boolean clearUnCheck, Integer userId, int maxId, int start, int limit, 
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 更新粉丝阅读标记
	 * 
	 * @param concernId
	 * @throws Exception
	 */
	public void updateNewFollow(Integer concernId) throws Exception;
	
	/**
	 * 构建用户搜索列表
	 * 
	 * @param userName
	 * @param joinId
	 * @param minId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 */
	public void buildUserSearchInfo(String userName, Integer joinId, int minId, int start, 
			int limit, Map<String, Object> jsonMap) throws Exception;
	
	
	/**
	 * 保存分类关注
	 * 
	 * @param userId
	 * @param typeId
	 * @throws Exception
	 */
	public void saveConcernType(Integer userId, Integer typeId) throws Exception;
	
	/**
	 * 取消分类关注
	 * 
	 * @param userId
	 * @param typeId
	 * @throws Exception
	 */
	public void cancelConcernType(Integer userId, Integer typeId) throws Exception;
	
	/**
	 * 构建为关注用户列表
	 * 
	 * @param userId
	 * @param loginCodesStr
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildNotConcern(Integer userId, String loginCodesStr, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建已经注册的用户列表
	 * 
	 * @param loginCodesStr
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildRegister(Integer userId, String loginCodesStr,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存用户屏蔽
	 * 
	 * @param userId
	 * @param shieldId
	 */
	public void saveShield(Integer userId, Integer shieldId) throws Exception;
	
	/**
	 * 删除用户屏蔽
	 * 
	 * @param userId
	 * @param shieldId
	 */
	public void deleteShield(Integer userId, Integer shieldId) throws Exception;
	
	/**
	 * 查询屏蔽用户
	 * 
	 * @param userId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildShieldUser(Integer userId, Integer maxId, Integer start, Integer limit,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存举报
	 * 
	 * @param userId
	 * @param reportId
	 * @throws Exception
	 */
	public void saveReport(Integer userId, Integer reportId) throws Exception;
	
	/**
	 * 获取备注
	 * 
	 * @param userId
	 * @param objList
	 * @throws Exception
	 */
	public void extractRemark(Integer userId, List<? extends ObjectWithUserRemark> objList);
	
	/**
	 * 获取备注
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void extractRemark(Integer userId, ObjectWithUserRemark obj);
	
	/**
	 * 更新备注
	 * 
	 * @param userId
	 * @param remarkId
	 * @param remark
	 * @throws Exception
	 */
	public void updateRemark(Integer userId, Integer remarkId, String remark) throws Exception;
	
	/**
	 * 删除备注
	 * 
	 * @param userId
	 * @param remarkId
	 * @throws Exception
	 */
	public void deleteRemark(Integer userId, Integer remarkId) throws Exception;
	

}
