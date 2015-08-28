package com.hts.web.aliyun.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.OsWorldCacheDao;
import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.pojo.HTWorldOpenSearch;

import net.sf.json.JSONArray;

@Repository("HTSOsWorldCacheDao")
public class OpWorldCacheDaoImpl extends BaseOsCacheDaoImpl implements OsWorldCacheDao {

	@Override
	public void saveWorld2Opts(HTWorldOpenSearch world) {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("id", world.getId());
		fields.put("longitude", world.getLongitude());
		fields.put("latitude", world.getLatitude());
		fields.put("loc_desc", world.getLocDesc());
		fields.put("province", world.getProvince());
		fields.put("latitude", world.getLatitude());
		saveOpt("add", fields, CacheKeies.OS_WORLD);	
	}

	@Override
	public JSONArray popOpts(int limit) {
		BoundListOperations<String, String> op = 
				getRedisKeyTemplate().boundListOps(CacheKeies.OS_WORLD);
		
		List<String> list = op.range(0, limit-1);
		if(list == null || list.size() == 0) {
			return null;
		}
		
		JSONArray jsarray = JSONArray.fromObject(list);
		// 清空已生效的操作缓存
		op.trim(list.size(), -1);
		
		return jsarray;
	}

}
