package com.hts.web.ztworld.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.ztworld.dao.HTWorldLabelCacheDao;

/**
 * <p>
 * 织图标签缓存数据访问对象
 * </p>
 * 
 * 创建时间:2014-5-5
 * @author tianjie
 *
 */
@Repository("HTSHTWorldLabelCacheDao")
public class HTWorldLabelCacheDaoImpl extends BaseCacheDaoImpl<HTWorldLabel> 
	implements HTWorldLabelCacheDao{

	@Override
	public List<HTWorldLabel> queryHotLabel(RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_LABEL_HOT, 
				rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}

	@Override
	public List<HTWorldLabel> queryActivityLabel(RowSelection rowSelection) {
		return getRedisTemplate().opsForList().range(CacheKeies.ZTWORLD_LABEL_ACTIVITY, 
				rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}


	
}
