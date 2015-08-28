package com.hts.web.aliyun.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldOpenSearch;

import net.sf.json.JSONArray;

/**
 * <p>
 * OpenSearch织图缓存数据访问接口
 * </p>
 * 
 * 创建时间: 2015-08-25
 * 
 * @author lynch
 *
 */
public interface OsWorldCacheDao extends BaseCacheDao {

	/**
	 * 缓存织图信息
	 * 
	 * @param longitude
	 * @param latitude
	 * @param locationDesc
	 * @param province
	 * @param city
	 */
	public void saveWorld2Opts(HTWorldOpenSearch world);

	/**
	 * 
	 * @param limit
	 * @return
	 */
	public JSONArray popOpts(int limit);
}
