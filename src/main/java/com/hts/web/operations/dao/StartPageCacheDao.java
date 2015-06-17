package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpMsgStartPage;

/**
 * <p>
 * 启动页面缓存数据访问接口
 * </p>
 * 
 * 创建时间: 2015-06-09
 * @author lynch
 *
 */
public interface StartPageCacheDao extends BaseCacheDao {

	/**
	 * 查询启动页面
	 * 
	 * @return
	 */
	public List<OpMsgStartPage> queryStartPage();
	
	/**
	 * 更新启动页
	 */
	public void updateStartPage(List<OpMsgStartPage> pages);
}
