package com.hts.web.plat.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.PlatConcern;

/**
 * <p>
 * 社交平台关注缓存数据访问接口
 * </p>
 * 
 * 创建时间: 2015-04-15
 * @author lynch
 *
 */
public interface PlatConcernCacheDao extends BaseCacheDao {

	/**
	 * 保存关注
	 * 
	 * @param concern
	 */
	public void saveConcern(PlatConcern concern);
	
	/**
	 * 弹出关注信息
	 * 
	 * @param limit
	 */
	public void popConcern(int limit) throws Exception;
	
	/**
	 * 保存被关注信息
	 * 
	 * @param concern
	 */
	public void saveBeConcern(PlatConcern concern);
	
	/**
	 * 删除被关注信息
	 * 
	 * @param index
	 */
	public void deleteBeConcern(int index);
	
	/**
	 * 查询所有被关注信息
	 * 
	 * @return
	 */
	public List<PlatConcern> queryAllBeConcern();
	
}
