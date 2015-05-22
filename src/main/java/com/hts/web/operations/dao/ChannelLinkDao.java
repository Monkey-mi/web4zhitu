package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpChannelLink;

/**
 * <p>
 * 相关频道数据访问接口
 * </p>
 * 
 * 创建时间: 2015-05-21
 * @author lynch
 *
 */
public interface ChannelLinkDao extends BaseDao {

	public List<OpChannelLink> queryLink(Integer channelId);
	
	/**
	 * 保存相关频道
	 * 
	 * @param channelId
	 * @param linkId
	 * @param serial
	 */
	public void saveLink(Integer channelId, Integer linkId, Integer serial);
	
}
