package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannelStar;

/**
 * <p>
 * 频道明星缓存数据访问对象
 * </p>
 * 
 * 创建时间:2014-10-31
 * @author lynch
 *
 */
public interface ChannelStarCacheDao extends BaseCacheDao {

	/**
	 * 查询频道明星列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<OpChannelStar> queryStar(Integer channelId);
}
