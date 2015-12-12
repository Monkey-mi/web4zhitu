package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannelTheme;

/**
 * <p>
 * 频道专题缓存数据访问接口
 * </p>
 * 
 * 创建时间: 2015-05-27
 * @author lynch
 *
 */
public interface ChannelThemeCacheDao extends BaseCacheDao {

	/**
	 * 更新频道缓存
	 */
	public void updateTheme();

	/**
	 * 查询缓存频道
	 * 
	 * @return
	 */
	public List<OpChannelTheme> queryTheme();
	
	/**
	 * 查询缓存频道列表(没有推荐类型)
	 * 
	 * @return
	 */
	public List<OpChannelTheme> queryThemeWithoutTop();
	
}
