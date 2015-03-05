package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannelCover;

public interface ChannelCoverCacheDao extends BaseCacheDao {

	/**
	 * 获取缓存封面
	 * 
	 * @return
	 */
	public List<OpChannelCover> queryCacheCover();
}
