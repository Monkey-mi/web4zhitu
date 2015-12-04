package com.hts.web.operations.dao;

import java.util.List;

import com.hts.web.common.dao.BaseDao;
import com.hts.web.common.pojo.OpNearLabelWorldDto;

/**
 * 附近标签织图 持久层接口
 * @author zxx 2015-12-4 12:19:41
 *
 */
public interface NearLabelWorldDao extends BaseDao{
	/**
	 * 根据最大序列号查询
	 * @param labelId
	 * @param maxSerial
	 * @param limit
	 * @return
	 */
	public List<OpNearLabelWorldDto> queryNearLabelWorldD(Integer labelId,Integer maxSerial,int limit);
	
	/**
	 * 查询
	 * @param labelId
	 * @param limit
	 * @return
	 */
	public List<OpNearLabelWorldDto> queryNearLabelWorldD(Integer labelId,int limit);
}
