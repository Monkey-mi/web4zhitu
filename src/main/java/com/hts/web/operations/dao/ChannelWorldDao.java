package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.common.pojo.OpChannelWorld;

public interface ChannelWorldDao extends BaseDao {

	public List<OpChannelWorld> queryChannelWorld(Integer channelId, RowSelection rowSelection);
	
	public List<OpChannelWorld> queryChannelWorld(Integer maxId, Integer channelId, RowSelection rowSelection);
	
	public List<OpChannelCover> queryTitleThumb(List<OpChannel> channels, 
			Integer limit);
}
