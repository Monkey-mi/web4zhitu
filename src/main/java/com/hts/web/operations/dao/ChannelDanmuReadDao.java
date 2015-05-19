package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseDao;

/**
 * <p>
 * 系统弹幕已读记录表
 * <p>
 * 
 * 创建时间: 2015-05-15
 * @author lynch
 *
 */
public interface ChannelDanmuReadDao extends BaseDao {

	
	/**
	 * 更新弹幕id
	 * 
	 * @param channelId
	 * @param userId
	 * @param maxId
	 */
	public void updateDanmuSerial(Integer channelId, Integer userId, Integer maxId);

	/**
	 * 查询弹幕id
	 * 
	 * @param channelId
	 * @param userId
	 * @return
	 */
	public Integer queryDanmuSerial(Integer channelId, Integer userId);
	
	/**
	 * 保存弹幕id
	 * 
	 * @param channelId
	 * @param userId
	 * @param maxId
	 */
	public void saveDanmuSerial(Integer channelId, Integer userId, Integer maxId);
}
