package com.hts.web.operations.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hts.web.base.constant.CacheKeies;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.impl.BaseCacheDaoImpl;
import com.hts.web.common.pojo.OpWorldTypeDto2;
import com.hts.web.operations.dao.OpWorldTypeDto2CacheDao;

/**
 * <p>
 * 织图分类数据缓存访问对象
 * </p>
 * 
 * 创建时间：2014-5-4
 * @author tianjie
 *
 */
@Repository("HTSOpWorldTypeDto2CacheDao")
public class OpWorldTypeDto2CacheDaoImpl extends BaseCacheDaoImpl<OpWorldTypeDto2> implements
		OpWorldTypeDto2CacheDao {

	@Override
	public List<OpWorldTypeDto2> querySuperbWorldType(RowSelection rowSelection) {
		return getRedisTemplate().boundListOps(CacheKeies.OP_SUPERB_TYPE)
			.range(rowSelection.getFirstRow(), rowSelection.getMaxRow() - 1);
	}
	
	@Override
	public List<OpWorldTypeDto2> querySuperbWorldType(int start, int end) {
		return getRedisTemplate().boundListOps(CacheKeies.OP_SUPERB_TYPE)
				.range(start, end);
	}

	@Override
	public OpWorldTypeDto2 querySuperWorldType(long index) {
		return getRedisTemplate().opsForList().index(CacheKeies.OP_SUPERB_TYPE, index);
	}

	@Override
	public OpWorldTypeDto2 queryLastSuperWorldType() {
		return querySuperWorldType(-1);
	}
	
}
