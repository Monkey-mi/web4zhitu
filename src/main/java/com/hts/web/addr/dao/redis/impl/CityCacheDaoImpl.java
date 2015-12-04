package com.hts.web.addr.dao.redis.impl;

import org.springframework.stereotype.Repository;

import com.hts.web.addr.dao.redis.CityCacheDao;
import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.AddrCity;
import com.hts.web.common.util.StringUtil;

@Repository("HTSAddrCityCacheDao")
public class CityCacheDaoImpl extends BaseCacheDaoImpl<AddrCity> implements CityCacheDao {

	@Override
	public AddrCity queryCityByName(String name) {
		if(!StringUtil.checkIsNULL(name)) {
			Object cityObj = getRedisTemplate().boundHashOps(CacheKeies.ADDR_CITY).get(
					StringUtil.getCityShotName(name));
			if(cityObj != null)
				return (AddrCity)cityObj;
		}
		return null;
	}

}
