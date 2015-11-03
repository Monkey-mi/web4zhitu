package com.hts.web.operations.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpSysMsgDto;
import com.hts.web.operations.dao.SysMsgCommonCacheDao;
import com.hts.web.operations.dao.SysMsgCommonMaxIdCacheDao;

@Repository("HTSSysMsgCommonCacheDao")
public class SysMsgCommonCacheDaoImpl extends BaseCacheDaoImpl<OpSysMsgDto>implements SysMsgCommonCacheDao {

	public TreeMap<Integer, Integer> index = new TreeMap<Integer, Integer>();

	@Autowired
	private SysMsgCommonMaxIdCacheDao maxIdDao;

	@Override
	public List<OpSysMsgDto> queryMsg(Integer limit) {
		if(index.isEmpty()) {
			rebuildIndex();
		}
		Integer maxId = maxIdDao.queryMaxId();
		List<OpSysMsgDto> list = getRedisTemplate().boundListOps(
				CacheKeies.OP_MSG_COMMON_SYSMSG).range(0, limit-1);
		
		if(list != null && !list.isEmpty() 
				&& list.get(0).getId() > maxId) {
			maxIdDao.updateMaxId(list.get(0).getId());
			rebuildIndex();
		}
		return list;
	}

	@Override
	public List<OpSysMsgDto> queryMsg(Integer maxId, Integer limit) {
		if(index.isEmpty()) {
			rebuildIndex();
		}
		Entry<Integer, Integer> entry = index.lowerEntry(maxId);
		if(entry != null) {
			Integer idx = entry.getValue();
			//　理论上取值范围应该是(idx~idx+limit-1),但是第一个却会重复
			return getRedisTemplate().boundListOps(
					CacheKeies.OP_MSG_COMMON_SYSMSG).range(idx+1, idx+limit);
		}
		return new ArrayList<OpSysMsgDto>();
	}
	
	/**
	 * 重建搜索索引
	 */
	public void rebuildIndex() {
		index.clear();
		List<OpSysMsgDto> list = getRedisTemplate().boundListOps(
				CacheKeies.OP_MSG_COMMON_SYSMSG).range(0, -1);
		for(int i = 0; i < list.size(); i++) {
			index.put(list.get(i).getId(), i);
		}
		list.clear();
		list = null;
	}

	@Override
	public Integer higherCount(Integer id) {
		if(index.isEmpty()) {
			rebuildIndex();
		}
		Entry<Integer, Integer> entry = index.higherEntry(id);
		if(entry != null) {
			return entry.getValue() + 1;
		}
		return 0;
	}

}
