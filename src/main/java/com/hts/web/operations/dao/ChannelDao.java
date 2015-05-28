package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.pojo.OpChannelCount;
import com.hts.web.common.pojo.OpChannelDetail;
import com.hts.web.common.pojo.OpChannelName;

/**
 * <p>
 * 频道数据访问接口
 * </p>
 * 
 * 创建时间: 2015-05-05
 * @author lynch
 *
 */
public interface ChannelDao extends BaseDao {

	/**
	 * 查询已经加入的频道
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannel> querySubscribedChannel(Integer userId,
			RowSelection rowSelection);
	
	/**
	 * 根据最大id查询已经加入的频道
	 * 
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannel> querySubscribedChannel(Integer maxId, 
			Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询已经加入的频道
	 * 
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannelName> querySubscribedName(Integer userId,
			RowSelection rowSelection);
	
	/**
	 * 根据最大id查询已经加入的频道
	 * 
	 * @param maxId
	 * @param userId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannelName> querySubscribedName(Integer maxId, 
			Integer userId, RowSelection rowSelection);
	
	/**
	 * 查询频道详情
	 * 
	 * @param channelId
	 * @return
	 */
	public OpChannelDetail queryChannelDetail(Integer channelId);
	
	/**
	 * 查询频道摘要
	 * 
	 * @param channelId
	 * @return
	 */
	public OpChannel queryChannel(Integer channelId);
	
	/**
	 * 查询频道计数
	 * 
	 * @param channelIds
	 * @param callback
	 */
	public void queryChannelCount(Integer[] channelIds, 
			RowCallback<OpChannelCount> callback);
	
	/**
	 * 查询精选频道列表
	 * 
	 * @return
	 */
	public List<OpChannel> querySuperbChannel(Integer limit);
	
	/**
	 * 添加织图和子世界总数
	 * 
	 * @param id
	 * @param worldCount
	 * @param childCount
	 */
	public void addWorldAndChildCount(Integer id, 
			Integer worldCount, Integer addChildCount);
	
	/**
	 * 更新织图和子世界总数
	 * 
	 * @param id
	 * @param worldCount
	 * @param childCount
	 */
	public void updateWorldAndChildCount(Integer id, 
			Integer worldCount, Integer childCount);
	
	/**
	 * 更新成员总数
	 * 
	 * @param id
	 * @param memberCount
	 */
	public void updateMemberCount(Integer id, Integer memberCount);

	/**
	 * 更新精选总数
	 * 
	 * @param id
	 * @param superbCount
	 */
	public void updateSuperbCount(Integer id, Integer superbCount);
	
	/**
	 * 保存频道
	 * 
	 * @param channel
	 */
	public void saveChannel(OpChannel channel);
	
	/**
	 * 查询专题频道
	 * 
	 * @param themeId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannel> queryThemeChannel(Integer themeId,
			RowSelection rowSelection);
	
	/**
	 * 查询专题频道
	 * 
	 * @param maxId
	 * @param themeId
	 * @param rowSelection
	 * @return
	 */
	public List<OpChannel> queryThemeChannel(Integer maxId, 
			Integer themeId, RowSelection rowSelection);
}
