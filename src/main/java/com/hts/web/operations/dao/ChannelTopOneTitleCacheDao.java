package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannelTopOneTitle;

public interface ChannelTopOneTitleCacheDao extends BaseCacheDao {

	/**
	 * 查询Top one人物页面标题
	 * 
	 * @return
	 */
	public OpChannelTopOneTitle queryTitle();
}
