package com.hts.web.aliyun.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Repository;

import uk.ltd.getahead.dwr.util.Logger;

import com.hts.web.aliyun.dao.OsUserLoginCacheDao;
import com.hts.web.aliyun.dao.OsUserLoginDao;
import com.hts.web.base.constant.CacheKeies;

@Repository("HTSOsUserLoginCacheDao")
public class OsUserLoginCacheDaoImpl extends BaseOsCacheDaoImpl implements
		OsUserLoginCacheDao {

	private static Logger log = Logger.getLogger(OsUserLoginCacheDaoImpl.class);
	
	@Autowired
	private OsUserLoginDao userLoginDao;
	
	@Override
	public void saveLogin(Integer id, Integer lastLogin) {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("user_id", id);
		fields.put("last_login", lastLogin);
		saveOpt("add", fields, CacheKeies.OS_USER_LOGIN);
	}

	@Override
	public void updateLogin(Integer id, Integer lastLogin) {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("user_id", id);
		fields.put("last_login", lastLogin);
		saveOpt("update", fields, CacheKeies.OS_USER_LOGIN);
	}
	
	@Override
	public void popOpts(int limit) {
		BoundListOperations<String, String> op = 
				getRedisKeyTemplate().boundListOps(CacheKeies.OS_USER_LOGIN);
		List<String> list = op.range(0, limit);
		if(list == null || list.size() == 0) {
			return;
		}
		
		JSONArray jsarray = JSONArray.fromObject(list);
		try {
			userLoginDao.pushUpdate(jsarray.toString());
		} catch (Exception e) {
			log.warn("pop opensearch userlogin operations error", e);
			return;
		}
		// 清空已生效的操作缓存
		for(int i = 0; i < list.size(); i++) {
			op.leftPop();
		}
	}

}
