package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerTop;
import com.hts.web.ztworld.dao.StickerTopCacheDao;

@Repository("HTSStickerTopCacheDao")
public class StickerTopCacheDaoImpl extends BaseCacheDaoImpl<HTWorldStickerTop>
		implements StickerTopCacheDao {

	@Override
	public List<HTWorldStickerTop> queryTopSticker() {
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_STICKER_TOP, 0, -1);
	}

}
