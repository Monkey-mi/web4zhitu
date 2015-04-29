package com.hts.web.userinfo.service;

import com.hts.web.common.service.BaseService;

/**
 * <p>
 * 用户活跃度业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2014-5-25
 * @author tianjie
 *
 */
public interface UserActivityService extends BaseService {

	/**
	 * 添加活跃积分
	 * 
	 * @param typeId
	 * @param multiple
	 * @param userId
	 */
	@Deprecated
	public void addActivityScore(Integer typeId, int multiple, Integer userId);
	
	/**
	 * 添加活跃积分
	 * @param typeId 定义在Tag类中 eg.Tag.ACT_TYPE_COMMENT
	 * @param userId
	 */
	public void addActivityScore(Integer  typeId,Integer userId)throws Exception;
}
