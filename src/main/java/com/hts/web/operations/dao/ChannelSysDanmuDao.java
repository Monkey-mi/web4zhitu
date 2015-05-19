package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpChannelSysDanmuDto;

/**
 * 系统弹幕数据访问接口
 * 
 * @author lynch
 *
 */
public interface ChannelSysDanmuDao extends BaseDao {

	/**
	 * 查询系统弹幕
	 * 
	 * @param channelId
	 * @return
	 */
	public List<OpChannelSysDanmuDto> querySysDanmu(Integer channelId, 
			RowSelection rowSelection);
	
	/**
	 * 根据最大id查询系统弹幕
	 * 
	 * @param maxId
	 * @param channelId
	 * @return
	 */
	public List<OpChannelSysDanmuDto> querySysDanmu(Integer maxId, 
			Integer channelId, RowSelection rowSelection);
}
