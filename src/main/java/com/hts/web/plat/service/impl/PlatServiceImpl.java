package com.hts.web.plat.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.PlatConcern;
import com.hts.web.plat.dao.PlatConcernCacheDao;
import com.hts.web.plat.service.PlatService;

@Service("HTSPlatService")
public class PlatServiceImpl implements PlatService {

	private static Logger log = Logger.getLogger(PlatServiceImpl.class);
	
	@Autowired
	private PlatConcernCacheDao concernCacheDao;
	
	@Override
	public void savePlatConcern(String uid, Integer pid) {
		try { // 捕获所有异常
			List<PlatConcern> beconcernList = concernCacheDao.queryAllBeConcern();
			if(beconcernList == null || beconcernList.isEmpty()) 
				return;
			
			for(PlatConcern be : beconcernList) {
				if(pid.equals(be.getPid())) {
					concernCacheDao.saveConcern(new PlatConcern(uid, be.getCid(), 
							be.getCname(), pid));
				}
				
			}
		} catch(Exception e) {
			log.warn("save plat concern err", e);
		}
	}

}
