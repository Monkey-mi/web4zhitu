package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCover;
import com.hts.web.common.pojo.OpChannelWorld;
import com.hts.web.common.pojo.OpChannelWorldDto;

public interface ChannelWorldDao extends BaseDao {

	/**
	 * 查询频道普通织图
	 * 
	 * @param channelId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannelWorldDto> queryChannelWorld(Integer channelId, 
			RowSelection rowSelection);
	
	/**
	 * 根据最大id查询频道织图
	 * 
	 * @param maxId
	 * @param channelId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannelWorldDto> queryChannelWorld(Integer maxId, 
			Integer channelId, RowSelection rowSelection);
	
	/**
	 * 查询精选织图
	 * 
	 * @param channelId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannelWorldDto> querySuperbChannelWorld(Integer channelId, 
			RowSelection rowSelection);
	
	/**
	 * 根据最大id查询精选织图
	 * 
	 * @param maxId
	 * @param channelId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannelWorldDto> querySuperbChannelWorld(Integer maxId, 
			Integer channelId, RowSelection rowSelection);
	
	/**
	 * 查询频道封面
	 *
	 * @param channels
	 * @param limit
	 * @return
	 */
	public List<OpChannelCover> queryTitleThumb(List<OpChannel> channels, 
			Integer limit);
	
	/**
	 * 保存频道织图
	 * 
	 * @param world
	 */
	public void saveChannelWorld(OpChannelWorld world);
	
	/**
	 * 查询频道织图总数
	 * 
	 * @param channelId
	 * @return
	 */
	public Long queryWorldCount(Integer channelId);
	
	/**
	 * 查询频道图片总数
	 * 
	 * @param channelId
	 * @return
	 */
	public int queryChildCount(Integer channelId);
	
	/**
	 * 查询精选总数
	 * 
	 * @param channelId
	 * @return
	 */
	public Long querySuperbCount(Integer channelId);
	
	/**
	 * 查询精选织图
	 * 
	 * @param channelId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannelWorldDto> queryWeightChannelWorld(Integer channelId, 
			RowSelection rowSelection);
	
	/**
	 * 根据最大id查询精选织图
	 * 
	 * @param maxId
	 * @param channelId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannelWorldDto> queryWeightChannelWorld(Integer maxId, 
			Integer channelId, RowSelection rowSelection);
	
}
