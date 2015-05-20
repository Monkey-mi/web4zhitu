package com.hts.web.operations.dao;

import com.hts.web.common.dao.BaseDao;

public interface ChannelCountBaseDao extends BaseDao {

	/**
	 * 查询织图基数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryWorldCount(Integer id);
	
	/**
	 * 查询成员基数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryMemberCount(Integer id);
	
	/**
	 * 查询精选基数
	 * 
	 * @param id
	 * @return
	 */
	public Integer querySuperbCount(Integer id);
	
	/**
	 * 查询织图和图片基数
	 * 
	 * @param id
	 * @return
	 */
	public Integer[] queryWorldAndChildCount(Integer id);
}
