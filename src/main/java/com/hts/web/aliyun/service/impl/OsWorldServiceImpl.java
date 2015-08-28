package com.hts.web.aliyun.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.dao.OsWorldCacheDao;
import com.hts.web.aliyun.dao.OsWorldDao;
import com.hts.web.aliyun.service.OsWorldService;
import com.hts.web.common.pojo.HTWorldOpenSearch;

import net.sf.json.JSONArray;

@Service("HTSOsWorldService")
public class OsWorldServiceImpl implements OsWorldService {

	private static Logger logger = Logger.getLogger(OsWorldServiceImpl.class);
	
	@Autowired
	private OsWorldCacheDao osWorldCacheDao;
	
	@Autowired
	private OsWorldDao osWorldDao;
	

	@Override
	public void saveWorld(Integer id, Double longitude, Double latitude, 
			String locationDesc, String province, String city) {
		try {
			osWorldCacheDao.saveWorld2Opts(new HTWorldOpenSearch(id, longitude, 
					latitude, locationDesc, province, city));
		} catch(Exception e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void commitOpts(Integer limit) throws Exception{
		JSONArray jsarray = osWorldCacheDao.popOpts(limit);
		if(jsarray != null) {
			osWorldDao.pushUpdate(jsarray.toString());
		}
	}

}
