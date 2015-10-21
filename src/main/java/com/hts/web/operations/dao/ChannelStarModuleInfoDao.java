package com.hts.web.operations.dao;

import java.util.List;
import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpStarModuleInfo;

public interface ChannelStarModuleInfoDao extends BaseDao {

	/**
	 * 获取主题中各个达人图片模板信息
	 * @param topicId
	 * @return 
		*	2015年9月25日
		*	mishengliang
	 */
	public 	List<OpStarModuleInfo> getOpStarModuleInfo(Integer topicId);
	
	
	/**
	 * 获取主题中各个达人织图模板信息
	 * @param topicId
	 * @return 
		*	2015年10月21日
		*	mishengliang
	 */
	public 	List<OpStarModuleInfo> getOpStarWorldModuleInfo(Integer topicId);
	
}
