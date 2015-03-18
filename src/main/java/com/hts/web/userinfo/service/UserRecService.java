package com.hts.web.userinfo.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户推荐业务罗辑访问接口
 * 
 * @author lynch
 *
 */
public interface UserRecService extends BaseService {

	/**
	 * 保存在社交平台关注的用账号
	 * 
	 * @param userId
	 * @param loginCodes
	 * @throws Exception
	 */
	public void savePlatConcerns(Integer userId, String loginCodes) throws Exception;
	
	
	public void buildRecUser(Integer userId,
			Map<String, Object> jsonMap) throws Exception;
	
	
	public void buildSuggestUser(Integer userId,
			Map<String, Object> jsonMap) throws Exception;
	
	
}
