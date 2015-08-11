package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpUserVerifyDto;

/**
 * <p>
 * 用户认证信息缓存数据访问接口
 * </p>
 * 
 * 创建时间：2014-7-15
 * @author tianjie
 *
 */
public interface OpUserVerifyDtoCacheDao extends BaseCacheDao {

	public List<OpUserVerifyDto> queryVerify();

	/**
	 * 查询随机认证类型
	 * 
	 * @return
	 */
	public OpUserVerifyDto queryRandomVerify();
	
}
