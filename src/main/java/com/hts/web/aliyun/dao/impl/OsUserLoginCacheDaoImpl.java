package com.hts.web.aliyun.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.aliyun.dao.OsUserLoginCacheDao;
import com.hts.web.aliyun.dao.OsUserLoginDao;

@Repository("HTSOsUserLoginCacheDao")
public class OsUserLoginCacheDaoImpl extends BaseOsCacheDaoImpl implements
		OsUserLoginCacheDao {

	private static Logger log = Logger.getLogger(OsUserLoginCacheDaoImpl.class);
	
	@Autowired
	private OsUserLoginDao userLoginDao;
	
	@Override
	public void saveLogin(Integer id, Long lastLogin) {
//		Map<String, Object> fields = new HashMap<String, Object>();
//		fields.put("user_id", id);
//		fields.put("last_login", lastLogin);
//		saveOpt("add", fields, CacheKeies.OS_USER_LOGIN);
	}

	@Override 
	public void updateLogin(Integer id, Long lastLogin) {
//		Map<String, Object> fields = new HashMap<String, Object>();
//		fields.put("user_id", id);
//		fields.put("last_login", lastLogin);
//		saveOpt("update", fields, CacheKeies.OS_USER_LOGIN);
	}
	
	@Override
	public void popOpts(int limit) {
//		BoundListOperations<String, String> op = 
//				getRedisKeyTemplate().boundListOps(CacheKeies.OS_USER_LOGIN);
//		List<String> list = op.range(0, limit-1);
//		if(list == null || list.size() == 0) {
//			return;
//		}
//		
//		JSONArray jsarray = JSONArray.fromObject(list);
//		try {
//			userLoginDao.pushUpdate(jsarray.toString());
//		} catch (Exception e) {
//			log.warn("pop opensearch userlogin operations error", e);
//			return;
//		}
//		// 清空已生效的操作缓存
//		op.trim(list.size(), -1);
	}

}
