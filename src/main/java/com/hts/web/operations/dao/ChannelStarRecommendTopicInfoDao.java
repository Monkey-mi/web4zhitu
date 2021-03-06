package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpStarRecommendPastTopicInfo;
import com.hts.web.common.pojo.OpStarRecommendTopicInfo;

public interface ChannelStarRecommendTopicInfoDao extends BaseDao {

	/**
	 * 获取推荐主题的主要信息
	 * @param topicId
	 * @return 
		*	2015年9月25日
		*	mishengliang
	 */
	public   List<OpStarRecommendTopicInfo> getInfo(Integer topicId);

	/**
	 * 获取往期的三期推荐主题
	 * @param topicId
	 * @return 
		*	2015年10月15日
		*	mishengliang
	 */
	public List<OpStarRecommendPastTopicInfo>  getPastTopicInfo(Integer topicId);
}
