package com.hts.web.userinfo.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;

/**
 * 用户背景redis缓存数据接口类
 * 
 * @author zhangbo	2015年12月25日
 *
 */
public interface UserBackgroundCacheDao extends BaseCacheDao {

	/**
	 * 查询为用户提供的默认背景图片路径集合
	 * 
	 * @return
	 * @throws Exception
	 * @author zhangbo	2015年12月25日
	 */
	List<String> queryDefaultBackgroundPath() throws Exception;
}
