package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldStickerSetDto;

public interface StickerSetDtoCacheDao extends BaseCacheDao {

	public List<HTWorldStickerSetDto> querySet(Integer typeId, Integer firstRow, 
			Integer lastRow);
}
