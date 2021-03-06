package com.hts.web.operations.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.UserVerify;
import com.hts.web.common.pojo.UserWithWorld;
import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户运营业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2013-8-29
 * @author ztj
 *
 */
public interface UserOperationsService extends BaseService {
	
	/**
	 * 构建推荐用户列表
	 * 
	 * @param sinceId
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param joinId
	 * @param jsonMap
	 */
	public void buildUserRecommend(int maxId, int start, int limit, int joinId,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建社交平台推荐用户列表
	 * 
	 * @param joinId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildPlatformUserRecomend(int joinId, Map<String, Object> jsonMap) throws Exception;
	

	/**
	 * 构建推荐用户列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param joinId
	 * @param worldLimt
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSquareRecommendUser(int maxId, int start, int limit, 
			Integer joinId, int worldLimt, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建推荐用户列表，根据activity排序
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param joinId
	 * @param worldLimit
	 * @param trimSelf
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildSquareRecommendUserV2(int maxId, int start, int limit, Integer joinId,
			int worldLimit, Boolean trimSelf, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建标签推荐用户列表
	 * 
	 * @param maxId
	 * @param start
	 * @param limit
	 * @param worldLimit
	 * @param joinId
	 * @param trimMe
	 * @param jsonMap
	 */
	public void buildLabelRecommendUser(int maxId, int start, int limit, int worldLimit, 
			Integer joinId, Boolean trimMe,Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 加载推荐织图信息
	 * @param worldLimit
	 * @param userList
	 * @throws Exception
	 */
	public void extractHTWorldThumbUser(int worldLimit, final List<? extends UserWithWorld> userList) throws Exception;
	
	/**
	 * 保存推荐用户,会出现以下状态：
	 * <pre>
	 * u  s  op	
	 ***************
	 * 0  1  u=1
	 ***************
	 * 1  0  exception
	 ***************
	 * 1  1  exception
	 ***************
	 * 1  2  s=0
	 ***************
	 * 2  1  u=1,s=0
	 * </pre>
	 * @param userId
	 * @param verifyId
	 * @throws Exception
	 */
	public void saveOrUpdateRecommend(Integer userId, Integer verifyId) throws Exception;
	
	/**
	 * 更新推荐用户接受状态
	 * 
	 * @param userId
	 * @param accepted
	 * @throws Exception
	 */
	public void updateRecommendUserAccept(Integer userId, Boolean accepted, Boolean deleteRecMsg) throws Exception;
	
	/**
	 * 查询用户被推荐状态<br />
	 * 1：可以申请
	 * 2：等待审核中
	 * 3：通过
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Integer getRecommendState(Integer userId) throws Exception;
	
	/**
	 * 获取用户接受状态
	 * 
	 * @param userId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void getUserAccpetState(Integer userId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 查询用户被推荐状态<br />
	 * 0：可以申请
	 * -2：等待审核中
	 * >0: 认证id
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public UserVerify getRecommendStateV2(Integer userId) throws Exception;
	
	/**
	 * 构建认证用户索引列表
	 * 
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildVerifyIndex(Integer userId, int limit, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建认证分榜推荐列表
	 * 
	 * @param maxId	
	 * @param start				起始页码，默认为1
	 * @param limit				每页限定最大记录数,默认为12
	 * @param userId			用户id（当前登陆用户id）
	 * @param verifyId			认证id，如果需要返回所有达人列表和verify（认证列表信息),务必将此参数设置为0，0时会加载userThemes（用户专题列表）	
	 * @param worldLimit		每个用户最新织图限定数量，默认为０，即不获取织图
	 * @param userThemeCount	获取用户专题列表限定数量，默认为0，即不返回用户专题列表
	 * @param jsonMap			返回值json对象
	 * @throws Exception
	 * @author zhutianjie
	 * @modify zhangbo	2015年12月2日
	 */
	public void buildVerifyRecommendUser(int maxId, int start, int limit,
			Integer userId, Integer verifyId, int worldLimit, Integer userThemeCount,
			Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 构建认证列表
	 * 
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildVerify(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 随机获取马甲id
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer getRandomZombieId() throws Exception;
	
}
