package com.hts.web.stat.service;

/**
 * 统计业务访问接口
 * 
 * @author lynch
 *
 */
public interface StatService {

	/**
	 * 添加PV
	 * 
	 * @param key
	 * @author lynch 2015-12-18
	 */
	public void incPV(Integer key);
	
	/**
	 * 添加子模块PV
	 * 
	 * @param key
	 * @param subkey
	 * @author lynch 2015-12-18
	 */
	public void incSubPV(Integer key, Integer subkey);
	
	/**
	 * 增加第一页和第二页查询的PV
	 * 
	 * @param key
	 * @param maxId
	 * @param nextPageKey
	 */
	public void inc2PagePV(Integer key, Integer maxId, Integer nextPageKey);
	
	/**
	 * 增加子模块第一页和第二页查询的PV
	 * 
	 * @param key
	 * @param subkey
	 * @param maxId
	 * @param nextPageKey
	 */
	public void incSub2PagePV(Integer key, Integer subkey, Integer maxId, Integer nextPageKey);
	
	
	/**
	 * 增加第一页和第二页查询的PV
	 * 
	 * @param key
	 * @param start
	 * @param nextPageKey
	 */
	public void inc2PagePVWithStart(Integer key, Integer start, Integer nextPageKey);
	
	/**
	 * 增加子模块第一页和第二页查询的PV
	 * 
	 * @param key
	 * @param id
	 * @param start
	 * @param nextPageKey
	 */
	public void incSub2PagePVWithStart(Integer key, Integer id, Integer start, Integer nextPageKey);

	
}
