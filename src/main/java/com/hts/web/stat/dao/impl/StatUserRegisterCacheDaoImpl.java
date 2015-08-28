package com.hts.web.stat.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.stat.dao.StatUserRegisterCacheDao;

@Repository("HTSStatUserRegisterCacheDao")
public class StatUserRegisterCacheDaoImpl extends BaseCacheDaoImpl<Map<String, Object>>
	implements StatUserRegisterCacheDao {

	private static Logger logger = Logger.getLogger(StatUserRegisterCacheDao.class);
	
	@Override
	public void saveRegisterStat(Integer uid, Integer phoneCode, Date registerDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", uid);
		map.put("phoneCode", phoneCode);
		map.put("registerDate", registerDate);
		try {
			getRedisTemplate().boundListOps(CacheKeies.STAT_USER_REGISTER).rightPush(map);
		} catch(Exception e) {
			logger.warn("save register statistics error", e);
		}
	}

}
