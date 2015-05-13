package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseCacheDao;
import com.hts.web.common.pojo.OpChannel;

public interface ChannelCacheDao extends BaseCacheDao {

	public List<OpChannel> queryChannel();
	
	public List<OpChannel> queryChannel(RowSelection rowSelection);
}
