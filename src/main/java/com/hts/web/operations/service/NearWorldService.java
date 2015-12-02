package com.hts.web.operations.service;

import java.util.List;

import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.service.BaseService;

/**
 * 附近的织图数据访问接口
 * 
 * @author lynch 2015-12-01
 *
 */
public interface NearWorldService extends BaseService {

	/**
	 * 根据经纬度查询附近的织图
	 * 
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param start 起始页码
	 * @param limit 每页限定记录数量
	 * @return 瀑布流织图列表(包含用户信息)
	 * @author lynch 2015-12-01
	 */
	public List<HTWorldInteractDto> queryNearWorld(double longitude, double latitude, 
			int start, int limit);
	
	/**
	 * 根据城市名查询附近的织图
	 * 
	 * @param city 城市名称,会删除省,市,县等后缀再进行查询
	 * @param start 起始页码
	 * @param limit 每页限定记录数量
	 * @return 瀑布流织图列表(包含用户信息)
	 * @author lynch
	 */
	public List<HTWorldInteractDto> queryNearWorld(String city, 
			int start, int limit);
	

	/**
	 * 保存附近的织图
	 * 
	 * @param world 织图信息
	 */
	public void saveNearWorld(HTWorld world);
	
	/**
	 * 根据id删除附近的织图
	 * 
	 * @param id
	 */
	public void deleteNearWorld(Integer id);
	
}
