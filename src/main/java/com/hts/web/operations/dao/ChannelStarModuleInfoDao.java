package com.hts.web.operations.dao;

import java.util.List;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpStarModuleInfo;

public interface ChannelStarModuleInfoDao extends BaseDao {

	/**
	 * 获取主题中各个达人信息
	 * @param topicId
	 * @return 
		*	2015年9月25日
		*	mishengliang
	 */
	public 	List<OpStarModuleInfo> getOpStarModuleInfo(Integer topicId);
	
}
