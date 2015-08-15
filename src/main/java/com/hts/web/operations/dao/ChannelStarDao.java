package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.base.database.RowSelection;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.UserInfoDto;

/**
 * <p>
 * 频道明星数据访问接口
 * </p>
 * 
 * 创建时间: 2015-08-14
 * @author lynch
 *
 */
public interface ChannelStarDao extends BaseDao {

	/**
	 * 查询频道明星
	 * 
	 * @param channelId
	 * @param rowSelection
	 * @return
	 */
	public List<UserInfoDto> queryStar(Integer channelId,
			RowSelection rowSelection);

}
