package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerDto;
import com.hts.web.ztworld.dao.HTWorldStickerCacheDao;

@Repository("HTSHTWorldStickerCacheDao")
public class HTWorldStickerCacheDaoImpl extends BaseCacheDaoImpl<HTWorldStickerDto> implements
		HTWorldStickerCacheDao {

	@Override
	public List<HTWorldStickerDto> queryTopSticker() {
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_STICKER_TOP, 0, -1);
	}

	@Override
	public List<HTWorldStickerDto> queryRecommendSticker() {
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_STICKER_RECOMMEND, 0, -1);
	}

	@Override
	public HTWorldStickerDto queryFirstRecommendSticker() {
		return getRedisTemplate().opsForList().index(CacheKeies.ZTWORLD_STICKER_RECOMMEND, 0);
	}

//	@Override
//	public List<HTWorldStickerDto> queryHotSticker() {
//		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_STICKER_HOT, 0, -1);
//	}

}
