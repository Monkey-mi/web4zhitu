package com.hts.web.ztworld.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.HTWorldSubtitleDto;

public interface SubtitleCacheDao extends BaseCacheDao {

	public List<HTWorldSubtitleDto> querySubtitle();
	
}
