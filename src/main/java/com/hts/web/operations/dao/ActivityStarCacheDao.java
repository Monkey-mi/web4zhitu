package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpActivityStar;

public interface ActivityStarCacheDao extends BaseCacheDao {

	public List<OpActivityStar> queryStar(Integer activityId);
}
