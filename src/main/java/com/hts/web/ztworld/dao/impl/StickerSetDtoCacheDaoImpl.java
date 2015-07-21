package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldStickerSetDto;
import com.hts.web.ztworld.dao.StickerSetDtoCacheDao;

@Repository("HTSStickerSetDtoCacheDao")
public class StickerSetDtoCacheDaoImpl extends BaseCacheDaoImpl<HTWorldStickerSetDto> implements
		StickerSetDtoCacheDao {

	@Override
	public List<HTWorldStickerSetDto> querySet(Integer typeId,
			Integer firstRow, Integer lastRow) {
		return getRedisTemplate().
				boundListOps(CacheKeies.ZTWORLD_STICKER_TYPE_ID + typeId).range(firstRow, lastRow);
	}

}
