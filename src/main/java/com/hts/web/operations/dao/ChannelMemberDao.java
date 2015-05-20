package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowCallback;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpChannelMember;
import com.hts.web.common.pojo.OpChannelMemberThumb;
import com.hts.web.common.pojo.OpChannelSub;

/**
 * <p>
 * 频道成员数据访问接口
 * </p>
 * 
 * 创建时间:2015-05-05
 * @author lynch
 *
 */
public interface ChannelMemberDao extends BaseDao {

	/**
	 * 保存成员
	 * 
	 * @param member
	 */
	public void saveMember(OpChannelMember member);
	
	/**
	 * 删除成员
	 * 
	 * @param channelId
	 * @param userId
	 */
	public void deleteMember(Integer channelId, Integer userId);
	
	/**
	 * 查询成员缩略图列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<OpChannelMemberThumb> queryMemberThumb(Integer channelId, 
			RowSelection rowSelection);
	
	/**
	 * 查询成员缩略图列表
	 * 
	 * @param maxId
	 * @param channelId
	 * @return
	 */
	public List<OpChannelMemberThumb> queryMemberThumb(Integer maxId, Integer channelId,
			RowSelection rowSelection);
	
	/**
	 * 查询记录id
	 * 
	 * @param channel
	 * @param userId
	 * @return
	 */
	public Integer queryId(Integer channel, Integer userId);
	
	/**
	 * 查询频道订阅状态
	 * 
	 * @param userId
	 * @param callback
	 */
	public void queryChannelSub(Integer userId, Integer[] channelIds, 
			RowCallback<OpChannelSub> callback);
	
	/**
	 * 查询成员总数
	 * 
	 * @param channelId
	 * @return
	 */
	public Long queryMemberCount(Integer channelId);
	
}
