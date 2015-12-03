package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.AddrCity;
import com.hts.web.common.pojo.OpNearCityGroupDto;

/**
 * 附近推荐城市持久层接口
 * @author zxx 2015-12-3 16:46:26
 *
 */
public interface NearRecommendCityDao extends BaseDao{
	/**
	 * 根据城市组id，查询城市组
	 * @param cityGroupId
	 * @return
	 * @author zxx 2015-12-3 16:49:51
	 */
	public List<AddrCity> queryNearRecommendCityByGroupId(Integer cityGroupId);
	
	/**
	 * 查询所有的城市推荐分组
	 * @return
	 */
	public List<OpNearCityGroupDto> queryNearCityGroup();
}
