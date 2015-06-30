package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldStickerTop;

/**
 * <p>
 * 置顶推荐贴纸缓存数据访问接口
 * </p>
 * 
 * @author lynch
 *
 */
public interface StickerTopCacheDao extends BaseCacheDao {

	/**
	 * 查询置顶贴纸
	 * 
	 * @return
	 */
	public List<HTWorldStickerTop> queryTopSticker();
}
