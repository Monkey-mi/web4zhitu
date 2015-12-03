package com.hts.web.operations.service;

import java.util.List;
import java.util.Map;

import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldInteractDto;
import com.hts.web.common.pojo.OpMsgBulletin;
import com.hts.web.common.pojo.OpNearLabelDto;
import com.hts.web.common.service.BaseService;

/**
 * 附近的织图数据访问接口
 * 
 * @author lynch 2015-12-01
 *
 */
public interface NearService extends BaseService {
	
	/**
	 * 查询附近织图
	 * @param address 地址一般指城市。若为空，则经纬度不能为空
	 * @param longitude 经度，和纬度成对出现。若为空，则address不能为空
	 * @param latitude 纬度，和经度成对出现。若为空，则address不能为空
	 * @param maxId
	 * @param limit
	 * @param jsonMap
	 * @param commentLimit
	 * @param likedLimit
	 * @throws Exception
	 * 
	 * @author zxx 2015-12-2 15:08:13
	 */
	public void buildNearWorld(String address,Double longitude, Double latitude,
			int maxId, int limit,Map<String,Object>jsonMap,Integer commentLimit,Integer likedLimit)throws Exception;
	
	/**
	 * 查询附近标签
	 * @param address 地址一般指城市。若为空，则经纬度不能为空
	 * @param longitude 经度，和纬度成对出现。若为空，则address不能为空
	 * @param latitude 纬度，和经度成对出现。若为空，则address不能为空
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * 
	 * @author zxx 2015-12-2 15:10:08
	 */
	public void buildNearLabel(String address,Double longitude, Double latitude,
			int maxId, int limit,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 查询附近的banner
	 * @param address  地址一般指城市。
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * 
	 * @author zxx 2015-12-2 15:11:02
	 */
	public void buildNearBanner(String address,Double longitude, Double latitude,
			int start, int limit,Map<String,Object>jsonMap)throws Exception;
	
	/**
	 * 查询附近标签对应的织图
	 * @param labelId 附近标签id
	 * @param start
	 * @param limit
	 * @param jsonMap
	 * @throws Exception
	 * @author zxx 2015-12-2 15:11:29
	 */
	public void buildNearLabelWorld(Integer labelId,int start, int limit,Map<String,Object>jsonMap)throws Exception;
	

	/**
	 * 根据经纬度查询附近的织图
	 * 
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param maxId 起始id,0表示第一页
	 * @param limit 每页限定记录数量
	 * @return 瀑布流织图列表(包含用户信息)
	 * @author lynch 2015-12-01
	 */
	public List<HTWorldInteractDto> queryNearWorld(double longitude, double latitude, 
			int maxId, int limit);
	
	/**
	 * 根据经纬度和搜索半径查询附近的织图
	 * 
	 * @param radius 搜索半径
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param maxId 起始id,0表示第一页
	 * @param limit 每页限定记录数量
	 * @return 瀑布流织图列表(包含用户信息)
	 * @return
	 * @author lynch 2015-12-03
	 */
	public List<HTWorldInteractDto> queryNearWorld(float radius, double longitude, double latitude, 
			int maxId, int limit);
	
	/**
	 * 根据城市名查询附近的织图
	 * 
	 * @param city 城市名称,会删除省,市,县等后缀再进行查询
	 * @param maxId 起始id,0表示第一页
	 * @param limit 每页限定记录数量
	 * @return 瀑布流织图列表(包含用户信息)
	 * @author lynch 2015-12-01
	 */
	public List<HTWorldInteractDto> queryNearWorld(String city, 
			int maxId, int limit);
	

	/**
	 * 保存附近的织图
	 * 
	 * @param world 织图信息
	 * @author lynch 2015-12-01
	 */
	public void saveNearWorld(HTWorld world);
	
	/**
	 * 根据id删除附近的织图
	 * 
	 * @param id
	 * @author lynch 2015-12-01
	 */
	public void deleteNearWorld(Integer id);
	
	
	/**
	 * 根据经纬度查询附近的标签
	 * 
	 * @param longitude
	 * @param latitude
	 * @param start
	 * @param limit
	 * @return
	 * @author lynch 2015-12-02
	 */
	public List<OpNearLabelDto> queryNearLabel(double longitude, 
			double latitude, int start, int limit);
	
	
	/**
	 * 查询附近的公告
	 * 
	 * @param longitude
	 * @param latitude
	 * @param start
	 * @param limit
	 * @return
	 * @author lynch 2015-12-02
	 */
	public List<OpMsgBulletin> queryNearBuilletin(double longitude, 
			double latitude, int start, int limit);
	
	/**
	 * 查询附近的推荐城市
	 * @throws Exception
	 * @author zxx 2015-12-3 17:16:18
	 */
	public void buildRecommendCity(Map<String,Object>jsonMap)throws Exception;
	
}
