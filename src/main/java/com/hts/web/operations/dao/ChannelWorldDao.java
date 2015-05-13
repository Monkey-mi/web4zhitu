package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.common.pojo.OpChannelWorld;
import com.hts.web.common.pojo.OpChannelWorldDto;

public interface ChannelWorldDao extends BaseDao {

	public List<OpChannelWorldDto> queryChannelWorld(Integer channelId, RowSelection rowSelection);
	
	public List<OpChannelWorldDto> queryChannelWorld(Integer maxId, Integer channelId, RowSelection rowSelection);
	
	public List<OpChannelWorldDto> querySuperbChannelWorld(Integer channelId, RowSelection rowSelection);
	
	public List<OpChannelWorldDto> querySuperbChannelWorld(Integer maxId, Integer channelId, RowSelection rowSelection);
	
	public List<OpChannelCover> queryTitleThumb(List<OpChannel> channels, 
			Integer limit);
	
	/**
	 * 保存频道织图
	 * 
	 * @param channelId
	 * @param worldId
	 */
	public void saveChannelWorld(OpChannelWorld world);
	
	/**
	 * 查询织图总数
	 * 
	 * @param channelId
	 */
	public long queryWorldCount(Integer channelId);
	
}
