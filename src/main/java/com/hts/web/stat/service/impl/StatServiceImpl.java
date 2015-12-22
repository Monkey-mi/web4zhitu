package com.hts.web.stat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.stat.dao.StatPVCacheDao;
import com.hts.web.stat.service.StatService;

@Repository("HTSStatService")
public class StatServiceImpl implements StatService {

	private static final String STAT_PREFIX = "stat:pv:";
	
	@Autowired
	private StatPVCacheDao statPVCacheDao;
	
	
	@Override
	public void incSubPV(Integer id, Integer subkey) {
		statPVCacheDao.inc(STAT_PREFIX + id + ":" + subkey);
	}
	
	@Override
	public void incPV(Integer id) {
		incSubPV(id, 0);
	}

	@Override
	public void inc2PagePV(Integer key, Integer maxId, Integer nextPageKey) {
		if(maxId == null || maxId == 0)
			incSubPV(key, 0);
		else
			incSubPV(nextPageKey, 0);
	}

	@Override
	public void incSub2PagePV(Integer key, Integer subkey, Integer maxId, Integer nextPageKey) {
		if(maxId == null || maxId == 0)
			incSubPV(key, subkey);
		else
			incSubPV(nextPageKey, subkey);
	}

	@Override
	public void inc2PagePVWithStart(Integer key, Integer start, Integer nextPageKey) {
		if(start == null || start <= 1)
			incSubPV(key, 0);
		else
			incSubPV(nextPageKey, 0);
	}

	@Override
	public void incSub2PagePVWithStart(Integer key, Integer subkey, Integer start, Integer nextPageKey) {
		if(start == null || start <= 1)
			incSubPV(key, subkey);
		else
			incSubPV(nextPageKey, subkey);
	}

}
