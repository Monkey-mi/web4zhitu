package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerTypeDto;
import com.hts.web.ztworld.dao.HTWorldStickerTypeCacheDao;

@Repository("HTSHTWorldStickerTypeCacheDao")
public class HTWorldStickerTypeCacheDaoImpl extends BaseCacheDaoImpl<HTWorldStickerTypeDto>
		implements HTWorldStickerTypeCacheDao {

	@Override
	public List<HTWorldStickerTypeDto> queryStickerType() {
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_STICKER_TYPE, 0, -1);
	}

	@Override
	public List<HTWorldStickerTypeDto> queryRecommendType() {
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_STICKER_RECOMMEND_TYPE, 0, -1);
	}

}
