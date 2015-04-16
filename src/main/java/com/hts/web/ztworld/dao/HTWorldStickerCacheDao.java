package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldStickerDto;

/**
 * <p>
 * 织图贴纸缓存数据访问接口
 * </p>
 * 
 * 创建时间:2014-12-26
 * @author lynch
 *
 */
public interface HTWorldStickerCacheDao extends BaseCacheDao {

	/**
	 * 查询置顶贴纸
	 * 
	 * @return
	 */
	public List<HTWorldStickerDto> queryTopSticker();
	
	/**
	 * 查询推荐贴纸
	 * 
	 * @return
	 */
	public List<HTWorldStickerDto> queryRecommendSticker();
	
	/**
	 * 查询推荐第一贴纸
	 * 
	 * @return
	 */
	public HTWorldStickerDto queryFirstRecommendSticker();
	
//	/**
//	 * 查询热门贴纸
//	 * 
//	 * @return
//	 */
//	public List<HTWorldStickerDto> queryHotSticker();
}
