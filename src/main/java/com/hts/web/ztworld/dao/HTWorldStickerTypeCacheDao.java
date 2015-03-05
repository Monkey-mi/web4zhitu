package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;

/**
 * <p>
 * 织图贴纸分类缓存数据访问接口
 * </p>
 * 
 * 创建时间:2014-12-26
 * @author lynch
 *
 */
public interface HTWorldStickerTypeCacheDao extends BaseCacheDao {

	/**
	 * 查询贴纸分类
	 * 
	 * @return
	 */
	public List<HTWorldStickerTypeDto> queryStickerType();
	
	/**
	 * 查询推荐分类
	 * 
	 * @return
	 */
	public List<HTWorldStickerTypeDto> queryRecommendType();
}
